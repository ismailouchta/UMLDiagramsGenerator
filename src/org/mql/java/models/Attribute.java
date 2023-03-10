package org.mql.java.models;

public class Attribute {
	
	private String modifier;
	private String name;
	private String type;
	private Object value;

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object object) {
		this.value = object;
	}

	public Attribute() {
		
	}

}
