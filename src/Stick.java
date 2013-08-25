import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Stick {
	private Image image;
	private int x, y;   //position
	private int width, height;   //pic width and height
	public Stick(){
		ImageIcon icon = new ImageIcon("src/source/stick.jpg");
		image = icon.getImage();
		x=400;
		y=375;
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}
	
	Image getImage(){
		return image;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
}
