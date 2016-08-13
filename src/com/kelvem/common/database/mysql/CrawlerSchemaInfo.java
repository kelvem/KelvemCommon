/**
 * 
 */
package com.kelvem.common.database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelvem.common.KLog;
import com.kelvem.common.database.base.DataBaseSession;
import com.kelvem.common.database.model.ColumnModel;
import com.kelvem.common.database.model.SchemaModel;
import com.kelvem.common.database.model.TableModel;

/**
 * @author kelvem
 *
 */
public class CrawlerSchemaInfo {

	
//	private String debugSql = " and TABLE_NAME='products_cms_online_trans_bing'";
	private String debugSql = "";
	
	private DataBaseSession db = null;
	
	private SchemaModel schema = null;
	
	private ResultSet rsTable = null;
	private ResultSet rsColumn = null;
	private ResultSet rs = null;
	
	public CrawlerSchemaInfo(String SchemaName, String TableName){
		db = new MysqlSession();
		db.open();
		
		schema = new SchemaModel(SchemaName);
		String TableNameAnd = " ";
		if (TableName != null) {
			TableNameAnd = " and table_name = '" + TableName + "' ";
		}
		
		// 查询表结构
		String sql = "select TABLE_SCHEMA, TABLE_NAME, ENGINE, AUTO_INCREMENT, TABLE_COMMENT " +
				" from information_schema.tables t " +
				" where t.table_schema = '" + SchemaName + "'  " + " and t.ENGINE is not null " + 
				TableNameAnd + debugSql + 
				" order by TABLE_SCHEMA, TABLE_NAME;";
		KLog.info("查询表结构用SQL");
		KLog.info(sql);
		rsTable = db.query(sql);
		
		// 查询列结构
		sql = "select  TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, COLUMN_COMMENT, COLUMN_TYPE, " +
				" COLUMN_DEFAULT, IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, COLUMN_KEY, EXTRA, " +
				" case when DATA_TYPE=COLUMN_TYPE then null " +
				"	when CHARACTER_MAXIMUM_LENGTH is not null then CHARACTER_MAXIMUM_LENGTH " +
				"	when NUMERIC_PRECISION is not null then NUMERIC_PRECISION " +
				"	else null end as SIZE " +
				" from information_schema.columns c where c.table_schema = '" + SchemaName + "' " + 
				TableNameAnd + debugSql + 
				" order by TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION";
		KLog.info("查询列结构用SQL");
		KLog.info(sql);
		rsColumn = db.query(sql);
	}
	
	public CrawlerSchemaInfo(String SchemaName){
		this(SchemaName, null);
	}
	
	public SchemaModel getSchemaMeta(){
		this.getBasicInfo();
		this.getPKInfo();
		this.getIndexInfo();
		this.getTriggerInfo();
		this.getCreateTableSql();
		
		return this.schema;
	}

	private void getBasicInfo(){
		
		try {
//			CREATE TABLE `user_info` (
//			  `user_id` int(11) NOT NULL AUTO_INCREMENT,
//			  `user_info_id` int(11) NOT NULL,
//			  `age` int(11) DEFAULT NULL,
//			  PRIMARY KEY (`user_id`)
//			) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8$$
			
			// 处理结果集里面的数据 
			rsTable.beforeFirst();
			while(rsTable.next()){

				TableModel table = new TableModel(this.schema);
				table.Schema = rsTable.getString("TABLE_SCHEMA");
				table.TableName = rsTable.getString("TABLE_NAME");
				table.TableDesc = rsTable.getString("TABLE_COMMENT");
				table.Engine = rsTable.getString("ENGINE");
				table.AutoIncrementIndex = rsTable.getBigDecimal("AUTO_INCREMENT");
				
				schema.putTable(table);
			}

			// 处理结果集里面的数据 
			rsColumn.beforeFirst();
			while(rsColumn.next()){

				String TableName = rsColumn.getString("TABLE_NAME");
				
				if (schema.contains(TableName) == false) {
					continue;
				}

				TableModel table = schema.getTable(TableName);
				
				if ("PRI".equalsIgnoreCase(rsColumn.getString("COLUMN_KEY")) == true) {
					continue;
				}
				
				ColumnModel column = new ColumnModel(table);
				column.ColumnName = rsColumn.getString("COLUMN_NAME");
				column.ColumnType = rsColumn.getString("COLUMN_TYPE");
				column.DataType = rsColumn.getString("DATA_TYPE");
				column.ColumnDefault = rsColumn.getString("COLUMN_DEFAULT");
				column.isNullEnable = rsColumn.getBoolean("IS_NULLABLE");
				column.ColumnDesc = rsColumn.getString("COLUMN_COMMENT");
				column.DataSize = rsColumn.getInt("SIZE");
				column.Position = rsColumn.getInt("ORDINAL_POSITION");
				column.ColumnTypeID = ColumnModel.parseColumnCode(column.DataType);
				
				// ###&&&
//				if (new BigDecimal(10).compareTo(column.ColumnSize) == 0) {
//					column.ColumnSize = new BigDecimal(11);
//				}
				
				if (rsColumn.getString("EXTRA").indexOf("auto_increment") >= 0) {
					column.isAutoIncrement = true;
				}
				
				table.listColumn.add(column);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getPKInfo(){
		try {

			// 处理结果集里面的数据 
			rsColumn.beforeFirst();
			while(rsColumn.next()){

				String TableName = rsColumn.getString("TABLE_NAME");
				
				if (schema.contains(TableName) == false) {
					continue;
				}

				TableModel table = schema.getTable(TableName);
				
				if ("PRI".equalsIgnoreCase(rsColumn.getString("COLUMN_KEY")) == false) {
					continue;
				}
				
				ColumnModel column = new ColumnModel(table);
				column.ColumnName = rsColumn.getString("COLUMN_NAME");
				column.ColumnType = rsColumn.getString("COLUMN_TYPE");
				column.DataType = rsColumn.getString("DATA_TYPE");
				column.ColumnDefault = rsColumn.getString("COLUMN_DEFAULT");
				column.isNullEnable = rsColumn.getBoolean("IS_NULLABLE");
				column.ColumnDesc = rsColumn.getString("COLUMN_COMMENT");
				column.DataSize = rsColumn.getInt("SIZE");
				column.Position = rsColumn.getInt("ORDINAL_POSITION");
				column.ColumnTypeID = ColumnModel.parseColumnCode(column.DataType);
				
				if (rsColumn.getString("EXTRA").indexOf("auto_increment") >= 0) {
					column.isAutoIncrement = true;
				}
				
				table.listPK.add(column);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void getIndexInfo(){
		
	}

	private void getTriggerInfo(){
		
	}
	
	/**
	 * 生成基础数据
	 * 
	 * @param limit
	 */
	public void getTableDataBasic(int limit){
		
		if (this.schema == null) {
			this.getSchemaMeta();
		}
		
		for (TableModel table : schema.getTables()) {
			// 生成数据, 并添加到Table.mapData中
			this.getData(table, limit, "");
		}
	}

	/**
	 * 生成类别数据
	 * 
	 * @param Schema
	 * @param TableName
	 * @param ColumnName
	 * @param limit
	 */
	public void getTableDataType(String Schema, String TableName, String ColumnName, int limit){
		
		if (Schema == null || TableName == null || ColumnName == null) {
			return;
		}
		if (this.schema == null) {
			this.getSchemaMeta();
		}
		
		// 获取关键表
		TableModel table = getTable(Schema, TableName);
		if (table == null) {
			return;
		}
		
		try {
			// 获取类型
			String sql = "select distinct `" + ColumnName + "` from `" + table.Schema + "`.`" + table.TableName + "` t; ";
			KLog.info("getTableDataType用sql(" + ColumnName + ")");
			KLog.debug(sql);
			ResultSet rsType = db.query(sql);
			
			// 查询数据
			while(rsType.next()){
				if (rsType.getString(1) != null && rsType.getString(1).indexOf("'") < 0) {
					this.getData(table, limit, " where `" + ColumnName + "` = '" + rsType.getString(1) + "' ");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成关联数据
	 * 
	 * @param Schema
	 * @param TableName
	 * @param ColumnName
	 * @param limit
	 */
	public void getTableDataRef(String Schema, String TableName, String ColumnName, int limit){
		
		if (Schema == null || TableName == null || ColumnName == null) {
			return;
		}
		if (this.schema == null) {
			this.getSchemaMeta();
		}
		
		// 获取关键表
		TableModel keyTable = getTable(Schema, TableName);
		if (keyTable == null) {
			return;
		}
		
		// 防止没有数据,额外执行一次
		// 生成数据, 并添加到Table.mapData中
		this.getData(keyTable, limit, "");
		if (keyTable.mapData.size() <= 0) {
			return;
		}
		
		// 生成in的数据
		StringBuilder sb = new StringBuilder();
		int index = keyTable.getColumnIndex(ColumnName);
		for (String[] row : keyTable.mapData.values()) {
			sb.append(",'" + row[index] + "'");
		}
		String inData = sb.toString().substring(1);
		
		KLog.info("getTableData用sql(" + ColumnName + ")");
		KLog.info("需要抽取数量" + keyTable.mapData.values().size());
		KLog.debug("inData = (" + inData + ")");
		
		// 遍历表抽取数据
		for (TableModel table : schema.getTables()) {
			
			if (table == keyTable) {
				continue;
			}
			KLog.debug("正在处理表" + table.TableName);
			
			// 判断是否有该字段
			boolean flag = false;
			for (ColumnModel column : table.getAllColumn()) {
				if (ColumnName != null && ColumnName.equalsIgnoreCase(column.ColumnName)) {
					flag = true;
					continue;
				}	
			}
			if (flag == false) {
				continue;
			}
			
			// 生成数据, 并添加到Table.mapData中
			this.getData(table, -1, " where `" + ColumnName + "` in (" + inData + "); ");
			
		}
	}
	
	/**
	 * 生成数据, 并添加到Table.mapData中
	 * 
	 * @param table
	 * @param limit
	 * @param where
	 */
	private void getData(TableModel table, int limit, String where){
		try {
			String PK = "concat('-'";
			for (ColumnModel column : table.listPK) {
				PK += ", " + column.ColumnName + ", '-'";
			}
			PK += "), ";

			String sql = "select " + PK + "t.* from `" + table.Schema + "`.`" + table.TableName + "` t ";
			if (where != null && where.length() > 0) {
				sql += " " + where + " ";
			}
			if (limit > 0) {
				sql += " limit " + limit;
			}
			ResultSet rs = db.query(sql);
			
			while(rs.next()){
				int colSize = rs.getMetaData().getColumnCount();
				String[] arr = new String[colSize - 1];
				for (int i = 1; i < colSize; i++) {
					arr[i-1] = rs.getString(i + 1);
				}
				
				String key = rs.getString(1);
				if (!table.mapData.containsKey(key)) {
					table.mapData.put(key, arr);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	private void getCreateTableSql() {
		for (TableModel table : schema.getTables()) {

			try {
				String sql = "show create table `" + table.Schema + "`.`" + table.TableName + "`;\r\n";
				KLog.info("查询创建表的SQL通过 " + sql);
				rs = db.query(sql);
				if (rs.next() == true) {
					String sqlCreateTable = rs.getString(2);
					table.sqlCreateTable = sqlCreateTable + ";\r\n";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public TableModel getTable(String Schema, String TableName){
		
		if (Schema == null || TableName == null) {
			return null;
		}
		if (this.schema == null) {
			this.getSchemaMeta();
		}
		
		// 获取关键表
		TableModel keyTable = null;
		for (TableModel table : schema.getTables()) {
			if (Schema.equalsIgnoreCase(table.Schema)
					&& TableName.equalsIgnoreCase(table.TableName)) {
				keyTable = table;
				continue;
			}
		}
		
		return keyTable;
	}

}
