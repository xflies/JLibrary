/**
 * 
 */
package com.peter2.swing.form;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.peter2.ObjectCloneFactory;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.component.ISheetForm;
import com.peter2.swing.component.JDigitField;
import com.peter2.swing.component.JIsbnField;

/**
 * @author Peter2_Weng
 *
 */
public abstract class AbstractSheetForm implements ISheetForm<KeeperFunction> {

	private static final Map<Class<?>, Method> ITEM_GET_VALUE_METHOD_MAP = new HashMap<Class<?>, Method>();
	static {
		try {
			ITEM_GET_VALUE_METHOD_MAP.put(JTextField.class, JTextField.class.getMethod("getText", new Class[]{}));
			ITEM_GET_VALUE_METHOD_MAP.put(JDigitField.class, JDigitField.class.getMethod("getValue", new Class[]{}));
			ITEM_GET_VALUE_METHOD_MAP.put(JIsbnField.class, JIsbnField.class.getMethod("getText", new Class[]{}));
			ITEM_GET_VALUE_METHOD_MAP.put(JComboBox.class, JComboBox.class.getMethod("getSelectedItem", new Class[]{}));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Map<Class<?>, Map<String, ComponentMethodPair>> SHEET_MAP_TABLES = new HashMap<Class<?>, Map<String, ComponentMethodPair>>();

	protected IExecutable<?> sheet;
	
	
	public AbstractSheetForm(IExecutable<?> sheet) throws Exception {
		Class<?> c = sheet.getClass();
		
		if (! c.isAnnotationPresent(FormBean.class)) {
			throw new RuntimeException("Class not annotated : "+c.toString());
		}
		
		this.sheet = sheet;
		
		FormBean formBeanAnnotation = c.getAnnotation(FormBean.class);
		Class<?> formBeanClass = formBeanAnnotation.value();
		
		Map<String, ComponentMethodPair> methodPairs = getSheetComponentGetters(sheet);
		Map<String, Method> setters = ObjectCloneFactory.getSetterMap(formBeanClass);
		
		// Copy values to form from sheet using getter and setter
		for (String fieldName : setters.keySet()) {
			// Check if the field exists in both sheet and form 
			ComponentMethodPair methodPair = methodPairs.get(fieldName);
			if (methodPair == null) continue;
			Method setter = setters.get(fieldName);
			
			Object value = methodPair.getter.invoke(methodPair.component, new Object[]{});
			setter.invoke(this, value);
		}
	}

	/**
	 * Build a new component-method map table for annotated components on the sheet
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private static Map<String, ComponentMethodPair> buildSheetMapTable(IExecutable<?> sheet) throws Exception {
		Map<String, ComponentMethodPair> mapTable = new HashMap<String, ComponentMethodPair>();
		
		Field[] fields = sheet.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (! field.isAnnotationPresent(FormField.class)) continue;
			FormField annotation = field.getAnnotation(FormField.class);
			field.setAccessible(true);
			
			Object component = field.get(sheet);

			Method getValueMethod = ITEM_GET_VALUE_METHOD_MAP.get(component.getClass());
			
			if (getValueMethod == null) throw new NullPointerException("Type unsupported at class: "+component);
			
			mapTable.put(annotation.value(), new ComponentMethodPair(component, getValueMethod));
		}
		
		return mapTable;
	}
	
	/**
	 * @return the map table of getters of sheet components 
	 * @throws Exception 
	 */
	public static Map<String, ComponentMethodPair> getSheetComponentGetters(IExecutable<?> sheet) throws Exception {
		Class<?> c = sheet.getClass();
		Map<String, ComponentMethodPair> mapTable = SHEET_MAP_TABLES.get(c);
		
		if (mapTable == null) {
			mapTable = buildSheetMapTable(sheet);
			
			SHEET_MAP_TABLES.put(c, mapTable);
		}
		
		return mapTable;
	}

	@Override
	public abstract void validate(KeeperFunction function) throws Exception;
	
}

/**
 * This brief Pair class only serves for the old architecture that directly access the components of the sheet
 * @author Peter2_Weng
 *
 */
class ComponentMethodPair {
	public Object component;
	public Method getter;
	public Method setter;
	
	public ComponentMethodPair(Object component, Method getter) {
		this.component = component;
		this.getter = getter;
	}

	public ComponentMethodPair(Object component, Method getter, Method setter) {
		this(component, getter);
		this.setter = setter;
	}
}
