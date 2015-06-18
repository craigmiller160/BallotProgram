package ballot;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import utilities.BallotFonts;
import data.BallotQ;

public class BQuestionPanel extends JPanel{

	private BallotQ ballotQ;
	private JComboBox<String> yesNoBox;
	
	protected BQuestionPanel(BallotQ bq){
		super();
		this.ballotQ = bq;
		String title = "<html><center>" + ballotQ.getBQName() + "<br>\"" + ballotQ.getBQTitle() + "\"";
		
		Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(loweredEtched, title,
                						 								  TitledBorder.CENTER, TitledBorder.BELOW_TOP,
                						 								  BallotFonts.PANEL_TITLE_FONT);
		
		
		
		JTextArea textArea = new JTextArea("\n\n" + bq.getBQDescription(), 40, 10);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setRows(10);
		textArea.setEditable(false);
		textArea.setBackground(null);
		textArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		
		String[] yesNo = {"", "YES", "NO"};
		yesNoBox = new JComboBox<String>(yesNo);
		yesNoBox.setFont(BallotFonts.PANEL_TITLE_FONT);
		JPanel yesNoPanel = new JPanel();
		yesNoPanel.add(yesNoBox);
		
		setBorder(titledBorder);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//add(label);
		add(textArea);
		add(yesNoPanel);
		
	}
	
	protected BallotQ getBallotQ(){
		return ballotQ;
	}
	
	protected JComboBox<String> getYesNoBox(){
		return yesNoBox;
	}
	
}
