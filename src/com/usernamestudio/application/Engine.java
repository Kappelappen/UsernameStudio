package com.usernamestudio.application;

import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.usernamestudio.database.Database;
import com.usernamestudio.ui.window.MainWindow;

public class Engine {

	public static void main(String[] args) {
		
		Engine.config();
		Engine.start();
		
	}
	
	private static void config() {
		
		UIManager.put("Button.font", new Font("Arial",Font.PLAIN,16));
		UIManager.put("CheckBox.font", new Font("Arial",Font.PLAIN,16));
		UIManager.put("RadioButton.font", new Font("Arial",Font.PLAIN,16));
		UIManager.put("ComboBox.font", new Font("Arial",Font.PLAIN,16));
		UIManager.put("Label.font", new Font("Arial",Font.PLAIN,16));
		
		Database db = new Database();
		db.doCheckDb();
		
	}
	
	private static void start() {
		
		Runnable rbl = new Runnable() {
			
			@Override
			public void run() {
				
				MainWindow mWindow = new MainWindow("UsernameStudio");
				mWindow.setVisible(true);
				
			}			
		};
		
		SwingUtilities.invokeLater(rbl);
		
	}	
}
