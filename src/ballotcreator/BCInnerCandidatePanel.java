package ballotcreator;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import data.Candidate;
import utilities.BallotFonts;
import exceptions.BallotInvalidInputException;
import exceptions.BallotMissingValuesException;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Insets;

public class BCInnerCandidatePanel extends JPanel{
	
	private JLabel candidatePanelTitleLabel;
    private JLabel candidateInstructionLabel;
    private JLabel candidatePartyLabel, candidateNameLabel, runningMateLabel;
    private JTextField candidatePartyField, candidateNameField, runningMateField;
    private GridBagConstraints cgc;
    
    public BCInnerCandidatePanel(){
        super();
        
        candidatePanelTitleLabel = new JLabel("CANDIDATES FOR: ", SwingConstants.CENTER);
        candidatePanelTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        candidateInstructionLabel = new JLabel("<html><center><u>Enter Candidate Information</u></center></html>");
        candidateInstructionLabel.setFont(BallotFonts.SUB_TITLE_FONT);
        
        candidatePartyLabel = new JLabel("Political Party: ", SwingConstants.CENTER);
        candidateNameLabel = new JLabel("Candidate Name: ", SwingConstants.CENTER);
        runningMateLabel = new JLabel("Running Mate Name: ", JLabel.CENTER);
        runningMateLabel.setVisible(false);
        
        candidatePartyField = new JTextField(15);
        candidateNameField = new JTextField(15);
        runningMateField = new JTextField(15);
        runningMateField.setVisible(false);
        
        setLayout(new GridBagLayout());
        cgc = new GridBagConstraints();
        
        //FIRST ROW
        //Candidate Panel Title Label
        cgc.gridx = 0;
        cgc.gridy = 0;
        cgc.gridwidth = 2;
        cgc.insets = new Insets(10, 10, 10, 10);
        add(candidatePanelTitleLabel, cgc);
        //SECOND ROW
        //Instruction Label
        cgc.gridx = 0;
        cgc.gridy = 1;
        add(candidateInstructionLabel, cgc);
        //THIRD ROW
        //Candidate Party Label
        cgc.gridx = 0;
        cgc.gridy = 2;
        cgc.gridwidth = 1;
        cgc.anchor = GridBagConstraints.EAST;
        add(candidatePartyLabel, cgc);
        //Candidate Party Field
        cgc.gridx = 1;
        cgc.anchor = GridBagConstraints.WEST;
        add(candidatePartyField, cgc);
        //FOURTH ROW
        //Candidate Name Label
        cgc.gridx = 0;
        cgc.gridy = 3;
        cgc.anchor = GridBagConstraints.EAST;
        add(candidateNameLabel, cgc);
        //Candidate Name Field
        cgc.gridx = 1;
        cgc.anchor = GridBagConstraints.WEST;
        add(candidateNameField, cgc);
        //FIFTH ROW
        //Running Mate Name Label
        cgc.gridx = 0;
        cgc.gridy = 4;
        cgc.anchor = GridBagConstraints.EAST;
        add(runningMateLabel, cgc);
        //Running Mate Name Field
        cgc.gridx = 1;
        cgc.anchor = GridBagConstraints.WEST;
        add(runningMateField, cgc);
    }
    
    public void runningMateEnabled(boolean enable){
    	if(enable == true){
    		runningMateLabel.setVisible(true);
    		runningMateField.setVisible(true);
    	}else{
    		runningMateLabel.setVisible(false);
    		runningMateField.setVisible(false);
    	}
    }
    
    public void setFields(Object object){
    	Candidate can = (Candidate) object;
    	
    	candidatePartyField.setText(can.getCandidateParty());
    	candidateNameField.setText(can.getCandidateName());
    	runningMateField.setText(can.getRunningMate());
    }
    
    public void clearFields(){
    	candidatePartyField.setText("");
    	candidateNameField.setText("");
    	runningMateField.setText("");
    }
    
    public Object[] saveCandidate() throws BallotMissingValuesException, BallotInvalidInputException{
    	String party = candidatePartyField.getText();
    	if(party.equals("")){
    		throw new BallotMissingValuesException("Candidate Party Field is blank");
    	}else if((party.contains("/")) ||
				   (party.contains("-")) ||
				   (party.contains("*")) ||
				   (party.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
    	
    	String candidate = candidateNameField.getText();
    	if(candidate.equals("")){
    		throw new BallotMissingValuesException("Candidate Name Field is blank");
    	}else if((candidate.contains("/")) ||
				   (candidate.contains("-")) ||
				   (candidate.contains("*")) ||
				   (candidate.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
    	
    	String mate = runningMateField.getText();
    	if(mate.equals("")){
    		if(runningMateField.isVisible()){
    			throw new BallotMissingValuesException("Running Mate Field is blank");
    		}
    	}else if((mate.contains("/")) ||
				   (mate.contains("-")) ||
				   (mate.contains("*")) ||
				   (mate.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
    	
    	Object[] tempArr = { (String) party, (String) candidate, (String) mate };
    	
    	return tempArr;
    }
	
}
