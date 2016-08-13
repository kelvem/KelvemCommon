package com.kelvem.common.database.oracle;

import java.sql.ResultSet;
import java.util.List;

import com.kelvem.common.database.base.ConnectionProperty;
import com.kelvem.common.database.base.DataBaseSession;


public class OracleSession extends DataBaseSession {

	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet query(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int execute(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] executeBatch(List<String> listSql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSessionType() {
		return "oracle";
	}
}
