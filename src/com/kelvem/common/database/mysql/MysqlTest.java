package com.kelvem.common.database.mysql;

import com.kelvem.common.database.base.DataBaseSession;

public class MysqlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DataBaseSession session = new MysqlSession();
		
		session.open();
		
		System.out.println("Test OK");
	}

}
