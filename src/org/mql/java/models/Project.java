package org.mql.java.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Project {
	private String name;
	private Vector<Package> packages;
	private List<String> associations;

	public Project(String name, Vector<Package> packages) {
		this.name = name;
		this.packages = packages;
		associations = new LinkedList<String>();
	}

	public String getName() {
		return name;
	}

	public void setPackages(Vector<Package> packages) {
		this.packages = packages;
	}
	
	public Vector<Package> getPackages() {
		return packages;
	}
	
//	public void setPackages(Vector<Package> packages) {
//	this.packages = packages;
//}

	public List<String> getAssociations() {
		return associations;
	}

	public void addAssociation(String association) {
		this.associations.add(association);
	}

}
