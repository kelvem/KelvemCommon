package com.kelvem.common.database.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kelvem.common.PropertiesUtil;

public abstract class DataBaseSession {

	public DataBaseSession(){
		this.property = readProperty();
	}
	
	public DataBaseSession(ConnectionProperty property){
		this.property = property;
	}
	
	public ConnectionProperty property = null;
	
	public Connection connection = null;
	
	public abstract void open();
	
	public abstract void close();
	
	public ConnectionProperty readProperty(){

		Properties config = PropertiesUtil.readProperties("database.properties");
		ConnectionProperty property = new ConnectionProperty();
		property.setDriver(config.getProperty("Driver"));
		property.setIp(config.getProperty("Ip"));
		property.setPort(config.getProperty("Port"));
		property.setSchemaName(config.getProperty("SchemaName"));
		property.setUserName(config.getProperty("UserName"));
		property.setPassword(config.getProperty("Password"));
		
		return property;
	}
	
	public abstract ResultSet query(String sql);
	
	public List<Object[]> queryObject(String sql) {

//		System.out.println(sql);
		try {
			
			ResultSet rs = query(sql);

			List<Object[]> result = new ArrayList<Object[]>();
			int colCnt = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				Object[] row = new Object[colCnt];
				for (int i = 0; i < colCnt; i++) {
					row[i] = rs.getObject(i+1);
				}
				result.add(row);
			}
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String[]> queryString(String sql) {

//		System.out.println(sql);
		try {
			
			ResultSet rs = query(sql);

			List<String[]> result = new ArrayList<String[]>();
			int colCnt = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				String[] row = new String[colCnt];
				for (int i = 0; i < colCnt; i++) {
					if (rs.getObject(i+1) == null) {
						row[i] = "null";
					} else {
						row[i] = rs.getObject(i+1).toString();
					}
				}
				result.add(row);
			}
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String queryScalar(String sql) {

//		System.out.println(sql);
		try {
			
			List<String[]> result = queryString(sql);
			
			if (result == null || result.size() <= 0 || result.get(0).length <= 0) {
				return null;
			} else {
				return  result.get(0)[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int count(String sql) {

//		System.out.println(sql);
		try {
			
			String result = queryScalar(sql);

			int count = Integer.valueOf(result);
			
			return count;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public abstract int execute(String sql);

	public abstract int[] executeBatch(List<String> listSql);
	
	public abstract String getSessionType();
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public ConnectionProperty getProperty() {
		return property;
	}

	public void setProperty(ConnectionProperty property) {
		this.property = property;
	}

	
}
