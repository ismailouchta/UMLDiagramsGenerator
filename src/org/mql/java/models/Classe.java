package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class Classe extends Interface {	
	
//	private String mod, name;
//	private Vector<String> constructors, attributes, methods;
//
//	private Class<?>[] interfaces;
//	private Class<?>[] inheritence;

//	private Vector<Classe> innerClasses;
	
	private Vector<String> constructors;
	
	public Classe() {
	}
	
	public Classe(String name) {
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

	public List<String> getConstructors() {
		return constructors;
	}

	public void setConstructors(Vector<String> constructors) {
		this.constructors = constructors;
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
	
	public void print() {
		System.out.print(mod + " class " + name);
		
		if (inheritence.length != 0) {
		System.out.print(" extends ");
			for (int i = 0; i < inheritence.length; i++) {
				System.out.print(inheritence[i].getName());
				if (i < inheritence.length-1)  System.out.print(", ");
			}
		}
		
		if ((interfaces.length != 0)) {
			System.out.print(" implements ");
			for (int i = 0; i < interfaces.length; i++) {
				System.out.print(interfaces[i].getName());
				if (i < interfaces.length-1)  System.out.print(", ");
			}
		}
		
		System.out.print(" {\n");
		
		if (attributes != null) {
			for (int i = 0; i < attributes.size(); i++) {
				System.out.println(attributes.get(i) + ";");
			}
		}
		
		if (constructors != null) {
			System.out.println("");
			for (int i = 0; i < constructors.size(); i++) {
				System.out.println(constructors.get(i) + ";");
			}
		}
		
		if (methods != null) {
			System.out.println("");
			for (int i = 0; i < methods.size(); i++) {
				System.out.println(methods.get(i) + ";");
			}
		}
		
//		if ((innerClasses.size() != 0) && innerClasses != null) {
//	        for (int i = 0; i < innerClasses.size(); i++) {
//	        	innerClasses.get(i).print();
//	        }
//		}
		
		System.out.println("}");
	}

}
