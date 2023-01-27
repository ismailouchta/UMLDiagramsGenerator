package org.mql.java.ui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Draw extends JPanel {
	private static final long serialVersionUID = 1L;

	private LinkedList<Association> associations;

	public Draw() {
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintAssociations(g);
	}

	public void paintAssociations(Graphics g) {
		if (associations != null) {
			for (Association a : associations) {
				if (a.getType().equals("implementation")) {
					Graphics2D g2d = (Graphics2D) g.create();
		            Stroke dashed = new BasicStroke(1, 
		            		BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		            g2d.setStroke(dashed);
		            g2d.drawLine(a.getX1(), a.getY1(), a.getX2(), a.getY2());
		            g2d.dispose(); //gets rid of the copy
				} else {
					g.drawLine(a.getX1(), a.getY1(), a.getX2(), a.getY2());
					
					System.out.println("executed");
				}
			}
		}
	}

	public LinkedList<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(LinkedList<Association> associations) {
		this.associations = associations;
	}
	
}