package Client;

import java.net.Socket;

public class ClientChatThread implements Runnable{

	public ClientChatThread(Socket s) {
		// TODO Auto-generated constructor stub
		socket = s;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ClientGUI client = new ClientGUI();
		client.connect(socket);
		client.setVisible(true);
		
	}
	public void start(){
		if (t == null){
			t = new Thread(this);
			t.start();
		}
	}
	Thread t = null;
	Socket socket = null;
}
