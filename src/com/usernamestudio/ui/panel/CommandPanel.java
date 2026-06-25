package com.usernamestudio.ui.panel;

import java.awt.*;
import javax.swing.*;

import com.usernamestudio.ui.window.MainWindow;

public class CommandPanel extends JPanel {
	
	private MainWindow mWindow;
	
	public JButton btnGenerator;
	public JButton btnCopy;
	public JButton btnFavorite;
	
	public CommandPanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initComponents();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initComponents() {
	
		this.btnGenerator = createJButton("Generate usernames", "Generate");
		this.btnCopy = createJButton("Copy to clipboard", "Copy");
		this.btnFavorite = createJButton("Add to favorites", "Favoirte");
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(new GridBagLayout());
		
	}
	
	private void createLayout() {
				
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.insets = new Insets(0,0,0,3);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.NONE;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		this.add(btnGenerator, gbc);
		
		gbc.gridx = 1; 
		gbc.gridy = 0;
		gbc.insets = new Insets(0,0,0,3);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.NONE;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		this.add(btnCopy, gbc);
		
		gbc.gridx = 2; 
		gbc.gridy = 0;
		gbc.insets = new Insets(0,0,0,3);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		this.add(btnFavorite, gbc);
		
	}
	
	private JButton createJButton(String tooltip, 
		String text) {
		
		Dimension dim = new Dimension(106,33);
		JButton button = new JButton();
		
		button.setFocusable(false);
		button.setMinimumSize(dim);
		button.setPreferredSize(dim);
		button.setMaximumSize(dim);
		button.setToolTipText(tooltip);
		button.setText(text);		
		
		return button;
		
	}
}
