import javax.swing.*;
import java.net.*;
import java.io.*;


public class Server extends Thread 
{
	private ServerSocket server;   // 伺服端ServerSocket
    private Socket client;      // 接收的客戶端Socket
    private String message;                  // 從客戶端讀到的資料
    private Network preinfo;      // 前端介面
    
    public Server(int port) 
    {
        try 
        {
            // 根據指定的連接埠建立Socket物件
        	server = new ServerSocket(port);
        }
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, e.toString(),"無法建立連線", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getMessage() 
    {
        return message;
    }
    
    public void setMessageObserver(Network pre) 
    {
    	preinfo = pre;
    }
    
    public void run() 
    {
        try 
        {
            message = "等待連線......";
            preinfo.update();
 
            // 接受連線，客戶端連線之後會得到Socket物件
            client = server.accept();
            message = "客戶端" + client.getInetAddress() + "已連線\n";
            preinfo.update();
            counter();
            message = "請稍後...\n";
            preinfo.update();
            counter();
            preinfo.frame.setVisible(false);
            
            new Game(server, client);   //start the pika Game, new Game(server, client);         
        }
        catch (IOException e) 
        {
            message = "連線失敗！！";
            preinfo.update();
            preinfo.setGUIState(true);
        }
    }
    
    public void counter()
    {
    	try 
    	{
			Thread.sleep(1000);
		} 
    	catch (InterruptedException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
