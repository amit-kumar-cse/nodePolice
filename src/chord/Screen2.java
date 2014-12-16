package chord;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen2 implements ActionListener {
    
    JFrame fr;
    JPanel p, inner;
    JButton run, remove;
    JLabel logo1, logo2;
    JLabel[] tag;
    JLabel[] text;
    public static int stage = 0;
    
    public Screen2(float a, float b, int c) throws Exception {
        
        fr = new JFrame();
        p = new JPanel();
        inner = new JPanel();
        tag = new JLabel[4];
        text = new JLabel[4];
        logo1 = new JLabel();
        logo2 = new JLabel();
        run = new JButton("Run another Simulation");
        remove = new JButton("Remove Culprit Nodes");
        p.setLayout(null);
        inner.setLayout(null);
        
        stage = 1;
        Font bigf = new Font("Californian FB", Font.BOLD, 23);
        Font midf = new Font("Californian FB", Font.BOLD, 16);
        
        stage = 2;
        
        for (int i = 0; i < 4; i++) {
            tag[i] = new JLabel();
            text[i] = new JLabel();
            tag[i].setFont(midf);
            text[i].setFont(midf);
        }
        text[0].setText(""+a+"%");
        
        text[1].setText(""+b + "%");
        
        text[2].setText(""+c);
        
        run.setFont(midf);
        remove.setFont(midf);
        
        logo1.setText("POLICING INSPIRED SECURITY SYSTEM ALGORITHM ");
        logo1.setFont(bigf);
        logo2.setText("FOR CHORD PROTOCOL");
        logo2.setFont(bigf);
        
        fr.setBounds(100, 100, 700, 520);
        
        stage = 3;
        fr.setVisible(true);
        logo1.setBounds(50, 30, 600, 40);
        logo2.setBounds(200, 80, 600, 40);
        tag[0].setBounds(100, 150, 230, 30);
        tag[1].setBounds(20, 70, 280, 30);
        tag[2].setBounds(20, 20, 280, 30);
        
        run.setBounds(270, 120, 250, 30);
        remove.setBounds(350, 200, 230, 30);
        
        stage = 4;
        tag[0].setText("Successful Queries");
        tag[1].setText("Successful Queries after cleaning");
		tag[2].setText("Correctly detected nodes");
        
        
        text[0].setBounds(350, 150, 200, 30);
        text[1].setBounds(300, 70, 280, 30);
        text[2].setBounds(300, 20, 280, 30);
        inner.setBounds(80, 300, 570, 170);
        inner.setBackground(Color.WHITE);
        inner.setVisible(false);
        remove.addActionListener(this);

        run.addActionListener(this);
        
        stage = 5;
        p.add(logo1);
        p.add(logo2);
        
        for (int i = 0; i < 1; i++) {
            p.add(tag[i]);
            p.add(text[i]);
        }
        inner.add(tag[1]);
        inner.add(text[1]);

        inner.add(tag[2]);
        inner.add(text[2]);
        inner.add(run);
        p.add(inner);
        p.add(remove);
        fr.add(p);
        
    }
 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == run) {
            fr.dispose();
            try {
                new Screen1();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        if (e.getSource() == remove) {
            inner.setVisible(true);
        }
    }
}
