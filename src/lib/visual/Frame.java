package lib.visual;

import genome.Genome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Frame extends JFrame {

    private Panel panel;
    private Genome genome;

    public Frame(Genome genome) {
        this();
        setGenome(genome);
        this.repaint();
    }

    public void setGenome(Genome genome){
        panel.setGenome(genome);
        this.genome = genome;
    }

    public Frame() throws HeadlessException {
        this.setDefaultCloseOperation(3);

        this.setTitle("NEAT");
        this.setMinimumSize(new Dimension(1000,700));
        this.setPreferredSize(new Dimension(1000,700));

        this.setLayout(new BorderLayout());


        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        try {
            UIManager.setLookAndFeel(looks[3].getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(1000,100));
        menu.setLayout(new GridLayout(1,6));

        JButton buttonB = new JButton("random weight");
        buttonB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.weight_random();
                repaint();
            }
        });
        menu.add(buttonB);

        JButton buttonZ = new JButton("weight shift");
        buttonZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.weight_shift();
                repaint();
            }
        });
        menu.add(buttonZ);

        JButton buttonC = new JButton("Link mutate");
        buttonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.add_connection();
                repaint();
            }
        });
        menu.add(buttonC);

        JButton buttonD = new JButton("Node mutate");
        buttonD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.add_node();
                repaint();
            }
        });
        menu.add(buttonD);

        JButton buttonE = new JButton("Bias mutate");
        buttonE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.add_bias_node();
                repaint();
            }
        });
        menu.add(buttonE);


        JButton buttonF = new JButton("on/off");
        buttonF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.connection_toggle();
                repaint();
            }
        });
        menu.add(buttonF);

        JButton buttonG = new JButton("Mutate");
        buttonG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genome.mutate();
                repaint();
            }
        });
        menu.add(buttonG);

        JButton buttonH = new JButton("Calculate");
        buttonH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Arrays.toString(genome.evaluate(0, 0, 0)));
                repaint();
            }
        });
        menu.add(buttonH);


        this.add(menu, BorderLayout.NORTH);

        this.panel = new Panel();
        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);
    }

}
