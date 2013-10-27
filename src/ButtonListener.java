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
				parent.setButtonsEnabled(false);
				//parent.parseAll();
				//parent.setButtonsEnabled(true);
				ParseThread pt = new ParseThread(true, parent);
				pt.start();
			}else if (command.equals(Batoto.commands[1])){
				parent.setButtonsEnabled(false);
				//parent.parseFirst();
				//parent.setButtonsEnabled(true);
				ParseThread pt = new ParseThread(true, parent);
				pt.start();
			}
			
		}
		
	}
	
	private static class ParseThread extends Thread{
		
		private final boolean boolAll;
		private final Batoto parent;
		
		public ParseThread(boolean all, Batoto parent){
			this.boolAll = all;
			this.parent = parent;
		}
		
		@Override
		public void run(){
			if (this.boolAll){
				parent.parseAll();
			}else{
				parent.parseFirst();
			}
		}
		
	}
	
}
