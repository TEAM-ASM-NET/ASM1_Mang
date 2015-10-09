package Client;
import Client.ClientGUI;
import java.io.*;
import java.net.*;

public  class UploadFile implements Runnable{
	public String addr;
    public int port;
    public Socket socket;
    public FileInputStream In;
    public OutputStream Out;
    public File file;
    public ClientGUI gui;
    public UploadFile(String addr, int port, File filelink, ClientGUI frame){
        super();
        try {
            file = filelink; gui = frame;
            socket = new Socket(InetAddress.getByName(addr), port);
            Out = socket.getOutputStream();
            In = new FileInputStream(filelink);
        } 
        catch (Exception ex) {
            System.out.println("Exception [Upload : Upload(...)]");
        }
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {       
            byte[] buffer = new byte[1024];
            int count;     
            while((count = In.read(buffer)) >= 0){
                Out.write(buffer, 0, count);
            }
            Out.flush();
            
           // gui.jTextArea1.append("[Applcation > Me] : File upload complete\n");
            gui.btnLinkSend.setEnabled(true);
            gui.btnSend.setEnabled(true);
            gui.textFieldMess.setVisible(true);
            
            if(In != null){ In.close(); }
            if(Out != null){ Out.close(); }
            if(socket != null){ socket.close(); }
        }
        catch (Exception ex) {
            System.out.println("Exception [Upload : run()]");
            ex.printStackTrace();
        }
		
	}
}

