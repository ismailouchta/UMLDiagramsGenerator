package org.mql.java.models;

import java.util.Vector;

public class Package {
	
	private String name;
	private Vector<Classe> classes;
	private Vector<Interface> interfaces;

	public Package(String name, Vector<Classe> classes, Vector<Interface> interfaces) {
		this.name = name;
		this.classes = classes;
		this.interfaces = interfaces;
	}

	public Vector<Classe> getClasses() {
		return classes;
	}

	public String getName() {
		return name;
	}

	public Vector<Interface> getInterfaces() {
		return interfaces;
	}
	
}
