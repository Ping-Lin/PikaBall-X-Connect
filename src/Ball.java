import java.awt.Image;
import java.awt.Rectangle;
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
	
	public Ball(){
		ImageIcon icon = new ImageIcon("src/source/pikaBall.png");
		image = icon.getImage();
		width = image.getWidth(null);   //initialize the x position
		height = image.getHeight(null);   //initialize the y position
		x=600;
		y=0;
		hitSpeedY = 10;
		hitSpeedX = 0.5f;
		currentSpeedY = 0;
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
		currentSpeedY -= 0.2;
		y -= currentSpeedY;
		if(y>=420){
			hit(0);
		}
		/*
		 * handle x position
		 */
		x += currentSpeedX;
		if(x < 0 || x>=720){
			currentSpeedX *=-1;
		}
	}
	
	public void spin(){   //ball spins
		angle +=45;		
	}
	
	public void hit(float d){   //ball hits something
		currentSpeedY = hitSpeedY;   //y position
		if(d!=0){
			hitSpeedX = d*0.1f;   //x position
			currentSpeedX = hitSpeedX;
		}
	}
	
	public void bePowHit(int n){
		if(n == 1){   //left power
			currentSpeedX = hitSpeedX *1.5f;
		}
		else if(n == -1){   //right power
			currentSpeedX = hitSpeedX * 1.5f * (-1);
		}
		else if(n == 2){   //up power
			currentSpeedY = hitSpeedY * 1.5f;
		}
		else if(n == -2){   //down power
			currentSpeedY = hitSpeedY * 1.5f * (-1);
		}
		else if(n == 0){   //stop power
			currentSpeedY = 0;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
}

