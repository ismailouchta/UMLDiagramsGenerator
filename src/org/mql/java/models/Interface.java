package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class Interface {	
	
	protected String mod, name;
	protected Vector<String> attributes, methods;

	protected Class<?>[] interfaces;
	protected Class<?>[] inheritence;

	protected Vector<Classe> innerClasses;
	
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

	public Vector<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Vector<String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(Vector<String> methods) {
		this.methods = methods;
	}

	public Vector<Classe> getInnerClasses() {
		return innerClasses;
	}

	public void setInnerClasses(Vector<Classe> innerClasses) {
		this.innerClasses = innerClasses;
	}
	
	public void setInterfaces(Class<?>[] classes) {
		this.interfaces = classes;
	}
	
	public Class<?>[] getInterfaces() {
		return interfaces;
	}
	
	public Class<?>[] getInheritence() {
		return inheritence;
	}
	
	public void setInheritence(Class<?>[] inheritence) {
		this.inheritence = inheritence;
	}

}
