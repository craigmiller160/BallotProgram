package exceptions;

public class BallotFileNameChangeException extends BallotIOException{

	public BallotFileNameChangeException(){
		super();
	}
	
	public BallotFileNameChangeException(String message){
		super(message);
	}
	
}
