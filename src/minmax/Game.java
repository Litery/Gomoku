package minmax;

import minmax.Gomoku;
import minmax.MinMax;
import minmax_v2.Node;

import java.util.Scanner;

public class Game {
    Gomoku gomoku;
    MinMax minMax;

    public Game() {
        gomoku = new Gomoku();
        minMax = new MinMax(gomoku);
        GomokuApp.setOnSquareClickListener(this::playMM);
    }

    public void playMM(int x, int y) {
        gomoku.move(x, y);
        GomokuApp.addPiece(1, x, y);
        Node node = minMax.chooseNode(2, -1);
        System.out.println(node + " ");
        gomoku.move(node.x, node.y);
        GomokuApp.addPiece(-1, node.x, node.y);
    }

}
