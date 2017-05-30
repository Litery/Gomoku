package minmax;

import minmax_v2.Node;

import java.util.List;
import java.util.stream.Collectors;

class Gomoku {
    final int WHITE = 1;
    final int BLACK = -1;
    final int NO_MOVE = 0;
    final int WIN_LENGTH = 5;
    final int BOARD_SIZE = 15;

    Node[][][] board = new Node[4][][];
    List<Node> expanded;

    boolean white_player = true;

    Gomoku() {
        board[0] = ArrayUtils.emptyBoard(BOARD_SIZE);
        board[1] = ArrayUtils.rightDiagonals(board[0], WIN_LENGTH);
        board[2] = ArrayUtils.leftDiagonals(board[0], WIN_LENGTH);
        board[3] = ArrayUtils.transposed(board[0]);
        expanded = ArrayUtils.expanded(board[0]);
    }

    int getWinner() {
        int result = 0;
        for (Node[][] array : board) {
            result = checkContinuous(array);
            if (result != 0) {
                break;
            }
        }
        return result;
    }

    void print() {
        ArrayUtils.print(board[0], WHITE, BLACK);
    }

    void printAll() {
        ArrayUtils.print(board[0], WHITE, BLACK);
        ArrayUtils.print(board[3], WHITE, BLACK);
        ArrayUtils.print(board[1], WHITE, BLACK);
        ArrayUtils.print(board[2], WHITE, BLACK);
    }

    private int checkContinuous(Node[][] array) {
        for (int i = 0; i < array.length; i++) {
            int black = 0;
            int white = 0;
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j].move == WHITE) {
                    white++;
                } else if (array[i][j].move == BLACK) {
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
        return player == 1 ? players[0][1] - players[1][1] : players[1][1] - players[0][1];
    }

    private int checkForward(Node[] row, int[] player, int startIndex) {
        int nextToCheck = 0, counter = -1;
        for (int i = startIndex; i >= 0 && i < startIndex + WIN_LENGTH && i < row.length; i ++) {
            if (row[i].move == player[0]) {
                counter += 1;
                if (nextToCheck == 0 && i != startIndex) {
                    nextToCheck = i;
                }
            } else if (row[i].move == -player[0]) {
                nextToCheck = i + 1;
                if (counter > 1) {
                    checkForward(row, player, i - WIN_LENGTH);
                }
                return nextToCheck;
            }
        }
        if (counter > 1) {
            player[1] *= counter;
        } else if (nextToCheck == 0) {
            nextToCheck = startIndex + WIN_LENGTH;
        }
        return nextToCheck;
    }

    List<Node> getMoves() {
        return expanded.stream().filter(node -> node.move == 0).collect(Collectors.toList());
    }

    void move(int[] pos) {
        move(pos[0], pos[1]);
    }

    void move(int x, int y) {
        board[0][x][y].move = white_player ? WHITE : BLACK;
        white_player = !white_player;
    }


    void move(Node node) {
        node.move = white_player ? WHITE : BLACK;
        white_player = !white_player;
    }

    void back(Node node) {
        node.move = NO_MOVE;
        white_player = !white_player;
    }
}