package org.mql.java.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import org.mql.java.testing.Type;

public class Entity extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private JPanel title, content, attribues, methods;
//	private Border blackline = BorderFactory.createLineBorder(Color.black);

	public Entity(String name, Type type) {
		
		title = new JPanel();
		attribues = new JPanel();
		methods = new JPanel();
		content = new JPanel();
		
		config();
		
		if (type == Type.INTERFACE) {
			
//			title.setBorder(new EmptyBorder(10, 10, 10, 10));
			
			JPanel tp = new JPanel();
			tp.setLayout(new FlowLayout(FlowLayout.CENTER));
			tp.add(new JLabel("<< interface >>"));
			title.add(tp);
//			tp.setBorder(new EmptyBorder(-1, -1, -1, -1));
			
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JLabel label = new JLabel(name);
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			
			p.add(label);
			title.add(p);
			
		} else {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JLabel label = new JLabel(name);
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			
			p.add(label);
			title.add(p);
		}
		
		
		
//		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
//		add(title);
		
		content.add(title);
		content.add(attribues);
		content.add(methods);
		add(content);
	}
//	
//	public void setName() {
//		title.add(new JLabel(name));
//	}
//	
	public void addMethod(String item) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel(item));
		methods.add(panel);
	}
	
	public void addAttribute(String item) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel(item));
		attribues.add(panel);
	}
	
//	public void addAttributes() {
//		
//	}
	
	
	// add attributes
	// add methods
	
	void config() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
		attribues.setLayout(new BoxLayout(attribues, BoxLayout.Y_AXIS));
		methods.setLayout(new BoxLayout(methods, BoxLayout.Y_AXIS));
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		title.setBorder(new MatteBorder(1, 0, 1, 0, Color.black));
		content.setBorder(new MatteBorder(0, 1, 1, 1, Color.black));
		methods.setBorder(new MatteBorder(1, 0, 0, 0, Color.black));
		
//		attribues.setMinimumSize(new Dimension(40, 40));
//		methods.setMinimumSize(new Dimension(40, 40));
	}

}
