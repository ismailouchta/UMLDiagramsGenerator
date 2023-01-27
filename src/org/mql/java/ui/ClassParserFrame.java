package org.mql.java.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mql.java.models.Attribute;
import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Method;
import org.mql.java.models.Project;

public class ClassParserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel screen, content, utils;
	private Draw draw;
	private JScrollPane scrollpane;

	private Project project;

	public ClassParserFrame() {
		screen = new JPanel();
		content = new JPanel();
		utils = new JPanel();
		draw = new Draw();

		init();
		showF();
	}

	void draw() {
		if (project != null) {
			setTitle("UML Diagrams Generator - " + project.getName());
			draw.removeAll();
			Vector<org.mql.java.models.Package> packc = project.getPackages();
			for (org.mql.java.models.Package p : packc) {
				Vector<Classe> classes = p.getClasses();
				Vector<Interface> interfaces = p.getInterfaces();

				if (!classes.isEmpty())
					for (Classe c : classes) {
						if (c != null)
							addEntity(c, org.mql.java.testing.Type.CLASS);

					}

				if (!interfaces.isEmpty())
					for (Interface i : interfaces) {
						if (i != null)
							addEntity(i, org.mql.java.testing.Type.INTERFACE);
					}
			}
			draw.revalidate();
			addAssociations();
			draw.repaint();
		}
	}

	public void addEntity(Classe temp, org.mql.java.testing.Type type) {

		Entity entity = new Entity(temp.getName(), type);
//		entity.setBorder(new EmptyBorder(30, 30, 30, 30));

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

	public void addEntity(Interface temp, org.mql.java.testing.Type type) {

		Entity entity = new Entity(temp.getName(), type);
//		entity.setBorder(new EmptyBorder(30, 30, 30, 30));

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
		setTitle("UML Diagrams Generator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	void init() {
		screen.setLayout(new FlowLayout(FlowLayout.CENTER));
		utils.setLayout(new BoxLayout(utils, BoxLayout.Y_AXIS));
		content.setLayout(new BorderLayout());
	}

	public void showF() {
		draw.setPreferredSize(new Dimension(1500, 1500));

		scrollpane = new JScrollPane(draw);
//		scrollpane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // Full Screen
		scrollpane.setPreferredSize(new Dimension(1000, 700));
		scrollpane.getVerticalScrollBar().setUnitIncrement(16);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(16);
		content.add(scrollpane, BorderLayout.CENTER);

		utils.setPreferredSize(new Dimension(250, 700));

		screen.add(content);
		screen.add(new utils(this));
		setContentPane(screen);
		config();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public static void main(String[] args) {
		new ClassParserFrame();
	}

	public void addAssociations() {		
		if (project != null ) {
			List<String> links = project.getAssociations();
			LinkedList<Association> associations = new LinkedList<Association>() ;
			
			for (String as : links) {
				String[] separated = as.split(",");
				
				Component a = getEntity(separated[0]);
				Component b = getEntity(separated[2]);
				String type = separated[1];
				
				int top1 = (int) a.getBounds().getWidth() / 2;
				int top2 = top1;
				if (b != null) {					
					top2 = (int) b.getBounds().getWidth() / 2;
				}
				
				if (a != null && b != null) {
					if (type.equals("inheritance")) {
						int bot2 = (int) b.getBounds().getHeight();
						associations.add(new Association(a.getX() + top1,
								a.getY(), b.getX() + top2, b.getY() + bot2, type));
					} else {
						associations.add(new Association(a.getX() + top1,
								a.getY(), b.getX() + top2, b.getY(), type));						
					}
				}
			}
			draw.setAssociations(associations);
			
			repaint();
			revalidate();
		}
	}

	public Component getEntity(String name) {
		Component[] units = draw.getComponents();
		for (Component e : units) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}
}
