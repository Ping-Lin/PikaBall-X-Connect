import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Pika2{
	private int dx, x, y;   //accelerate and position
	private float jumpSpeed, currentSpeed;   //the speed when jump up and current speed
	private boolean ifJump;   //jump or not
	private boolean ifPowHit;   //power hit, direct left(-1), right(1), up(2), down(-2), only enter(0)
	private boolean ifPu;
	private Image image, image2, image3, image4;   //walk, pu, jump, hit
	private int width, height;   //width and height of image
	private int direction;   //Pika direct left(-1), right(1), up(2), down(-2) or stop(0)
	private boolean ifLeft, ifRight, ifZ;   //set for true if player click the keyboard
	private Timer timer, timer2;
	private long tmp;   //code at PuTask, help for calculating distance of Pu
	public boolean ifStart;
	
	public Pika2(){
		ImageIcon icon = new ImageIcon("src/source/1-finish-2.gif");
		image = icon.getImage();
		image2 = (Image)transform("src/source/4-1.png");
		image3 = new ImageIcon("src/source/2-finish-2.gif").getImage();
		image4 = (Image)transform("src/source/3-1.png");
		width = image.getWidth(null);
		height = image.getHeight(null);
		x=660;   //initialize the x position
		y=500;   //initialize the y position
		jumpSpeed = -10;
		direction = 0;
		ifJump = false;
		ifPowHit = false;
		ifPu = false;
		ifLeft = ifRight = ifZ = false;
		ifStart = true;
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
	
	public boolean getIfJump(){
		return ifJump;
	}

	public Image getImage(){
		return image;
	}
	
	public Image getImage2(){
		return image2;
	}
	
	public Image getImage3(){
		return image3;
	}
	
	public Image getImage4(){
		return image4;
	}
	
	public void move(){   //move function, when uses keyboard to control
		
		if(ifLeft == true && ifPu == false){
			moveLeft();
		}
		if(ifRight == true && ifPu == false){
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
	
	public void moveLeft(){
		dx = -3;
		direction = -1;
	}
	
	public void moveRight(){
		dx = 3;
		direction = 1;
	}
	
	public void restart(){
		x = 660;
		y = 500;
		ifJump = false;
		ifPowHit = false;
		ifPu = false;
		ifLeft = ifRight = ifZ = ifStart = false;
	}
	
	public BufferedImage transform(String src){
		BufferedImage di = null;
		try{
			BufferedImage si = ImageIO.read(new FileInputStream(src));
			AffineTransform t = new AffineTransform(-1, 0, 0, 1, si.getWidth(), 0);
			AffineTransformOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
			di = op.filter(si, null);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return di;
	}
	
	class PuTask extends TimerTask{   //Pu move, with execution time change, change the image of pu
		public void run(){
			if(ifZ==false){   //if pu, then can't move directly, ifZ can help to set up the tmp time				
				dx = 0;
				tmp = this.scheduledExecutionTime();
				ifZ = true;
			}
			
			if (this.scheduledExecutionTime()-tmp>=600){   //check for the execution time. If bigger than 0.6s, then cancel the thread
				direction = 0;   //initialize
				timer.cancel();
				ifPu = false;
				ifZ = false;
			}
			else if (this.scheduledExecutionTime()-tmp>400){   //4-3圖
				if(direction == 1){   //撲右
					image2 = new ImageIcon("src/source/4-3.png").getImage();
				}
				else if(direction == -1){   //撲左
					image2 = transform("src/source/4-3.png");
				}
			}
			else if (this.scheduledExecutionTime()-tmp>100){   //4-2圖
				if(direction == 1){   //撲右
					image2 = new ImageIcon("src/source/4-2.png").getImage();
					x+=7;
					if(x>700){
						x = 700;
					}
				}
				else if(direction == -1){   //撲左
					image2 = transform("src/source/4-2.png");
					x-=7;
					if(x<=410){
						x = 410;
					}
				}
				//y改變
				if(this.scheduledExecutionTime()-tmp<200) //0~199
					y-=2;   //往上飛一點
				else{   //200~399
					y+=2;   //往下飛一點
					if(y >= 500)
						y = 500;   //防止超過底線
				}
			}
			else if (this.scheduledExecutionTime()-tmp<=100){   //4-1圖
				if(direction == 1){   //撲右
					image2 = new ImageIcon("src/source/4-1.png").getImage();
					x+=7;
					if(x>700){
						x = 700;
					}
				}
				else if(direction == -1){   //撲左
					image2 = (Image)transform("src/source/4-1.png");
					x-=7;
					if(x<=410){
						x = 410;
					}
				}
				y-=2;   //往上飛一點
			}
		}
	}
	
	class HitTask extends TimerTask{
		public void run(){
			if(ifZ==false){   //if pu, then can't move directly, ifZ can help to set up the tmp time		
				tmp = this.scheduledExecutionTime();
				ifZ = true;
			}
			
			if (this.scheduledExecutionTime()-tmp>=700){   //check for the execution time. If bigger than 0.6s, then cancel the thread
				timer2.cancel();
				ifPowHit = false;
				ifZ = false;
			}
			else if (this.scheduledExecutionTime()-tmp>600){   //3-5圖
				image4 = (Image)transform("src/source/3-5.png");
			}
			else if (this.scheduledExecutionTime()-tmp>500){   //3-4圖
				image4 = (Image)transform("src/source/3-4.png");
			}
			else if (this.scheduledExecutionTime()-tmp>400){   //3-3圖
				image4 = (Image)transform("src/source/3-3.png");
			}
			else if (this.scheduledExecutionTime()-tmp > 100){   //3-2圖
				image4 = (Image)transform("src/source/3-2.png");
			}
			else if (this.scheduledExecutionTime()-tmp <= 100){   //3-1圖
				image4 = (Image)transform("src/source/3-1.png");
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(ifStart == true){
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_UP && ifJump == false && ifPu == false){
				ifJump=true;
				currentSpeed = jumpSpeed;
			}
	
			if(key == KeyEvent.VK_ENTER && ifZ == false){			
				if(ifJump == true){   //在空中
					ifPowHit = true;
					timer2 = new Timer();
					timer2.scheduleAtFixedRate(new HitTask(), 0, 20);
				}
				else{
					if(ifPu == false){   //撲在地上
						if(direction == 1){   //撲 right
							ifPu = true;
							timer = new Timer();
							timer.scheduleAtFixedRate(new PuTask(), 0, 20);
						}
						if(direction == -1){   //撲 left
							ifPu = true;
							timer = new Timer();
							timer.scheduleAtFixedRate(new PuTask(), 0, 20);
						}
					}
				}
			}
			
			if(key == KeyEvent.VK_LEFT){
				ifLeft = true;
			}
			
			if(key == KeyEvent.VK_RIGHT){
				ifRight = true;
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			ifLeft = false;
			dx = 0;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			ifRight = false;
			dx = 0;
		}
	}
	
	public Rectangle getBounds(){   //check for collision
		return new Rectangle(x, y, width, height);
	}
}
