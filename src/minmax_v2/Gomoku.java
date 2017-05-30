package minmax_v2;


import minmax.ArrayUtils;
import minmax_v2.Node;

import java.util.List;
import java.util.stream.Collectors;

public class Gomoku {
    Node[][][] board = new Node[4][][];
    List<Node> expanded;
    private Window window = new Window(5, 5, 5, 5);
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
        int result = checkContinuous(board[0][move.x]);
        int diagonal_row = 0;
        if (result == 0)
            result = checkContinuous(board[3][move.y]);
        diagonal_row = move.rightDiagonalRowIndex();
        if (result == 0 && diagonal_row > 0 && diagonal_row < board[1].length)
            result = checkContinuous(board[1][diagonal_row]);
        diagonal_row = move.leftDiagonalRowIndex();
        if (result == 0 && diagonal_row > 0 && diagonal_row < board[2].length)
            result = checkContinuous(board[1][diagonal_row]);
        return result;
    }

    //find 5 consecutive moves in any color on whole board
    private int checkContinuous(Node[][] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
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
            } else if (row[j].move == BLACK) {
                black++;
            } else {
                white = 0;
                black = 0;
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
        int[][] players = {{WHITE, 1}, {BLACK, 1}};
        for (int[] color : players) {
            for (Node[][] board : board) {
                for (Node[] row : board) {
                    int j = 0;
                    while (j < row.length - WIN_LENGTH + 1) {
                        j = checkForward(row, color, j);
                    }
                }
            }
        }
        return player == 1 ? players[0][1] - 2 * players[1][1] : players[1][1] - 2 * players[0][1];
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
                    checkForward(row, color, i - WIN_LENGTH);
                }
                return result;
            }
        }
        if (counter > 1) {
            color[1] *= counter;
        } else if (result == 0) {
            result = index + WIN_LENGTH;
        }
        return result;
    }

    void move(Node node) {
        node.move = whitePlayer ? WHITE : BLACK;
        window.widen(node.x, node.y);
        whitePlayer = !whitePlayer;
    }

    void move(int x, int y) {
        move(board[0][x][y]);
    }

    void back(Node node) {
        node.move = 0;
        window.narrow();
        whitePlayer = !whitePlayer;
    }

    List<Node> getMoves() {
        return expanded.stream()
                .filter(node -> node.move == 0)
                .filter(window::isWithin)
                .collect(Collectors.toList());
    }


    void print() {
        ArrayUtils.print(board[0], WHITE, BLACK);
    }
}
