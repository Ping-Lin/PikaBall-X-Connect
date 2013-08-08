import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Pika {
	private int dx, x, y;   //accelerate and position
	private float jumpSpeed, currentSpeed;   //the speed when jump up and current speed
	private boolean ifJump;   //jump or not
	private int powHit;   //power hit, direct left(-1), right(1), up(2), down(-2), only enter(0)
	private Image image;
	private int width, height;   //width and height of image
	private int direction;   //Pika direct left(-1), right(1), up(2), down(-2) or stop(0)
	
	public Pika(){
		ImageIcon icon = new ImageIcon("src/source/pika.gif");
		image = icon.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		x=600;   //initialize the x position
		y=400;   //initialize the y position
		jumpSpeed = 10;
		direction = 0;
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
	
	public int getPowHit(){
		return powHit;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void move(){   //move function, when uses keyboard to control
		x += dx;
		if(ifJump == true){
			currentSpeed -= 0.2;
			y -= currentSpeed;
		}
		if(y == 400){
			ifJump = false;
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			dx = -3;
			direction = -1;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			dx = 3;
			direction = 1;
		}
		
		if(key == KeyEvent.VK_UP){
			direction = 2;
		}
		
		if(key == KeyEvent.VK_UP && ifJump == false){
			ifJump=true;
			currentSpeed = jumpSpeed;
			direction = 2;
		}
		
		if(key == KeyEvent.VK_DOWN){
			direction = -2;
		}
		
		if(key == KeyEvent.VK_ENTER){
			
			if(ifJump == false){   //撲在地上
				if(direction == 1)   //撲 right
					x+=50;
				if(direction == -1)   //撲 left
					x-=50;
			}
			else{   //在空中
				powHit = direction;
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			dx = 0;
			direction = 0;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			dx = 0;
			direction = 0;
		}
		
		if(key == KeyEvent.VK_UP){
			direction = 0;
		}
		
		if(key == KeyEvent.VK_DOWN){
			direction = 0;
		}
		
		if(key == KeyEvent.VK_ENTER){
			//powHit = 0;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
	
}
