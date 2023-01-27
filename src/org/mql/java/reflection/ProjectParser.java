package org.mql.java.reflection;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Package;
import org.mql.java.models.Project;

public class ProjectParser {
	private Project project;
	private Set<String> packs;

	static Set<String> classesz;
	
	public ProjectParser(Project p) {
		project = p;
	}

	public ProjectParser(String src) {
		packs = new HashSet<>();
		listOfPackage(src+"/bin",packs);
		
		classesz = new HashSet<>();
		
		Vector<Package> packages = new Vector<Package>();
		
		project = new Project("Random Project", packages);
		
		for (String p : packs) {
			File directory = new File(src+"/bin/"+p.replace(".", "/")+"/");
			File[] contents = directory.listFiles();
			
			Vector<Classe> classes = new Vector<Classe>();
			Vector<Interface> interfaces = new Vector<Interface>();
			
			for ( File fc : contents) {
		    	  if (fc.isFile()) {
		    		  String className = fc.getName().replace(".class", "");
		    		  classesz.add(p + "." + className);
		    	  }
			}
			
			for ( File f : contents) {
		    	  if (f.isFile()) {
		    		String className = f.getName().replace(".class", "");

					try {
						File ff = new File(src+"/bin");
						URL[] cp = {ff.toURI().toURL()};
						try (URLClassLoader urlcl = new URLClassLoader(cp)) {
							Class<?> cls  = urlcl.loadClass(p+"."+className);
						ClassParser classparser = new ClassParser(
								src+"/bin/",p + "." + className, project);
						
							if (cls.isInterface()) {
								interfaces.add(classparser.getInterface()); // add classe
							} else {
								classes.add(classparser.getClasse());
							}
						}
						
						} catch (Exception e) {
						e.getMessage();
					}
		    	  }
			}
			
			Package pack = new Package(p, classes, interfaces);
			packages.add(pack);
		}
		project.setPackages(packages);
	}
	
    public static void listOfPackage(String directoryName, Set<String> pack) {
	    	File directory = new File(directoryName);
	    	File[] contents = directory.listFiles();
//	    	if (contents != null)
		    	for ( File f : contents) {
		    	  if (f.isFile()) {
		    		  String path = f.getPath();
		    		  String packName = path.substring(path.indexOf("bin")+4, path.lastIndexOf('/'));
		    		  packName = packName.replace('/', '.');
		    		  pack.add(packName);
		    	  } else if (f.isDirectory())
		    		  listOfPackage(f.getAbsolutePath(), pack);
		    	}
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
