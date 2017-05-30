package minmax;


import minmax_v2.Node;

import java.util.ArrayList;

public class ArrayUtils {

    //only works for square arrays
    public static Node[][] rightDiagonals(Node[][] array, int min_length) {
        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        ArrayList<Node> row;
        for (int i = min_length - 1; i < array.length; i++) {
            row = new ArrayList<>();
            int x = 0, y = i;
            while (x < array.length && y >= 0) {
                row.add(array[x][y]);
                x += 1;
                y -= 1;
            }
            result.add(row);
        }
        for (int i = 1; i < array.length - min_length + 1; i++) {
            row = new ArrayList<>();
            int x = i, y = array.length - 1;
            while (x < array.length && y >= 0) {
                row.add(array[x][y]);
                x += 1;
                y -= 1;
            }
            result.add(row);
        }
        Node[][] result_array = new Node[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            row = result.get(i);
            result_array[i] = row.toArray(new Node[row.size()]);
        }
        return result_array;
    }

    //only works for square arrays
    public static Node[][] leftDiagonals(Node[][] array, int min_length) {
        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        ArrayList<Node> row;
        for (int i = array.length - min_length; i > 0; i--) {
            row = new ArrayList<>();
            int x = i, y = 0;
            while (x < array.length && y < array.length) {
                row.add(array[x][y]);
                x += 1;
                y += 1;
            }
            result.add(row);
        }
        for (int i = 0; i < array.length - min_length + 1; i++) {
            row = new ArrayList<>();
            int x = 0, y = i;
            while (x < array.length && y < array.length) {
                row.add(array[x][y]);
                x += 1;
                y += 1;
            }
            result.add(row);
        }
        Node[][] result_array = new Node[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            row = result.get(i);
            result_array[i] = row.toArray(new Node[row.size()]);
        }
        return result_array;
    }

    public static Node[][] transposed(Node[][] array) {
        Node[][] result = new Node[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                result[j][i] = array[i][j];
            }
        }
        return result;
    }

    public static ArrayList<Node> expanded(Node[][] array) {
        ArrayList<Node> result = new ArrayList<>(array.length * array.length);
        int x = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                result.add(x++, array[i][j]);
            }
        }
        return result;
    }

    public static void print(Node[][] array, int white, int black) {
        for (Node[] row : array) {
            System.out.println();
            for (Node node : row) {
                System.out.print(node.move == white ? "X" : "");
                System.out.print(node.move == black ? "O" : "");
                System.out.print(node.move == 0 ? "- " : "" + " ");
            }
        }
        System.out.println();
    }

    public static void print(Node[] row, int white, int black) {
        System.out.println();
        for (Node node : row) {
            System.out.print(node.move == white ? "X" : "");
            System.out.print(node.move == black ? "O" : "");
            System.out.print(node.move == 0 ? "- " : "" + " ");
        }
        System.out.println();
    }

    public static Node[][] emptyBoard(int size) {
        Node[][] result = new Node[size][size];
        int x = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = new Node(i, j);
//                if (i - j == 8)
//                    result[i][j].move = x;
            }
        }
        return result;
    }
}
