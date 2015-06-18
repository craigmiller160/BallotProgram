package ballotcreator;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import ballot.Ballot;
import data.BallotDataList;
import data.BallotQ;
import data.BallotTitle;
import data.Candidate;
import data.ElectedOffice;
import utilities.BallotFonts;
import utilities.BallotType;
import utilities.ElectionType;
import exceptions.BallotFileNameChangeException;
import exceptions.BallotInputException;
import exceptions.BallotMissingValuesException;
import exceptions.BallotNothingSelectedException;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Write a description of class MainEditorPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BCMainEditorPanel extends JPanel{
	/**
     * MAIN EDITOR ITEMS
     */
    private JPanel outerCardSwitcher, selectionPanel, selectionDisplayPanel,
            selectionButtonPanel, ballotTitlePanel;
    private JScrollPane selectionScrollPane;
    private JButton selectEditButton, selectRemoveButton;
    private JLabel selectionTitleLabel, selectionDisplayLabel, ballotTitleLabel;
    private BCOuterPanel outerOfficePanel, outerMainPanel, outerBallotQPanel, outerCandidatePanel;
    private GridBagConstraints sgc;
    private ButtonGroup officeGroup, candidateGroup, ballotQGroup;
    private JRadioButton[] radioArray;
    /**
     * OTHER ITEMS
     */
    private BallotDataList ballotDataList;
    private int officeIndex, candidateIndex, ballotQIndex;
    
    private ElectedOffice tempOffice;
    
    public BCMainEditorPanel(BallotDataList ballotDataList){
        super();
        this.ballotDataList = ballotDataList;
        
        Border etchedBorder = BorderFactory.createEtchedBorder();
        
        this.officeIndex = 0;
        this.candidateIndex = 0;
        this.ballotQIndex = 0;
        
        /**
         * TITLE PANEL
         */
        ballotTitleLabel = new JLabel("WELCOME!", SwingConstants.LEFT);
        ballotTitleLabel.setFont(BallotFonts.BALLOT_TITLE_FONT);
        
        ballotTitlePanel = new JPanel();
        ballotTitlePanel.setLayout(new BorderLayout());
        ballotTitlePanel.add(ballotTitleLabel, BorderLayout.WEST);
        
        
        /**
         * OUTER CARD SWITCHER
         */
        
        outerCardSwitcher = new JPanel();
        outerCardSwitcher.setLayout(new CardLayout());
        outerCardSwitcher.setBorder(etchedBorder);
        
        outerMainPanel = new BCOuterPanel(BCOuterPanel.MAIN, ballotDataList);
        outerOfficePanel = new BCOuterPanel(BCOuterPanel.OFFICE, ballotDataList);
        outerBallotQPanel = new BCOuterPanel(BCOuterPanel.BALLOTQ, ballotDataList);
        outerCandidatePanel = new BCOuterPanel(BCOuterPanel.CANDIDATE, ballotDataList);
        
        outerCardSwitcher.add(outerMainPanel, "OuterMainPanel");
        outerCardSwitcher.add(outerOfficePanel, "OuterOfficePanel");
        outerCardSwitcher.add(outerBallotQPanel, "OuterBallotQPanel");
        outerCardSwitcher.add(outerCandidatePanel, "OuterCandidatePanel");
        
        /**
         * SELECTION PANEL
         */
        
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new BorderLayout());
        
        selectionButtonPanel = new JPanel();
        selectionButtonPanel.setLayout(new GridLayout(1, 2));
        
        selectionDisplayLabel = new JLabel();
        
        selectionTitleLabel = new JLabel("", SwingConstants.CENTER);
        selectionTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
        
        selectEditButton = new JButton("Edit Selection", new ImageIcon("Icons/Black/EditIcon.png"));
        selectEditButton.setFont(BallotFonts.BUTTON_FONT);
        selectEditButton.setMnemonic(KeyEvent.VK_E);
        selectEditButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        selectEditButton.setHorizontalTextPosition(SwingConstants.CENTER);
        selectEditButton.setPreferredSize(new Dimension(140, 64));
        
        selectRemoveButton = new JButton("Remove Selection", new ImageIcon("Icons/Black/DeleteIcon.png"));
        selectRemoveButton.setFont(BallotFonts.BUTTON_FONT);
        selectRemoveButton.setMnemonic(KeyEvent.VK_R);
        selectRemoveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        selectRemoveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        selectRemoveButton.setPreferredSize(new Dimension(140, 64));
        
        selectEditButton.setEnabled(false);
        selectRemoveButton.setEnabled(false);
        
        selectionButtonPanel.add(selectEditButton);
        selectionButtonPanel.add(selectRemoveButton);
        
        selectionDisplayPanel = new JPanel();
        //selectionDisplayPanel.setLayout(new GridBagLayout());
        selectionDisplayPanel.setLayout(new BoxLayout(selectionDisplayPanel, BoxLayout.Y_AXIS));
        selectionDisplayPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        selectionScrollPane = new JScrollPane(selectionDisplayPanel);
        selectionScrollPane.setPreferredSize(new Dimension(selectionScrollPane.getPreferredSize()));
        selectionScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        
        sgc = new GridBagConstraints();
        
        /**
         * ADDING TONS OF LISTENERS
         */
        for(JButton b : outerMainPanel.getButtonArray()){
            b.addActionListener(new MainPanelListener());
            b.addMouseListener(new ButtonMouseListener());
        }
        for(JButton b : outerOfficePanel.getButtonArray()){
            b.addActionListener(new OfficePanelListener());
            b.addMouseListener(new ButtonMouseListener());
        }
        for(JButton b : outerBallotQPanel.getButtonArray()){
            b.addActionListener(new BallotQPanelListener());
            b.addMouseListener(new ButtonMouseListener());
        }
        selectEditButton.addActionListener(new SelectionPanelListener());
        selectEditButton.addMouseListener(new ButtonMouseListener());
        selectRemoveButton.addActionListener(new SelectionPanelListener());
        selectRemoveButton.addMouseListener(new ButtonMouseListener());
        outerMainPanel.getRunButton().addActionListener(new RunBallotListener());
        outerMainPanel.getRunButton().addMouseListener(new ButtonMouseListener());
        
        
        
        selectionPanel.add(selectionButtonPanel, BorderLayout.SOUTH);
        selectionPanel.add(selectionScrollPane, BorderLayout.CENTER);
        selectionPanel.add(selectionTitleLabel, BorderLayout.NORTH);
        selectionPanel.setBorder(etchedBorder);
        
        /**
         * FINAL MAIN EDITOR STUFF
         */
        
        
        setLayout(new BorderLayout());
        add(ballotTitlePanel, BorderLayout.NORTH);
        add(selectionPanel, BorderLayout.EAST);
        add(outerCardSwitcher, BorderLayout.CENTER);
        setBorder(etchedBorder);
    }
    
    protected JButton getRunButton(){
    	return outerMainPanel.getRunButton();
    }
    
    public void resetBallotTitleLabel(){
    	ballotTitleLabel.setText("WELCOME!");
    }
    
    public void setBallotStatFields(Object object){
    	outerMainPanel.setFields(BCOuterPanel.MAIN, object);
    }
    
    public void clearBallotStatFields(){
    	BCInnerMainPanel imp = (BCInnerMainPanel) outerMainPanel.getInnerPanel(BCOuterPanel.MAIN);
    	imp.clearFields();
    }
    
    protected BCOuterPanel getOuterPanel(int type){
    	switch(type){
    	case BCOuterPanel.MAIN:
    		return outerMainPanel;
    	case BCOuterPanel.OFFICE:
    		return outerOfficePanel;
    	case BCOuterPanel.BALLOTQ:
    		return outerBallotQPanel;
		default:
			return null;
    	}
    }
    
    public BCInnerMainPanel getInnerMainPanel(){
    	return (BCInnerMainPanel) outerMainPanel.getInnerPanel(BCOuterPanel.MAIN);
    }
    
    public JButton[] getMainButtonArray(){
    	return outerMainPanel.getButtonArray();
    }
    
    public JPanel getOuterCardSwitcher(){
    	return outerCardSwitcher;
    }
    
    public JPanel getInnerMainCardSwitcher(){
    	return outerMainPanel.getInnerCardSwitcher();
    }
    
    public void clearSelectionDisplayPanel(){
    	selectionTitleLabel.setText("");
    	selectionDisplayPanel.removeAll();
    	selectionDisplayPanel.revalidate();
    	selectionDisplayPanel.repaint();
    }
    
    protected void clearBallotStatsFields(){
    	outerMainPanel.clearFields(BCOuterPanel.MAIN);
    }
    
    public void displayContents(){
    	clearSelectionDisplayPanel();
    	selectionTitleLabel.setText("BALLOT CONTENTS");
    	JLabel contentsLabel = new JLabel();
    	String contents = "<html>";
    	
    	for(int i = 0; i < ballotDataList.getOfficeListSize(); i++){
            ElectedOffice eo = ballotDataList.getOffice(i);
            int numCandidates = eo.getCandidateListSize();
            
			String officeString = "  " + eo.getOfficeTitle().toUpperCase() + "<br>"
					+ "  " + eo.getOfficeLocation() + "<br>" + "  " + numCandidates + " Candidates";
            					
    		contents += officeString + "<br><br>";
    	}
    	
    	for(int i = 0; i < ballotDataList.getBallotQListSize(); i++){
    		BallotQ bq = ballotDataList.getBallotQ(i);
    		String bqName = "BALLOT QUESTION<br>" + bq.getBQName();
    		
    		contents += bqName + "<br><br>";
    	}
    	
    	contentsLabel.setText(contents);
    	contentsLabel.setHorizontalAlignment(JLabel.CENTER);
    	
    	sgc.gridx = 0;
    	sgc.gridy = 0;
    	sgc.anchor = GridBagConstraints.WEST;
    	selectionDisplayPanel.add(contentsLabel);
    	selectionDisplayPanel.revalidate();
    	selectionDisplayPanel.repaint();
    }
    
    
    public void displaySavedOffices(boolean radio){
    	clearSelectionDisplayPanel();
    	selectionTitleLabel.setText("ELECTED OFFICES");
        int radioButtonCount = 0;
        officeGroup = new ButtonGroup();
        radioArray = new JRadioButton[ballotDataList.getOfficeListSize()];
        JLabel label = new JLabel();
    	String text = "<html>";
        
        for(int i = 0; i < ballotDataList.getOfficeListSize(); i++){
            String officeString = ballotDataList.getOffice(i).officeToLabel();
            
            if(radio){
            	//Create Radio Button
                JRadioButton radioButton = new JRadioButton("<html>" + officeString + "</html>", false);
                radioButton.setHorizontalAlignment(SwingConstants.LEFT);
                radioButton.addMouseListener(new ButtonMouseListener());
                //Add to officeGroup
                officeGroup.add(radioButton);
                
                selectionDisplayPanel.add(radioButton);
                
                //Add radio button to array
                radioArray[i] = radioButton;
                
                //Increment count for y coordinate placement
                radioButtonCount++;
            }else{
            	text += officeString + "<br><br>";
            }
        }
        
        if(! radio){
    		label.setText(text);
    		selectionDisplayPanel.add(label);
    	}
        
        //Repaint selectionDisplayPanel
        selectionDisplayPanel.revalidate();
        selectionDisplayPanel.repaint();
    }
    
    
    
    public void displaySavedCandidates(boolean radio){
    	clearSelectionDisplayPanel();
    	selectionTitleLabel.setText("CANDIDATES");
    	
		int radioButtonCount = 0;
    	//int listSize = ballotDataList.getOffice(officeIndex).getCandidateList().size();
		int listSize = tempOffice.getCandidateList().size();
    	radioArray = new JRadioButton[listSize];
    	candidateGroup = new ButtonGroup();
    	JLabel label = new JLabel();
    	String text = "<html>";
    	
    	for(int i = 0; i < listSize; i++){
    		//String candidateString = ballotDataList.getOffice(officeIndex).getCandidateList().get(i).toLabel();
    		String candidateString = tempOffice.getCandidate(i).toLabel();
    		
    		if(radio){
    			//Create Radio Button
        		JRadioButton radioButton = new JRadioButton("<html>" + candidateString + "</html>", false);
        		radioButton.addMouseListener(new ButtonMouseListener());
        		//Add to candidateGroup
        		candidateGroup.add(radioButton);
        		
                selectionDisplayPanel.add(radioButton);
                
                //Add radio button to array
                radioArray[i] = radioButton;
                
                //Increment count for y coordinate placement
                radioButtonCount++;
    		}else{
    			text += candidateString + "<br><br>";
    		}
    	}
    	
    	if(! radio){
    		label.setText(text);
    		selectionDisplayPanel.add(label);
    	}
    	
    	//Repaint selectionDisplayPanel
        selectionDisplayPanel.revalidate();
        selectionDisplayPanel.repaint();
    }
    
    public void displaySavedBallotQ(boolean radio){
    	clearSelectionDisplayPanel();
    	int radioButtonCount = 0;
    	ballotQGroup = new ButtonGroup();
    	int listSize = ballotDataList.getBallotQListSize();
    	radioArray = new JRadioButton[listSize];
    	JLabel label = new JLabel();
    	String text = "<html>";
    	
    	selectionTitleLabel.setText("BALLOT QUESTIONS");
    	
    	for(int i = 0; i < listSize; i++){
    		String ballotQString = ballotDataList.getBallotQ(i).toString();
    		
    		if(radio){
    			//Create Radio Button
        		JRadioButton radioButton = new JRadioButton("<html>" + ballotQString + "</html>", false);
        		radioButton.addMouseListener(new ButtonMouseListener());
        		//Add to candidateGroup
        		ballotQGroup.add(radioButton);
        		
                selectionDisplayPanel.add(radioButton);
                
                //Add radio button to array
                radioArray[i] = radioButton;
                
                //Increment count for y coordinate placement
                radioButtonCount++;
    		}else{
    			text += ballotQString + "<br><br>";
    		}
    		
    		
    	}
    	
    	if(! radio){
    		label.setText(text);
    		selectionDisplayPanel.add(label);
    	}
    	
    	//Repaint selectionDisplayPanel
        selectionDisplayPanel.revalidate();
        selectionDisplayPanel.repaint();
    }
    
    private class ButtonMouseListener extends MouseAdapter{
    	
    	@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getSource().getClass().equals(javax.swing.JButton.class)){
				JButton button = (JButton) e.getSource();
				ImageIcon icon = (ImageIcon) button.getIcon();
				String iconName = getIconName(icon);
				button.setIcon(new ImageIcon("Icons/Orange/" + iconName + ".png"));
				button.setForeground(Color.ORANGE);
			}else if(e.getSource().getClass().equals(javax.swing.JRadioButton.class)){
				JRadioButton radio = (JRadioButton) e.getSource();
				radio.setForeground(Color.ORANGE);
			}
    		
    		
    		
		}
		
		private String getIconName(ImageIcon icon){
			String iconString = icon.toString();
			int slashIndex = iconString.lastIndexOf("/");
			int dotIndex = iconString.lastIndexOf(".");
			String iconName = iconString.substring((slashIndex + 1), dotIndex);
			
			return iconName;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(e.getSource().getClass().equals(javax.swing.JButton.class)){
				JButton button = (JButton) e.getSource();
				ImageIcon icon = (ImageIcon) button.getIcon();
				String iconName = getIconName(icon);
				button.setIcon(new ImageIcon("Icons/Black/" + iconName + ".png"));
				button.setForeground(Color.BLACK);
			}else if(e.getSource().getClass().equals(javax.swing.JRadioButton.class)){
				JRadioButton radio = (JRadioButton) e.getSource();
				radio.setForeground(Color.BLACK);
			}
			
			
		}
    	
    }
    
    private class RunBallotListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			Ballot ballot = new Ballot(ballotDataList);
			
		}
    	
    }
    
    
    private class SelectionPanelListener implements ActionListener{
        private final int EDIT_OFFICE = 0;
        private final int EDIT_CANDIDATE = 1;
        private final int EDIT_BALLOTQ = 2;
        private final int DELETE_OFFICE = 3;
        private final int DELETE_CANDIDATE = 4;
        private final int DELETE_BALLOTQ = 5;
        
        private int buttonPressed;
    	
        public void actionPerformed(ActionEvent e){
        	if((outerOfficePanel.getInnerBlankPanel().isVisible()) && (!outerBallotQPanel.isVisible())){
        		if(e.getSource() == selectEditButton){
        			buttonPressed = EDIT_OFFICE;
        			try{
        				edit();
            			setSelectionPanelContent();
            			setEnabledButtons();
            			setButtonContent();
            			changeCard();
        			}catch(BallotNothingSelectedException ex){
        				JOptionPane.showMessageDialog(selectionPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}else if(e.getSource() == selectRemoveButton){
        			buttonPressed = DELETE_OFFICE;
        			try{
        				delete();
            			setSelectionPanelContent();
            			setEnabledButtons();
            			setButtonContent();
            			changeCard();
        			}catch(BallotNothingSelectedException ex){
        				JOptionPane.showMessageDialog(selectionPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}
        	}else if(outerOfficePanel.getInnerPanel(BCOuterPanel.OFFICE).isVisible()){
        		if(e.getSource() == selectEditButton){
        			buttonPressed = EDIT_CANDIDATE;
        			try{
        				edit();
            			setSelectionPanelContent();
            			setEnabledButtons();
            			setButtonContent();
            			changeCard();
        			}catch(BallotNothingSelectedException ex){
        				JOptionPane.showMessageDialog(selectionPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}else if(e.getSource() == selectRemoveButton){
        			buttonPressed = DELETE_CANDIDATE;
        			try{
        				delete();
            			setSelectionPanelContent();
            			setEnabledButtons();
            			setButtonContent();
            			changeCard();
        			}catch(BallotNothingSelectedException ex){
        				JOptionPane.showMessageDialog(selectionPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}
        	}else if(outerBallotQPanel.getInnerBlankPanel().isVisible()){
        		if(e.getSource() == selectEditButton){
        			buttonPressed = EDIT_BALLOTQ;
        			try{
        				edit();
            			setSelectionPanelContent();
            			setEnabledButtons();
            			setButtonContent();
            			changeCard();
        			}catch(BallotNothingSelectedException ex){
        				JOptionPane.showMessageDialog(selectionPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}else if(e.getSource() == selectRemoveButton){
        			buttonPressed = DELETE_BALLOTQ;
        			try{
    					delete();
            			setSelectionPanelContent();
            			setEnabledButtons();
            			setButtonContent();
            			changeCard();
        			}catch(BallotNothingSelectedException ex){
        				JOptionPane.showMessageDialog(selectionPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}
        	}
        }
    	
    	private void changeCard(){
			CardLayout officeInnerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
	        CardLayout candidateInnerCL = (CardLayout) outerCandidatePanel.getInnerCardSwitcher().getLayout();
	        CardLayout ballotQInnerCL = (CardLayout) outerBallotQPanel.getInnerCardSwitcher().getLayout();
	        
	        switch(buttonPressed){
	        	case EDIT_OFFICE:
	        		officeInnerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerOfficePanel");
	        		break;
	        	case EDIT_CANDIDATE:
	        		officeInnerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerCandidatePanel");
	        		break;
	        	case EDIT_BALLOTQ:
	        		ballotQInnerCL.show(outerBallotQPanel.getInnerCardSwitcher(), "InnerBallotQPanel");
	        		break;
	        	case DELETE_OFFICE:
	        		break;
	        	case DELETE_CANDIDATE:
	        		break;
	        	case DELETE_BALLOTQ:
	        		break;
        		default:
        			break;
	        }
    	}
    	
    	private void setEnabledButtons(){
    		switch(buttonPressed){
	        	case EDIT_OFFICE:
	        		outerOfficePanel.getButtonArray()[0].setEnabled(true);
	        		outerOfficePanel.getButtonArray()[1].setEnabled(true);
	        		outerOfficePanel.getButtonArray()[2].setEnabled(true);
	        		selectEditButton.setEnabled(true);
	        		selectRemoveButton.setEnabled(true);
	        		break;
	        	case EDIT_CANDIDATE:
	        		outerCandidatePanel.getButtonArray()[0].setEnabled(true);
	        		outerCandidatePanel.getButtonArray()[1].setEnabled(true);
	        		outerCandidatePanel.getButtonArray()[2].setEnabled(false);
	        		selectEditButton.setEnabled(false);
	        		selectRemoveButton.setEnabled(false);
	        		break;
	        	case EDIT_BALLOTQ:
	        		outerBallotQPanel.getButtonArray()[0].setEnabled(true);
	        		outerBallotQPanel.getButtonArray()[1].setEnabled(true);
	        		outerBallotQPanel.getButtonArray()[2].setEnabled(false);
	        		selectEditButton.setEnabled(false);
	        		selectRemoveButton.setEnabled(false);
	        		break;
	        	case DELETE_OFFICE:
	        		break;
	        	case DELETE_CANDIDATE:
	        		break;
	        	case DELETE_BALLOTQ:
	        		break;
	    		default:
	    			break;
    		}
    	}
    	
    	private void setButtonContent(){
    		switch(buttonPressed){
	        	case EDIT_OFFICE:
	        		outerOfficePanel.getButtonArray()[0].setText("Discard Changes");
	        		outerOfficePanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/CancelIcon.png"));
	        		outerOfficePanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_D);
	        		outerOfficePanel.getButtonArray()[2].setText("Add New Candidate");
	        		outerOfficePanel.getButtonArray()[2].setIcon(new ImageIcon("Icons/Black/AddCandidate.png"));
	        		selectEditButton.setText("Edit Candidate");
	        		selectRemoveButton.setText("Remove Candidate");
	        		break;
	        	case EDIT_CANDIDATE:
	        		outerCandidatePanel.getButtonArray()[0].setText("Discard Changes");
	        		outerCandidatePanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/CancelIcon.png"));
	        		break;
	        	case EDIT_BALLOTQ:
	        		outerBallotQPanel.getButtonArray()[0].setText("Discard Changes");
	        		outerBallotQPanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_D);
	        		outerBallotQPanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/CancelIcon.png"));
	        		break;
	        	case DELETE_OFFICE:
	        		break;
	        	case DELETE_CANDIDATE:
	        		break;
	        	case DELETE_BALLOTQ:
	        		break;
	    		default:
	    			break;
			}
    	}
    	
    	private void edit() throws BallotNothingSelectedException{
    		String name = "";
    		boolean found = false;
    		
    		int radioIndex = 0;
    		int i = 0;
    		for(JRadioButton rb : radioArray){
    			if(rb.isSelected()){
    				radioIndex = i;
    				found = true;
    			}else{
    				i++;
    			}
    		}
    		
    		if(found){
    			switch(buttonPressed){
    			case EDIT_OFFICE:
    				editOffice(radioIndex);
    				break;
    			case EDIT_CANDIDATE:
    				editCandidate(radioIndex);
    				break;
    			case EDIT_BALLOTQ:
    				editBallotQ(radioIndex);
    				break;
    			}
    		}else{
    			
    			throw new BallotNothingSelectedException("You must make a selection to edit.");
    		}
    	}
    	
    	private void editOffice(int index){
    		officeIndex = index;
    		ElectedOffice eo = ballotDataList.getOffice(index);
    		
    		tempOffice = ElectedOffice.copyOf(eo);
    		
    		//Set the fields in the office wizard
    		outerOfficePanel.setFields(BCOuterPanel.OFFICE, tempOffice);
    	}
    	
    	private void editCandidate(int index){
    		candidateIndex = index;
    		Candidate can = tempOffice.getCandidate(index);
    		
    		BCInnerCandidatePanel canPanel = (BCInnerCandidatePanel) outerOfficePanel.getInnerPanel(BCOuterPanel.CANDIDATE);
        	BCInnerOfficePanel officePanel = (BCInnerOfficePanel) outerOfficePanel.getInnerPanel(BCOuterPanel.OFFICE);
        	
        	if(officePanel.getElectionType() == ElectionType.PRESIDENT_G){
        		canPanel.runningMateEnabled(true);
        	}else{
        		canPanel.runningMateEnabled(false);
        	}
    		
    		//Set the fields in the candidate wizard
    		outerOfficePanel.setFields(BCOuterPanel.CANDIDATE, can);
    	}
    	
    	
    	private void editBallotQ(int index){
    		BallotQ bq = ballotDataList.getBallotQ(index);
    		ballotQIndex = index;
    		//Set the fields in the ballotQ wizard
    		outerBallotQPanel.setFields(BCOuterPanel.BALLOTQ, bq);
    	}
    	
    	
    	private void delete() throws BallotNothingSelectedException{
    		String name = "";
    		boolean found = false;
    		int radioIndex = 0;
    		int i = 0;
    		
    		for(JRadioButton rb : radioArray){
    			if(rb.isSelected()){
    				radioIndex = i;
    				found = true;
    			}else{
    				i++;
    			}
    		}
    		
    		if(found){
    			//Match selected radio with saved entry & remove the entry
        		switch(buttonPressed){
        		case DELETE_OFFICE:
        			ballotDataList.removeOffice(radioIndex);
        			break;
        		case DELETE_CANDIDATE:
        			tempOffice.removeCandidate(radioIndex);
        			break;
        		case DELETE_BALLOTQ:
        			ballotDataList.removeBallotQ(radioIndex);
        			break;
        		}
    		}else{
    			String exceptionName = "";
    			
    			switch(buttonPressed){
    			case DELETE_OFFICE:
    				exceptionName = "office";
    				break;
				case DELETE_CANDIDATE:
					exceptionName = "candidate";
					break;
				case DELETE_BALLOTQ:
					exceptionName = "ballot question";
					break;
    			}
    			
    			throw new BallotNothingSelectedException("You must make a selection to delete");
    		}
    		
    		
    	}
    	
    	private void setSelectionPanelContent(){
    		switch(buttonPressed){
	        	case EDIT_OFFICE:
	        		displaySavedCandidates(true);
	        		break;
	        	case EDIT_CANDIDATE:
	        		displaySavedCandidates(false);
	        		break;
	        	case EDIT_BALLOTQ:
	        		displaySavedBallotQ(false);
	        		break;
	        	case DELETE_OFFICE:
	        		displaySavedOffices(true);
	        		break;
	        	case DELETE_CANDIDATE:
	        		displaySavedCandidates(true);
	        		break;
	        	case DELETE_BALLOTQ:
	        		displaySavedBallotQ(true);
	        		break;
	    		default:
	    			break;
			}
    	}
    }
    
    private class MainPanelListener implements ActionListener{
        private final int BALLOT_STATS = 0;
        private final int ELECTED_OFFICES = 1;
        private final int BALLOT_QUESTIONS = 2;
        private final int SAVE = 3;
    	
        private int buttonPressed;
    	
    	
    	public void actionPerformed(ActionEvent e){
        	if(e.getSource() == outerMainPanel.getButtonArray()[0]){
        		if(outerMainPanel.getInnerBlankPanel().isVisible()){
        			//Ballot Stats
        			buttonPressed = BALLOT_STATS;
        			fillStatsFields();
        			setEnabledButtons();
        			setButtonContent();
            		setSelectionPanelContent();
            		changeCard();
        		}else if(outerMainPanel.getInnerPanel(BCOuterPanel.MAIN).isVisible()){
        			//Save Changes
        			buttonPressed = SAVE;
        			try{
        				save();
            			setEnabledButtons();
            			setButtonContent();
            			setSelectionPanelContent();
            			changeCard();
        			}catch(BallotInputException ex){
        				JOptionPane.showMessageDialog(outerMainPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}catch(BallotFileNameChangeException ex){
        				JOptionPane.showMessageDialog(outerMainPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        			}
        		}
        	}else if(e.getSource() == outerMainPanel.getButtonArray()[1]){
        		//Elected Offices
        		buttonPressed = ELECTED_OFFICES;
        		setEnabledButtons();
        		setButtonContent();
        		setSelectionPanelContent();
        		changeCard();
        	}else if(e.getSource() == outerMainPanel.getButtonArray()[2]){
        		//Ballot Questions
        		buttonPressed = BALLOT_QUESTIONS;
        		setEnabledButtons();
        		setButtonContent();
        		setSelectionPanelContent();
        		changeCard();
        	}
        }
    	
    	private void fillStatsFields(){
    		BallotTitle bt = ballotDataList.getBallotTitle();
    		outerMainPanel.setFields(BCOuterPanel.MAIN, bt);
    	}
    	
    	private void save() throws BallotInputException, BallotFileNameChangeException{
    		BallotTitle bt = new BallotTitle();
    		Object[] tempArr = outerMainPanel.save(BCOuterPanel.MAIN);
    		
    		bt.setBallotYear((int) tempArr[0]);
    		bt.setBallotType((BallotType) tempArr[1]);
    		bt.setBallotState((String) tempArr[2]);
    		bt.setBallotCounty((String) tempArr[3]);
    		bt.setBallotCity((String) tempArr[4]);
    		
    		ballotTitleLabel.setText(bt.toString());
    		
    		outerMainPanel.clearFields(BCOuterPanel.MAIN);
    		
    		ballotDataList.setBallotTitle(bt);
    	}
    	
    	private void changeCard(){
    		CardLayout outerCL = (CardLayout) outerCardSwitcher.getLayout();
    		CardLayout innerCL = (CardLayout) outerMainPanel.getInnerCardSwitcher().getLayout();
    		
    		switch(buttonPressed){
    		case BALLOT_STATS:
    			innerCL.show(outerMainPanel.getInnerCardSwitcher(), "InnerMainPanel");
    			break;
    		case ELECTED_OFFICES:
    			outerCL.show(outerCardSwitcher, "OuterOfficePanel");
    			break;
    		case BALLOT_QUESTIONS:
    			outerCL.show(outerCardSwitcher, "OuterBallotQPanel");
    			break;
    		case SAVE:
    			innerCL.show(outerMainPanel.getInnerCardSwitcher(), "InnerBlankPanel");
    		}
    	}
    	
    	private void setEnabledButtons(){
    		switch(buttonPressed){
    		case BALLOT_STATS:
    			selectEditButton.setEnabled(false);
    			selectRemoveButton.setEnabled(false);
	            outerMainPanel.getButtonArray()[0].setEnabled(true);
	            outerMainPanel.getButtonArray()[1].setEnabled(false);
	            outerMainPanel.getButtonArray()[2].setEnabled(false);
    			break;
    		case ELECTED_OFFICES:
    			if(ballotDataList.getOfficeListSize() > 0){
    				selectEditButton.setEnabled(true);
        			selectRemoveButton.setEnabled(true);
    			}else{
    				selectEditButton.setEnabled(false);
        			selectRemoveButton.setEnabled(false);
    			}
	            outerOfficePanel.getButtonArray()[0].setEnabled(true);
	            outerOfficePanel.getButtonArray()[1].setEnabled(false);
	            outerOfficePanel.getButtonArray()[2].setEnabled(true);
    			break;
    		case BALLOT_QUESTIONS:
    			if(ballotDataList.getOfficeListSize() > 0){
    				selectEditButton.setEnabled(true);
        			selectRemoveButton.setEnabled(true);
    			}else{
    				selectEditButton.setEnabled(false);
        			selectRemoveButton.setEnabled(false);
    			}
	            outerBallotQPanel.getButtonArray()[0].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[1].setEnabled(false);
	            outerBallotQPanel.getButtonArray()[2].setEnabled(true);
    			break;
    		case SAVE:
    			selectEditButton.setEnabled(false);
    			selectRemoveButton.setEnabled(false);
	            outerMainPanel.getButtonArray()[0].setEnabled(true);
	            outerMainPanel.getButtonArray()[1].setEnabled(true);
	            outerMainPanel.getButtonArray()[2].setEnabled(true);
    			break;
    		}
    	}
    	
    	private void setButtonContent(){
    		switch(buttonPressed){
    		case BALLOT_STATS:
    			outerMainPanel.getButtonArray()[0].setText("Save Changes");
    			outerMainPanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/SaveIcon.png"));
    			break;
    		case SAVE:
    			outerMainPanel.getButtonArray()[0].setText("Edit Ballot Stats");
    			outerMainPanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/BallotIcon.png"));
    			break;
    		case ELECTED_OFFICES:
    			selectEditButton.setText("Edit Office");
    			selectRemoveButton.setText("Remove Office");
    			break;
    		case BALLOT_QUESTIONS:
    			selectEditButton.setText("Edit Question");
    			selectRemoveButton.setText("Remove Question");
    			break;
    		}
    	}
    	
    	private void setSelectionPanelContent(){
    		switch(buttonPressed){
    		case BALLOT_STATS:
    			selectionTitleLabel.setText("");
    			clearSelectionDisplayPanel();
    			break;
    		case ELECTED_OFFICES:
    			displaySavedOffices(true);
    			break;
    		case BALLOT_QUESTIONS:
    			displaySavedBallotQ(true);
    			break;
    		case SAVE:
    			displayContents();
    			break;
    		}
    	}
    	
    }
    
    private class OfficePanelListener implements ActionListener{
        private final int MAIN_MENU = 0;
        private final int OFFICE_DISCARD_CHANGES = 1;
        private final int OFFICE_SAVE_CHANGES = 2;
        private final int ADD_OFFICE = 3;
        private final int ADD_CANDIDATE = 4;
        private final int CANDIDATE_DISCARD_CHANGES = 5;
        private final int CANDIDATE_SAVE_CHANGES = 6;
        
        private int buttonPressed;
    	
        public void actionPerformed(ActionEvent e){
        	if(outerOfficePanel.getInnerBlankPanel().isVisible()){
        		if(e.getSource() == outerOfficePanel.getButtonArray()[0]){
        			buttonPressed = MAIN_MENU;
        			returnToMainMenu();
        		}else if(e.getSource() == outerOfficePanel.getButtonArray()[1]){
        			
        		}else if(e.getSource() == outerOfficePanel.getButtonArray()[2]){
        			buttonPressed = ADD_OFFICE;
        			addOffice();
        		}
        	}else if(outerOfficePanel.getInnerPanel(BCOuterPanel.OFFICE).isVisible()){
        		if(e.getSource() == outerOfficePanel.getButtonArray()[0]){
        			buttonPressed = OFFICE_DISCARD_CHANGES;
        			officeDiscardChanges();
        		}else if(e.getSource() == outerOfficePanel.getButtonArray()[1]){
        			buttonPressed = OFFICE_SAVE_CHANGES;
        			officeSaveChanges();
        		}else if(e.getSource() == outerOfficePanel.getButtonArray()[2]){
        			buttonPressed = ADD_CANDIDATE;
        			addCandidate();
        		}
        	}else if(outerOfficePanel.getInnerPanel(BCOuterPanel.CANDIDATE).isVisible()){
        		if(e.getSource() == outerOfficePanel.getButtonArray()[0]){
        			buttonPressed = CANDIDATE_DISCARD_CHANGES;
        			candidateDiscardChanges();
        		}else if(e.getSource() == outerOfficePanel.getButtonArray()[1]){
        			buttonPressed = CANDIDATE_SAVE_CHANGES;
        			candidateSaveChanges();
        		}else if(e.getSource() == outerOfficePanel.getButtonArray()[2]){
        			
        		}
        	}
        }
        
        private void returnToMainMenu(){
        	CardLayout outerCL = (CardLayout) outerCardSwitcher.getLayout();
        	outerCL.show(outerCardSwitcher, "OuterMainPanel");
        	
        	selectEditButton.setEnabled(false);
        	selectRemoveButton.setEnabled(false);
			outerOfficePanel.getButtonArray()[0].setEnabled(true);
			outerOfficePanel.getButtonArray()[1].setEnabled(true);
			outerOfficePanel.getButtonArray()[2].setEnabled(true);
			
			selectEditButton.setText("Edit Selection");
			selectRemoveButton.setText("Remove Selection");
            
            displayContents();
            selectionTitleLabel.setText("BALLOT CONTENTS");
            
        }
        
        private void addOffice(){
        	officeIndex = -1;
        	outerOfficePanel.clearFields(BCOuterPanel.OFFICE);
        	tempOffice = new ElectedOffice();
        	
        	CardLayout innerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
        	innerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerOfficePanel");
        	
        	selectEditButton.setEnabled(false);
        	selectRemoveButton.setEnabled(false);
	        outerOfficePanel.getButtonArray()[0].setEnabled(true);
	        outerOfficePanel.getButtonArray()[1].setEnabled(true);
	        outerOfficePanel.getButtonArray()[2].setEnabled(true);
	        
	        outerOfficePanel.getButtonArray()[0].setText("Discard Changes");
	        outerOfficePanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/CancelIcon.png"));
	        outerOfficePanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_D);
	        outerOfficePanel.getButtonArray()[2].setText("Add New Candidate");
	        outerOfficePanel.getButtonArray()[2].setIcon(new ImageIcon("Icons/Black/AddCandidate.png"));
	        selectEditButton.setText("Edit Candidate");
	        selectRemoveButton.setText("Remove Candidate");
	        
	        displaySavedCandidates(true);
        }
        
        private void officeDiscardChanges(){
        	outerOfficePanel.clearFields(BCOuterPanel.OFFICE);
        	
        	CardLayout innerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
        	innerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerBlankPanel");
        	
        	selectEditButton.setEnabled(true);
        	selectRemoveButton.setEnabled(true);
			outerOfficePanel.getButtonArray()[0].setEnabled(true);
			outerOfficePanel.getButtonArray()[1].setEnabled(false);
			outerOfficePanel.getButtonArray()[2].setEnabled(true);
			
			outerOfficePanel.getButtonArray()[0].setText("Back to Main Menu");
	        outerOfficePanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/BackIcon.png"));
	        outerOfficePanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_B);
	        outerOfficePanel.getButtonArray()[2].setText("Add New Office");
	        outerOfficePanel.getButtonArray()[2].setIcon(new ImageIcon("Icons/Black/AddIcon.png"));
	        selectEditButton.setText("Edit Office");
	        selectRemoveButton.setText("Remove Office");
	        
	        displaySavedOffices(true);
        }
        
        private void officeSaveChanges(){
        	try{
        		saveOffice();
            	
            	selectEditButton.setEnabled(true);
            	selectRemoveButton.setEnabled(true);
        		outerOfficePanel.getButtonArray()[0].setEnabled(true);
        		outerOfficePanel.getButtonArray()[1].setEnabled(false);
        		outerOfficePanel.getButtonArray()[2].setEnabled(true);
        		
        		outerOfficePanel.getButtonArray()[0].setText("Back to Main Menu");
	            outerOfficePanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/BackIcon.png"));
	            outerOfficePanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_B);
	            outerOfficePanel.getButtonArray()[2].setText("Add New Office");
	            outerOfficePanel.getButtonArray()[2].setIcon(new ImageIcon("Icons/Black/AddIcon.png"));
	            selectEditButton.setText("Edit Office");
	            selectRemoveButton.setText("Remove Office");
	            
	            displaySavedOffices(true);
	            
	            CardLayout innerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
            	innerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerBlankPanel");
        	}catch(BallotInputException ex){
        		JOptionPane.showMessageDialog(outerOfficePanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        	}
        }
        
        private void addCandidate(){
        	candidateIndex = -1;
    		outerOfficePanel.clearFields(BCOuterPanel.CANDIDATE);
        	
        	CardLayout innerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
        	innerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerCandidatePanel");
        	BCInnerCandidatePanel canPanel = (BCInnerCandidatePanel) outerOfficePanel.getInnerPanel(BCOuterPanel.CANDIDATE);
        	BCInnerOfficePanel officePanel = (BCInnerOfficePanel) outerOfficePanel.getInnerPanel(BCOuterPanel.OFFICE);
        	
        	if(officePanel.getElectionType() == ElectionType.PRESIDENT_G){
        		canPanel.runningMateEnabled(true);
        	}else{
        		canPanel.runningMateEnabled(false);
        	}
        	
        	selectEditButton.setEnabled(false);
        	selectRemoveButton.setEnabled(false);
    		outerOfficePanel.getButtonArray()[0].setEnabled(true);
    		outerOfficePanel.getButtonArray()[1].setEnabled(true);
    		outerOfficePanel.getButtonArray()[2].setEnabled(false);
    		
    		displaySavedCandidates(false);
        }
        
        private void candidateSaveChanges(){
        	try{
        		saveCandidate();
        		
        		CardLayout innerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
            	innerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerOfficePanel");
            	
            	selectEditButton.setEnabled(true);
            	selectRemoveButton.setEnabled(true);
        		outerOfficePanel.getButtonArray()[0].setEnabled(true);
        		outerOfficePanel.getButtonArray()[1].setEnabled(true);
        		outerOfficePanel.getButtonArray()[2].setEnabled(true);
            	
            	displaySavedCandidates(true);
        	}catch(BallotInputException ex){
        		JOptionPane.showMessageDialog(outerOfficePanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        	}
        }
        
        private void candidateDiscardChanges(){
        	outerOfficePanel.clearFields(BCOuterPanel.CANDIDATE);
        	
        	CardLayout innerCL = (CardLayout) outerOfficePanel.getInnerCardSwitcher().getLayout();
        	innerCL.show(outerOfficePanel.getInnerCardSwitcher(), "InnerOfficePanel");
        	
        	selectEditButton.setEnabled(true);
        	selectRemoveButton.setEnabled(true);
    		outerOfficePanel.getButtonArray()[0].setEnabled(true);
    		outerOfficePanel.getButtonArray()[1].setEnabled(true);
    		outerOfficePanel.getButtonArray()[2].setEnabled(true);
    		
    		displaySavedCandidates(true);
        }
        
        private void saveOffice() throws BallotInputException{
        	Object[] tempArr = outerOfficePanel.save(BCOuterPanel.OFFICE);
        	
        	tempOffice.setOfficeTitle((String) tempArr[0]);
        	tempOffice.setOfficeLocation((String) tempArr[1]);
        	tempOffice.setOfficeTerm((int) tempArr[2]);
        	tempOffice.setOfficeVoteFor((int) tempArr[3]);
        	tempOffice.setOfficeElectionType((ElectionType) tempArr[4]);
        	
        	if(officeIndex >= 0){
        		ballotDataList.setOffice(officeIndex, tempOffice);
        	}else{
        		ballotDataList.addOffice(tempOffice);
        		officeIndex = (ballotDataList.getOfficeListSize() - 1);
        	}
        }
        
        private void saveCandidate() throws BallotInputException{
        	Candidate can = new Candidate();
        	
        	Object[] tempArr = outerOfficePanel.save(BCOuterPanel.CANDIDATE);
        	
        	can.setParty((String) tempArr[0]);
    		can.setName((String) tempArr[1]);
    		can.setRunningMate((String) tempArr[2]);
        	
    		if(candidateIndex >= 0){
    			tempOffice.setCandidate(candidateIndex, can);
    		}else{
    			tempOffice.addCandidate(can);
    		}
        }
    }
    
    private class BallotQPanelListener implements ActionListener{
    	private final int MAIN_MENU = 0;
	    private final int DISCARD_CHANGES = 1;
	    private final int SAVE_CHANGES = 2;
	    private final int ADD_NEW = 3;
    	
    	private int buttonPressed;
    	
    	public void actionPerformed(ActionEvent e){
    		if(e.getSource() == outerBallotQPanel.getButtonArray()[0]){
    			if(outerBallotQPanel.getInnerBlankPanel().isVisible()){
    				//Back to Main Menu
    				buttonPressed = MAIN_MENU;
    				setEnabledButtons();
    				setButtonContent();
    				setSelectionPanelContent();
    				changeCard();
    			}else if(outerBallotQPanel.getInnerPanel(BCOuterPanel.BALLOTQ).isVisible()){
    				//Discard Changes/Back to Ballot Questions
    				buttonPressed = DISCARD_CHANGES;
    				discard();
    				setEnabledButtons();
    				setButtonContent();
    				setSelectionPanelContent();
    				changeCard();
    			}
    		}else if(e.getSource() == outerBallotQPanel.getButtonArray()[1]){
    			//Save Ballot Question
    			buttonPressed = SAVE_CHANGES;
    			try{
    				save();
        			setEnabledButtons();
    				setButtonContent();
    				setSelectionPanelContent();
    				changeCard();
    			}catch(BallotInputException ex){
    				JOptionPane.showMessageDialog(outerBallotQPanel, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
    			}
    		}else if(e.getSource() == outerBallotQPanel.getButtonArray()[2]){
    			//Add Ballot Question
    			buttonPressed = ADD_NEW;
    			add();
    			setEnabledButtons();
				setButtonContent();
				setSelectionPanelContent();
				changeCard();
    		}
        }
    	
    	private void changeCard(){
    		CardLayout outerCL = (CardLayout) outerCardSwitcher.getLayout();
            CardLayout innerCL = (CardLayout) outerBallotQPanel.getInnerCardSwitcher().getLayout();
    		
    		switch(buttonPressed){
    		case MAIN_MENU:
    			outerCL.show(outerCardSwitcher, "OuterMainPanel");
    			break;
    		case DISCARD_CHANGES:
    			innerCL.show(outerBallotQPanel.getInnerCardSwitcher(), "InnerBlankPanel");
    			break;
    		case SAVE_CHANGES:
    			innerCL.show(outerBallotQPanel.getInnerCardSwitcher(), "InnerBlankPanel");
    			break;
    		case ADD_NEW:
    			innerCL.show(outerBallotQPanel.getInnerCardSwitcher(), "InnerBallotQPanel");
    			break;
    		}
    	}
    	
    	private void save() throws BallotInputException{
    		BallotQ bq;
    		
    		if(ballotQIndex >= 0){
    			bq = ballotDataList.getBallotQ(ballotQIndex);
    		}else{
    			bq = new BallotQ();
    		}
    		
    		Object[] tempArr = outerBallotQPanel.save(BCOuterPanel.BALLOTQ);
    		
    		bq.setBQName((String) tempArr[0]);
    		bq.setBQTitle((String) tempArr[1]);
    		bq.setBQDescription((String) tempArr[2]);
    		
    		if(ballotQIndex >= 0){
        		ballotDataList.setBallotQ(ballotQIndex, bq);
        	}else{
        		ballotDataList.addBallotQ(bq);
        		ballotQIndex = (ballotDataList.getBallotQListSize() - 1);
        	}
    	}
    	
    	private void add(){
    		ballotQIndex = -1;
    		outerBallotQPanel.clearFields(BCOuterPanel.BALLOTQ);
    	}
    	
    	private void discard(){
    		outerBallotQPanel.clearFields(BCOuterPanel.BALLOTQ);
    	}
    	
    	private void setEnabledButtons(){
    		switch(buttonPressed){
    		case MAIN_MENU:
    			selectEditButton.setEnabled(false);
    			selectRemoveButton.setEnabled(false);
	            outerBallotQPanel.getButtonArray()[0].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[1].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[2].setEnabled(true);
    			break;
    		case DISCARD_CHANGES:
    			selectEditButton.setEnabled(true);
    			selectRemoveButton.setEnabled(true);
	            outerBallotQPanel.getButtonArray()[0].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[1].setEnabled(false);
	            outerBallotQPanel.getButtonArray()[2].setEnabled(true);
    			break;
    		case SAVE_CHANGES:
    			selectEditButton.setEnabled(true);
    			selectRemoveButton.setEnabled(true);
	            outerBallotQPanel.getButtonArray()[0].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[1].setEnabled(false);
	            outerBallotQPanel.getButtonArray()[2].setEnabled(true);
    			break;
    		case ADD_NEW:
    			selectEditButton.setEnabled(false);
    			selectRemoveButton.setEnabled(false);
	            outerBallotQPanel.getButtonArray()[0].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[1].setEnabled(true);
	            outerBallotQPanel.getButtonArray()[2].setEnabled(false);
    			break;
    		}
    	}
    	
    	private void setButtonContent(){
    		switch(buttonPressed){
    		case MAIN_MENU:
    			selectEditButton.setText("Edit Selection");
    			selectRemoveButton.setText("Remove Selection");
    			break;
    		case DISCARD_CHANGES:
    			outerBallotQPanel.getButtonArray()[0].setText("Back to Main Menu");
    			outerBallotQPanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/BackIcon.png"));
    			outerBallotQPanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_B);
    			break;
    		case SAVE_CHANGES:
    			outerBallotQPanel.getButtonArray()[0].setText("Back to Main Menu");
    			outerBallotQPanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/BackIcon.png"));
    			outerBallotQPanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_B);
    			break;
    		case ADD_NEW:
    			outerBallotQPanel.getButtonArray()[0].setText("Discard Changes");
    			outerBallotQPanel.getButtonArray()[0].setIcon(new ImageIcon("Icons/Black/CancelIcon.png"));
    			outerBallotQPanel.getButtonArray()[0].setMnemonic(KeyEvent.VK_D);
    			break;
    		}
    	}
    	
    	private void setSelectionPanelContent(){
    		switch(buttonPressed){
    		case MAIN_MENU:
    			displayContents();
    			selectionTitleLabel.setText("BALLOT CONTENTS");
    			break;
    		case DISCARD_CHANGES:
    			displaySavedBallotQ(true);
    			break;
    		case SAVE_CHANGES:
    			displaySavedBallotQ(true);
    			break;
    		case ADD_NEW:
    			displaySavedBallotQ(false);
    			break;
    		}
    	}
    }
}
