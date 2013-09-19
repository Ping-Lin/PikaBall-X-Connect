import java.net.*;
import java.io.*;

public class UdpClient {
	int port;
	InetAddress serverIp;   //server IP
	String msg;   //massage to send
	
	public UdpClient(String serverIp, int port, String msg){
		
		try {
			this.port = port;
			this.serverIp = InetAddress.getByName(serverIp);
			this.msg = msg;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		try{
			byte buffer[] = msg.getBytes();   //將訊息字串轉換為位元串
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverIp, port);   //指定傳送對象,將位元串封裝成為封包
			DatagramSocket socket = new DatagramSocket();   //傳送的Udp socket
			socket.send(packet);
			socket.close();
		}
		catch(SocketException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
