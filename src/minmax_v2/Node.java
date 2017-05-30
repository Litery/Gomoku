package minmax_v2;

public class Node {
    public int move = 0, x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int rightDiagonalRowIndex() {
        return rightDiagonalRowIndex(x, y);
    }

    public static int rightDiagonalRowIndex(int x, int y) {
        return x + y - Gomoku.WIN_LENGTH;
    }

    public int leftDiagonalRowIndex() {
        return leftFiagonalRowIndex(x, y);
    }

    public static int leftFiagonalRowIndex(int x, int y) {
        return y - x + Gomoku.BOARD_SIZE;
    }
}
