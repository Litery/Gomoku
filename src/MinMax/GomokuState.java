package MinMax;

import java.util.HashSet;
import java.util.stream.Stream;

public class GomokuState {
    HashSet<Threat> threats = new HashSet<Threat>();
    int x_max, x_min, y_max, y_min;

    public GomokuState(int x_max, int x_min, int y_max, int y_min) {
        this.x_max = x_max;
        this.x_min = x_min;
        this.y_max = y_max;
        this.y_min = y_min;
    }

    public GomokuState(GomokuState gs) {
        this.x_max = gs.x_max;
        this.x_min = gs.x_min;
        this.y_max = gs.y_max;
        this.y_min = gs.y_min;
        gs.threats.stream().map( threat -> this.threats.add(new Threat(threat)));
    }
}
