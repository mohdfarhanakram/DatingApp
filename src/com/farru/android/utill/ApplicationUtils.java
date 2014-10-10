package com.farru.android.utill;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

/**
 * This class is only to hold manifest and apk related methods.
 */
public class ApplicationUtils {

	private static String	TAG	= "ApplicationUtils";

	/**
	 * @param pContext
	 * @param pFlags
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context pContext, int pFlags) {
		try {
			PackageManager _pm = pContext.getPackageManager();
			return _pm.getPackageInfo(pContext.getPackageName(), pFlags);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getPackageInfo()", e);
		} catch (Exception e) {
			Log.e(TAG, "getPackageInfo()", e);
		}
		return null;
	}

	/**
	 * @param pContext
	 */
	public static void printHashKey(Context pContext) {
		try {
			PackageInfo info = getPackageInfo(pContext, PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String hashKey = new String(Base64.encode(md.digest(), 0));
				Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
			}
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "printHashKey()", e);
		} catch (Exception e) {
			Log.e(TAG, "printHashKey()", e);
		}
	}

	/**
	 * @param pContext
	 * @return
	 */
	public static int getAppVersionCode(Context pContext) {
		try {
			PackageManager packageManager = pContext.getPackageManager();
			// TODO - search for a better flag
			PackageInfo packageInfo = packageManager.getPackageInfo(pContext.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getPackageInfo()", e);
		} catch (Exception e) {
			Log.e(TAG, "getPackageInfo()", e);
		}
		return 0;
	}

	/**
	 * @param pContext
	 * @return
	 */
	public static String getAppVersionName(Context pContext) {
		try {
			PackageManager packageManager = pContext.getPackageManager();
			// TODO - search for a better flag
			PackageInfo packageInfo = packageManager.getPackageInfo(pContext.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getPackageInfo()", e);
		} catch (Exception e) {
			Log.e(TAG, "getPackageInfo()", e);
		}
		return "";
	}
}
