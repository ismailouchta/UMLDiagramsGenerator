package org.mql.java.ui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Entity extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel top, mid, bot;
	private Border blackline = BorderFactory.createLineBorder(Color.black);

	public Entity(String name) {
		System.out.println(name);
		top = new JPanel();
		mid = new JPanel();
		bot = new JPanel();
		
		config();
		top.add(new JLabel(name));
		add(top); add(mid); add(bot); 
	}
	
	public void addItem(String item) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel(item));
		bot.add(panel);
	}
	
//	public void addAttributes() {
//		
//	}
	
	
	// add attributes
	// add methods
	
	void config() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
		top.setBorder(blackline);
		bot.setBorder(blackline);
		mid.setBorder(blackline);
	}

}
