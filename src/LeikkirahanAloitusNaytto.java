import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class LeikkirahanAloitusNaytto extends JFrame {
    private JPanel edellinenNakyma;
    private JPanel leikkialoitus = new JPanel();
    Database db = Database.getInstance();

    //Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
    //lisätään frameen
    public LeikkirahanAloitusNaytto(JPanel panel, JFrame frame) {
        edellinenNakyma = panel;
        Color aloitustausta = new Color(199, 208, 216);
        Color valkonen = new Color(255, 255, 255);
        Font big = new Font("Arial", Font.BOLD, 60);
        Font mainfont = new Font("Arial", Font.BOLD, 30);
        Font newfont = new Font("Arial", Font.BOLD, 20);
        Font vaihtofont = new Font("Arial", Font.BOLD, 11);
        Font xfont = new Font("Arial", Font.BOLD, 15);

        JLabel pelitilanvaihto = new JLabel("Pelaa oikealla rahalla");
        pelitilanvaihto.setBounds(12, 1, 150, 20);
        pelitilanvaihto.setFont(vaihtofont);
        leikkialoitus.add(pelitilanvaihto);
        pelitilanvaihto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String code = "leikki";
                new VarmistaPelitavanVaihto(leikkialoitus, frame, code);
                frame.setVisible(true);
            }
        });

        RoundButton panoksensuuruus = new RoundButton("1.00€");
        panoksensuuruus.setBounds(520, 200, 150, 150);
        panoksensuuruus.setFont(mainfont);
        panoksensuuruus.setBackground(aloitustausta);
        panoksensuuruus.setEnabled(false);

        String[] panokset = new String[10];
        panokset[0] = "0.20";
        panokset[1] = "0.40";
        panokset[2] = "0.60";
        panokset[3] = "0.80";
        panokset[4] = "1.00";
        panokset[5] = "2.00";
        panokset[6] = "4.00";
        panokset[7] = "6.00";
        panokset[8] = "8.00";
        panokset[9] = "10.00";

        JLabel plus = new JLabel("+");
        plus.setBounds(695, 364, 250, 60);
        plus.setFont(mainfont);

        JLabel miinus = new JLabel("-");
        miinus.setBounds(470, 360, 250, 60);
        miinus.setFont(mainfont);

        Icon fonttiicon = new ImageIcon("C:/Users/alexh/Downloads/WinterWonderland/fontti2.png");
        RoundButton fontti = new RoundButton(fonttiicon);
        fontti.setBounds(1100, 685, 56, 56);
        fontti.setFont(mainfont);
        fontti.setBackground(aloitustausta);
        fontti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new FontinVaihto(frame, leikkialoitus);
                frame.setVisible(true);
            }
        });

        JSlider slider = new JSlider(1, 10);
        slider.setBounds(490, 380, 200, 25);
        slider.setBackground(aloitustausta);
        leikkialoitus.add(slider);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                panoksensuuruus.setText(panokset[slider.getValue() - 1] + "€");
            }
        });

        JLabel alleviivaus = new JLabel("___________________");
        alleviivaus.setBounds(12, 1, 250, 20);
        alleviivaus.setFont(vaihtofont);

        JLabel alkuteksti = new JLabel("WINTER WONDERLAND");
        alkuteksti.setBounds(252, 50, 800, 60);
        alkuteksti.setFont(big);

        JLabel panosviiva = new JLabel("PANOS");
        panosviiva.setBounds(560, 125, 150, 60);
        panosviiva.setFont(newfont);

        JLabel suurinvoitto = new JLabel("VOITTO JOPA 10 000x");
        suurinvoitto.setBounds(484, 700, 250, 60);
        suurinvoitto.setFont(newfont);

        RoundButton info = new RoundButton("i");
        info.setBounds(25, 685, 55, 55);
        info.setFont(mainfont);
        info.setBackground(aloitustausta);

        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma nakyma = new InfoNakyma(leikkialoitus);
                nakyma.InfoNakyma1(frame);
                frame.setVisible(true);
            }
        });

        JButton x = new JButton("X");
        x.setBounds(1120, 15, 45, 45);
        x.setBackground(aloitustausta);
        x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new VarmistaLopetus(leikkialoitus, frame);
                frame.setVisible(true);
            }
        });

        JButton jatka = new JButton("JATKA");
        jatka.setBounds(465, 600, 250, 75);
        jatka.setFont(xfont);
        jatka.setBackground(aloitustausta);
        jatka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    db.setPanos(panokset[slider.getValue() - 1]);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                leikkialoitus.setVisible(false);
                LeikkiPaaNakyma nakyma = new LeikkiPaaNakyma();
                try {
                    try {
                        nakyma.LeikkipaaNakyma(frame);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        leikkialoitus.setLayout(null);
        leikkialoitus.add(jatka);
        leikkialoitus.add(x);
        leikkialoitus.add(info);
        leikkialoitus.add(plus);
        leikkialoitus.add(miinus);
        leikkialoitus.add(fontti);
        leikkialoitus.add(suurinvoitto);
        leikkialoitus.add(panosviiva);
        leikkialoitus.add(alkuteksti);
        leikkialoitus.add(alleviivaus);
        leikkialoitus.add(panoksensuuruus);
        leikkialoitus.setBackground(valkonen);
        edellinenNakyma.setVisible(false);
        frame.add(leikkialoitus);
    }
}
