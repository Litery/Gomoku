package MinMax;

public class MinMax {
    Gomoku problem;
    boolean player;

    public MinMax(Gomoku problem) {
        this.problem = problem;
    }

    Node chooseNode(int depth, boolean player) {
        int result = 0, tmp;
        Node bestNode = null;
        this.player = player;
        result = Integer.MIN_VALUE;
        for (Node node : problem.getMoves()) {
            problem.move(node);
            if ((tmp = minMax(depth - 1, false)) > result) {
                result = tmp;
                bestNode = node;
            }
            problem.back(node);
//            System.out.println(node.x + " " + node.y + " " + tmp);
        }
        return bestNode;
    }

    int minMax(int depth, boolean maxPlayer) {
        if (problem.isWon() != 0) {
            return Integer.MIN_VALUE;
        } else if (depth == 0) {
            return problem.heuristicValue(player);
        }

        int result = 0, tmp = 0;
        if (maxPlayer) {
            result = Integer.MIN_VALUE;
            for (Node node : problem.getMoves()) {
                problem.move(node);
                result = Integer.max(result, tmp = minMax(depth - 1, !maxPlayer));
                problem.back(node);
//                if (depth > 2)
//                    System.out.println("    " + node.x + " " + node.y + " " + node.move + " " + tmp);
            }
        } else {
            result = Integer.MAX_VALUE;
            for (Node node : problem.getMoves()) {
                problem.move(node);
                result = Integer.min(result, tmp = minMax(depth - 1, !maxPlayer));
                problem.back(node);
//                if (depth > 2)
//                    System.out.println("    " + node.x + " " + node.y + " " + node.move + " " + tmp);
            }
        }

        return result;
    }

}
