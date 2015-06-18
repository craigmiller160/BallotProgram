package exceptions;

public class BallotInputException extends BallotException{

	public BallotInputException(){
		super();
	}
	
	public BallotInputException(String message){
		super(message);
	}
	
}
