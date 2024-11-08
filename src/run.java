import lib.visual.Frame;
import neat.Neat;

public class run {
    public static void main(String[] args) {
        Neat neat = new Neat(3,2,0);
        new Frame(neat.emptyGenome());
    }
}
