package genome;

import java.util.HashMap;

public class NodeGene extends Gene{

    protected double x, y;
    private double value = 0;
    // TODO: Remove after debugging
    private boolean visited = false;

    public NodeGene(int innovationNumber){
        super(innovationNumber);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equals(NodeGene other) {
        return this.getInnovationNumber() == other.getInnovationNumber();
    }

    public double getValue() {
        if (!visited){
            throw new RuntimeException("Node not visited");
        }
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double activate(){
        visited = true;
        return 1 / (1 + Math.exp(-value));
    }

    // TODO: Remove after debugging
    public boolean isVisited() {
        return visited;
    }

    public void visit(){
        if (visited){
            throw new RuntimeException("Node already visited");
        }
        visited = true;
    }

    @Override
    public String toString() {
        return "NodeGene{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + getInnovationNumber() +
                ", value=" + value +
                '}';
    }
}

