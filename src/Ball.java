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
	private boolean ifLeft, ifRight, ifUp, ifDown, ifEnter, ifZ;   //set for true if player click the keyboard
	
	public Ball(){
		ImageIcon icon = new ImageIcon("src/source/pikaBall.png");
		image = icon.getImage();
		width = image.getWidth(null);   //initialize the x position
		height = image.getHeight(null);   //initialize the y position
		x=630;
		y=10;
		hitSpeedY = -10;
		hitSpeedX = 1.1f;
		currentSpeedX = 0;
		currentSpeedY = 0;
		ifLeft = ifRight = ifUp = ifDown = ifEnter = ifZ = false;
	}
	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
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
		else if(y<0){   //撞到天花板
			//hit(0, false);
			y=0;
			currentSpeedY = 3;
			//currentSpeedY = currentSpeedY * (-1) - 5;
		}
	
		/*
		 * handle x position
		 */
		if(x < 0 || x>=720){
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
	
	public void hitUpStick(){
		y = 275;
		currentSpeedY = Math.abs(currentSpeedY)*(-1)+3;
	}
	
	public void hit(float d, boolean ifPowHit, int player){   //ball hits something, contains power hit
		currentSpeedY = hitSpeedY-1;   //y position
		
		if(d!=0){
			hitSpeedX = d*0.07f;   //x position
			if(d<0)
				hitSpeedX -= 1;   //為了要讓球沒那麼好接
			else
				hitSpeedX += 1;   //為了要讓球沒那麼好接
			
			currentSpeedX = hitSpeedX;
		}
	
		if(ifPowHit == true){   //有使用力量攻擊,先以右邊為設定
			
			if(ifLeft == true){   //按左鍵
				currentSpeedX = -14;   //要快點
				currentSpeedY = 3;
			}
	
			else if(ifRight == true){  //或按右鍵
				currentSpeedX = -12;   //要比左慢一點
				currentSpeedY = 3;
			}
			
			if(ifDown == true){   //按下鍵
				if(ifLeft == true || ifRight == true){
					if(player == 2)
						currentSpeedX = Math.abs(currentSpeedX+3)*(-1);
					else
						currentSpeedX = Math.abs(currentSpeedX)*(-1);
				}
				else{
					if(player == 2)
						currentSpeedX = Math.abs(currentSpeedX+9)*(-1);   //加三是為了控制向左力量 
					else
						currentSpeedX = Math.abs(currentSpeedX+1)*(-1);
				}
				currentSpeedY = 14;   //y position
			}
			
			else if(ifUp == true){   //或按上鍵
				if(ifLeft == true || ifRight == true)
					currentSpeedX = Math.abs(currentSpeedX)*(-1)-4;
				else
					currentSpeedX = Math.abs(currentSpeedX)*(-1)-3;
				currentSpeedY = -17;
			}
			if((ifEnter == true || ifZ == true) && ifLeft == false && ifRight == false && ifUp == false && ifDown == false){
				currentSpeedX = -8;
				currentSpeedY = 3;
			}
			
			if(player == 1){   //因為上面寫的方法是以player2為基準去寫，player1只需改變x的方向即可
				currentSpeedX *= -1;
			}
		}
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_D){
			ifLeft = true;
		}
		
		if(key == KeyEvent.VK_RIGHT|| key == KeyEvent.VK_G){
			ifRight = true;
		}
		
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_R){
			ifUp = true;
		}
		
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_F){
			ifDown = true;
		}
		
		if(key == KeyEvent.VK_ENTER){
			ifEnter = true;
		}
		if(key == KeyEvent.VK_Z){
			ifZ = true;
		}
		if(key == KeyEvent.VK_Q){
			restart(1);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_D){
			ifLeft = false;
		}
		
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_G){
			ifRight = false;
		}
		
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_R){
			ifUp = false;
		}
		
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_F){
			ifDown = false;
		}
		
		if(key == KeyEvent.VK_ENTER){
			ifEnter = false;
		}
		if(key == KeyEvent.VK_Z){
			ifZ = true;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
	
	public void restart(int n){
		if(n == 1){
			x=630;
			y=10;
		}
		else{
			x = 63;
			y = 10;
		}
		currentSpeedX = 0;
		currentSpeedY = 0;	
	}
}

