package data;

/**
 * Write a description of class Candidate here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Candidate {
	
    private String party;
    private String candidateName;
    private String runningMate;
    
    public Candidate(){
    	this("", "", null);
    }
    
    public Candidate(String party, String name){
        this.party = party;
        this.candidateName = name;
        this.runningMate = null;
    }
    
    public Candidate(String party, String name, String mate){
    	this.party = party;
    	this.candidateName = name;
    	this.runningMate = mate;
    }
    
    public void setParty(String party){
        this.party = party;
    }
    
    public void setName(String name){
        this.candidateName = name;
    }
    
    public String getCandidateParty(){
        return party;
    }
    
    public String getCandidateName(){
        return candidateName;
    }
    
    public String getRunningMate(){
    	return runningMate;
    }
    
    public void setRunningMate(String rm){
    	this.runningMate = rm;
    }
    
    public String toLabel(){
    	if(runningMate == null){
    		String candidateString = party + "<br>" + candidateName;
        	return candidateString;
    	}else{
    		String candidateString = party + "<br>" + candidateName + "<br>" + runningMate;
        	return candidateString;
    	}
    }
    
    public String toFile(){
        if(runningMate == null){
        	String fileString = "//" + party + " - " + candidateName + System.lineSeparator();
        	return fileString;
        }else{
        	String fileString = "//" + party + " - " + candidateName + " - " + runningMate + System.lineSeparator();
        	return fileString;
        }
    }
}
