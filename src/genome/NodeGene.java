package genome;

import org.w3c.dom.Node;

import java.util.HashMap;

import static lib.util.StringOps.*;

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

    public String getType(){
        if (x == 0.1){
            return "input";
        } else if (x == 0.9){
            return "output";
        } else if (x == 0.15){
            return "bias";
        }else{
            return "hidden";
        }
    }

    @Override
    public String toString() {
//        return "NodeGene{" +
//                "x=" + x +
//                ", y=" + y +
//                ", id=" + getInnovationNumber() +
//                ", value=" + value +
//                '}';

        return "NodeGene{id=" + getInnovationNumber() + "; x=" + formatDouble(x, 3) +'}';
    }
}

