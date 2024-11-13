package lib.util;

import genome.BiasNodeGene;
import genome.Gene;
import genome.NodeGene;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GeneSet {
    private TreeMap<Integer, Gene> genes = new TreeMap<>();

    public GeneSet(){}

    public GeneSet(List<Gene> genes){
        for (Gene gene : genes){
            this.genes.put(gene.getInnovationNumber(), gene);
        }
    }

    public void add(Gene gene){
        genes.put(gene.getInnovationNumber(), gene);
    }

    public Gene get(int innovationNumber){
        return genes.get(innovationNumber);
    }

    public List<Gene> getGenes(){
        return new ArrayList<>(genes.values());
    }

    public List<NodeGene> getNodeGenes(){
        List<NodeGene> nodes = new ArrayList<>();
        for (Gene gene : genes.values()){
            nodes.add((NodeGene) gene);
        }
        return nodes;
    }

    public boolean contains(Gene gene){
        return genes.containsKey(gene.getInnovationNumber());
    }

    public boolean contains(int innovationNumber){
        return genes.containsKey(innovationNumber);
    }

    public int size(){
        return genes.size();
    }

    public int maxInnovation(){
        return genes.lastKey();
    }

    public Gene remove(int innovationNumber){
        return genes.remove(innovationNumber);
    }

    public Gene remove(Gene gene){
        return genes.remove(gene.getInnovationNumber());
    }

    public Gene random(){
        int index = (int) (Math.random() * genes.size() + 1);
        return genes.get(index);
    }

    public Gene randomNode(List<String> exclude){
        List<NodeGene> nodes = getNodeGenes();
        if (exclude.contains("input")) nodes.removeIf(nodeGene -> nodeGene.getX() == 0.1);
        if (exclude.contains("output")) nodes.removeIf(nodeGene -> nodeGene.getX() == 0.9);
        if (exclude.contains("bias")) nodes.removeIf(nodeGene -> nodeGene instanceof BiasNodeGene);
        if (exclude.contains("hidden")) nodes.removeIf(nodeGene -> nodeGene.getX() != 0.1 || nodeGene.getX() != 0.9 || nodeGene instanceof BiasNodeGene);
        int index = (int) (Math.random() * (genes.size()-1)) + 1;
        return genes.get(index);
    }

    public void clear(){
        genes.clear();
    }
}
