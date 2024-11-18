package genome;

import static lib.util.StringOps.*;

public class BiasNodeGene extends NodeGene{
    private double bias;

    public BiasNodeGene(int innovationNumber, double bias) {
        super(innovationNumber);
        this.bias = bias;
    }

    @Override
    public void setValue(double value) {}

    public void setBias(double bias) {
        this.bias = bias;
    }

    @Override
    public double getValue() {
        return bias;
    }

    @Override
    public double activate() {
        return bias;
    }

    @Override
    public void weightShift(double WEIGHT_SHIFT_STRENGTH) {
        bias += (Math.random() * 2 - 1) * WEIGHT_SHIFT_STRENGTH;
    }

    @Override
    public void weightRandom() {
        bias = Math.random() * 2 - 1;
    }

    @Override
    public String toString() {
//        return "BiasNodeGene{" +
//                "innovationNumber=" + innovationNumber +
//                ", bias=" + bias +
//                '}';
        return "NodeGene{id=" + getInnovationNumber() + "; bias=" + formatDouble(bias, 5)+'}';
    }
}
