import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Record extends JPanel{
	private Timer timer;
	private int width, height;
	private int x, y;
	private int count1, count2;
	private JLabel countLabel1, countLabel2;
	
	public Record(){
		countLabel1 = new JLabel("0");
		countLabel2 = new JLabel("1");
		width = 850;
		height = 200;
		x = 0;
		y = 0;
		count1 = 10;
		count2 = 10;
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 0, 100);
		
		//this.setBackground(Color.RED);
		this.setPreferredSize(new Dimension(width,height));
		this.setLayout(null);
		this.setOpaque(false);
		this.setVisible(true);
		
		countLabel1.setPreferredSize(new Dimension(120,80));
		countLabel1.setBounds(80, 30, 80, 80);
		countLabel1.setFont(new Font("Times-Roman", Font.BOLD | Font.ITALIC, 60));   //設定字型、粗體、顏色
		countLabel1.setForeground(Color.RED);   //設定字體顏色
		
		countLabel2.setPreferredSize(new Dimension(120,80));
		countLabel2.setBounds(650, 30, 80, 80);
		countLabel2.setFont(new Font("Times-Roman", Font.BOLD | Font.ITALIC, 60));   //設定字型、粗體、顏色
		countLabel2.setForeground(Color.RED);   //設定字體顏色
		
		this.add(countLabel1);
		this.add(countLabel2);
		this.validate();
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
	
	class ScheduleTask extends TimerTask{
		public void run(){
			countLabel1.setText(Integer.toString(count1));
			countLabel2.setText(Integer.toString(count2));
		}
	}
	
	public void plusCount1(){
		count1 += 1;
	}

	public void plusCount2(){
		count2 += 1;
	}
	
	public void showMessage(){
		countLabel1.setText(Integer.toString(count1));
		countLabel2.setText(Integer.toString(count2));
	}
}
