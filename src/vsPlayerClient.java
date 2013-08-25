import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class vsPlayerClient extends Thread
{
	private JFrame frame2P;
	private Socket client;        // 客戶端連線Socket物件
	private BufferedReader theInputStream;  // 讀取客戶端資料的緩衝區
    private PrintStream theOutputStream;    // 將資料丟出給客戶端的物件
	private String data;

	public vsPlayerClient(Socket client/*, JFrame f*/)
	{
		this.client = client;
		/*frame2P = f;*/
	}
	
	public void run()
	{
        try 
        {
        	
        	// 建立讀取緩衝區
			theInputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			// 建立資料丟出物件
			theOutputStream = new PrintStream(client.getOutputStream());
			
			// 利用迴圈讀取資料
			while((data = theInputStream.readLine()) != null) 
			{
				if(data.equals("win")){
					JOptionPane.showMessageDialog(null,"You lose!!");
					frame2P.dispose();
					//new Menu();
					try 
					{
						client.close();
					}
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			
			
        }
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
        	e.printStackTrace();
        	JOptionPane.showMessageDialog(null, e.toString(),"Error!!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void dataOutput(String data) 
    {
        theOutputStream.println(data); // 客戶端丟出資料的方法
    }
	
	private class interruptListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			try 
			{
				client.close();
			}
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
            frame2P.dispose();
            new Network();
		}
	}
}
