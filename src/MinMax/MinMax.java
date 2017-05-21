package MinMax;

public class MinMax {
    Gomoku problem;

    public MinMax(Gomoku problem) {
        this.problem = problem;
    }

    int minMax(int depth, boolean maxPlayer) {
        if (problem.isWon() != 0) {
            return Integer.MIN_VALUE;
        } else if (depth == 0) {
            return problem.heuristicValue(maxPlayer);
        }

        int result = 0;
        if (maxPlayer) {
            result = Integer.MIN_VALUE;
            for (Gomoku.Move move : problem.getMoves()) {
                problem.move(move);
                result = Integer.max(result, minMax(depth - 1, !maxPlayer));
                problem.back(move);
            }
        } else {
            result = Integer.MAX_VALUE;
            for (Gomoku.Move move : problem.getMoves()) {
                problem.move(move);
                result = Integer.min(result, minMax(depth - 1, !maxPlayer));
                problem.back(move);
            }
        }

        return result;
    }

}
