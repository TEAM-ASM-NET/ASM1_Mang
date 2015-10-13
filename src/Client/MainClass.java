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

			Socket socket = new Socket(serverIp, serverPort);
			
			int peerPort = socket.getPort();
			
			//Wait for other peer connet to chat P2P
			ServerSocket peerSocket = new ServerSocket(peerPort);
			
			ClientGUI window = new ClientGUI();
			window.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

	
}
