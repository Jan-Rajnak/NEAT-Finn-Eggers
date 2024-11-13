package genome;

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
}
