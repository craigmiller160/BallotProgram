package ballotcreator;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import data.BallotQ;
import utilities.BallotFonts;
import exceptions.BallotInvalidInputException;
import exceptions.BallotMissingValuesException;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Write a description of class BCInnerBallotQPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BCInnerBallotQPanel extends JPanel{
	
	private JLabel ballotQPanelTitleLabel, ballotQInstructionLabel;
    private GridBagConstraints bqgc;
    private JTextField bqNameField, bqTitleField;
    private JTextArea bqDescriptionTextArea;
    private JScrollPane bqDescriptionScrollPane;
    
    public BCInnerBallotQPanel(){
        super();
        
        JLabel bqNameLabel, bqTitleLabel, bqDescriptionLabel;
        
        ballotQPanelTitleLabel = new JLabel("BALLOT QUESTIONS", SwingConstants.CENTER);
        ballotQPanelTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        ballotQInstructionLabel = new JLabel("<html><center><u>Enter Ballot Question Information</u></center></html>",
                                             SwingConstants.CENTER);
        ballotQInstructionLabel.setFont(BallotFonts.SUB_TITLE_FONT);
        
        bqNameLabel = new JLabel("Ballot Question Name: ", SwingConstants.CENTER);
        bqTitleLabel = new JLabel("Ballot Question Title: ", SwingConstants.CENTER);
        bqDescriptionLabel = new JLabel("Ballot Question Description: ", SwingConstants.CENTER);
        
        bqNameField = new JTextField(20);
        bqTitleField = new JTextField(20);
        
        bqDescriptionTextArea = new JTextArea(5, 40);
        bqDescriptionTextArea.setLineWrap(true);
        bqDescriptionTextArea.setWrapStyleWord(true);
        
        bqDescriptionScrollPane = new JScrollPane(bqDescriptionTextArea);
        
        setLayout(new GridBagLayout());
        bqgc = new GridBagConstraints();
        
        //FIRST ROW
        //BallotQ Panel Title Label
        bqgc.gridx = 0;
        bqgc.gridy = 0;
        bqgc.gridwidth = 2;
        bqgc.insets = new Insets(10, 10, 10, 10);
        add(ballotQPanelTitleLabel, bqgc);
        //SECOND ROW
        //BallotQ Instruction Label
        bqgc.gridx = 0;
        bqgc.gridy = 1;
        add(ballotQInstructionLabel, bqgc);
        //THIRD ROW
        //BQ Name Label
        bqgc.gridx = 0;
        bqgc.gridy = 2;
        bqgc.gridwidth = 1;
        bqgc.anchor = GridBagConstraints.EAST;
        add(bqNameLabel, bqgc);
        //BQ Name Field
        bqgc.gridx = 1;
        bqgc.anchor = GridBagConstraints.WEST;
        add(bqNameField, bqgc);
        //FOURTH ROW
        //BQ Title Label
        bqgc.gridx = 0;
        bqgc.gridy = 3;
        bqgc.anchor = GridBagConstraints.EAST;
        add(bqTitleLabel, bqgc);
        //BQ Title Field
        bqgc.gridx = 1;
        bqgc.anchor = GridBagConstraints.WEST;
        add(bqTitleField, bqgc);
        //FIFTH ROW
        //BQ Description Label
        bqgc.gridx = 0;
        bqgc.gridy = 4;
        //bqgc.anchor = GridBagConstraints.NONE;
        add(bqDescriptionLabel, bqgc);
        //SIXTH ROW
        //BQ Description Field
        bqgc.gridx = 0;
        bqgc.gridy = 5;
        bqgc.gridwidth = 2;
        add(bqDescriptionScrollPane, bqgc);
    }
    
    public void clearFields(){
    	bqNameField.setText("");
    	bqTitleField.setText("");
    	bqDescriptionTextArea.setText("");
    }
    
    public void setFields(Object object){
    	BallotQ bq = (BallotQ) object;
    	
    	bqNameField.setText(bq.getBQName());
    	bqTitleField.setText(bq.getBQTitle());
    	bqDescriptionTextArea.setText(bq.getBQDescription());
    	
    }
    
    public Object[] saveBallotQ() throws BallotMissingValuesException, BallotInvalidInputException{
    	String bqName = bqNameField.getText();
    	if(bqName.equals("")){
    		throw new BallotMissingValuesException("Ballot Question Name Field is blank");
    	}else if((bqName.contains("/")) ||
				   (bqName.contains("-")) ||
				   (bqName.contains("*")) ||
				   (bqName.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
    	
    	String bqTitle = bqTitleField.getText();
    	if(bqTitle.equals("")){
    		throw new BallotMissingValuesException("Ballot Question Title Field is blank");
    	}else if((bqTitle.contains("/")) ||
				   (bqTitle.contains("-")) ||
				   (bqTitle.contains("*")) ||
				   (bqTitle.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
    	
    	String bqDescription = bqDescriptionTextArea.getText();
    	if(bqDescription.equals("")){
    		throw new BallotMissingValuesException("Ballot Question Description Field is blank");
    	}else if((bqDescription.contains("/")) ||
				   (bqDescription.contains("-")) ||
				   (bqDescription.contains("*")) ||
				   (bqDescription.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
    	
    	Object[] tempArr = { (String) bqName, (String) bqTitle, (String) bqDescription };
    	
    	return tempArr;
    }
	
}
