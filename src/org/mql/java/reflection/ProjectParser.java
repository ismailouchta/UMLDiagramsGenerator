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

	public ProjectParser(String src) {
		packs = new HashSet<>();
		listOfPackage(src,packs);
		
//		Project project = new Project("Random Prject");
		
		Vector<Package> packages = new Vector<Package>();
		
		for (String p : packs) {			
			File directory = new File(src+p.replace(".", "/")+"/");
			File[] contents = directory.listFiles();
			
			// if not empty add it later
//			Package pack = new Package(p);
			Vector<Classe> classes = new Vector<Classe>();
			Vector<Interface> interfaces = new Vector<Interface>();
			
			for ( File f : contents) {
		    	  if (f.isFile()) {
		    		  
		    		  String className = f.getName().replace(".java", "");

		    		  // whats its type ?
					try {
						Class<?> cls = Class.forName(p + "." + className);
						ClassParser classparser = new ClassParser(p + "." + className);
						
						if (cls.isInterface()) {
							interfaces.add(classparser.getInterface()); // add classe
						} else {
							classes.add(classparser.getClasse());
						}
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
		    		  
		    		  
		    		  
//		    		  ClassParser classparser = new ClassParser(p + "." + className);
//		    		  classes.add(classparser.getClasse()); // add classe
		    	  }
			}
//			pack.setInterfaces(interfaces);
//			pack.setClasses(classes);
			Package pack = new Package(p, classes, interfaces);
			packages.add(pack);
			
		}
		Project project = new Project("Random Prject", packages);
//		project.setPackages(packages);
		
		
		
		
//		Testing [
		Vector<Package> packc = project.getPackages();
//		System.out.println(packc.size());
		ClassParser cp = new ClassParser();
		for (Package p : packc) {
			Vector<Classe> classes = p.getClasses();
			Vector<Interface> interfaces = p.getInterfaces();
			
			for (Classe c : classes) {
				cp.draw(c);				
			}
			for (Interface c : interfaces) {
				cp.draw(c);
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
