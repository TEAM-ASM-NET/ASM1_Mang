package Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Upload implements Runnable{

    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream In;
    public OutputStream Out;
    public File file;
    public ClientGUI ui;
    
    public Upload(Socket socket, File filepath, ClientGUI frame){
        super();
        try {
            file = filepath; ui = frame;
            this.socket = socket;
            Out = socket.getOutputStream();
            In = new FileInputStream(filepath);
        } 
        catch (Exception ex) {
            System.out.println("Exception [Upload : Upload(...)]");
        }
    }
    
    @Override
    public void run() {
        try {       
            byte[] buffer = new byte[1024];
            int count;
            
            while((count = In.read(buffer)) >= 0){
                Out.write(buffer, 0, count);
            }
            Out.flush();
            
            ui.txtrMsg.append("[Applcation > Me] : File upload complete\n");
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
