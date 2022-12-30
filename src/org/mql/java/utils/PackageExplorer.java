package org.mql.java.utils;

import java.io.File;
import java.util.Vector;

public class PackageExplorer {

	public PackageExplorer() {
	}
	
	public String [] getClassList(String packageName) {
				
		String classpath = PackageExplorer.class
				.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath();
		
//		System.out.println(classpath);
		String packagepath = packageName.replace(".", "/");
//		System.out.println(packagepath);
		File dir = new File(classpath + "/" + packagepath);
//		System.out.println(dir.isDirectory());	
		
		File f[] = dir.listFiles();
		
		Vector<String> v = new Vector<String>();
		
		for (int i = 0; i < f.length; i++) {
//			System.out.println("- " + f[i].getName());
			
			if (f[i].isFile() && f[i].getName().endsWith("Test.class")) {
				String name = f[i].getName().replace(".class", "");
				v.add(packageName + "." + name);
			}
		}
		
		String t[]  =  new String[v.size()];
		v.toArray(t);
		return t;
	}
	
	public static void main(String[] args) {
		PackageExplorer p = new PackageExplorer();
		String[] classes = p.getClassList("org.mql.java.models");
		for (String classs : classes) {
			System.out.println(classs);
		}
	}
}