package org.mql.java.reflection;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Package;
import org.mql.java.models.Project;

public class ProjectParser {
	private Set<String> packs;
	static Set<String> classesz;
//	private Set<String> classesNames;

	public ProjectParser(String src) {
		
		packs = new HashSet<>();
		listOfPackage(src,packs);
		
		Vector<Package> packages = new Vector<Package>();
		classesz = new HashSet<>();
//		classesNames = new HashSet<>();
		
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
						ClassParser classparser = new ClassParser(p + "." + className);
//						System.out.println(p + "." + className);
						
//						classesz.add(p + "." + className);
						
//						System.out.println(p + "." + className);
						
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
		
		Project project = new Project("Random Project", packages);
		
		// Testing
		Vector<Package> packc = project.getPackages();
		ClassParser cp = new ClassParser();
		
		for (Package p : packc) {
			
			Vector<Classe> classes = p.getClasses();
			Vector<Interface> interfaces = p.getInterfaces();
			
			// Inheritence & implements For Classes
			for (Classe c : classes) {
			
				if (!c.getInheritence().isEmpty()) {
					
					Vector<String> parents = c.getInheritence();
					for (String pr : parents)
						System.out.println(c.getName() + " -> " + pr);
				}
				
				if (!c.getInterfaces().isEmpty()) {
					Vector<String> itr = c.getInterfaces();
					for (String it : itr)
						System.out.println(c.getName() + " --> " + it);
				}
				cp.draw(c);
			}

			// Inheritence For Interfaces
			if (!interfaces.isEmpty()) {	
				for (Interface c : interfaces) {
					Vector<String> prt = c.getInheritence();
					if (!c.getInheritence().isEmpty()) {
						for (String pr : prt)
							System.out.println(c.getName() + " -> " + pr);
					}
					cp.draw(c);
				}
			}
		}
		cp.show();
		
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
