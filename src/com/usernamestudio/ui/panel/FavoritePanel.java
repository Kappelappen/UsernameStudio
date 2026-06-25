package com.usernamestudio.ui.panel;

import java.awt.*;
import javax.swing.*;

import com.usernamestudio.model.Username;
import com.usernamestudio.ui.window.MainWindow;

public class FavoritePanel extends JPanel {
	
	private MainWindow mWindow;
	
	private DefaultListModel<String> listModel;
	private JList<String> jltHistory;
	private JScrollPane jspHistory;
	
	public FavoritePanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initComponents();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initComponents() {
		
		this.listModel = new DefaultListModel();
		this.jltHistory = new JList<String>(listModel);
		this.jspHistory = new JScrollPane(jltHistory);
				
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(new GridBagLayout());
		
	}
	
	private void createLayout() {
		
		this.configJList();
		this.fetchFavorites();
		
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
	
	public void fetchFavorites() {
		
		this.listModel.removeAllElements();
	
		java.util.List<Username> userList = mWindow.userDAO.findFavorites();
		
		for (int i = 0; i < userList.size(); i++) {
			
			Username user = userList.get(i);
			String name = user.getName();			
			this.listModel.addElement(name);
			
		}
		
	}
}
