package minmax;

class Game {
    enum Type {PLAYER_VS_AI, AI_VS_PLAYER, AI_VS_AI}

    private Gomoku[] gomoku = new Gomoku[2];
    private MinMax[] minMax = new MinMax[2];
    private int player = Gomoku.WHITE;
    private Type type = Type.PLAYER_VS_AI;
    private static int X_START = 7, Y_START = 7;

    Game(Type type) {
        this.type = type;
        gomoku[0] = new Gomoku();
        gomoku[1] = new Gomoku();
        minMax[0] = new MinMax(gomoku[0], 4, true);
        minMax[1] = new MinMax(gomoku[1], 4, true);
    }

    void start() {
        switch (type) {
            case PLAYER_VS_AI:
                GomokuApp.setOnSquareClickListener(this::playerMove);
                break;
            case AI_VS_PLAYER:
                move(X_START, Y_START);
                GomokuApp.setOnSquareClickListener(this::playerMove);
                break;
            case AI_VS_AI:
                move(X_START, Y_START);
                GomokuApp.setOnSquareClickListener((x, y) -> aiMove());
                break;
        }
    }

    private void playerMove(int x, int y) {
        move(x, y);
        aiMove();
    }

    private void move(int x, int y) {
        gomoku[0].move(x, y);
        gomoku[1].move(x, y);
        GomokuApp.addPiece(player, x, y);
        player = -player;
        checkWinCondition();
    }

    private void move(Node node) {
        move(node.x, node.y);
    }

    private void aiMove() {
        int ai_index = 0;
        if (player == Gomoku.BLACK)
            ai_index = 1;
        Node node = minMax[ai_index].bestMove(player);
        move(node);
    }

    private void checkWinCondition() {
        int winningPlayer = gomoku[0].getWinningPlayer();
        if (winningPlayer != 0) GomokuApp.showWinnerDialog(winningPlayer);
    }
}
