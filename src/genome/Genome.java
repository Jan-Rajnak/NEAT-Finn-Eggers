package genome;

import com.sun.source.tree.Tree;
import lib.util.GeneSet;
import neat.Neat;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Genome {

    private GeneSet connections = new GeneSet();
    private GeneSet nodes = new GeneSet();
    private Neat neat;

    public Genome(Neat neat){
        this.neat = neat;
    }

    /**
     * Calculates the distance between two genomes
     * !g1 has higher max innovation number than g2
     * @param g2 genome to compare to
     * @return distance between g1 and g2
     */
    public double distance(Genome g2){
        Genome g1 = this;

        int higInnNum1 = g1.getConnections().maxInnovation();
        int higInnNum2 = g2.getConnections().maxInnovation();

        if (higInnNum1 < higInnNum2){
            Genome temp = g1;
            g1 = g2;
            g2 = temp;
        }

        int index1 = 0;
        int index2 = 0;

        int disjoint = 0;
        int excess = 0;
        double weightDiff = 0;
        int matching = 0;

        while (index1 < g1.getConnections().size() && index2 < g2.getConnections().size()){
            ConnectionGene c1 = (ConnectionGene) g1.getConnections().get(index1);
            ConnectionGene c2 = (ConnectionGene) g2.getConnections().get(index2);

            if (c1.getInnovationNumber() == c2.getInnovationNumber()){
                // Matching gene
                weightDiff += Math.abs(c1.getWeight() - c2.getWeight());
                matching++;
                index1++;
                index2++;
            } else if (c1.getInnovationNumber() < c2.getInnovationNumber()){
                // Disjoint gene in g1
                disjoint++;
                index1++;
            } else {
                // Disjoint gene in g2
                disjoint++;
                index2++;
            }
        }

        excess = g1.getConnections().size() - index1;

        double avgWeightDiff = weightDiff / matching;

        double N = Math.max(g1.getConnections().size(), g2.getConnections().size());
        if (N < 20){
            N = 1;
        }

        return neat.getC1() * excess / N + neat.getC2() * disjoint / N + neat.getC3() * avgWeightDiff;

    }

    /**
     * creates a new genome.
     * g1 should have the higher score
     *  - take all the genes of g1
     *  - if there is a genome in g1 that is also in g2, choose randomly
     *  - do not take disjoint genes of g2
     *  - take excess genes of g1 if they exist
     * @param g1 genome with higher score
     * @param g2 genome with lower score
     * @return new genome, not yet mutated
     */
    public static Genome crossover(Genome g1, Genome g2){
        Neat neat = g1.neat;

        Genome child = neat.emptyGenome();

        int index1 = 0;
        int index2 = 0;

        while (index1 < g1.getConnections().size() && index2 < g2.getConnections().size()){
            ConnectionGene c1 = (ConnectionGene) g1.getConnections().get(index1);
            ConnectionGene c2 = (ConnectionGene) g2.getConnections().get(index2);

            if (c1.getInnovationNumber() == c2.getInnovationNumber()){
                // Matching gene
                if (Math.random() < 0.5){
                    child.connections.add(neat.getConnection(c1));
                } else {
                    child.connections.add(neat.getConnection(c2));
                }
                index1++;
                index2++;
            } else if (c1.getInnovationNumber() < c2.getInnovationNumber()){
                // Disjoint gene in g1
                child.connections.add(neat.getConnection(c1));
                index1++;
            } else {
                // Disjoint gene in g2
                index2++;
            }
        }

        // Excess genes
        while (index1 < g1.getConnections().size()){
            ConnectionGene c1 = (ConnectionGene) g1.getConnections().get(index1);
            child.connections.add(neat.getConnection(c1));
            index1++;
        }

        // Add all the nodes
        for (Gene c : child.connections.getGenes()){
            ConnectionGene temp = (ConnectionGene) c;
            child.getNodes().add(temp.getFrom());
            child.getNodes().add(temp.getTo());
        }

        return child;
    }

    public GeneSet getConnections() {
        return connections;
    }

    public GeneSet getNodes() {
        return nodes;
    }

    public void mutate(){
        if(neat.getPROBABILITY_MUTATE_LINK() > Math.random()){
            mutate_link();
        }if(neat.getPROBABILITY_MUTATE_NODE() > Math.random()){
            mutate_node();
        }if(neat.getPROBABILITY_MUTATE_WEIGHT_SHIFT() > Math.random()){
            mutate_weight_shift();
        }if(neat.getPROBABILITY_MUTATE_WEIGHT_RANDOM() > Math.random()){
            mutate_weight_random();
        }if(neat.getPROBABILITY_MUTATE_TOGGLE_LINK() > Math.random()){
            mutate_link_toggle();
        }if (neat.getPROBABILITY_BIAS_NODE() > Math.random()){
            mutate_bias_node();
        }
    }

    public void mutate_link() {

        for(int i = 0; i < 100; i++){

            NodeGene a = (NodeGene) nodes.random();
            NodeGene b = (NodeGene) nodes.random();

            if(a.getX() == b.getX()){
                continue;
            }

            ConnectionGene con;
            if(a.getX() < b.getX()){
                con = neat.getConnection(a,b);
            }else{
                con = neat.getConnection(b,a);
            }

            if(connections.contains(con)){
                continue;
            }

            con = neat.getConnection(con.getFrom(), con.getTo());
            con.setWeight((Math.random() * 2 - 1) * neat.getWEIGHT_RANDOM_STRENGTH());

            connections.add(con);
            System.out.println("added connection" + con);
            neat.printAllConnections();
            return;
        }
    }

    public void mutate_node() {
        ConnectionGene con = (ConnectionGene) connections.random();
        if(con == null) return;

        NodeGene from = con.getFrom();
        NodeGene to = con.getTo();

        NodeGene middle = neat.getNode();
        middle.setX((from.getX() + to.getX()) / 2);
        middle.setY((from.getY() + to.getY()) / 2 + Math.random() * 0.1 - 0.05);

        ConnectionGene con1 = neat.getConnection(from, middle);
        ConnectionGene con2 = neat.getConnection(middle, to);

        con1.setWeight(1);
        con2.setWeight(con.getWeight());
        con2.setEnabled(con.isEnabled());

        connections.remove(con);
        connections.add(con1);
        connections.add(con2);

        nodes.add(middle);
    }

    public void mutate_bias_node(){
        BiasNodeGene bn = neat.getBiasNode();
        bn.setBias(Math.random() * 2 - 1);
        nodes.add(bn);
        // Choose a random node to connect to
        NodeGene node = (NodeGene) nodes.randomNode(List.of("input", "bias"));
        ConnectionGene con = neat.getConnection(bn, node);

        con.setWeight((Math.random() * 2 - 1) * neat.getWEIGHT_RANDOM_STRENGTH());
        connections.add(con);
        // Set the bias node's x and y values
        bn.setX(0.15);
        bn.setY(Math.random());

    }

    public void mutate_weight_shift() {
        ConnectionGene con = (ConnectionGene) connections.random();
        if(con != null){
            con.setWeight(con.getWeight() + (Math.random() * 2 - 1) * neat.getWEIGHT_SHIFT_STRENGTH());
        }
    }

    public void mutate_weight_random() {
        ConnectionGene con = (ConnectionGene) connections.random();
        if(con != null){
            con.setWeight((Math.random() * 2 - 1) * neat.getWEIGHT_RANDOM_STRENGTH());
        }
    }

    public void mutate_link_toggle() {
        ConnectionGene con = (ConnectionGene) connections.random();
        if(con != null){
            con.setEnabled(!con.isEnabled());
        }
    }

    public double[] evaluate(double... inputs){
        if (inputs.length != neat.getInput_size()){
            throw new RuntimeException("Invalid input size");
        }

        double[] outputs = new double[neat.getOutput_size()];

        // Set input nodes
        for (int i = 0; i < neat.getInput_size(); i++){
            NodeGene node = (NodeGene) nodes.getGenes().get(i);
            node.setValue(inputs[i]);
            node.activate();
        }

        TreeMap<Double, List<NodeGene>> sorted = sortNodesByX();
        // Remove the input nodes and bias nodes
        sorted.remove(0.1);
        sorted.remove(0.15);
        HashMap<NodeGene, List<ConnectionGene>> connsIn = createConnsIn(nodes);
        for (List<NodeGene> list : sorted.values()){
            for (NodeGene n : list){
                double sum = 0;
                for (ConnectionGene c : connsIn.get(n)){
                    if (c.isEnabled()){
                        sum += c.getFrom().getValue() * c.getWeight();
                    }
                }
                n.setValue(sum);
                n.activate();
            }
        }

        for (int i = 0; i < neat.getOutput_size(); i++){
            outputs[i] = ((NodeGene) nodes.getGenes().get(neat.getInput_size() + i)).getValue();
        }

        return outputs;
    }

    private TreeMap<Double, List<NodeGene>> sortNodesByX(){
        TreeMap<Double, List<NodeGene>> sorted = new TreeMap<>();
        for (Gene n : nodes.getGenes()){
            NodeGene node = (NodeGene) n;
            if (sorted.containsKey(node.getX())){
                sorted.get(node.getX()).add(node);
            } else {
                List<NodeGene> list = new ArrayList<>();
                list.add(node);
                sorted.put(node.getX(), list);
            }
        }
        return sorted;
    }

    private HashMap<NodeGene, List<ConnectionGene>> createConnsIn(GeneSet nodes){
        HashMap<NodeGene, List<ConnectionGene>> connsIn = new HashMap<>();
        for (Gene n : nodes.getGenes()){
            NodeGene node = (NodeGene) n;
            connsIn.put(node, new ArrayList<>());
        }
        for (Gene c : connections.getGenes()){
            ConnectionGene conn = (ConnectionGene) c;
            connsIn.get(conn.getTo()).add(conn);
        }
        return connsIn;
    }

}
