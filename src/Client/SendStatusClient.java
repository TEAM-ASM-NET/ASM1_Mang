package Client;

import java.io.*;
import java.net.*;

import Protocol.XMLProtocol;

public class SendStatusClient implements Runnable{
	
	public SendStatusClient(Socket s, String u){
		socket = s;
		username = u;
	}
	public void run(){
		try{
			while(true){
				recieve = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				send = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				XMLProtocol protocol = new XMLProtocol();
				String strSend = protocol.alive(username, "ALIVE");
				send.write(strSend);
				send.flush();
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
}
