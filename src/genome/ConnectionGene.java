package genome;

import static lib.util.StringOps.*;

public class ConnectionGene extends Gene{

    private NodeGene from;
    private NodeGene to;

    private double weight;
    private boolean enabled = true;

    public ConnectionGene(NodeGene from, NodeGene to) {
        this.from = from;
        this.to = to;
    }

    public NodeGene getFrom() {
        return from;
    }

    public void setFrom(NodeGene from) {
        this.from = from;
    }

    public NodeGene getTo() {
        return to;
    }

    public void setTo(NodeGene to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void weightShift(double WEIGHT_SHIFT_STRENGTH) {
        weight += (Math.random() * 2 - 1) * WEIGHT_SHIFT_STRENGTH;
    }

    @Override
    public void weightRandom() {
        weight = Math.random() * 2 - 1;
    }

    public boolean equals(ConnectionGene other) {
        return this.from.equals(other.from) && this.to.equals(other.to);
    }

    @Override
    public String toString() {
//        return "ConnectionGene{" +
//                "from=" + from +
//                ", to=" + to +
//                ", weight=" + weight +
//                ", enabled=" + enabled +
//                '}';

        return "ConnectionGene{" + from.getInnovationNumber() + " -> " + to.getInnovationNumber() + " : " + (enabled? formatDouble(weight,3) : "disabled") + '}';
    }
}
