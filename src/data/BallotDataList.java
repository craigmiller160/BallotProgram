package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.RandomAccess;
import java.util.Scanner;

import javax.swing.JOptionPane;

import utilities.BallotType;
import utilities.ElectionType;
import exceptions.BallotFileNameChangeException;
import exceptions.BallotFileNotFoundException;
import exceptions.BallotIOException;
import exceptions.BallotInvalidFileException;

/**
 * Write a description of class BallotDataList here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BallotDataList extends AbstractList implements RandomAccess{
	private transient ElectedOffice[] officeList;
	private transient BallotQ[] ballotQList;
    private int officeListSize;
    private int ballotQListSize;
    
    private BallotTitle ballotTitle;
    
    public BallotDataList(int initialCapacity) throws IllegalArgumentException{
        if(initialCapacity < 0){
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.officeList = new ElectedOffice[initialCapacity];
        this.ballotQList = new BallotQ[initialCapacity];
    }
    
    public BallotDataList(){
        this.officeList = new ElectedOffice[10];
        this.ballotQList = new BallotQ[10];
    }
    
    public void setBallotTitle(BallotTitle title) throws BallotFileNameChangeException{
    	boolean match = false;
    	
    	if(ballotTitle == null){
    		ballotTitle = title;
    		saveFile();
    	}else{
    		if( (title.getBallotYear() != ballotTitle.getBallotYear()) ||
        			(title.getBallotType() != ballotTitle.getBallotType()) ||
        			(! title.getBallotState().equals(ballotTitle.getBallotState())) ||
        			(! title.getBallotCounty().equals(ballotTitle.getBallotCounty())) ||
        			(! title.getBallotCity().equals(ballotTitle.getBallotCity()))){
        		
        		File file1 = new File("Ballots/" + ballotTitle.toFile() + ".txt");
        		File file2 = new File("Ballots/" + title.toFile() + ".txt");
        		
        		ballotTitle = title;
        		
        		if(file2.exists()){
        			throw new BallotFileNameChangeException("Another ballot with this name already exists.");
        		}else{
        			boolean success = file1.renameTo(file2);
        			if(! success){
        				throw new BallotFileNameChangeException("File name change failed");
        			}
        		}
        	}
    	}
    }
    
    
    public BallotTitle getBallotTitle(){
    	return ballotTitle;
    }
    
    @Override
    public Object get(int index){
    	System.out.println("default get method triggering");
    	return null;
    }
    
    public ElectedOffice getOffice(int index){
        return officeList[index];
    }
    
    public BallotQ getBallotQ(int index){
        return ballotQList[index];
    }
    
    public ElectedOffice[] getOfficeList(){
    	return officeList;
    }
    
    public BallotQ[] getBallotQList(){
    	return ballotQList;
    }
    
    @Override
    public int size(){
    	System.out.println("default Size method triggering");
    	return 0;
    }
    
    public int getOfficeListSize(){
        if(officeListSize >= Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else{
            return officeListSize;
        }
    }
    
    public int getBallotQListSize(){
        if(ballotQListSize >= Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else{
            return ballotQListSize;
        }
    }
    
    @Override
    public Object set(int index, Object o){
    	System.out.println("default set method triggering");
		return null;
        
    }
    
    public ElectedOffice setOffice(int index, ElectedOffice eo){
    	officeRangeCheck(index); //Makes more sense here
        ElectedOffice oldValue = officeList[index];
        officeList[index] = eo;
        return oldValue;
    }
    
    public BallotQ setBallotQ(int index, BallotQ bq){
    	ballotQRangeCheck(index); //Makes more sense here
        BallotQ oldValue = ballotQList[index];
        ballotQList[index] = bq;
        return oldValue;
    }
    
    
    @Override
    public boolean add(Object o){
        System.out.println("default add method triggering");
    	return false;
    }
    
    public boolean addOffice(ElectedOffice eo){
    	ensureOfficeCapacity(officeListSize + 1);
        officeList[officeListSize++] = eo;
        return true;
    }
    
    public boolean addBallotQ(BallotQ bq){
    	ensureBallotQCapacity(ballotQListSize + 1);
        ballotQList[ballotQListSize++] = bq;
        return true;
    }
    
    @Override
    public boolean remove(Object o){
    	System.out.println("default remove method triggering");
    	return false;
    }
    
    
    
    public boolean removeOffice(ElectedOffice eo){
        if(eo == null){
            for(int i = 0; i < officeListSize; i++){
                if(officeList[i] == null){
                	fastOfficeRemove(i);
                    return true;
                }
            }
        }else{
            for(int i = 0; i < officeListSize; i++){
                if(officeList[i].equals(eo)){
                	fastOfficeRemove(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean removeBallotQ(BallotQ bq){
        if(bq == null){
            for(int i = 0; i < ballotQListSize; i++){
                if(ballotQList[i] == null){
                	fastBallotQRemove(i);
                    return true;
                }
            }
        }else{
            for(int i = 0; i < ballotQListSize; i++){
                if(ballotQList[i].equals(bq)){
                	fastBallotQRemove(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void clear(){
    	System.out.println("default clear method triggered");
    }
    
    public boolean clearOfficeList(){
    	ElectedOffice[] tempList = new ElectedOffice[10];
    	officeList = tempList;
    	officeListSize = 0;
    	return true;
    }
    
    public boolean clearBallotQList(){
    	BallotQ[] tempList = new BallotQ[10];
    	ballotQList = tempList;
    	ballotQListSize = 0;
    	return true;
    }
    
    @Override
    public Object remove(int index){
    	System.out.println("Old remove method triggering");
    	return null;
    }
    
    
    public Object removeOffice(int index){
    	officeRangeCheck(index);
        Object oldValue = officeList[index];
        fastOfficeRemove(index);
        return oldValue;
    }
    
    public Object removeBallotQ(int index){
    	ballotQRangeCheck(index);
        Object oldValue = ballotQList[index];
        fastBallotQRemove(index);
        return oldValue;
    }
    
    @Override
    public boolean isEmpty(){
        System.out.println("old isEmpty() method triggered");
    	return false;
    }
    
    public boolean isOfficeEmpty(){
    	return officeListSize == 0;
    }
    
    public boolean isBallotQEmpty(){
    	return ballotQListSize == 0;
    }
    
    @Override
    public int indexOf(Object o){
    	System.out.println("old indexOf method triggered");
    	return 0;
    }
    
    public int indexOfOffice(ElectedOffice eo){
        if(eo == null){
            for(int i = 0; i < officeListSize; i++){
                if(officeList[i] == null){
                    return i;
                }
            }
        }else{
            for(int i = 0; i < officeListSize; i++){
                if(eo.equals(officeList[i])){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int indexOfBallotQ(BallotQ bq){
        if(bq == null){
            for(int i = 0; i < ballotQListSize; i++){
                if(ballotQList[i] == null){
                    return i;
                }
            }
        }else{
            for(int i = 0; i < ballotQListSize; i++){
                if(bq.equals(ballotQList[i])){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void loadFile(File file) throws BallotIOException{
        Scanner scan = null;
        
        
        
        try{
            scan = new Scanner(file);
            
            int year;
            BallotType bt;
            String state, county, city;
            
            String fileName = file.toString();
            int slashIndex = fileName.lastIndexOf("\\");
            int txtIndex = fileName.indexOf(".txt");
            String tempFName = fileName.substring((slashIndex + 1), txtIndex);
            
            int nameDashIndex = tempFName.indexOf("-");
            year = Integer.parseInt(tempFName.substring(0, nameDashIndex));
            tempFName = tempFName.substring((nameDashIndex + 1), tempFName.length());
            
            nameDashIndex = tempFName.indexOf("-");
            bt = BallotType.getBallotType(tempFName.substring(0, nameDashIndex));
            tempFName = tempFName.substring((nameDashIndex + 1), tempFName.length());
            
            nameDashIndex = tempFName.indexOf("-");
            state = tempFName.substring(0, nameDashIndex);
            tempFName = tempFName.substring((nameDashIndex + 1), tempFName.length());
            
            nameDashIndex = tempFName.indexOf("-");
            county = tempFName.substring(0, nameDashIndex);
            tempFName = tempFName.substring((nameDashIndex + 1), tempFName.length());
            
            city = tempFName;
            
            ballotTitle = new BallotTitle(year, bt, state, county, city);
            
            
            int officeCount = 0;
            int ballotQCount = 0;
            ElectedOffice[] loadOfficeList = new ElectedOffice[10];
            BallotQ[] loadBallotQList = new BallotQ[10];
            while(scan.hasNext()){
                String text = scan.nextLine();
                if(text.equals("")){
                    
                }else{
                    if((text.indexOf("//") < 0) && (text.indexOf("**") < 0)){
                        int dashIndex = text.indexOf("-");
                    
                        String electionTypeString = text.substring(0, (dashIndex - 1));
                        text = text.substring((dashIndex + 2), text.length());
                        
                        dashIndex = text.indexOf("-");
                        String officeTitle = text.substring(0, (dashIndex - 1));
                        text = text.substring((dashIndex + 2), text.length());
                        
                        dashIndex = text.indexOf("-");
                        String officeLocation = text.substring(0, (dashIndex - 1));
                        text = text.substring((dashIndex + 2), text.length());
                        
                        dashIndex = text.indexOf("-");
                        int voteFor = Integer.parseInt(text.substring(0, (dashIndex - 1)));
                        text = text.substring((dashIndex + 2), text.length());
                        
                        int term = Integer.parseInt(text);
                        
                        ElectionType electionType = ElectionType.getElectionType(electionTypeString);
                        
                        ElectedOffice office = new ElectedOffice(officeTitle, officeLocation,
                                                                 voteFor, term, electionType);
                        
                        if(officeCount >= loadOfficeList.length){
                            ElectedOffice[] oldData = loadOfficeList;
                            loadOfficeList = Arrays.copyOf(oldData, (officeCount + 10));
                        }
                        
                       
                        //System.out.println("Length: " + loadOfficeList.length);
                        loadOfficeList[officeCount] = office;
                        officeCount++;
                    }else if(text.indexOf("//") >= 0){
                        int dashIndex = text.indexOf("-");
                        String candidateParty = text.substring(2, (dashIndex - 1));
                        text = text.substring((dashIndex + 2), text.length());
                        
                        dashIndex = text.indexOf("-");
                        String candidateName = null;
                        String runningMate = null;
                        if(dashIndex < 0){
                        	candidateName = text;
                        }else{
                        	candidateName = text.substring(0, (dashIndex - 1));
                        	text = text.substring((dashIndex + 2), text.length());
                        	runningMate = text;
                        }
                        
                        Candidate can = new Candidate(candidateParty, candidateName, runningMate);
                        
                        loadOfficeList[officeCount - 1].addCandidate(can);
                    }else if(text.indexOf("**") == 0){
                    	int dashIndex = text.indexOf("-");
                    	
                    	String ballotQName = text.substring(2, (dashIndex - 1));
                    	text = text.substring((dashIndex + 2), text.length());
                    	
                    	dashIndex = text.indexOf("-");
                    	String ballotQTitle = text.substring(0, (dashIndex - 1));
                    	text = text.substring((dashIndex + 2), text.length());
                    	
                    	String ballotQDescription = text;
                    	
                    	BallotQ bq = new BallotQ(ballotQName, ballotQTitle, ballotQDescription);
                    	
                    	if(ballotQCount >= loadBallotQList.length){
                            BallotQ[] oldData = loadBallotQList;
                            loadBallotQList = Arrays.copyOf(oldData, (ballotQCount + 10));
                        }
                    	
                    	loadBallotQList[ballotQCount] = bq;
                    	
                    	ballotQCount++;
                    }
                }
            }
            for(int i = 0; i < officeCount; i++){
                addOffice(loadOfficeList[i]);
            }
            for(int i = 0; i < ballotQCount; i++){
            	addBallotQ(loadBallotQList[i]);
            }
        }catch(StringIndexOutOfBoundsException ex){
        	throw new BallotInvalidFileException();
        }catch(FileNotFoundException ex){
        	throw new BallotFileNotFoundException(ex.getMessage());
        }catch(IOException ex){
            throw new BallotIOException(ex.getMessage());
        }finally{
            if(scan != null){
                scan.close();
            }
        }
    }
    
    public void saveFile(){
    	PrintStream oFile;
    	
    	try{
    		
    		oFile = new PrintStream("Ballots/" + ballotTitle.toFile() + ".txt");
        	
        	for(int i = 0; i < officeListSize; i++){
        		ElectedOffice office = officeList[i];
        		
        		oFile.print(office.toFile());
        	}
        	
        	for(int i = 0; i < ballotQListSize; i++){
        		BallotQ bq = ballotQList[i];
        		
        		oFile.println(bq.toFile());
        	}
        	
        	oFile.close();
        	
        }catch(IOException ex){
        	System.out.println("*** I/O Error ***\n" + ex);
        }
    }
    
    private void fastOfficeRemove(int index){
        int numMoved = officeListSize - index - 1;
        if(numMoved > 0){
            System.arraycopy(officeList, index + 1, officeList, index, numMoved);
        }
        officeList[--officeListSize] = null;
    }
    
    private void fastBallotQRemove(int index){
        int numMoved = ballotQListSize - index - 1;
        if(numMoved > 0){
            System.arraycopy(ballotQList, index + 1, ballotQList, index, numMoved);
        }
        ballotQList[--ballotQListSize] = null;
    }
    
    private void ensureOfficeCapacity(int minCapacity){
        int oldCapacity = officeList.length;
        if(minCapacity > oldCapacity){
            Object oldData[] = officeList;
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if(newCapacity < minCapacity){
                newCapacity = minCapacity;
            }
            officeList = Arrays.copyOf(officeList, newCapacity);
        }
    }
    
    private void ensureBallotQCapacity(int minCapacity){
    	int oldCapacity = ballotQList.length;
    	if(minCapacity > oldCapacity){
    		Object oldData[] = ballotQList;
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if(newCapacity < minCapacity){
                newCapacity = minCapacity;
            }
            ballotQList = Arrays.copyOf(ballotQList, newCapacity);
    	}
    }
    
    private void officeRangeCheck(int index){
        if(index >= officeListSize){
            throw new IndexOutOfBoundsException(officeOutOfBoundsMsg(index));
        }
    }
    
    private void ballotQRangeCheck(int index){
        if(index >= ballotQListSize){
            throw new IndexOutOfBoundsException(bqOutOfBoundsMsg(index));
        }
    }
    
    private void officeRangeCheckForAdd(int index){
        if((index > officeListSize) || (index < 0)){
            throw new IndexOutOfBoundsException(officeOutOfBoundsMsg(index));
        }
    }
    
    private void ballotQRangeCheckForAdd(int index){
        if((index > ballotQListSize) || (index < 0)){
            throw new IndexOutOfBoundsException(bqOutOfBoundsMsg(index));
        }
    }
    
    private String officeOutOfBoundsMsg(int index){
        return "Index: " + index + ", Size: " + officeListSize;
    }
    
    private String bqOutOfBoundsMsg(int index){
        return "Index: " + index + ", Size: " + ballotQListSize;
    }
}
