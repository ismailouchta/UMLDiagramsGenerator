package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.testing.Type;
import org.mql.java.ui.ClassParserFrame;

public class ClassParser {
	private ClassParserFrame frame = new ClassParserFrame();
	private Classe temp;
	
	public ClassParser() {
	}
	
//	public ClassParser(Class<?> c) {
//		frame = new ClassParserFrame();
//		temp = extract(c);
////		temp.print();
//		frame.addEntity(temp);
//		frame.addStats(nbrAttributes, nbrMethods);
//	}

	public ClassParser(String className) {
		try {
			Class<?> cls = Class.forName(className);
			draw(cls);
			temp = extract(cls);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public Classe getClasse() {
		return temp;
	}
	
	public Interface getInterface() {
		return (Interface) temp;
	}
	
	void draw(Classe cl) {
//		temp = cc;
		frame.addEntity(cl, Type.CLASS);
	}
	
	void draw(Interface it) {
//		temp = (Classe) it;
		frame.addEntity((Classe) it, Type.INTERFACE);
	}
	
	void show() {
		frame.showF();
	}
	
	Classe extract(Class<?> cls) {
		Classe cl = new Classe();
		getFirst(cls, cl);
		getInheritence(cls, cl);
		if (!cls.isInterface()) getInterfaces(cls, cl);
		getAttributes(cls, cl);
		getConstructors(cls, cl);
		getMethods(cls, cl);
//		getInnerClass(cls, cl);
		return cl;
	}
	
	void draw(Class<?> cls) {
//		Class<?> herite[] = cls.getInterfaces();
//		Class<?> herite = cls.getSuperclass();
//		if (herite != null) {	
//			System.out.println(herite);
//			Classe s = extract(herite);
//			if (s != null && s.getName() != null) {
//				
//				frame.addEntity(s, Type.CLASS);
//			}
//		}
//		if (herite.length != 0) {
//				draw(herite[0]);
//				Classe s = extract(herite[0]);
//				frame.addEntity(s, Type.CLASS);
//		}
	}
	
	void getFirst(Class<?> cls, Classe temp) {
		int mods = cls.getModifiers();
		temp.setName(cls.getSimpleName());
		temp.setMod(Modifier.toString(mods));
	}
	
//	void getInnerClass(Class<?> cls, Classe temp) {
//		Class<?> innerClasses[] = cls.getDeclaredClasses();
//		Vector<Classe> innerCl = new Vector<Classe>();
//		if (innerClasses.length != 0) {
//	        for (int i = 0; i < innerClasses.length; i++) {       	
//				innerCl.add(extract(innerClasses[i]));
//	        }
//		}
//		temp.setInnerClasses(innerCl);
//	}
	
	void getInheritence(Class<?> cls, Classe temp) {
		if (cls.isInterface()) {
			Class<?> interfaces[] = cls.getInterfaces();
			Vector<String> inheritence = new Vector<String>();
			if (interfaces.length != 0) {
				for (int i = 0; i < interfaces.length; i++) {
					inheritence.add(interfaces[i].getSimpleName());
				}
			}
			temp.setInheritence(inheritence);
		} else {
			Vector<String> inheritence = new Vector<String>();
			Class<?> superClass = cls.getSuperclass();
			if (superClass != null) {
				String className = superClass.getSimpleName();
				if (!className.contains("Object")) {
					inheritence.add(className);
				}
				temp.setInheritence(inheritence);
			}
		}
	}
	
	void getInterfaces(Class<?> cls, Classe temp) {
		// only classes go through this
		Class<?> extractedInterfaces[] = cls.getInterfaces();
		Vector<String> interfaces = new Vector<String>();
		if (extractedInterfaces.length != 0) {
			for (int i = 0; i < extractedInterfaces.length; i++) {
				interfaces.add(extractedInterfaces[i].getSimpleName());
			}
		}
		temp.setInterfaces(interfaces);
	}
	
	void getAttributes(Class<?> cls, Classe temp) {
		Field f[] = cls.getDeclaredFields();
		if (f.length != 0) {			
			Vector<String> attributes = new Vector<String>();
			
			for (int i = 0; i < f.length; i++) {
				String attribute = "";
				String modifier = Modifier.toString(f[i].getModifiers());
				if (modifier.contains("private")) {
					attribute += "-";
				} else if (modifier.contains("protected")) {
					attribute += "#";
				} else if (modifier.contains("public")) {
					attribute += "+";
				}
				
				// Detect associations
				Set<String> classesz = ProjectParser.classesz;
				if (classesz.contains(f[i].getType().getName())) {
					System.out.println(cls.getSimpleName() + " - " + f[i].getType().getSimpleName());
				}
				//
				
				attribute += " " + f[i].getName();
				attribute += " : " + f[i].getType().getSimpleName();
				
				if (modifier.contains("final")) {
					attribute += " = ";
					f[i].setAccessible(true);
			        try {
						attribute += f[i].get(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (modifier.contains("static")) {
					attribute += "st4tic";
				}				
				attributes.add(attribute);
				temp.setAttributes(attributes);
			}
		}
	}
	
	void getConstructors(Class<?> cls, Classe temp) {
		Constructor<?> constructors[] = cls.getConstructors();
		if (constructors.length != 0) {
			Vector<String> constr = new Vector<String>();
			for (int i = 0; i < constructors.length; i++) {
				String constructor = "";
	    			String modif = Modifier.toString(constructors[i].getModifiers());
				if (modif.length() != 0) {
    				if (modif.contains("private")) {
    					constructor += "-";
    				} else if (constructor.contains("protected")) {
    					constructor += "#";
    				} else if (constructor.contains("public")) {
    					constructor += "+";
    				} else {
    					constructor += "+";
    				}
    			} else {
    				constructor += "+";
			}
			constructor += " "
			+ constructors[i].getName().replace(cls.getPackage().getName()+".", "")
			+ "(";
//    			Parameter p[] = m[i].getParameters();
//    			if (p.length != 0) {
//        			for (int j = 0; j < p.length; j++) {
//        				method += p[j].getParameterizedType().getTypeName();        				
//        				if (j < p.length-1) {
//        					method += ", ";
//        				}
//        			}
//    			}
				constructor += ")";
				constr.add(constructor);
			}
			temp.setConstructors(constr);
		}
	}
	
	void getMethods(Class<?> cls, Classe temp) {
		Method m[] = cls.getDeclaredMethods();
		
		Vector<String> methodes = new Vector<String>();
		
        for (int i = 0; i < m.length; i++) {
    		
    		String method = "";
    		String modif = Modifier.toString(m[i].getModifiers());

    			if (modif.length() != 0) {
    				if (modif.contains("private")) {
    					method += "-";
    				} else if (method.contains("protected")) {
    					method += "#";
    				} else if (method.contains("public")) {
    					method += "+";
    				} else {
    					method += "+";
    				}
    			} else {
					method += "+";
				}
    			method += " " + m[i].getName() + "(";
//    			Parameter p[] = m[i].getParameters();
//    			if (p.length != 0) {
//        			for (int j = 0; j < p.length; j++) {
//        				method += p[j].getParameterizedType().getTypeName();        				
//        				if (j < p.length-1) {
//        					method += ", ";
//        				}
//        			}
//    			}
    			method += ")";
    			method += " : " + m[i].getReturnType().getSimpleName();
    			
    			if (modif.contains("static")) method += "st4tic";
    			methodes.add(method);
        }
        temp.setMethods(methodes);
	}
		
	public static void main(String[] args) {
		if (args.length != 0) {
			new ClassParser(args[0]);
		} else {
//			new ClassParser(new RandomClass().getClass());
			new ClassParser("org.mql.java.models.Classe");
//			new ClassParser("java.util.LinkedHashSet");
//			new ClassParser("java.util.List");
//			new ClassParser("java.util.Vector");
//			new ClassParser("java.lang.String");
//			new ClassParser(args[0]);
		}
	}
}