package ballotcreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import utilities.BallotFonts;

public class BCStartPanel extends JPanel{

	private JButton runButton, editButton;
	private Border etched = BorderFactory.createEtchedBorder();
	private JLabel contentsLabel, statsLabel;
	
	public BCStartPanel(){
		super();
		
		setLayout(new BorderLayout());
		
		JPanel centerPanel = createCenterPanel();
		JPanel eastPanel = createEastPanel();
		eastPanel.setPreferredSize(setEastWestSize());
		JPanel westPanel = createWestPanel();
		westPanel.setPreferredSize(setEastWestSize());
		
		add(centerPanel, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);
		
	}
	
	public JButton getRunButton(){
		return runButton;
	}
	
	public JButton getEditButton(){
		return editButton;
	}
	
	private Dimension setEastWestSize(){
		Dimension preferredSize = null;
		
		JPanel eastPanel = createEastPanel();
		JPanel westPanel = createWestPanel();
		
		Dimension eastSize = eastPanel.getPreferredSize();
		Dimension westSize = westPanel.getPreferredSize();
		
		double eastSizeArea = eastSize.getWidth() * eastSize.getHeight();
		double westSizeArea = westSize.getWidth() * westSize.getHeight();
		
		if(eastSizeArea >= westSizeArea){
			int width = (int) eastSize.getWidth() + 30;
			int height = (int) eastSize.getHeight() + 30;
			
			preferredSize = new Dimension(width, height);
		}else{
			int width = (int) westSize.getWidth() + 30;
			int height = (int) westSize.getHeight() + 30;
			
			preferredSize = new Dimension(width, height);
		}
		
		return preferredSize;
		
	}
	
	
	private JPanel createCenterPanel(){
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JPanel buttonPanel = createCenterButtonPanel();
		JPanel blankPanel = new JPanel();
		
		centerPanel.add(blankPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		return centerPanel;
	}
	
	private JPanel createCenterButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		runButton = new JButton("Run Ballot", new ImageIcon("Icons/RunIcon.png"));
    	runButton.setVerticalTextPosition(JButton.BOTTOM);
    	runButton.setHorizontalTextPosition(JButton.CENTER);
    	runButton.setFont(BallotFonts.BUTTON_FONT);
    	
    	editButton = new JButton("Edit Ballot", new ImageIcon("Icons/EditIcon.png"));
    	editButton.setVerticalTextPosition(JButton.BOTTOM);
    	editButton.setHorizontalTextPosition(JButton.CENTER);
    	editButton.setFont(BallotFonts.BUTTON_FONT);
		
    	buttonPanel.add(runButton);
    	buttonPanel.add(editButton);
		
		return buttonPanel;
	}
	
	private JPanel createEastPanel(){
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setBorder(etched);
		
		JLabel ballotContentsLabel = new JLabel("BALLOT CONTENTS", JLabel.CENTER);
		ballotContentsLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
		
		eastPanel.add(ballotContentsLabel, BorderLayout.NORTH);
		
		return eastPanel;
	}
	
	private JPanel createEastDisplayPanel(){
		JPanel displayPanel = new JPanel();
		
		contentsLabel = new JLabel();
		
		displayPanel.add(contentsLabel);
		
		return displayPanel;
	}
	
	private JPanel createWestPanel(){
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		westPanel.setBorder(etched);
		
		JLabel ballotStatsLabel = new JLabel("BALLOT STATS", JLabel.CENTER);
		ballotStatsLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
		
		westPanel.add(ballotStatsLabel, BorderLayout.NORTH);
		
		return westPanel;
	}
	
	private JScrollPane createWestDisplayPanel(){
		JPanel displayPanel = new JPanel();
		JScrollPane displayScrollPane = new JScrollPane(displayPanel);
		
		statsLabel = new JLabel();
		
		displayPanel.add(statsLabel);
		
		return displayScrollPane;
	}
	
}
