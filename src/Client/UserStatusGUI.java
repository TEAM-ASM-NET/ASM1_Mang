package Client;

import javax.swing.JFrame;
import java.io.*;
import java.net.*;

import javax.swing.SpringLayout;
import javax.swing.JButton;

import javax.swing.JList;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractListModel;

public class UserStatusGUI extends JFrame{
	
	public UserStatusGUI()
	{
		Initial();
	}
	
	private void Initial()
	{
		setMaximumSize(new Dimension(100, 100));
		getContentPane().setMaximumSize(new Dimension(100, 100));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Haha", "hoho", "hehe"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setMaximumSize(new Dimension(100, 1000));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnClose.setText(list.getSelectedValue().toString());
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, list, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, list, 226, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, list, 144, SpringLayout.WEST, getContentPane());
		getContentPane().add(list);
		
		btnClose = new JButton("Close");
		springLayout.putConstraint(SpringLayout.NORTH, btnClose, 6, SpringLayout.SOUTH, list);
		springLayout.putConstraint(SpringLayout.WEST, btnClose, 49, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, -6, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnClose, -46, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnClose);
	}
	
	private JList list = null;
	private JButton btnClose = null;
}

