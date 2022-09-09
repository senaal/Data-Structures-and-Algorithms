import java.util.ArrayList;

public class Node {
    private int height;
    private int capacity;
    private String type;
    private int excessFlow;
    private ArrayList<Edge> edges = new ArrayList<>();
    private boolean relabeled = false;

    public Node(int height, int capacity) {
        this.height = height;
        this.capacity = capacity;
    }
    public Node(String type, int height, int capacity) {
        this.height = height;
        this.capacity = capacity;
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getExcessFlow() {
        return excessFlow;
    }

    public void setExcessFlow(int excessFlow) {
        this.excessFlow = excessFlow;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setRelabeled(boolean relabeled) {
        this.relabeled = relabeled;
    }
}
