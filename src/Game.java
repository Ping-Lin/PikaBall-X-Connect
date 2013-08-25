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
	
	public Game(ServerSocket server, Socket client){
		this.add(new PlayBoard(server, client));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //click X to close
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);   //to medium(置中)
		this.setTitle("PikaBall X Connect");
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public Game(Socket client){
		this.add(new PlayBoard(client));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //click X to close
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);   //to medium(置中)
		this.setTitle("PikaBall X Connect");
		this.setResizable(false);
		this.setVisible(true);
	}
}
