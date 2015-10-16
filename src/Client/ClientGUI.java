package Client;

import Protocol.*;
import Server.Server;

import java.io.*;
import java.net.*;
import java.awt.event.*;

import javax.swing.*;

public class ClientGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 */
	public ClientGUI() {
		
		initialize();
		
	}
	public void connect(Socket s) throws IOException{
//		client = s;
//		reciever = new RecieveMessageThread(this, s);
//		reciever.start();
        StartShareFile(s);
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		//frame = new JFrame();
		this.setBounds(100, 100, 434, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		this.getContentPane().setLayout(springLayout);
		
		btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSend, -85, SpringLayout.EAST, getContentPane());
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ClientGUI f = new ClientGUI();
				//f.setVisible

				if (Sender)
				{
					long size = file.length();
					System.out.print(size);
					if (size<150*1024*1024)
					{
						share.send(new XMLProtocol().fileRequest(file.getName()));
						
						share.send( new XMLProtocol().fileDataBegin());
						share.sendfile(filepath);
						share.send(new XMLProtocol().fileDataEnd());
						textFieldMess.setText("");
						txtrMsg.setText("File shared success\n");
						Sender = false;
					}
					else 
					{
						Sender = false;
						textFieldMess.setText("");
						txtrMsg.setText("File is size too large\n");
					}
							
				}
				else{
					sendMessage();
				}

			}
		});
		this.getContentPane().add(btnSend);
		
		btnLinkSend = new JButton("...");
		springLayout.putConstraint(SpringLayout.SOUTH, btnLinkSend, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLinkSend, -146, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 1, SpringLayout.EAST, btnLinkSend);
		btnLinkSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChooseFile();
			}
		});
		this.getContentPane().add(btnLinkSend);
		
		textFieldMess = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textFieldMess, 37, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldMess, -13, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldMess, -197, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnLinkSend, 3, SpringLayout.EAST, textFieldMess);
		textFieldMess.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					btnSend.doClick();
				}
			}
		});
		this.getContentPane().add(textFieldMess);
		textFieldMess.setColumns(10);
		
		scrPnlMSg = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, btnLinkSend, 13, SpringLayout.SOUTH, scrPnlMSg);
		springLayout.putConstraint(SpringLayout.NORTH, scrPnlMSg, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrPnlMSg, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrPnlMSg, 215, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrPnlMSg, 408, SpringLayout.WEST, getContentPane());
		getContentPane().add(scrPnlMSg);
		
		txtrMsg = new JTextArea();
		txtrMsg.setEditable(false);
		txtrMsg.setWrapStyleWord(true);
		txtrMsg.setText("txtMsg");
		scrPnlMSg.setViewportView(txtrMsg);
		
		JButton btnOnline = new JButton("Online");
		btnOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnOnline, 0, SpringLayout.NORTH, btnSend);
		springLayout.putConstraint(SpringLayout.EAST, btnOnline, 0, SpringLayout.EAST, scrPnlMSg);
		getContentPane().add(btnOnline);	
	}	
	public void actionChooseFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showDialog(this, "Select File");
	    file = fileChooser.getSelectedFile();
	    if(file != null){
            if(!file.getName().isEmpty()){
            	Sender = true;
                textFieldMess.setText(file.getName());
                filepath = file.getPath();;   
                
            }
	    }
	}
	
	public void StartShareFile( Socket socket) {
			share = new SharedFile(socket);
			share.start();
	}
	public void addMessage(String msg, String src)
	{
		String message = src + ":" + msg + "\r\n";
		txtrMsg.setText(txtrMsg.getText() + message);
	}

	public void sendMessage()
	{
		addMessage(textFieldMess.getText(), "Me");
		sender = new SendMessageThread(client, textFieldMess.getText());
		//System.out.println("Msg trong GUI: " + textFieldMess.getText());
		sender.start();
		textFieldMess.setText("");
	}

	public javax.swing.JButton btnSend;
    public javax.swing.JButton btnLinkSend;
    public javax.swing.JTextField textFieldMess;
   
    private JScrollPane scrPnlMSg;
    public JTextArea txtrMsg;
    SharedFile share;
    private SendMessageThread sender = null;
    private RecieveMessageThread reciever = null;
  //private JFrame frame;
  	public Socket client;
    //public int port;
    public String username = null;
  //  public Thread clientThread;
    public File file;
    private static DataInputStream input;
    private static DataOutputStream output;
    public String filepath;
    public boolean Sender = false;

  
}
