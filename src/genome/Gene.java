package genome;

public class Gene {
    protected int innovationNumber;

    public Gene(int innovationNumber) {
        this.innovationNumber = innovationNumber;
    }

    public Gene(){}

    public int getInnovationNumber() {
        return innovationNumber;
    }

    public void setInnovationNumber(int innovationNumber) {
        this.innovationNumber = innovationNumber;
    }

    protected void weightShift(double WEIGHT_SHIFT_STRENGTH){
        throw new UnsupportedOperationException("This gene does not support weight shifting" + this);
    };

    protected void weightRandom(){
        throw new UnsupportedOperationException("This gene does not support weight randomization" + this);
    }
}
