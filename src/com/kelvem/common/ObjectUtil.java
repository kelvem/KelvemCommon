/**
 * 
 */
package com.kelvem.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import com.kelvem.common.database.model.TableModel;

/**
 * @author kelvem
 *
 */
public class ObjectUtil {

	private ObjectUtil(){
		// do nothing
	}
	
	public static String toString(Object obj){
		
		if (obj == null) {
			return "null";
		}
		
		if (obj.getClass().getSimpleName().equals("Boolean")
				|| obj.getClass().getSimpleName().equals("String")
				|| obj.getClass().getSimpleName().equals("Integer")
				|| obj.getClass().getSimpleName().equals("Long")
				|| obj.getClass().getSimpleName().equals("Double")
				|| obj.getClass().getSimpleName().equals("BigDecimal") ) {
			return obj.toString();
		}
		
		HashSet<Object> set = new HashSet<Object>();
		return toString(obj, set);
	}
	
	private static String toString(Object obj, HashSet<Object> set){
		
		if (obj == null) {
			return "null";
		}
		
		set.add(obj);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(obj.getClass().getSimpleName());
		sb.append(":{"); 

		if (obj.getClass().getSimpleName().equals("Boolean")
				|| obj.getClass().getSimpleName().equals("String")
				|| obj.getClass().getSimpleName().equals("Integer")
				|| obj.getClass().getSimpleName().equals("Long")
				|| obj.getClass().getSimpleName().equals("Double")
				|| obj.getClass().getSimpleName().equals("BigDecimal") ) {
			return obj.toString();
		}
		
		Map<String, Object> mapField = ReflectUtil.getFieldValueMap(obj);
		for (String fieldName : mapField.keySet()) {
			
			sb.append("'" + fieldName + "'");
			sb.append(":");
			if (set.contains(mapField.get(fieldName))) {
				sb.append("(...)");
			} else {
				set.add(mapField.get(fieldName));
				sb.append(toString(mapField.get(fieldName), set));
			}
			sb.append(", ");
		}

		sb.append("}");
		return sb.toString();
	}
	
	public static String getObjectInfo(Object obj){
		
		if (obj == null) {
			return "null";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[Field]");
		sb.append(toString(obj));
		sb.append("\n");
		
		sb.append("[Method]");
		
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {

			if ("notify".equalsIgnoreCase(method.getName())
					|| "notifyAll".equalsIgnoreCase(method.getName())
					|| "clear".equalsIgnoreCase(method.getName())
					|| method.getName().startsWith("set")
					|| "void".equalsIgnoreCase(method.getReturnType().toString())) {
				continue;
			}
			
			sb.append(method.getName());
			sb.append("(");
			Class<?>[] paramTypes = method.getParameterTypes();
			
			int paramSize = paramTypes.length;
			Object[] params = new Object[paramSize];
//				for (Class<?> clazz : paramTypes) {
			for (int i = 0; i < paramTypes.length; i++) {
				Class<?> clazz = paramTypes[i];
						
				sb.append(clazz.getSimpleName());
				sb.append(",");
				
				if (clazz.getSimpleName().equals("Integer")
						|| clazz.getSimpleName().equals("Long")
						|| clazz.getSimpleName().equals("Double") ) {
					params[i] = 1;
				} else if(clazz.getSimpleName().equals("String")) {
					params[i] = "1";
				} else if(clazz.getSimpleName().equals("BigDecimal")) {
					params[i] = new BigDecimal(1);
				} else if(clazz.getSimpleName().equals("Boolean")) {
					params[i] = false;
				} else {
					params[i] = null;
				}
			}
			sb.append(")");
			
			sb.append(" -> (");
			Object ret = null;
			try {
				if (paramSize <= 0) {
					ret = method.invoke(obj);
				} else {
					ret = method.invoke(obj, params);
				}
			} catch (Exception e) {
				ret = e.getClass().getSimpleName() + " : " + e.getMessage();
			}
			sb.append(toString(ret));
			sb.append(")");
			sb.append("\n");
				
		}
		
		return sb.toString();
	}
	
	public static String toPrintArray(Object[] arr){
		StringBuilder sb = new StringBuilder();
		for (Object obj : arr) {
			sb.append(obj + "\t");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		TableModel table = new TableModel();
//		System.out.println(toString(table));
		System.out.println(getObjectInfo(table));
		
	}
}
