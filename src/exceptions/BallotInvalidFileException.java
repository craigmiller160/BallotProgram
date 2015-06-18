package exceptions;

public class BallotInvalidFileException extends BallotIOException{

	public BallotInvalidFileException(){
		super("Not a Valid Ballot File: ");
	}
	
	public BallotInvalidFileException(String message){
		super("Not a Valid Ballot File: " + message);
	}
	
}
