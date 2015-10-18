package Client;
import java.io.*;
import java.net.*;

public class MainClass {

	public static void main(String[] args) {		
		try {
			
			UserStatusGUI f = new UserStatusGUI();
			f.setVisible(true);
		
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

	
}
