package utilities;

/**
 * Enumeration class ElectionType contains all valid Election Types for creating
 * an electronic ballot, and the official title of that type.
 * 
 * @author Craig Miller
 * @version 6.0
 */
public enum ElectionType {
	
    /**
     * President (Primary)
     */
    PRESIDENT_P ("President (Primary)"),
    /**
     * President (General)
     */
    PRESIDENT_G ("President (General)"),
	/**
     * Federal Offices
     */
    FEDERAL ("Federal Offices"),
    /**
     * State Offices
     */
    STATE ("State Offices"),
    /**
     * Local Offices
     */
    LOCAL ("Local Offices"),
    /**
     * Judicial Offices
     */
    JUDICIAL ("Judicial Offices"),
    /**
     * County Offices
     */
    COUNTY ("County Offices"),
    /**
     * Party Offices
     */
    PARTY ("Party Offices");
    
    //The String title assigned to each enum ElectionType
    private String typeTitle;
    /**
     * The size of the enum list.
     */
    protected static final int size = ElectionType.values().length;
    
    /**
     * Private constructor links the typeTitle variable with
     * the String title assigned to each enum ElectionType
     * 
     * @param                   the String title assigned to each enum ElectionType
     */
    private ElectionType(String title){
        this.typeTitle = title;
    }
    
    /**
     * Returns the String title assigned to the
     * specified enum ElectionType.
     * 
     * @return                 the String title assigned to each enum ElectionType
     */
    public String getTypeTitle(){
        return typeTitle;
    }
    
    public static ElectionType getElectionType(String title){
        ElectionType et = null;
        for(ElectionType e : ElectionType.values()){
            if(e.getTypeTitle().equals(title)){
                et = e;
            }
        }
        return et;
    }
    
    /**
     * Returns a String Array of the String titles
     * assigned to each enum ElectionType
     * 
     * @return                   a String Array of the String titles assigned to each enum ElectionType
     */
    public static String[] getETTitleArray(){
        String[] etTitleArray = new String[size];
        int i = 0;
        for(ElectionType e : ElectionType.values()){
            etTitleArray[i] = e.getTypeTitle();
            i++;
        }
        return etTitleArray;
    }
}
