package MinMax;


public class ArrayUtils {

    //only works for square arrays
    public static int[][] leftDiagonals(int[][] array, int shortest) {
        int result[][] = new int[(2 * array.length) - (2 * shortest)][];
        int result_i = 0, result_j = 0;
        for (int source_i = shortest - 1; source_i < array[0].length; source_i++) {
            int x = 0, y = source_i;
            result[result_i] = new int[source_i + 1];

            while(x < array.length && y >= 0) {

            }
        }
    return result;
    }
}
