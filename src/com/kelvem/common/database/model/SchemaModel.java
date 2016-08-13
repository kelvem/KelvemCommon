package com.kelvem.common.database.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kelvem.common.KLog;

public class SchemaModel {


	public String SchemaName = "";
	public Map<String, TableModel> mapTable = new HashMap<String, TableModel>();
	
//	public List<TableModel> listTable = new ArrayList<TableModel>();
	
//	public Map<String, TableModel> mapView = new HashMap<String, TableModel>();
	
//	public List<TableModel> listView = new ArrayList<TableModel>();
	
//	public Map<String, TableModel> mapTrigger = new HashMap<String, TableModel>();
	
//	public List<TableModel> listTrigger = new ArrayList<TableModel>();
	
	public SchemaModel(String SchemaName){
		this.SchemaName = SchemaName;
	}
	
	public void putTable(TableModel table){
			
		String key = table.TableName;
		if (mapTable.containsKey(key)) {
			mapTable.remove(key);
		}

		mapTable.put(key, table);
	}
		
	public void removeTable(TableModel table){
		
		String key = table.TableName;
		if (mapTable.containsKey(key)) {
			mapTable.remove(key);
		}
	
		mapTable.put(key, table);
	}
	
	public List<TableModel> getTables(){
		List<TableModel> list = new ArrayList<TableModel>();
		
		for (TableModel table : mapTable.values()) {
			list.add(table);
		}
		
		list = sortTables(list);
		
		return list;
	}
	
	public List<String> getTableNames(){
		List<String> list = new ArrayList<String>();
		
		for (TableModel table : getTables()) {
			list.add(table.TableName);
		}
		
		return list;
	}
	
	public TableModel getTable(String TableName){
		
		String key = TableName;
		if (mapTable.containsKey(key)) {
			return mapTable.get(key);
		} else {
			return null;
		}
	}
	
	public boolean contains(String TableName){
		return this.mapTable.containsKey(TableName);
	}
	
	public boolean contains(TableModel table){
		return this.mapTable.containsKey(table.TableName);
	}

	public List<TableModel> sortTables(List<TableModel> list){
		List<TableModel> result = new ArrayList<TableModel>();
		
		result.addAll(list);
		
		// 按名字排序
		for (int i = 0; i < result.size() - 1; i++) {
			for (int j = i + 1; j < result.size(); j++) {
				if (result.get(i).TableName.compareToIgnoreCase(result.get(j).TableName) > 0) {
					TableModel tablei = result.get(i);
					TableModel tablej = result.get(j);
					result.set(i, tablej);
					result.set(j, tablei);
				}
			}
		}
		
		// 按依賴排序
		for (int n = 0; n < result.size(); n++) {
			boolean hasConflict = false;
			for (int i = 0; i < result.size() - 1; i++) {
				for (int j = i + 1; j < result.size(); j++) {
					if (result.get(i).References != null 
							&&result.get(i).References.equals(result.get(j).TableName)) {
						TableModel tablei = result.get(i);
						result.add(j+1, tablei);
						result.remove(i);
						hasConflict = true;
					}
				}
			}
			if (!hasConflict) {
				if (n > 2) {
					KLog.info("按依賴排序" + (n+1) + "次后，调整完成依赖关系的顺序");
				}
				break;
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		
//		System.out.println("A".compareTo("B"));
//		System.out.println("C".compareTo("B"));
		TableModel table1 = new TableModel();
		table1.TableName = "A";
		TableModel table2 = new TableModel();
		table2.TableName = "C";
		TableModel table3 = new TableModel();
		table3.TableName = "B";
		
		List<TableModel> result = new ArrayList<TableModel>();
		result.add(table1);
		result.add(table2);
		result.add(table3);
		
		SchemaModel schema = new SchemaModel("");
		result = schema.sortTables(result);
		
		for (TableModel tableModel : result) {
			System.out.println(tableModel.TableName);
		}
	}
	
}
