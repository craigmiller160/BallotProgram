package startscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import ballot.Ballot;
import ballotcreator.BallotCreator;
import data.BallotDataList;
import exceptions.BallotIOException;
import utilities.BallotFonts;
import utilities.Dialogs;
import net.miginfocom.swing.MigLayout;

public class StartScreen {

	private JFrame window;
	private BallotDataList ballotDataList;
	
	public StartScreen(){
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ballotDataList = new BallotDataList();
		
		ImageIcon windowIcon = new ImageIcon("Icons/Black/VoteIcon.png");
		window.setIconImage(windowIcon.getImage());
		
		
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Welcome to BallotPro", JLabel.CENTER);
		label.setFont(BallotFonts.BALLOT_TITLE_FONT);
		panel.add(label);
		panel.setPreferredSize(new Dimension(190, 200));
		contentPane.add(panel, BorderLayout.CENTER);
		
		panel = createButtonPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		window.setJMenuBar(createMenuBar());
		window.setContentPane(contentPane);
		window.setVisible(true);
		window.pack();
		window.setLocationRelativeTo(null);
		
	}
	
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		JMenuItem menuItem = new JMenuItem("Exit Program", new ImageIcon("Icons/Menu/ExitIcon.png"));
		menuItem.addActionListener(new ButtonListener());
		menuItem.setMnemonic(KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		menu.add(menuItem);
		
		menuBar.add(menu);
		
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menuItem = new JMenuItem("About", new ImageIcon("Icons/Menu/AboutIcon.png"));
		menuItem.addActionListener(new ButtonListener());
		menuItem.setMnemonic(KeyEvent.VK_A);
		menu.add(menuItem);
		
		menuBar.add(menu);
		
		return menuBar;
	}
	
	private JPanel createButtonPanel(){
		MigLayout layout = new MigLayout("wrap 3", "[center][center][center]");
		
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		
		//Comp1, 0,0
		JLabel label = new JLabel("BALLOT", JLabel.CENTER);
		label.setFont(BallotFonts.BUTTON_FONT);
		panel.add(label);
		
		//Comp2, 0,1 spanning 2 cells
		label = new JLabel("BALLOT CREATOR");
		label.setFont(BallotFonts.BUTTON_FONT);
		panel.add(label,  "span 2");
		
		//Comp3, 1,0
		JButton button = new JButton("Run Saved Ballot");
		button.setIcon(new ImageIcon("Icons/Black/RunIcon.png"));
		button.addActionListener(new ButtonListener());
		button.setMnemonic(KeyEvent.VK_R);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setFont(BallotFonts.BUTTON_FONT);
		panel.add(button);
		
		//Comp4, 1,1
		button = new JButton("Edit Saved Ballot");
		button.setIcon(new ImageIcon("Icons/Black/EditIcon.png"));
		button.addActionListener(new ButtonListener());
		button.setMnemonic(KeyEvent.VK_E);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setFont(BallotFonts.BUTTON_FONT);
		panel.add(button);
		
		//Comp4, 1,2
		button = new JButton("Create New Ballot");
		button.setIcon(new ImageIcon("Icons/Black/AddIcon.png"));
		button.addActionListener(new ButtonListener());
		button.setMnemonic(KeyEvent.VK_N);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setFont(BallotFonts.BUTTON_FONT);
		panel.add(button);
		
		return panel;
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(ae.getActionCommand() == "Exit Program"){
				System.exit(0);
			}else if(ae.getActionCommand() == "About"){
				about();
			}else if(ae.getActionCommand() == "Run Saved Ballot"){
				try{
					runSavedBallot();
				}catch(BallotIOException ex){
					JOptionPane.showMessageDialog(window, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}else if(ae.getActionCommand() == "Edit Saved Ballot"){
				try{
					editSavedBallot();
				}catch(BallotIOException ex){
					JOptionPane.showMessageDialog(window, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}else if(ae.getActionCommand() == "Create New Ballot"){
				createNewBallot();
			}
			
		}
		
		private void runSavedBallot() throws BallotIOException{
			JFileChooser fc = Dialogs.ballotFileChooser();
			int returnVal = fc.showOpenDialog(window);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File ballotFile = fc.getSelectedFile();
				ballotDataList.loadFile(ballotFile);
				Ballot ballot = new Ballot(ballotDataList);
				window.dispose();
			}
		}
		
		private void editSavedBallot() throws BallotIOException{
			JFileChooser fc = new JFileChooser("Ballots");
			fc.setDialogTitle("Open Saved Ballot");
			fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
			int returnVal = fc.showOpenDialog(window);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File ballotFile = fc.getSelectedFile();
				ballotDataList.loadFile(ballotFile);
				BallotCreator bc = new BallotCreator(ballotDataList);
				window.dispose();
			}
		}
		
		private void createNewBallot(){
			BallotCreator bc = new BallotCreator();
			window.dispose();
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
	
}
