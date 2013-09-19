import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConnectMenu {
    JFrame frame;
    JPanel bgPanel;
    JButton singleORcon = new JButton();
    ImageIcon img=new ImageIcon();
    ConnectMenu(){
        frame = new JFrame();
        frame.setSize(600, 280);
        bgPanel = new JPanel();
        bgPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 50));
        
        img=new ImageIcon("src/source/ConnectBg1.png");
        
        singleORcon=new JButton(img);
        singleORcon.setPreferredSize(new Dimension(240,120)); 
        singleORcon.addMouseListener(new Listener());
        bgPanel.add(singleORcon);

        frame.setTitle("連線");
        frame.add(bgPanel);
        frame.setSize(600, 280);
        frame.setLocationRelativeTo(frame); //視窗置中
        frame.setResizable(false);        //不能調整視窗
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //按X的結果
    }
    
    class Listener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
        	if(e.getSource() == singleORcon){
                frame.dispose();
                new Network();
            }

        }
    }
}

