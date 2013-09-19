import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;

public class Game extends JFrame{
	
	public Game(){
		this.add(new PlayBoard());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //click X to close
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);   //to medium(置中)
		this.setTitle("PikaBall X Connect");
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public Game(InetAddress chost, int port, int isServer){
		this.add(new PlayBoard2(chost, port));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //click X to close
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);   //to medium(置中)
		this.setTitle("PikaBall X Connect Server");
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public Game(InetAddress host, int port){
		this.add(new PlayBoard(host, port));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //click X to close
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);   //to medium(置中)
		this.setTitle("PikaBall X Connect Client");
		this.setResizable(false);
		this.setVisible(true);
	}
}
