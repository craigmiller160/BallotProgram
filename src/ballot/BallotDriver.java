package ballot;

import java.io.File;

import data.BallotDataList;
import exceptions.BallotFileNotFoundException;
import exceptions.BallotIOException;

public class BallotDriver {

	public static void main(String[] args){
		BallotDataList ballotDataList = new BallotDataList();
		File file = new File("Ballots/2012-GENERAL-NJ-Middlesex-East Brunswick.txt");
		
		try{
			ballotDataList.loadFile(file);
		}catch(BallotIOException ex){
			System.out.println("BALLOT FILE NOT FOUND" + ex);
		}
		
		Ballot ballot = new Ballot(ballotDataList);
		
		
	}
	
}
