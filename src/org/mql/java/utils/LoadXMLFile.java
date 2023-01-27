package org.mql.java.utils;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mql.java.models.Attribute;
import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Method;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadXMLFile {
	private Document document;
	private Project project;

	public LoadXMLFile(String fileName) {
		try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		document = builder.parse(new File(fileName));
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		
		Vector<Package> packages = new Vector<Package>();
		
		project = new Project(root.getAttribute("name"), packages);
		
		// Extract Packages
		NodeList packs = document.getElementsByTagName("package");
		for (int i = 0; i < packs.getLength(); i++) {
			Node node = packs.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element)node;
				
				String packName = e.getAttribute("name"); // package name
				
				// Extract Classes & Interfaces
				Vector<Classe> classes = new Vector<Classe>();
				Vector<Interface> interfaces = new Vector<Interface>();
				
				NodeList xmlClasses = e.getChildNodes();
				
				for (int j = 0; j < xmlClasses.getLength(); j++) {
					Node nodee = xmlClasses.item(j);
					if (nodee.getNodeType() == Node.ELEMENT_NODE) {
						Element ee = (Element)nodee;
						
						if (ee.getNodeName() == "class") {
							loadClass(ee, classes);
						} else if (ee.getNodeName() == "interface") {
							loadInterface(ee, interfaces);
						}
					}
				}
				Package pack = new Package(packName, classes, interfaces);
				packages.add(pack);
			}
		}
		
		loadAssociations(); // Load Associations
		project.setPackages(packages);
		
//		SaveXMLFile xml = new SaveXMLFile(project);
//		xml.writePackages();
//		xml.writeAssociations();
//		xml.save();
		
//		new ProjectParser(project); // draw from xml file
	}
	
	void loadInterface(Element ee, Vector<Interface> interfaces) {
		Interface temp = new Interface();
		temp.setMod(ee.getAttribute("modifier")); // modifier
		temp.setName(ee.getAttribute("name")); // name
		
		// Attributes
		NodeList atts = ee.getElementsByTagName("attribute");
		Vector<Attribute> ats = new Vector<Attribute>();
		loadAttributes(atts, ats);
		temp.setAttributes(ats);
		
		// Methods
		NodeList meths = ee.getElementsByTagName("method");
		Vector<Method> mts = new Vector<Method>();
		loadMethods(meths, mts);
		temp.setMethods(mts);
		
		interfaces.add(temp);
	}

	private void loadClass(Element ee, Vector<Classe> classes) {
		Classe temp = new Classe();
		temp.setMod(ee.getAttribute("modifier")); // modifier
		temp.setName(ee.getAttribute("name")); // class name
		
		// Attributes
		NodeList atts = ee.getElementsByTagName("attribute");
		Vector<Attribute> ats = new Vector<Attribute>();

		loadAttributes(atts, ats);
		temp.setAttributes(ats);
		
		// Methods
		NodeList meths = ee.getElementsByTagName("method");
		Vector<Method> mts = new Vector<Method>();
		loadMethods(meths, mts);
		temp.setMethods(mts);	
	
		classes.add(temp);
	}

	void loadMethods(NodeList meths, Vector<Method> mts) {
		for (int k = 0; k < meths.getLength(); k++) {
			Element method = (Element)meths.item(k);
			
			Method mt = new Method();
			mt.setName(method.getFirstChild().getNodeValue());
			mt.setModifier(method.getAttribute("modifier"));
			mt.setReturnType(method.getAttribute("returnType"));
			
			mts.add(mt);
		}
	}

	void loadAttributes(NodeList atts, Vector<Attribute> ats) {
		for (int k = 0; k < atts.getLength(); k++) {
			Element attribute = (Element)atts.item(k);
			
			Attribute at = new Attribute();
			at.setName(attribute.getFirstChild().getNodeValue());
			at.setModifier(attribute.getAttribute("modifier"));
			at.setType(attribute.getAttribute("type"));
			if (at.getModifier().contains("final"))
				at.setValue(attribute.getAttribute("value"));
			
			ats.add(at);
		}
	}

	public void loadAssociations() {
		NodeList associations = document.getElementsByTagName("association");
		for (int i = 0; i < associations.getLength(); i++) {
			Node node = associations.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element)node;
				project.addAssociation(
						e.getAttribute("from")
						+ ","+ e.getAttribute("type") +"," 
						+ e.getAttribute("to")
						);
			}
		}
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
