package com.kelvem.common.database.sqlite;

import com.kelvem.common.database.base.DataBaseSession;

public class SqliteTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DataBaseSession session = new SqliteSession();
		
		session.open();
		
		System.out.println("Test OK");
	}

}
