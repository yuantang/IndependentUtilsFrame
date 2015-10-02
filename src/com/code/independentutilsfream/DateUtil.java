package com.code.independentutilsfream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

public class DateUtil {

	private Calendar calendar;

	public DateUtil() {
		calendar = Calendar.getInstance();
	}
	
	/**
	 * 把2014-04-05的日期转换为04月05日
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String dateTransMMdd(String date) throws ParseException {
		return new SimpleDateFormat("MM月dd日").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
	}
	/**
	 * 把2014-04-05的日期转换为04月05日
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String dateTransyyyMMdd(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(date));
	}
	
	public static Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
	}
	
	/**
	 * 获取当前年
	 * @return
	 */
	public int getCurrentYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月
	 * @return
	 */
	public int getCurrentMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日
	 * @return
	 */
	public int getCurrentDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取2014-12-05格式的日期，日期是当前的日期
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getyyyyMMdd() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 获取给定日期的前12个月，日期精确到月份
	 * @param myDate
	 * @return
	 */
	public ArrayList<MyDate> getPre12Month(MyDate myDate) {
		ArrayList<MyDate> dateList = new ArrayList<MyDate>();
		int year = myDate.year;
		int month = myDate.month;
		int tempMonth;
		for (int i = 0; i < 12; i++) {
			// 如果当前是1月，则变成13月，这样取13月的上一月就是12月，也就是1月的上一月是12月
			tempMonth = month;
			month = month == 1 ? 13 : month; 
			month--;
			
			// 如果上个月前当前月不是相差1，说明当前的月份是另一年的月份了，所以年份要-1。
			if ((tempMonth - month != 1) && year == myDate.year) {
				year--;
			}
			dateList.add(new MyDate(year, month));
		}
		
		return dateList;
	}
	
	/**
	 * 获取给定日期的前面第2个月，如给定的是5月，则往前2个月是3月
	 * @param myDate
	 * @return
	 */
	public MyDate getPreTwoDate(MyDate myDate) {
		int year = myDate.year;
		int month = myDate.month;
		int tempMonth;
		MyDate date = null;
		for (int i = 0; i < 2; i++) {
			// 如果当前是1月，则变成13月，这样取13月的上一月就是12月，也就是1月的上一月是12月
			tempMonth = month;
			month = month == 1 ? 13 : month; 
			month--;
			
			// 如果上个月前当前月不是相差1，说明当前的月份是另一年的月份了，所以年份要-1。
			if ((tempMonth - month != 1) && year == myDate.year) {
				year--;
			}
			date = new MyDate(year, month);
		}
		
		return date;
	}
	
	/**
	 * 返回当前年及往后推的(yearCounts -1)年
	 * @param yearCounts 指定要获取多少年
	 * @return
	 */
	public String[] getYears(int yearCounts) {
		String[] years = new String[yearCounts];
		int year = getCurrentYear();
		years[0] = year + "年";
		
		// 往后减(yearCounts -1)年
		for (int i = 1, length = yearCounts -1; i <= length; i++) {
			years[i] = (year - i) + "年";
		}
		return years;
	}
	
	/**
	 * 返回给定日期的yyyy-MM的格式
	 * @param myDate
	 * @return
	 */
	public String getYYYYMM(MyDate myDate) {
		return myDate.month < 10 ? myDate.year + "0" + myDate.month :  myDate.year + "" + myDate.month;
	}
	
	/**
	 * 把2014-03-23的日期创建为date对象
	 * @param myDate
	 * @return
	 */
	public MyDate createDateByYYYMMDD(String yyymmdd) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(yyymmdd);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return new MyDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取给定日期的下一个月
	 * @param myDate
	 * @return
	 */
	public MyDate getNextDate(MyDate myDate) {
		int year = myDate.year;
		int month = myDate.month;
		
		month = month == 12 ? 0 : month; 
		month++;
		if (month - myDate.month != 1) {
			year++;
		}
		
		return new MyDate(year, month);
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public MyDate getCurrentDate() {
		return new MyDate(getCurrentYear(), getCurrentMonth());
	}

	/** 获取格式为"2014-03-05"字符串数据中的日，如果解析出错，则返回1 */
	public int getDay(String yyyymmdd) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(yyyymmdd);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	/** 获取格式为"yyyymmdd"字符串数据 */
	public static String getCurrentDayStr() {
		String year, month, day;
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR) + "";
		int m = cal.get(Calendar.MONTH) + 1;
		if (m < 10) {
			month = "0" + m;
		} else {
			month = m + "";
		}
		int d = cal.get(Calendar.DATE);
		if (d < 10) {
			day = "0" + d;
		} else {
			day = d + "";
		}
		return year + month + day;
	}

	/**
	 * 获取格式为"yyyymmdd"字符串数据
	 * 
	 * @param monthAgo
	 *            查询几月前
	 * @return
	 */
	public static String getMonthsAgoDayStr(int monthAgo) {
		String year, month, day;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -monthAgo);
		year = cal.get(Calendar.YEAR) + "";
		int m = cal.get(Calendar.MONTH) + 1;
		if (m < 10) {
			month = "0" + m;
		} else {
			month = m + "";
		}
		int d = cal.get(Calendar.DATE);
		if (d < 10) {
			day = "0" + d;
		} else {
			day = d + "";
		}
		return year + month + day;
	}
	
	/**
	 * 判断该年是否是闰年<p>
	 * 闰年的2月有29天，平年只有28天<p>
	 * 公历闰年判定遵循的规律为：四年一闰，百年不闰，四百年再闰。
	 * @param year 年
	 * @return 是闰年返回true
	 */
	public static boolean isLeapYear(int year) {
		return ((year % 400) == 0) ? true : ((year % 4 == 0) && (year % 100 != 0));
	}
	
	/**
	 * 返回指定年中的某一个月的最大天数
	 * 有31天的月份为：1、3、5、7、8、10、12
	 * 只有30天的月份：4、6、9、11
	 * 闰年2月有29天
	 * 平年2月有28天
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public static int maxDayOfMonth(int year, int month) {
		// 如果是2月
		if (month == 2) {
			return isLeapYear(year) ? 29 : 28;
		}
		
		int maxDay;
		switch (month) {
		case 4:
		case 6:
		case 9:
		case 11:
			maxDay = 30;
			break;
		default:
			maxDay = 31;
			break;
		}
		return maxDay;
	}

	public class MyDate {
		
		public int year;
		public int month;
		
		
		public MyDate(int year, int month) {
			this.year = year;
			this.month = month;
		}
		
		@Override
		public String toString() {
			return year + "-" + (month < 10 ? "0" : "") + month;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + month;
			result = prime * result + year;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MyDate other = (MyDate) obj;
			if (month != other.month)
				return false;
			if (year != other.year)
				return false;
			return true;
		}

	}

}
