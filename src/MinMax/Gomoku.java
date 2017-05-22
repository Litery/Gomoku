package MinMax;

import java.util.ArrayList;
import java.util.Arrays;
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

    int isWon() {
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

    float heuristicValue(boolean player) {
        float[][] players = {{WHITE, 1}, {BLACK, 1}};
        for (float[] color : players) {
            for (Node[][] board : board) {
                for (Node[] row : board) {
                    int space_counter = 0;
                    int counter = -1;
                    for (int j = 0; j < row.length; j++) {
                        if (row[j].move == NO_MOVE) {
                            space_counter += 1;
                        } else if (row[j].move == color[0]) {
                            counter += 1;
                            space_counter += 1;
                        } else if (space_counter >= 5 && counter > 1) {
                            color[1] *= counter;
                            space_counter = 0;
                            counter = -1;
                            ArrayUtils.print(row, WHITE, BLACK);
                            System.out.println("in");
                        } else {
                            space_counter = 0;
                            counter = -1;
                        }
                    }
                    if (space_counter >= 5 && counter > 1) {
                        color[1] *= counter;
                        ArrayUtils.print(row, WHITE, BLACK);
                        System.out.println("out");
                    }
                }
            }
        }
        return player ? players[0][1] / players[1][1] : players[1][1] / players[0][1];
    }

    List<Node> getMoves() {
        return expanded.stream().filter(node -> node.move == 0).collect(Collectors.toList());
    }

    void move(int[] pos) {
        board[0][pos[0]][pos[1]].move = white_player ? WHITE : BLACK;
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

class Node {
    int move = 0, x, y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}