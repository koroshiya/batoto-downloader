import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ButtonListener implements ActionListener{
	
	private final Batoto parent;
	
	public ButtonListener(Batoto parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object obj = arg0.getSource();
		
		if (obj instanceof JButton){
			
			JButton btn = (JButton) obj;
			String command = btn.getText();
			
			if (command.equals(Batoto.commands[4])){
				parent.add();
			}else if (command.equals(Batoto.commands[2])){
				parent.clearAll();
			}else if (command.equals(Batoto.commands[3])){
				parent.clearFirst();
			}else if (command.equals(Batoto.commands[0])){
				parent.setEnabled(false);
				parent.parseAll();
				parent.setEnabled(true);
			}else if (command.equals(Batoto.commands[1])){
				parent.setEnabled(false);
				parent.parseFirst();
				parent.setEnabled(true);
			}
			
		}
		
	}
	
}
