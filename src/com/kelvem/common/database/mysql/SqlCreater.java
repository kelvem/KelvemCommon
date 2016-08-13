/**
 * 
 */
package com.kelvem.common.database.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kelvem.common.database.model.ColumnModel;
import com.kelvem.common.database.model.TableModel;


/**
 * @author kelvem
 *
 */
public class SqlCreater {
	
	private SqlCreater(){
	}
	
	public static String getCreateTableSQL(TableModel table){
//		CREATE TABLE `user_info` (
//		  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
//		  `user_info_id` int(11) NOT NULL,
//		  `age` int(11) DEFAULT NULL,
//		  PRIMARY KEY (`user_id`)
//		) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8$$
		
		StringBuilder sb = new StringBuilder();
		sb.append("-- " + table.TableDesc + "\r\n");
		sb.append("CREATE TABLE `" + table.Schema + "`.`" + table.TableName + "` (");
		sb.append("\r\n");
		
		for (int i = 0; i < table.listPK.size(); i++) {
			ColumnModel column = table.listPK.get(i);

			sb.append("`" + column.ColumnName + "` ");
			sb.append(column.ColumnType + " ");

			sb.append("NOT NULL ");
			if (column.ColumnDefault != null && !column.ColumnDefault.equalsIgnoreCase("null")) {
				if (column.ColumnDefault.equalsIgnoreCase("CURRENT_TIMESTAMP")) {
					sb.append("DEFAULT " + column.ColumnDefault + " ");
				} else {
					sb.append("DEFAULT '" + column.ColumnDefault + "' ");
				}
			}
			
			if (column.isAutoIncrement == true) {
				sb.append("AUTO_INCREMENT ");
			}
			if (column.ColumnDesc != null && column.ColumnDesc.length() > 0) {
				sb.append("COMMENT '" + column.ColumnDesc + "'");
			}
			sb.append(", ");
			sb.append("\r\n");	
		}
		for (int i = 0; i < table.listColumn.size(); i++) {
			ColumnModel column = table.listColumn.get(i);

			sb.append("`" + column.ColumnName + "` ");
			sb.append(column.ColumnType + " ");
			
			if (column.isNullEnable == false) {
				sb.append("NOT NULL ");
			}
			if (column.isNullEnable == true && column.ColumnDefault != null && !column.ColumnDefault.equalsIgnoreCase("null")) {
				if (column.ColumnDefault.equalsIgnoreCase("CURRENT_TIMESTAMP")) {
					sb.append("DEFAULT " + column.ColumnDefault + " ");
				} else {
					sb.append("DEFAULT '" + column.ColumnDefault + "' ");
				}
			}
			
			if (column.isAutoIncrement == true) {
				sb.append("AUTO_INCREMENT ");
			}
			if (column.ColumnDesc != null && column.ColumnDesc.length() > 0) {
				sb.append("COMMENT '" + column.ColumnDesc + "'");
			}
			if (i + 1 != table.listColumn.size() || table.listPK.size() > 0) {
				sb.append(", ");
			}
			sb.append("\r\n");	
		}

		if (table.listPK.size() > 0) {
			sb.append("PRIMARY KEY (`" + table.listPK.get(0).ColumnName + "`");
			for (int i = 1; i < table.listPK.size(); i++) {
				ColumnModel column = table.listPK.get(i);
				sb.append(", `" + column.ColumnName + "` ");
			}
			sb.append(")");
			sb.append("\r\n");
		}
		
		sb.append(") ENGINE=" + table.Engine + " ");
		if (table.AutoIncrementIndex != null) {
			sb.append("AUTO_INCREMENT=" + table.AutoIncrementIndex + " ");
		}
		sb.append("DEFAULT CHARSET=utf8 ");
		if (table.TableDesc != null && table.TableDesc.length() > 0) {
			sb.append("COMMENT='" + table.TableDesc + "' ");
		}
		sb.append(";\r\n");
		
		return sb.toString();
	}
	
	public static String getDropTableSQL(TableModel table){
		
		return "DROP TABLE `" + table.Schema + "`.`" + table.TableName + "`;\r\n";
	}
	
	public static String getDeleteDataSQL(TableModel table){
		
		return "DELETE FROM `" + table.Schema + "`.`" + table.TableName + "`;\r\n";
	}
	
	
	
	/**
	 * Insert Data
	 */
	public static String getInsertDataSQL(TableModel table, List<String[]> data){
//		INSERT INTO `blog`.`user`
//		(`user_id`,
//		`user_name`,
//		`last_login_time`,
//		`user_info_id`)
//		VALUES
//		(
//		<{user_id: 3}>,
//		<{user_name: }>,
//		<{last_login_time: }>,
//		<{user_info_id: }>
//		);
		
		String CR = "";
		// 生成sql前缀
		StringBuilder sbBase = new StringBuilder();
		sbBase.append("INSERT INTO `" + table.Schema + "`.`" + table.TableName + "` (");
		sbBase.append(CR);
		
		for (int i = 0; i < table.getAllColumn().size(); i++) {
			ColumnModel column = table.getAllColumn().get(i);
			sbBase.append("`" + column.ColumnName + "`");
			if (i + 1 != table.getAllColumn().size()) {
				sbBase.append(",");
			}
			sbBase.append(CR);
		}
		sbBase.append(") VALUES ( " + CR);
		String base = sbBase.toString();

		// 生成insert语句
		StringBuilder sb = new StringBuilder();
		sb.append("-- " + table.TableDesc + " " + table.Schema + "." + table.TableName + "\r\n");
		for (String[] row : data) {
			sb.append(base);
//			for (int i = 0; i < row.length; i++) {
//				if (row[i] == null) {
//					sb.append(row[i]);
//				} else {
//					sb.append("'" + row[i] + "'");
//				}
//				if (i + 1 != row.length) {
//					sb.append(",");
//				}
//				sb.append(CR);
//			}

			for (int i = 0; i < table.getAllColumn().size(); i++) {
				ColumnModel column = table.getAllColumn().get(i);
				int index = column.Position - 1;
				if (row[index] == null) {
					sb.append(row[index]);
				} else {
					sb.append("'" + row[index].replaceAll("\'", "\\\\'") + "'");
				}
				if (i + 1 != table.getAllColumn().size()) {
					sb.append(",");
				}
				sb.append(CR);
			}
			sb.append(");\r\n");
		}
		
		return sb.toString();
	}
	

	public static String getInsertDataSQL(TableModel table, Map<String, String[]> data){

		List<String[]> list = new ArrayList<String[]>();
		for (String[] row : data.values()) {
			list.add(row);
		}
		
		return getInsertDataSQL(table, list);
		
//		String CR = "";
//		// 生成sql前缀
//		StringBuilder sbBase = new StringBuilder();
//		sbBase.append("INSERT INTO `" + table.Schema + "`.`" + table.TableName + "` (");
//		sbBase.append(CR);
//		
//		for (int i = 0; i < table.getAllColumn().size(); i++) {
//			ColumnModel column = table.getAllColumn().get(i);
//			sbBase.append("`" + column.ColumnName + "`");
//			if (i + 1 != table.getAllColumn().size()) {
//				sbBase.append(",");
//			}
//			sbBase.append(CR);
//		}
//		sbBase.append(") VALUES ( " + CR);
//		String base = sbBase.toString();
//
//		// 生成insert语句
//		StringBuilder sb = new StringBuilder();
//		sb.append("-- " + table.TableDesc + " " + table.Schema + "." + table.TableName + "\r\n");
//		for (String[] row : data.values()) {
//			sb.append(base);
////			for (int i = 0; i < row.length; i++) {
////				if (row[i] == null) {
////					sb.append(row[i]);
////				} else {
////					sb.append("'" + row[i] + "'");
////				}
////				if (i + 1 != row.length) {
////					sb.append(",");
////				}
////				sb.append(CR);
////			}
//			for (int i = 0; i < table.getAllColumn().size(); i++) {
//				ColumnModel column = table.getAllColumn().get(i);
//				int index = column.Position - 1;
//				if (row[index] == null) {
//					sb.append(row[index]);
//				} else {
//					sb.append("'" + row[index].replaceAll("\'", "\\\\'") + "'");
//				}
//				if (i + 1 != table.getAllColumn().size()) {
//					sb.append(",");
//				}
//				sb.append(CR);
//			}
//			sb.append(");\r\n");
//		}
//		
//		return sb.toString();
	}
	

	public static String getGenerateDataSQL2(TableModel table, List<String[]> listData){
		
		String CR = "";
		// 生成sql前缀
		StringBuilder sbBase = new StringBuilder();
		sbBase.append("INSERT INTO `" + table.Schema + "`.`" + table.TableName + "` (");
		sbBase.append(CR);
		
		for (int i = 0; i < table.getAllColumn().size(); i++) {
			ColumnModel column = table.getAllColumn().get(i);
			if (column.isAutoIncrement == true) {
				continue;
			}
			sbBase.append("`" + column.ColumnName + "`");
			if (i + 1 != table.getAllColumn().size()) {
				sbBase.append(",");
			}
			sbBase.append(CR);
		}
		sbBase.append(") VALUES ( " + CR);
		String base = sbBase.toString();

		// 生成insert语句
		StringBuilder sb = new StringBuilder();
		sb.append("-- " + table.TableDesc + " " + table.Schema + "." + table.TableName + "\r\n");
		for (String[] row : listData) {
			sb.append(base);

			for (int i = 0; i < table.getAllColumn().size(); i++) {
				ColumnModel column = table.getAllColumn().get(i);
				if (column.isAutoIncrement == true) {
					continue;
				}
				int index = column.Position - 1;
				if (row[index] == null) {
					sb.append(row[index]);
				} else {
					sb.append("'" + row[index].replaceAll("\'", "\\\\'") + "'");
				}
				if (i + 1 != table.getAllColumn().size()) {
					sb.append(",");
				}
				sb.append(CR);
			}
			sb.append(");\r\n");
		}
		
		return sb.toString();
	}


	public static String getGenerateDataSQL(TableModel table, List<String[]> listData){
		
		String CR = "";
		// 生成sql前缀
		StringBuilder sbBase = new StringBuilder();
		sbBase.append("INSERT INTO `" + table.Schema + "`.`" + table.TableName + "` (");
		sbBase.append(CR);
		
		for (int i = 0; i < table.getAllColumn().size(); i++) {
			ColumnModel column = table.getAllColumn().get(i);
			if (column.isAutoIncrement == true) {
				continue;
			}
			sbBase.append("`" + column.ColumnName + "`");
			if (i + 1 != table.getAllColumn().size()) {
				sbBase.append(",");
			}
			sbBase.append(CR);
		}
		sbBase.append(") \r\nVALUES \r\n" + CR);
		String base = sbBase.toString();

		// 生成insert语句
		StringBuilder sb = new StringBuilder();
		sb.append("-- " + table.TableDesc + " " + table.Schema + "." + table.TableName + "\r\n");
		sb.append(base);
		
		for (String[] row : listData) {
			
			sb.append("(");
			for (int i = 0; i < table.getAllColumn().size(); i++) {
				ColumnModel column = table.getAllColumn().get(i);
				if (column.isAutoIncrement == true) {
					continue;
				}
				int index = column.Position - 1;
				if (row[index] == null) {
					sb.append(row[index]);
				} else {
					sb.append("'" + row[index].replaceAll("\'", "\\\\'") + "'");
				}
				if (i + 1 != table.getAllColumn().size()) {
					sb.append(",");
				}
				sb.append(CR);
			}
			sb.append("), \r\n");
		}
		
		String result = sb.substring(0, sb.length() - 4);
		
		return result;
	}


	// 把分散的insert语句，都include进来
	public static String getInsertDataSourceSQL(TableModel table){
		
		return "source Insert_Data/Insert_Data_" + table.TableName + ".sql;\r\n";
	}
}
