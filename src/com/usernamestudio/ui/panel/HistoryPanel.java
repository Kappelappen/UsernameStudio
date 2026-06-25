package com.usernamestudio.ui.panel;

import java.awt.*;
import javax.swing.*;

import com.usernamestudio.model.Username;
import com.usernamestudio.ui.window.MainWindow;

public class HistoryPanel extends JPanel {
	
	private MainWindow mWindow;
	
	public DefaultListModel<String> listModel;
	private JList<String> jltHistory;
	private JScrollPane jspHistory;
	
	private java.util.List<Username> userList;
	
	public HistoryPanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initComponents();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initComponents() {
		
		this.listModel = new DefaultListModel<String>();
		this.jltHistory = new JList<String>(listModel);
		this.jspHistory = new JScrollPane(jltHistory);
		
		this.userList = mWindow.userDAO.findAll();
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(new GridBagLayout());
		
	}
	
	private void createLayout() {
		
		this.configJList();
		this.fetchHistory();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(jspHistory, gbc);
		
	}
	
	private void configJList() {
		
		int mode = ListSelectionModel.SINGLE_SELECTION;		
		this.jltHistory.getSelectionModel().setSelectionMode(mode);
		
	}
	
	private void fetchHistory() {
		
		for (int i = 0; i < userList.size(); i++) {
			
			Username user = userList.get(i);
			String name = user.getName();
			this.listModel.addElement(name);
						
		}		
	}
}
