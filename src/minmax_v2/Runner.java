package minmax_v2;

import minmax.*;

import java.util.Scanner;

public class Runner {

    static void playMM() {
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
            Node node = minMax.bestMove(3, -1);
            gomoku.move(node);
            gomoku.print();
        }
    }

    public static void main(String[] args) {
        playMM();
    }
}
