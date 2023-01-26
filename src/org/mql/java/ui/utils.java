package org.mql.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mql.java.models.Project;
import org.mql.java.utils.LoadXMLFile;
import org.mql.java.utils.SaveXMLFile;

public class utils extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private Project project;
	private JButton demo, select, save, load;
	private ClassParserFrame frame;

	public utils(ClassParserFrame frame) {
		
//		frame.draw();
		
		project = frame.getProject();
		this.frame = frame;
		init();
	}
	
	void init() {
		setBackground(Color.white);
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setPreferredSize(new Dimension(250, 700));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(new JLabel("Demo"));
		demo = new JButton("Draw");
		demo.addActionListener(this);
		add(demo);
		
		add(new JLabel("Draw UML from project"));
		select = new JButton("Select");
		select.addActionListener(this);
		add(select);
		
		add(new JLabel("Save UML as XML file"));
		save = new JButton("Save File");
		save.addActionListener(this);
		add(save);
		
		add(new JLabel("Draw UML from XML file"));
		load = new JButton("Select File");
		load.addActionListener(this);
		add(load);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == demo) {
			frame.draw();
		} else if (e.getSource() == select) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
//				System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			}
		} else if (e.getSource() == load) {
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				LoadXMLFile loadxml = new LoadXMLFile(fileChooser.getSelectedFile().getAbsolutePath());
				project = loadxml.getProject();
				frame.setProject(project);
				frame.draw();
			}
		} else if (e.getSource() == save) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(new File("project.xml"));
			int response = fileChooser.showSaveDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				SaveXMLFile saveXML = new SaveXMLFile(project,
						fileChooser.getSelectedFile().getAbsolutePath());
				saveXML.save();
			}
		}
	}
}