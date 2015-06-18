package utilities;

import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Dialogs {

	public static JFileChooser ballotFileChooser(){
		JFileChooser fc = new JFileChooser("Ballots");
		fc.setDialogTitle("Open Saved Ballot");
		fc.setFileFilter(new FileNameExtensionFilter("Ballot Files", "txt"));
		fc.setMultiSelectionEnabled(false);
		fc.setApproveButtonText("Open Ballot");
		
		return fc;
	}
	
	public static JOptionPane aboutDialog(){
		String message = "Ballot Creator\n\n"
				   + "Version 6.0\n\n"
				   + "(c) Copyright Craig Miller 2015\n"
				   + "All rights reserved\n\n"
				   + "\"Democracy is the worst form of government,\n"
				   + "except for all the alternatives.\"\n--Winston Churchill.";
		
		JOptionPane about = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE,
											JOptionPane.OK_OPTION);
		
		return about;
	}
	
	public static void main(String[] args){
		Dialogs.ballotFileChooser().showOpenDialog(null);
	}
	
}
