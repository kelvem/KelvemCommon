package com.kelvem.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kelvem.common.CsvReader.Letters;

public class _FileUtil {

	public static final String DefaultSplitCell = String.valueOf(Letters.COMMA);
	
	public static final String DefaultSplitRow = String.valueOf(Letters.LF);
	
	private _FileUtil() {

	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFile(String fileName) {

		StringBuilder sb = new StringBuilder();

		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				sb.append(tempString + "\r\n");
				line++;
			}
			KLog.info("读取文件：" + fileName + " : 行" + line);

			reader.close();
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static List<String> readLines(String fileName) {

		List<String> lines = new ArrayList<String>();

		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				lines.add(tempString);
				line++;
			}
			KLog.info("读取文件：" + fileName + " : 行" + line);

			reader.close();
			return lines;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static List<String[]> readCsvFile(String filePath) {
		KLog.info("Start read csv file : " + filePath);
		Date start = new Date();
		List<String[]> result = new ArrayList<String[]>();
		try {
			CsvReader csv = new CsvReader(filePath, Letters.COMMA, Charset.forName("UTF-8"));
			
			while(csv.readRecord()){
				String[] row = csv.getValues();
				result.add(row);
			}
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException("", e);
		} catch (IOException e) {
			throw new RuntimeException("", e);
		}
		Date end = new Date();
		KLog.info("End   read csv file : return.size = " + result.size());
		KLog.info("End   read csv file : cost " + (end.getTime() - start.getTime())/1000 + "s");
		KLog.info("");
		return result;
	}
	
	public static String[][] readFile(String filePath, String cellSplit, String enterSplit) {

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
				KLog.info("Read Line Err : " + rows[i].replaceAll("\r\n", "") + "@End");
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
	
	public static <T> List<T> readFile(Class<T> clazz, String filePath){

		List<T> result = new ArrayList<T>();
		long start = System.currentTimeMillis() / 1000;
		try {
			List<String[]> datas = _FileUtil.readCsvFile(filePath);

		    if (datas == null || datas.size() <= 0) {
				return result;
			}
		    
		    int rowCnt = datas.size();
		    int colCnt = datas.get(0).length;
		    
		    Map<String, Boolean> fieldEnableMap = new HashMap<String, Boolean>();
		    for (String fieldName : datas.get(0)) {
				boolean enable = ReflectUtil.checkField(clazz, fieldName);
				fieldEnableMap.put(fieldName, enable);
			}
		    for (int i = 1; i < rowCnt; i++) {
		    	T model = clazz.newInstance();
				for (int j = 0; j < colCnt; j++) {
					if (fieldEnableMap.get(datas.get(0)[j]) == true) {
						ReflectUtil.setParamValue(model, datas.get(0)[j], datas.get(i)[j]);
					}
				}
				result.add(model);
			}
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}

		KLog.info("SqlDataFile.read() cost : " + (System.currentTimeMillis() / 1000 - start) + "s");
		
		return result;
	}

	public static boolean writeFile(String fileName, String content) {
		KLog.info("Start write file : " + fileName);
		Date start = new Date();
		
		OutputStreamWriter osw = null;
		try {
//			FileWriter fw = new FileWriter(fileName);
//			String s = context;
//			fw.write(s, 0, s.length());
//			fw.flush();
			File file = new File(fileName);
			
			if ( !file.exists() ){
				
				boolean ret1 = file.getParentFile().mkdirs();
				boolean ret2 = file.createNewFile();
				
				KLog.info("创建文件：" + fileName + " : " + ret1 + " :" + ret2);
			}
			osw = new OutputStreamWriter(new FileOutputStream(fileName));
			// ###
//			content = content.replace("[", "#{");
//			content = content.replace("]", "}");
			osw.write(content, 0, content.length());
			osw.flush();
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
//					new FileOutputStream("hello3.txt")), true);
//			pw.println(s);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
				}
			}
			Date end = new Date();
			KLog.info("End   write file : cost " + (end.getTime() - start.getTime())/1000 + "s");
		}
	}
}
