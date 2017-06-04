package minmax;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Gomoku {
    private Node[][][] board = new Node[4][][];
    private List<Node> expanded;
    private boolean whitePlayer = true;

    static final int WHITE = 1;
    static final int BLACK = -1;
    static final int NO_MOVE = 0;
    static final int WIN_LENGTH = 5;
    static final int BOARD_SIZE = 15;

    Gomoku() {
        board[0] = ArrayUtils.emptyBoard(BOARD_SIZE);
        board[1] = ArrayUtils.rightDiagonals(board[0], WIN_LENGTH);
        board[2] = ArrayUtils.leftDiagonals(board[0], WIN_LENGTH);
        board[3] = ArrayUtils.transposed(board[0]);
        expanded = ArrayUtils.expanded(board[0]);
    }

    public Node getNode(int x, int y) {
        return board[0][x][y];
    }

    //check whole board for a winner
    int getWinningPlayer() {
        int result = 0;
        for (Node[][] array : board) {
            result = checkContinuous(array);
            if (result != 0) {
                break;
            }
        }
        return result;
    }

    //check winner after given move
    int getWinningPlayer(Node move) {
        int result = 0;
        for(Node[] row : getAffectingRows(move)) {
            result = checkContinuous(row);
            if (result != 0) break;
        }
        return result;
    }

    //find 5 consecutive moves in any color on whole board
    private int checkContinuous(Node[][] array) {
        int result = 0;
        for (int i = 0; i < array.length && result == 0; i++) {
            result = checkContinuous(array[i]);
        }
        return result;
    }

    //find 5 consecutive moves in any color in a row
    private int checkContinuous(Node[] row) {
        int black = 0;
        int white = 0;
        for (int j = 0; j < row.length; j++) {
            if (row[j].move == WHITE) {
                white++;
                black = 0;
            } else if (row[j].move == BLACK) {
                black++;
                white = 0;
            } else {
                black = 0;
                white = 0;
            }
            if (white == 5) {
                return WHITE;
            } else if (black == 5) {
                return BLACK;
            }
        }
        return 0;
    }

    public ArrayList<Node> evaluateThreats(Node move) {
        ArrayList<Node> oldThreats = new ArrayList<>();
        int threatValue = 0;
        for (Node[] row : getAffectingRows(move)) {
            for (int i = 0; i < row.length - Gomoku.WIN_LENGTH + 1; i ++) {
                threatValue = checkThreat(row, i);
                if (threatValue != row[i].threat) {
                    Node old = new Node(row[i]);
                    oldThreats.add(old);
                    row[i].threat = threatValue;
                }
            }
        }
        return oldThreats;
    }

    public void backThreats(ArrayList<Node> oldThreats) {
        for (Node node : oldThreats) {
            board[0][node.x][node.y].threat = node.threat;
        }
    }

    int heuristicValue(int player) {
        return player * expanded.stream().mapToInt(Node::getThreat).sum();
    }

    private int checkThreat(Node[] row, int index) {
        int result = 0, black = 0, white = 0;
        for (int i = index; i < Gomoku.WIN_LENGTH; i++) {
            if (row[i].move == WHITE)
                white++;
            else if (row[i].move == BLACK)
                black--;
            if (black != 0 && white!= 0)
                return 0;
        }
        if (white > 2) {
            result = white;
        } else if (black < 2) {
            result = black;
        }
        return result;
    }

    private ArrayList<Node[]> getAffectingRows(Node node) {
        int diagonalIndex = 0;
        ArrayList<Node[]> result = new ArrayList<>();
        result.add(board[0][node.x]);
        result.add(board[3][node.y]);
        diagonalIndex = node.rightDiagonalRowIndex();
        if (diagonalIndex > 0 && diagonalIndex < board[1].length)
            result.add(board[1][diagonalIndex]);
        diagonalIndex = node.leftDiagonalRowIndex();
        if (diagonalIndex > 0 && diagonalIndex < board[2].length)
            result.add(board[2][diagonalIndex]);
        return result;
    }

    void move(Node node) {
        node.move = whitePlayer ? WHITE : BLACK;
        whitePlayer = !whitePlayer;
    }

    void move(int x, int y) {
        move(board[0][x][y]);
    }

    void back(Node node) {
        node.move = NO_MOVE;
        whitePlayer = !whitePlayer;
    }

    List<Node> getMoves() {
        List<Node> moves = expanded
                .stream()
                .filter(node -> node.move != NO_MOVE)
                .collect(Collectors.toList());
        List<Node> result =  expanded.stream()
                .filter(node -> node.move == NO_MOVE)
                .filter(node -> moves.stream().anyMatch(node::isInCircle))
                .collect(Collectors.toList());
        Collections.shuffle(result);
        return result;
    }

    void print() {
        ArrayUtils.print(board[0], WHITE, BLACK);
    }
}
