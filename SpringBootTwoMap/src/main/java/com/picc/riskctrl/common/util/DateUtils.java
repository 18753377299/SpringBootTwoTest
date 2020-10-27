package com.picc.riskctrl.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.picc.riskctrl.map.vo.typhoonRequest.TyphoonPath;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年10月22日 下午5:33:59
 * @version 1.0 
 * @parameter 	
 * @since  日期类型的工具
 * @return  */
public class DateUtils {
	/**
	 * 获得某个月最大天数,传递参数是日期类型的字符串
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public  static int getMaxDayByYearMonth(String yearMonth){
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(yearMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.getActualMaximum(Calendar.DATE);
	}
	/**
	 * 获得某个月最大天数
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public  static int getMaxDayByYearMonth(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DATE);
	}
	/**
	 * 将日期格式转换为字符串类型,年月日类型yyyy-MM-dd
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public static String dateTransferStrYMD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		return  sdf_YMD.format(date);
	}
	/**
	 * 将日期格式转换为字符串类型,年月日类型yyyy-MM-dd
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 * @日期  20200427 liqiankun
	 */
	public static Date strYMDTransferDate(String dateTime){
		Date date =null;
		SimpleDateFormat sdf_YMD;
		try {
			sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf_YMD.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  date;
	}
	/**
	 * 将日期格式转换为字符串类型,年月日类型yyyy-MM-dd
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 * @日期  20200427 liqiankun
	 */
	public static Date strYMDNoTransferDate(String dateTime){
		Date date =null;
		SimpleDateFormat sdf_YMD;
		try {
			sdf_YMD = new SimpleDateFormat("yyyyMMdd");
			date = sdf_YMD.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  date;
	}
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式转换yyyy-MM-dd
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public static String strTransferStrYMD(String date){
		// yyyy-MM-dd HH:mm:ss 必须要写成这样，如果写成YYYY-MM-dd HH:mm:ss 会出会出现日期时间错误，跟原有时间不一致。
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_HMS.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  sdf_YMD.format(calendar.getTime());
	}
	/**
	 * 将日期格式转换为字符串类型,年月日类型YYYY-MM-dd HH:mm:ss
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public static String dateTransferStrHMS(Date date){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  sdf_HMS.format(date);
	}
	/**
	 * 将字符串类型转换为日期类型,年月日类型YYYY-MM-dd HH:mm:ss
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public static Date strHMSTransferDate(String  date){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date returnDate = null;
		try {
			returnDate = sdf_HMS.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  returnDate;
	}
	/**
	 * 获取某一日期的下一天的日期
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getNextDate(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return  sdf_YMD.format(calendar.getTime());
	}
	/**
	 * 获取某一日期的下n天的日期,日期格式: yyyyMMdd
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getNextDateFormat(Date date,int day){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return  sdf_YMD.format(calendar.getTime());
	}
	/**
	 * 获取某一日期的下n天的日期,传参是String形式,日期格式: yyyyMMdd
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getNextStringDateFormat(String date,int day){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date returnDate = null;
		try {
			returnDate = sdf_YMD.parse(date);
			calendar.setTime(returnDate);
			calendar.add(Calendar.DAY_OF_MONTH, day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  sdf_YMD.format(calendar.getTime());
	}
	/**
	 * 获取某一日期的下几天天几点零分的日期
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 * @时间：20191120
	 */
	public static String getNextDateEightHourMinu(Date date,int day,int hour){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		return  sdf_YMD.format(calendar.getTime());
	}
	/**
	 * 获取某一日期的下几天天几点的日期,用于实况降雨数据
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 * @时间：20191120
	 */
	public static String getNextDateEightHour(Date date,int day,int hour){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyyMMddHH");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		return  sdf_YMD.format(calendar.getTime());
	}
	/**
	 * 获取当前日期是周几
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getWeekOfDate(String dayOfYear){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		String [] weekDays ={"7","1","2","3","4","5","6"};
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(dayOfYear));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if(week<0){
			week = 0;
		}
		return  weekDays[week];
	}
	
	/**
	 * 获取的一天的开始时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getStartDateOfDay(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		calendar.setTime(date);
//		// 如果值传递年月日的话，可以把下面这一段去掉
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		System.out.println(calendar.getTimeInMillis());
		
	    /**  other way 
	    SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    start = formater2.parse(formater.format(new Date())+ " 00:00:00");
	    end = formater2.parse(formater.format(new Date())+ " 23:59:59");*/
		
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取的一天的开始时间,返回日期是date类型，FD： formatDate,FS: formatString
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static Date getStartDateOfDayFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.getTime();
	}
	/**
	 * 获取的一天的结束时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getEndDateOfDay(Date date){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
//		calendar.set(Calendar.HOUR_OF_DAY,23);
//		calendar.set(Calendar.MINUTE, 59);
//		calendar.set(Calendar.SECOND, 59);
//		calendar.set(Calendar.MILLISECOND, 999);
		// 获取到第二天减一秒
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
//		System.out.println(calendar.getTimeInMillis());
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取的一个月的开始时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getStartDateOfMonth(String date){
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取的一个月的结束时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getEndDateOfMonth(String date){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(date));
			// 获取到第二天减一秒
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.SECOND, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取当天十一点半的时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static Date getHalfDateOfDay(String date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(date));
			calendar.set(Calendar.HOUR_OF_DAY,11);
			calendar.set(Calendar.MINUTE, 30);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calendar.getTime();
	}
	
	/**
	 * 将当前日期转换成yyyy-MM-dd HH:mm:ss形式
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getCurrentDateFormat(Date date){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取当前日期的月份
	 * @param dateTime： yyyy-MM
	 * @param 
	 * @author liqiankun 20200426
	 * @return 
	 */
	public static Integer  getCurrentMonth(String dateTime){
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		Integer month = 0; 
		try {
			calendar.setTime(sdf_YM.parse(dateTime));
			month = calendar.get(Calendar.MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month;
	}
	/**
	 * 获取该年的开始时间
	 * @param dateTime： yyyy-MM
	 * @param 
	 * @author liqiankun 20200426
	 * @return 
	 */
	public static String  getStartDateOfYear(String dateTime){
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(dateTime));
//			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取的前一个月的月末
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static String getEndDateOfLastMonth(String date){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(date));
			// 获取到第二天减一秒
			calendar.add(Calendar.SECOND, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取开始时间,大灾平台调用，
	 * @param dateTime： yyyy-MM
	 * @param 
	 * @author liqiankun 20200426
	 * @return 
	 */
	public static String  getStartDateOfYearByDanger(String dateTime){
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(dateTime));
			int month = calendar.get(Calendar.MONTH);
			if(11==month ||7==month ||8==month ||9==month ||10==month){
				/*12/8/9/10/11月1日：提取当年7月数据*/
				calendar.set(Calendar.MONTH, 6);
			}else if(0==month||1==month||2==month ||3==month ||4==month ||5==month ||6==month){
				/*1/2/3/4/5/6/7月1日：提取上年7-12月数据；*/
				calendar.set(Calendar.MONTH, 6);
				calendar.add(Calendar.YEAR, -1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 获取结束时间,大灾平台调用，
	 * @param dateTime： yyyy-MM
	 * @param 
	 * @author liqiankun 20200426
	 * @return 
	 */
	public static String  getEndDateOfYearByDanger(String dateTime){
		SimpleDateFormat  sdf_YM = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YM.parse(dateTime));
			int month = calendar.get(Calendar.MONTH);
			if(11==month){
				/*12月1日：提取当年7-11月数据*/
				calendar.set(Calendar.MONTH, 11);
				calendar.add(Calendar.SECOND,-1);
			}else if(0==month){
				/*1月1日：提取上年7-12月数据；*/
				calendar.set(Calendar.MONTH, 0);
				calendar.add(Calendar.SECOND,-1);
			}else if(1==month){
				/*2月1日：提取上年7月至当年1月数据；*/
				calendar.set(Calendar.MONTH, 1);
				calendar.add(Calendar.SECOND,-1);
			}else if(2==month){
				/*3月1日：提取上年7月至当年2月数据；*/
				calendar.set(Calendar.MONTH, 2);
				calendar.add(Calendar.SECOND,-1);
			}else if(3==month){
				/*4月1日：提取上年7月至当年3月数据；*/
				calendar.set(Calendar.MONTH, 3);
				calendar.add(Calendar.SECOND,-1);
			}else if(4==month){
				/*5月1日：提取上年7月至当年4月数据；*/
				calendar.set(Calendar.MONTH, 4);
				calendar.add(Calendar.SECOND,-1);
			}else if(5==month){
				/*6月1日：提取上年7月至当年5月数据；*/
				calendar.set(Calendar.MONTH, 5);
				calendar.add(Calendar.SECOND,-1);
			}else if(6==month){
				/*7月1日：提取上年7月至当年6月数据；*/
				calendar.set(Calendar.MONTH, 6);
				calendar.add(Calendar.SECOND,-1);
			}else if(7==month){
				/*8月1日：提取当年7月数据；*/
				calendar.set(Calendar.MONTH, 7);
				calendar.add(Calendar.SECOND,-1);
			}else if(8==month){
				/*9月1日：提取当年7-8月数据；*/
				calendar.set(Calendar.MONTH, 8);
				calendar.add(Calendar.SECOND,-1);
			}else if(9==month){
				/*10月1日：提取当年7-9月数据*/
				calendar.set(Calendar.MONTH, 9);
				calendar.add(Calendar.SECOND,-1);
			}else if(10==month){
				/*11月1日：提取当年7-10月数据*/
				calendar.set(Calendar.MONTH, 10);
				calendar.add(Calendar.SECOND,-1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf_HMS.format(calendar.getTime());
	}
	/**
	 * 将string日期形式改成mdy格式的字符串
	 * @param 
	 * @param 
	 * @return 
	 */
	public static String transferDateToMdy(String dateTime){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		StringBuilder  result = new StringBuilder("mdy(");
		try {
			calendar.setTime(sdf_HMS.parse(dateTime));
			int month = calendar.get(Calendar.MONTH)+1;
			int year = calendar.get(Calendar.YEAR);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			result = result.append(month).append(",").append(day).append(",").append(year).append(")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	/**
	 * 将格林时间转换为北京时间，转换方式是格林时间+8h=北京时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 
	 */
	public static TyphoonPath fromGreenToWorldTime(TyphoonPath typhoonPath){
		SimpleDateFormat  sdf_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			String dateTime = typhoonPath.getDatetime();
			calendar.setTime(sdf_HMS.parse(dateTime));
			calendar.add(Calendar.HOUR_OF_DAY, 8);
			String dateTimeNew = sdf_HMS.format(calendar.getTime());
			/*资料时间*/
			typhoonPath.setDatetime(dateTimeNew);
			
			Calendar calendarTwo = Calendar.getInstance();
			calendarTwo.set(Integer.valueOf(typhoonPath.getYear()).intValue(), Integer.valueOf(typhoonPath.getMon())-1,
					Integer.valueOf(typhoonPath.getDay()).intValue(), Integer.valueOf(typhoonPath.getHour())+8,0);
			typhoonPath.setYear(calendarTwo.get(Calendar.YEAR)+"");
			typhoonPath.setMon((calendarTwo.get(Calendar.MONTH)+1)+"");
			typhoonPath.setDay(calendarTwo.get(Calendar.DAY_OF_MONTH)+"");
			typhoonPath.setHour(calendarTwo.get(Calendar.HOUR_OF_DAY)+"");
			
			Calendar calendarThree = Calendar.getInstance();
			calendarThree.set(Integer.valueOf(typhoonPath.getYear_Data()).intValue(), Integer.valueOf(typhoonPath.getMon_Data())-1,
					Integer.valueOf(typhoonPath.getDay_Data()).intValue(), Integer.valueOf(typhoonPath.getHour_Data())+8,0);
			typhoonPath.setYear_Data(calendarThree.get(Calendar.YEAR)+"");
			typhoonPath.setMon_Data((calendarThree.get(Calendar.MONTH)+1)+"");
			typhoonPath.setDay_Data(calendarThree.get(Calendar.DAY_OF_MONTH)+"");
			typhoonPath.setHour_Data(calendarThree.get(Calendar.HOUR_OF_DAY)+"");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return typhoonPath;
	}
	/**
	 * 获取当前日期中的周一时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return  20200730  addby liqiankun
	 */
	public static Date  getStartDateOfWeekFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		String [] weekDays ={"7","1","2","3","4","5","6"};
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if(week<0){
			week = 0;
		}
		// 获取当前日期的星期一的开始时间
		calendar.add(Calendar.DAY_OF_MONTH, 1-Integer.parseInt(weekDays[week]));
		return calendar.getTime();
	}
	/**
	 * 获取当前日期中一个月的开始时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return  20200730  addby liqiankun
	 */
	public static Date  getStartDateOfMonthFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	/**
	 * 获取当前季度中的开始时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 20200730  addby liqiankun
	 */
	public static Date  getStartDateOfQuarterFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int month = calendar.get(Calendar.MONTH)+1;
		if (month>=1 && month<4){
			calendar.set(Calendar.MONTH, 0);
		}else if (month>=4 && month<7){
			calendar.set(Calendar.MONTH, 3);
		}else if (month>=7 && month<10){
			calendar.set(Calendar.MONTH, 6);
		}else if (month>=10 && month<=12){
			calendar.set(Calendar.MONTH, 9);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	/**
	 * 获取半年中的开始时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 20200730  addby liqiankun
	 */
	public static Date  getStartDateOfHalfYearFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int month = calendar.get(Calendar.MONTH)+1;
		if (month>=1 && month<7){
			calendar.set(Calendar.MONTH, 0);
		}else if (month>=7 && month<=12){
			calendar.set(Calendar.MONTH, 6);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	/**
	 * 获取当年中的开始时间
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 20200730  addby liqiankun
	 */
	public static Date  getStartDateOfYearFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int month = calendar.get(Calendar.MONTH)+1;
		if (month>=1 && month<7){
			calendar.set(Calendar.MONTH, 0);
		}else if (month>=7 && month<=12){
			calendar.set(Calendar.MONTH, 6);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	/**
	 * 获取每两年的开始时间，2019 年1月1日开始。每隔两年触发。
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 20200730  addby liqiankun
	 */
	public static Date  getStartDateOfTwoYearFD(Date date){
		SimpleDateFormat  sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf_YMD.parse(sdf_YMD.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int month = calendar.get(Calendar.MONTH)+1;
		if (month>=1 && month<7){
			calendar.set(Calendar.MONTH, 0);
		}else if (month>=7 && month<=12){
			calendar.set(Calendar.MONTH, 6);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	
	
	
}
