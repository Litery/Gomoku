package minmax_v2;


import java.util.Stack;

public class Window {
    private int space = 2;
    Stack<Integer> x_min = new Stack<>();
    Stack<Integer> y_min = new Stack<>();
    Stack<Integer> x_max = new Stack<>();
    Stack<Integer> y_max = new Stack<>();

    public Window(int x_min, int y_min, int x_max, int y_max) {
        this.x_min.push(x_min);
        this.y_min.push(y_min);
        this.x_max.push(x_max);
        this.y_max.push(y_max);
    }

    public boolean isWithin(int x, int y) {
        return x >= x_min.peek() && x <= x_max.peek() && y >= y_min.peek() && y <= y_max.peek();
    }
    public boolean isWithin(Node node) {
        return isWithin(node.x, node.y);
    }

    public void widen(int x, int y) {
        x_max.push( x + space > x_max.peek()? x + space : x_max.peek());
        x_min.push(x - space < x_min.peek()? x - space : x_min.peek());
        y_max.push(y + space > y_max.peek() ? y + space : y_max.peek());
        y_min.push(y - space < y_min.peek() ? y - space : y_min.peek());
    }

    public void narrow() {
        x_min.pop();
        x_max.pop();
        y_min.pop();
        y_max.pop();
    }
}
