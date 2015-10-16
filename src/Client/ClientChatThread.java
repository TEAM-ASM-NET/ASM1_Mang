package Client;

import java.io.DataInputStream;
import java.net.Socket;

import Protocol.XMLProtocol;

public class ClientChatThread implements Runnable{

	public ClientChatThread(Socket s, String userchat) {
		// TODO Auto-generated constructor stub
		socket = s;
		userChat = userchat;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			DataInputStream dip = new DataInputStream(socket.getInputStream());
			String aod = dip.readUTF();
			XMLProtocol proto = new XMLProtocol();
			System.out.println(aod + "   aid");
			if (!aod.equals(proto.chatDeny())){
				ClientGUI client = new ClientGUI();
				client.connect(socket, userChat);
				client.setVisible(true);
				client.setTitle("Chat with " + userChat);
			}
			else socket.close();
			
		}catch (Exception e){
			System.out.println(e.getMessage());
			
		}
		
	}
	public void start(){
		if (t == null){
			t = new Thread(this);
			t.start();
		}
	}
	
	Thread t = null;
	Socket socket = null;
	String userChat;
}
