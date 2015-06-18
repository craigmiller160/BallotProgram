package exceptions;


public class BallotFileNotFoundException extends BallotIOException{
	
	public BallotFileNotFoundException(){
		super("Ballot File Not Found: ");
	}
	
	public BallotFileNotFoundException(String message){
		super("Ballot File Not Found:\n" + message);
	}
	
}