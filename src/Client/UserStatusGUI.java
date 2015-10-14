package Client;

import javax.swing.*;

import Protocol.XMLProtocol;
import Server.ServerForm;

import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.net.*;
import java.io.*;
import javax.swing.table.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.awt.Window.Type;
import java.awt.Dimension;

public class UserStatusGUI extends JFrame{
	private JTextField txtHostname;
	private JTextField txthostport;
	private JTextField txtusername;
	private JPasswordField pwdTxtpass;
	public UserStatusGUI() {
		setSize(new Dimension(500, 320));
		setTitle("Start form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//socket = s;
		Initially();
	}
	
	private void Initially(){
		
		table = new DefaultTableModel();
		table.addColumn("username");
		
		table.addColumn("ip");
		table.addColumn("port");
		
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblHostName = new JLabel("Host name");
		springLayout.putConstraint(SpringLayout.NORTH, lblHostName, 30, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblHostName, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblHostName);
		
		txtHostname = new JTextField();
		txtHostname.setText("192.168.56.1");
		springLayout.putConstraint(SpringLayout.NORTH, txtHostname, -3, SpringLayout.NORTH, lblHostName);
		springLayout.putConstraint(SpringLayout.WEST, txtHostname, 6, SpringLayout.EAST, lblHostName);
		getContentPane().add(txtHostname);
		txtHostname.setColumns(10);
		
		JLabel lblHostPort = new JLabel("Host port");
		springLayout.putConstraint(SpringLayout.NORTH, lblHostPort, 0, SpringLayout.NORTH, lblHostName);
		springLayout.putConstraint(SpringLayout.WEST, lblHostPort, 32, SpringLayout.EAST, txtHostname);
		getContentPane().add(lblHostPort);
		
		txthostport = new JTextField();
		txthostport.setText("6696");
		springLayout.putConstraint(SpringLayout.WEST, txthostport, 6, SpringLayout.EAST, lblHostPort);
		springLayout.putConstraint(SpringLayout.SOUTH, txthostport, 0, SpringLayout.SOUTH, lblHostName);
		getContentPane().add(txthostport);
		txthostport.setColumns(10);
		
		JLabel lblUserName = new JLabel("User name");
		springLayout.putConstraint(SpringLayout.NORTH, lblUserName, 23, SpringLayout.SOUTH, lblHostName);
		springLayout.putConstraint(SpringLayout.WEST, lblUserName, 0, SpringLayout.WEST, lblHostName);
		getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.NORTH, lblUserName);
		springLayout.putConstraint(SpringLayout.EAST, lblPassword, 0, SpringLayout.EAST, lblHostPort);
		getContentPane().add(lblPassword);
		
		txtusername = new JTextField();
		txtusername.setEnabled(false);
		springLayout.putConstraint(SpringLayout.WEST, txtusername, 0, SpringLayout.WEST, txtHostname);
		springLayout.putConstraint(SpringLayout.SOUTH, txtusername, 0, SpringLayout.SOUTH, lblUserName);
		getContentPane().add(txtusername);
		txtusername.setColumns(10);
		
		pwdTxtpass = new JPasswordField();
		pwdTxtpass.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, pwdTxtpass, 17, SpringLayout.SOUTH, txthostport);
		springLayout.putConstraint(SpringLayout.WEST, pwdTxtpass, 0, SpringLayout.WEST, txthostport);
		springLayout.putConstraint(SpringLayout.EAST, pwdTxtpass, 0, SpringLayout.EAST, txthostport);
		getContentPane().add(pwdTxtpass);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txthostport.getText().isEmpty() && !txthostport.getText().isEmpty()){
					try{
						socket = new Socket(txtHostname.getText(), Integer.parseInt(txthostport.getText()));
						
						btnLogin.setEnabled(true);
						btnRegister.setEnabled(true);
						list.setEnabled(true);
						txtHostname.setEditable(false);
						txthostport.setEnabled(false);
						btnConnect.setEnabled(false);
						txtusername.setEnabled(true);
						pwdTxtpass.setEnabled(true);
											
					}catch (Exception e){}
				}
				else {
					JOptionPane.showMessageDialog(null, "Not find server");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnConnect, 26, SpringLayout.SOUTH, lblUserName);
		springLayout.putConstraint(SpringLayout.WEST, btnConnect, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnConnect, 177, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnConnect);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginOrResgister(false);
			}
		});
		btnRegister.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnRegister, 18, SpringLayout.SOUTH, btnConnect);
		springLayout.putConstraint(SpringLayout.WEST, btnRegister, 0, SpringLayout.WEST, lblHostName);
		springLayout.putConstraint(SpringLayout.EAST, btnRegister, 0, SpringLayout.EAST, btnConnect);
		getContentPane().add(btnRegister);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginOrResgister(true);
			}
		});
		btnLogin.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnLogin, 20, SpringLayout.SOUTH, btnRegister);
		springLayout.putConstraint(SpringLayout.WEST, btnLogin, 0, SpringLayout.WEST, lblHostName);
		springLayout.putConstraint(SpringLayout.EAST, btnLogin, 0, SpringLayout.EAST, btnConnect);
		getContentPane().add(btnLogin);
		
		scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(106, 142));
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 13, SpringLayout.SOUTH, lblPassword);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 39, SpringLayout.EAST, btnConnect);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -45, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, txthostport);
		getContentPane().add(scrollPane);
		
		list = new JList<String>();
		list.setSize(new Dimension(106, 142));
		list.setEnabled(false);
		scrollPane.setColumnHeaderView(list);
		
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnClose, 19, SpringLayout.SOUTH, btnLogin);
		springLayout.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, lblHostName);
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, 42, SpringLayout.SOUTH, btnLogin);
		springLayout.putConstraint(SpringLayout.EAST, btnClose, 0, SpringLayout.EAST, btnConnect);
		getContentPane().add(btnClose);
		
		JButton btnStartChat = new JButton("Start Chat");
		btnStartChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()){
					try{
						int index = list.getSelectedIndex();
						String ip = table.getValueAt(index, 1).toString();
						String port = table.getValueAt(index, 2).toString();
						//String username = table.getValueAt(index, 0).toString();
						
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									
									Socket peerServer = new Socket(ip, Integer.parseInt(port));
									ClientGUI newChat = new ClientGUI();
									newChat.connect(peerServer);
									newChat.username = txtusername.getText();
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}catch(Exception e){}
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnStartChat, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, btnStartChat, -10, SpringLayout.EAST, scrollPane);
		getContentPane().add(btnStartChat);

	}
	private void LoginOrResgister(boolean isLogin){
		
		if (!txtusername.getText().isEmpty() && !pwdTxtpass.getText().isEmpty()){
			try{
				XMLProtocol protocol = new XMLProtocol();
				String ip = socket.getLocalAddress().toString();
				int port = socket.getLocalPort();
				
				//Send info to server
				String sendInfo;
				
				if (isLogin){
					sendInfo = protocol.logIn(txtusername.getText(), pwdTxtpass.getText(), ip,Integer.toString(port));
				}else sendInfo = protocol.register(txtusername.getText(), pwdTxtpass.getText());
				
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(sendInfo);
				dout.flush();
				
				//Recieve list user online from server
				DataInputStream recieve = new DataInputStream(socket.getInputStream());
				String lstUser = recieve.readUTF();
				//Add user to Jlist
				boolean resultLogin = User2List(lstUser);
				
				if (resultLogin){
					btnLogin.setEnabled(false);
		    		btnRegister.setEnabled(false);
		    		list.setEnabled(true);
		    		txtHostname.setEnabled(false);
		    		txthostport.setEnabled(false);
		    		btnConnect.setEnabled(false);
		    		txtusername.setEnabled(false);
		    		pwdTxtpass.setEnabled(false);
				}
			}
			catch(Exception e){
				System.out.println("kkkkk"+e.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Username or Password is empty");
		}
	}
	
	private void Register(){
		
	}
	private boolean User2List(String lstUser) throws ParserConfigurationException, SAXException, IOException{
		//Analys XML to list user
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
		Document doc = docBuilder.parse(lstUser);
        doc.getDocumentElement().normalize();
        XMLProtocol protocol = new XMLProtocol();
        if (!doc.toString().equals(protocol.loginDeny())|| !doc.toString().equals(protocol.registerDeny())){
        	NodeList nList = doc.getElementsByTagName("PEER");
    		
    		javax.swing.DefaultListModel<String> tmpListModel = new javax.swing.DefaultListModel<String>();
    		list.setModel(tmpListModel);
    		
            for(int i = 0; i< nList.getLength(); i++){
            	Node nNode = nList.item(i);
            	if(nNode.getNodeType()==Node.ELEMENT_NODE)
            	{
            		Element eElement = (Element)nNode;
                	
            		String userName = eElement.getElementsByTagName("USER_NAME").item(0).getTextContent();
            		String userIp = eElement.getElementsByTagName("IP").item(0).getTextContent();
            		String userPort = eElement.getElementsByTagName("PORT").item(0).getTextContent();
            		
            		String[] data = {userName, userIp, userPort};
            		table.addRow(data);	
            		tmpListModel.addElement(data[0]);
            	}
            }
            btnLogin.setEnabled(false);
    		btnRegister.setEnabled(false);
    		list.setEnabled(true);
    		txtHostname.setEnabled(false);
    		txthostport.setEnabled(false);
    		btnConnect.setEnabled(false);
    		txtusername.setEnabled(false);
    		pwdTxtpass.setEnabled(false);
    		return true;
        }  else {
        	JOptionPane.showMessageDialog(null, "Server denys login/register. Check username or password");
        	return false;
        }
		
	}
	
	JButton btnClose;
	JList<String> list;
	JScrollPane scrollPane;
	JButton btnLogin;
	JButton btnRegister;
	JButton btnConnect;
	
	Socket socket = null;
	public String username;
	
	private DefaultTableModel table = null;
}
