package lib.util;

import genome.BiasNodeGene;
import genome.Gene;
import genome.NodeGene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    public NodeGene randomNode(List<String> excludeTypes) {
        List<NodeGene> filteredNodes = genes.values().stream()
                .filter(gene -> gene instanceof NodeGene)
                .map(gene -> (NodeGene) gene)
                .filter(node -> !excludeTypes.contains(node.getType()))
                .toList();

        if (filteredNodes.isEmpty()) {
            return null; // or throw an exception if no valid nodes are found
        }

        return filteredNodes.get((int) (Math.random() * filteredNodes.size()));
    }

    public NodeGene randomNode(String... exclude){
        List<String> excludeList = new ArrayList<>(Arrays.asList(exclude));
        return randomNode(excludeList);
    }

    public List<NodeGene> getNodes(String... exclude){
        List<Double> excludeX = new ArrayList<>();
        boolean excludeHidden = false;
        for (String s : exclude){
            switch (s){
                case "input":
                    excludeX.add(0.1);
                    break;
                case "output":
                    excludeX.add(0.9);
                    break;
                case "bias":
                    excludeX.add(0.15);
                    break;
                case "hidden":
                    excludeHidden = true;
                    break;
            }
        }

        List<NodeGene> nodes = new ArrayList<>(getNodeGenes());
        boolean finalExcludeHidden = excludeHidden;
        nodes.removeIf(n-> excludeX.contains(n.getX()) || (finalExcludeHidden && n.getX() != 0.1 && n.getX() != 0.9 && n.getX() != 0.15));
        return nodes;
    }

    public void clear(){
        genes.clear();
    }

    public void print(){
        for (Gene gene : genes.values()){
            System.out.println(gene);
        }
    }
}
