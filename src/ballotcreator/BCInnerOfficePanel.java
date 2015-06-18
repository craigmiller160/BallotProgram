package ballotcreator;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import data.ElectedOffice;
import utilities.BallotFonts;
import utilities.ElectionType;
import exceptions.BallotInvalidInputException;
import exceptions.BallotMissingValuesException;

import java.awt.Insets;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Write a description of class BCOfficeInnerPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BCInnerOfficePanel extends JPanel{
	private GridBagConstraints ogc;
    private JComboBox<String> electionTypeBox, officeTermBox, officeVoteForBox;
    private JTextField officeTitleField, officeLocationField;
    
    public BCInnerOfficePanel(){
        super();
        
        
        String[] numberSelect = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        
        JLabel electionTypeLabel, officeTitleLabel, officeLocationLabel,
               officeVoteForLabel, officeTermLabel, officePanelTitleLabel;
        
        officePanelTitleLabel = new JLabel("<html><center><u>Enter Elected Office Details</u></center></html>", SwingConstants.CENTER);
        officePanelTitleLabel.setFont(BallotFonts.SUB_TITLE_FONT);
        
        electionTypeLabel = new JLabel("Election Type: ", SwingConstants.CENTER);
        officeTitleLabel = new JLabel("Title of Office: ", SwingConstants.CENTER);
        officeLocationLabel = new JLabel("Location of Office: ", SwingConstants.CENTER);
        officeVoteForLabel = new JLabel("Vote For (how many?): ", SwingConstants.CENTER);
        officeTermLabel = new JLabel("Term Length: ", SwingConstants.CENTER);
        
        electionTypeBox = new JComboBox<String>(ElectionType.getETTitleArray());
        electionTypeBox.addActionListener(new ElectionTypeListener());
        officeTermBox = new JComboBox<String>(numberSelect);
        officeVoteForBox = new JComboBox<String>(numberSelect);
        
        officeTitleField = new JTextField(15);
        officeLocationField = new JTextField(15);
        
        
               
        setLayout(new GridBagLayout());
        ogc = new GridBagConstraints();
        
        //FIRST ROW
        ogc.gridx = 0;
        ogc.gridy = 0;
        ogc.gridwidth = 2;
        add(officePanelTitleLabel, ogc);
        //SECOND ROW
        //Election Type Label
        ogc.gridx = 0;
        ogc.gridy = 1;
        ogc.gridwidth = 1;
        ogc.insets = new Insets(10, 10, 10, 10);
        ogc.anchor = GridBagConstraints.EAST;
        add(electionTypeLabel, ogc);
        //Election Type ComboBox
        ogc.gridx = 1;
        ogc.anchor = GridBagConstraints.WEST;
        add(electionTypeBox, ogc);
        //THIRD ROW
        //Office Title Label
        ogc.gridx = 0;
        ogc.gridy = 2;
        ogc.anchor = GridBagConstraints.EAST;
        add(officeTitleLabel, ogc);
        //Office Title Field
        ogc.gridx = 1;
        ogc.anchor = GridBagConstraints.WEST;
        add(officeTitleField, ogc);
        //FOURTH ROW
        //Office Location Label
        ogc.gridx = 0;
        ogc.gridy = 3;
        ogc.anchor = GridBagConstraints.EAST;
        add(officeLocationLabel, ogc);
        //Office Location Field
        ogc.gridx = 1;
        ogc.anchor = GridBagConstraints.WEST;
        add(officeLocationField, ogc);
        //FIFTH ROW
        //Office Term Label
        ogc.gridx = 0;
        ogc.gridy = 4;
        ogc.anchor = GridBagConstraints.EAST;
        add(officeTermLabel, ogc);
        //Office Term Box
        ogc.gridx = 1;
        ogc.anchor = GridBagConstraints.WEST;
        add(officeTermBox, ogc);
        //SIXTH ROW
        //Office Vote For Label
        ogc.gridx = 0;
        ogc.gridy = 5;
        ogc.anchor = GridBagConstraints.EAST;
        add(officeVoteForLabel, ogc);
        //Office Vote For Box
        ogc.gridx = 1;
        ogc.anchor = GridBagConstraints.WEST;
        add(officeVoteForBox, ogc);
    }
    
    public ElectionType getElectionType(){
    	String etString = (String) electionTypeBox.getSelectedItem();
    	
    	ElectionType et = ElectionType.getElectionType(etString);
    	
    	return et;
    }
    
    public void setFields(Object object){
    	ElectedOffice office = (ElectedOffice) object;
    	
    	officeTitleField.setText(office.getOfficeTitle());
    	officeLocationField.setText(office.getOfficeLocation());
    	electionTypeBox.setSelectedItem(office.getOfficeElectionType().getTypeTitle());
    	String termS = "" + office.getOfficeTerm();
    	officeTermBox.setSelectedItem(termS);
    	String vote4S = "" + office.getOfficeVoteFor();
    	officeVoteForBox.setSelectedItem(vote4S);
    }
    
   public void clearFields(){
    	officeTitleField.setText("");
    	officeLocationField.setText("");
    	electionTypeBox.setSelectedIndex(0);
    	officeTermBox.setSelectedIndex(0);
    	officeVoteForBox.setSelectedIndex(0);
   }
   
   public Object[] saveOffice() throws BallotMissingValuesException, BallotInvalidInputException{
	   String etS = (String) electionTypeBox.getSelectedItem();
	   
	   String oTitle = (String) officeTitleField.getText();
	   if(oTitle.equals("")){
		   throw new BallotMissingValuesException("Office Title Field is blank");
	   }else if((oTitle.contains("/")) ||
			   (oTitle.contains("-")) ||
			   (oTitle.contains("*")) ||
			   (oTitle.contains("|"))){
		   
		   throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	   }
	   
	   String oLocation = (String) officeLocationField.getText();
	   if(oLocation.equals("")){
		   throw new BallotMissingValuesException("Office Location Field is blank");
	   }else if((oLocation.contains("/")) ||
			   (oLocation.contains("-")) ||
			   (oLocation.contains("*")) ||
			   (oLocation.contains("|"))){
		   
		   throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	   }
	   
	   int termI = Integer.parseInt((String) officeTermBox.getSelectedItem());
	   int vote4 = Integer.parseInt((String) officeVoteForBox.getSelectedItem());
	   
	   ElectionType eType = null;
	   for(ElectionType e : ElectionType.values()){
	       if(e.getTypeTitle().equals(etS)){
	           eType = e;
	       }
	   }
	    
	   Object[] saveOfficeArray = { (String) oTitle, (String) oLocation, (int) termI, (int) vote4, (ElectionType) eType };
	   
	   return saveOfficeArray;
	   
   }
   
   private class ElectionTypeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			JComboBox<String> box = electionTypeBox;
			String selection = (String) box.getSelectedItem();
			
			ElectionType et = ElectionType.getElectionType(selection);
			if(et == ElectionType.PRESIDENT_G){
				officeTitleField.setText("President & Vice President");
				officeTitleField.setEditable(false);
				officeLocationField.setText("United States of America");
				officeLocationField.setEditable(false);
				officeTermBox.setSelectedIndex(3);
				officeTermBox.setEnabled(false);
				officeVoteForBox.setSelectedIndex(0);
				officeVoteForBox.setEnabled(false);
				
			}else{
				officeTitleField.setEditable(true);
				officeLocationField.setEditable(true);
				officeTermBox.setEnabled(true);
				officeVoteForBox.setEnabled(true);
			}
		}
	   
   }
}
