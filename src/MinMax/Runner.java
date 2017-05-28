package MinMax;


import java.util.Scanner;

public class Runner {

    static void play() {
        Gomoku gomoku = new Gomoku();
        MinMax minMax = new MinMax(gomoku);
        Scanner in = new Scanner(System.in);
        while (gomoku.isWon() == 0) {
            int[] pos = {0, 0};
            for (int i = 0; i < 2 && in.hasNextInt(); i++) {
                pos[i] = in.nextInt();
            }
            gomoku.move(pos);
            gomoku.print();
            System.out.println(gomoku.heuristicValue(true));
        }
    }

    static void playMM() {
        Gomoku gomoku = new Gomoku();
        MinMax minMax = new MinMax(gomoku);
        Scanner in = new Scanner(System.in);
        while (gomoku.isWon() == 0) {
            int[] pos = {0, 0};
            for (int i = 0; i < 2 && in.hasNextInt(); i++) {
                pos[i] = in.nextInt();
            }
            gomoku.move(pos);
            gomoku.print();
            System.out.println(gomoku.heuristicValue(true));
            if(gomoku.isWon() != 0) {
                break;
            }
            Node node = minMax.chooseNode(3, false);
            gomoku.move(node.x, node.y);
            gomoku.print();
        }
    }

    static void playProfile() {
        Gomoku gomoku = new Gomoku();
        MinMax minMax = new MinMax(gomoku);
        int i = 0;
        while (i++ < 4) {
            int[] pos = {i, i};
            gomoku.move(pos);
            gomoku.print();
            Node node = minMax.chooseNode(5, false);
            gomoku.move(node.x, node.y);
            gomoku.print();
        }
    }

    public static void test1() {
        Gomoku gomoku = new Gomoku();
        for (Node[][] array : gomoku.board)
            ArrayUtils.print(array, gomoku.WHITE, gomoku.BLACK);

        System.out.println(gomoku.isWon());
    }


    public static void main(String[] args) {
        playProfile();
    }
}
