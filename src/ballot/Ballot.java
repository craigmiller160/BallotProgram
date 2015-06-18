package ballot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ballotcreator.BallotCreator;
import data.BallotDataList;
import data.BallotQ;
import data.BallotTitle;
import data.Candidate;
import data.ElectedOffice;
import exceptions.BallotMissingValuesException;
import startscreen.StartScreen;
import utilities.BallotFonts;
import utilities.ElectionType;

public class Ballot {
	
	private BallotDataList ballotDataList;
	private ArrayList<ElectionType> etArray = new ArrayList<ElectionType>();
	private ArrayList<BElectionTypePanel> etPanelArray = new ArrayList<BElectionTypePanel>();
	private ArrayList<BOfficePanel> oPanelList = new ArrayList<BOfficePanel>();
	private ArrayList<BQuestionPanel> bqPanelList = new ArrayList<BQuestionPanel>();
	private ArrayList<JPanel> sPanelList = new ArrayList<JPanel>();
	private JFrame window;
	private JTabbedPane etTabs;
	private JButton voteButton;
	private JPanel selectionDisplayPanel;
	private JLabel selectionLabel;
	
	public Ballot(BallotDataList dl){
		this.ballotDataList = dl;
		
		Border etched = BorderFactory.createEtchedBorder();
		
		ImageIcon windowIcon = new ImageIcon("Icons/Black/VoteIcon.png");
		
		window = new JFrame("Ballot");
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setIconImage(windowIcon.getImage());
		window.addWindowListener(new BallotWindowListener());
		
		JMenuBar menuBar = createMenuBar();
		window.setJMenuBar(menuBar);
		
		etTabs = new JTabbedPane();
		etTabs.setBorder(etched);
		
		
		for(int i = 0; i < ballotDataList.getOfficeListSize(); i++){
			ElectionType et = ballotDataList.getOffice(i).getOfficeElectionType();
			boolean match = false;
			for(ElectionType e : etArray){
				if(et == e){
					match = true;
				}else{
					match = false;
				}
			}
			
			if(match == false){
				BElectionTypePanel etPanel = new BElectionTypePanel(et);
				etPanelArray.add(etPanel);
				etArray.add(et);
			}
		}
		
		BElectionTypePanel etPanel;
		JScrollPane mainScrollPane;
		for(int i = 0; i < etPanelArray.size(); i++){
			etPanel = etPanelArray.get(i);
			for(int i2 = 0; i2 < ballotDataList.getOfficeListSize(); i2++){
				ElectedOffice eo = ballotDataList.getOffice(i2);
				if(etPanel.getElectionType() == eo.getOfficeElectionType()){
					BOfficePanel oPanel = new BOfficePanel(eo);
					mainScrollPane = new JScrollPane(oPanel);
					mainScrollPane.getVerticalScrollBar().setUnitIncrement(10);
					etPanel.add(mainScrollPane, eo.getOfficeTitle());
					oPanelList.add(oPanel);
					
					if(eo.getOfficeVoteFor() == 1){
						ArrayList<JRadioButton> radioList = oPanel.getRadioList();
						for(JRadioButton rb : radioList){
							rb.addActionListener(new SelectionChangeListener());
						}
					}else{
						ArrayList<JCheckBox> boxList = oPanel.getBoxList();
						for(JCheckBox cb : boxList){
							cb.addActionListener(new SelectionChangeListener());
						}
					}
				}
			}
			etTabs.add(etPanel, etPanel.getElectionType().getTypeTitle());
		}
		
		JTabbedPane ballotQPanel = createBallotQPanel();
		
		for(int i = 0; i < ballotDataList.getBallotQListSize(); i++){
			BallotQ bq = ballotDataList.getBallotQ(i);
			BQuestionPanel bqPanel = new BQuestionPanel(bq);
			ballotQPanel.add(bqPanel, "Question #" + (i + 1));
			bqPanel.getYesNoBox().addActionListener(new SelectionChangeListener());
			bqPanelList.add(bqPanel);
		}
		
		BallotTitle bt = ballotDataList.getBallotTitle();
		
		etTabs.add(ballotQPanel, "Ballot Questions");
		JPanel selectionPanel = createSelectionPanel();
		JPanel titlePanel = createTitlePanel(bt);
		
		
		window.setLayout(new BorderLayout());
		window.add(titlePanel, BorderLayout.NORTH);
		window.add(etTabs, BorderLayout.CENTER);
		window.add(selectionPanel, BorderLayout.WEST);
		
		window.pack();
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		
	}
	
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem item = new JMenuItem("Edit in BallotCreator", new ImageIcon("Icons/Menu/OpenEditorIcon.png"));
		item.setMnemonic(KeyEvent.VK_E);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		item.addActionListener(new MenuBarListener());
		menu.add(item);
		
		item = new JMenuItem("View Results", new ImageIcon("Icons/Menu/ResultsIcon.png"));
		item.setMnemonic(KeyEvent.VK_R);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		item.addActionListener(new MenuBarListener());
		menu.add(item);
		
		menu.addSeparator();
		
		item = new JMenuItem("Close Ballot", new ImageIcon("Icons/Menu/CloseIcon.png"));
		item.setMnemonic(KeyEvent.VK_C);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		item.addActionListener(new MenuBarListener());
		menu.add(item);
		
		menuBar.add(menu);
		
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		
		item = new JMenuItem("About", new ImageIcon("Icons/Menu/AboutIcon.png"));
		item.setMnemonic(KeyEvent.VK_A);
		item.addActionListener(new MenuBarListener());
		menu.add(item);
		menuBar.add(menu);
		
		return menuBar;
	}
	
	
	
	private JPanel createTitlePanel(BallotTitle bt){
		Border etched = BorderFactory.createEtchedBorder();
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.setBorder(etched);
		
		String line1 = "  " + bt.getBallotYear() + " " + bt.getBallotType().getTypeTitle().toUpperCase()
					 + " ELECTION";
		String line2 = "  " + bt.getBallotCity() + ", " + bt.getBallotCounty() + " County, " + bt.getBallotState();
		
		JLabel label = new JLabel(line1);
		label.setFont(BallotFonts.PANEL_TITLE_FONT);
		titlePanel.add(label);
		label = new JLabel(line2);
		label.setFont(BallotFonts.PANEL_TITLE_FONT);
		titlePanel.add(label);
		
		//COLORS
		titlePanel.setBorder(null);
		
		return titlePanel;
	}
	
	private JTabbedPane createBallotQPanel(){
		JTabbedPane ballotQPanel = new JTabbedPane();
		
		String title = "Ballot Questions";
		Border raised = BorderFactory.createRaisedBevelBorder();
	    Border lowered = BorderFactory.createLoweredBevelBorder();
	    Border compound = BorderFactory.createCompoundBorder(raised, lowered);
	    TitledBorder titledBorder = BorderFactory.createTitledBorder(compound, title, TitledBorder.CENTER,
	                                                                 TitledBorder.BELOW_TOP, BallotFonts.PANEL_TITLE_FONT);
	    
	    ballotQPanel.setBorder(titledBorder);
		
		return ballotQPanel;
	}
	
	private JPanel createSelectionPanel(){
		Border etched = BorderFactory.createEtchedBorder();
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new BorderLayout());
		selectionPanel.setBorder(etched);
		
		
		JLabel selectionTitleLabel = new JLabel("Your Selections", JLabel.CENTER);
		selectionTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
		
		voteButton = new JButton("VOTE!!!", new ImageIcon("Icons/Black/VoteIcon.png"));
		voteButton.setMnemonic(KeyEvent.VK_V);
		voteButton.setFont(BallotFonts.BUTTON_FONT);
		voteButton.setHorizontalTextPosition(JButton.CENTER);
		voteButton.setVerticalTextPosition(JButton.BOTTOM);
		voteButton.addActionListener(new VoteButtonListener());
		voteButton.addMouseListener(new ButtonMouseListener());
		
		selectionDisplayPanel = new JPanel();
		selectionDisplayPanel.setLayout(new BoxLayout(selectionDisplayPanel, BoxLayout.Y_AXIS));
		selectionDisplayPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setSelectionText();
		selectionLabel = new JLabel();
		//selectionLabel.setText(setSelectionText());
		selectionDisplayPanel.add(selectionLabel);
		
		JScrollPane scrollPane = new JScrollPane(selectionDisplayPanel);
		//scrollPane.setPreferredSize(new Dimension(200, 300));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		selectionPanel.add(selectionTitleLabel, BorderLayout.NORTH);
		selectionPanel.add(scrollPane, BorderLayout.CENTER);
		selectionPanel.add(voteButton, BorderLayout.SOUTH);
		
		return selectionPanel;
	}
	
	private void setSelectionText(){
		
		for(BOfficePanel op : oPanelList){
			String selection = "<html>";
			
			ElectedOffice eo = op.getOffice();
			int vote4 = eo.getOfficeVoteFor();
			ElectionType et = eo.getOfficeElectionType();
			selection += "<u>" + eo.getOfficeTitle().toUpperCase() + "</u><br>";
			if(vote4 == 1){
				if(et == ElectionType.PRESIDENT_G){
					selection += "None / None<br>";
				}else{
					selection += "None<br>";
				}
			}else{
				for(int i = 0; i < vote4; i++){
					selection += "None<br>";
				}
			}
			
			selection += "<br>";
			
			JPanel panel = createSelectionLabelPanel(selection);
			
			selectionDisplayPanel.add(panel);
		}
		
		int q = 1;
		for(BQuestionPanel bqPanel : bqPanelList){
			String selection = "<html><u>QUESTION #" + q + "</u><br>No Decision<br><br>";
			
			JPanel panel = createSelectionLabelPanel(selection);
			selectionDisplayPanel.add(panel);
			q++;
		}
	}
	
	private JPanel createSelectionLabelPanel(String selection){
		JLabel label = new JLabel(selection);
		label.setFont(BallotFonts.BALLOT_CONTENT_FONT);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		panel.setBackground(null);
		panel.add(label);
		sPanelList.add(panel);
		
		return panel;
	}
	
	private class MenuBarListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(ae.getActionCommand() == "Open in Editor"){
				BallotCreator bc = new BallotCreator(ballotDataList);
				window.dispose();
			}else if(ae.getActionCommand() == "View Results"){
				
			}else if(ae.getActionCommand() == "Close Ballot"){
				closeBallot();
			}else if(ae.getActionCommand() == "About"){
				about();
			}
			
		}
		
		private void closeBallot(){
			closeWindow();
		}
		
		private void closeWindow(){
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}
		
		
		private void about(){
			String message = "Ballot Creator\n\n"
						   + "Version 6.0\n\n"
						   + "(c) Copyright Craig Miller 2015\n"
						   + "All rights reserved\n\n"
						   + "\"Democracy is the worst form of government,\n"
						   + "except for all the alternatives.\"\n--Winston Churchill.";
			
			JOptionPane.showMessageDialog(window, message, "About Ballot Creator", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private class ButtonMouseListener extends MouseAdapter{
    	
    	@Override
		public void mouseEntered(MouseEvent e) {
			JButton button = (JButton) e.getSource();
			ImageIcon icon = (ImageIcon) button.getIcon();
			String iconName = getIconName(icon);
			button.setIcon(new ImageIcon("Icons/Orange/" + iconName + ".png"));
			button.setForeground(Color.ORANGE);
		}
		
		private String getIconName(ImageIcon icon){
			String iconString = icon.toString();
			int slashIndex = iconString.lastIndexOf("/");
			int dotIndex = iconString.lastIndexOf(".");
			String iconName = iconString.substring((slashIndex + 1), dotIndex);
			
			return iconName;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton button = (JButton) e.getSource();
			ImageIcon icon = (ImageIcon) button.getIcon();
			String iconName = getIconName(icon);
			button.setIcon(new ImageIcon("Icons/Black/" + iconName + ".png"));
			button.setForeground(Color.BLACK);
		}
    	
	}
	
	private class BallotWindowListener extends WindowAdapter{
		
		@Override
		public void windowClosing(WindowEvent we) {
			//TODO include a save method for vote data
			ballotDataList.saveFile();
			StartScreen sc = new StartScreen();
			window.dispose();
		}
		
	}

	
	private class SelectionChangeListener implements ActionListener{
		
		private String selection;
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			//selectionDisplayPanel.removeAll();
			
			JLabel label;
			
			int officeIndex = 0;
			for(BOfficePanel op : oPanelList){
				selection = "<html>";
				
				if(op.getOffice().getOfficeVoteFor() == 1){
					ElectionType et = op.getOffice().getOfficeElectionType();
					try{
						singleRadioSelection(op);
					}catch(BallotMissingValuesException ex){
						JOptionPane.showMessageDialog(window, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						ArrayList<JRadioButton> radioList = op.getRadioList();
						radioList.get(0).setSelected(true);
						radioList.get(ex.getBoxIndex()).setSelected(false);
						
						if(et == ElectionType.PRESIDENT_G){
							selection += "None / None<br><br>";
						}else{
							selection += "None<br><br>";
						}
					}
				}else{
					try{
						multiBoxSelection(op);
					}catch(BallotMissingValuesException ex){
						JOptionPane.showMessageDialog(window, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						ArrayList<JCheckBox> boxList = op.getBoxList();
						JCheckBox box = boxList.get(ex.getBoxIndex());
						box.setSelected(false);
						
						int vote4 = op.getOffice().getOfficeVoteFor();
						for(int i = 0; i < vote4; i++){
							selection+= "None<br>";
						}
						selection += "<br>";
					}
				}
				
				label = new JLabel(selection);
				label.setFont(BallotFonts.BALLOT_CONTENT_FONT);
				
				int noneIndex = selection.indexOf("None");
				JPanel panel = sPanelList.get(officeIndex);
				panel.removeAll();
				if(noneIndex < 0){
					
					//panel.setBackground(Color.BLUE);
					
				}else{
					//panel.setBackground(Color.RED);
				}
				panel.add(label);
				
				//selectionDisplayPanel.add(panel);
				officeIndex++;
			}
			
			int bqIndex = 0;
			for(BQuestionPanel bqPanel : bqPanelList){
				selection = "<html>";
				int officeCount = oPanelList.size();
				
				JComboBox<String> yesNoBox = bqPanel.getYesNoBox();
				selection += "<u>" + "QUESTION #" + (bqIndex + 1) + "</u><br>";
				
				String selected = (String) yesNoBox.getSelectedItem();
				if(selected.equals("")){
					selection += "No Decision<br><br>";
				}else if(selected.equalsIgnoreCase("NO")){
					selection += "NO<br><br>";
				}else if(selected.equalsIgnoreCase("YES")){
					selection += "YES<br><br>";
				}
				
				
				JPanel panel = sPanelList.get(officeCount + bqIndex);
				
				label = new JLabel(selection);
				label.setFont(BallotFonts.BALLOT_CONTENT_FONT);
				int noneIndex = selection.indexOf("No Decision");
				panel.removeAll();
				if(noneIndex < 0){
					//panel.setBackground(Color.BLUE);
				}else{
					//panel.setBackground(Color.RED);
				}
				panel.add(label);
				bqIndex++;
			}
			
			//selectionLabel.setText(selection);
			selectionDisplayPanel.revalidate();
			selectionDisplayPanel.repaint();
			window.pack();
		}
		
		private void singleRadioSelection(BOfficePanel op) throws BallotMissingValuesException{
			ElectedOffice eo = op.getOffice();
			ElectionType et = eo.getOfficeElectionType();
			ArrayList<JRadioButton> radioList = op.getRadioList();
			
			int index = 0;
			for(int i = 0; i < radioList.size(); i++){
				if(radioList.get(i).isSelected()){
					index = i;
				}
			}
			
			selection += "<u>" + eo.getOfficeTitle().toUpperCase() + "</u><br>";
			
			int canListSize = eo.getCandidateListSize();
			
			if(index == 0){
				if(et == ElectionType.PRESIDENT_G){
					selection += "None / None<br><br>";
				}else{
					selection += "None<br><br>";
				}
			}else if(index <= canListSize){
				Candidate can = eo.getCandidate(index - 1);
				
				if(et == ElectionType.PRESIDENT_G){
					selection += can.getCandidateName() + " / " + can.getRunningMate() + "<br><br>";
				}else{
					selection += can.getCandidateName() + "<br><br>";
				}
			}else{
				ArrayList<JTextField> textFieldList = op.getTextFieldList();
				int tfIndex = index - canListSize - 1;
				if(et == ElectionType.PRESIDENT_G){
					String text1 = textFieldList.get(0).getText();
					String text2 = textFieldList.get(1).getText();
					
					if((text1.equals("")) || (text2.equals(""))){
						throw new BallotMissingValuesException(index, "You need to fill in your write-in candidate for BOTH President and Vice-President before selecting the option");
					}else if((text1.equalsIgnoreCase("Write-In President")) || (text2.equalsIgnoreCase("Write-In Vice-President"))){
						throw new BallotMissingValuesException(index, "You need to fill in your write-in candidate for BOTH President and Vice-President before selecting the option");
					}else{
						selection += text1 + " / " + text2 + "<br><br>";
					}
				}else{
					String text = textFieldList.get(tfIndex).getText();
					if(text.equals("")){
						throw new BallotMissingValuesException(index, "You need to fill in your write-in candidate before selecting the option");
					}else if(text.equalsIgnoreCase("Write-In Candidate")){
						throw new BallotMissingValuesException(index, "You need to fill in your write-in candidate before selecting the option");
					}else{
						selection += text + "<br><br>";
					}
				}
			}
		}
		
		private void multiBoxSelection(BOfficePanel op) throws BallotMissingValuesException{
			ElectedOffice eo = op.getOffice();
			ArrayList<JCheckBox> boxList = op.getBoxList();
			int vote4 = eo.getOfficeVoteFor();
			int canListSize = eo.getCandidateListSize();
			
			selection += "<u>" + eo.getOfficeTitle().toUpperCase() + "</u><br>";
			
			int index = -1;
			int boxCount = 0;
			for(int i = 0; i < boxList.size(); i++){
				JCheckBox box = boxList.get(i);
				
				if(box.isSelected()){
					index = i;
					if((index >= 0) && (index < canListSize)){
						String name = eo.getCandidate(index).getCandidateName();
						selection += name + "<br>";
						boxCount++;
					}else if(index >= canListSize){
						ArrayList<JTextField> textFieldList = op.getTextFieldList();
						int tfIndex = index - canListSize;
						String text = textFieldList.get(tfIndex).getText();
						if(text.equals("")){
							finalMultiBoxActions(boxCount, vote4, boxList);
							throw new BallotMissingValuesException(index, "You need to in your write-in candidate before selecting the option");
						}else if(text.equalsIgnoreCase("Write-In Candidate")){
							throw new BallotMissingValuesException(index, "You need to fill in your write-in candidate before selecting the option");
						}else{
							selection += text + "<br>";
							boxCount++;
						}
					}
				}
			}
			
			finalMultiBoxActions(boxCount, vote4, boxList);
		}
		
		private void finalMultiBoxActions(int boxCount, int vote4, ArrayList<JCheckBox> boxList){
			if(boxCount < vote4){
				int dif = vote4 - boxCount;
				
				for(int i = 0; i < dif; i++){
					selection += "None<br>";
				}
			}
			
			selection += "<br>";
			
			if(boxCount >= vote4){
				for(JCheckBox box : boxList){
					if(! box.isSelected()){
						box.setEnabled(false);
					}
				}
			}else{
				for(JCheckBox box : boxList){
					if(! box.isSelected()){
						box.setEnabled(true);
					}
				}
			}
		}
		
	}
	
	private class VoteButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			JPanel votePanel = createVotePanel();
			
			int result = JOptionPane.showConfirmDialog(window, votePanel, "Voting!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			if(result == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(window, "Congratulations! Your vote has been cast!", "Voting!", JOptionPane.INFORMATION_MESSAGE);
				window.dispose();
			}
			
		}
		
		private JPanel createVotePanel(){
			JPanel votePanel = new JPanel();
			votePanel.setLayout(new BorderLayout());
			
			JLabel label = new JLabel();
			label.setText("NEED TO FIX THE PATH TO GETTING VOTE DATA");
			JPanel panel = new JPanel();
			JScrollPane scroll = new JScrollPane(panel);
			scroll.setPreferredSize(new Dimension(300,250));
			scroll.getVerticalScrollBar().setUnitIncrement(10);
			
			
			panel.add(label);
			
			label = new JLabel("You are voting for:");
			label.setFont(BallotFonts.PANEL_TITLE_FONT);
			
			votePanel.add(label, BorderLayout.NORTH);
			votePanel.add(scroll, BorderLayout.CENTER);
			
			label = new JLabel("Submit vote?");
			label.setFont(BallotFonts.PANEL_TITLE_FONT);
			votePanel.add(label, BorderLayout.SOUTH);
			
			return votePanel;
		}
		
	}
	
}
