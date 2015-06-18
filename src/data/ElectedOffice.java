package data;

import java.util.ArrayList;

import utilities.ElectionType;

/**
 * Write a description of class ElectedOffice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ElectedOffice {
	public String officeTitle;
    public String officeLocation;
    public int voteFor;
    public int term;
    public ElectionType electionType;
    
    private ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
    private int candidateArraySize;
    
    public ElectedOffice(){
    	this("", "", 1, 1, ElectionType.FEDERAL);
    }
    
    public ElectedOffice(String title, String location, int voteFor, int term, ElectionType et){
        this.officeTitle = title;
        this.officeLocation = location;
        this.voteFor = voteFor;
        this.term = term;
        this.electionType = et;
        
        candidateArraySize = 0;
    }
    
    public static ElectedOffice copyOf(ElectedOffice eo){
    	String title = eo.getOfficeTitle();
    	String location = eo.getOfficeLocation();
    	ElectionType et = eo.getOfficeElectionType();
    	int vote4 = eo.getOfficeVoteFor();
    	int termS = eo.getOfficeTerm();
    	
    	ElectedOffice office = new ElectedOffice(title, location, vote4, termS, et);
    	
    	office.addCandidateList(eo.getCandidateList());
    	
    	return office;
    }
    
    public void addCandidate(String party, String name){
    	candidateList.add(new Candidate(party, name));
        candidateArraySize++;
    }
    
    public void addCandidate(Candidate candidate){
        //candidate.setOfficeTitle(officeTitle);
        candidateList.add(candidate);
    	candidateArraySize++;
    }
    
    public void addCandidateList(ArrayList<Candidate> canList){
    	candidateList.addAll(canList);
    }
    
    public void addCandidateArray(Candidate[] canArr){
    	for(Candidate c : canArr){
    		candidateList.add(c);
    	}
    }
    
    public void setCandidate(int index, Candidate candidate){
    	//candidate.setOfficeTitle(officeTitle);
    	candidateList.set(index, candidate);
    }
    
    public Candidate getCandidate(int index){
    	return candidateList.get(index);
    }
    
    public void removeCandidate(int index){
    	candidateList.remove(index);
    	candidateArraySize--;
    }
    
    public void setOfficeTitle(String officeTitle){
        this.officeTitle = officeTitle;
    }
    
    public void setOfficeLocation(String officeLocation){
        this.officeLocation = officeLocation;
    }
    
    public void setOfficeVoteFor(int voteFor){
        this.voteFor = voteFor;
    }
    
    public void setOfficeTerm(int term){
        this.term = term;
    }
    
    public void setOfficeElectionType(ElectionType et){
        this.electionType = et;
    }
    
    public String getOfficeTitle(){
        return officeTitle;
    }
    
    public String getOfficeLocation(){
        return officeLocation;
    }
    
    public int getOfficeVoteFor(){
        return voteFor;
    }
    
    public int getOfficeTerm(){
        return term;
    }
    
    public ElectionType getOfficeElectionType(){
        return electionType;
    }
    
    public ArrayList<Candidate> getCandidateList(){
        return candidateList;
    }
    
    public int getCandidateListSize(){
    	return candidateArraySize;
    }
    
    public String officeToString(){
        String officeString = officeTitle + " - " + officeLocation;
        return officeString;
    }
    
    public String officeToLabel(){
        String officeString = officeTitle + "<br>" + officeLocation;
        return officeString;
    }
    
    public String getCandidateString(){
    	String candidateString = "";
    	for(Candidate c : candidateList){
    		candidateString += c.toString() + "|";
    	}
    	System.out.println(candidateString);
    	return candidateString;
    }
    
    public String toFile(){
        String officeString = "";
        String candidateString = "";
        String fileString = "";
        
        officeString = electionType.getTypeTitle() + " - " + officeTitle + " - "
                       + officeLocation + " - " + voteFor + " - " + term
                       + System.lineSeparator();
        
        for(Candidate c : candidateList){
            candidateString += c.toFile();
        }
        
        fileString = officeString + candidateString;
        return fileString;
    }
}
