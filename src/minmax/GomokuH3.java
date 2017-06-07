package minmax;


public class GomokuH3 extends Gomoku {

    protected int[] heuristicValues(int player) {
        int[][] players = {{WHITE, 1}, {BLACK, 1}};
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
        return new int[]{players[0][1], players[1][1]};
    }

    protected int heuristicValue(int player) {
        int[] values = heuristicValues(player);
        return (player == WHITE) ? ((values[0]) - (values[1])) : ((values[1]) - (values[0]));
    }

    protected int checkForward(Node[] row, int[] color, int index) {
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
            color[1] *= 3;
        } else if (counter == 3) {
            color[1] *= 6;
        } else if (result == 0) {
            result = index + WIN_LENGTH;
        }
        return result;
    }
}
