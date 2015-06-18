package ballotcreator;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import data.BallotDataList;
import utilities.BallotFonts;
import exceptions.BallotInvalidInputException;
import exceptions.BallotMissingValuesException;

import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Write a description of class BCOuterPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BCOuterPanel extends JPanel{
	protected static final int MAIN = 0;
    protected static final int OFFICE = 1;
    protected static final int CANDIDATE = 2;
    protected static final int BALLOTQ = 3;
    
    
    /**
     * OUTER PANEL ITMES
     */
    private JPanel outerButtonPanel, innerBlankPanel, innerCardSwitcher;
    private int panelType, numButtons;
    private String button1name, button2name, button3name, addButtonName;
    private JButton[] buttonArray;
    private String[] buttonNameArray;
    private BCInnerOfficePanel innerOfficePanel;
    private BCInnerCandidatePanel innerCandidatePanel;
    private BCInnerBallotQPanel innerBallotQPanel;
    private BCInnerMainPanel innerMainPanel;
    private BallotDataList ballotDataList;
    private JButton runButton;
    
    public BCOuterPanel(final int panelType, BallotDataList ballotDataList){
        super();
        this.ballotDataList = ballotDataList;
        
        outerButtonPanel = new JPanel();
        outerButtonPanel.setLayout(new GridLayout(1, numButtons));
        
        buttonArray = new JButton[3];
        for(int i = 0; i < buttonArray.length; i++){
            JButton button = new JButton();
            button.setFont(BallotFonts.BUTTON_FONT);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonArray[i] = button;
            outerButtonPanel.add(button);
        }
        
        innerBlankPanel = new JPanel();
        innerBlankPanel.setLayout(new BorderLayout());
        
        runButton = new JButton("   Run Ballot", new ImageIcon("Icons/Black/RunIcon.png"));
        runButton.setFont(BallotFonts.BUTTON_FONT);
        
        innerCardSwitcher = new JPanel();
        innerCardSwitcher.setLayout(new CardLayout());
        
        innerCardSwitcher.add(innerBlankPanel, "InnerBlankPanel");
        
        JLabel officeTitleLabel = new JLabel("ELECTED OFFICES", SwingConstants.CENTER);
        officeTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        JLabel mainTitleLabel = new JLabel("MAIN MENU", SwingConstants.CENTER);
        mainTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        JLabel candidateTitleLabel = new JLabel("CANDIDATES", SwingConstants.CENTER);
        candidateTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        JLabel ballotQTitleLabel = new JLabel("BALLOT QUESTIONS", SwingConstants.CENTER);
        ballotQTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        buttonNameArray = new String[3];
        switch(panelType){
            case BCOuterPanel.OFFICE:
                this.panelType = panelType;
                numButtons = 3;
                buttonArray[0].setText("Back to Main Menu");
                buttonArray[0].setMnemonic(KeyEvent.VK_B);
                buttonArray[0].setIcon(new ImageIcon("Icons/Black/BackIcon.png"));
                buttonArray[1].setText("Save Changes");
                buttonArray[1].setMnemonic(KeyEvent.VK_S);
                buttonArray[1].setIcon(new ImageIcon("Icons/Black/SaveIcon.png"));
                buttonArray[2].setText("Add New Office");
                buttonArray[2].setMnemonic(KeyEvent.VK_A);
                buttonArray[2].setIcon(new ImageIcon("Icons/Black/AddIcon.png"));
                innerOfficePanel = new BCInnerOfficePanel();
                innerCandidatePanel = new BCInnerCandidatePanel();
                innerCardSwitcher.add(innerOfficePanel, "InnerOfficePanel");
                innerCardSwitcher.add(innerCandidatePanel, "InnerCandidatePanel");
                break;
            case BCOuterPanel.BALLOTQ:
                this.panelType = panelType;
                numButtons = 2;
                buttonArray[0].setText("Back to Main");
                buttonArray[0].setIcon(new ImageIcon("Icons/Black/BackIcon.png"));
                buttonArray[0].setMnemonic(KeyEvent.VK_B);
                buttonArray[1].setText("Save Changes");
                buttonArray[1].setMnemonic(KeyEvent.VK_S);
                buttonArray[1].setIcon(new ImageIcon("Icons/Black/SaveIcon.png"));
                buttonArray[2].setText("Add New Question");
                buttonArray[2].setMnemonic(KeyEvent.VK_A);
                buttonArray[2].setIcon(new ImageIcon("Icons/Black/AddIcon.png"));
                innerBallotQPanel = new BCInnerBallotQPanel();
                innerCardSwitcher.add(innerBallotQPanel, "InnerBallotQPanel");
                innerBlankPanel.add(ballotQTitleLabel, BorderLayout.NORTH);
                break;
            case BCOuterPanel.MAIN:
            default:
                this.panelType = panelType;
                numButtons = 3;
                buttonArray[0].setText("Save Changes");
                buttonArray[0].setIcon(new ImageIcon("Icons/Black/SaveIcon.png"));
                buttonArray[0].setMnemonic(KeyEvent.VK_S);
                buttonArray[1].setText("Edit Elected Offices");
                buttonArray[1].setIcon(new ImageIcon("Icons/Black/ElectedOfficeIcon.png"));
                buttonArray[1].setMnemonic(KeyEvent.VK_O);
                buttonArray[1].setEnabled(false);
                buttonArray[2].setText("Edit Ballot Questions");
                buttonArray[2].setMnemonic(KeyEvent.VK_Q);
                buttonArray[2].setIcon(new ImageIcon("Icons/Black/BallotQIcon.png"));
                buttonArray[2].setEnabled(false);
                innerBlankPanel.add(mainTitleLabel, BorderLayout.NORTH);
                innerBlankPanel.add(runButton, BorderLayout.SOUTH);
                innerMainPanel = new BCInnerMainPanel();
                innerCardSwitcher.add(innerMainPanel, "InnerMainPanel");
                break;
        }
        
        setLayout(new BorderLayout());
        add(innerCardSwitcher, BorderLayout.CENTER);
        add(outerButtonPanel, BorderLayout.SOUTH);
        
    }
    
    public JButton getRunButton(){
    	return runButton;
    }
    
    public int getPanelType(){
        return panelType;
    }
    
    public JButton[] getButtonArray(){
        return buttonArray;
    }
    
    public JPanel getInnerCardSwitcher(){
        return innerCardSwitcher;
    }
    
    public JPanel getInnerBlankPanel(){
        return innerBlankPanel;
    }
    
    public Object[] save(int type) throws BallotMissingValuesException, BallotInvalidInputException{
    	switch(type){
	        case BCOuterPanel.OFFICE:
	        	return innerOfficePanel.saveOffice();
	        case BCOuterPanel.CANDIDATE:
	        	return innerCandidatePanel.saveCandidate();
	        case BCOuterPanel.BALLOTQ:
	        	return innerBallotQPanel.saveBallotQ();
	        case BCOuterPanel.MAIN:
	        	return innerMainPanel.saveStats();
	    }
    	
    	return null;
    }
    
    public JPanel getInnerPanel(int type){
        switch(type){
            case BCOuterPanel.OFFICE:
                return innerOfficePanel;
            case BCOuterPanel.CANDIDATE:
                return innerCandidatePanel;
            case BCOuterPanel.BALLOTQ:
                return innerBallotQPanel;
            case BCOuterPanel.MAIN:
            	return innerMainPanel;
        }
        
        return null;
    }
    
    public void setFields(int type, Object object){
    	switch(type){
    	case BCOuterPanel.OFFICE:
    		innerOfficePanel.setFields(object);
    		break;
        case BCOuterPanel.CANDIDATE:
        	innerCandidatePanel.setFields(object);
            break;
        case BCOuterPanel.BALLOTQ:
        	innerBallotQPanel.setFields(object);
            break;
        case BCOuterPanel.MAIN:
        	innerMainPanel.setFields(object);
        	break;
    	}
    }
    
    public void clearFields(int type){
    	switch(type){
    	case BCOuterPanel.OFFICE:
    		innerOfficePanel.clearFields();
    		break;
        case BCOuterPanel.CANDIDATE:
        	innerCandidatePanel.clearFields();
        	break;
        case BCOuterPanel.BALLOTQ:
        	innerBallotQPanel.clearFields();
            break;
        case BCOuterPanel.MAIN:
        	innerMainPanel.clearFields();
        	break;
    	}
    }
}
