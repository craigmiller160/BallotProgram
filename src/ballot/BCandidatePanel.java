package ballot;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utilities.BallotFonts;
import data.Candidate;

public class BCandidatePanel extends JPanel{

	private Candidate candidate;
	private JRadioButton radioButton;
	private JCheckBox checkBox;
	
	protected BCandidatePanel(Candidate can,  int voteFor, int stripe){
		super();
		this.candidate = can;
		
		setLayout(new GridLayout(1, 3));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		radioButton = new JRadioButton("", false);
		radioButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		checkBox = new JCheckBox("", false);
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		
		String name = candidate.getCandidateName();
		if(can.getRunningMate() != null){
			name += " / " + candidate.getRunningMate();
		}
		
		JLabel partyLabel = new JLabel(candidate.getCandidateParty(), JLabel.CENTER);
		partyLabel.setFont(BallotFonts.BALLOT_CONTENT_FONT);
		JLabel candidateLabel = new JLabel("<html><center>" + name, JLabel.CENTER);
		candidateLabel.setFont(BallotFonts.BALLOT_CONTENT_FONT);
		
		add(partyLabel);
		add(candidateLabel);
		
		if(voteFor == 1){
			add(radioButton);
		}else{
			add(checkBox);
		}
	}
	
	protected JRadioButton getRadioButton(){
		return radioButton;
	}
	
	protected JCheckBox getCheckBox(){
		return checkBox;
	}
	
}
