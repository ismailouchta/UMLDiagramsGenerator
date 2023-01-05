package org.mql.java.reflection;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ProjectParser {
	private Set<String> packages;

	public ProjectParser(String src) {
		packages = new HashSet<>();
		listOfPackage(src,packages);
		System.out.println(packages);
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
