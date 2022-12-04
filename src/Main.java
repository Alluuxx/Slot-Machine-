import javax.swing.*;

public class Main extends JFrame{
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Winter Wonderland");
        frame.getContentPane();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        TalletusNakyma talletus = new TalletusNakyma(frame);
        frame.add(talletus.getPanel());
        frame.setVisible(true);
        
    }
}
