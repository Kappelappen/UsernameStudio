package com.usernamestudio.ui.window;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.usernamestudio.dao.StyleDAO;
import com.usernamestudio.dao.StyleDAOImpl;
import com.usernamestudio.dao.UsernameDAO;
import com.usernamestudio.dao.UsernameDAOImpl;
import com.usernamestudio.dao.WordDAO;
import com.usernamestudio.dao.WordDAOImpl;
import com.usernamestudio.database.Connector;
import com.usernamestudio.generator.UsernameGenerator;
import com.usernamestudio.model.Style;
import com.usernamestudio.model.Username;
import com.usernamestudio.ui.panel.CommandPanel;
import com.usernamestudio.ui.panel.FavoritePanel;
import com.usernamestudio.ui.panel.HistoryPanel;
import com.usernamestudio.ui.panel.WordPanel;

public class MainWindow extends JFrame {
	
	private String title;
	
	public Connection conn;
	public StyleDAO styleDAO;
	public WordDAO wordDAO;
	public UsernameDAO userDAO;	
	
	private Box leftBox;
	
	private JLabel lblStyle;
	private JLabel lblTheme;
	private JLabel lblCustomWord;
	
	private JComboBox<Style> jcbStyleData;
	
	private JSlider jslLength;
	private JCheckBox jcbNumbers;
	private JCheckBox jcbCapitalize;
	private JCheckBox jcbUseSeparator;
	private JCheckBox jcbSpecialCharacters;
	
	private JRadioButton jrbProfessional;
	private JRadioButton jrbGaming;
	private JRadioButton jrbFantasy;
	private JRadioButton jrbRandom;

	private JTextField txtCustomWord;
	
	private ButtonGroup btnGroupLeft;
	
	private UsernameGenerator generator;
		
	private HistoryPanel jplHistory;
	private FavoritePanel jplFavorite;
	private WordPanel jplWord;
	
	private CommandPanel jplCommand;
	
	private JTabbedPane jtbNameOwner;
	
	private JPanel jplSettings;
	
	private JLabel lblUsername;
	
	private JPanel jplLeftView;
	private JPanel jplRightView;
	
	private JSplitPane jspMainView;
	
	private JPanel jplContainer;
	
	public MainWindow(String title) {
		
		this.title = title;
	
		this.initDAO();
		this.initComponents();
		this.configJComboBox();
		this.configJFrame();
		this.createLayout();
		this.registerEvents();
		
	}
	
	private void initDAO() {
		
		try {
		this.conn = Connector.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.styleDAO = new StyleDAOImpl(conn);
		this.wordDAO = new WordDAOImpl(conn);				
		this.userDAO = new UsernameDAOImpl(conn);
		
	}
	
	private void initComponents() {
		
		this.leftBox = Box.createHorizontalBox();
		
		this.lblStyle = new JLabel("Style");
		this.lblTheme = new JLabel("Theme");
		this.lblCustomWord = new JLabel("Custom word");
		
		this.jcbStyleData = new JComboBox<Style>();
		this.jslLength = new JSlider(6, 20, 12);
		
		this.jcbNumbers = createJCheckBox("Include numbers");
		this.jcbCapitalize = createJCheckBox("Capitatlize first letter");
		this.jcbUseSeparator = createJCheckBox("Use separator");
		this.jcbSpecialCharacters = createJCheckBox("Special characters");
		
		this.jrbProfessional = createJRadioButton("Professional");
		this.jrbGaming = createJRadioButton("Gaming");
		this.jrbFantasy = createJRadioButton("Fantasy");
		this.jrbRandom = createJRadioButton("Random");
		
		this.txtCustomWord = new JTextField();
		
		this.btnGroupLeft = new ButtonGroup();
		
		this.jplHistory = new HistoryPanel(this);
		this.jplFavorite = new FavoritePanel(this);
		this.jplWord = new WordPanel(this);
		
		this.jplCommand = new CommandPanel(this);
		
		this.jtbNameOwner = new JTabbedPane(JTabbedPane.TOP);
		
		this.generator = new UsernameGenerator();
		
		this.jplSettings = createJPanel(275,500, "Settings");
		
		this.lblUsername = new JLabel();
		
		this.jplLeftView = new JPanel(new GridBagLayout());
		this.jplRightView = new JPanel(new GridBagLayout());
		
		this.jspMainView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);		
		this.jplContainer = new JPanel(new GridBagLayout());
		
	}
	
	private void configJFrame() {
		
		this.setTitle(title);
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setContentPane(jplContainer);
				
	}
	
	private void createLayout() {
				
		this.configJTabbedPane();
		
		this.configButtonGroup();
		this.buildJPanelSettings();
		this.configJSliderLength();
		this.buildJPanelLeftView();
		this.buildJPanelRightView();
		this.configJSplitPane();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(6,6,6,6);
		gbc.weightx = GridBagConstraints.NONE;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.jplContainer.add(jplCommand, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0,0,0,0);
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		this.jplContainer.add(jspMainView, gbc);
		
	}
	
	private void configJTabbedPane() {
		
		this.jtbNameOwner.addTab("History", jplHistory);
		this.jtbNameOwner.addTab("Favorite", jplFavorite);
		this.jtbNameOwner.addTab("Words", jplWord);
		
	}
	
	private void configButtonGroup() {
		
		this.btnGroupLeft.add(jrbProfessional);
		this.btnGroupLeft.add(jrbGaming);
		this.btnGroupLeft.add(jrbFantasy);
		this.btnGroupLeft.add(jrbRandom);
		
	}
	
	private void configJComboBox() {
		
		this.jcbStyleData.addItem(null);		
		this.jcbStyleData.setFocusable(false);
		
		java.util.List<Style> list = styleDAO.findAll();
		
		for (int i = 0; i < list.size(); i++) {
			
			Style style = list.get(i);
			this.jcbStyleData.addItem(style);
			
		}		
	}
	
	private void configJSliderLength() {
		
		TitledBorder tBorderMain = new TitledBorder("Username Length");
		tBorderMain.setTitleFont(new Font("Arial", Font.PLAIN,16));
		
		EmptyBorder emptyBorder = new EmptyBorder(6,0,6,0);
		
		CompoundBorder compBorder = 
			new CompoundBorder(tBorderMain,emptyBorder);		
		
		this.jslLength.setMinimum(2);
		this.jslLength.setMaximum(5);
		this.jslLength.setValue(2);		
		
		jslLength.setPaintTicks(true);
		jslLength.setPaintLabels(true);

		jslLength.setMajorTickSpacing(1);
		jslLength.setMinorTickSpacing(1);
		
	}
	
	private void buildJPanelSettings() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.NONE;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(10,8,6,6);
		this.jplSettings.add(lblStyle, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(10,6,6,6);
		this.jplSettings.add(jcbStyleData, gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 1;
		gbc.gridwidth = 6;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.NONE;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(6,8,6,6);
		this.jplSettings.add(lblCustomWord, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 6;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(0,6,6,6);
		this.jplSettings.add(txtCustomWord, gbc);
				
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 6;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(6,6,0,0);
		this.jplSettings.add(jslLength, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(10,6,6,6);
		this.jplSettings.add(jcbNumbers, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(0,6,6,6);
		this.jplSettings.add(jcbCapitalize, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(0,6,6,6);
		this.jplSettings.add(jcbUseSeparator, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(0,6,0,6);
		this.jplSettings.add(jcbSpecialCharacters, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(16,6,6,6);
		this.jplSettings.add(lblTheme, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(6,6,6,6);
		this.jplSettings.add(jrbProfessional, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(0,6,6,6);
		this.jplSettings.add(jrbGaming, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.insets = new Insets(0,6,6,6);
		this.jplSettings.add(jrbFantasy, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 10;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0,6,6,6);
		this.jplSettings.add(jrbRandom, gbc);
		
	}
	
	private void buildJPanelLeftView() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10,10,0,6);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.NONE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.jplLeftView.add(jplSettings, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 10;
		gbc.insets = new Insets(0,0,0,0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.fill = GridBagConstraints.BOTH;
		this.jplLeftView.add(leftBox, gbc);
		
	}
	
	private void buildJPanelRightView() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.lblUsername.setHorizontalAlignment(JLabel.CENTER);
		this.lblUsername.setBorder(BorderFactory.createEtchedBorder());
		this.lblUsername.setPreferredSize(lblUsername.getMinimumSize());
		this.jtbNameOwner.setPreferredSize(jtbNameOwner.getMinimumSize());
		
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10,6,0,6);
		this.jplRightView.add(lblUsername, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(6,6,6,0);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = GridBagConstraints.BOTH;
		gbc.weighty = GridBagConstraints.BOTH;
		gbc.fill = GridBagConstraints.BOTH;
		this.jplRightView.add(jtbNameOwner, gbc);
		
	}
	
	private void registerEvents() {
		
		this.jplCommand.btnGenerator.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        Style style =
		            (Style) jcbStyleData.getSelectedItem();

		        if (style == null) {

		            JOptionPane.showMessageDialog(
		                MainWindow.this,"Choose a style first."
		            );

		            return;
		        }

		        java.util.List<String> words =
		            wordDAO.findByStyle(style.getId());

		        if (words == null || words.size() < 2) {

		            JOptionPane.showMessageDialog(
		            	MainWindow.this,"Too few words selected for Style."
		            );

		            return;
		        }

		        String username =
		            generator.generate(
		                words,
		                jslLength.getValue(),
		                jcbNumbers.isSelected(),
		                jcbUseSeparator.isSelected(),
		                jcbSpecialCharacters.isSelected(),
		                jcbCapitalize.isSelected()
		            );

		        if (username == null || username.isEmpty()) {

		            JOptionPane.showMessageDialog(
		            	MainWindow.this, "Could not generate the username"
		            );

		            return;
		        }

		        // Visa i UI
		        lblUsername.setText(username);

		        // Lägg till i historiken
		        jplHistory.listModel.addElement(username);

		        // Skapa modellobjekt
		        Username user = new Username();

		        user.setName(username);
		        user.setStyle(style.getName());

		        // Slider-värdet är mer logiskt än username.length()
		        user.setLength(jslLength.getValue());

		        user.setHasNumbers(
		            jcbNumbers.isSelected()
		        );

		        user.setHasUnderscore(
		            jcbUseSeparator.isSelected()
		        );

		        user.setHasSymbols(
		            jcbSpecialCharacters.isSelected()
		        );

		        userDAO.insert(user);
		        jplWord.fetchWords();
		        
		        String customWord = txtCustomWord.getText().trim();
		        
		        if (style == null || customWord.isEmpty()) {
		            return;
		        }

		        wordDAO.insert(style.getId(), customWord);
		        txtCustomWord.setText(null);
		        
		    }		
		});
		
		this.jplCommand.btnCopy.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        String username =
		            lblUsername.getText();

		        if (username == null ||
		            username.trim().isEmpty()) {
		            return;
		        }

		        StringSelection selection =
		            new StringSelection(username);

		        Toolkit.getDefaultToolkit()
		               .getSystemClipboard()
		               .setContents(selection, null);
		    }
		});		
		
		this.jplCommand.btnFavorite.addActionListener(
			new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String username = lblUsername.getText();

				if (username == null || username.trim().isEmpty()) {
			        return;			        
				}
				
				userDAO.setFavorite(username);
				jplFavorite.fetchFavorites();
			        
			}			 
		});
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				jFrameClosing();
				
			}			
		});
	}
	
	private void configJSplitPane() {
		
		this.jspMainView.setBorder(null);
		this.jspMainView.setContinuousLayout(true);
		this.jspMainView.setDividerLocation(250);
		this.jspMainView.setDividerSize(10);
		this.jspMainView.setLeftComponent(jplLeftView);
		this.jspMainView.setRightComponent(jplRightView);
		
	}
	
	private void jFrameClosing() {
		
		try {			
		this.conn.close();			
		} catch (java.sql.SQLException ex) { ex.printStackTrace(); }
		
		this.dispose();
		System.exit(0);
		
	}
	
	private JCheckBox createJCheckBox(String label) {
		
		JCheckBox checkBox = new JCheckBox();		
		checkBox.setFocusable(false);
		checkBox.setText(label);
		
		return checkBox;
		
	}
	
	private JRadioButton createJRadioButton(String label) {
		
		JRadioButton button = new JRadioButton();
		
		button.setFocusable(false);
		button.setText(label);
		
		return button;
		
	}
	
	private JButton createJButton(String label, 
		int width, int height) {
		
		Dimension dim = new Dimension(width,height);
		JButton button = new JButton();
		
		button.setFocusable(false);
		button.setMinimumSize(dim);
		button.setPreferredSize(dim);
		button.setMaximumSize(dim);
		button.setText(label);
		
		return button;
		
	}
	
	private JPanel createJPanel(int width, 
		int height, String label) {
		
		TitledBorder tBorderMain = new TitledBorder(label);
		
		tBorderMain.setTitleFont(new Font("Arial", Font.PLAIN,16));
		tBorderMain.setBorder(BorderFactory.createEtchedBorder());
		
		EmptyBorder empBorder = new EmptyBorder(6,0,6,0);
		CompoundBorder compBorder = new CompoundBorder(tBorderMain,empBorder);
		
		Dimension dim = new Dimension();
		
		if (width != 0 || height != 0) {
			
			dim.width = width;
			dim.height = height;
			
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.setBorder(compBorder);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);		
		
		return panel;
		
	}
}
