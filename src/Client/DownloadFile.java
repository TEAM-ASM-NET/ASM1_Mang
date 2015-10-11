package Client;

import Client.ClientGUI;

import java.io.*;
import java.net.*;

public class DownloadFile implements Runnable{

	 public ServerSocket server;
	 public Socket socket;
	 public int port;
	 public String saveTo = "";
	 public InputStream In;
	 public FileOutputStream Out;
	 public ClientGUI gui;
	 public DownloadFile(String saveTo, ClientGUI gui){
	        try {
	            server = new ServerSocket(0);
	            port = server.getLocalPort();
	            this.saveTo = saveTo;
	            this.gui = gui;
	        } 
	        catch (IOException ex) {
	            System.out.println("Exception [Download : Download(...)]");
	        }
	    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            socket = server.accept();
            System.out.println("Download : "+socket.getRemoteSocketAddress());
           
            In = socket.getInputStream();
            Out = new FileOutputStream(saveTo);
            
            byte[] buffer = new byte[1024];
            int count;
            
            while((count = In.read(buffer)) >= 0){
                Out.write(buffer, 0, count);
            }
            
            Out.flush();
            
           // gui.jTextArea1.append("[Application > Me] : Download complete\n");
            
            if(Out != null){ Out.close(); }
            if(In != null){ In.close(); }
            if(socket != null){ socket.close(); }
        } 
        catch (Exception ex) {
            System.out.println("Exception [Download : run(...)]");
        }
		
	}
	
}
