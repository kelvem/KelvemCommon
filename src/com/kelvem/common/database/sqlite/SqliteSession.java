package com.kelvem.common.database.sqlite;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.kelvem.common.database.base.DataBaseSession;

public class SqliteSession extends DataBaseSession {
	
	@Override
	public void open() {

		try {
			if (connection != null && connection.isClosed() == false) {
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// read property
		if (this.property == null) {
			throw new RuntimeException("[Error] Property Fail, check the config");
		}
		
		// "jdbc:sqlite:test.db";    
		String url = "jdbc:sqlite:" + property.getSchemaName() + ".db";

		try {

			// 加载驱动程序
			Class.forName(property.getDriver());

		} catch (Exception e) {
			throw new RuntimeException("[Error] Property'Driver not Correct # " + property.getDriver(), e);
		}

		try {

			// 连续数据库
			connection = DriverManager.getConnection(url, property.getUserName(), property.getPassword());

			if (connection.isClosed() == false) {
				// System.out.println("Succeeded connecting to the Database!");
			}

		} catch (Exception e) {
			throw new RuntimeException("[Error] Connection can't be create # " + url, e);
		}
	}

	@Override
	public void close() {

		try {
			
			if (connection == null) {
				return;
			}
			
			if (connection.isClosed() == false){
				connection.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
	}

	@Override
	public ResultSet query(String sql) {

//		System.out.println(sql);
		try {
			
			if (connection == null || connection.isClosed() == true) {
				open();
			}
			
			// 获取表达式  
			Statement stmt = connection.createStatement();  
			
			// 执行 SQL  
			return stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			System.out.println(sql);
			throw new RuntimeException(e);
		}
	}

	@Override
	public int execute(String sql) {

		try {
			
			if (connection == null || connection.isClosed() == true) {
				open();
			}
			
			// 获取表达式  
			Statement stmt = connection.createStatement();  
			
			// 执行插入数据的 SQL  
			return stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			System.out.println(sql);
//			e.printStackTrace();
//			return -1;
			throw new RuntimeException(e);
		}
	}

	@Override
	public int[] executeBatch(List<String> listSql) {

		try {
			
			if (connection == null || connection.isClosed() == true) {
				open();
			}
			
			// 获取表达式  
			Statement stmt = connection.createStatement();  
			
			for (String sql : listSql) {
				stmt.addBatch(sql);
			}
			// 执行插入数据的 SQL
			return stmt.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getSessionType() {
		return "sqlite";
	}
	
	

}
