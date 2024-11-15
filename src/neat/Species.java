package neat;

import genome.Genome;

import java.util.Comparator;
import java.util.TreeSet;

public class Species {
    private final TreeSet<Genome> members = new TreeSet<>((o1, o2) -> Double.compare(o2.getFitness(), o1.getFitness()));
    private Genome representative;
    private double max_fitness = 0;
    private double average_fitness = 0;
    private int staleness = 0;

    public Species(Genome g){
        this.representative = g;
        this.members.add(g);
    }

    public void add(Genome g){
        members.add(g);
    }

    public boolean put(Genome g){
        if (representative.distance(g) < representative.getNeat().getSPECIES_THRESHOLD()){
            members.add(g);
            return true;
        }
        return false;
    }

    public static void addToSpecies(Genome g, TreeSet<Species> species){
        for (Species s : species){
            if (s.put(g)){
                return;
            }
        }
        species.add(new Species(g));
    }
}
