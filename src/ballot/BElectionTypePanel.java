package ballot;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import utilities.BallotFonts;
import utilities.ElectionType;

public class BElectionTypePanel extends JTabbedPane{

	private ElectionType electionType;
	
	protected BElectionTypePanel(ElectionType et){
		super();
		this.electionType = et;
		
		String title = et.getTypeTitle().toUpperCase();
		Border raised = BorderFactory.createRaisedBevelBorder();
	    Border lowered = BorderFactory.createLoweredBevelBorder();
	    Border compound = BorderFactory.createCompoundBorder(raised, lowered);
	    Border empty = BorderFactory.createEmptyBorder();
	    TitledBorder titledBorder = BorderFactory.createTitledBorder(empty, title, TitledBorder.CENTER,
	                                                                 TitledBorder.BELOW_TOP, BallotFonts.PANEL_TITLE_FONT);
	    
	    
	    setBorder(titledBorder);
	}
	
	protected ElectionType getElectionType(){
		return electionType;
	}
	
}
