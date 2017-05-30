package minmax;

class Game {
    private Gomoku gomoku;
    private MinMax minMax;

    Game() {
        gomoku = new Gomoku();
        minMax = new MinMax(gomoku);
        GomokuApp.setOnSquareClickListener(this::playMM);
    }

    private void playMM(int x, int y) {
        gomoku.move(x, y);
        GomokuApp.addPiece(1, x, y);
        Node node = minMax.bestMove(3, -1);
        System.out.println(node + " ");
        gomoku.move(node.x, node.y);
        GomokuApp.addPiece(-1, node.x, node.y);
    }

}
