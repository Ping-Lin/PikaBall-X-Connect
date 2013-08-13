import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public class Pika {
	private int dx, x, y;   //accelerate and position
	private float jumpSpeed, currentSpeed;   //the speed when jump up and current speed
	private boolean ifJump;   //jump or not
	private boolean ifPowHit;   //power hit, direct left(-1), right(1), up(2), down(-2), only enter(0)
	private boolean ifPu;
	private Image image;
	private Image image2;
	private int width, height;   //width and height of image
	private int direction;   //Pika direct left(-1), right(1), up(2), down(-2) or stop(0)
	private boolean ifLeft, ifRight;   //set for true if player click the keyboard
	private Timer timer;
	private int tempX;   //calculate distance of Pu
	
	public Pika(){
		ImageIcon icon = new ImageIcon("src/source/1-finish.gif");
		image2 = new ImageIcon("src/source/4-finish.gif").getImage();   //撲球
		image = icon.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		x=20;   //initialize the x position
		y=500;   //initialize the y position
		jumpSpeed = -10;
		direction = 9;
		ifJump = false;
		ifPowHit = false;
		ifPu = false;
		ifLeft = ifRight = false;
		tempX = 0;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean getPowHit(){
		return ifPowHit;
	}
	
	public boolean getIfPu(){
		return ifPu;
	}
	
	public Image getImage(){
		return image;
	}
	
	public Image getImage2(){
		return image2;
	}
	
	public void move(){   //move function, when uses keyboard to control
		
		if(ifLeft == true){
			moveLeft();
		}
		if(ifRight == true){
			moveRight();
		}
		
		x += dx;
		if(ifJump == true){
			currentSpeed += 0.2;
			y += currentSpeed;
		}
		if(y == 500){   //on ground
			ifJump = false;
		}
		if(x < 0 || x>310){
			x-=dx;
		}
	}
	
	public void moveLeft(){
		dx = -3;
		direction = -1;
	}
	
	public void moveRight(){
		dx = 3;
		direction = 1;
	}
	
	public void restart(){
		x = 20;
		y = 500;
		ifJump = false;
		ifPowHit = false;
		ifPu = false;
		ifLeft = ifRight = false;
	}
	
	class PuTask extends TimerTask{   //Pu的移動
		public void run(){
			x+=5;
			tempX+=5;
			if(tempX == 50){
				tempX = 0;
				ifPu = false;
				timer.cancel();
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			ifLeft = true;
		}
		
		if(key == KeyEvent.VK_G){
			ifRight = true;
		}
		
		if(key == KeyEvent.VK_R && ifJump == false){
			ifJump=true;
			currentSpeed = jumpSpeed;
		}

		if(key == KeyEvent.VK_Z){
			
			if(ifJump == false){   //撲在地上
				if(direction == 1){   //撲 right
					ifPu = true;
					timer = new Timer();
					timer.scheduleAtFixedRate(new PuTask(), 0, 50);
					//x+=50;
				}
				if(direction == -1){   //撲 left
					ifPu = true;
					x-=50;
				}
			}
			else{   //在空中
				ifPowHit = true;
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			ifLeft = false;
			dx = 0;
		}
		
		if(key == KeyEvent.VK_G){
			ifRight = false;
			dx = 0;
		}
		
		if(key == KeyEvent.VK_Z){
			//ifPu = false;
			ifPowHit = false;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
	
}
