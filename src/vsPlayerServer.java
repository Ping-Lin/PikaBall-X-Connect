import javax.swing.*;
import java.net.*;
import java.io.*;

public class vsPlayerServer extends Thread
{
	private InetAddress clientIp;
	private int port;
	private DatagramSocket server;   // 伺服端ServerSocket
	private Socket client;        // 客戶端連線Socket物件
    private PrintStream theOutputStream;    // 將資料丟出給客戶端的物件
    private Pika pika1;
    private String pika1PosX, pika1PosY, pika2PosX, pika2PosY, pikaBallX, pikaBallY;
    private String msg, smsg;   //receive message and send message
	
	public vsPlayerServer(InetAddress ip, int port)
	{
		this.clientIp = ip;
		this.port = port;
	}
	
	public void run()
	{
		final int SIZE = 8192;   //set up max size of message	
		byte buffer[] = new byte[SIZE];   //buffer
		
		byte sendBuffer[];
		while(true){
			
			try{
				//send
				sendBuffer = smsg.getBytes();;
				DatagramPacket packet0 = new DatagramPacket(sendBuffer, sendBuffer.length, clientIp, port);   //指定傳送對象,將位元串封裝成為封包
				DatagramSocket client = new DatagramSocket();   //傳送的Udp socket
				client.send(packet0);
				client.close();
				
				//receive
				DatagramSocket server = new DatagramSocket(port);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				server.receive(packet);   //receive package
				msg = new String(buffer, 0, packet.getLength());
				splitLetter(msg);
				server.close();
			}
    		catch(NullPointerException e){
    			//e.printStackTrace();
    		}
			catch(IOException e){
				//e.printStackTrace();
			}
		}
		
	}

	private void splitLetter(String m){
		String[] msg = m.split(":");
		pika1PosX = msg[0];
		pika1PosY = msg[1];
		pikaBallX = msg[2];
		pikaBallY = msg[3];
	}
	
	public int getPika1PosX(){
		if(pika1PosX != null)
			return Integer.parseInt(pika1PosX);
		else
			return 25;
	}
	
	public int getPika1PosY(){
		if(pika1PosY != null)
			return Integer.parseInt(pika1PosY);
		else
			return 500;
	}
	
	public int getPikaBallPosX(){
		if(pikaBallX != null)
			return Integer.parseInt(pikaBallX);
		else
			return 630;
	}
	
	public int getPikaBallPosY(){
		if(pikaBallY != null)
			return Integer.parseInt(pikaBallY);
		else
			return 10;
	}
	
	public void pikaOutPut(String x, String y){
		pika2PosX = x;
		pika2PosY = y;
		smsg = pika2PosX + ":" + pika2PosY;
	}
}
