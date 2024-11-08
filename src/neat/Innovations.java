package neat;

import genome.ConnectionGene;

import java.util.Set;
import java.util.TreeMap;

public class Innovations {
    private final TreeMap<Integer, ConnectionGene> innovations = new TreeMap<>();
    private int current_innovation = 0;

    public Innovations(){}

    public ConnectionGene get(int innovation){
        return innovations.get(innovation);
    }

    private ConnectionGene create(ConnectionGene gene){
        current_innovation++;
        gene.setInnovationNumber(current_innovation);
        gene.setWeight(0);
        gene.setEnabled(true);
        innovations.put(current_innovation, gene);
        return gene;
    }

    public ConnectionGene getConnection(ConnectionGene gene){
        for (ConnectionGene g : innovations.values()){
            if (g.equals(gene)){
                return g;
            }
        }
        return create(gene);
    }

    public int size(){
        return innovations.size();
    }

    public void print(){
        Set<Integer> keys = innovations.keySet();
        for (Integer key : keys){
            System.out.println(key + " : " + innovations.get(key));
        }
    }

}
