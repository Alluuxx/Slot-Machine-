import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class TalletusNakyma extends JFrame {
    private JPanel panel = new JPanel();
    private double summaTietokantaan = 0;
    Database db = Database.getInstance();

    //Saa parametreinä käytettävän framen ja toimii myös pelin ensimmäisenä näyttönä. Asettaa aina pelikerran alkaessa fontin koon keskikokoon.
    public TalletusNakyma(JFrame frame) throws IOException, SQLException {
        db.setFont(2);
        Color aloitustausta = new Color(199, 208, 216);
        Color aloitusjatka = new Color(166, 166, 166);
        Color valkonen = new Color(255, 255, 255);
        Font big = new Font("Arial", Font.BOLD, 60);
        Font mainfont = new Font("Arial", Font.BOLD, 30);
        Font newfont = new Font("Arial", Font.BOLD, 20);
        Font vaihtofont = new Font("Arial", Font.BOLD, 10);
        Font viivafont = new Font("Arial", Font.BOLD, 20);

        JLabel pelitilanvaihto = new JLabel("Haluatko sittenkin pelata leikkirahalla? Paina");
        pelitilanvaihto.setBounds(12, 1, 250, 20);
        pelitilanvaihto.setFont(vaihtofont);

        JTextField summa = new JTextField();
        summa.setBounds(467, 335, 250, 100);
        summa.setBackground(aloitustausta);
        summa.setFont(big);
        summa.setHorizontalAlignment(JTextField.CENTER);
        panel.add(summa);

        JLabel alleviivaus = new JLabel("_____");
        alleviivaus.setBounds(243, 2, 100, 20);
        alleviivaus.setFont(vaihtofont);

        JLabel tasta = new JLabel("tästä.");
        tasta.setBounds(243, 1, 100, 20);
        tasta.setFont(vaihtofont);
        tasta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String code = "oikea";
                new VarmistaPelitavanVaihto(panel, frame, code);
                frame.setVisible(true);
            }
        });

        JLabel alkuteksti = new JLabel("SYÖTÄ SUMMA, JOLLA HALUAT PELATA");
        alkuteksti.setBounds(390, 200, 600, 60);
        alkuteksti.setFont(newfont);

        JLabel minimimaksimi = new JLabel("(min. 10€, max. 500€)");
        minimimaksimi.setBounds(498, 435, 250, 60);
        minimimaksimi.setFont(newfont);

        JLabel panosviiva = new JLabel("__________________________");
        panosviiva.setBounds(450, 400, 400, 60);
        panosviiva.setFont(viivafont);

        JLabel suurinvoitto = new JLabel("VOITTO JOPA 10 000x");
        suurinvoitto.setBounds(484, 700, 300, 30);
        suurinvoitto.setFont(newfont);

        RoundButton info = new RoundButton("i");
        info.setBounds(25, 685, 56, 56);
        info.setFont(mainfont);
        info.setBackground(valkonen);

        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                InfoNakyma nakyma = new InfoNakyma(panel);
                nakyma.InfoNakyma1(frame);
                frame.setVisible(true);
            }
        });

        Icon fonttiicon = new ImageIcon("C:/Users/alexh/Downloads/WinterWonderland/fontti.png");
        RoundButton fontti = new RoundButton(fonttiicon);
        fontti.setBounds(1100, 685, 56, 56);
        fontti.setFont(mainfont);
        fontti.setBackground(valkonen);
        fontti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new FontinVaihto(frame, panel);
                frame.setVisible(true);
            }
        });

        JButton jatka = new JButton("JATKA");
        jatka.setBounds(518, 550, 150, 75);
        jatka.setFont(mainfont);
        jatka.setBackground(aloitusjatka);
        jatka.setEnabled(false);
        jatka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    db.setSaldo(summaTietokantaan);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                new OikeanrahanAloitusNaytto(panel, frame);
                frame.setVisible(true);
            }
        });
        summa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                double talletusSumma = Double.parseDouble(summa.getText());
                summaTietokantaan = talletusSumma;
                if (talletusSumma >= 10 && talletusSumma <= 500) {
                    jatka.setBackground(valkonen);
                    jatka.setEnabled(true);
                } else {
                    jatka.setBackground(aloitusjatka);
                    jatka.setEnabled(false);
                }
            }
        });

        panel.setLayout(null);
        panel.add(jatka);
        panel.add(info);
        panel.add(fontti);
        panel.add(suurinvoitto);
        panel.add(panosviiva);
        panel.add(minimimaksimi);
        panel.add(alkuteksti);
        panel.add(pelitilanvaihto);
        panel.add(tasta);
        panel.add(alleviivaus);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(aloitustausta);
        frame.add(panel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
