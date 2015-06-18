package utilities;

public enum BallotType {
	
	GENERAL ("General"),
	PRIMARY ("Primary"),
	SPECIAL ("Special");
	
	private String typeTitle;
	
	private static final int size = BallotType.values().length;
	
	private BallotType(String typeTitle){
		this.typeTitle = typeTitle;
	}
	
	public String getTypeTitle(){
		return typeTitle;
	}
	
	public static String[] getTypeTitleList(){
		String[] btTitleArray = new String[size];
		int i = 0;
		for(BallotType b : BallotType.values()){
			btTitleArray[i] = b.getTypeTitle();
			i++;
		}
		return btTitleArray;
	}
	
	public int getTypeIndex(BallotType bt){
		switch(bt){
		case GENERAL:
			return 0;
		case PRIMARY:
			return 1;
		case SPECIAL:
			return 2;
		default:
			return 0;
		}
	}
	
	public static BallotType getBallotType(String title){
		BallotType bt = null;
        for(BallotType b : BallotType.values()){
            if(b.getTypeTitle().equalsIgnoreCase(title)){
                bt = b;
            }
        }
        return bt;
	}
	
}
