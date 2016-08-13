package com.kelvem.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtils {

	public static Date now() {
		return getCurrentDate();
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static String getDateString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public static String getDateString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String getTimeHuor(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	public static String getDateTimeString(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}

	}

	public static String getDateTimeString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date parseDate(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
	}

	public static Date parseDate(String date, String format)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	public static Date parseDateTime(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}

	public static Date parseDateTime(String date, String format)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	/**
	 * Get the previous date
	 * 
	 * @author Randy Wong
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * Get date before the input date according to param-days
	 * 
	 * @author Randy Wong
	 * @param date
	 * @param days
	 *            must be negtive
	 * @return Date
	 */
	public static Date getDateBefore(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * Get the next date
	 * 
	 * @author Randy Wong
	 * @param date
	 * @return Date
	 */
	public static Date getNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}

	/**
	 * Get date after the input date according to param-days
	 * 
	 * @author Randy Wong
	 * @param date
	 * @param days
	 *            must be positive
	 * @return Date
	 */
	public static Date getDateAfter(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * 获取给定时间所在月的第一天Ｆ的日期和最后一天的日期
	 * 
	 * @param calendar
	 * @return Date数组，[0]为第一天的日期，[1]最后一天的日期
	 * */
	public static Date[] getMonthStartAndEndDate(Calendar calendar) {
		Date[] dates = new Date[2];
		Date firstDateOfMonth, lastDateOfMonth; // 得到当天是这月的第几天
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		// 减去dayOfMonth,得到第一天的日期，因为Calendar用０代表每月的第一天，所以要减一
		calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
		firstDateOfMonth = calendar.getTime();
		// calendar.getActualMaximum(Calendar.DAY_OF_MONTH)得到这个月有几天
		calendar.add(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
		lastDateOfMonth = calendar.getTime();
		dates[0] = firstDateOfMonth;
		dates[1] = lastDateOfMonth;
		return dates;
	}

	/**
	 * 获取2个日期间的天数
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDayStartAndEndDate(Date beginTime, Date endTime) {

		// 相差的天数
		int days = (int) ((endTime.getTime() - beginTime.getTime()) / 86400000);
		return days;
	}

	/**
	 * 
	 * <p>传入时间,返回 天：小时：分：秒串</p>
	 * @param dateFirst
	 * @param dateSecond
	 * @return String
	 * @see
	 */
	public static String getDateSplitString(Date dateFirst,Date dateSecond){
		if(dateFirst == null){
			return "";
		}
		StringBuffer strResult = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFirst);
		long millF = cal.getTimeInMillis();
		cal.setTime(dateSecond);
		long millS = cal.getTimeInMillis();
		
		long secs = (millF-millS)/1000;
		long day = secs/(24*3600);
		long hour = (secs%(24*3600))/3600;
		long min = ((secs%(24*3600))%3600)/60;
		long sec = ((secs%(24*3600))%3600)%60;
		
		strResult.append(day).append(":").append(hour).append(":").append(min).append(":").append(sec);
		return strResult.toString();
	}	
	
	/**
	 * 
	 * <p>根据偏移量偏移时间</p>
	 * @param date
	 * @param offset,格式如 天:小时：分钟:秒
	 * @return
	 * @return String
	 * @throws ParseException 
	 * @see
	 */
	public static String getOffsetDateString(String dateStr,String offset) throws ParseException{
		
		String[] arr = offset.split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.parseDate(dateStr,"yyyy-MM-dd HH:mm:ss"));
		cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(arr[0]));
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[1]));
		cal.add(Calendar.MINUTE, Integer.parseInt(arr[2]));
		cal.add(Calendar.SECOND, Integer.parseInt(arr[3]));
		return DateUtils.getDateTimeString(cal.getTime());
	}
	
	/**
	 * 
	 * <p>根据偏移量偏移时间</p>
	 * @param date
	 * @param offset,格式如 天:小时：分钟:秒
	 * @return
	 * @return String
	 * @throws ParseException 
	 * @see
	 */
	public static Date getOffsetDate(Date date,String offset) throws ParseException{
		
		String[] arr = offset.split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.parseDate(getDateTimeString(date),"yyyy-MM-dd HH:mm:ss"));
		cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(arr[0]));
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[1]));
		cal.add(Calendar.MINUTE, Integer.parseInt(arr[2]));
		cal.add(Calendar.SECOND, Integer.parseInt(arr[3]));
		return cal.getTime();
	}
	
}
