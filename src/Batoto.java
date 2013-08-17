import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Batoto extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String home;
	
	private JTextArea textInput;
	private JTextField textEntry;
	private JPanel panel;
	private JButton[] buttons = new JButton[5];
	public static final String[] commands = {"Parse All", "Parse First", "Clear All", "Clear First", "Add"};
	
	public Batoto(){
		this(System.getProperty("user.home"));
	}
	
	public Batoto(String home){
		setHome(home);
		instantiateGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setHome(String home){
		this.home = home;
	}
	
	public String getHome(){
		return this.home;
	}

	public void instantiateGUI(){
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		textInput = new JTextArea();
		textInput.setEditable(false);
		JPanel panelTop = new JPanel();
		this.setLayout(new FlowLayout());
		this.setMinimumSize(new Dimension(550, 400));
		this.setResizable(false);
		panel.setMinimumSize(new Dimension(400, 300));
		panel.setPreferredSize(new Dimension(400, 300));
		panel.add(new BScrollPane(textInput), BorderLayout.CENTER);
		this.add(panel);
		
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new GridLayout(4, 0));
		rightPane.setMaximumSize(new Dimension(50, 400));
		
		ButtonListener listener = new ButtonListener(this);
		
		for (int i = 0; i < buttons.length; i++){
			buttons[i] = setJButton(commands[i], listener);
			if (i < buttons.length - 1){
				rightPane.add(buttons[i]);
			}
		}
		
		textEntry = new JTextField(36);
		
		this.add(rightPane);
		this.add(panelTop);
		this.add(textEntry);
		this.add(buttons[4]);
		
		this.setVisible(true);
		
	}
	
	private JButton setJButton(String text, ButtonListener listener){
		JButton btn = new JButton(text);
		btn.addActionListener(listener);
		btn.setPreferredSize(new Dimension(110, 25));
		return btn;
	}
	
	public void add(){
		
		String curText = textInput.getText();
		
		if (!curText.equals("")){
			curText += "\n";
		}
		curText += textEntry.getText();
		
		textInput.setText(curText);
		textEntry.setText("");
	}
	
	public void parseFirst(){
		
		String[] lines = textInput.getText().split("\\n");
		textInput.setText("");
		StringBuffer remaining = new StringBuffer();
		
		if (lines.length > 0){
			int i = 0;
			if (!URLParser.downloadFromURL(lines[i], home)){
				remaining.append(lines[i]);
			}
			while(++i < lines.length){
				remaining.append("\n" + lines[i]);
				//i++;
			}
		}
		
		textInput.setText(remaining.toString());
		
	}
	
	public void parseAll(){
		
		String[] lines = textInput.getText().split("\\n");
		textInput.setText("");
		StringBuffer remaining = new StringBuffer();
		
		for (String s : lines){
			if (!URLParser.downloadFromURL(s, home)){
				remaining.append(s + "\n");
			}
		}
		
		textInput.setText(remaining.toString());
		
	}
	
	public void clearFirst(){

		String[] lines = textInput.getText().split("\\n");
		textInput.setText("");
		StringBuffer remaining = new StringBuffer();
		
		if (lines.length > 1){
			for (int i = 1; i < lines.length; i++){
				remaining.append(lines[i]);
				if (i < lines.length - 1){
					remaining.append("\n");
				}
			}
		}
		
		textInput.setText(remaining.toString());
		
	}
	
	public void clearAll(){
		textInput.setText("");
	}
	
	public void setEnabled(boolean enabled){
		
		for (JButton c : buttons){
			c.setEnabled(enabled);
			c.repaint();
			c.validate();
			c.invalidate();
			c.updateUI();
		}
		
		this.repaint();
		this.validate();
		this.invalidate();
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		panel.repaint();
		panel.invalidate();
		panel.updateUI();
		textInput.updateUI();
	}
	
}
