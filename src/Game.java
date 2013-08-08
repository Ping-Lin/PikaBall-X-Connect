import javax.swing.JFrame;

public class Game extends JFrame{
	
	public Game(){
		this.add(new PlayBoard());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //click X to close
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);   //to medium(置中)
		this.setTitle("PikaBall X Connect");
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
