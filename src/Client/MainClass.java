package Client;

import java.io.*;
import java.net.*;

public class MainClass {

	public static void main(String[] args) {		
		try {
			
//			UserStatusGUI f = new UserStatusGUI();
	//		f.setVisible(true);
			ServerSocket ss = new ServerSocket(6696);
			Socket s = ss.accept();
			ClientGUI c1 = new ClientGUI();
			c1.setTitle("Tao là server");
			c1.connect(s);
			c1.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

	
}
