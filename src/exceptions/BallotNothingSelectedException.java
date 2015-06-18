package exceptions;

public class BallotNothingSelectedException extends Exception{

	public BallotNothingSelectedException(){
		super();
	}
	
	public BallotNothingSelectedException(String message){
		super(message);
	}
	
}
