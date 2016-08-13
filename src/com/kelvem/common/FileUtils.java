package com.kelvem.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileUtils {

	public static String readFile(String filePath) {

		if (filePath == null) {
			throw new RuntimeException("param is null");
		}
		File file = new File(filePath);
		if (file == null || file.exists() == false) {
			throw new RuntimeException("file not exists : " + filePath);
		}
		StringBuilder sb = new StringBuilder();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// KLog.info(tempString);
				sb.append(tempString);
				sb.append("\r\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
	
	public static String[][] readFile(String filePath, String cellSplit,
			String enterSplit) {

		Date start = new Date();

		String text = readFile(filePath);
		String[] rows = text.split(enterSplit);

		int rowCnt = rows.length;
		int colCnt = rows[0].split(cellSplit).length;
		KLog.info("rowCnt = " + rowCnt);
		KLog.info("colCnt = " + colCnt);

		String[][] result = new String[rowCnt][colCnt];
		for (int i = 0; i < rowCnt; i++) {
			String[] cells = rows[i].split(cellSplit);
			int buf = cells.length;
			if (buf != colCnt) {
//				KLog.info("Read Line Err : " + rows[i].replaceAll("\r\n", "") + "@End");
				continue;
			}
			for (int j = 0; j < colCnt; j++) {
				result[i][j] = cells[j];
			}
		}

		Date end = new Date();
		KLog.info("readFile cost : " + (end.getTime() - start.getTime()) / 1000 + "s");
		return result;
	}

	public static void writeFile(String filePath, String text) {
		KLog.info("Start write file : " + filePath);
		Date start = new Date();

		FileWriter fw = null;
		try {

			fw = new FileWriter(filePath);
			fw.write(text);
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Date end = new Date();
		KLog.info("End   write file : cost " + (end.getTime() - start.getTime())/1000 + "s");
	}
}
