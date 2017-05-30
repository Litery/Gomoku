package minmax;


class Node {
    int move = 0, x, y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int rightDiagonalRowIndex() {
        return rightDiagonalRowIndex(x, y);
    }

    private static int rightDiagonalRowIndex(int x, int y) {
        return x + y - Gomoku.WIN_LENGTH;
    }

    int leftDiagonalRowIndex() {
        return leftFiagonalRowIndex(x, y);
    }

    private static int leftFiagonalRowIndex(int x, int y) {
        return y - x + Gomoku.BOARD_SIZE;
    }
}
