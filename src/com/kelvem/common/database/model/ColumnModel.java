/**
 * 
 */
package com.kelvem.common.database.model;

import com.kelvem.common.KLog;
import com.kelvem.common.ObjectUtil;

/**
 * @author kelvem
 *
 */
public class ColumnModel {
	
//	tinyint  1 字节 (-128，127) (0，255) 
//	smallint 2 字节 (-32 768，32 767) (0，65 535)
//	mediumint3 字节 (-8 388 608，8 388 607) (0，16 777 215)
//	int      4 字节 (-2 147 483 648，2 147 483 647) (0，4 294 967 295)
//	bigint   8 字节 (-9 233 372 036 854 775 808，9 223 372 036 854 775 807) (0，18 446 744 073 709 551 615)
	public final static int COLUMN_TYPE_INT = 10;
	
//	float    4 字节 (-3.402 823 466 E+38，1.175 494 351 E-38)，0，(1.175 494 351 E-38，3.402 823 466 351 E+38) 0，(1.175 494 351 E-38，3.402 823 466 E+38) 
//	double   8 字节 (1.797 693 134 862 315 7 E+308，2.225 073 858 507 201 4 E-308)，0，(2.225 073 858 507 201 4 E-308，1.797 693 134 862 315 7 E+308) 0，(2.225 073 858 507 201 4 E-308，1.797 693 134 862 315 7 E+308) 
//	decimal  DECIMAL(M,D)
	public final static int COLUMN_TYPE_DOUBLE = 11;
	
//	char 0-255字节 定长字符串 
//	varchar 0-255字节 变长字符串 
	public final static int COLUMN_TYPE_STRING = 20;
	
//	tinyblob 0-255字节 不超过 255 个字符的二进制字符串 
//	tinytext 0-255字节 短文本字符串 
//	blob 0-65 535字节 二进制形式的长文本数据 
//	text 0-65 535字节 长文本数据 
//	mediumblob 0-16 777 215字节 二进制形式的中等长度文本数据 
//	mediumtext 0-16 777 215字节 中等长度文本数据 
//	logngblob 0-4 294 967 295字节 二进制形式的极大文本数据 
//	longtext 0-4 294 967 295字节 极大文本数据 
	public final static int COLUMN_TYPE_BLOB = 21;
	

//	date 3 1000-01-01/9999-12-31 YYYY-MM-DD 日期值 
//	time 3 '-838:59:59'/'838:59:59' HH:MM:SS 时间值或持续时间 
//	year 1 1901/2155 YYYY 年份值 
//	datetime 8 1000-01-01 00:00:00/9999-12-31 23:59:59 YYYY-MM-DD HH:MM:SS 混合日期和时间值 
//	timestamp 8 1970-01-01 00:00:00/2037 年某时 YYYYMMDD HHMMSS 混合日期和时间值，时间戳 
	public final static int COLUMN_TYPE_DATETIME = 30;
	public final static int COLUMN_TYPE_DATE = 31;
	public final static int COLUMN_TYPE_TIME = 32;
	public final static int COLUMN_TYPE_TIMESTAMP = 33;
	
// unkown	
	public final static int COLUMN_TYPE_UNKNOW = 0;
	
	
	public TableModel table = null;
	
	public String ColumnName;
	public Integer ColumnTypeID; // 1 int 2 varchar 3 time  9 key_id
	public String DataType;
	public String ColumnType;
	public Integer DataSize;
	public String ColumnDefault;
	public String ColumnDesc;
	public Integer Position;
	
	// NN: not null (column is nullable) 非空
	public boolean isNullEnable = false; 
	
	// UQ: unique (column is part of a unique key) 唯一
	public boolean isUnique = false;
	
	// AI: auto increment (the column is auto incremented when rows are inserted) 自增
	public boolean isAutoIncrement = false;
	
	// BIN: binary (if dt is a blob or similar, this indicates that is binary data, rather than text) 二进制(比text更大的二进制数据)
	
	// UN: unsigned (for integer types, see docs: “10.2. Numeric Types”) 整数
	
	// ZF: zero fill (rather a display related flag, see docs: “10.2. Numeric Types”)值中最有意义的字节总为0，并且不保存。


	public ColumnModel(){
	}
	public ColumnModel(TableModel table){
		this.table = table;
	}
	
	public static int parseColumnCode(String DataType){
		
		if(DataType == null){
			return COLUMN_TYPE_UNKNOW;
		} else if (DataType.toLowerCase().endsWith("int")){
			return COLUMN_TYPE_INT;
		} else if (DataType.toLowerCase().startsWith("float")
				|| DataType.toLowerCase().startsWith("double")
				|| DataType.toLowerCase().startsWith("decimal")){
			return COLUMN_TYPE_DOUBLE;
		} else if (DataType.toLowerCase().startsWith("char")
				|| DataType.toLowerCase().startsWith("varchar")){
			return COLUMN_TYPE_STRING;
		} else if (DataType.toLowerCase().endsWith("blob")
				|| DataType.toLowerCase().endsWith("text")){
			return COLUMN_TYPE_BLOB;
		} else if (DataType.toLowerCase().equals("datetime")){
			return COLUMN_TYPE_DATETIME;
		} else if (DataType.toLowerCase().equals("date")){
			return COLUMN_TYPE_DATE;
		} else if (DataType.toLowerCase().equals("time")){
			return COLUMN_TYPE_TIME;
		} else if (DataType.toLowerCase().equals("timestamp")){
			return COLUMN_TYPE_TIMESTAMP;
		} else {
			KLog.error("Unknow Type : " + DataType);
			return COLUMN_TYPE_UNKNOW;
		}
	}
	
	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
