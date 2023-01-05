package org.mql.java.models;

import java.util.Vector;

public class Project {
	private String name;
	private Vector<Package> packages;

	public Project(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Vector<Package> getPackages() {
		return packages;
	}

	public void setPackages(Vector<Package> packages) {
		this.packages = packages;
	}

}
