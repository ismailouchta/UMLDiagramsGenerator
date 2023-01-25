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
			
			for (Classe cls : pk.getClasses()) {
				Element c = document.createElement("class");
				c.setAttribute("name", cls.getName());
				c.setAttribute("modifier", cls.getMod());
				pack.appendChild(c);
				
				if (cls.getAttributes() != null) {
					Element atts = document.createElement("attributes");
					c.appendChild(atts);
					for (Attribute att : cls.getAttributes()) {
						Element at = document.createElement("attribute");
						at.setAttribute("modifier", att.getModifier());
						at.setAttribute("type", att.getType());
						if (att.getModifier().contains("final"))
						at.setAttribute("value", att.getValue().toString());
						at.appendChild(document.createTextNode(att.getName()));
						atts.appendChild(at);
					}
				}
				
				if (cls.getMethods() != null) {
					Element meths = document.createElement("methods");
					c.appendChild(meths);
					for (String m : cls.getMethods()) {
						Element met = document.createElement("method");
						met.appendChild(document.createTextNode(m));
						meths.appendChild(met);
					}
				}
			}
		}
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
