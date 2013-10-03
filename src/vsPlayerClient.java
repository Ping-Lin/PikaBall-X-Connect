import javax.swing.*;
import java.net.*;
import java.io.*;

public class vsPlayerClient extends Thread
{
	private InetAddress serverIp;   //server IP
	private int port;   //server port
	private String msg, smsg;   //receive message and send massage
    private String pika1PosX, pika1PosY, pika2PosX, pika2PosY, pikaBallX, pikaBallY;
    
	public vsPlayerClient(InetAddress host, int port){
		this.serverIp = host;
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
    			sendBuffer = smsg.getBytes();   //將訊息字串轉換為位元串
    			DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, serverIp, port);   //指定傳送對象,將位元串封裝成為封包
				DatagramSocket client = new DatagramSocket();   //傳送的Udp socket
				client.send(packet);
				client.close();
				
				//receive
				DatagramSocket server = new DatagramSocket(port);
				DatagramPacket packet0 = new DatagramPacket(buffer, buffer.length);
				server.receive(packet0);   //receive package
				msg = new String(buffer, 0, packet0.getLength());
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
		pika2PosX = msg[0];
		pika2PosY = msg[1];
		pikaBallX = msg[2];
		pikaBallY = msg[3];
	}
	
	public int getPika2PosX(){
		if(pika2PosX != null)
			return Integer.parseInt(pika2PosX);
		else
			return 670;
	}
	
	public int getPika2PosY(){
		if(pika2PosY != null)
			return Integer.parseInt(pika2PosY);
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
		pika1PosX = x;
		pika1PosY = y;
//		pikaBallX = ballX;
//		pikaBallY = ballY;
		smsg = pika1PosX + ":" + pika1PosY;
	}
}
