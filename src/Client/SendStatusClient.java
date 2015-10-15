package Client;

import java.io.*;
import java.net.*;

import Protocol.XMLProtocol;

public class SendStatusClient implements Runnable{
	
	public SendStatusClient(Socket s, String u, UserStatusGUI f){
		socket = s;
		username = u;
		frm = f;
	}
	
	public void run(){
		try{
			while(true){
				
				send = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				XMLProtocol protocol = new XMLProtocol();
				String strSend = protocol.alive(username, "ALIVE");
				send.write(strSend);
				send.flush();
				
				recieve = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String lstUser = recieve.readLine();
				frm.UpdateJList(lstUser);
				Thread.sleep(15000);
				
			}
		}catch(Exception e){}
	}
	public void start(){
		
	}
	Thread t = null;
	BufferedReader recieve = null;
	BufferedWriter send = null;
	Socket socket = null;
	String username;
	UserStatusGUI frm;
}
