import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Statistiikka extends JFrame {
    private JPanel edellinenNakyma;
    Database db = Database.getInstance();
    private JPanel statistiikka = new JPanel();

    // Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
    // lisätään frameen ja on näkyvissä siihen asti kunnes x nappia painetaan ja siirrytään aiempaan näkymään.
    public Statistiikka(JPanel panel, JFrame frame) throws SQLException {
        edellinenNakyma = panel;
        Color aloitustausta = new Color(199, 208, 216);
        Color valkonen = new Color(255, 255, 255);
        Font mainfont = new Font("Arial", Font.BOLD, 45);
        Font newfont = new Font("Arial", Font.BOLD, 30);

        JLabel alkuteksti = new JLabel("SINUN PELITIETOSI");
        alkuteksti.setBounds(350, 30, 800, 60);
        alkuteksti.setFont(mainfont);

        JLabel pelattuMaara = new JLabel("PELATTUJEN KIERROSTEN MÄÄRÄ: " + String.valueOf(db.getKierrokset()));
        pelattuMaara.setBounds(20, 180, 700, 30);
        pelattuMaara.setFont(newfont);

        JLabel enitenHavittyja = new JLabel(
                "ENITEN HÄVITTYJÄ KIERROKSIA PUTKEEN: " + String.valueOf(db.getHavitytKierrokset()));
        enitenHavittyja.setBounds(20, 230, 700, 30);
        enitenHavittyja.setFont(newfont);

        JLabel suurinVoittokerroin = new JLabel(
                "SUURIN VOITTOKERROIN: " + String.valueOf(db.getSuurinVoittoKerroin()) + " x");
        suurinVoittokerroin.setBounds(20, 280, 700, 30);
        suurinVoittokerroin.setFont(newfont);

        JLabel voittoTappio = new JLabel("VOITTO-TAPPIO-TILANNE: " + String.valueOf(db.getVoittoTappioTilanne()) + "€");
        voittoTappio.setBounds(20, 330, 700, 30);
        voittoTappio.setFont(newfont);

        JButton x = new JButton("X");
        x.setBounds(1120, 15, 45, 45);
        x.setBackground(valkonen);
        x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                statistiikka.setVisible(false);
                edellinenNakyma.setVisible(true);
            }
        });

        statistiikka.setLayout(null);
        statistiikka.add(pelattuMaara);
        statistiikka.add(x);
        statistiikka.add(enitenHavittyja);
        statistiikka.add(suurinVoittokerroin);
        statistiikka.add(voittoTappio);
        statistiikka.add(alkuteksti);
        statistiikka.setBackground(aloitustausta);
        edellinenNakyma.setVisible(false);
        frame.add(statistiikka);
    }
}
