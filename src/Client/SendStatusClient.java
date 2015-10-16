package Client;

import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import Protocol.XMLProtocol;

public class SendStatusClient implements Runnable{
	
	public SendStatusClient(Socket s, String u, UserStatusGUI f){
		socket = s;
		username = u;
		frm = f;
	}
	
	public void run(){
		try{
			Boolean d = true;
			while(d){
				send = new DataOutputStream(socket.getOutputStream());
				
				XMLProtocol protocol = new XMLProtocol();
				String strSend = protocol.alive(username, "ALIVE");
				send.writeUTF(strSend);
				send.flush();
				recieve = new DataInputStream(socket.getInputStream());
				
				String lstUser = recieve.readUTF();
				//JOptionPane.showMessageDialog(null, lstUser + "DUoi read");
				frm.UpdateJList(lstUser);
				Thread.sleep(5000);
			}
		}catch(Exception e){
			System.out.print("Loi " + e.getMessage());
			t.stop();
		}
	}
	public void start(){
		if (t==null){
			t = new Thread(this);
			t.start();
		}
	}
	
	Thread t = null;
	DataInputStream recieve = null;
	DataOutputStream send = null;
	Socket socket = null;
	String username;
	UserStatusGUI frm;
//	static CountDownLatch countDownLatch = new CountDownLatch(1);
}
