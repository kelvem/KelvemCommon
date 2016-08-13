/**
 * 
 */
package com.kelvem.common;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author kelvem
 *
 */
public class StringUtil {

	private StringUtil(){
		// do nothing
	}
	
	public static String valueOf(InputStream is){
		
		try {
			int i = -1;
			byte[] b = new byte[1024 * 100];
			StringBuffer sb = new StringBuffer();
			while ((i = is.read(b)) != -1) {
				sb.append(new String(b, 0, i));
			}
			String content = sb.toString();
			return content;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 拼接某属性的 get方法
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String fieldGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	/**
	 * 拼接在某属性的 set方法
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String fieldSetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}
	
	// columnModel -> COLUMN_MODEL
	public static String fieldToColumnName(String name) { 
		
		if ( name == null || name.isEmpty()){
    		return name;
    	}
		
        StringBuffer buf = new StringBuffer(name.replace('.', '_')); 
        for (int i = 1; i < buf.length() - 1; i++) { 
            if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) { 
                buf.insert(i++, '_'); 
            } 
        } 
        return buf.toString().toUpperCase(); 
    } 
	
	// COLUMN_MODEL -> columnModel
	public static String columnNameToField(String name) { 
		
    	if ( name == null || name.isEmpty()){
    		return name;
    	} 
    	
    	StringBuffer buf = new StringBuffer("");
        
        String[] fields = name.replace('.', '_').split("_");
        
        for (String field : fields) {
        	if ( field.length() > 1 ){
        		field = field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
        	} else {
        		field = field.toUpperCase();
        	}
			buf.append(field);
		}

        String first = String.valueOf(buf.charAt(0)).toLowerCase();
        buf.setCharAt(0, first.charAt(0));

        return buf.toString(); 
    }  
	
	// TABLE_MODEL -> ClassModel
	public static String tableNameToClass(String name) { 
		
    	if ( name == null || name.isEmpty()){
    		return name;
    	} 
    	
    	StringBuffer buf = new StringBuffer("");
        
        String[] fields = name.replace('.', '_').split("_");
        
        for (String field : fields) {
        	if ( field.length() > 1 ){
        		field = field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
        	} else {
        		field = field.toUpperCase();
        	}
			buf.append(field);
		}

        return buf.toString(); 
    }  
	
	// TABLE_MODEL -> ClassVO
	public static String tableNameToVoClass(String name) { 
		
    	if ( name == null || name.isEmpty()){
    		return name;
    	} 
    	if ( name.toUpperCase().startsWith("B_") || name.toUpperCase().startsWith("C_")){
    		name = name.substring(2); 
    	}       
        
        return tableNameToClass(name); 
    } 
	
	public static String toHTMLString(String in)
	{   
		if (in == null || in.length() <= 0) {
			return "";
		}
		
		StringBuffer out = new StringBuffer();   
		for( int i = 0; i < in.length(); i++)
		{   
        	char c   =   in.charAt(i);   
        	if(c == '\'')  
        		out.append("'");  
        	else if (c == '\"')  
        		out.append("");  
        	else if (c == '<')  
        		out.append("&lt;");  
        	else if (c == '>')  
        		out.append("&gt;");  
        	else if (c == '&')  
        		out.append("&amp;");  
        	else if (c == ' ')  
        		out.append("&nbsp;");  
        	else if (c == '\n')  
        		out.append("<br>");  
        	else if( c == '\r' )
        		continue;
        	else
        		out.append(c);  
		}
		return  out.toString();
	}

	
	/**
	 * <p>Aaa -> aaa</p>
	 * 
	 * @param name
	 * @return String
	 * @see
	 */
	public static String toLowerFirstChar(String str) {
		if ( str == null || str.isEmpty()){
			return str;
		} 
		
		if ( str.length() > 1 ){
			str = str.substring(0, 1).toLowerCase() + str.substring(1);
		} else {
			str = str.toLowerCase();
		}
		
		return str;
	}
	
	/**
	 * <p>aaa -> Aaa</p>
	 * 
	 * @param name
	 * @return String
	 * @see
	 */
	public static String toUpperFirstChar(String str) {
		if ( str == null || str.isEmpty()){
			return str;
		} 
		
		if ( str.length() > 1 ){
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			str = str.toUpperCase();
		}
		
		return str;
	}
	
	/**
	 * <p>AAA_AAA -> AaaAaa</p>
	 * 
	 * @param name
	 * @return String
	 * @see
	 */
	public static String AAA_AAAToAaaAaa(String name) { 
		
		if ( name == null || name.isEmpty()){
			return name;
		} 
		
		if ( name.toLowerCase().startsWith("b_") || name.toLowerCase().startsWith("c_") || name.toLowerCase().startsWith("t_")){
			name = name.substring(2);
		} else if (name.toLowerCase().startsWith("khj_")) {
			name = name.substring(4);
		}
		
		name = name.toLowerCase();
		
		StringBuffer buf = new StringBuffer("");
		
		String[] fields = name.replace('.', '_').split("_");
		
		for (String field : fields) {
			buf.append(toUpperFirstChar(field));
		}

		return buf.toString(); 
	}
	
	/**
	 * <p>AAA_AAA -> aaaAaa</p>
	 * 
	 * @param name
	 * @return String
	 * @see
	 */
	public static String AAA_AAAToaaaAaa(String name) { 
		
		String buf = AAA_AAAToAaaAaa(name);

		return toLowerFirstChar(buf); 
	}
	
	/**
	 * <p>aaaAaa -> aaa_aaa</p>
	 * 
	 * @param name
	 * @return String
	 * @see
	 */
	public static String aaaAaaToaaa_aaa(String name) { 
		
		if ( name == null || name.isEmpty()){
			return name;
		} 
		StringBuilder sb = new StringBuilder();
		
		name = StringUtil.toLowerFirstChar(name);
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (c >= 'A' && c <= 'Z') {
				sb.append("_");
			}
			sb.append(c);
		}

		return sb.toString().toLowerCase(); 
	}
}
