package ballot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Tester {

	private JFrame window;
	private JRadioButton radio1, radio2;
	
	public static void main(String[] args){
		Tester tester = new Tester();
		tester.start();
	}
	
	private void start(){
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonGroup group = new ButtonGroup();
		
		radio1 = new JRadioButton("Radio 1", false);
		radio1.addActionListener(new TestListener());
		radio2 = new JRadioButton("Radio 2", false);
		radio2.addActionListener(new TestListener());
		group.add(radio1);
		group.add(radio2);
		
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setSize(300, 300);
	}
	
	private class TestListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource() == radio1){
				JOptionPane.showMessageDialog(window, "Radio 1", "Message", JOptionPane.INFORMATION_MESSAGE);
			}else{
				radio2.setSelected(false);
				radio1.setSelected(true);
				//radio1.fireActionPerformed(ae);
			}
			
		}
		
	}
	
}
