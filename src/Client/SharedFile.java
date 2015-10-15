package Client;


import java.io.*;
import java.net.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import Protocol.XMLProtocol;

public class SharedFile  extends Thread {
	
	ClientGUI frame;
	Socket socket;
	boolean running = true;
	boolean check =false;
	XMLProtocol protocol;
	private DataInputStream input;
	private ObjectOutputStream output;
	//public boolean accept = true;
    public SharedFile(Socket socket )  {
	// TODO Auto-generated constructor stub
    	this.socket=socket;
    	protocol = new XMLProtocol();
    }
 
	@Override
	public void run() {
		// TODO Auto-generated method stud
		int choise = 0;
		try{
			output = new ObjectOutputStream(socket.getOutputStream());
	        //output.flush();
	       // input = new DataInputStream(socket.getInputStream());
	        
			
				System.out.print("Lối");
				
				
				if(frame.Sender) {
					System.out.print("Lối2");
					choise = JOptionPane.showConfirmDialog(null, frame.username + " want to send " + frame.filepath + " to you?", "Message",
					        JOptionPane.YES_NO_OPTION);
                    if(choise == JOptionPane.YES_OPTION) {
                    	send(protocol.fileRequest("FILE_REQ_ACK"));
                    }
                    else {
                    	JOptionPane.showMessageDialog(null, frame.username + " dosen't accept");
                        frame.textFieldMess.setText("");
                        send(protocol.fileRequestNoAck());
                    }
                }
                if(check) {
                    JOptionPane.showMessageDialog(null, frame.username + " accept");
                    System.out.println("Ingoing : file");
                    String saveTo = "";
                    JFileChooser jf = new JFileChooser();
                    jf.setSelectedFile(new File(frame.filepath));
                    int returnVal = jf.showSaveDialog(frame);
                    saveTo = jf.getSelectedFile().getPath();
                    @SuppressWarnings("resource")
					FileOutputStream Out = new FileOutputStream(saveTo);
                    if(saveTo != null && returnVal == JFileChooser.APPROVE_OPTION){
                        byte[] buffer = new byte[1024];
                        int count;
                        while((count = input.read(buffer)) >= 0){
                            Out.write(buffer, 0, count);
                        }
                        Out.flush();
                    }
                    check=false;
                }   
              if(choise==JOptionPane.YES_OPTION) {
                	frame.textFieldMess.setText("");
                    frame.txtrMsg.append("Ban da nhan duoc mot file tu" + frame.username);
                }
			}
			
		catch (Exception e){
			running =false;
		}
		
	}
	public void send(String message){
		try {
			output.writeUTF(message);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendfile(String _filepath) {
		try {
			
			@SuppressWarnings("resource")
			FileInputStream fileshare = new FileInputStream(_filepath);
			byte[] buffer = new byte[1024];
		    int count;
			 while((count = fileshare.read(buffer)) >= 0){
			      output.write(buffer, 0, count);
	           }             
		      output.flush();
		      check=true;
	    } catch (IOException ex) {
			 System.out.println("Error: Can't send");
	    }
	}
}
