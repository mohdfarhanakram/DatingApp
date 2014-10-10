package com.farru.android.utill;

import java.util.Calendar;
import java.util.TimeZone;

import android.text.format.DateFormat;

/**
 * Utility class for date-time related methods.
 */
public class DateTimeUtils {

	/**
	 * Constants to be used in
	 * {@link DateTimeUtils#getFormattedDate(long, String)} method.
	 */
	public interface Format {
		String	DD_Mmm_YYYY			= "dd MMM yyyy";
		String	DD_Mmm_YY			= "dd MMM ''yy";
		String	DD_Mmmm_YYYY		= "dd MMMM, yyyy";
		String	DD_Mmm_YYYY_Dow		= "dd MMM yyyy, E";
		String	DayWeek_DD_Mmm_YYYY	= "EEEE, dd MMM yyyy";
		String	day_of_week_3_chars	= "E";
		String	DD					= "dd";
		String	MMM					= "MMM";
		String	Dow					= "E";
		String	Mmmm_YYYY			= "MMMM yyyy";
		String	DD_Mmmm_YYYY_HH_MM	= "dd MMM yyy kk:mm";
		String	DD_MMM				= "dd MMM";
		String	M_SS				= "m:ss";
	}

	/**
	 * @param pEpochMillis
	 * @param pFormat
	 *            Possible values for pFormat are:
	 *            <ul>
	 *            <li>{@link Format#DD_Mmm_YYYY_Dow}</li>
	 *            <li>{@link Format#day_of_week_3_chars}</li>
	 *            </ul>
	 * @return date string as per pEpochMillis, formatted as per pFormat.
	 */
	public static String getFormattedDate(long pEpochMillis, String pFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(pEpochMillis);
		return DateFormat.format(pFormat, calendar).toString();
	}

	/**
	 * @param pEpochMillis
	 * @param pFormat
	 *            Possible values for pFormat are:
	 *            <ul>
	 *            <li>{@link Format#DD_Mmm_YYYY_Dow}</li>
	 *            <li>{@link Format#day_of_week_3_chars}</li>
	 *            </ul>
	 * @return date string as per calendar, formatted as per pFormat.
	 */
	public static String getFormattedDate(Calendar calendar, String pFormat) {
		return DateFormat.format(pFormat, calendar).toString();
	}

	/**
	 * @note method is only for duration of 23:59:59:999 or less.
	 * 
	 * @param pDurationMillis
	 * @param pFormat
	 * @return
	 */
	public static String getFormattedDuration(long pDurationMillis, String pFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getMillis(calendar, 0, 0, 0, 0) + pDurationMillis);
		return DateFormat.format(pFormat, calendar).toString();
	}

	/**
	 * @param pDaysOffset
	 * @return mid-night epoch milliseconds of start of today+pDaysOffset
	 */
	public static long getMidNightMillis(int pDaysOffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, pDaysOffset);
		return getMillis(calendar, 0, 0, 0, 0);
	}

	/**
	 * @param pTimeZone
	 * @param pDaysOffset
	 * @return mid-night epoch milliseconds of start of today+pDaysOffset
	 */
	public static long getMidNightMillis(TimeZone pTimeZone, int pDaysOffset) {
		Calendar calendar = Calendar.getInstance(pTimeZone);
		calendar.add(Calendar.DAY_OF_YEAR, pDaysOffset);
		return getMillis(calendar, 0, 0, 0, 0);
	}

	/**
	 * @param pYear
	 * @param pMonthIndex
	 * @param pDayOfMonth
	 * @return mid-night epoch milliseconds as per parameters
	 */
	public static long getMidNightMillis(int pYear, int pMonthIndex, int pDayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(pYear, pMonthIndex, pDayOfMonth);
		return getMillis(calendar, 0, 0, 0, 0);
	}

	/**
	 * @param pCalendar
	 * @param pHourOfDay
	 * @param pMinute
	 * @param pSecond
	 * @return epoch milliseconds of pCalendar after setting values given in
	 *         parameters
	 */
	private static long getMillis(Calendar pCalendar, int pHourOfDay, int pMinute, int pSecond, int pMillis) {
		pCalendar.set(Calendar.HOUR_OF_DAY, pHourOfDay);
		pCalendar.set(Calendar.MINUTE, pMinute);
		pCalendar.set(Calendar.SECOND, pSecond);
		pCalendar.set(Calendar.MILLISECOND, pMillis);
		return pCalendar.getTimeInMillis();
	}

	/**
	 * @param pEpochMillis
	 * @return int array with year, monthIndex and day-of-month
	 */
	public static int[] getDateFields(long pEpochMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(pEpochMillis);
		int[] fields = new int[3];
		fields[0] = calendar.get(Calendar.YEAR);
		fields[1] = calendar.get(Calendar.MONTH);
		fields[2] = calendar.get(Calendar.DAY_OF_MONTH);
		/*
		 * fields[3] = calendar.get(Calendar.HOUR_OF_DAY); fields[4] =
		 * calendar.get(Calendar.MINUTE); fields[5] =
		 * calendar.get(Calendar.SECOND); fields[6] =
		 * calendar.get(Calendar.MILLISECOND); fields[7] =
		 * calendar.get(Calendar.DAY_OF_WEEK);
		 */
		return fields;
	}

	/**
	 * @param pExtendedDateString
	 *            date in ZULU format YYYY-MM-ddTHH:mm:ssz <br/>
	 * 
	 * @note Any character can be used in place of '-', 'T', ':' and 'z'
	 * 
	 * @return epoch milliseconds for dateStr
	 */
	public static long parseExtendedDate(String pExtendedDateString) {
		/**
		 * 2013-01-01T23:59:59z <br/>
		 * 01234567890123456789
		 */
		int year = StringUtils.parseInt(pExtendedDateString, 0, 4);
		int month = StringUtils.parseInt(pExtendedDateString, 5, 7);
		int day = StringUtils.parseInt(pExtendedDateString, 8, 10);
		int hour = StringUtils.parseInt(pExtendedDateString, 11, 13);
		int minute = StringUtils.parseInt(pExtendedDateString, 14, 16);
		int second = StringUtils.parseInt(pExtendedDateString, 17, 19);

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1); // Jan is 1 but jan-index is 0
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTimeInMillis();
	}

	/**
	 * @param pDateMillisOne
	 * @param pDateMillisTwo
	 * @return
	 */
	public static boolean equalsIgnoreTime(long pDateMillisOne, long pDateMillisTwo) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(pDateMillisOne);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(pDateMillisTwo);
		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
			return false;
		}
		if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
			return false;
		}
		if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)) {
			return false;
		}
		return true;
	}
}
