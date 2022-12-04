import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class LeikkiPaaNakyma extends JFrame {

    private JPanel paaPaneeli = new JPanel();
    private JPanel reel1 = new JPanel();
    private JPanel reel2 = new JPanel();
    private JPanel reel3 = new JPanel();
    private JPanel vaihtoehdot = new JPanel();
    private JPanel automaattiPaneeli = new JPanel();
    private JPanel suuriVoitto = new JPanel();
    JPanel panel = new JPanel();
    Color valkoinen = new Color(255, 255, 255);
    Database db = Database.getInstance();
    int i = 0;
    int j = 0;
    int voittotappio;
    int havityt = 0;
    double alkuperainenSaldo = 1000;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final int TASK_DURATION_IN_SECONDS = 2 * 1000;

    //Saa parametrinä käytettävän framen ja luo tästä näkymästä toimintoineen paneelin joka toimii pääasiallisena näkymänä.
    public void LeikkipaaNakyma(JFrame frame) throws SQLException, IOException {
        Font big = new Font("Arial", Font.BOLD, 47);
        Random rand = new Random();
        Color aloitusjatka = new Color(166, 166, 166);
        Color aloitustausta = new Color(199, 208, 216);
        Font xfont = new Font("Arial", Font.BOLD, 15);
        Font small = new Font("Arial", Font.BOLD, 13);
        Font smaller = new Font("Arial", Font.BOLD, 11);
        Font mainfont = new Font("Arial", Font.BOLD, 20);
        Font iso = new Font("Arial", Font.BOLD, 30);
        voittotappio = db.getVoittoTappioTilanne();

        JLabel saldonSuuruus = new JLabel("1000,00" + "€");
        saldonSuuruus.setFont(mainfont);
        saldonSuuruus.setBounds(40, 710, 150, 20);
        db.setSaldo(1000.00);

        Icon iconAvaus = new ImageIcon("C:/Users/alexh/Downloads/WinterWonderland/menu.png");
        Icon iconSulku = new ImageIcon("C:/Users/alexh/Downloads/WinterWonderland/x.png");
        Icon stat = new ImageIcon("C:/Users/alexh/Downloads/WinterWonderland/statistiikka.png");
        Icon fonttiicon = new ImageIcon("C:/Users/alexh/Downloads/WinterWonderland/fontti.png");
        JButton suljeVaihtoehto = new JButton(iconSulku);
        JButton vaihtoehto = new JButton(iconAvaus);

        JLabel pelitilanvaihto = new JLabel("Pelaa oikealla rahalla");
        pelitilanvaihto.setBounds(12, 1, 150, 20);
        pelitilanvaihto.setFont(smaller);
        pelitilanvaihto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if (havityt > db.getHavitytKierrokset()) {
                        db.setHavityt(havityt);
                    }
                    if (alkuperainenSaldo > db.getSaldo()) {
                        int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                        db.setVoittoTappio(voittotappio + tulos);
                    } else {
                        if (alkuperainenSaldo < db.getSaldo()) {
                            int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                            db.setVoittoTappio(voittotappio + tulos);
                        } else {
                            if (alkuperainenSaldo == db.getSaldo()) {
                                int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                                db.setVoittoTappio(voittotappio + tulos);
                            }
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                String code = "leikki";
                new VarmistaPelitavanVaihto(paaPaneeli, frame, code);
                frame.setVisible(true);
            }
        });

        JLabel alleviivaus = new JLabel("___________________");
        alleviivaus.setBounds(12, 1, 250, 20);
        alleviivaus.setFont(smaller);

        RoundButton spin = new RoundButton("PYÖRÄYTÄ");
        spin.setBounds(545, 655, 100, 100);
        spin.setBackground(aloitustausta);
        spin.setFont(smaller);
        spin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (db.getKierrokset() == 0) {
                        db.setKierrokset(1);
                    } else {
                        db.setKierrokset(db.getKierrokset() + 1);
                    }

                    vaihtoehdot.setVisible(false);
                    suljeVaihtoehto.setVisible(false);
                    vaihtoehto.setVisible(true);
                    automaattiPaneeli.setVisible(false);
                    suuriVoitto.setVisible(false);

                    String panos = db.getPanos();
                    Double saldo = db.getSaldo();
                    double panos2 = Double.valueOf(panos);
                    Double uusisaldo = saldo - panos2;
                    db.setSaldo(uusisaldo);
                    if (db.getSaldo() < 0) {
                        db.setSaldo(0.00);
                    }
                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                        spin.setEnabled(false);
                    }
                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                        spin.setEnabled(true);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        vaihtoehdot.setBounds(1020, 425, 85, 250);
        vaihtoehdot.setBackground(aloitusjatka);

        suuriVoitto.setBounds(325, 150, 550, 350);
        suuriVoitto.setBackground(aloitusjatka);
        suuriVoitto.setVisible(true);
        paaPaneeli.add(suuriVoitto);

        paaPaneeli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                suuriVoitto.setVisible(false);
            }
        });

        JButton suurivoittox = new JButton("X");
        suurivoittox.setBounds(485, 15, 50, 50);
        suurivoittox.setBackground(valkoinen);
        suurivoittox.setFont(xfont);
        suuriVoitto.setVisible(false);
        suurivoittox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                suuriVoitto.setVisible(false);
            }
        });
        suuriVoitto.add(suurivoittox);

        JLabel ilmoitus = new JLabel("SUURI VOITTO!");
        ilmoitus.setFont(big);
        ilmoitus.setBounds(100, 130, 450, 45);
        suuriVoitto.add(ilmoitus);

        JLabel ilmoitus2 = new JLabel("");
        ilmoitus2.setFont(big);
        ilmoitus2.setBounds(100, 200, 450, 45);
        suuriVoitto.add(ilmoitus2);

        RoundButton statistiikka = new RoundButton(stat);
        statistiikka.setBounds(10, 20, 65, 65);
        statistiikka.setFont(xfont);
        statistiikka.setBackground(valkoinen);
        statistiikka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (havityt > db.getHavitytKierrokset()) {
                        db.setHavityt(havityt);
                    }
                    if (alkuperainenSaldo > db.getSaldo()) {
                        int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                        db.setVoittoTappio(voittotappio + tulos);
                    } else {
                        if (alkuperainenSaldo < db.getSaldo()) {
                            int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                            db.setVoittoTappio(voittotappio + tulos);
                        } else {
                            if (alkuperainenSaldo == db.getSaldo()) {
                                int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                                db.setVoittoTappio(voittotappio + tulos);
                            }
                        }
                    }
                    new Statistiikka(paaPaneeli, frame);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                frame.setVisible(true);
            }
        });

        RoundButton fontti = new RoundButton(fonttiicon);
        fontti.setBounds(10, 95, 65, 65);
        fontti.setFont(xfont);
        fontti.setBackground(valkoinen);
        fontti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new FontinVaihto(frame, paaPaneeli);
                frame.setVisible(true);
            }
        });

        RoundButton info = new RoundButton("i");
        info.setBounds(10, 170, 65, 65);
        info.setFont(iso);
        info.setBackground(valkoinen);
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma nakyma = new InfoNakyma(paaPaneeli);
                nakyma.InfoNakyma1(frame);
                frame.setVisible(true);
            }
        });

        vaihtoehdot.add(statistiikka);
        vaihtoehdot.add(fontti);
        vaihtoehdot.add(info);
        paaPaneeli.add(vaihtoehdot);
        vaihtoehdot.setVisible(false);

        vaihtoehto.setBounds(1020, 685, 85, 55);
        vaihtoehto.setBackground(aloitustausta);
        vaihtoehto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                vaihtoehdot.setVisible(true);
                vaihtoehto.setVisible(false);
                suljeVaihtoehto.setVisible(true);
                automaattiPaneeli.setVisible(false);
            }
        });

        suljeVaihtoehto.setBounds(1020, 685, 85, 55);
        suljeVaihtoehto.setBackground(aloitustausta);
        suljeVaihtoehto.setVisible(false);
        suljeVaihtoehto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                vaihtoehdot.setVisible(false);
                suljeVaihtoehto.setVisible(false);
                vaihtoehto.setVisible(true);
                automaattiPaneeli.setVisible(false);
            }
        });

        JLabel automaatti = new JLabel("AUTOMAATTI-");
        automaatti.setFont(small);
        automaatti.setBounds(345, 650, 150, 20);

        JLabel pelaus = new JLabel("PELAUS");
        pelaus.setFont(small);
        pelaus.setBounds(365, 667, 150, 20);

        automaattiPaneeli.setBounds(200, 350, 450, 250);
        automaattiPaneeli.setBackground(valkoinen);
        paaPaneeli.add(automaattiPaneeli);
        automaattiPaneeli.setVisible(false);

        JLabel pyoraytysten = new JLabel("PYÖRÄYTYSTEN");
        pyoraytysten.setFont(mainfont);
        pyoraytysten.setBounds(150, 50, 200, 20);
        automaattiPaneeli.add(pyoraytysten);

        JLabel maara = new JLabel("MÄÄRÄ");
        maara.setFont(mainfont);
        maara.setBounds(200, 85, 80, 20);
        automaattiPaneeli.add(maara);

        JButton kymmenen = new JButton("10");
        kymmenen.setBackground(aloitustausta);
        kymmenen.setBounds(120, 150, 60, 60);
        kymmenen.setFont(xfont);
        automaattiPaneeli.add(kymmenen);

        JButton kaksviis = new JButton("25");
        kaksviis.setBounds(195, 150, 60, 60);
        kaksviis.setBackground(aloitustausta);
        kaksviis.setFont(xfont);
        automaattiPaneeli.add(kaksviis);

        JButton viiskyt = new JButton("50");
        viiskyt.setBounds(270, 150, 60, 60);
        viiskyt.setBackground(aloitustausta);
        viiskyt.setFont(xfont);
        automaattiPaneeli.add(viiskyt);

        JButton seitviis = new JButton("75");
        seitviis.setBounds(120, 150, 60, 60);
        seitviis.setBackground(aloitustausta);
        seitviis.setFont(xfont);
        seitviis.setVisible(false);
        automaattiPaneeli.add(seitviis);

        JButton sata = new JButton("100");
        sata.setBounds(195, 150, 60, 60);
        sata.setBackground(aloitustausta);
        sata.setFont(xfont);
        sata.setVisible(false);
        automaattiPaneeli.add(sata);

        JButton kaksSataa = new JButton("200");
        kaksSataa.setBounds(270, 150, 60, 60);
        kaksSataa.setBackground(aloitustausta);
        kaksSataa.setFont(xfont);
        kaksSataa.setVisible(false);
        automaattiPaneeli.add(kaksSataa);

        JButton plus = new JButton("+");
        JButton miinus = new JButton("-");
        plus.setBounds(345, 150, 60, 60);
        plus.setBackground(aloitustausta);
        plus.setFont(xfont);
        plus.setEnabled(true);
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                seitviis.setVisible(true);
                sata.setVisible(true);
                kaksSataa.setVisible(true);
                kaksviis.setVisible(false);
                viiskyt.setVisible(false);
                kymmenen.setVisible(false);
                if (kaksSataa.isVisible()) {
                    plus.setEnabled(false);
                    miinus.setEnabled(true);
                } else {
                    plus.setEnabled(true);
                    miinus.setEnabled(false);
                }
            }
        });
        automaattiPaneeli.add(plus);

        miinus.setBounds(45, 150, 60, 60);
        miinus.setBackground(aloitustausta);
        miinus.setFont(xfont);
        miinus.setEnabled(false);
        miinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                seitviis.setVisible(false);
                sata.setVisible(false);
                kaksSataa.setVisible(false);
                kaksviis.setVisible(true);
                viiskyt.setVisible(true);
                kymmenen.setVisible(true);
                if (kymmenen.isVisible()) {
                    miinus.setEnabled(false);
                    plus.setEnabled(true);
                } else {
                    miinus.setEnabled(true);
                    plus.setEnabled(false);
                }
            }
        });
        automaattiPaneeli.add(miinus);

        JButton automaattix = new JButton("X");
        automaattix.setBounds(380, 15, 50, 50);
        automaattix.setBackground(aloitustausta);
        automaattix.setFont(xfont);
        automaattix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                automaattiPaneeli.setVisible(false);
            }
        });
        automaattiPaneeli.add(automaattix);

        JButton automaatti2 = new JButton("ASETA");
        automaatti2.setFont(smaller);
        automaatti2.setBackground(aloitustausta);
        automaatti2.setBounds(350, 685, 85, 55);
        automaatti2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                vaihtoehdot.setVisible(false);
                suljeVaihtoehto.setVisible(false);
                vaihtoehto.setVisible(true);
                automaattiPaneeli.setVisible(true);
                suuriVoitto.setVisible(false);
            }
        });

        JButton pysayta = new JButton("PYSÄYTÄ");
        pysayta.setFont(smaller);
        pysayta.setBackground(aloitustausta);
        pysayta.setBounds(350, 685, 85, 55);

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

        JLabel panos = new JLabel("PANOS");
        panos.setFont(small);
        panos.setBounds(808, 650, 80, 20);

        JButton panoksenVaihto = new JButton(db.getPanos() + "€");
        panoksenVaihto.setBounds(790, 685, 85, 55);
        panoksenVaihto.setBackground(aloitustausta);
        panoksenVaihto.setFont(xfont);
        panoksenVaihto.setEnabled(false);

        JButton isompiPanos = new JButton("+");
        isompiPanos.setBackground(aloitustausta);
        JButton pienempiPanos = new JButton("–");
        pienempiPanos.setBackground(aloitustausta);
        isompiPanos.setFont(small);
        isompiPanos.setBounds(880, 691, 45, 45);
        isompiPanos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                vaihtoehdot.setVisible(false);
                suljeVaihtoehto.setVisible(false);
                vaihtoehto.setVisible(true);
                automaattiPaneeli.setVisible(false);
                String luku = "0.20";
                int i = 1;
                try {
                    while (!luku.equals(db.getPanos())) {
                        luku = panokset[i];
                        i++;
                    }
                    db.setPanos(panokset[i]);
                    panoksenVaihto.setText(db.getPanos() + "€");
                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                        spin.setEnabled(false);
                    }
                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                        spin.setEnabled(true);
                    }

                    if (db.getPanos().equals("10.00")) {
                        isompiPanos.setEnabled(false);
                        pienempiPanos.setEnabled(true);
                    } else
                        isompiPanos.setEnabled(true);
                    pienempiPanos.setEnabled(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        pienempiPanos.setFont(small);
        pienempiPanos.setBounds(740, 691, 45, 45);
        pienempiPanos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                vaihtoehdot.setVisible(false);
                suljeVaihtoehto.setVisible(false);
                vaihtoehto.setVisible(true);
                automaattiPaneeli.setVisible(false);
                String luku = "10.00";
                int i = 8;
                try {
                    while (!luku.equals(db.getPanos())) {
                        luku = panokset[i];
                        i--;
                    }
                    db.setPanos(panokset[i]);
                    panoksenVaihto.setText(db.getPanos() + "€");
                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)
                            || Double.valueOf(db.getPanos()) < db.getSaldo()) {
                        spin.setEnabled(true);
                    }
                    if (db.getPanos().equals("0.20")) {
                        pienempiPanos.setEnabled(false);
                        isompiPanos.setEnabled(true);
                    } else {
                        pienempiPanos.setEnabled(true);
                        isompiPanos.setEnabled(true);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel saldo = new JLabel("SALDO");
        saldo.setFont(mainfont);
        saldo.setBounds(38, 685, 80, 20);

        JLabel alkuteksti = new JLabel("WINTER WONDERLAND");
        alkuteksti.setBounds(335, 20, 800, 60);
        alkuteksti.setFont(big);

        JButton x = new JButton("X");
        x.setBounds(1120, 25, 45, 45);
        x.setBackground(aloitustausta);
        x.setFont(xfont);
        x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (havityt > db.getHavitytKierrokset()) {
                        db.setHavityt(havityt);
                    }
                    if (alkuperainenSaldo > db.getSaldo()) {
                        int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                        db.setVoittoTappio(voittotappio + tulos);
                    } else {
                        if (alkuperainenSaldo < db.getSaldo()) {
                            int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                            db.setVoittoTappio(voittotappio + tulos);
                        } else {
                            if (alkuperainenSaldo == db.getSaldo()) {
                                int tulos = (int) (db.getSaldo() - alkuperainenSaldo);
                                db.setVoittoTappio(voittotappio + tulos);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                new VarmistaLopetus(paaPaneeli, frame);
                vaihtoehdot.setVisible(false);
                suljeVaihtoehto.setVisible(false);
                vaihtoehto.setVisible(true);
                automaattiPaneeli.setVisible(false);
                frame.setVisible(true);
            }
        });

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setBounds(0, 650, 1200, 10);

        reel1.setBounds(100, 85, 300, 525);
        reel1.setBackground(aloitustausta);

        reel2.setBounds(450, 85, 300, 525);
        reel2.setBackground(aloitustausta);

        reel3.setBounds(800, 85, 300, 525);
        reel3.setBackground(aloitustausta);

        BufferedImage[] kuviot = new BufferedImage[4];
        kuviot[0] = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/ympyra.png"));
        kuviot[1] = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/kolmio.png"));
        kuviot[2] = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/nelio.png"));
        kuviot[3] = ImageIO.read(new File("C:/Users/alexh/Downloads/WinterWonderland/hexa.png"));

        JLabel ekayla = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        ekayla.setBounds(60, 10, 180, 180);
        reel1.add(ekayla);

        JLabel ekakeski = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        ekakeski.setBounds(60, 180, 180, 180);
        reel1.add(ekakeski);

        JLabel ekaala = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        ekaala.setBounds(60, 350, 180, 180);
        reel1.add(ekaala);

        JLabel tokayla = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        tokayla.setBounds(60, 10, 180, 180);
        reel2.add(tokayla);

        JLabel tokakeski = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        tokakeski.setBounds(60, 180, 180, 180);
        reel2.add(tokakeski);

        JLabel tokaala = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        tokaala.setBounds(60, 350, 180, 180);
        reel2.add(tokaala);

        JLabel kolmasyla = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        kolmasyla.setBounds(60, 10, 180, 180);
        reel3.add(kolmasyla);

        JLabel kolmaskeski = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        kolmaskeski.setBounds(60, 180, 180, 180);
        reel3.add(kolmaskeski);

        JLabel kolmasala = new JLabel(new ImageIcon(kuviot[rand.nextInt(4)]));
        kolmasala.setBounds(60, 350, 180, 180);
        reel3.add(kolmasala);

        spin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    spin.setEnabled(false);
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {
                        int ey;
                        int ek;
                        int ea;
                        int ty;
                        int tk;
                        int ta;
                        int ky;
                        int kk;
                        int ka;
                        int value;
                        double kerroin = 0;

                        @Override
                        public void run() {
                            value = rand.nextInt(4);
                            ey = value;
                            ekayla.setIcon(new ImageIcon(kuviot[value]));
                            value = rand.nextInt(4);
                            ek = value;
                            ekakeski.setIcon(new ImageIcon(kuviot[value]));
                            value = rand.nextInt(4);
                            ea = value;
                            ekaala.setIcon(new ImageIcon(kuviot[value]));
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            value = rand.nextInt(4);
                            ty = value;
                            tokayla.setIcon(new ImageIcon(kuviot[value]));
                            value = rand.nextInt(4);
                            tk = value;
                            tokakeski.setIcon(new ImageIcon(kuviot[value]));
                            value = rand.nextInt(4);
                            ta = value;
                            tokaala.setIcon(new ImageIcon(kuviot[value]));
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            value = rand.nextInt(4);
                            ky = value;
                            kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                            value = rand.nextInt(4);
                            kk = value;
                            kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                            value = rand.nextInt(4);
                            ka = value;
                            kolmasala.setIcon(new ImageIcon(kuviot[value]));

                            try {

                                if ((ey == ty && ty == ky) || (ek == tk && tk == kk) || (ea == ta && ta == ka)
                                        || (ey == tk && tk == ka) || (ea == tk && tk == ky)) {
                                    if (ey == ty && ty == ky) {
                                        switch (ey) {
                                            case 0:
                                                db.setSaldo(db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 10;
                                                break;
                                            case 1:
                                                db.setSaldo(db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 5;
                                                break;
                                            case 2:
                                                db.setSaldo(db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 7;
                                                break;
                                            case 3:
                                                db.setSaldo(db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 3;
                                                break;
                                        }
                                    }
                                    if (ek == tk && tk == kk) {
                                        switch (ek) {
                                            case 0:
                                                db.setSaldo(db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 10;
                                                break;
                                            case 1:
                                                db.setSaldo(db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 5;
                                                break;
                                            case 2:
                                                db.setSaldo(db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 7;
                                                break;
                                            case 3:
                                                db.setSaldo(db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 3;
                                                break;
                                        }

                                    }
                                    if (ea == ta && ta == ka) {
                                        switch (ea) {
                                            case 0:
                                                db.setSaldo(db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 10;
                                                break;
                                            case 1:
                                                db.setSaldo(db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 5;
                                                break;
                                            case 2:
                                                db.setSaldo(db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 7;
                                                break;
                                            case 3:
                                                db.setSaldo(db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 3;
                                                break;
                                        }
                                    }
                                    if (ey == tk && tk == ka) {
                                        switch (ey) {
                                            case 0:
                                                db.setSaldo(db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 10;
                                                break;
                                            case 1:
                                                db.setSaldo(db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 5;
                                                break;
                                            case 2:
                                                db.setSaldo(db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 7;
                                                break;
                                            case 3:
                                                db.setSaldo(db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 3;
                                                break;
                                        }

                                    }
                                    if (ea == tk && tk == ky) {
                                        switch (ea) {
                                            case 0:
                                                db.setSaldo(db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 10;
                                                break;
                                            case 1:
                                                db.setSaldo(db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 5;
                                                break;
                                            case 2:
                                                db.setSaldo(db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 7;
                                                break;
                                            case 3:
                                                db.setSaldo(db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                kerroin = kerroin + 3;
                                                break;
                                        }
                                    }
                                    if (havityt > db.getHavitytKierrokset()) {
                                        db.setHavityt(havityt);
                                    }
                                    havityt = 0;
                                } else {
                                    havityt++;
                                }
                                if (kerroin > db.getSuurinVoittoKerroin()) {
                                    db.setSuurinKerroin(kerroin);
                                }
                                if (kerroin >= 20) {
                                    suuriVoitto.setVisible(true);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                Thread.sleep(500);
                                spin.setEnabled(true);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            timer.cancel();
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    spin.setEnabled(false);
                                }

                                if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                        && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                        && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                    spin.setEnabled(true);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });

        kymmenen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                spin.setEnabled(false);
                automaatti2.setVisible(false);
                automaattiPaneeli.setVisible(false);
                pysayta.setVisible(true);
                try {
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {

                        @Override
                        public void run() {
                            int ey;
                            int ek;
                            int ea;
                            int ty;
                            int tk;
                            int ta;
                            int ky;
                            int kk;
                            int ka;
                            int value;
                            int k = Integer.valueOf(kymmenen.getText()) - 1;
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    i = Integer.valueOf(kymmenen.getText());
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                } else {
                                    for (i = 0; i < Integer.valueOf(kymmenen.getText()); i++) {
                                        try {
                                            if (db.getKierrokset() == 0) {
                                                db.setKierrokset(1);
                                            } else {
                                                db.setKierrokset(db.getKierrokset() + 1);
                                            }
                                            String panos = db.getPanos();
                                            Double saldo = db.getSaldo();
                                            double panos2 = Double.valueOf(panos);
                                            Double uusisaldo = saldo - panos2;
                                            db.setSaldo(uusisaldo);
                                            saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        spin.setText(String.valueOf(k));
                                        spin.setFont(iso);

                                        pienempiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        isompiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        statistiikka.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        info.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        fontti.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        x.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        pelitilanvaihto.addMouseListener(new MouseAdapter() {
                                            public void mouseClicked(MouseEvent e) {
                                                i = Integer.valueOf(kymmenen.getText());
                                            }
                                        });

                                        pysayta.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                j = Integer.valueOf(kymmenen.getText());
                                                pysayta.setVisible(false);
                                                automaatti2.setVisible(true);
                                                i = j;
                                                spin.setText("PYÖRÄYTÄ");
                                                spin.setFont(smaller);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ey = value;
                                        ekayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ek = value;
                                        ekakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ea = value;
                                        ekaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ty = value;
                                        tokayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        tk = value;
                                        tokakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ta = value;
                                        tokaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ky = value;
                                        kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        kk = value;
                                        kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ka = value;
                                        kolmasala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            double kerroin = 0;
                                            if ((ey == ty && ty == ky) || (ek == tk && tk == kk)
                                                    || (ea == ta && ta == ka) || (ey == tk && tk == ka)
                                                    || (ea == tk && tk == ky)) {
                                                if (ey == ty && ty == ky) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ek == tk && tk == kk) {
                                                    switch (ek) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == ta && ta == ka) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ey == tk && tk == ka) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == tk && tk == ky) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (havityt > db.getHavitytKierrokset()) {
                                                    db.setHavityt(havityt);
                                                }
                                                havityt = 0;
                                            } else {
                                                havityt++;
                                            }
                                            if (kerroin > db.getSuurinVoittoKerroin()) {
                                                db.setSuurinKerroin(kerroin);
                                            }

                                            ilmoitus2.setText("VOITIT  "
                                                    + df.format(kerroin * Double.parseDouble(db.getPanos())) + "€");

                                            if (kerroin >= 20) {
                                                suuriVoitto.setVisible(true);
                                                i = Integer.valueOf(kymmenen.getText());
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        k--;
                                        if (Double.valueOf(
                                                db.getPanos()) > (db.getSaldo() - Double.valueOf(db.getPanos()))) {
                                            i = Integer.valueOf(kymmenen.getText());
                                        }

                                    }
                                    spin.setEnabled(true);
                                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                }
                                try {
                                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                        spin.setEnabled(false);
                                    }

                                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                        spin.setEnabled(true);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        kaksviis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                spin.setEnabled(false);
                automaatti2.setVisible(false);
                automaattiPaneeli.setVisible(false);
                pysayta.setVisible(true);
                try {
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {

                        @Override
                        public void run() {
                            int ey;
                            int ek;
                            int ea;
                            int ty;
                            int tk;
                            int ta;
                            int ky;
                            int kk;
                            int ka;
                            int value;
                            int k = Integer.valueOf(kaksviis.getText()) - 1;
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    i = Integer.valueOf(kaksviis.getText());
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                } else {
                                    for (i = 0; i < Integer.valueOf(kaksviis.getText()); i++) {
                                        try {
                                            if (db.getKierrokset() == 0) {
                                                db.setKierrokset(1);
                                            } else {
                                                db.setKierrokset(db.getKierrokset() + 1);
                                            }
                                            String panos = db.getPanos();
                                            Double saldo = db.getSaldo();
                                            double panos2 = Double.valueOf(panos);
                                            Double uusisaldo = saldo - panos2;
                                            db.setSaldo(uusisaldo);
                                            saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        spin.setText(String.valueOf(k));
                                        spin.setFont(iso);

                                        pienempiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        isompiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        statistiikka.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        info.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        fontti.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        x.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        pelitilanvaihto.addMouseListener(new MouseAdapter() {
                                            public void mouseClicked(MouseEvent e) {
                                                i = Integer.valueOf(kaksviis.getText());
                                            }
                                        });

                                        pysayta.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                j = Integer.valueOf(kaksviis.getText());
                                                pysayta.setVisible(false);
                                                automaatti2.setVisible(true);
                                                i = j;
                                                spin.setText("PYÖRÄYTÄ");
                                                spin.setFont(smaller);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ey = value;
                                        ekayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ek = value;
                                        ekakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ea = value;
                                        ekaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ty = value;
                                        tokayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        tk = value;
                                        tokakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ta = value;
                                        tokaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ky = value;
                                        kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        kk = value;
                                        kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ka = value;
                                        kolmasala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            double kerroin = 0;
                                            if ((ey == ty && ty == ky) || (ek == tk && tk == kk)
                                                    || (ea == ta && ta == ka) || (ey == tk && tk == ka)
                                                    || (ea == tk && tk == ky)) {
                                                if (ey == ty && ty == ky) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ek == tk && tk == kk) {
                                                    switch (ek) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == ta && ta == ka) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ey == tk && tk == ka) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == tk && tk == ky) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (havityt > db.getHavitytKierrokset()) {
                                                    db.setHavityt(havityt);
                                                }
                                                havityt = 0;
                                            } else {
                                                havityt++;
                                            }
                                            if (kerroin > db.getSuurinVoittoKerroin()) {
                                                db.setSuurinKerroin(kerroin);
                                            }

                                            ilmoitus2.setText("VOITIT  "
                                                    + df.format(kerroin * Double.parseDouble(db.getPanos())) + "€");

                                            if (kerroin >= 20) {
                                                suuriVoitto.setVisible(true);
                                                i = Integer.valueOf(kaksviis.getText());
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        k--;
                                        if (Double.valueOf(
                                                db.getPanos()) > (db.getSaldo() - Double.valueOf(db.getPanos()))) {
                                            i = Integer.valueOf(kaksviis.getText());
                                        }

                                    }
                                    spin.setEnabled(true);
                                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                }
                                try {
                                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                        spin.setEnabled(false);
                                    }

                                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                        spin.setEnabled(true);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        viiskyt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                spin.setEnabled(false);
                automaatti2.setVisible(false);
                automaattiPaneeli.setVisible(false);
                pysayta.setVisible(true);
                try {
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {

                        @Override
                        public void run() {
                            int ey;
                            int ek;
                            int ea;
                            int ty;
                            int tk;
                            int ta;
                            int ky;
                            int kk;
                            int ka;
                            int value;
                            int k = Integer.valueOf(viiskyt.getText()) - 1;
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    i = Integer.valueOf(viiskyt.getText());
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                } else {
                                    for (i = 0; i < Integer.valueOf(viiskyt.getText()); i++) {
                                        try {
                                            if (db.getKierrokset() == 0) {
                                                db.setKierrokset(1);
                                            } else {
                                                db.setKierrokset(db.getKierrokset() + 1);
                                            }
                                            String panos = db.getPanos();
                                            Double saldo = db.getSaldo();
                                            double panos2 = Double.valueOf(panos);
                                            Double uusisaldo = saldo - panos2;
                                            db.setSaldo(uusisaldo);
                                            saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        spin.setText(String.valueOf(k));
                                        spin.setFont(iso);

                                        pienempiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        isompiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        statistiikka.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        info.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        fontti.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        x.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        pelitilanvaihto.addMouseListener(new MouseAdapter() {
                                            public void mouseClicked(MouseEvent e) {
                                                i = Integer.valueOf(viiskyt.getText());
                                            }
                                        });

                                        pysayta.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                j = Integer.valueOf(viiskyt.getText());
                                                pysayta.setVisible(false);
                                                automaatti2.setVisible(true);
                                                i = j;
                                                spin.setText("PYÖRÄYTÄ");
                                                spin.setFont(smaller);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ey = value;
                                        ekayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ek = value;
                                        ekakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ea = value;
                                        ekaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ty = value;
                                        tokayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        tk = value;
                                        tokakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ta = value;
                                        tokaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ky = value;
                                        kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        kk = value;
                                        kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ka = value;
                                        kolmasala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            double kerroin = 0;
                                            if ((ey == ty && ty == ky) || (ek == tk && tk == kk)
                                                    || (ea == ta && ta == ka) || (ey == tk && tk == ka)
                                                    || (ea == tk && tk == ky)) {
                                                if (ey == ty && ty == ky) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ek == tk && tk == kk) {
                                                    switch (ek) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == ta && ta == ka) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ey == tk && tk == ka) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == tk && tk == ky) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (havityt > db.getHavitytKierrokset()) {
                                                    db.setHavityt(havityt);
                                                }
                                                havityt = 0;
                                            } else {
                                                havityt++;
                                            }
                                            if (kerroin > db.getSuurinVoittoKerroin()) {
                                                db.setSuurinKerroin(kerroin);
                                            }

                                            ilmoitus2.setText("VOITIT  "
                                                    + df.format(kerroin * Double.parseDouble(db.getPanos())) + "€");

                                            if (kerroin >= 20) {
                                                suuriVoitto.setVisible(true);
                                                i = Integer.valueOf(viiskyt.getText());
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        k--;
                                        if (Double.valueOf(
                                                db.getPanos()) > (db.getSaldo() - Double.valueOf(db.getPanos()))) {
                                            i = Integer.valueOf(viiskyt.getText());
                                        }

                                    }
                                    spin.setEnabled(true);
                                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                }
                                try {
                                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                        spin.setEnabled(false);
                                    }

                                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                        spin.setEnabled(true);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        seitviis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                spin.setEnabled(false);
                automaatti2.setVisible(false);
                automaattiPaneeli.setVisible(false);
                pysayta.setVisible(true);
                try {
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {

                        @Override
                        public void run() {
                            int ey;
                            int ek;
                            int ea;
                            int ty;
                            int tk;
                            int ta;
                            int ky;
                            int kk;
                            int ka;
                            int value;
                            int k = Integer.valueOf(seitviis.getText()) - 1;
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    i = Integer.valueOf(seitviis.getText());
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                } else {
                                    for (i = 0; i < Integer.valueOf(seitviis.getText()); i++) {
                                        try {
                                            if (db.getKierrokset() == 0) {
                                                db.setKierrokset(1);
                                            } else {
                                                db.setKierrokset(db.getKierrokset() + 1);
                                            }
                                            String panos = db.getPanos();
                                            Double saldo = db.getSaldo();
                                            double panos2 = Double.valueOf(panos);
                                            Double uusisaldo = saldo - panos2;
                                            db.setSaldo(uusisaldo);
                                            saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        spin.setText(String.valueOf(k));
                                        spin.setFont(iso);

                                        pienempiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        isompiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        statistiikka.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        info.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        fontti.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        x.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        pelitilanvaihto.addMouseListener(new MouseAdapter() {
                                            public void mouseClicked(MouseEvent e) {
                                                i = Integer.valueOf(seitviis.getText());
                                            }
                                        });

                                        pysayta.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                j = Integer.valueOf(seitviis.getText());
                                                pysayta.setVisible(false);
                                                automaatti2.setVisible(true);
                                                i = j;
                                                spin.setText("PYÖRÄYTÄ");
                                                spin.setFont(smaller);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ey = value;
                                        ekayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ek = value;
                                        ekakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ea = value;
                                        ekaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ty = value;
                                        tokayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        tk = value;
                                        tokakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ta = value;
                                        tokaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ky = value;
                                        kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        kk = value;
                                        kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ka = value;
                                        kolmasala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            double kerroin = 0;
                                            if ((ey == ty && ty == ky) || (ek == tk && tk == kk)
                                                    || (ea == ta && ta == ka) || (ey == tk && tk == ka)
                                                    || (ea == tk && tk == ky)) {
                                                if (ey == ty && ty == ky) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ek == tk && tk == kk) {
                                                    switch (ek) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == ta && ta == ka) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ey == tk && tk == ka) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == tk && tk == ky) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (havityt > db.getHavitytKierrokset()) {
                                                    db.setHavityt(havityt);
                                                }
                                                havityt = 0;
                                            } else {
                                                havityt++;
                                            }
                                            if (kerroin > db.getSuurinVoittoKerroin()) {
                                                db.setSuurinKerroin(kerroin);
                                            }

                                            ilmoitus2.setText("VOITIT  "
                                                    + df.format(kerroin * Double.parseDouble(db.getPanos())) + "€");

                                            if (kerroin >= 20) {
                                                suuriVoitto.setVisible(true);
                                                i = Integer.valueOf(seitviis.getText());
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        k--;
                                        if (Double.valueOf(
                                                db.getPanos()) > (db.getSaldo() - Double.valueOf(db.getPanos()))) {
                                            i = Integer.valueOf(seitviis.getText());
                                        }

                                    }
                                    spin.setEnabled(true);
                                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                }
                                try {
                                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                        spin.setEnabled(false);
                                    }

                                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                        spin.setEnabled(true);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        sata.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                spin.setEnabled(false);
                automaatti2.setVisible(false);
                automaattiPaneeli.setVisible(false);
                pysayta.setVisible(true);
                try {
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {

                        @Override
                        public void run() {
                            int ey;
                            int ek;
                            int ea;
                            int ty;
                            int tk;
                            int ta;
                            int ky;
                            int kk;
                            int ka;
                            int value;
                            int k = Integer.valueOf(sata.getText()) - 1;
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    i = Integer.valueOf(sata.getText());
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                } else {
                                    for (i = 0; i < Integer.valueOf(sata.getText()); i++) {
                                        try {
                                            if (db.getKierrokset() == 0) {
                                                db.setKierrokset(1);
                                            } else {
                                                db.setKierrokset(db.getKierrokset() + 1);
                                            }
                                            String panos = db.getPanos();
                                            Double saldo = db.getSaldo();
                                            double panos2 = Double.valueOf(panos);
                                            Double uusisaldo = saldo - panos2;
                                            db.setSaldo(uusisaldo);
                                            saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        spin.setText(String.valueOf(k));
                                        spin.setFont(iso);

                                        pienempiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        isompiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        statistiikka.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        info.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        fontti.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        x.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        pelitilanvaihto.addMouseListener(new MouseAdapter() {
                                            public void mouseClicked(MouseEvent e) {
                                                i = Integer.valueOf(sata.getText());
                                            }
                                        });

                                        pysayta.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                j = Integer.valueOf(sata.getText());
                                                pysayta.setVisible(false);
                                                automaatti2.setVisible(true);
                                                i = j;
                                                spin.setText("PYÖRÄYTÄ");
                                                spin.setFont(smaller);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ey = value;
                                        ekayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ek = value;
                                        ekakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ea = value;
                                        ekaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ty = value;
                                        tokayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        tk = value;
                                        tokakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ta = value;
                                        tokaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ky = value;
                                        kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        kk = value;
                                        kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ka = value;
                                        kolmasala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            double kerroin = 0;
                                            if ((ey == ty && ty == ky) || (ek == tk && tk == kk)
                                                    || (ea == ta && ta == ka) || (ey == tk && tk == ka)
                                                    || (ea == tk && tk == ky)) {
                                                if (ey == ty && ty == ky) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ek == tk && tk == kk) {
                                                    switch (ek) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == ta && ta == ka) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ey == tk && tk == ka) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == tk && tk == ky) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (havityt > db.getHavitytKierrokset()) {
                                                    db.setHavityt(havityt);
                                                }
                                                havityt = 0;
                                            } else {
                                                havityt++;
                                            }
                                            if (kerroin > db.getSuurinVoittoKerroin()) {
                                                db.setSuurinKerroin(kerroin);
                                            }

                                            ilmoitus2.setText("VOITIT  "
                                                    + df.format(kerroin * Double.parseDouble(db.getPanos())) + "€");

                                            if (kerroin >= 20) {
                                                suuriVoitto.setVisible(true);
                                                i = Integer.valueOf(sata.getText());
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        k--;
                                        if (Double.valueOf(
                                                db.getPanos()) > (db.getSaldo() - Double.valueOf(db.getPanos()))) {
                                            i = Integer.valueOf(sata.getText());
                                        }

                                    }
                                    spin.setEnabled(true);
                                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                }
                                try {
                                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                        spin.setEnabled(false);
                                    }

                                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                        spin.setEnabled(true);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        kaksSataa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                spin.setEnabled(false);
                automaatti2.setVisible(false);
                automaattiPaneeli.setVisible(false);
                pysayta.setVisible(true);
                try {
                    Timer timer = new Timer();
                    TimerTask timertask = new TimerTask() {

                        @Override
                        public void run() {
                            int ey;
                            int ek;
                            int ea;
                            int ty;
                            int tk;
                            int ta;
                            int ky;
                            int kk;
                            int ka;
                            int value;
                            int k = Integer.valueOf(kaksSataa.getText()) - 1;
                            try {
                                if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                    i = Integer.valueOf(kaksSataa.getText());
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                } else {
                                    for (i = 0; i < Integer.valueOf(kaksSataa.getText()); i++) {
                                        try {
                                            if (db.getKierrokset() == 0) {
                                                db.setKierrokset(1);
                                            } else {
                                                db.setKierrokset(db.getKierrokset() + 1);
                                            }
                                            String panos = db.getPanos();
                                            Double saldo = db.getSaldo();
                                            double panos2 = Double.valueOf(panos);
                                            Double uusisaldo = saldo - panos2;
                                            db.setSaldo(uusisaldo);
                                            saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        spin.setText(String.valueOf(k));
                                        spin.setFont(iso);

                                        pienempiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        isompiPanos.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        statistiikka.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        info.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        fontti.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        x.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        pelitilanvaihto.addMouseListener(new MouseAdapter() {
                                            public void mouseClicked(MouseEvent e) {
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }
                                        });

                                        pysayta.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent ae) {
                                                j = Integer.valueOf(kaksSataa.getText());
                                                pysayta.setVisible(false);
                                                automaatti2.setVisible(true);
                                                i = j;
                                                spin.setText("PYÖRÄYTÄ");
                                                spin.setFont(smaller);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ey = value;
                                        ekayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ek = value;
                                        ekakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ea = value;
                                        ekaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ty = value;
                                        tokayla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        tk = value;
                                        tokakeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ta = value;
                                        tokaala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        value = rand.nextInt(4);
                                        ky = value;
                                        kolmasyla.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        kk = value;
                                        kolmaskeski.setIcon(new ImageIcon(kuviot[value]));
                                        value = rand.nextInt(4);
                                        ka = value;
                                        kolmasala.setIcon(new ImageIcon(kuviot[value]));
                                        try {
                                            double kerroin = 0;
                                            if ((ey == ty && ty == ky) || (ek == tk && tk == kk)
                                                    || (ea == ta && ta == ka) || (ey == tk && tk == ka)
                                                    || (ea == tk && tk == ky)) {
                                                if (ey == ty && ty == ky) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ek == tk && tk == kk) {
                                                    switch (ek) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == ta && ta == ka) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (ey == tk && tk == ka) {
                                                    switch (ey) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }

                                                }
                                                if (ea == tk && tk == ky) {
                                                    switch (ea) {
                                                        case 0:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 10 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 10;
                                                            break;
                                                        case 1:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 5 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 5;
                                                            break;
                                                        case 2:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 7 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 7;
                                                            break;
                                                        case 3:
                                                            db.setSaldo(
                                                                    db.getSaldo() + 3 * Double.valueOf(db.getPanos()));
                                                            kerroin = kerroin + 3;
                                                            break;
                                                    }
                                                }
                                                if (havityt > db.getHavitytKierrokset()) {
                                                    db.setHavityt(havityt);
                                                }
                                                havityt = 0;
                                            } else {
                                                havityt++;
                                            }
                                            if (kerroin > db.getSuurinVoittoKerroin()) {
                                                db.setSuurinKerroin(kerroin);
                                            }

                                            ilmoitus2.setText("VOITIT  "
                                                    + df.format(kerroin * Double.parseDouble(db.getPanos())) + "€");

                                            if (kerroin >= 20) {
                                                suuriVoitto.setVisible(true);
                                                i = Integer.valueOf(kaksSataa.getText());
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        k--;
                                        if (Double.valueOf(
                                                db.getPanos()) > (db.getSaldo() - Double.valueOf(db.getPanos()))) {
                                            i = Integer.valueOf(kaksSataa.getText());
                                        }

                                    }
                                    spin.setEnabled(true);
                                    saldonSuuruus.setText(df.format(db.getSaldo()) + "€");
                                    pysayta.setVisible(false);
                                    automaatti2.setVisible(true);
                                    spin.setText("PYÖRÄYTÄ");
                                    spin.setFont(smaller);
                                    timer.cancel();
                                }
                                try {
                                    if (Double.valueOf(db.getPanos()) > db.getSaldo()) {
                                        spin.setEnabled(false);
                                    }

                                    if (db.getPanos().charAt(0) == df.format(db.getSaldo()).charAt(0)
                                            && db.getPanos().charAt(2) == df.format(db.getSaldo()).charAt(2)
                                            && db.getPanos().charAt(3) == df.format(db.getSaldo()).charAt(3)) {
                                        spin.setEnabled(true);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                        }
                    };
                    timer.scheduleAtFixedRate(timertask, 0, TASK_DURATION_IN_SECONDS);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        paaPaneeli.setLayout(null);
        automaattiPaneeli.setLayout(null);
        vaihtoehdot.setLayout(null);
        reel1.setLayout(null);
        reel2.setLayout(null);
        reel3.setLayout(null);
        paaPaneeli.add(separator);
        paaPaneeli.add(x);
        paaPaneeli.add(spin);
        paaPaneeli.add(pelaus);
        paaPaneeli.add(automaatti);
        paaPaneeli.add(automaatti2);
        paaPaneeli.add(pysayta);
        paaPaneeli.add(panos);
        paaPaneeli.add(isompiPanos);
        paaPaneeli.add(pienempiPanos);
        paaPaneeli.add(saldo);
        paaPaneeli.add(vaihtoehto);
        paaPaneeli.add(suljeVaihtoehto);
        paaPaneeli.add(panoksenVaihto);
        paaPaneeli.add(saldonSuuruus);
        paaPaneeli.add(alkuteksti);
        paaPaneeli.add(reel1);
        paaPaneeli.add(reel2);
        paaPaneeli.add(reel3);
        paaPaneeli.add(pelitilanvaihto);
        paaPaneeli.add(alleviivaus);
        frame.add(paaPaneeli);

    }
}