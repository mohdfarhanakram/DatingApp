package com.digitalforce.datingapp.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import java.util.regex.Pattern;

public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String ALPHABET_REGEX = "[a-zA-Z ]+";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_REGEX = "\\d{7,19}";
    private static final String TIME24HOURS_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private static final String DATE_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    private static final String VISA_REGEX = "^4[0-9]{12}(?:[0-9]{3})?$";
    private static final String MASTERCARD_REGEX = "^5[1-5][0-9]{14}$";
    private static final String AMERICANEXPRESS_REGEX = "^3[47][0-9]{13}$";
    private static final String DISCOVER_REGEX = "^6(?:011|5[0-9]{2})[0-9]{12}$";
    private static final String EXPIRYDATE_REGEX = "(?:0[1-9]|1[0-2])[0-9]{2}";
    private static final String URL_REGEX = "^(http:\\/\\/|https:\\/\\/|ftp:\\/\\/|www.)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z][a-z]{1,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String USERNAME_MSG = "invalid username";
    private static final String ALPHABET_MSG = "alphabets only";
    private static final String URL_MSG = "invalid url";
    private static final String PHONE_MSG = "7-19 Digits";
    private static final String PASS_MATCH = "Password Doesn't Match";
    private static final String USER_AUTHENTICATE = "Username or Password is incorrect.........";
    private static final String TIME_MSG = "HH:MM (24 HOURS)";
    private static final String VISA_MSG = "invalid VISA";
    private static final String MASTERCARD_MSG = "invalid MASTERCARD";
    private static final String AMERICANEXPRESS_MSG = "invalid AMERICANEXPRESS";
    private static final String DISCOVER_MSG = "invalid DISCOVER";
    private static final String EXPIRYDATE_MSG = "MMYY";
    private static final String DATE_MSG = "DD/MM/YYYY";

    
    // call this method when you need to check Card validation
    public static boolean isValidCard(String cardType, EditText editText, boolean required) {
    	boolean flag = false;
    	if (cardType.equals("Master Card"))
    		flag = isValid(editText, MASTERCARD_REGEX, MASTERCARD_MSG, required);
    	else if (cardType.equals("Visa"))
    		flag = isValid(editText, VISA_REGEX, VISA_MSG, required);
    	else if (cardType.equals("American Express Card"))
    		flag = isValid(editText, AMERICANEXPRESS_REGEX, AMERICANEXPRESS_MSG, required);
    	else if (cardType.equals("Discover"))
    		flag = isValid(editText, DISCOVER_REGEX, DISCOVER_MSG, required);
    	return flag;
    }

    
    // call this method when you need to check email validation
    public static boolean isExpiryDate(EditText editText, boolean required) {
        return isValid(editText, EXPIRYDATE_REGEX, EXPIRYDATE_MSG, required);
    }

    // call this method when you need to check email validation
    public static boolean isUrlAddress(EditText editText, boolean required) {
        return isValid(editText, URL_REGEX, URL_MSG, required);
    }


    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }
    // call this method when you need to check email validation
    public static boolean isAlphabet(EditText editText, boolean required) {
        return isValid(editText, ALPHABET_REGEX, ALPHABET_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    // Common Validation for character limit
    public static boolean isUserName(EditText editText,int min, int max, boolean required) {
    	String regex = "^[a-z0-9._-]{"+min+","+max+"}$";
    	//String msg = min+"-"+max+" Characters";
        return isValid(editText, regex, USERNAME_MSG, required);
    }
    
    // Common Validation for character limit
    public static boolean limit(EditText editText,int min, int max, boolean required) {
    	//String regex = "\\w{"+min+","+max+"}";
    	String regex = "^[a-z0-9._@#$%&*()!-]{"+min+","+max+"}$";
    	String msg = min+"-"+max+" Characters";
        return isValid(editText, regex, msg, required);
    }

    // call this method when you need to check time format validation
    public static boolean isTimeFormat(EditText editText, boolean required) {
        return isValid(editText, TIME24HOURS_REGEX, TIME_MSG, required);
    }

    // call this method when you need to check time format validation
    public static boolean isDateFormat(EditText editText, boolean required) {
        return isValid(editText, DATE_REGEX, DATE_MSG, required);
    }
    
    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
     	   int ecolor = Color.RED; // whatever color you want
   	    ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
   	    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errMsg);
   	    ssbuilder.setSpan(fgcspan, 0, errMsg.length(), 0);

        	editText.setError(ssbuilder);
            return false;
        };

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean isPasswordMatch(EditText editText1, EditText editText2) {
    	if(hasText(editText1) && hasText(editText2))
    	{
        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
 
        // length 0 means there is no text
        if (text1.equals(text2)) {
            return true;
        }
        else
        {
            editText2.setError(PASS_MATCH);
        }
    	}
    	
        return false;
    }
    public static boolean isUserAuthenticate(EditText editText1, EditText editText2, String str1, String str2) {
        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();

        // length 0 means there is no text
        if (!text1.equals(str1) || !text2.equals(str2)) {
            editText1.setError(USER_AUTHENTICATE);
            return false;
        }

        
        return true;
    }
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
        	
        	   int ecolor = Color.RED; // whatever color you want
        	    ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
        	    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(REQUIRED_MSG);
        	    ssbuilder.setSpan(fgcspan, 0, REQUIRED_MSG.length(), 0);
        	
            editText.setError(ssbuilder);
            return false;
        }

        return true;
    }
    
 
    
}


