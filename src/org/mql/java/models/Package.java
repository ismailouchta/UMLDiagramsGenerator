package org.mql.java.models;

import java.util.Vector;

public class Package {
	private String name;
	private Vector<Classe> classes;
//	private Vector<Interface> interfaces;

	public Package(String name) {
		this.name = name;
	}

	public Vector<Classe> getClasses() {
		return classes;
	}

	public void setClasses(Vector<Classe> classes) {
		this.classes = classes;
	}

	public String getName() {
		return name;
	}
	
}
