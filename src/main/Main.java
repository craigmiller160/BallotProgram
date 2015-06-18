package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import startscreen.StartScreen;

public class Main {
	public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                
            	try{
            		for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            			if("Nimbus".equals(info.getName())){
            				UIManager.setLookAndFeel(info.getClassName());
            			}
            		}
            	}catch(Exception ex){
            		System.out.println("Exception From UI Manager");
            	}
            	
            	new StartScreen();
            }
        });
    }
}
