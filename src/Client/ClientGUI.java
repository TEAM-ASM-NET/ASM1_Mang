package Client;




import java.io.*;
import java.net.*;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class ClientGUI extends JFrame{

	

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		newForm();
		
	}
	
	public static void newForm()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
				//	window.frame.setVisible(true);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
		
	}
	public void connect(Socket s){
		client = s;
		reciever = new RecieveMessageThread(this, s);
		reciever.start();
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
				sendMessage();
			}
		});
		this.getContentPane().add(btnSend);
		
		btnLinkSend = new JButton("...");
		springLayout.putConstraint(SpringLayout.SOUTH, btnLinkSend, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLinkSend, -146, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 1, SpringLayout.EAST, btnLinkSend);
		btnLinkSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChooseFile(e);
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

	private void actionChooseFile(java.awt.event.ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this, "Select file");
        file = fileChooser.getSelectedFile();
        
        if(file != null){
            if(!file.getName().isEmpty()){
                btnSend.setEnabled(true); String str;
                
                if(textFieldMess.getText().length() > 30){
                    String t = file.getPath();
                    str = t.substring(0, 20) + " [...] " + t.substring(t.length() - 20, t.length());
                }
                else{
                    str = file.getPath();
                }
                textFieldMess.setText(str);
            }
        }
    }
	
	private void actionSendFile(java.awt.event.ActionEvent e) {
		
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
		sender.start();
	}

	public javax.swing.JButton btnSend;
    public javax.swing.JButton btnLinkSend;
    public javax.swing.JTextField textFieldMess;
   
    private JScrollPane scrPnlMSg;
    public JTextArea txtrMsg;
    
    private SendMessageThread sender = null;
    private RecieveMessageThread reciever = null;
  //private JFrame frame;
  	public Socket client;
    //public int port;
    public String username;
  //  public Thread clientThread;
    public File file;
    
  
}
