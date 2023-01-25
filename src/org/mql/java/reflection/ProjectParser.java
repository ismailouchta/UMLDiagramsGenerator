package org.mql.java.reflection;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Package;
import org.mql.java.models.Project;
import org.mql.java.testing.Type;
import org.mql.java.ui.ClassParserFrame;
import org.mql.java.utils.SaveXMLFile;

public class ProjectParser {
	
	private Project project;
	private Set<String> packs;
	private ClassParserFrame frame = new ClassParserFrame();

	static Set<String> classesz;

	public ProjectParser(String src) {
		
		packs = new HashSet<>();
		listOfPackage(src,packs);
		classesz = new HashSet<>();
		
		Vector<Package> packages = new Vector<Package>();
		
		project = new Project("Random Project", packages);
		
		SaveXMLFile xml = new SaveXMLFile(project);
		
		for (String p : packs) {			
			File directory = new File(src+p.replace(".", "/")+"/");
			File[] contents = directory.listFiles();
			
			Vector<Classe> classes = new Vector<Classe>();
			Vector<Interface> interfaces = new Vector<Interface>();
			
			for ( File fc : contents) {
		    	  if (fc.isFile()) {
		    		  String className = fc.getName().replace(".java", "");
		    		  classesz.add(p + "." + className);
		    	  }
			}
			
			for ( File f : contents) {
		    	  if (f.isFile()) {
		    		  
		    		String className = f.getName().replace(".java", "");

					try {
						Class<?> cls = Class.forName(p + "." + className);
						ClassParser classparser = new ClassParser(p + "." + className, project);
						
						if (cls.isInterface()) {
							interfaces.add(classparser.getInterface()); // add classe
						} else {
							classes.add(classparser.getClasse());
						}
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
		    	  }
			}
			
			Package pack = new Package(p, classes, interfaces);
			packages.add(pack);
		}
		project.setPackages(packages);
		
		xml.writePackages();
		xml.writeAssociations();
		xml.save();
		
		draw();
		
	}
	
	void draw() {
		Vector<Package> packc = project.getPackages();
		for (Package p : packc) {
			Vector<Classe> classes = p.getClasses();
			Vector<Interface> interfaces = p.getInterfaces();

			if (!classes.isEmpty())
				for (Classe c : classes) frame.addEntity(c, Type.CLASS);

			if (!interfaces.isEmpty())
				for (Interface i : interfaces) frame.addEntity((Classe) i, Type.INTERFACE);
		}
		frame.showF();
	}
	
    public static void listOfPackage(String directoryName, Set<String> pack) {
	    	File directory = new File(directoryName);
	    	File[] contents = directory.listFiles();
	    	for ( File f : contents) {
	    	  if (f.isFile()) {
	    		  String path = f.getPath();
	    		  String packName = path.substring(path.indexOf("src")+4, path.lastIndexOf('/'));
	    		  pack.add(packName.replace('/', '.'));
	    	  } else if (f.isDirectory())
	    		  listOfPackage(f.getAbsolutePath(), pack);
	    	}
    }
    
    public static void main(String[] args) {
    		new ProjectParser("/Users/ismailouchta/eclipse-workspace/UMLDiagramsGenerator/src/");
    }
}
