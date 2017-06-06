package minmax;

import java.util.List;

class MinMax {
    private Gomoku problem;
    private int depth;
    private boolean alpha_beta;
    private long max_search_time;
    private int min_max_calls;

    public int getMin_max_calls() {
        return min_max_calls;
    }

    MinMax(Gomoku problem) {
        this(problem, 4,true);
    }

    MinMax(Gomoku problem, int depth, boolean alpha_beta) {
        this(problem, depth, alpha_beta, 1000);
    }

    MinMax(Gomoku problem, int depth, boolean alpha_beta, long max_search_time) {
        this.problem = problem;
        this.depth = depth;
        this.alpha_beta = alpha_beta;
        this.max_search_time = max_search_time;
    }

    Node bestMove(int player) {
        min_max_calls = 0;
        if (alpha_beta)
            return chooseMoveAlphaBeta(depth, player);
        else
            return chooseMove(depth, player);
    }

    private Node chooseMove(int depth, int player) {
        int bestValue = Integer.MIN_VALUE, result;
        Node bestMove = null;
        long start_time = System.currentTimeMillis();
        for (Node node : problem.getMoves()) {
            result = -negaMax(node, depth - 1, player);
            if (result > bestValue) {
                bestValue = result;
                bestMove = node;
            }
            if (System.currentTimeMillis() - start_time > max_search_time) break;
        }
        return bestMove;
    }

    private Node chooseMoveAlphaBeta(int depth, int player) {
        int bestValue = Integer.MIN_VALUE, result;
        Node bestMove = null;
        long start_time = System.currentTimeMillis();
        for (Node node : problem.getMoves()) {
            result = -negaMax(node, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
            if (result > bestValue) {
                bestValue = result;
                bestMove = node;
            }
            if (System.currentTimeMillis() - start_time > max_search_time) break;
        }
//        System.out.println("\n" + bestMove.x + " " + bestMove.y + " " + bestValue);
//        System.out.println((System.currentTimeMillis() - start_time));
//        System.out.println( min_max_calls / (System.currentTimeMillis() - start_time));
        return bestMove;
    }



    private int negaMax(Node move, int depth, int player) {
        problem.move(move);
        min_max_calls++;
        int winningPlayer = problem.getWinningPlayer(move);
        if (winningPlayer != 0) {
            problem.back(move);
            return (Integer.MIN_VALUE + 1);
        }
        if (depth == 0) {
            problem.back(move);
            return -problem.heuristicValue(player);
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
        min_max_calls++;
        int winningPlayer = problem.getWinningPlayer(move);
        if (winningPlayer != 0) {
            problem.back(move);
            return (Integer.MIN_VALUE + 1);
        }
        if (depth == 0) {
            problem.back(move);
            return -problem.heuristicValue(player);
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
