/**
 * 
 */
package com.kelvem.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kelvem
 * 
 */
public class RegxUtil {

	private RegxUtil() {

	}
	
	public static List<String> matchSingle(String content, String pattern) {

		List<String> result = new ArrayList<String>();

		Matcher match = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(content);
		if(match.find()){
			for (int i = 0; i < match.groupCount() + 1; i++) {
				String buf = match.group(i);
				result.add(buf);
			}
		}

		return result;
	}

	public static boolean contain(String content, String pattern) {
		
		Matcher match = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(content);
		
		if (match.find()){
			return true;
		} else {
			return false;
		}
	}

	public static List<String> match(String content, String pattern, int groupId) {
		
		List<String> result = new ArrayList<String>();

		Matcher match = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(content);
		while(match.find()){
			String buf = match.group(groupId);
			result.add(buf);
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<String> list = null;
		String pattern = "(\\#\\[([^\\]]*)\\]\\[([^\\]]*)\\])[^\\[]?+";
		int groupId = 1;
		String content = 
" " +
"#[table.listColumn][							<th>{ColumnDesc}</th>\r\n] t" +
"#[table.listColumn][							<td>${row.{Name$aaaAaa}}</td>\r\n] " +
" " +
"							<td>";

		
		list = RegxUtil.match(content, pattern, groupId);
		System.out.println(list.size());
		for (String string : list) {
			System.out.println(string);
		}

//		list = RegxUtil.match("#[table.listColumn][							<td>${row.{Name$aaaAaa}}</td>\r\n]\r\n", pattern, groupId);
//		System.out.println(list.size());
//		for (String string : list) {
//			System.out.println(string);
//		}
	}
}
