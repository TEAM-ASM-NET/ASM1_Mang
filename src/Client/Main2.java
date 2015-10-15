package Client;

import java.io.DataOutputStream;
import java.net.Socket;

public class Main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket s = new Socket("localhost", 6696);
		
			ClientGUI f = new ClientGUI();
			f.setTitle("Tao l� client");
			f.connect(s);
			f.setVisible(true);
		}catch(Exception e){
			System.out.println("Loi");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
	}

}
