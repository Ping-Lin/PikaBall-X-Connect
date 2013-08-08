import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class PlayBoard extends JPanel{
	private Timer timer;   //control the game cycle
	private Timer timer2;   //control the ball cycle
	private Pika pika1;
	private Ball pikaBall;
	
	public PlayBoard(){
		this.addKeyListener(new tempAdapter());   //add listener to board
		this.setFocusable(true);   //set for controlling the component on the game board
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);   //memory associated 
	
		pika1 = new Pika();
		pikaBall = new Ball();
		timer = new Timer();
		timer2 = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 0, 10);
		timer2.scheduleAtFixedRate(new BallTask(), 0, 100);
	}
	
	private class tempAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			pika1.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
		pika1.keyReleased(e);
		}
	}
	
	class ScheduleTask extends TimerTask{
		public void run(){
			pika1.move();
			pikaBall.move();
			collision();
			repaint();
		}
	}
	
	class BallTask extends TimerTask{
		public void run(){
			pikaBall.spin();
			repaint();
		}
	}
	
//	public void addNotify(){
//		super.addNotify();
//	}
	
	private void collision(){
		Rectangle r1 = pika1.getBounds();
		Rectangle r2 = pikaBall.getBounds();
		
		
		if(r1.intersects(r2)){
			float d = (pikaBall.getX()+pikaBall.getWidth()/2)-(pika1.getX()+pika1.getWidth()/2);
		
			/*
			 * power hit
			 */
			if(pika1.getPowHit() == 1){
				pikaBall.bePowHit(1);
			}
			
			if(pika1.getPowHit() == -1){
				pikaBall.bePowHit(-1);
			}
			
			if(pika1.getPowHit() == 2){
				pikaBall.bePowHit(2);
			}
			
			if(pika1.getPowHit() == -2){
				pikaBall.bePowHit(-2);
			}
			if(pika1.getPowHit() == 0){
				pikaBall.bePowHit(0);
			}
			//pikaBall.hit(d);
		}
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		g2D.drawImage(pika1.getImage(), pika1.getX(), pika1.getY(), this);
		g2D.rotate(Math.toDegrees(pikaBall.getAngle()), pikaBall.getX()+pikaBall.getWidth()/2, pikaBall.getY()+pikaBall.getHeight()/2);
		g2D.drawImage(pikaBall.getImage(), pikaBall.getX(), pikaBall.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
}
