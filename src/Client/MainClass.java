package Client;

import java.io.*;
import java.net.*;

public class MainClass {

	public static void main(String[] args) {		
		try {
			
			//Role is server to Listen other peer to chat
			//SocketPeer p = new SocketPeer(serverPort);
		//	p.start();
			
			//ROle is client
			
			//final int serverPort = 6696;
		//	final String serverIp = "localhost";
			
//			Socket client = new Socket(serverIp, serverPort);
//			UserStatusGUI f = new UserStatusGUI(client);
//			f.setVisible(true);
//			
			UserStatusGUI f = new UserStatusGUI();
			f.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

	
}
