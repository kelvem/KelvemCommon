package com.kelvem.common;

import java.io.InputStream;

public class CmdUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String content = shell("ping localhost");
		System.out.print(content);
	}

	public static String shell(String cmd){
		String content = "";
		try { 
			// 执行命令
			Runtime shell = Runtime.getRuntime();
			Process process = shell.exec("cmd.exe  /c  " + cmd);
			InputStream is = process.getInputStream();
			
			content = StringUtil.valueOf(is);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
}
