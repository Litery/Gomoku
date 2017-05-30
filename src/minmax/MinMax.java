package minmax;

import java.util.List;

class MinMax {
    private Gomoku problem;

    MinMax(Gomoku problem) {
        this.problem = problem;
    }

    Node bestMove(int depth, int player, boolean alfabeta) {
        if (alfabeta)
            return chooseMoveAlfaBeta(depth, player);
        else
            return chooseMove(depth, player);
    }

    private Node chooseMove(int depth, int player) {
        int bestValue = Integer.MIN_VALUE, result;
        Node bestMove = null;
        for (Node node : problem.getMoves()) {
            result = -negaMax(node, depth - 1, -player);
            if (result > bestValue) {
                bestValue = result;
                bestMove = node;
            }
        }
        return bestMove;
    }

    private Node chooseMoveAlfaBeta(int depth, int player) {
        int bestValue = Integer.MIN_VALUE, result;
        Node bestMove = null;
        for (Node node : problem.getMoves()) {
            result = -negaMax(node, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, -player);
            System.out.println(node.x + " " + node.y + " " + result);
            if (result >= bestValue) {
                bestValue = result;
                bestMove = node;
            }
        }
        System.out.println("\n" + bestMove.x + " " + bestMove.y + " " + bestValue);
        return bestMove;
    }



    private int negaMax(Node move, int depth, int player) {
        problem.move(move);
        int winningPlayer = problem.getWinningPlayer(move);
        if (winningPlayer != 0) {
            problem.back(move);
            return (Integer.MAX_VALUE - 1) * -player;
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

    private int negaMax(Node move, int depth, int alfa, int beta, int player) {
        problem.move(move);
        int winningPlayer = problem.getWinningPlayer(move);
        if (winningPlayer != 0) {
            problem.back(move);
            return (Integer.MAX_VALUE - 1) * -player;
        }
        if (depth == 0) {
            problem.back(move);
            return problem.heuristicValue(player);
        }
        int bestValue = Integer.MIN_VALUE, result = 0;
        for (Node node : problem.getMoves()) {
            result = -negaMax(node, depth - 1, -beta, -alfa, -player);
            bestValue = Integer.max(bestValue, result);
            alfa = Integer.max(alfa, result);
            if (alfa >= beta)
                break;
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
