package com.farru.android.utill;

import java.io.FileInputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

/**
 * This class is intended to have methods, which uses android specific file IO
 * APIs. If any method uses other APIs, that should be added to any other more
 * suitable Utility class.
 */
public class AndroidFileUtils {

	private static final String	LOG_TAG	= "AndroidFileUtils";

	/**
	 * @param pContext
	 * @param pFileName
	 * @param pFileData
	 * @return
	 */
	public static boolean createAppPrivateFile(Context pContext, String pFileName, byte[] pFileData) {
		try {
			OutputStream os = pContext.openFileOutput(pFileName, Context.MODE_PRIVATE);
			os.write(pFileData);
			os.close();
			return true;
		} catch (Exception e) {
			Log.e(LOG_TAG, "createAppPrivateFile: " + pFileName, e);
			return false;
		}
	}

	/**
	 * @param pContext
	 * @param pFileName
	 * @return
	 */
	public static byte[] readAppPrivateFile(Context pContext, String pFileName) {
		try {
			FileInputStream fileInputStream = pContext.openFileInput(pFileName);
			return DataUtils.convertStreamToBytes(fileInputStream);
		} catch (Exception e) {
			Log.e(LOG_TAG, "readAppPrivateFile: " + pFileName);
			return null;
		}
	}

	/**
	 * @param pContext
	 * @param pFileName
	 * @return
	 */
	public static boolean deleteAppPrivateFile(Context pContext, String pFileName) {
		try {
			return pContext.deleteFile(pFileName);
		} catch (Exception e) {
			Log.e(LOG_TAG, "deleteAppPrivateFile: " + pFileName);
			return false;
		}
	}

	/**
	 * @param pContext
	 * @param pFileName
	 * @return
	 */
	public static boolean isAppPrivateFileExists(Context pContext, String pFileName) {
		try {
			String[] fileNamesArr = pContext.fileList();
			if (fileNamesArr == null || fileNamesArr.length == 0) {
				return false;
			}
			for (String existingFileName : fileNamesArr) {
				if (existingFileName.equals(pFileName)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			Log.e(LOG_TAG, "isAppPrivateFileExists: " + pFileName);
			return false;
		}
	}
}