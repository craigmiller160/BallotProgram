package ballotcreator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.BallotTitle;
import utilities.BallotFonts;
import utilities.BallotType;
import exceptions.BallotInvalidInputException;
import exceptions.BallotMissingValuesException;

public class BCInnerMainPanel extends JPanel{
	
	private JLabel mainPanelTitleLabel, mainPanelInstructionLabel,
				   ballotCountyLabel, ballotCityLabel, ballotYearLabel,
				   ballotTypeLabel, ballotStateLabel;
	private JTextField ballotCountyField, ballotCityField;
	private JComboBox<String> ballotYearBox, ballotTypeBox, ballotStateBox;
	private GridBagConstraints mgc;
	
	private final String[] years = { "1990", "1991", "1992", "1993", "1994", "1995",
							         "1996", "1997", "1998", "1999", "2000", "2001",
							         "2002", "2003", "2004", "2005", "2006", "2007",
							         "2008", "2009", "2010", "2011", "2012", "2013",
							         "2014", "2015", "2016", "2017", "2018", "2019",
							         "2020", "2021", "2022", "2023", "2024", "2025",
							         "2026", "2027", "2028", "2029", "2030", "2031",
							         "2032", "2033", "2034", "2035", "2036", "2037",
							         "2038", "2039", "2040", "2041", "2042", "2043",
							         "2044", "2045", "2046", "2047", "2048", "2049",
							         "2050", "2051", "2052", "2053", "2054", "2055" };
	
	private final String[] types = BallotType.getTypeTitleList();
	
	private final String[] states = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
									  "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
									  "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
									  "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
									  "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
									  "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
									  "WI", "WY" };
	
	
	
	public BCInnerMainPanel(){
		super();
		
		mainPanelTitleLabel = new JLabel("BALLOT STATS", SwingConstants.CENTER);
		mainPanelTitleLabel.setFont(BallotFonts.PANEL_TITLE_FONT);
		
		mainPanelInstructionLabel = new JLabel("<html><center>The following"
											   + " information must be provided<br>to"
											   + " set the Ballot for the correct election:</center></html>",
											   SwingConstants.CENTER);
		mainPanelInstructionLabel.setFont(BallotFonts.SUB_TITLE_FONT);
		
		ballotCountyLabel = new JLabel("County: ");
		ballotCityLabel = new JLabel("City: ");
		ballotYearLabel = new JLabel("Year: ");
		ballotTypeLabel = new JLabel("Type: ");
		ballotStateLabel = new JLabel("State: ");
		
		ballotCountyField = new JTextField(15);
		ballotCityField = new JTextField(15);
		
		ballotYearBox = new JComboBox<String>(years);
		ballotTypeBox = new JComboBox<String>(types);
		ballotStateBox = new JComboBox<String>(states);
		
		setLayout(new GridBagLayout());
		mgc = new GridBagConstraints();
		
		//FIRST ROW
		//Main Panel Title Label
		mgc.gridx = 0;
		mgc.gridy = 0;
		mgc.gridwidth = 2;
		mgc.insets = new Insets(10, 10, 10, 10);
		add(mainPanelTitleLabel, mgc);
		//SECOND ROW
		//Main Instructions Label
		mgc.gridx = 0;
		mgc.gridy = 1;
		add(mainPanelInstructionLabel, mgc);
		//THIRD ROW
		//Year Label
		mgc.gridx = 0;
		mgc.gridy = 2;
		mgc.gridwidth = 1;
		mgc.anchor = GridBagConstraints.EAST;
		add(ballotYearLabel, mgc);
		//Year Box
		mgc.gridx = 1;
		mgc.anchor = GridBagConstraints.WEST;
		add(ballotYearBox, mgc);
		//FOURTH ROW
		//Type Label
		mgc.gridx = 0;
		mgc.gridy = 3;
		mgc.anchor = GridBagConstraints.EAST;
		add(ballotTypeLabel, mgc);
		//Type Box
		mgc.gridx = 1;
		mgc.anchor = GridBagConstraints.WEST;
		add(ballotTypeBox, mgc);
		//FIFTH ROW
		//State Label
		mgc.gridx = 0;
		mgc.gridy = 4;
		mgc.anchor = GridBagConstraints.EAST;
		add(ballotStateLabel, mgc);
		//State Box
		mgc.gridx = 1;
		mgc.anchor = GridBagConstraints.WEST;
		add(ballotStateBox, mgc);
		//SIXTH ROW
		//County Label
		mgc.gridx = 0;
		mgc.gridy = 5;
		mgc.anchor = GridBagConstraints.EAST;
		add(ballotCountyLabel, mgc);
		//County Field
		mgc.gridx = 1;
		mgc.anchor = GridBagConstraints.WEST;
		add(ballotCountyField, mgc);
		//SEVENTH ROW
		//City Label
		mgc.gridx = 0;
		mgc.gridy = 6;
		mgc.anchor = GridBagConstraints.EAST;
		add(ballotCityLabel, mgc);
		//City Field
		mgc.gridx = 1;
		mgc.anchor = GridBagConstraints.WEST;
		add(ballotCityField, mgc);
		
	}
	
	public void setFields(Object object){
		BallotTitle bTitle = (BallotTitle) object;
		
		String year = "" + bTitle.getBallotYear();
		int type = bTitle.getBallotType().getTypeIndex(bTitle.getBallotType());
		
		ballotYearBox.setSelectedItem(year);
		ballotTypeBox.setSelectedIndex(type);
		ballotStateBox.setSelectedItem(bTitle.getBallotState());
		ballotCountyField.setText(bTitle.getBallotCounty());
		ballotCityField.setText(bTitle.getBallotCity());
	}
	
	public void clearFields(){
		ballotYearBox.setSelectedIndex(0);
		ballotTypeBox.setSelectedIndex(0);
		ballotStateBox.setSelectedIndex(0);
		ballotCountyField.setText("");
		ballotCityField.setText("");
	}
	
	public Object[] saveStats() throws BallotMissingValuesException, BallotInvalidInputException{
		String yearS = (String) ballotYearBox.getSelectedItem();
		int year = Integer.parseInt(yearS);
		String typeS = (String) ballotTypeBox.getSelectedItem();
		BallotType type = BallotType.getBallotType(typeS);
		String state = (String) ballotStateBox.getSelectedItem();
		
		String county = ballotCountyField.getText();
		if(county.equals("")){
			throw new BallotMissingValuesException("Ballot County Field is blank ");
		}else if((county.contains("/")) ||
				   (county.contains("-")) ||
				   (county.contains("*")) ||
				   (county.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
		
		
		String city = ballotCityField.getText();
		if(city.equals("")){
			throw new BallotMissingValuesException("Ballot City Field is blank ");
		}else if((city.contains("/")) ||
				   (city.contains("-")) ||
				   (city.contains("*")) ||
				   (city.contains("|"))){
			   
			throw new BallotInvalidInputException("Invalid character use. '/', '*', '-', & '|' are not allowed");
	    }
		
		Object[] tempArr = { (int) year, (BallotType) type, (String) state,
							 (String) county, (String) city };
				
		return tempArr;
	}
	
}
