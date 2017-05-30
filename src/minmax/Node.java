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
        return x + y - Gomoku.WIN_LENGTH + 1;
    }

    int leftDiagonalRowIndex() {
        return leftFiagonalRowIndex(x, y);
    }

    private static int leftFiagonalRowIndex(int x, int y) {
        return y - x + (Gomoku.BOARD_SIZE - Gomoku.WIN_LENGTH);
    }

    boolean isInCircle(Node node) {
        return (node.x - x) * (node.x - x) + (node.y - y) * (node.y - y) <= 5;
    }

}
