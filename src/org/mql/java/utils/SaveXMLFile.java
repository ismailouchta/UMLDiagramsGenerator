package org.mql.java.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SaveXMLFile {
	private Project p;
	private Document document;
	private Element project, packages, associations;

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
			
			
			
			
//			for (Package pk : p.getPackages()) {
//				System.out.println(pk.getName());
////				Element pack = document.createElement("package");
////				pack.setAttribute("name", pk.getName());
////				packages.appendChild(pack);
//			}

			
			
			
//			List<Object> associations = p.getAssociations();
//			for (Object as : associations) {
//				System.out.println(as);
//			}
			
			// email element
			// Element email = document.createElement("email");
			// email.appendChild(document.createTextNode("admin@interviewBubble.com"));
			// user.appendChild(email);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void writePackages() {
		for (Package pk : p.getPackages()) {
//			System.out.println(pk.getName());
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
					for (String att : cls.getAttributes()) {
						Element at = document.createElement("attribute");
						at.appendChild(document.createTextNode(att));
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
		
//		Vector<Package> packs = p.getPackages();
//		for (Package pk : packs) {
//
//			Vector<Classe> classes = pk.getClasses();
//			Vector<Interface> interfaces = pk.getInterfaces();
//
//			// Classes [ Inheritence & implementation ]
//			for (Classe c : classes) {
//
//				if (!c.getInheritence().isEmpty()) {
//					Vector<String> parents = c.getInheritence();
//					for (String pr : parents) {
//						addAssociation(c.getName(), pr, "inheritance");
//					}
//				}
//
//				if (!c.getInterfaces().isEmpty()) {
//					Vector<String> itr = c.getInterfaces();
//					for (String it : itr) {
//						addAssociation(c.getName(), it, "implementation");
//					}
//				}
//			}
//
//			// Interfaces [ Inheritence ]
//			if (!interfaces.isEmpty()) {
//				for (Interface i : interfaces) {
//					Vector<String> prt = i.getInheritence();
//					if (!i.getInheritence().isEmpty()) {
//						for (String pr : prt) {
//							addAssociation(i.getName(), pr, "inheritance");
//						}
//					}
//				}
//			}
//		}
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
			// 5. Create a new StreamResult to the output stream you want to use.
			StreamResult result = new StreamResult(new File("project.xml"));
			// 6. Use transform method to write the DOM object to the output stream.
			transformer.transform(source, result);
//			System.out.println("XML File created successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
