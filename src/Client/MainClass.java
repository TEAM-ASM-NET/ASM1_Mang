package Client;

import java.io.*;
import java.net.*;

public class MainClass {

	public static void main(String[] args) {		
		try {
			final int serverPort = 6696;
			final String serverIp = "localhost";
			
			//Listen other peer to chat
			SocketPeer p = new SocketPeer();
			p.start();
			
			UserStatusGUI f = new UserStatusGUI();
			f.setVisible(true);
			
			//Send request to chat
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}
	public static void sendRequsetoChat()
	{
		
	}
	
}
