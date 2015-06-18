package ballotcreator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.BallotDataList;
import startscreen.StartScreen;
import utilities.BallotFonts;
import utilities.BallotType;
import utilities.Dialogs;
import exceptions.BallotFileNotFoundException;
import exceptions.BallotIOException;

/**
 * This class creates the top-level container for the <tt>BallotCreator</tt> part
 * of the <tt>Ballot</tt> program. It attaches a series of <tt>JPanel</tt> classes to build
 * the entire UI.<br><br>
 * 
 * <b>Component Class Hierarchy (from top to bottom):</b><br>
 * BCWindow<br>
 * BCMainEditorPanel<br>
 * BCOuterPanel<br>
 * BCInnerOfficePanel, BCInnerCandidatePanel, BCInnerBallotQPanel
 * 
 * @author Craig Miller
 * @version 6.0
 */
public class BallotCreator {
	/**
	 * FIELDS
	 */
	private static int NEW = 0;
	private static int FILE = 1;
	
	/**
     * MENU ITEMS
     */
    private JMenuBar menuBar, mainMenuBar;
    private JMenu menu, mainMenu;
    private JMenuItem menuItem, newBallotMI, openBallotMI, saveBallotMI, closeBallotMI;
    private JFileChooser fileChooser;
    private ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
    /**
     * WINDOW ITEMS
     */
    private JFrame window;
    private JLabel ballotWelcomeLabel;
    private BCMainEditorPanel mainEditorPanel;
    private BCStartPanel startPanel;
    private JPanel mainCardPanel, blankPanel, contentPane, optionsPanel;
    private JPanel ballotQListPanel, officeListPanel;
    private JButton saveToggleButton;
    /**
     * CREATE BALLOT DIALOG BOX
     */
    private JPanel dialogPanel;
    private JTextField enterBallotNameField, enterBallotLocationField;
    /**
     * DATA
     */
    private BallotDataList ballotDataList;
    
    public BallotCreator(){
        
    	ballotDataList = new BallotDataList();
    	
        window = createWindow(BallotCreator.NEW);
        window.addWindowListener(new BallotWindowListener());
    }
    
    public BallotCreator(BallotDataList bdl){
    	ballotDataList = bdl;
    	
    	window = createWindow(BallotCreator.FILE);
    	window.addWindowListener(new BallotWindowListener());
    	
    	
    }
    
    private JFrame createWindow(int creatorType){
    	/**
         * FILE CHOOSER
         */
        fileChooser = Dialogs.ballotFileChooser();
        
        ImageIcon windowIcon = new ImageIcon("Icons/Black/VoteIcon.png");
        
        JFrame frame = new JFrame("Ballot Creator");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setIconImage(windowIcon.getImage());
        
        JPanel cardPanel = createMainCardPanel(creatorType);
        
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(cardPanel);
        
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        return frame;
    }
    
    private JPanel createMainCardPanel(int creatorType){
    	mainCardPanel = new JPanel();
    	mainCardPanel.setLayout(new CardLayout());
    	
    	ballotWelcomeLabel = new JLabel("WELCOME TO BALLOT CREATOR", SwingConstants.CENTER);
        ballotWelcomeLabel.setFont(BallotFonts.BALLOT_TITLE_FONT);
    	
    	blankPanel = new JPanel();
        blankPanel.setLayout(new BorderLayout());
        blankPanel.add(ballotWelcomeLabel, BorderLayout.CENTER);
        
        mainEditorPanel = new BCMainEditorPanel(ballotDataList);
        mainEditorPanel.getRunButton().addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		window.dispose();
        	}
        });
        
        saveToggleButton = mainEditorPanel.getMainButtonArray()[0];
        saveToggleButton.addActionListener(new MenuBarListener());
        
        mainCardPanel.add(blankPanel, "BlankPanel");
        mainCardPanel.add(mainEditorPanel, "MainEditorPanel");
        
       CardLayout mainCL = (CardLayout) mainCardPanel.getLayout();
    	mainCL.show(mainCardPanel, "MainEditorPanel");
    	BCOuterPanel mainOuterPanel = mainEditorPanel.getOuterPanel(BCOuterPanel.MAIN);
    	CardLayout innerCL = (CardLayout) mainOuterPanel.getInnerCardSwitcher().getLayout();
    	innerCL.show(mainOuterPanel.getInnerCardSwitcher(), "InnerMainPanel");
    	
        if(creatorType == BallotCreator.FILE){
        	mainEditorPanel.setBallotStatFields(ballotDataList.getBallotTitle());
        }
        
    	return mainCardPanel;
    }
    
    
    private JMenuBar createMenuBar(){
    	menuBar = new JMenuBar();
    	
    	menu = new JMenu("File");
    	menu.setMnemonic(KeyEvent.VK_F);
    	
    	menuItem = new JMenuItem("Save Ballot", new ImageIcon("Icons/Menu/SaveIcon.png"));
    	menuItem.setMnemonic(KeyEvent.VK_S);
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    	menuItem.addActionListener(new MenuBarListener());
    	menu.add(menuItem);
    	menuItemList.add(menuItem);
    	
    	menu.addSeparator();
    	
    	menuItem = new JMenuItem("Close Ballot", new ImageIcon("Icons/Menu/CloseIcon.png"));
    	menuItem.setMnemonic(KeyEvent.VK_C);
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
    	menuItem.addActionListener(new MenuBarListener());
    	menu.add(menuItem);
    	menuItemList.add(menuItem);
    	
    	menuBar.add(menu);
    	
    	menu = new JMenu("Help");
    	menu.setMnemonic(KeyEvent.VK_H);
    	
    	menuItem = new JMenuItem("About Ballot Creator", new ImageIcon("Icons/Menu/AboutIcon.png"));
    	menuItem.setMnemonic(KeyEvent.VK_A);
    	menuItem.addActionListener(new MenuBarListener());
    	menu.add(menuItem);
    	menuItemList.add(menuItem);
    	
    	menuBar.add(menu);
        
    	return menuBar;
    }
    
    private class MenuBarListener implements ActionListener{
    	
    	private final int NEW_BALLOT = 0;
    	private final int OPEN_BALLOT = 1;
    	private final int SAVE_BALLOT = 2;
    	private final int CLOSE_BALLOT = 3;
    	private final int EXIT_PROGRAM = 4;
    	private final int ABOUT = 5;
    	
    	private int buttonPressed;
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Save Ballot")){
				buttonPressed = SAVE_BALLOT;
				saveBallot();
			}else if(e.getActionCommand().equals("Close Ballot")){
				buttonPressed = CLOSE_BALLOT;
				closeBallot();
			}else if(e.getActionCommand().equals("About Ballot Creator")){
				buttonPressed = ABOUT;
				about();
			}
		}
		
		private void closeBallot(){
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}
		
		
		
		private void saveBallot(){
			ballotDataList.saveFile();
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
		
		private void showInnerMainPanel(){
			CardLayout mainCL = (CardLayout) mainCardPanel.getLayout();
			CardLayout outerCL = (CardLayout) mainEditorPanel.getOuterCardSwitcher().getLayout();
			CardLayout innerCL = (CardLayout) mainEditorPanel.getInnerMainCardSwitcher().getLayout();
			
			mainCL.show(mainCardPanel, "MainEditorPanel");
			outerCL.show(mainEditorPanel.getOuterCardSwitcher(), "OuterMainPanel");
			innerCL.show(mainEditorPanel.getInnerMainCardSwitcher(), "InnerMainPanel");
			
			setMainButtons();
		}
		
		private void setMainButtons(){
			JButton[] tempArr = mainEditorPanel.getMainButtonArray();
			
			tempArr[0].setEnabled(true);
			tempArr[0].setText("Save Changes");
			tempArr[0].setIcon(new ImageIcon("Icons/Black/SaveIcon.png"));
			tempArr[1].setEnabled(false);
			tempArr[2].setEnabled(false);
		}
    }
    
    private class BallotWindowListener extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent we) {
			
			if((ballotDataList.getOfficeListSize() > 0) || (ballotDataList.getBallotQListSize() > 0) || (ballotDataList.getBallotTitle() != null)){
				int choice = JOptionPane.showConfirmDialog(window, "Do you want to save changes to your Ballot?", "Save Ballot?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if(choice == JOptionPane.YES_OPTION){
					ballotDataList.saveFile();
					clearBallot();
					window.dispose();
				}else if(choice == JOptionPane.NO_OPTION){
					clearBallot();
					window.dispose();
				}else{
					
				}
			}else{
				window.dispose();
			}
			
			StartScreen sc = new StartScreen();
			
		}
		
		private void clearBallot(){
			CardLayout mainCL = (CardLayout) mainCardPanel.getLayout();
			//Clear the data list
			ballotDataList.clearOfficeList();
			ballotDataList.clearBallotQList();
			
			//Clear the selectionDisplayPanel
			mainEditorPanel.clearSelectionDisplayPanel();
			
			//Reset the ballot title label
			mainEditorPanel.resetBallotTitleLabel();
			
			//Clear ballot stats fields
			mainEditorPanel.clearBallotStatFields();
			
			//Change the panel
			mainCL.show(mainCardPanel, "BlankPanel");
		}
    }
}
