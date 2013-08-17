import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class BScrollPane extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	public BScrollPane(){
		
		super();
		setUI();
		
	}
	
	public BScrollPane(JTextArea area){

		super(area);
		setUI();
		
	}
	
	private void setUI(){
		
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.setDoubleBuffered(true);
		this.setIgnoreRepaint(true);
		this.setOpaque(true);
		this.setWheelScrollingEnabled(true);
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		this.updateUI();
	}
	
}
