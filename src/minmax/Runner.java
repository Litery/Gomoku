package minmax;

import minmax.*;

import java.util.Scanner;

public class Runner {

    private static void playMM() {
        Gomoku gomoku = new Gomoku();
        MinMax minMax = new MinMax(gomoku);
        Scanner in = new Scanner(System.in);
        while (gomoku.getWinningPlayer() == 0) {
            int[] pos = {0, 0};
            for (int i = 0; i < 2 && in.hasNextInt(); i++) {
                pos[i] = in.nextInt();
            }
            gomoku.move(pos[0], pos[1]);
            gomoku.print();
            System.out.println(gomoku.heuristicValue(1));
            if(gomoku.getWinningPlayer() != 0) {
                break;
            }
            Node node = minMax.bestMove(-1);
            gomoku.move(node);
            gomoku.print();
        }
    }

    static double h1vsh1() {
        int player = 1;
        int[] sides = {-1, 1};
        double[] results = {0, 0};
        for (int side : sides) {
            for (int i = 0; i < 10; i++) {
                Gomoku[] gomoku = new Gomoku[2];
                MinMax[] minMax = new MinMax[2];
                gomoku[0] = new Gomoku();
                gomoku[1] = new Gomoku();
                minMax[0] = new MinMax(gomoku[0], 4, true);
                minMax[1] = new MinMax(gomoku[1], 3, true);
                gomoku[0].move(8, 8);
                gomoku[1].move(8, 8);
                player = -player;
                int ai_index=0;
                while (gomoku[0].getWinningPlayer() == 0) {
                    ai_index = 0;
                    if (player == Gomoku.BLACK * side)
                        ai_index = 1;
                    Node node = minMax[ai_index].bestMove(player);
                    gomoku[0].move(node.x, node.y);
                    gomoku[1].move(node.x, node.y);
                    player = -player;
//                    gomoku[0].print();
                }
                results[ai_index] ++;
            }
        }
        return results[0] / (results[1] + results[0]);
    }

    static double h1vsh2() {
        int player = 1;
        int[] sides = {-1, 1};
        double[] results = {0, 0};
        for (int side : sides) {
            for (int i = 0; i < 10; i++) {
                Gomoku[] gomoku = new Gomoku[2];
                MinMax[] minMax = new MinMax[2];
                gomoku[0] = new Gomoku();
                gomoku[1] = new Gomoku();
                minMax[0] = new MinMax(gomoku[0], 4, true);
                minMax[1] = new MinMax(gomoku[1], 3, true);
                gomoku[0].move(8, 8);
                gomoku[1].move(8, 8);
                player = -player;
                int ai_index=0;
                while (gomoku[0].getWinningPlayer() == 0) {
                    ai_index = 0;
                    if (player == Gomoku.BLACK * side)
                        ai_index = 1;
                    Node node = minMax[ai_index].bestMove(player);
                    gomoku[0].move(node.x, node.y);
                    gomoku[1].move(node.x, node.y);
                    player = -player;
//                    gomoku[0].print();
                }
                results[ai_index] ++;
            }
        }
        return results[0] / (results[1] + results[0]);
    }


    public static void main(String[] args) {
        System.out.println(h1vsh1());
    }
}
