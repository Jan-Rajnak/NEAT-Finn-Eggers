package genome;

public class BiasNodeGene extends NodeGene{
    private double bias;

    public BiasNodeGene(int innovationNumber, double bias) {
        super(innovationNumber);
        this.bias = bias;
        x = 0.15;
    }

    @Override
    public void setValue(double value) {}

    @Override
    public double activate() {
        return bias;
    }
}
