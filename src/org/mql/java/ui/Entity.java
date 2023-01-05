package org.mql.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class Entity extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel title, content, attribues, methods;
//	private Border blackline = BorderFactory.createLineBorder(Color.black);

	public Entity(String name) {
		
		title = new JPanel();
		attribues = new JPanel();
		methods = new JPanel();
		content = new JPanel();
		
		config();
		
		title.add(new JLabel(name));
		
		add(title);
		content.add(attribues);
		content.add(methods);
		add(content);
	}
	
	public void addItem(String item) {
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
		methods.setLayout(new BoxLayout(methods, BoxLayout.Y_AXIS));
		attribues.setLayout(new BoxLayout(attribues, BoxLayout.Y_AXIS));
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		title.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		content.setBorder(new MatteBorder(0, 1, 1, 1, Color.black));
		methods.setBorder(new MatteBorder(1, 0, 0, 0, Color.black));
		
//		attribues.setMinimumSize(new Dimension(40, 40));
//		methods.setMinimumSize(new Dimension(40, 40));
	}

}
