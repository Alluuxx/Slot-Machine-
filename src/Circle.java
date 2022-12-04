import javax.swing.JPanel;
import java.awt.*;

public class Circle extends JPanel{
    public void paint(Graphics g){
        setSize(500,500);
        g.drawOval(100,100,50,50);
    }
}
