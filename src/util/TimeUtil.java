package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/**
	 * @return 当前格式化的时间  例：2016-12-15 12:31:25
	 */
	public static String getCurDate() {
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * @return 当前年份 例：2015
	 */
	public static String getCurYear() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			return format.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * @return 当前月份 例：1225
	 */
	public static String getCurNyDate() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MMdd");
			return format.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * @return 当前日期 例：20160815
	 */
	public static String getFormatCurDate() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			return format.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * @desc 获取数据库中格式化的日期 例：2014-12-17T09:31:00
	 */
	public static String getCpwcNoitceTime() {
		
		try {
			
			SimpleDateFormat dayformat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat hoursformat = new SimpleDateFormat("HH:mm:ss");
			
			String curDate = dayformat.format(new Date()) + "T" + hoursformat.format(new Date());
			return  curDate;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * @desc 获取当前月份  例：11
	 */
	public static String getCurMonth() {
		
		try {
			SimpleDateFormat curMonth = new SimpleDateFormat("MM");
			return curMonth.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * @desc 获取当天日期号 例：23
	 */
	public static String getCurDay() {
		
		try {
			SimpleDateFormat curMonth = new SimpleDateFormat("dd");
			return curMonth.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
