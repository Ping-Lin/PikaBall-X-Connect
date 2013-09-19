import java.net.*;
import java.io.*;

public class UdpServer {
	int port;
	
	public UdpServer(int port){
		this.port = port;
	}
	
	public void run(){
		final int SIZE = 8192;   //set up max size of message
		
		byte buffer[] = new byte[SIZE];   //buffer
		while(true){
			try{
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				DatagramSocket socket = new DatagramSocket(port);   //UDP socket
				
				socket.receive(packet);   //receive package
				
				String msg = new String(buffer, 0, packet.getLength());
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
}
