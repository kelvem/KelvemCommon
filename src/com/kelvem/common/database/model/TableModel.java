/**
 * 
 */
package com.kelvem.common.database.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kelvem.common.ObjectUtil;

/**
 * @author kelvem
 *
 */
public class TableModel {

	public SchemaModel schema = null;
	
//	public Map<String, ColumnModel> mapPK = new TreeMap<String, ColumnModel>();
//	
//	public Map<String, ColumnModel> mapColumn = new TreeMap<String, ColumnModel>();

	public List<ColumnModel> listPK = new ArrayList<ColumnModel>();
	
	public List<ColumnModel> listColumn = new ArrayList<ColumnModel>();
	
	public Map<String, String[]> mapData = new HashMap<String, String[]>();
	
	public String Schema = null;
	public String TableName = null;
	public String TableDesc = null;

	public String Engine = null;

	public BigDecimal AutoIncrementIndex = null;
	
	public String sqlCreateTable = "";
	public String References = "";
	

	public TableModel(){
	}
	public TableModel(SchemaModel schema){
		this.schema = schema;
	}
	
	public List<ColumnModel> getAllColumn(){
		List<ColumnModel> result = new ArrayList<ColumnModel>();
		
		result.addAll(this.listPK);
		result.addAll(this.listColumn);

		return result;
	}
	
	public ColumnModel getColumn(String ColumnName){
		
		if (ColumnName == null) {
			return null;
		}
		
		List<ColumnModel> list = getAllColumn();
		for (int i = 0; i < list.size(); i++) {
			ColumnModel column = list.get(i);
			if (ColumnName.equalsIgnoreCase(column.ColumnName)) {
				return column;
			}
		}
		return null;
	}
	
	public ColumnModel getColumn(int index){
		
		List<ColumnModel> list = getAllColumn();
		
		if (list.size() <= index) {
			return null;
		}

		return list.get(index);
	}
	
	public int getColumnIndex(String ColumnName){
		
		if (ColumnName == null) {
			return -1;
		}
		
		List<ColumnModel> list = getAllColumn();
		for (int i = 0; i < list.size(); i++) {
			ColumnModel column = list.get(i);
			if (ColumnName.equalsIgnoreCase(column.ColumnName)) {
				return i;
			}
		}
		return -1;
	}
	
	public static TableModel getDummyTable(){
	
		TableModel table = new TableModel();
		table.TableName = "C_PERSON_MGR";
		
		ColumnModel col; 
		
		col = new ColumnModel(table);
		col.ColumnName = "ID1";
		col.ColumnTypeID = 1;
		col.ColumnDesc = "主键ID1";
		table.listPK.add(col);
		
		col = new ColumnModel(table);
		col.ColumnName = "ID2";
		col.ColumnTypeID = 2;
		col.ColumnDesc = "主键ID2";
		table.listPK.add(col);
		
		col = new ColumnModel(table);
		col.ColumnName = "NAME";
		col.ColumnTypeID = 2;
		col.DataSize = 10;
		col.ColumnDesc = "姓名";
		table.listColumn.add(col);
		
		col = new ColumnModel(table);
		col.ColumnName = "DESC";
		col.ColumnTypeID = 2;
		col.DataSize = 20;
		col.ColumnDesc = "描述";
		table.listColumn.add(col);
		
		col = new ColumnModel(table);
		col.ColumnName = "UPDATE_TIME";
		col.ColumnTypeID = 3;
		col.DataSize = 0;
		col.ColumnDesc = "更新时间";
		col.isNullEnable = true;
		table.listColumn.add(col);
				
		return table;
	}
	
	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
