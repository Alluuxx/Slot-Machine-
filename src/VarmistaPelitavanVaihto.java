import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class VarmistaPelitavanVaihto extends JFrame {
    private JPanel edellinenNakyma;
    private JPanel varmistaVaihto = new JPanel();

    //Saa parametreinä käytettävän framen, edellisen näkymän paneelin ja koodin, jonka mukaan vaihtaa oikeaan näkymään.
    //Luo myös tästä näkymästä oman paneelin, joka lisätään frameen ja on näkyvissä siihen asti kunnes valinta on tehty, joko
    //pelitavan vaihtamisen tai sen säilyttämisen suhteen.
    public VarmistaPelitavanVaihto(JPanel panel, JFrame frame, String code) {
        edellinenNakyma = panel;
        Color aloitustausta = new Color(199, 208, 216);
        Color valkonen = new Color(255, 255, 255);
        Font big = new Font("Arial", Font.BOLD, 40);
        Font mainfont = new Font("Arial", Font.BOLD, 30);

        JLabel alkuteksti = new JLabel("Haluatko varmasti vaihtaa pelitavan?");
        alkuteksti.setBounds(300, 200, 800, 60);
        alkuteksti.setFont(big);

        JButton kylla = new JButton("KYLLÄ");
        kylla.setBounds(300, 400, 250, 75);
        kylla.setBackground(valkonen);
        kylla.setFont(mainfont);
        kylla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (code.equals("leikki")) {
                    try {
                        panel.setVisible(false);
                        varmistaVaihto.setVisible(false);
                        new TalletusNakyma(frame);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    panel.setVisible(false);
                    new LeikkirahanAloitusNaytto(varmistaVaihto, frame);
                }
            }
        });

        JButton ei = new JButton("EI");
        ei.setBounds(650, 400, 250, 75);
        ei.setBackground(valkonen);
        ei.setFont(mainfont);
        ei.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                varmistaVaihto.setVisible(false);
                edellinenNakyma.setVisible(true);
            }
        });

        varmistaVaihto.setLayout(null);
        varmistaVaihto.add(kylla);
        varmistaVaihto.add(alkuteksti);
        varmistaVaihto.add(ei);
        varmistaVaihto.setBackground(aloitustausta);
        edellinenNakyma.setVisible(false);
        frame.add(varmistaVaihto);
    }
}
