package MinMax;

public class MinMax {
    Gomoku problem;

    public MinMax(Gomoku problem) {
        this.problem = problem;
    }

    Node chooseNode(int depth) {
        float result = 0, tmp;
        Node bestNode = null;
        result = Float.MIN_VALUE;
        for (Node node : problem.getMoves()) {
            problem.move(node);
            if ((tmp = minMax(depth - 1, false)) > result) {
                result = tmp;
                bestNode = node;
            }
            problem.back(node);
        }
        return bestNode;
    }

    float minMax(int depth, boolean maxPlayer) {
        if (problem.isWon() != 0) {
            return Integer.MIN_VALUE;
        } else if (depth == 0) {
            return problem.heuristicValue(maxPlayer);
        }

        float result = 0;
        if (maxPlayer) {
            result = Float.MIN_VALUE;
            for (Node node : problem.getMoves()) {
                problem.move(node);
                result = Float.max(result, minMax(depth - 1, !maxPlayer));
                problem.back(node);
            }
        } else {
            result = Integer.MAX_VALUE;
            for (Node node : problem.getMoves()) {
                problem.move(node);
                result = Float.min(result, minMax(depth - 1, !maxPlayer));
                problem.back(node);
            }
        }

        return result;
    }

}
