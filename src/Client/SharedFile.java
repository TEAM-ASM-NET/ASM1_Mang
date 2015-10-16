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
	public String filename;
	private DataInputStream input;
	private DataOutputStream output;
	public boolean accept = false;
    public SharedFile(Socket socket )  {
	// TODO Auto-generated constructor stub
    	this.socket = socket;
    }
 
	@Override
	public void run() {
		// TODO Auto-generated method stud
		
		try{
			output = new DataOutputStream(socket.getOutputStream());
	        output.flush();
	        
	        input = new DataInputStream(socket.getInputStream());
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			while(running)
			{
					String message = input.readUTF();
					Document doc = docBuilder.parse(new InputSource(new StringReader(message)));
					doc.getDocumentElement().normalize();
					System.out.println(message);
					
				if(doc.getDocumentElement().getNodeName().equals("FILE_REQ")) {
					
                    filename = doc.getDocumentElement().getFirstChild().getTextContent();
                   
                    int choise = JOptionPane.showConfirmDialog(null, " want to send " + filename + " to you?", "Message",
					        JOptionPane.YES_NO_OPTION);
                    if(choise == JOptionPane.YES_OPTION) {
                    
                        send(new XMLProtocol().fileRequestAck("6696"));
                        
                    }
                    else {
                        send(new XMLProtocol().fileRequestNoAck());
                    }
                }
                else if(doc.getDocumentElement().getNodeName().equals("FILE_REQ_ACK")) {
                	frame.txtrMsg.setText("Đối phương đã chấp nhận yêu cầu");
                }
                else if(doc.getDocumentElement().getNodeName().equals("FILE_REQ_NOACK")) {
                	frame.txtrMsg.setText("Đối phương từ chối yêu cầu");
                    frame.textFieldMess.setText("");
                }
                else if(doc.getDocumentElement().getNodeName().equals("FILE_DATA_BEGIN")) {
                    String saveTo = "";
                    JFileChooser jf = new JFileChooser();
                    jf.setSelectedFile(new File(filename));
                    int returnVal = jf.showSaveDialog(frame);
                    saveTo = jf.getSelectedFile().getPath();
                    @SuppressWarnings("resource")
					FileOutputStream Out = new FileOutputStream(saveTo);
                    if(saveTo != null && returnVal == JFileChooser.APPROVE_OPTION){
                        byte[] buffer = new byte[1024];
                        int count;
                        while((count = input.read(buffer)) >= 0 ){
                        	System.out.println("hfsk"+count);
                            Out.write(buffer, 0, count);
                            Out.flush();
                            if(count< 1024) break;
                        }
                        System.out.println("hfhsadkfhaslkhflsda"+count);
                        accept = true;
                       // Out.flush();
                    }
                }
                else if(doc.getDocumentElement().getNodeName().equals("FILE_DATA_END")) {
                    frame.txtrMsg.append("Bạn đã nhận được một file" );
                }
			}
		}catch (Exception e){
			System.out.println("LOi khong ta");
			running=false;
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

	public void sendfile(String _filepath) { // day
		try {
			@SuppressWarnings("resource")
			FileInputStream fileshare = new FileInputStream(_filepath);
			byte[] buffer = new byte[1024];
		    int count;
			            
			 while((count = fileshare.read(buffer)) >= 0){
			      output.write(buffer, 0, count);
	           }             
		      output.flush();
	    } catch (IOException ex) {
			 System.out.println("Error: Can't send");
	    }
	}
}
