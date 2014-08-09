package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.regex.Pattern;

public class Util {

	public static Object loadInstance(String className) {
		Object instance = null;
		ClassLoader classLoader = Util.class.getClassLoader();
		Package pack = Util.class.getPackage();
		try {
			Class shapeKind = classLoader.loadClass(pack.getName() + "." + className);
			Constructor constructor = shapeKind.getConstructor();
			instance = constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public static boolean isNumber(String level) {
		return Pattern.matches("\\d+", level.trim());
	}
	
	public static Color[] createColorArray(String value) {
		String[] stringArray = value.split(";");
		Color[] result = new Color[stringArray.length];
		for(int x=0; x<result.length; x++)
			result[x] = createColor(stringArray[x]);
		return result;
	}
	
	public static Color createColor(String colorNumber){
		String[] stringArray = colorNumber.split(",");
		int[] intArray = stringToInt(stringArray);
		return new Color(intArray[0], intArray[1], intArray[2]);
	}

	public static int[] stringToInt(String[] stringArray) {
		int[] result = new int[stringArray.length];
		for(int x=0; x<result.length; x++)
			result[x] = Integer.valueOf(stringArray[x]);
		return result;
	}
}
