package Client;
import java.io.*;
import java.net.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import Client.ClientGUI;
import Protocol.XMLProtocol;
public class SharedFile  extends Thread {
	
	ClientGUI frame;
	Socket socket;
	boolean running = true;
	public String filename;
	private DataInputStream input;
	private DataOutputStream output;
    public SharedFile(Socket socket ) {
	// TODO Auto-generated constructor stub
    	this.socket = socket;
    	
    }
    public void send(String message) throws IOException{
		output.writeUTF(message);
		output.flush();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			output = new DataOutputStream(socket.getOutputStream());
			output.flush();
			input = new DataInputStream(socket.getInputStream());
			while (running)
			{
				String message = input.readUTF();
				if (message!=null)
				{
					Document doc = docBuilder.parse(new InputSource(new StringReader(message)));
					doc.getDocumentElement().normalize();
					if(doc.getDocumentElement().getNodeName().equals("FILE_REQ")) {
                        filename = doc.getDocumentElement().getFirstChild().getTextContent();
                        int choise = JOptionPane.showConfirmDialog(null, frame.username + " want to send " + filename + " to you?", "Message",
						        JOptionPane.YES_NO_OPTION);
                        if(choise == JOptionPane.YES_OPTION) {
                            send(new XMLProtocol().fileRequest(filename));
                            
                        }
                        else {
                            send(new XMLProtocol().fileRequestNoAck());
                        }
                    }
                    else if(doc.getDocumentElement().getNodeName().equals("FILE_REQ_ACK")) {
                        JOptionPane.showMessageDialog(null, frame.username + " accept");
                    }
                    else if(doc.getDocumentElement().getNodeName().equals("FILE_REQ_NOACK")) {
                        JOptionPane.showMessageDialog(null, frame.username + " dosen't accept");
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
                            while((count = input.read(buffer)) >= 0){
                                Out.write(buffer, 0, count);
                            }
                            Out.flush();
                        }
                    }
                    else if(doc.getDocumentElement().getNodeName().equals("FILE_DATA_END")) {
                        frame.textFieldMess.setText("");
                        frame.txtrMsg.append("You get the a file from" + frame.username+"\n");
                    }
				}
			}
			
		}catch(IOException | ParserConfigurationException | SAXException e)
		{
			running = false;
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
	    } catch (IOException ex) {
			 System.out.println("Error: Can't send");
	    }
	}
}
