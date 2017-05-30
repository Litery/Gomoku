package minmax;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Gomoku {
    private Node[][][] board = new Node[4][][];
    private List<Node> expanded;
    private boolean whitePlayer = true;

    public static final int WHITE = 1;
    public static final int BLACK = -1;
    public static final int NO_MOVE = 0;
    public static final int WIN_LENGTH = 5;
    public static final int BOARD_SIZE = 15;

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
//        System.out.println(move.x + " " + move.y);
        int result = checkContinuous(board[0][move.x]);
        int diagonal_row = 0;
        if (result == 0)
            result = checkContinuous(board[3][move.y]);
        diagonal_row = move.rightDiagonalRowIndex();
//        System.out.println("rd" + diagonal_row);
        if (result == 0 && diagonal_row > 0 && diagonal_row < board[1].length)
            result = checkContinuous(board[1][diagonal_row]);
        diagonal_row = move.leftDiagonalRowIndex();
//        System.out.println("ld" + diagonal_row);
        if (result == 0 && diagonal_row > 0 && diagonal_row < board[2].length)
            result = checkContinuous(board[2][diagonal_row]);
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

    int heuristicValue(int player) {
        int[][] players = {{WHITE, 0}, {BLACK, 0}};
        for (int[] color : players) {
            for (Node[][] board : board) {
                for (Node[] row : board) {
                    int j = 0;
                    while (j < row.length - WIN_LENGTH + 1) {
                        checkForward(row, color, j);
                        j++;
                    }
                }
            }
        }
//        System.out.println(players[0][1]);
//        System.out.println(players[1][1]);
        return (player == 1) ? ((players[0][1]) - (players[1][1])) : ((players[1][1]) - (players[0][1]));
    }

    private int checkForward(Node[] row, int[] color, int index) {
        int result = 0, counter = -1;
        for (int i = index; i >= 0 && i < index + WIN_LENGTH && i < row.length; i++) {
            if (row[i].move == color[0]) {
                counter += 1;
                if (result == 0 && i != index) {
                    result = i;
                }
            } else if (row[i].move == -color[0]) {
                result = i + 1;
                if (counter > 1) {
                    //checkForward(row, color, i - WIN_LENGTH);
                }
                return result;
            }
        }
        if (counter == 2) {
            color[1] += counter;
        } else if (counter == 3) {
            color[1] += 5;
        } else if (result == 0) {
            result = index + WIN_LENGTH;
        }
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
        node.move = 0;
        whitePlayer = !whitePlayer;
    }

    List<Node> getMoves() {
        List<Node> moves = expanded
                .stream()
                .filter(node -> node.move != 0)
                .collect(Collectors.toList());
        List<Node> result =  expanded.stream()
                .filter(node -> node.move == 0)
                .filter(node -> moves.stream().anyMatch(node::isInCircle))
                .collect(Collectors.toList());
        Collections.shuffle(result);
        return result;
    }

    void print() {
        ArrayUtils.print(board[0], WHITE, BLACK);
    }
}
