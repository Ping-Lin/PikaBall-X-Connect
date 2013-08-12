import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PlayBoard extends JPanel{
	private Timer timer;   //control the game cycle
	private Timer timer2;   //control the ball cycle
	private Pika pika1;
	private Pika2 pika2;
	private Ball pikaBall;
	private Stick stick;
	private Image bg;
	private Record record;
//	Line2D.Double leftLine = new Line2D.Double(0, 600, 400, 600);
//	Line2D.Double rightLine = new Line2D.Double(417, 600, 800, 600);
	
	public PlayBoard(){
		ImageIcon icon = new ImageIcon("src/source/bg.jpg");
		bg = icon.getImage();
		this.addKeyListener(new tempAdapter());   //add listener to board
		this.addKeyListener(new ballListener());
		this.setFocusable(true);   //set for controlling the component on the game board
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);   //memory associated
		this.setLayout(null);
		this.setOpaque(false);
	
		pika1 = new Pika();
		pika2 = new Pika2();
		pikaBall = new Ball();
		stick = new Stick();
		timer = new Timer();
		timer2 = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 3000, 10);
		timer2.scheduleAtFixedRate(new BallTask(), 3000, 100);
			
		record = new Record();
		this.add(record);
		
	}
	
	private class tempAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			pika1.keyPressed(e);
			pika2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
			pika1.keyReleased(e);
			pika2.keyReleased(e);
		}
	}
	
	private class ballListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			pikaBall.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
		pikaBall.keyReleased(e);
		}
	}
	
	class ScheduleTask extends TimerTask{
		public void run(){
			pika1.move();
			pika2.move();
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
	
	class RestartTask1 extends TimerTask{   //球落在左邊，2P贏
		public void run(){
			pikaBall.restart(1);
			pika1.restart();
			pika2.restart();
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleTask(), 1500, 10);
			
		}
	}
	
	class RestartTask2 extends TimerTask{   //球落在右邊，1P贏
		public void run(){
			pikaBall.restart(2);
			pika1.restart();
			pika2.restart();
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleTask(), 1500, 10);
		}
	}
	
//	public void addNotify(){
//		super.addNotify();
//	}
	
	private void collision(){
		Rectangle r1 = pika1.getBounds();
		Rectangle r2 = pikaBall.getBounds();
		Rectangle r3 = stick.getBounds();
		Rectangle r4 = pika2.getBounds();
		Line2D.Double leftLine = new Line2D.Double(0, 600, 410, 600);
		Line2D.Double rightLine = new Line2D.Double(410, 600, 800, 600);
		
		if(r1.intersects(r2)){
			float d = ((float)pikaBall.getX()+(float)pikaBall.getWidth()/2f)-((float)pika1.getX()+(float)pika1.getWidth()/2f);		
			pikaBall.hit(d, pika1.getPowHit());
		}
		else if(r4.intersects(r2)){
			float d = ((float)pikaBall.getX()+(float)pikaBall.getWidth()/2f)-((float)pika2.getX()+(float)pika2.getWidth()/2f);		
			pikaBall.hit(d, pika2.getPowHit());
		}
		
		/*if(r2.intersects(r3)){
			pikaBall.hitStick();
		}*/

		if(r2.intersectsLine(leftLine)){   //球落在左邊，2P贏
			record.plusCount2();
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleTask(), 0, 60);
			
			Timer timer3 = new Timer();
			timer3.schedule(new RestartTask1(), 1500);		
		}
		else if(r2.intersectsLine(rightLine)){   //球落在右邊，1P贏
			record.plusCount1();
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleTask(), 0, 60);
			
			Timer timer3 = new Timer();
			timer3.schedule(new RestartTask2(), 1500);
		}
	}
	
	public void paint(Graphics g){
		
		g.drawImage(bg, 0, 0, this);   //draw background
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2D.setComposite(comp);
		
		if(pika1.getIfPu() == false){
			g2D.drawImage(pika1.getImage(), pika1.getX(), pika1.getY(), 100, 100, this);
		}
		else{
			g2D.drawImage(pika1.getImage2(), pika1.getX(), pika1.getY(), 100, 100, this);
		}
	
		if(pika2.getIfPu() == false){
			g2D.drawImage(pika2.getImage(), pika2.getX(), pika2.getY(), 100, 100, this);
		}
		else{
			g2D.drawImage(pika2.getImage2(), pika2.getX(), pika2.getY(), 100, 100, this);
		}
		
//		g2D.draw(leftLine);
//		g2D.draw(rightLine);
		
		g2D.drawImage(stick.getImage(), stick.getX(), stick.getY(), this);
		g2D.rotate(Math.toDegrees(pikaBall.getAngle()), pikaBall.getX()+pikaBall.getWidth()/2, pikaBall.getY()+pikaBall.getHeight()/2);
		g2D.drawImage(pikaBall.getImage(), pikaBall.getX(), pikaBall.getY(), this);
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();	
	}
}
