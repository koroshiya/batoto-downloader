import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class BatotoListener implements ActionListener{
	
	private Batoto b;
	
	public BatotoListener(Batoto b){
		this.b = b;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		if (obj instanceof JMenuItem){
			JMenuItem jmi = (JMenuItem)obj;
			String command = jmi.getText();
			
			if (command.equals(Batoto.menuitems[0])){
				b.openFile();
			}else if (command.equals(Batoto.menuitems[1])){
				b.saveFile();
			}
		}
	}

}
