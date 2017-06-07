package minmax;


public class GomokuH1 extends Gomoku{
    protected int heuristicValue(int player) {
        int[] values = super.heuristicValues(player);
        return (player == WHITE) ? values[0] : values[1];
    }
}
