package org.mql.java.models;

import java.util.Vector;

public class Interface {	
	
	protected String mod, name;
	protected Vector<Attribute> attributes;
	protected Vector<Method> methods;
	protected Vector<String> interfaces;
	protected Vector<String> inheritence;
	
	public Interface() {
		
	}
	
	public Interface(String name) {
		this.name = name;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Vector<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Vector<Method> getMethods() {
		return methods;
	}

	public void setMethods(Vector<Method> methods) {
		this.methods = methods;
	}
	
	public void setInterfaces(Vector<String> classes) {
		this.interfaces = classes;
	}
	
	public Vector<String> getInterfaces() {
		return interfaces;
	}
	
	public Vector<String> getInheritence() {
		return inheritence;
	}
	
	public void setInheritence(Vector<String> inheritence) {
		this.inheritence = inheritence;
	}
}
