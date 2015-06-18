package data;

import utilities.BallotType;

public class BallotTitle {
	
	private int ballotYear;
	private BallotType ballotType;
	private String ballotState;
	private String ballotCounty;
	private String ballotCity;
	
	public BallotTitle(){
		this(2015, BallotType.GENERAL, "NJ", "Middlesex", "East Brunswick");
	}
	
	public BallotTitle(int year, BallotType type, String state, String county, String city){
		this.ballotYear = year;
		this.ballotType = type;
		this.ballotState = state;
		this.ballotCounty = county;
		this.ballotCity = city;
	}
	
	public void setBallotYear(int year){
		this.ballotYear = year;
	}
	
	public void setBallotType(BallotType type){
		this.ballotType = type;
	}
	
	public void setBallotState(String state){
		this.ballotState = state;
	}
	
	public void setBallotCounty(String county){
		this.ballotCounty = county;
	}
	
	public void setBallotCity(String city){
		this.ballotCity = city;
	}
	
	public int getBallotYear(){
		return ballotYear;
	}
	
	public BallotType getBallotType(){
		return ballotType;
	}
	
	public String getBallotCounty(){
		return ballotCounty;
	}
	
	public String getBallotState(){
		return ballotState;
	}
	
	public String getBallotCity(){
		return ballotCity;
	}
	
	public String toString(){
		String ballotTypeTitle = ballotType.getTypeTitle();
		String ballotTitleString = ballotYear + " - " + ballotTypeTitle.toUpperCase() + " ELECTION" + " - "
								   + ballotState + " - " + ballotCounty.toUpperCase() + " - " + ballotCity.toUpperCase();
		return ballotTitleString;
	}
	
	public String toFile(){
		String ballotTitleFile = ballotYear + "-" + ballotType.getTypeTitle().toUpperCase() + "-"
				   			     + ballotState + "-" + ballotCounty + "-" + ballotCity;
		return ballotTitleFile;
	}
	
}
