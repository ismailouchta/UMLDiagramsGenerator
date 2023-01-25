package org.mql.java.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mql.java.models.Attribute;
import org.mql.java.models.Classe;
import org.mql.java.models.Method;

public class ClassParserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel screen, content, draw, stats;
	private JScrollPane scrollpane;
	
	public ClassParserFrame() {
		screen = new JPanel();
		content = new JPanel();
		stats = new JPanel();
		draw = new JPanel();
		
		draw.setBackground(Color.WHITE);
		
		init();
	}
	
	public void addEntity(Classe temp, org.mql.java.testing.Type type) {
		
		Entity entity = new Entity(temp.getName(), type);
		
		List<Attribute> attributes = temp.getAttributes();
		if (attributes != null) {
			for (int i = 0; i < attributes.size(); i++) {
				String attribute = "";
				boolean isStatic = false;
				
				String modifier = attributes.get(i).getModifier();
				if (modifier.contains("private")) {
					attribute += "-";
				} else if (modifier.contains("protected")) {
					attribute += "#";
				} else if (modifier.contains("public")) {
					attribute += "+";
				}
				
				attribute += " " + attributes.get(i).getName();
				attribute += " : " + attributes.get(i).getType();
	
				if (modifier.contains("final"))
					attribute += " = " + attributes.get(i).getValue();
	
				if (modifier.contains("static")) {
					isStatic = true;
				}
				
				entity.addAttribute(attribute, isStatic);
			}
		}
		
		List<Method> methods = temp.getMethods();
		if (methods != null) {
			for (int i = 0; i < methods.size(); i++) {

				String method = "";
				boolean isStatic = false;
				
				String modifier = methods.get(i).getModifier();
				if (modifier.length() != 0) {
					if (modifier.contains("private")) {
						method += "-";
					} else if (method.contains("protected")) {
						method += "#";
					} else if (method.contains("public")) {
						method += "+";
					} else {
						method += "+";
					}
				} else {
					method += "+";
				}
				method += " " + methods.get(i).getName() + "()";
				
				method += " : " + methods.get(i).getReturnType();
				
				if (modifier.contains("static")) {
					isStatic = true;
				}
				
				entity.addMethod(method, isStatic);
			}
		}

		draw.add(entity);
	}
	
	private void config() {
		getContentPane().setBackground(Color.white);
		setTitle("UML Diagrams Generator - Java Project");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	void init() { 
		screen.setLayout(new FlowLayout(FlowLayout.CENTER));
		stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
		content.setLayout(new BorderLayout());
	}

	public void showF() {
		scrollpane = new JScrollPane(draw);
		scrollpane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // Full Screen
		scrollpane.getVerticalScrollBar().setUnitIncrement(16);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(16);
		content.add(scrollpane, BorderLayout.CENTER);
				
		screen.add(content);
		setContentPane(screen);
		config();
	}
}
