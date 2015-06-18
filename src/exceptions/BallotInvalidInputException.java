package exceptions;

public class BallotInvalidInputException extends BallotInputException{

	public BallotInvalidInputException(){
		super();
	}
	
	public BallotInvalidInputException(String message){
		super(message);
	}
	
}
