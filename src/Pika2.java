import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Pika2{
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
	
	public Pika2(){
		ImageIcon icon = new ImageIcon("src/source/1-finish.gif");
		image = icon.getImage();
		image2 = new ImageIcon("src/source/4-finish.gif").getImage();   //撲球
		width = image.getWidth(null);
		height = image.getHeight(null);
		x=600;   //initialize the x position
		y=500;   //initialize the y position
		jumpSpeed = -10;
		direction = 9;
		ifJump = false;
		ifPowHit = false;
		ifPu = false;
		ifLeft = ifRight = false;
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
	
	public void moveLeft(){
		dx = -3;
		direction = -1;
	}
	
	public void moveRight(){
		dx = 3;
		direction = 1;
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
		if(x < 410 || x>700){
			x-=dx;
		}
	}
	
//	public BufferedImage transform(){
//		try{
//			BufferedImage si = ImageIO.read(new FileInputStream("src/source/1-finish.gif"));
//			di = null;
//			AffineTransform t = new AffineTransform(-1, 0, 0, 1, si.getWidth(), 0);
//			AffineTransformOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
//			di = op.filter(si, null);
//		}
//		catch(FileNotFoundException e){
//			e.printStackTrace();
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		return di;
//	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			ifLeft = true;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			ifRight = true;
		}
		
		if(key == KeyEvent.VK_UP && ifJump == false){
			ifJump=true;
			currentSpeed = jumpSpeed;
		}

		if(key == KeyEvent.VK_ENTER){
			
			if(ifJump == false){   //撲在地上
				if(direction == 1){   //撲 right
					ifPu = true;
					x+=50;
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
	
	public void restart(){
		x = 600;
		y = 500;
		ifJump = false;
		ifPowHit = false;
		ifPu = false;
		ifLeft = ifRight = false;
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			dx = 0;
			ifLeft = false;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			dx = 0;
			ifRight = false;
		}
		
		if(key == KeyEvent.VK_ENTER){
			ifPu = false;
			ifPowHit = false;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
}
