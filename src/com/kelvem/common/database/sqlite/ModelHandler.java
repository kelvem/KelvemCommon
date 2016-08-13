package com.kelvem.common.database.sqlite;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kelvem.common.StringUtil;
import com.kelvem.common.database.base.DataBaseSession;

public class ModelHandler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	protected static String createTableSql(Class<?> clazz) {
		
		Map<String, String> columnMap = new LinkedHashMap<String, String>();
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			String columnName = StringUtil.aaaAaaToaaa_aaa(fieldName);

			String fieldType = field.getType().getSimpleName();
			String columnType = "";
			
			if ("String".equalsIgnoreCase(fieldType)) {
				columnType = "text";
			} else if ("Integer".equalsIgnoreCase(fieldType)) {
				columnType = "integer";
			} else if ("Date".equalsIgnoreCase(fieldType)) {
				columnType = "date";
			} else {
				throw new RuntimeException("未识别的类成员类型 ：" + fieldType);
			}
			
			columnMap.put(columnName, columnType);
		}

		String className = clazz.getSimpleName();
		String tableName = StringUtil.aaaAaaToaaa_aaa(className);
		if (tableName.endsWith("_model")) {
			tableName = tableName.substring(0, tableName.length() - "_model".length());
		}
		
		String primaryKey = tableName + "_id";
		if (!columnMap.containsKey(primaryKey)) {
			throw new RuntimeException("class[" + className + "] 缺少主键字段 " + primaryKey);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(tableName);
		sb.append("(\r\n");
		
		boolean isFirst = true;
		for (String key : columnMap.keySet()) {
			if (isFirst == true) {
				isFirst = false;
			} else {
				sb.append(",\r\n");
			}
			sb.append("\t").append(key).append(" ").append(columnMap.get(key));

			if (key.equalsIgnoreCase(primaryKey)) {
				sb.append(" primary key autoincrement");
			}
		}

		sb.append(")");
		
		String sql = sb.toString();
		System.out.println(sql);
		
		return sql;
	}
	
	public static void createTable(Class<?> clazz) {
		
		System.out.println("根据Model创建表 ： " + clazz.getSimpleName());
		String sql = createTableSql(clazz);

		DataBaseSession session = new SqliteSession();
		session.open();
		session.execute(sql);
		session.close();
	}
	
	protected static String dropTableSql(Class<?> clazz) {
		
		String className = clazz.getSimpleName();
		String tableName = StringUtil.aaaAaaToaaa_aaa(className);
		if (tableName.endsWith("_model")) {
			tableName = tableName.substring(0, tableName.length() - "_model".length());
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("drop table ").append(tableName);

		String sql = sb.toString();
		System.out.println(sql);
		
		return sql;
	}
	
	public static boolean dropTable(Class<?> clazz) {
		
		System.out.println("根据Model删除表 ： " + clazz.getSimpleName());
		String sql = dropTableSql(clazz);

		try {
			DataBaseSession session = new SqliteSession();
			session.open();
			session.execute(sql);
			session.close();
			
			return false;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
