package genome;

public class NodeGene extends Gene{

    private double x, y;

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

    @Override
    public String toString() {
        return "NodeGene{" +
                "x=" + x +
                ", y=" + y +
                ", innovationNumber=" + getInnovationNumber() +
                '}';
    }
}

