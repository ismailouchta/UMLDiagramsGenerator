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
	private JPanel title, content, attributes, methods;

	public Entity(String name, Type type) {
		this.name = name;
		title = new JPanel();
		attributes = new JPanel();
		methods = new JPanel();
		content = new JPanel();
		
		config();
		
		if (type == Type.INTERFACE) {			
			JPanel tp = new JPanel();
			tp.setLayout(new FlowLayout(FlowLayout.CENTER));
			tp.add(new JLabel("<< interface >>"));
			tp.setBackground(Color.WHITE);
			title.add(tp);
			
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel label = new JLabel(this.name);
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			p.add(label);
			p.setBackground(Color.WHITE);
			title.add(p);
			
		} else {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel label = new JLabel(name);
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			p.add(label);
			p.setBackground(Color.WHITE);
			title.add(p);
		}
		content.add(title); content.add(attributes); content.add(methods);
		add(content);
	}
	
	public void addMethod(String item, boolean isStatic) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if (isStatic) {
			JLabel jl = new JLabel("<HTML><U>"+item+"</U></HTML>");
			panel.add(jl);
		} else {			
			panel.add(new JLabel(item));
		}
		
		panel.setBackground(Color.WHITE);
		methods.add(panel);
	}
	
	public void addAttribute(String item, boolean isStatic) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if (isStatic) {
			JLabel jl = new JLabel("<HTML><U>"+item+"</U></HTML>");
			panel.add(jl);
		} else {			
			panel.add(new JLabel(item));
		}
		panel.setBackground(Color.WHITE);
		attributes.add(panel);
	}
	
	void config() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
		attributes.setLayout(new BoxLayout(attributes, BoxLayout.Y_AXIS));
		methods.setLayout(new BoxLayout(methods, BoxLayout.Y_AXIS));
		
		title.setBorder(new MatteBorder(1, 0, 1, 0, Color.black));
		content.setBorder(new MatteBorder(0, 1, 1, 1, Color.black));
		methods.setBorder(new MatteBorder(1, 0, 0, 0, Color.black));	
	}
}
