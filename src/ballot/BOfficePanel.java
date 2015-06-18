package ballot;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import utilities.BallotFonts;
import utilities.ElectionType;
import data.Candidate;
import data.ElectedOffice;

public class BOfficePanel extends JPanel{

	private ElectedOffice office;
	private int voteFor;
	private ArrayList<JRadioButton> canRadioList = new ArrayList<JRadioButton>();
	private ArrayList<JCheckBox> canBoxList = new ArrayList<JCheckBox>();
	private ArrayList<JTextField> textFieldList = new ArrayList<JTextField>();
	private ButtonGroup buttonGroup;
	
	protected BOfficePanel(ElectedOffice eo){
		super();
		this.office = eo;
		this.voteFor = eo.getOfficeVoteFor();
		String title = "<html><center>" + office.getOfficeTitle() + "<br>" + office.getOfficeLocation();
		
		Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(loweredEtched, title,
                						 								  TitledBorder.CENTER, TitledBorder.BELOW_TOP,
                						 								  BallotFonts.PANEL_TITLE_FONT);
		
		setBorder(titledBorder);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		buttonGroup = new ButtonGroup();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JLabel label = new JLabel(eo.getOfficeTerm() + " Year Term", JLabel.CENTER);
		label.setFont(BallotFonts.SUB_TITLE_FONT);
		panel.add(label);
		label = new JLabel("Vote For " + eo.getOfficeVoteFor(), JLabel.CENTER);
		label.setFont(BallotFonts.SUB_TITLE_FONT);
		panel.add(label);
		add(panel);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		label = new JLabel("<html><center><u>PARTY</u></center></html>", JLabel.CENTER);
		label.setFont(BallotFonts.BALLOT_CONTENT_FONT);
		panel.add(label);
		label = new JLabel("<html><center><u>CANDIDATE</u></center></html>", JLabel.CENTER);
		label.setFont(BallotFonts.BALLOT_CONTENT_FONT);
		panel.add(label);
		if(voteFor == 1){
			JRadioButton radioButton = new JRadioButton("", true);
			radioButton.setHorizontalAlignment(SwingConstants.CENTER);
			buttonGroup.add(radioButton);
			canRadioList.add(radioButton);
			panel.add(radioButton);
		}else{
			JCheckBox checkBox = new JCheckBox("", false);
			checkBox.setHorizontalAlignment(SwingConstants.CENTER);
			checkBox.setVisible(false);
			checkBox.setEnabled(false);
			panel.add(checkBox);
		}
		add(panel);
		
		ArrayList<Candidate> canList = eo.getCandidateList();
		int stripe = 0;
		for(Candidate c : canList){
			BCandidatePanel canPanel = new BCandidatePanel(c, voteFor, stripe);
			if(voteFor == 1){
				buttonGroup.add(canPanel.getRadioButton());
				canRadioList.add(canPanel.getRadioButton());
			}else{
				canBoxList.add(canPanel.getCheckBox());
			}
			
			add(canPanel);
			if(stripe == 0){
				stripe = 1;
			}else{
				stripe = 0;
			}
		}
		
		JPanel otherPanel = createOtherPanel(stripe);
		
		add(otherPanel);
	}
	
	private JPanel createOtherPanel(int stripe){
		ElectionType et = office.getOfficeElectionType();
		JPanel otherPanel = new JPanel();
		otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
		
		int i = 0;
		while(i < voteFor){
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3));
			
			
			JLabel label = new JLabel("Other", JLabel.CENTER);
			label.setFont(BallotFonts.BALLOT_CONTENT_FONT);
			
			JTextField textField = new JTextField("Write-In Candidate", 15);
			textField.addFocusListener(new OtherFieldFocusListener());
			textField.setFont(BallotFonts.BALLOT_CONTENT_FONT);
			textFieldList.add(textField);
			JPanel smallPanel;
			if(et == ElectionType.PRESIDENT_G){
				smallPanel = new JPanel();
				//presPanel.setLayout(new BoxLayout(presPanel, BoxLayout.Y_AXIS));
				smallPanel.setLayout(new GridBagLayout());
				GridBagConstraints gc = new GridBagConstraints();
				textField.setText("Write-In President");
				gc.gridx = 0;
				gc.gridy = 0;
				smallPanel.add(textField, gc);
				
				textField = new JTextField(15);
				textField.setFont(BallotFonts.BALLOT_CONTENT_FONT);
				textField.setText("Write-In Vice-President");
				textField.addFocusListener(new OtherFieldFocusListener());
				textFieldList.add(textField);
				gc.gridx = 0;
				gc.gridy = 1;
				smallPanel.add(textField, gc);
				
				panel.add(label);
				panel.add(smallPanel);
			}else{
				smallPanel = new JPanel();
				smallPanel.setLayout(new GridBagLayout());
				smallPanel.add(textField);
				
				panel.add(label);
				panel.add(smallPanel);
			}
			
			JRadioButton radioButton= new JRadioButton("", false);
			JCheckBox checkBox = new JCheckBox("", false);
			
			if(voteFor == 1){
				radioButton.setHorizontalAlignment(SwingConstants.CENTER);
				buttonGroup.add(radioButton);
				canRadioList.add(radioButton);
				panel.add(radioButton);
			}else{
				checkBox.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(checkBox);
				canBoxList.add(checkBox);
			}
			
			otherPanel.add(panel);
			i++;
			stripe++;
		}
		
		if(stripe == 0){
			otherPanel.setBackground(Color.RED);
		}else{
			otherPanel.setBackground(Color.WHITE);
		}
		
		return otherPanel;
	}
	
	protected ElectedOffice getOffice(){
		return office;
	}
	
	protected ArrayList<JTextField> getTextFieldList(){
		return textFieldList;
	}
	
	protected ArrayList<JRadioButton> getRadioList(){
		return canRadioList;
	}
	
	protected ArrayList<JCheckBox> getBoxList(){
		return canBoxList;
	}
	
	private class OtherFieldFocusListener implements FocusListener{
		
		String text = null;
		
		@Override
		public void focusGained(FocusEvent fe) {
			JTextField textField = (JTextField) fe.getSource();
			text = textField.getText();
			textField.setText("");
			
		}

		@Override
		public void focusLost(FocusEvent fe) {
			JTextField textField = (JTextField) fe.getSource();
			if(textField.getText().equals("")){
				textField.setText(text);
			}
		}
		
	}
	
}
