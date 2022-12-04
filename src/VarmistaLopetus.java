import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VarmistaLopetus extends JFrame {
    private JPanel edellinenNakyma;
    private JPanel varmistaLopetus = new JPanel();

    //Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
    //lisätään frameen ja on näkyvissä siihen asti kunnes valitaan peliin palaamisen tai lopettamisen väliltä.
    public VarmistaLopetus(JPanel panel, JFrame frame) {
        edellinenNakyma = panel;
        Color aloitustausta = new Color(199, 208, 216);
        Color valkonen = new Color(255, 255, 255);
        Font big = new Font("Arial", Font.BOLD, 40);
        Font mainfont = new Font("Arial", Font.BOLD, 30);

        JLabel alkuteksti = new JLabel("Haluatko varmasti lopettaa pelin?");
        alkuteksti.setBounds(300, 200, 800, 60);
        alkuteksti.setFont(big);

        JButton kylla = new JButton("KYLLÄ");
        kylla.setBounds(300, 400, 250, 75);
        kylla.setBackground(valkonen);
        kylla.setFont(mainfont);
        kylla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(1);
            }
        });

        JButton ei = new JButton("EI");
        ei.setBounds(650, 400, 250, 75);
        ei.setBackground(valkonen);
        ei.setFont(mainfont);
        ei.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                varmistaLopetus.setVisible(false);
                edellinenNakyma.setVisible(true);
            }
        });

        varmistaLopetus.setLayout(null);
        varmistaLopetus.add(kylla);
        varmistaLopetus.add(alkuteksti);
        varmistaLopetus.add(ei);
        varmistaLopetus.setBackground(aloitustausta);
        edellinenNakyma.setVisible(false);
        frame.add(varmistaLopetus);
    }
}
