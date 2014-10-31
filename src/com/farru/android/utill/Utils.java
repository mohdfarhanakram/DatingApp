/**
 *
 */
package com.farru.android.utill;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore.MediaColumns;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.farru.android.volley.AuthFailureError;

//9650275007;	

/**
 * All common methods are defined here as a public static.
 *
 * @author m.farhan
 */
public class Utils {

    /**
     * return base url remove '/' char from last of the url
     *
     * @param url
     * @return
     */
    public static String getBaseUrl(String url) {
        String baseUrl = url.substring(0, url.length());

        return baseUrl;
        // return Constants.BASE_URL;
    }

    /**
     * Check particular node is array.
     *
     * @param jsonObject
     * @return true if particular node is Array.
     */
    public static boolean isJsonArray(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONArray(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is object.
     *
     * @param jsonObject
     * @return true if particular node is Object.
     */
    public static boolean isJsonObject(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONObject(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is having particular key.
     *
     * @param jsonObject
     * @return true if particular node is having key.
     */
    public static boolean isJsonKeyAvailable(JSONObject jsonObject, String key) {

        return jsonObject.has(key);

    }


    /**
     * Display Toast message.
     *
     * @param activity
     * @param msgId
     */
    public static void showToast(final Activity activity, final int msgId) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(activity, activity.getString(msgId),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void displayToast(final Activity activity, String message) {
        if(activity!=null)
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }



    /**
     * convert dp into pixel
     *
     * @param context
     * @param dpValue value in dp
     * @return value in pixel
     */
    public static int convertToPx(Context context, int dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * @param text
     * @return text with underline
     */
    public static Spanned getUnderLine(String text) {
        return Html.fromHtml("<u>" + text + "</u>");
    }

    public static void showAlert(Context context, String title, String msg) {
        Builder builder = new Builder(context);
        builder.setPositiveButton("OK", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setMessage(msg);
        alertDialog.setTitle(title);
        alertDialog.show();

    }

    /**
     * Check Internet connection is available or not.
     *
     * @param context is the {@link Context} of the {@link Activity}.
     * @return <b>true</b> is Internet connection is available.
     */
    public static boolean isInternetAvailable(Context context) {
        boolean isInternetAvailable = false;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            if (networkInfo != null && (networkInfo.isConnected())) {
                isInternetAvailable = true;
            }
        } catch (Exception exception) {
            // Do Nothing
        }

        return isInternetAvailable;
    }

	/*public static HashMap<String, String> getDefaultHeader(
            Request request) {

		HashMap<String, String> header = new HashMap<String, String>();
		header.put("X-ROCKET-MOBAPI-PLATFORM","application/android.rocket.SHOP_MOBILEAPI_STAGING-v1.0+json");
		header.put("X-ROCKET-MOBAPI-TOKEN", "b5467071c82d1e0d88a46f6e057dbb88");
		header.put("X-ROCKET-MOBAPI-VERSION", "1.0");
		header.put("AUTO-LOGIN-TOKEN", request.getApiToken());
        //header.put("X-USER-DEVICE-TYPE", "mobile");

		*//*
         * header.put("Username", "rocket"); header.put("Password",
		 * "LetsRockJabong2012");
		 *//*

		// if (MainActivity.sessionId != null) {
		// header.put("session[id]", MainActivity.sessionId);
		// }
		return header;
	}*/

    /**
     * This method append User Id and password in URL
     *
     * @return urlWithUserIdPwd
     */
    /*
     * public static String getURLWithUidPwd(String url,boolean isRequired) {
	 * 
	 * String urlWithUidPwd = ""; if(!isRequired) return url; if(
	 * url.indexOf("http://") == 0){ url = url.replaceAll("http://", "");
	 * urlWithUidPwd =
	 * urlWithUidPwd.concat("http://").concat(Constants.UID_PWD).concat(url);
	 * return urlWithUidPwd; } else if( url.indexOf("https://") == 0){ url =
	 * url.replaceAll("https://", ""); urlWithUidPwd =
	 * urlWithUidPwd.concat("https://").concat(Constants.UID_PWD).concat(url);
	 * return urlWithUidPwd; }
	 * 
	 * return url;
	 * 
	 * }
	 */
    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String removeDecimalIfAnyFromPrice(String price) {
        price = price.split("\\.")[0];
        return price;
    }

    public static String convertStringtoTwoDecimalPlaces(String price) {
        return String.format("%.2f", Float.parseFloat(price));
    }

    public static boolean checkEmailValidation(String email, String pattern) {
        Pattern PATTERN = Pattern.compile(pattern);
        return PATTERN.matcher(email).matches();
    }

    public static boolean checkMobileValidation(String email, String pattern) {
        Pattern PATTERN = Pattern.compile(pattern);
        return PATTERN.matcher(email).matches();
    }

    public static boolean checkJCValidation(String jabongCredits) {
        String pattern = "^\\d+(\\.\\d{1,2})?$";
        Pattern PATTERN = Pattern.compile(pattern);
        return PATTERN.matcher(jabongCredits).matches();
    }

    public static ArrayList<String> getJsonKeysFromObject(JSONObject jsonObj) {
        ArrayList<String> keys = new ArrayList<String>();
        JSONArray it = jsonObj.names();
        for (int i = 0; i < it.length(); i++) {
            try {
                keys.add(i, it.getString(i));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return keys;

    }

    /**
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View listItem;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            listItem = listAdapter.getView(i, null, listView);
            listItem.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + Utils.convertToPx(listView.getContext(), 8);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildrenNew(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View listItem;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            listItem = listAdapter.getView(i, null, listView);
            listItem.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount())) + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * convert dp into pixel
     *
     * @param context
     * @param dpValue value in dp
     * @return value in pixel
     */
    public static float convertToPx(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (float) (dpValue * scale + 0.5f);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView, int moreHeight, int noOfCol) {


        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View gridItem = listAdapter.getView(0, null, gridView);
        gridItem.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
        gridItem.measure(0, 0);
        totalHeight += gridItem.getMeasuredHeight() + Utils.convertToPx(gridItem.getContext(), 3);
        ViewGroup.LayoutParams params = gridView.getLayoutParams();

        int noOfRow = (gridView.getAdapter().getCount() / noOfCol);
        noOfRow = noOfRow + ((gridView.getAdapter().getCount() % noOfCol) > 0 ? 1 : 0);
        totalHeight = totalHeight * noOfRow;
        params.height = (totalHeight + Utils.dpToPx(moreHeight));
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }


    public static String urlEncode(String str) {
        try {
            //return str.replaceAll(" ", "%20");
            //return str.replace(" ", "%20");
            //return URLEncoder.encode(str, "UTF-8");
            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
            return Uri.encode(str, "UTF-8");
        } catch (Exception e) {
            throw new IllegalArgumentException("failed to encode", e);
        }
    }


    public static void makeCall(Context mContext, String phoneNo) {

        try {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNo));
            mContext.startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(mContext, "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    

    public static boolean isSimCardReadyForCall(Context context) {

        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        if (telMgr != null && telMgr.getSimState() == TelephonyManager.SIM_STATE_READY) {
            //the device has a sim card
            return true;
        } else {
            //no sim card available
            return false;
        }
    }


    public static boolean rateTheApp(Context context) {

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        //Uri uri = Uri.parse("market://details?id=" +"com.killam.apartment");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        boolean isAppAvailableinMarket = true;
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            isAppAvailableinMarket = false;

        }
        return isAppAvailableinMarket;
    }

    public static boolean shareWithFriends(Context context) {

        boolean isReadyToShare = true;
        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Jabong";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Jabong Mobile App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey! I just discovered the Jabong Mobile App. It's awesome! \n http://www.jabong.com/mobile");
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } catch (Exception e) {
            isReadyToShare = false;

        }
        return isReadyToShare;
    }

   

    public static boolean isDebugMode() {
        return false;
    }

   


    public static String getFormatedMessage(String message, String replacement) {
        return message.replaceAll("%C%", replacement);
    }

    public static byte[] getBody(HashMap<String, String> map) throws AuthFailureError {
        if (map != null && map.size() > 0) {
            return encodeParameters(map, "UTF-8");
        }
        return null;
    }

    public static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public static String ordinal(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return "th";
            default:
                return sufixes[i % 10];
        }
    }

    public static String getMonth(String month) {
        String returnString = month;
        try {
            if (month.equalsIgnoreCase("01") || month.equalsIgnoreCase("1"))
                returnString = "January";
            else if (month.equalsIgnoreCase("02") || month.equalsIgnoreCase("2"))
                returnString = "February";
            else if (month.equalsIgnoreCase("03") || month.equalsIgnoreCase("3"))
                returnString = "March";
            else if (month.equalsIgnoreCase("04") || month.equalsIgnoreCase("4"))
                returnString = "April";
            else if (month.equalsIgnoreCase("05") || month.equalsIgnoreCase("5"))
                returnString = "May";
            else if (month.equalsIgnoreCase("06") || month.equalsIgnoreCase("6"))
                returnString = "June";
            else if (month.equalsIgnoreCase("07") || month.equalsIgnoreCase("7"))
                returnString = "July";
            else if (month.equalsIgnoreCase("08") || month.equalsIgnoreCase("8"))
                returnString = "August";
            else if (month.equalsIgnoreCase("09") || month.equalsIgnoreCase("9"))
                returnString = "September";
            else if (month.equalsIgnoreCase("10"))
                returnString = "October";
            else if (month.equalsIgnoreCase("11"))
                returnString = "November";
            else if (month.equalsIgnoreCase("12"))
                returnString = "December";
            else
                return returnString;
        }
        catch (Exception ex) {
        }

        return returnString;
    }

    public static String getShortMonth(String month) {
        String returnString = month;
        try {
            if (month.equalsIgnoreCase("01") || month.equalsIgnoreCase("1"))
                returnString = "Jan";
            else if (month.equalsIgnoreCase("02") || month.equalsIgnoreCase("2"))
                returnString = "Feb";
            else if (month.equalsIgnoreCase("03") || month.equalsIgnoreCase("3"))
                returnString = "Mar";
            else if (month.equalsIgnoreCase("04") || month.equalsIgnoreCase("4"))
                returnString = "Apr";
            else if (month.equalsIgnoreCase("05") || month.equalsIgnoreCase("5"))
                returnString = "May";
            else if (month.equalsIgnoreCase("06") || month.equalsIgnoreCase("6"))
                returnString = "Jun";
            else if (month.equalsIgnoreCase("07") || month.equalsIgnoreCase("7"))
                returnString = "Jul";
            else if (month.equalsIgnoreCase("08") || month.equalsIgnoreCase("8"))
                returnString = "Aug";
            else if (month.equalsIgnoreCase("09") || month.equalsIgnoreCase("9"))
                returnString = "Sep";
            else if (month.equalsIgnoreCase("10"))
                returnString = "Oct";
            else if (month.equalsIgnoreCase("11"))
                returnString = "Nov";
            else if (month.equalsIgnoreCase("12"))
                returnString = "Dec";
            else
                return returnString;
        } catch (Exception ex) {
        }
        return returnString;
    }

    public static String getCardExpireText(String expireDate) {
        String returnText = expireDate;
        if (expireDate.length() == 5)
            expireDate = "0" + expireDate;
        try {

            String month = expireDate.substring(0, 2);
            String date = expireDate.substring(2, 4);
            String year = expireDate.substring(4, expireDate.length());


            returnText = "Expire on " + date + ordinal(Integer.parseInt(date)) + " " + getMonth(month) + " 20" + year;


        } catch (Exception e) {

        }

        return returnText;
    }


    public static boolean isDebugMode(Context appContext, Context baseContext) {
        try {
            return (appContext.getPackageManager().getPackageInfo(
                    baseContext.getPackageName(), 0).applicationInfo.flags
                    & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }




    public static String getDeviceDensityName(Context context) {

        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";

            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";

            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";

            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";

            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";

            default:
                return "hdpi";

        }

    }

    public static int getCurrentDeviceOrientation(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        } else {
            return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
    }


    public static void setHasEmbeddedTabs(Object inActionBar, final boolean inHasEmbeddedTabs) {
        // get the ActionBar class
        Class<?> actionBarClass = inActionBar.getClass();

        // if it is a Jelly Bean implementation (ActionBarImplJB), get the super class (ActionBarImplICS)
        if ("android.support.v7.app.ActionBarImplJB".equals(actionBarClass.getName())) {
            actionBarClass = actionBarClass.getSuperclass();
        }

        try {
            // try to get the mActionBar field, because the current ActionBar is probably just a wrapper Class
            // if this fails, no worries, this will be an instance of the native ActionBar class or from the ActionBarImplBase class
            final Field actionBarField = actionBarClass.getDeclaredField("mActionBar");
            actionBarField.setAccessible(true);
            inActionBar = actionBarField.get(inActionBar);
            actionBarClass = inActionBar.getClass();
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (NoSuchFieldException e) {
        }

        try {
            // now call the method setHasEmbeddedTabs, this will put the tabs inside the ActionBar
            // if this fails, you're on you own <img src="http://www.blogc.at/wp-includes/images/smilies/icon_wink.gif" alt=";-)" class="wp-smiley">
            final Method method = actionBarClass.getDeclaredMethod("setHasEmbeddedTabs", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(inActionBar, new Object[]{inHasEmbeddedTabs});
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        }
    }


   

   

   




   
   
    public static String getNetworkClass(Context context) {
        String networkTypeName = "";
        if (isConnectedWifi(context)) {
            return "Wi-fi";
        } else if (isConnectedMobile(context)) {
            TelephonyManager mTelephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "GPRS";
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "EDGE";
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "Unknown";
            }
        }
        return networkTypeName;
    }

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @param
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

   


   

    public static void putStringinPrefs(Context mContext, String mKey, String mVal) {
        SharedPreferences.Editor prefsEditorr = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        try {
            prefsEditorr.putString(mKey, mVal.toString());
            prefsEditorr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method for creating standard url campaign params for  following cases
     * 1.If urlParams is in format c=campaignName
     * 2.If urlParams is in format utm=campaignName
     * 3.If urlParams is in format utm_campaign=campaignName
     * 4.If urlParams is campaignId
     * 5.If urlParams contains utm_campaign,utm_source,utm_medium keys
     *
     * It will return utm_campaign=campaignName&utm_source=campaignSource&utm_medium=campaignMedium
     *
     *
     *1
     * @param
     * @return
     */
    public static String getCampaignParamsUrl(String queryString)
    {
        final String UTM_SOURCE="utm_source";
        final String UTM_MEDIUM="utm_medium";
        final String UTM_CAMPAIGN="utm_campaign";
        if(queryString==null)
            return null;

        String campaignName=null,campaignSource=null,campaignMedium=null;

        if(!queryString.contains("&"))
        {
            if(!queryString.contains("="))
                //case 4 we received only campaignName
                campaignName=queryString;
            else
            {
                String[] split=queryString.split("=");
                if(split[0].equalsIgnoreCase(UTM_CAMPAIGN))
                    campaignName=split[1];
                else if(split[0].equalsIgnoreCase(UTM_MEDIUM))
                    campaignMedium=split[1];
                else if(split[0].equalsIgnoreCase(UTM_SOURCE))
                    campaignSource=split[1];

            }
        }
        String[] splittedPair=queryString.split("&");

        for(String query:splittedPair) {
            if(!query.contains("="))
                continue;
            String[] split = query.split("=");

            if (split[0].equalsIgnoreCase("utm")
                    || split[0].equalsIgnoreCase("c")
                    ||split[0].equalsIgnoreCase(UTM_CAMPAIGN) ) {
                campaignName = split[1];
                continue;

            }
            if (split[0].equalsIgnoreCase(UTM_SOURCE)) {
                campaignSource = split[1];
                continue;
            }
            if (split[0].equalsIgnoreCase(UTM_MEDIUM)) {
                campaignMedium = split[1];
                continue;
            }
        }
        StringBuffer finalUrl=new StringBuffer();
        if(!StringUtils.isNullOrEmpty(campaignName))
            finalUrl.append(UTM_CAMPAIGN).append("=").append(campaignName).append("&");
        if(!StringUtils.isNullOrEmpty(campaignMedium))
            finalUrl.append(UTM_MEDIUM+"=").append(campaignMedium).append("&");
        if(!StringUtils.isNullOrEmpty(campaignSource))
            finalUrl.append(UTM_SOURCE).append("=").append(campaignSource);

        if(finalUrl.length()>0 && finalUrl.charAt(finalUrl.length()-1)=='&')
            finalUrl.deleteCharAt(finalUrl.length()-1);

        return finalUrl.length()>0?finalUrl.toString():null;
    }


    /*
    to get MD5 of string...used for Vizury
     */
    public static String md5Java(String message){
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return digest;
    }

   
    public static String replacePlatformDensity(Context ctx,String rawUrl){
        String density = Utils.getDeviceDensityName(ctx);
        rawUrl =rawUrl.replace("PLATFORM","android").replace("RESOLUTION",density);
        return rawUrl;
    }
    public static String getStringFromAssets(String filename,Context ctx)
    {
        byte[] buffer=null;
        InputStream is;
        try {
            is =  ctx.getAssets().open(filename);

            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();

        } catch (Exception e) {
            Log.e("Utils", e.getMessage()+"");
            e.printStackTrace();
        }
        String bufferString = new String(buffer);

        return bufferString;

    }
    public static void enableDisableView(View view, boolean enabled) {
        if(view==null)
            return;
        view.setEnabled(enabled);

        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
    
    
    public static String getDeviceEMI(Context context){
    	TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    	return telephonyManager.getDeviceId().toString();
    }

    public static boolean isWebPSupportedDevice(){

        //Build.VERSION.ICE_CREAM_SANDWICH = 4.0

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return true;
        }

        return false;
    }
    
    
    
    public static String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
    
    public static void scaleImage(ImageView view, int boundBoxInDp)
    {
        // Get the ImageView and its bitmap
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

        // Get current dimensions
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public static int dpToPx(int dp,Context context)
    {
        float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
