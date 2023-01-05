package org.mql.java.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mql.java.models.Classe;
import org.mql.java.testing.Type;

public class ClassParserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel screen, content, draw, stats;
	private JScrollPane scrollpane;
	private int nbrUnits = 0;
	
	public ClassParserFrame() {
		screen = new JPanel();		
		content = new JPanel();
		stats = new JPanel();
		draw = new JPanel();
		init();
	}
	
	public void addEntity(Classe temp, org.mql.java.testing.Type type) {
		
		Entity entity = new Entity(temp.getName(), type);
//		entity.setName();

		List<String> attributes = temp.getAttributes();
		if (attributes != null) {
			for (int i = 0; i < attributes.size(); i++) {
				entity.addAttribute(attributes.get(i));
			}
		}
		
		List<String> construct = temp.getConstructors();
		if (construct != null) {
			for (int i = 0; i < construct.size(); i++) {
				entity.addMethod(construct.get(i));
			}
		}
		
		List<String> methods = temp.getMethods();
		if (methods != null) {
			for (int i = 0; i < methods.size(); i++) {
				entity.addMethod(methods.get(i));
			}
		}
		
//		if (nbrUnits != 0) {
//			JPanel arrowP = new JPanel();
//			arrowP.add(new JLabel("↑"));
//			draw.add(arrowP);
//		}
		
		draw.add(entity);
		nbrUnits++;
	}
	
	private void config() {
		setTitle("UML Diagrams Generator - Java Project");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setSize(800, 800);
		pack();
		setVisible(true);
	}
	
	void init() { 
		screen.setLayout(new FlowLayout(FlowLayout.CENTER));
		stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
//		draw.setLayout(new BoxLayout(draw, BoxLayout.Y_AXIS)); // here to make it | again
		content.setLayout(new BorderLayout());
	}

	public void addStats(int nbrAttributes, int nbrMethods) {
		scrollpane = new JScrollPane(draw);
//		scrollpane.setPreferredSize(new Dimension(550, 700));
		scrollpane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); // full screen
		
		content.add(scrollpane, BorderLayout.CENTER);
		
//		stats.add(new JLabel("	"));
//		stats.add(new JLabel("	"));
//		stats.add(new JLabel("			Informations extraites"));
//		stats.add(new JLabel("			Nombre de propriétés : " + nbrAttributes));
//		stats.add(new JLabel("			nombre de méthodes : " + nbrMethods));
//		content.add(stats, BorderLayout.EAST);
		
		screen.add(content);
		setContentPane(screen);
		config();
	}
}
