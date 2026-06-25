package com.usernamestudio.ui.panel;

import java.awt.*;
import javax.swing.*;

import com.usernamestudio.model.Word;
import com.usernamestudio.ui.window.MainWindow;

public class WordPanel extends JPanel {
	
	private MainWindow mWindow;
	
	private DefaultListModel<Word> listModel;
	private JList<Word> jltHistory;
	private JScrollPane jspHistory;
	
	public WordPanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initComponents();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initComponents() {
		
		this.listModel = new DefaultListModel<Word>();
		this.jltHistory = new JList<Word>(listModel);
		this.jspHistory = new JScrollPane(jltHistory);
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(new GridBagLayout());
		
	}
	
	private void createLayout() {
		
		this.configJList();
		this.fetchWords();
		
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
	
	public void fetchWords() {
		
		this.listModel.clear();
		
		java.util.List<Word> list = mWindow.wordDAO.findAll();
		
		for (int i = 0; i < list.size(); i++) {
			
			Word word = list.get(i);
			this.listModel.addElement(word);
			
		}		
	}
}
