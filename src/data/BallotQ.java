package data;

public class BallotQ {
	
	private String bqName, bqTitle, bqDescription;
	
	public BallotQ(){
		this("", "", "");
	}
	
	public BallotQ(String name, String title, String description){
		this.bqName = name;
		this.bqTitle = title;
		this.bqDescription = description;
	}
	
	public void setBQName(String name){
		this.bqName = name;
	}
	
	public void setBQTitle(String title){
		this.bqTitle = title;
	}
	
	public void setBQDescription(String description){
		this.bqDescription = description;
	}
	
	public String getBQName(){
		return bqName;
	}
	
	public String getBQTitle(){
		return bqTitle;
	}
	
	public String getBQDescription(){
		return bqDescription;
	}
	
	public String toString(){
		String bqString = bqName;
		return bqString;
	}
	
	public String toFile(){
		String bqString = "**" + bqName + " - " + bqTitle + " - " + bqDescription;
		String fileString = bqString + System.lineSeparator();
		
		return fileString;
	}
	
}
