package Client;


import Client.UploadFile;
import Client.DownloadFile;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import socket.Message;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI {

	private JFrame frame;
	public Socket client;
    public int port;
    public String username;
    public Thread clientThread;
    public File file;
  

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 488, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		frame.getContentPane().add(btnSend);
		
		btnLinkSend = new JButton("...");
		springLayout.putConstraint(SpringLayout.SOUTH, btnLinkSend, -33, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnSend, 0, SpringLayout.NORTH, btnLinkSend);
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 6, SpringLayout.EAST, btnLinkSend);
		btnLinkSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChooseFile(e);
			}
		});
		frame.getContentPane().add(btnLinkSend);
		
		textFieldMess = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, btnLinkSend, 6, SpringLayout.EAST, textFieldMess);
		springLayout.putConstraint(SpringLayout.WEST, textFieldMess, 67, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldMess, -167, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldMess, -35, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(textFieldMess);
		textFieldMess.setColumns(10);
		
		
		
		
	}
	private void actionChooseFile(java.awt.event.ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this.frame, "Select file");
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
	
//	private void actionSendFile(java.awt.event.ActionEvent e) {
//		
//	}

    public javax.swing.JButton btnSend;
    public javax.swing.JButton btnLinkSend;
    public javax.swing.JTextField textFieldMess;
}
