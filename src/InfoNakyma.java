import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InfoNakyma extends JFrame {
    private JPanel panel = new JPanel();
    private JPanel edellinenNakyma;

    //Ottaa talteen aiemman näkymän paneelin ja jos jonkun infonäkymän x nappia painetaan niin palataan aiempaan "päänäkymään"
    public InfoNakyma(JPanel paneeli) {
        edellinenNakyma = paneeli;
    }

    //Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
    //lisätään frameen ja on näkyvissä siihen asti kunnes x nappia painetaan ja siirrytään aiempaan näkymään tai nuolilla
    //navigoiden johonkin toiseen infoikkunaan.
    public void InfoNakyma1(JFrame frame) {
        Font big = new Font("Arial", Font.BOLD, 60);
        Font newfont = new Font("Arial", Font.BOLD, 20);
        Color valkoinen = new Color(255, 255, 255);
        Font xfont = new Font("Arial", Font.BOLD, 15);
        Color aloitustausta = new Color(199, 208, 216);
        Color musta = new Color(0, 0, 0);
        panel.setBackground(aloitustausta);

        JLabel alkuteksti = new JLabel("WINTER WONDERLAND");
        alkuteksti.setBounds(252, 50, 800, 60);
        alkuteksti.setFont(big);

        JLabel teksti1 = new JLabel("WINTER WONDERLAND on uusi mukaansa tempaava peli, jossa pääset testaamaan onneasi tuttujen kuvioiden parissa.");
        teksti1.setBounds(20, 150, 1200, 30);
        teksti1.setFont(newfont);

        JLabel teksti2 = new JLabel("Pelin RTP(return to player) on 96.6% ja siinä on 5 tapaa voittaa.");
        teksti2.setBounds(20, 180, 700, 30);
        teksti2.setFont(newfont);

        JLabel teksti3 = new JLabel("Todennäköisyys voittaa päävoitto: 1/60 000 000");
        teksti3.setBounds(20, 210, 500, 30);
        teksti3.setFont(newfont);

        JLabel teksti4 = new JLabel("Todennäköisyys voittaa 2000 kertaa panos: 1/40 000");
        teksti4.setBounds(20, 240, 500, 30);
        teksti4.setFont(newfont);

        JLabel teksti5 = new JLabel("Todennäköisyys voittaa 1000 kertaa panos: 1/10 000");
        teksti5.setBounds(20, 270, 500, 30);
        teksti5.setFont(newfont);

        JLabel teksti6 = new JLabel("Todennäköisyys voiton osumiseen: 1/8");
        teksti6.setBounds(20, 300, 500, 30);
        teksti6.setFont(newfont);

        BasicArrowButton east = new BasicArrowButton(BasicArrowButton.EAST);
        east.setBounds(640, 700, 50, 25);
        panel.add(east);
        east.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma2(frame, panel);
                    panel.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST);
        west.setBounds(480, 700, 50, 25);
        panel.add(west);
        west.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma3(frame, panel);
                    panel.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        RoundButton eka = new RoundButton("");
        eka.setBounds(550, 707, 10, 10);
        eka.setBackground(musta);

        RoundButton toka = new RoundButton("");
        toka.setBounds(580, 707, 10, 10);
        toka.setBackground(valkoinen);
        toka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma2(frame, panel);
                    panel.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        RoundButton kolmas = new RoundButton("");
        kolmas.setBounds(610, 707, 10, 10);
        kolmas.setBackground(valkoinen);
        kolmas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma3(frame, panel);
                    panel.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        panel.add(eka);
        panel.add(toka);
        panel.add(kolmas);

        JButton x = new JButton("X");
        x.setBounds(1120, 25, 45, 45);
        x.setBackground(valkoinen);
        x.setFont(xfont);
        x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                panel.setVisible(false);
                edellinenNakyma.setVisible(true);
            }
        });

        panel.setLayout(null);
        panel.add(x);
        panel.add(alkuteksti);
        panel.add(teksti1);
        panel.add(teksti2);
        panel.add(teksti3);
        panel.add(teksti4);
        panel.add(teksti5);
        panel.add(teksti6);
        frame.add(panel);
        panel.setVisible(true);
        edellinenNakyma.setVisible(false);
        frame.setVisible(true);

    }

    //Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
    //lisätään frameen ja on näkyvissä siihen asti kunnes x nappia painetaan ja siirrytään aiempaan näkymään tai nuolilla
    //navigoiden johonkin toiseen infoikkunaan.
    public void InfoNakyma2(JFrame frame, JPanel paneeli) throws IOException {
        JPanel paneli = new JPanel();
        Font big = new Font("Arial", Font.BOLD, 60);
        Font mid = new Font("Arial", Font.BOLD, 45);
        Font newfont = new Font("Arial", Font.BOLD, 20);
        Color valkoinen = new Color(255, 255, 255);
        Color musta = new Color(0, 0, 0);
        Color aloitustausta = new Color(199, 208, 216);
        Font xfont = new Font("Arial", Font.BOLD, 15);
        paneli.setBackground(aloitustausta);

        JLabel alkuteksti = new JLabel("WINTER WONDERLAND");
        alkuteksti.setBounds(252, 50, 800, 60);
        alkuteksti.setFont(big);

        JLabel alkuteksti2 = new JLabel("VOITTOTAULU");
        alkuteksti2.setBounds(420, 110, 600, 60);
        alkuteksti2.setFont(mid);

        BufferedImage ympyra = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/ympyra.png"));
        JLabel y = new JLabel(new ImageIcon(ympyra));
        y.setBounds(150, 200, 180, 180);
        paneli.add(y);

        BufferedImage kolmio = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/kolmio.png"));
        JLabel k = new JLabel(new ImageIcon(kolmio));
        k.setBounds(850, 200, 180, 180);
        paneli.add(k);

        BufferedImage nelio = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/nelio.png"));
        JLabel n = new JLabel(new ImageIcon(nelio));
        n.setBounds(500, 200, 180, 180);
        paneli.add(n);

        BufferedImage hexa = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/hexa.png"));
        JLabel h = new JLabel(new ImageIcon(hexa));
        h.setBounds(500, 450, 180, 180);
        paneli.add(h);

        JLabel ympyrateksti = new JLabel("3kpl, 10x");
        ympyrateksti.setBounds(195, 400, 150, 25);
        ympyrateksti.setFont(newfont);

        JLabel nelioteksti = new JLabel("3kpl, 7x");
        nelioteksti.setBounds(550, 400, 150, 25);
        nelioteksti.setFont(newfont);

        JLabel kolmioteksti = new JLabel("3kpl, 5x");
        kolmioteksti.setBounds(900, 400, 150, 25);
        kolmioteksti.setFont(newfont);

        JLabel hexateksti = new JLabel("3kpl, 3x");
        hexateksti.setBounds(550, 630, 150, 25);
        hexateksti.setFont(newfont);

        JButton x = new JButton("X");
        x.setBounds(1120, 25, 45, 45);
        x.setBackground(valkoinen);
        x.setFont(xfont);
        x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                paneli.setVisible(false);
                edellinenNakyma.setVisible(true);
            }
        });

        BasicArrowButton east = new BasicArrowButton(BasicArrowButton.EAST);
        east.setBounds(640, 700, 50, 25);
        paneli.add(east);
        east.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma3(frame, paneli);
                    paneli.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        RoundButton eka = new RoundButton("");
        eka.setBounds(550, 707, 10, 10);
        eka.setBackground(valkoinen);
        eka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma1(frame);
                paneli.setVisible(false);
            }
        });

        RoundButton toka = new RoundButton("");
        toka.setBounds(580, 707, 10, 10);
        toka.setBackground(musta);

        RoundButton kolmas = new RoundButton("");
        kolmas.setBounds(610, 707, 10, 10);
        kolmas.setBackground(valkoinen);
        kolmas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma3(frame, paneli);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paneli.setVisible(false);
            }
        });

        paneli.add(eka);
        paneli.add(toka);
        paneli.add(kolmas);

        BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST);
        west.setBounds(480, 700, 50, 25);
        paneli.add(west);
        west.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma1(frame);
                paneli.setVisible(false);
            }
        });

        paneli.setLayout(null);
        paneli.add(x);
        paneli.add(alkuteksti);
        paneli.add(alkuteksti2);
        paneli.add(ympyrateksti);
        paneli.add(nelioteksti);
        paneli.add(kolmioteksti);
        paneli.add(hexateksti);
        frame.add(paneli);
        paneeli.setVisible(false);
        frame.setVisible(true);

    }

    //Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
    //lisätään frameen ja on näkyvissä siihen asti kunnes x nappia painetaan ja siirrytään aiempaan näkymään tai nuolilla
    //navigoiden johonkin toiseen infoikkunaan.
    public void InfoNakyma3(JFrame frame, JPanel paneeli) throws IOException {

        JPanel paneli3 = new JPanel();
        Font big = new Font("Arial", Font.BOLD, 60);
        Font mid = new Font("Arial", Font.BOLD, 45);
        Color valkoinen = new Color(255, 255, 255);
        Color musta = new Color(0, 0, 0);
        Color aloitustausta = new Color(199, 208, 216);
        Font xfont = new Font("Arial", Font.BOLD, 15);
        paneli3.setBackground(aloitustausta);

        JLabel alkuteksti = new JLabel("WINTER WONDERLAND");
        alkuteksti.setBounds(252, 50, 800, 60);
        alkuteksti.setFont(big);

        JLabel alkuteksti2 = new JLabel("VOITTOLINJAT");
        alkuteksti2.setBounds(420, 110, 600, 60);
        alkuteksti2.setFont(mid);

        BufferedImage keskilinja = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/keskilinja.png"));
        JLabel kl = new JLabel(new ImageIcon(keskilinja));
        kl.setBounds(150, 200, 180, 180);
        paneli3.add(kl);

        BufferedImage ylalinja = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/ylalinja.png"));
        JLabel yl = new JLabel(new ImageIcon(ylalinja));
        yl.setBounds(850, 200, 180, 180);
        paneli3.add(yl);

        BufferedImage alalinja = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/alalinja.png"));
        JLabel al = new JLabel(new ImageIcon(alalinja));
        al.setBounds(500, 200, 180, 180);
        paneli3.add(al);

        BufferedImage vasala = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/vasala.png"));
        JLabel va = new JLabel(new ImageIcon(vasala));
        va.setBounds(150, 450, 180, 180);
        paneli3.add(va);

        BufferedImage vasyla = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/vasyla.png"));
        JLabel vy = new JLabel(new ImageIcon(vasyla));
        vy.setBounds(500, 450, 180, 180);
        paneli3.add(vy);

        BasicArrowButton east = new BasicArrowButton(BasicArrowButton.EAST);
        east.setBounds(640, 700, 50, 25);
        paneli3.add(east);
        east.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma1(frame);
                paneli3.setVisible(false);
            }
        });

        BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST);
        west.setBounds(480, 700, 50, 25);
        paneli3.add(west);
        west.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma2(frame, paneli3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paneli3.setVisible(false);
            }
        });

        RoundButton eka = new RoundButton("");
        eka.setBounds(550, 707, 10, 10);
        eka.setBackground(valkoinen);
        eka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma1(frame);
                paneli3.setVisible(false);
            }
        });

        RoundButton toka = new RoundButton("");
        toka.setBounds(580, 707, 10, 10);
        toka.setBackground(valkoinen);
        toka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    InfoNakyma2(frame, paneeli);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paneli3.setVisible(false);
            }
        });

        RoundButton kolmas = new RoundButton("");
        kolmas.setBounds(610, 707, 10, 10);
        kolmas.setBackground(musta);

        paneli3.add(eka);
        paneli3.add(toka);
        paneli3.add(kolmas);

        JButton x = new JButton("X");
        x.setBounds(1120, 25, 45, 45);
        x.setBackground(valkoinen);
        x.setFont(xfont);
        x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                paneli3.setVisible(false);
                edellinenNakyma.setVisible(true);
            }
        });

        paneli3.setLayout(null);
        paneli3.add(x);
        paneli3.add(alkuteksti);
        paneli3.add(alkuteksti2);
        frame.add(paneli3);
        paneeli.setVisible(false);
        frame.setVisible(true);

    }
}