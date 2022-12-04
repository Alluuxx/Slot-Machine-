import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class FontinVaihto extends JFrame {
  JPanel panel = new JPanel();
  private JPanel edellinenNakyma;
  Database db = Database.getInstance();
  int fontti = 0;

  //Saa parametreinä käytettävän framen ja edellisen näkymän paneelin. Luo myös tästä näkymästä oman paneelin, joka
  //lisätään frameen ja on näkyvissä siihen asti kunnes x nappia painetaan ja siirrytään aiempaan näkymään.
  public FontinVaihto(JFrame frame, JPanel paneeli) {
    edellinenNakyma = paneeli;
    Color aloitustausta = new Color(199, 208, 216);
    Color valkonen = new Color(255, 255, 255);
    Color musta = new Color(0, 0, 0);
    Font mainfont = new Font("Arial", Font.BOLD, 30);
    Font midfont = new Font("Arial", Font.BOLD, 25);
    Font newfont = new Font("Arial", Font.BOLD, 20);
    Font xfont = new Font("Arial", Font.BOLD, 15);

    JLabel alkuteksti = new JLabel("VALITSE HALUAMASI FONTIN SUURUUS");
    alkuteksti.setBounds(390, 200, 400, 60);
    alkuteksti.setFont(newfont);

    Border border = BorderFactory.createLineBorder(musta, 5);

    JButton pieni = new JButton("PIENI");
    pieni.setBounds(250, 350, 190, 60);
    pieni.setBackground(valkonen);
    pieni.setFont(newfont);

    JButton keskikoko = new JButton("KESKIKOKO");
    keskikoko.setBounds(500, 350, 190, 60);
    keskikoko.setBackground(valkonen);
    keskikoko.setFont(midfont);

    JButton suuri = new JButton("SUURI");
    suuri.setBounds(750, 350, 190, 60);
    suuri.setBackground(valkonen);
    suuri.setFont(mainfont);

    try {
      fontti = db.getFont();
      if (fontti == 1) {
        pieni.setBorder(border);
        keskikoko.setBorder(null);
        suuri.setBorder(null);
      } else {
        if (fontti == 2) {
          pieni.setBorder(null);
          keskikoko.setBorder(border);
          suuri.setBorder(null);
        } else {
          if (fontti == 3) {
            pieni.setBorder(null);
            keskikoko.setBorder(null);
            suuri.setBorder(border);
          }
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    suuri.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        pieni.setBorder(null);
        keskikoko.setBorder(null);
        suuri.setBorder(border);
        fontti = 3;
        try {
          db.setFont(fontti);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });

    keskikoko.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        pieni.setBorder(null);
        keskikoko.setBorder(border);
        suuri.setBorder(null);
        fontti = 2;
        try {
          db.setFont(fontti);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });

    pieni.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        pieni.setBorder(border);
        keskikoko.setBorder(null);
        suuri.setBorder(null);
        fontti = 1;
        try {
          db.setFont(fontti);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });

    JButton x = new JButton("X");
    x.setBounds(1120, 25, 45, 45);
    x.setBackground(valkonen);
    x.setFont(xfont);
    x.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        panel.setVisible(false);
        edellinenNakyma.setVisible(true);
        pieni.setBorder(null);
        keskikoko.setBorder(null);
        suuri.setBorder(null);
      }
    });

    JButton jatka = new JButton("JATKA");
    jatka.setBounds(518, 550, 150, 75);
    jatka.setFont(mainfont);
    jatka.setBackground(valkonen);
    jatka.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        panel.setVisible(false);
        edellinenNakyma.setVisible(true);
        try {
          db.setFont(fontti);
        } catch (SQLException e) {
          e.printStackTrace();
        }

      }
    });

    panel.setLayout(null);
    panel.add(jatka);
    panel.add(x);
    panel.add(pieni);
    panel.add(keskikoko);
    panel.add(suuri);
    panel.add(alkuteksti);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panel.setBackground(aloitustausta);
    frame.add(panel);
    paneeli.setVisible(false);
    frame.setVisible(true);
  }
}
