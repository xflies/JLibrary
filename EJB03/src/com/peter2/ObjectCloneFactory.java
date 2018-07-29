package com.peter2;
/**
 * 
 */


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter2_Weng
 *
 */
public class ObjectCloneFactory {
	
	private static Map<Class<?>, Map<String, Field>> FIELD_MAPS = new HashMap<Class<?>, Map<String, Field>>();

	/**/
	// 
	
	private static Map<Class<?>, Map<String, Method>> GETTER_MAPS = new HashMap<Class<?>, Map<String, Method>>();
	private static Map<Class<?>, Map<String, Method>> SETTER_MAPS = new HashMap<Class<?>, Map<String, Method>>();
	/**/
	
	/**
	 * Clone an object from the source class to the destination class along with the Maps of accessors i.e. getters and setters.
	 * @param source
	 * @param getters
	 * @param destination
	 * @param setters
	 * @throws Exception
	 */
	public static void cloneObjectByAccessors(final Object source, final Map<String, Method> getters, 
			Object destination, final Map<String, Method> setters) throws Exception {

		for (String fieldName : getters.keySet()) {
			// Check if the field is able to be copied i.e. has getter in source and setter in destination
			Method setter = setters.get(fieldName);
			if (setter == null) continue;
			Method getter = getters.get(fieldName);
			
			// Get field value by getter of source
			Object value = getter.invoke(source, new Object[]{});
			// Set field value by setter of destination
			setter.invoke(destination, value);
		}
	}
	
	public static void cloneObjectByAccessors(final Object source, Object destination) throws Exception {
		Map<String, Method> getters = getGetterMap(source.getClass());
		Map<String, Method> setters = getSetterMap(destination.getClass());
		
		cloneObjectByAccessors(source, getters, destination, setters);
	}
	
	/**
	 * Clone an object from the source class to the destination class along with the Maps of fields.
	 * @param source
	 * @param srcFieldMap
	 * @param destination
	 * @param destFieldMap
	 * @throws Exception
	 */
	public static void cloneObjectByFields(final Object source, Map<String, Field> srcFieldMap, 
			Object destination, Map<String, Field> destFieldMap) throws Exception {
		
		for (String fieldName : srcFieldMap.keySet()) {
			// Check if the field is able to be copied i.e. exists in both source and destination
			Field destField = destFieldMap.get(fieldName);
			if (destField == null) continue;

			// Copy field value by Field.get() and Field.set()
			destField.set(destination, srcFieldMap.get(fieldName).get(source));
		}
	}
	
	/**
	 * Execute shallow copy between two instances. Only fields have the same name of two 
	 * instances will be copied. Shallow copy means only copy references of objects. 
	 * @param source
	 * @param destination
	 * @throws Exception
	 */
	public static void cloneObject(final Object source, final Class<?> srcClass, 
			Object destination, final Class<?> destClass) throws Exception {

		Map<String, Field> srcFieldMap = getFieldMap(srcClass);

		Map<String, Field> destFieldMap = getFieldMap(destClass);

		cloneObjectByFields(source, srcFieldMap, destination, destFieldMap);
	}
	
	/**
	 * Clone an instance typed as another class. Only fields sharing the same names will be copied.
	 * @param source Source object
	 * @param destClass Class of the destination object 
	 * @param parentClass 
	 * @return Destination object
	 */
	public static Object cloneObject(final Object source, final Class<?> srcClass, final Class<?> destClass) throws Exception {

		Object dest = destClass.newInstance();

		cloneObject(source, srcClass, dest, destClass);
		
		return dest;
	}
		
	/**
	 * Clone an instance typed as its parent class
	 * @param obj the source object
	 * @return a new instance typed as parent class of the source
	 * @throws Exception
	 */
	public static Object getParent(final Object obj) throws Exception {
		Class<?> c = obj.getClass();
		Class<?> parentClass = c.getSuperclass();
		if (parentClass==null) {	// Occurs when c==Object whose parent is null exactly)
			return null;
		}

		Object parent = null;
		
		parent = cloneObject(obj, parentClass, parentClass);
		
		return parent;
	}

	/**
	 * Obtain the field map corresponding to the specified class. Because of using java.lang.Class.getDeclaredFields(), 
	 * inherited fields will be skipped. 
	 * @return the field map 
	 */
	public static Map<String, Field> getFieldMap(final Class<?> c) throws Exception {
		Map<String, Field> fieldMap = FIELD_MAPS.get(c);
		
		// Lazy initialization of the field map of Class<?> c
		if (fieldMap == null) {
			// Build the field map
			fieldMap = buildFieldMap(c);
			
			// Put the field map to the static FIELD_MAPS
			FIELD_MAPS.put(c, fieldMap);
		}
		
		return fieldMap;
	}
	
	private static Map<String, Field> buildFieldMap(final Class<?> c) throws Exception {
		HashMap<String, Field> fieldMap = new HashMap<String, Field>();

		for (Field field : c.getDeclaredFields()) {
			// Skip static or final fields
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			
			// Set private and protected fields accessible
			field.setAccessible(true);
			
			// Put the field into the hash table with its name as the key  
			fieldMap.put(field.getName(), field);
		}

		return fieldMap;
	}

	/**/
	
	/**
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Method> getGetterMap(final Class<?> c) throws Exception {
		Map<String, Method> getterMap = GETTER_MAPS.get(c);
		
		// Lazy initialization of the getter map of Class<?> c
		if (getterMap == null) {
			// Build the field map
			String patternStr = "get";
			getterMap = buildMethoMap(c, patternStr);
			
			
			// Put the field map to the static GETTER_MAPS
			GETTER_MAPS.put(c, getterMap);
		}

		return getterMap;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Method> getSetterMap(final Class<?> c) throws Exception {
		Map<String, Method> setterMap = SETTER_MAPS.get(c);
		
		// Lazy initialization of the getter map of Class<?> c
		if (setterMap == null) {
			// Build the field map
			String patternStr = "set";
			setterMap = buildMethoMap(c, patternStr);
			
			
			// Put the field map to the static GETTER_MAPS
			GETTER_MAPS.put(c, setterMap);
		}

		return setterMap;
	}
	
	/**
	 * Build a field-method map of a class. The methods' names must match the pattern string. The field name 
	 * will be translated to lower case. 
	 * Because of java.lang.Class.getMethods() is used, only public methods (including inherited ones) will 
	 * be included.
	 * @param c - the class
	 * @param patternStr - the pattern string
	 * @return the method map 
	 * @throws Exception
	 */
	private static Map<String, Method> buildMethoMap(final Class<?> c, String patternStr) throws Exception {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		patternStr = patternStr + "([A-Z])(\\w+)";
		
		for (Method method : c.getMethods()) {
			String methodName = method.getName();
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(methodName);
			if (!matcher.find()) continue;
			
			String fieldName = matcher.group(1).toLowerCase() + matcher.group(2);
			// Put the field into the hash table with its name as the key
			methodMap.put(fieldName, method);
		}
		
		return methodMap;
	}
	/**/
}
