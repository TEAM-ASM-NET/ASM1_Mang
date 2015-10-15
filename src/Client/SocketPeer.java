package Client;

import java.awt.Dialog;
import java.io.*;
import java.net.*;
import javax.swing.*;

import Protocol.XMLProtocol;

public class SocketPeer implements Runnable{

	public SocketPeer(int port) throws Exception{
		// TODO Auto-generated constructor stub
		server = new ServerSocket(port);
		this.port = port;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while (true)
			{
				socket = server.accept();
				buff = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				String msgbox = "Client ";// + socket.getInetAddress() + " want to chat. Are you agree?";
				
				int result = JOptionPane.showConfirmDialog(null, msgbox, "Information", JOptionPane.YES_NO_OPTION);
				
				XMLProtocol pro = new XMLProtocol();
				String _result;
				
				if (result == 0){
					_result = pro.chatAccept();
					buff.write(_result);
					ClientGUI frm = new ClientGUI();
					frm.connect(socket);
					frm.setVisible(true);
					
				} else{
					
					_result = pro.chatDeny();
					buff.write(_result);
					
				}
			}//end while true
		}
		catch (IOException e){}
	}
	
	public void start(){
		if (t== null){
			t = new Thread(this);
			t.start();
		}
	}
	private Socket socket = null;
	private ServerSocket server = null;
	private BufferedWriter buff = null;
	public int port;
	private Thread t = null;
	
}
