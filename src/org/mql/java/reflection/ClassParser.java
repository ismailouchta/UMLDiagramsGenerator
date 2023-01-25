package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.Attribute;
import org.mql.java.models.Classe;
import org.mql.java.models.Interface;
import org.mql.java.models.Project;

public class ClassParser {

	private Project project;
	private Classe temp;

	public ClassParser(String className, Project project) {
		this.project = project;
		
		try {
			Class<?> cls = Class.forName(className);
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

	Classe extract(Class<?> cls) {
		Classe cl = new Classe();
		getFirst(cls, cl);
		getInheritence(cls, cl);
		if (!cls.isInterface()) getInterfaces(cls, cl);
		getAttributes(cls, cl);
		getMethods(cls, cl);
		return cl;
	}

	void getFirst(Class<?> cls, Classe temp) {
		int mods = cls.getModifiers();
		temp.setName(cls.getSimpleName());
		temp.setMod(Modifier.toString(mods));
	}

	void getInheritence(Class<?> cls, Classe temp) {
		if (cls.isInterface()) {
			Class<?> interfaces[] = cls.getInterfaces();
			Vector<String> inheritence = new Vector<String>();
			if (interfaces.length != 0) {
				for (int i = 0; i < interfaces.length; i++) {
					inheritence.add(interfaces[i].getSimpleName());
					project.addAssociation(cls.getSimpleName() 
							+ ",inheritance," 
							+ interfaces[i].getSimpleName());
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
					project.addAssociation(cls.getSimpleName() 
							+ ",inheritance," 
							+ className);
				}
				temp.setInheritence(inheritence);
			}
		}
	}

	void getInterfaces(Class<?> cls, Classe temp) {
		// Only classes go through this
		Class<?> extractedInterfaces[] = cls.getInterfaces();
		Vector<String> interfaces = new Vector<String>();
		if (extractedInterfaces.length != 0) {
			for (int i = 0; i < extractedInterfaces.length; i++) {
				interfaces.add(extractedInterfaces[i].getSimpleName());
				project.addAssociation(cls.getSimpleName() 
						+ ",implementation," 
						+ extractedInterfaces[i].getSimpleName());
			}
		}
		temp.setInterfaces(interfaces);
	}

	void getAttributes(Class<?> cls, Classe temp) {
		
		Field f[] = cls.getDeclaredFields();
		if (f.length != 0) {
			
//			Vector<String> attributes = new Vector<String>();
			Vector<Attribute> attributes = new Vector<Attribute>(); //
			
			for (int i = 0; i < f.length; i++) {
				
				// Detect associations
				Set<String> classesz = ProjectParser.classesz;
				if (classesz.contains(f[i].getType().getName())) {
					project.addAssociation(cls.getSimpleName() 
							+ ",association," 
							+ f[i].getType().getSimpleName());
				}
				if (f[i].getType().getName().contains("java.util.")) {
					String s = f[i].getGenericType().getTypeName();
					s = s.substring(s.indexOf("<") + 1);
					s = s.substring(0, s.indexOf(">"));
					if (classesz.contains(s)) {
						project.addAssociation(cls.getSimpleName() 
								+ ",association," 
								+ s.substring(s.lastIndexOf('.') + 1));
					}
				} // End
				
//				String attribute = "";
				
				Attribute attribute = new Attribute(); //
				
				String modifier = Modifier.toString(f[i].getModifiers());
				
				attribute.setModifier(modifier);
				attribute.setName(f[i].getName());
				attribute.setType(f[i].getType().getSimpleName());
				
				if (modifier.contains("final")) {
					f[i].setAccessible(true);
					try {
						attribute.setValue(f[i].get(null));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
//				System.out.println(modifier);
				
//				if (modifier.contains("private")) {
//					attribute += "-";
//				} else if (modifier.contains("protected")) {
//					attribute += "#";
//				} else if (modifier.contains("public")) {
//					attribute += "+";
//				}
				

//				attribute += " " + f[i].getName();
//				attribute += " : " + f[i].getType().getSimpleName();

//				if (modifier.contains("final")) {
//					attribute += " = ";
//					f[i].setAccessible(true);
//					try {
//						attribute += f[i].get(null);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}

//				if (modifier.contains("static")) {
//					attribute += "st4tic";
//				}
				
				attributes.add(attribute);
				temp.setAttributes(attributes);
			}
		}
	}

	void getMethods(Class<?> cls, Classe temp) {
		Vector<String> methodes = new Vector<String>();

		// Constructors
		Constructor<?> constructors[] = cls.getConstructors();
		if (constructors.length != 0) {
//			Vector<String> constr = new Vector<String>();
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
				constructor += " " + constructors[i].getName()
						.replace(cls.getPackage()
						.getName() + ".", "") + "(";
				
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
				methodes.add(constructor);
			}
			// temp.setConstructors(constr);
		}

		// Methods
		Method m[] = cls.getDeclaredMethods();
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

			if (modif.contains("static"))
				method += "st4tic";
			methodes.add(method);
		}
		temp.setMethods(methodes);
	}
}