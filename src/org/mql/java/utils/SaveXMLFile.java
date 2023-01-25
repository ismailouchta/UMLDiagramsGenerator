package org.mql.java.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.models.Attribute;
import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Method;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SaveXMLFile {
	private Project p;
	private Document document;
	private Element packages, associations;

	public SaveXMLFile(Project p) {
		this.p = p;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();

			document = dbuilder.newDocument();

			Element project = document.createElement("project");
			document.appendChild(project);

			packages = document.createElement("packages");
			project.appendChild(packages);

			associations = document.createElement("associations");
			project.appendChild(associations);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void writePackages() {
		for (Package pk : p.getPackages()) {
			Element pack = document.createElement("package");
			pack.setAttribute("name", pk.getName());
			packages.appendChild(pack);
			
			for (Classe cls : pk.getClasses())
				addClass(pack, cls);
			
			for (Interface ints : pk.getInterfaces())
				addInterface(pack, ints);
		}
	}
	
	private void addInterface(Element pack, Interface ints) {
		Element c = document.createElement("interface");
		c.setAttribute("name", ints.getName());
		c.setAttribute("modifier", ints.getMod());
		pack.appendChild(c);
		
		if (ints.getAttributes() != null) {
			Element atts = document.createElement("attributes");
			c.appendChild(atts);
			for (Attribute att : ints.getAttributes())
			addAttribute(atts, att.getModifier(), att.getType(), att.getValue(), att.getName());
		}
		
		if (ints.getMethods() != null) {
			Element meths = document.createElement("methods");
			c.appendChild(meths);
			for (Method m : ints.getMethods())
				addMehtod(meths, m.getModifier(), m.getReturnType(), m.getName());
		}
		
	}

	public void addClass(Element pack, Classe cls) {
			Element c = document.createElement("class");
			c.setAttribute("name", cls.getName());
			c.setAttribute("modifier", cls.getMod());
			pack.appendChild(c);
			
			if (cls.getAttributes() != null) {
				Element atts = document.createElement("attributes");
				c.appendChild(atts);
				for (Attribute att : cls.getAttributes())
				addAttribute(atts, att.getModifier(), att.getType(), att.getValue(), att.getName());
			}
			
			if (cls.getMethods() != null) {
				Element meths = document.createElement("methods");
				c.appendChild(meths);
				for (Method m : cls.getMethods())
				addMehtod(meths, m.getModifier(), m.getReturnType(), m.getName());
			}
	}
	
	public void addMehtod(Element parent, String modifier, String returnType, String name) {
		Element met = document.createElement("method");
		
		met.setAttribute("modifier", modifier);
		met.setAttribute("returnType", returnType);
		
		met.appendChild(document.createTextNode(name));
		parent.appendChild(met);
	}
	
	public void addAttribute(Element parent, String modifier, String type, Object object, String name) {
		Element at = document.createElement("attribute");
		at.setAttribute("modifier", modifier);
		at.setAttribute("type", type);
		if (modifier.contains("final")) at.setAttribute("value", object.toString());
		at.appendChild(document.createTextNode(name));
		parent.appendChild(at);
	}

	public void writeAssociations() {
		for (String as : p.getAssociations()) {
			String[] separated = as.split(",");
			addAssociation(separated[0], separated[2], separated[1]);
		}
	}

	public void addAssociation(String from, String to, String type) {
		Element association = document.createElement("association");
		association.setAttribute("from", from);
		association.setAttribute("to", to);
		association.setAttribute("type", type);
		associations.appendChild(association);
	}

	public void save() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("project.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
