package chord;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Screen1 implements ActionListener {

    JFrame fr;
    JPanel p;
    JButton run;
    JLabel logo1, logo2;
    JLabel[] tag;
    JTextField[] text;

    public Screen1() throws Exception {

        fr = new JFrame();
        p = new JPanel();
        tag = new JLabel[4];
        text = new JTextField[4];
        logo1 = new JLabel();
        logo2 = new JLabel();
        run = new JButton("Run ");
        p.setLayout(null);

        Font bigf = new Font("Californian FB", Font.BOLD, 23);
        Font midf = new Font("Californian FB", Font.BOLD, 16);

        for (int i = 0; i < 4; i++) {
            tag[i] = new JLabel();
            text[i] = new JTextField();
            tag[i].setFont(midf);
            text[i].setFont(midf);
        }
        text[0].setText("100000");
        text[1].setText("1000");
        text[2].setText("100");
        text[3].setText("100000");
        run.setFont(midf);
        run.addActionListener(this);

        logo1.setText("POLICING INSPIRED SECURITY SYSTEM ALGORITHM ");
        logo1.setFont(bigf);
        logo2.setText("FOR CHORD PROTOCOL");
        logo2.setFont(bigf);

        fr.setBounds(100, 100, 700, 520);

        fr.setVisible(true);
        logo1.setBounds(50, 30, 600, 40);
        logo2.setBounds(200, 80, 600, 40);

        tag[0].setBounds(100, 150, 230, 30);
        tag[1].setBounds(100, 200, 230, 30);
        tag[2].setBounds(100, 250, 230, 30);
        tag[3].setBounds(100, 300, 230, 30);

        tag[0].setText("Number of Honest nodes");
        tag[1].setText("Number of Culprit nodes");
        tag[2].setText("Number of Police nodes");
        tag[3].setText("Number of Queries");

        text[0].setBounds(350, 150, 200, 30);
        text[1].setBounds(350, 200, 200, 30);
        text[2].setBounds(350, 250, 200, 30);
        text[3].setBounds(350, 300, 200, 30);

        run.setBounds(350, 350, 70, 30);

        p.add(logo1);
        p.add(logo2);

        fr.setResizable(false);

        for (int i = 0; i < 4; i++) {
            p.add(tag[i]);
            p.add(text[i]);
        }
        p.add(run);
        fr.add(p);

    }

    public static void main(String[] args) {
        try {
            new Screen1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //String[] s = new String[4];
        int[] val = new int[4];
        if (e.getSource() == run) {
            boolean b = true;
            for (int i = 0; i < 4; i++) {
                if ((text[i].getText() == null) || (text[i].getText().equals(""))) {
                    b = false;
                }
            }
            if (b) {
                for (int i = 0; i < 4; i++) {
                    val[i] = Integer.parseInt(text[i].getText());

                }
                //Ring.create(l[0],l[1],l[2],l[3]);
                Ring ring = new Ring(val[0], val[1], val[2]);
                float firstResult=ring.runQueries(val[3]);
                
                //MyRandomGenerator numGen = MyRandomGenerator.getInstance();
                //float fraction = 4 + ((1.0f)*numGen.nextPositiveInt())/Integer.MAX_VALUE;
                CulpritAnalysisCount culpritCount = ring.getCulpritAnalysis();
                int rightlyDetectedNodes = culpritCount.getRightlyDetectedCulprits();
                ring.removeFaultyNodes();
                float secondResult = ring.runQueries(val[3]);
                try {
                	fr.dispose();
                	new Screen2(firstResult, secondResult, rightlyDetectedNodes);
				} catch (Exception e2) {
					// TODO: anuj's exception
					//e2.printtackTrace();
				}
                
            } else {
                JOptionPane.showMessageDialog(null, "None of the entry parts can be NULL");
            }   
        }
    }
}