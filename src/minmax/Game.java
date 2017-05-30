package minmax;

class Game {
    private Gomoku gomoku;
    private MinMax minMax;
    private int player = 1;

    Game() {
        gomoku = new Gomoku();
        minMax = new MinMax(gomoku);
        GomokuApp.setOnSquareClickListener(this::playMM);
    }

    private void playMM(int x, int y) {
        gomoku.move(x, y);
        GomokuApp.addPiece(1, x, y);
        Node node = minMax.bestMove(3, -1, true);
        gomoku.move(node.x, node.y);
        GomokuApp.addPiece(-1, node.x, node.y);
    }

    private void testWinCheck(int x, int y) {
        gomoku.move(y, x);
        GomokuApp.addPiece(player, x, y);
        System.out.println(gomoku.getWinningPlayer(gomoku.getNode(y, x)));
        System.out.println(gomoku.getWinningPlayer());
        gomoku.heuristicValue(1);
        System.out.println();
        player = -player;
    }

}
