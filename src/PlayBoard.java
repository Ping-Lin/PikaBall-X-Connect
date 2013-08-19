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
import java.awt.geom.Path2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PlayBoard extends JPanel{
	private Timer timer;   //control the game cycle
	private Timer timer2;   //control the ball cycle
	private Timer timer3;   //control the hide task
	private Pika pika1;
	private Pika2 pika2;
	private Ball pikaBall;
	private Stick stick;
	private Image bg;
	private Record record;
	private int roundWin;   //if 1 than 1P win, 2 than 2p win
	private boolean roundOver;
	private float alpha;
//	Line2D.Double leftLine = new Line2D.Double(0, 600, 400, 600);
//	Line2D.Double rightLine = new Line2D.Double(417, 600, 800, 600);
//	Line2D.Double leftStickLine = new Line2D.Double(400, 375, 400, 600);
//	Line2D.Double rightStickLine = new Line2D.Double(417, 375, 417, 600);
	
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
		timer.scheduleAtFixedRate(new ScheduleTask(), 3000, 12);
		timer2.scheduleAtFixedRate(new BallTask(), 3000, 100);
			
		record = new Record();
		this.add(record);
		
		roundWin = 0;
		roundOver = false;
		alpha = 0f;

	}
	
	private class tempAdapter extends KeyAdapter{   //按鍵的listener
		public void keyPressed(KeyEvent e){
			pika1.keyPressed(e);
			pika2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
			pika1.keyReleased(e);
			pika2.keyReleased(e);
		}
	}
	
	private class ballListener extends KeyAdapter{   //球的listener
		public void keyPressed(KeyEvent e){
			pikaBall.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
		pikaBall.keyReleased(e);
		}
	}
	
	class ScheduleTask extends TimerTask{   //整個遊戲的運行
		public void run(){
			pika1.ifStart = true;
			pika2.ifStart = true;
			pika1.move();
			pika2.move();
			pikaBall.move();
			collision();
			repaint();
		}
	}
	
	class BallTask extends TimerTask{   //球的旋轉, 之後可以加上控制旋轉速度
		public void run(){
			pikaBall.spin();
			repaint();
		}
	}

	class RestartTask extends TimerTask{   //球落在右邊，1P贏
		public void run(){
			timer.cancel();
			if(roundWin == 1){
				pikaBall.restart(2);
			}
			else{
				pikaBall.restart(1);
			}
			pika1.restart();
			pika2.restart();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleTask(), 1500, 12);
			timer3.cancel();
		}
	}
	
	class HideTask extends TimerTask{
		public void run(){
			if(alpha<0.99f){
				alpha+=0.01f;
			}
			else{
				roundOver = false;
				alpha = 0f;
				timer3.cancel();
				timer3 = new Timer();
				timer3.schedule(new RestartTask(), 0);
			}
		}
	}
	
//	public void addNotify(){
//		super.addNotify();
//	}
	
	private void collision(){
		Rectangle r1 = pika1.getBounds();
		Rectangle r2 = pikaBall.getBounds();
		Rectangle r4 = pika2.getBounds();
		Line2D.Double leftLine = new Line2D.Double(0, 600, 410, 600);
		Line2D.Double rightLine = new Line2D.Double(410, 600, 800, 600);
		Line2D.Double leftStickLine = new Line2D.Double(400, 375, 400, 600);
		Line2D.Double rightStickLine = new Line2D.Double(417, 375, 417, 600);
		Line2D.Double upStickLine = new Line2D.Double(400, 375, 416, 375);
		
		
		if(r1.intersects(r2)){   //pika1撞球
			float d = ((float)pikaBall.getX()+(float)pikaBall.getWidth()/2f)-((float)pika1.getX()+(float)pika1.getWidth()/2f);		
			pikaBall.hit(d, pika1.getPowHit(), 1);
		}
		else if(r4.intersects(r2)){   //pika2撞球
			float d = ((float)pikaBall.getX()+(float)pikaBall.getWidth()/2f)-((float)pika2.getX()+(float)pika2.getWidth()/2f);		
			pikaBall.hit(d, pika2.getPowHit(), 2);
		}
		if(r2.intersectsLine(upStickLine)){   //撞到柱子上面
			pikaBall.hitUpStick();
		}
		else if(r2.intersectsLine(rightStickLine) || r2.intersectsLine(leftStickLine)){   //撞到柱子左邊和右邊
			pikaBall.hitStick();
		}	
		
		if(r2.intersectsLine(leftLine) || r2.intersectsLine(rightLine)){   //球落地
			if(r2.intersectsLine(leftLine)){   //球落在左邊，2P贏
				record.plusCount2();
				roundWin = 2;
			}
			else if(r2.intersectsLine(rightLine)){   //球落在右邊，1P贏
				record.plusCount1();
				roundWin = 1;
			}
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleTask(), 0, 60);
			
			timer3 = new Timer();
			timer3.schedule(new HideTask(), 700, 15);
			roundOver = true;
			pika1.ifStart = false;
			pika2.ifStart = false;
		}
	}
	
	public void paint(Graphics g){
		
		g.drawImage(bg, 0, 0, this);   //draw background
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g.create();
		
		if(pika1.getIfJump() == true){
			if(pika1.getPowHit() == true)
				g2D.drawImage(pika1.getImage4(), pika1.getX(), pika1.getY(), 100, 100, this);
			else
				g2D.drawImage(pika1.getImage3(), pika1.getX(), pika1.getY(), 100, 100, this);
		}
		else if(pika1.getIfPu() == true){
			g2D.drawImage(pika1.getImage2(), pika1.getX(), pika1.getY(), 100, 100, this);
		}
		else {
			g2D.drawImage(pika1.getImage(), pika1.getX(), pika1.getY(), 100, 100, this);
		}
		
		if(pika2.getIfJump() == true){
			if(pika2.getPowHit() == true)
				g2D.drawImage(pika2.getImage4(), pika2.getX(), pika2.getY(), 100, 100, this);
			else
				g2D.drawImage(pika2.getImage3(), pika2.getX(), pika2.getY(), 100, 100, this);
		}
		else if(pika2.getIfPu() == true){
			g2D.drawImage(pika2.getImage2(), pika2.getX(), pika2.getY(), 100, 100, this);
		}
		else{
			g2D.drawImage(pika2.getImage(), pika2.getX(), pika2.getY(), 100, 100, this);
		}
		
//		g2D.draw(leftLine);
//		g2D.draw(rightLine);
//		g2D.draw(leftStickLine);
//		g2D.draw(rightStickLine);
		
		g2D.drawImage(stick.getImage(), stick.getX(), stick.getY(), 17, 225, this);
		g2D.rotate(Math.toDegrees(pikaBall.getAngle()), pikaBall.getX()+pikaBall.getWidth()/2, pikaBall.getY()+pikaBall.getHeight()/2);
		g2D.drawImage(pikaBall.getImage(), pikaBall.getX(), pikaBall.getY(), this);		
		
		Toolkit.getDefaultToolkit().sync();
		g2D.dispose();   //close the g2D draw
		
		if(roundOver == true){
			g2D = (Graphics2D)g.create();
			Rectangle hideR = new Rectangle(0, 0, 800, 700);
			AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2D.setComposite(comp);
			g2D.fill(hideR);
			
			Toolkit.getDefaultToolkit().sync();
			g2D.dispose();
		}
		
		g.dispose();
	}
}
