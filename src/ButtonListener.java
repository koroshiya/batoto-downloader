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
			
			if (command.equals(Batoto.add)){
				parent.add();
			}else if (command.equals(Batoto.clearAll)){
				parent.clearAll();
			}else if (command.equals(Batoto.clearFirst)){
				parent.clearFirst();
			}else if (command.equals(Batoto.parseAll)){
				parent.setEnabled(false);
				parent.parseAll();
				parent.setEnabled(true);
			}else if (command.equals(Batoto.parseFirst)){
				parent.setEnabled(false);
				parent.parseFirst();
				parent.setEnabled(true);
			}
			
		}
		
	}
	
}
