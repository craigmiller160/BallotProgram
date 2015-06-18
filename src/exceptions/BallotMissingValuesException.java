package exceptions;


public class BallotMissingValuesException extends BallotInputException{
	
	private int boxIndex = -1;
	
	public BallotMissingValuesException(String message){
		super("Error! All fields must be filled out.\n\n" + message);
	}
	
	public BallotMissingValuesException(int index, String message){
		super(message);
		boxIndex = index;
	}
	
	public int getBoxIndex() throws IndexOutOfBoundsException{
		if(boxIndex < 0){
			throw new IndexOutOfBoundsException("Missing checkbox index");
		}else{
			return boxIndex;
		}
	}
	
}
