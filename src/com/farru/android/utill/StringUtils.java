package com.farru.android.utill;

import java.net.URLDecoder;
import java.util.Collection;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * Utility class useful when dealing with string objects. This class is a
 * collection of static functions it is not allowed to create instances of this
 * class
 */
public abstract class StringUtils {

	private static final String	LOG_TAG			= "StringUtils";

	public static final String	CHARSET_UTF_8	= "UTF-8";
	public static final String	MIME_TEXT_HTML	= "text/html";

	public static final String	EMAIL_REGEX		= "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/**
	 * @param pStr
	 *            String object to be tested.
	 * @returns true if the given string is null or empty or contains spaces
	 *          only.
	 */
	public static boolean isNullOrEmpty(final String pStr) {
		return pStr == null || pStr.trim().length() == 0 || pStr.trim().equalsIgnoreCase("null");
	}

	/**
	 * @param pEmail
	 * @param pAllowBlank
	 * @return true if pEmail matches with {@link StringUtils#EMAIL_REGEX},
	 *         false otherwise
	 */
	public static boolean isValidEmail(String pEmail, boolean pAllowBlank) {
		if (pAllowBlank && isNullOrEmpty(pEmail)) {
			return true;
		}
		Pattern validRegexPattern = Pattern.compile(EMAIL_REGEX);
		return validRegexPattern.matcher(pEmail).matches();
	}

	/**
	 * @param pStr
	 * @param pStartIndex
	 *            or -1 to parse from complete pStr
	 * @param pEndIndex
	 * @return int value, parsed from pStr or a substring of pStr
	 */
	public static int parseInt(String pStr, int pStartIndex, int pEndIndex) {
		if (pStr == null) {
			return 0;
		}
		try {
			if (pStartIndex == -1) {
				return Integer.parseInt(pStr);
			} else {
				return Integer.parseInt(pStr.substring(pStartIndex, pEndIndex));
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "parseInt() pStr: " + pStr + ", Start: " + pStartIndex + ", End: " + pEndIndex);
			return 0;
		}
	}

	/**
	 * This method checks and ensure http/https protocol in URL
	 * 
	 * @param url
	 * @return formattedUrl
	 */
	public static String getFormattedURL(String url) {
		if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0) {
			return url;
		} else if (url.indexOf("://") == 0) {
			return "http" + url;
		} else if (url.indexOf("//") == 0) {
			return "http:" + url;
		} else {
			return "http://" + url;
		}
	}

	/**
	 * @param pStr
	 *            .
	 * @returns
	 */
	public static String firstLetterToUpperCase(String pWord) {
		pWord = pWord == null ? "" : pWord;
		String output = "";
		for (int i = 0; i < pWord.length(); i++) {
			if (i == 0) {
				output += Character.toUpperCase(pWord.charAt(i));
			} else {
				output += Character.toLowerCase(pWord.charAt(i));
			}
		}
		return output;
	}

	/**
	 * @param phone
	 * @param startsWithPlus
	 * @param minDigits
	 * @note maxDigit validation can be implemented by XML
	 * @return
	 */
	public static boolean isValidMobileNumber(String pMobileNumber, boolean pPlusSignNeeded, int pMinLength) {
		if (StringUtils.isNullOrEmpty(pMobileNumber)) {
			return false;
		}
		pMobileNumber = pMobileNumber.trim();
		if (pPlusSignNeeded && !pMobileNumber.startsWith("+")) {
			return false;
		}
		if (pMobileNumber.length() < pMinLength) {
			return false;
		}
		return true;
	}

	/**
	 * check whether permission exits in active session or not.
	 * 
	 * @param subset
	 * @param superset
	 * @return
	 */
	public static boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param pEncodedString
	 * @param pIsUrlEncoding
	 * @return
	 */
	public static String decode(String pEncodedString, String pCharsetName, boolean pIsUrlEncoding) {
		try {
			if (pIsUrlEncoding) {
				return URLDecoder.decode(pEncodedString, pCharsetName);
			} else {
				return new String(pEncodedString.getBytes(), pCharsetName);
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * return formated float as 232.00000000 should be 232, 0.180000000001
	 * should be 0.18
	 * 
	 * @param pInputFloat
	 * @return
	 */
	public static String getFormatDecimalAmount(float pInputFloat) {
		return getFormatDecimalAmount(pInputFloat, 2);
	}

	/**
	 * return formated float as 232.00000000 should be 232, 0.180000000001
	 * should be 0.18
	 * 
	 * @param pInputFloat
	 * @param pNeededDigitsAfterDecimal
	 * @return
	 */
	public static String getFormatDecimalAmount(float pInputFloat, int pNeededDigitsAfterDecimal) {
		if (pInputFloat == (int) pInputFloat || pNeededDigitsAfterDecimal <= 0) {
			return String.format("%d", (int) pInputFloat);
		} else {
			return String.format("%1." + pNeededDigitsAfterDecimal + "f", pInputFloat);
		}
	}
}
