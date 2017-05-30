package minmax;

import minmax_v2.Node;

public class MinMax {
    Gomoku problem;
    int player;

    public MinMax(Gomoku problem) {
        this.problem = problem;
    }

    Node chooseNode(int depth, int player) {
        int result = Integer.MIN_VALUE, tmp;
        Node bestNode = null;
        this.player = player;
        System.out.println(problem.getMoves().isEmpty());
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

    int minMax(int depth, boolean isMaximizingPlayer) {
        int winner = problem.getWinner();
        if (winner != 0) {
            return (20000);
        } else if (depth == 0) {
            return problem.heuristicValue(player);
        }

        int result = 0;
        if (isMaximizingPlayer) {
            result = Integer.MIN_VALUE;
            for (Node node : problem.getMoves()) {
                problem.move(node);
                result = Integer.max(result, minMax(depth - 1, !isMaximizingPlayer));
                problem.back(node);
//                if (depth > 2)
//                    System.out.println("    " + node.x + " " + node.y + " " + node.move + " " + tmp);
            }
        } else {
            result = Integer.MAX_VALUE;
            for (Node node : problem.getMoves()) {
                problem.move(node);
                result = Integer.min(result, minMax(depth - 1, !isMaximizingPlayer));
                problem.back(node);
//                if (depth > 2)
//                    System.out.println("    " + node.x + " " + node.y + " " + node.move + " " + tmp);
            }
        }

        return result;
    }

    int alfaBeta(int depth, boolean maxPlayer, int alfa, int beta) {
        if (problem.getWinner() != 0) {
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
