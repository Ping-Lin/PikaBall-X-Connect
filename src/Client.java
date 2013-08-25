import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client extends Thread 
{
	private Socket client;        // 客戶端連線Socket物件
    private InetAddress host;  // 指定的伺服端IP
    private int port;          // 指定的伺服端連接埠 
    private String message;     // 伺服端傳回的資料
    private Network preinfo;      // 前端介面
    
    public Client(String ip, int port) 
    {
        try 
        {
            // 取得伺服端的InetAddress物件、通訊連接埠
            host = InetAddress.getByName(ip);
            this.port = port;
        }
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, e.toString(),"錯誤", JOptionPane.ERROR_MESSAGE);
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
            message = "嘗試連線......";
            preinfo.update();
 
            // 建立Socket物件並嘗試連線
            client = new Socket(host, port);
            message = "連線成功\n";
            preinfo.update();
            counter();
            message = "請稍後...\n";
            preinfo.update();
            counter();
            preinfo.frame.setVisible(false);
            new Game(client);   //start pika game, client
            //new PlayMap(4, 5, type, 2,client);
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
