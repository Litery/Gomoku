package MinMax;


public class Threat {
    //types 0, 1, 2, 3 - rows, rdiag, ldiag, cols
    protected int type, row, column, player, level;
    public Threat(int type, int row, int column, int player, int level) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.player = player;
        this.level = level;
    }

    public Threat(Threat t) {
        this.type = t.type;
        this.row = t.row;
        this.column = t.column;
        this.player = t.player;
        this.level = t.level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Threat threat = (Threat) o;

        if (type != threat.type) return false;
        if (row != threat.row) return false;
        if (column != threat.column) return false;
        if (player != threat.player) return false;
        return level == threat.level;
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + row;
        result = 31 * result + column;
        result = 31 * result + player;
        result = 31 * result + level;
        return result;
    }
}
