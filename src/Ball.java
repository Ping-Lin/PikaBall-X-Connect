import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Ball {
	private int width;
	private int height;
	private Image image;
	private float hitSpeedY;   //when something intersects the ball, give the speed of Y direction to the ball
	private float hitSpeedX;   ///when something intersects the ball, give the speed of X direction to the ball
	private float currentSpeedY;   //speed of y direction the ball
	private float currentSpeedX;   //speed of x direction of the ball
	private int x, y;   //position
	private int angle;    //angle of the ball
	private boolean ifLeft, ifRight, ifUp, ifDown, ifEnter;   //set for true if player click the keyboard
	
	public Ball(){
		ImageIcon icon = new ImageIcon("src/source/pikaBall.png");
		image = icon.getImage();
		width = image.getWidth(null);   //initialize the x position
		height = image.getHeight(null);   //initialize the y position
		x=600;
		y=10;
		hitSpeedY = -10;
		hitSpeedX = 1.1f;
		currentSpeedX = 0;
		currentSpeedY = 0;
		ifLeft = ifRight = ifUp = ifDown = ifEnter = false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getAngle(){
		return angle;
	}
	
	public void move(){   //ball moves
		/*
		 * handle y position 
		 */
		currentSpeedY += 0.2;
	
		y += currentSpeedY;
		x += currentSpeedX;
		if(y>=520){
			hitGround();
			//currentSpeedY *= -1;
		}
		else if(y<0){
			//hit(0, false);
			currentSpeedY *= -1;
		}
		else if((x+40 >= 400 && x+40 <= 417) && (y+80>=365 && y+80<=375)){
			currentSpeedY *= -1;
		}
	
		/*
		 * handle x position
		 */
		if(x < 0 || x>=720){
			currentSpeedX *= -1;
		}
		else if((x+40 >= 400 && x+40 <= 417) && (y>365 && y+80<600)){
			currentSpeedX *= -1;
		}
	}
	
	public void spin(){   //ball spins
		angle +=45;		
	}
	
	public void hitGround(){
		currentSpeedY = hitSpeedY-3.5f;   //y position
	}
	
	public void hitStick(){
		currentSpeedX *=-1;
	}
	
	public void hit(float d, boolean ifPowHit){   //ball hits something
		currentSpeedY = hitSpeedY-1;   //y position
		if(d!=0){
			hitSpeedX = d*0.03f;   //x position
			if(d<0)
				hitSpeedX -= 1;
			else
				hitSpeedX += 1;
			
			currentSpeedX = hitSpeedX;
		}
	
		if(ifPowHit == true){
			
			if(ifLeft == true){
				currentSpeedY = 0;
				if(d<=0){
					currentSpeedX = -1;
					currentSpeedX -=3;
				}
				else{
					currentSpeedX = -1;
					currentSpeedX -=3;
				}
			}
			if(ifRight == true){
				currentSpeedY = 0;
				if(d>=0){
					currentSpeedX = 1;
					currentSpeedX +=3;
				}else{
					currentSpeedX = 1;
					currentSpeedX +=3;
				}
			}
			
			if(ifDown == true){
				currentSpeedX *= 1.1;
				currentSpeedY = hitSpeedY;   //y position
				currentSpeedY *= -1.4;
			}
			else if(ifUp == true){
				currentSpeedX *= 1.1+2;
				currentSpeedY = hitSpeedY;   //y position
				currentSpeedY *= 1.4;
			}
			else if(ifEnter == true){
				currentSpeedY = 0;
			}
		}
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			ifLeft = true;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			ifRight = true;
		}
		
		if(key == KeyEvent.VK_UP){
			ifUp = true;
		}
		
		if(key == KeyEvent.VK_DOWN){
			ifDown = true;
		}
		
		if(key == KeyEvent.VK_ENTER){
			ifEnter = true;
		}
		if(key == KeyEvent.VK_Q){
			restart(1);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			ifLeft = false;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			ifRight = false;
		}
		
		if(key == KeyEvent.VK_UP){
			ifUp = false;
		}
		
		if(key == KeyEvent.VK_DOWN){
			ifDown = false;
		}
		
		if(key == KeyEvent.VK_ENTER){
			ifEnter = false;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
	
	public void restart(int n){
		if(n == 1){
			x=600;
			y=10;
		}
		else{
			x = 20;
			y = 10;
		}
		currentSpeedX = 0;
		currentSpeedY = 0;	
	}
}

