package minmax;

class Game {
    enum Type {PLAYER_VS_AI, AI_VS_PLAYER, AI_VS_AI}

    private Gomoku gomoku;
    private MinMax minMax;
    private int player = 1;
    private Type type = Type.PLAYER_VS_AI;

    Game(Type type) {
        this.type = type;
        gomoku = new Gomoku();
        minMax = new MinMax(gomoku);
    }

    public void start() {
        switch (type) {
            case PLAYER_VS_AI:
                GomokuApp.setOnSquareClickListener(this::playerMove);
                break;
            case AI_VS_PLAYER:
                gomoku.move(7, 7);
                GomokuApp.addPiece(player, 7, 7);
                player = -player;
                GomokuApp.setOnSquareClickListener(this::playerMove);
                break;
            case AI_VS_AI:
                gomoku.move(7, 7);
                GomokuApp.addPiece(player, 7, 7);
                player = -player;
                GomokuApp.setOnSquareClickListener((x, y) -> aiMove());
                break;
        }
    }

    private void playerMove(int x, int y) {
        gomoku.move(x, y);
        GomokuApp.addPiece(player, x, y);
        checkWinCondition();
        player = - player;
        aiMove();
    }

    private void aiMove() {
        Node node = minMax.bestMove(4, player, true);
        gomoku.move(node.x, node.y);
        GomokuApp.addPiece(player, node.x, node.y);
        player = -player;
        checkWinCondition();
    }

    private void checkWinCondition() {
        int winningPlayer = gomoku.getWinningPlayer();
        if (winningPlayer != 0) GomokuApp.showWinnerDialog(winningPlayer);
    }

    // TODO: fix this shit
    private void aiVsAi(int currentPlayer) {
        Node node = minMax.bestMove(3, currentPlayer, true);
        gomoku.move(node.x, node.y);
        GomokuApp.addPiece(currentPlayer, node.x, node.y);

        int winningPlayer = gomoku.getWinningPlayer();
        if (winningPlayer != 0) GomokuApp.showWinnerDialog(winningPlayer);
        else aiVsAi(-currentPlayer);
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
