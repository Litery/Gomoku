package minmax;

import java.util.List;

class MinMax {
    private Gomoku problem;

    MinMax(Gomoku problem) {
        this.problem = problem;
    }

    Node bestMove(int depth, int player) {
        int bestValue = Integer.MIN_VALUE, result;
        Node bestMove = null;
        printMoves(problem.getMoves());
        for (Node node : problem.getMoves()) {
            result = -negaMax(node, depth - 1, -player);
            if (result > bestValue) {
                bestValue = result;
                bestMove = node;
            }
        }
        return bestMove;
    }

    private int negaMax(Node move, int depth, int player) {
        problem.move(move);
        int winningPlayer = problem.getWinningPlayer(move);
        if (winningPlayer != 0) {
            problem.back(move);
            return Integer.MAX_VALUE;
        }
        if (depth == 0) {
            problem.back(move);
            return problem.heuristicValue(player);
        }
        int bestValue = Integer.MIN_VALUE;
        for (Node node : problem.getMoves()) {
            bestValue = Integer.max(bestValue, -negaMax(node, depth - 1, -player));
        }
        problem.back(move);
        return bestValue;
    }

    private void printMoves(List<Node> moves) {
        System.out.println();
        for (Node node : moves) {
            System.out.print("(" + node.x + ", " + node.y + "), ");
        }
        System.out.println();
    }
}
