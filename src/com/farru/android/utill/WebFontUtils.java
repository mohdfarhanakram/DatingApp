/**
 * 
 */
package com.farru.android.utill;

import java.io.InputStream;
import java.util.Hashtable;

import android.content.Context;
import android.util.Log;

/**
 * @author sachin.gupta
 */
public class WebFontUtils {

	private static final String					LOG_TAG	= "WebFontUtils";

	private static Hashtable<String, String>	mTemplatesCache;

	/**
	 * @param pContext
	 */
	private static void initialize() {
		if (mTemplatesCache == null) {
			mTemplatesCache = new Hashtable<String, String>();
		}
	}

	/**
	 * @param pContext
	 * @param pHtmlBodyText
	 * @param pTemplateAssetPath
	 * 
	 * @return HTML where pHtmlBodyText is embedded between <body> and </body>
	 *         of file at pTemplateAssetPath
	 */
	public static String wrapBodyIntoTemplate(Context pContext, String pHtmlBodyText, String pTemplateAssetPath) {

		try {
			String templatePageStr = getTemplateFromAssets(pContext, pTemplateAssetPath);
			int indexOfBodyEndTag = templatePageStr.indexOf("</body>");
			String embededFileStr = templatePageStr.substring(0, indexOfBodyEndTag);
			embededFileStr += extractHtmlBody(pHtmlBodyText) + "</body></html>";
			return embededFileStr;
		} catch (Exception e) {
			Log.e(LOG_TAG, "wrapBodyIntoTemplate: " + pHtmlBodyText);
			return pHtmlBodyText;
		}
	}

	/**
	 * @param pContext
	 * @param pTemplateAssetPath
	 * 
	 * @return
	 */
	private static String getTemplateFromAssets(Context pContext, String pTemplateAssetPath) {
		initialize();
		if (mTemplatesCache.contains(pTemplateAssetPath)) {
			return mTemplatesCache.get(pTemplateAssetPath);
		}
		String templatePageStr = null;
		try {
			InputStream inputStream = pContext.getAssets().open(pTemplateAssetPath);
			byte[] templateFileBytes = DataUtils.convertStreamToBytes(inputStream);
			templatePageStr = new String(templateFileBytes);
		} catch (Exception e) {
			Log.e(LOG_TAG, "initialize(): " + pTemplateAssetPath);
		}
		if (templatePageStr != null) {
			mTemplatesCache.put(pTemplateAssetPath, templatePageStr);
			return templatePageStr;
		} else {
			return "<html><body></body></html>";
		}
	}

	/**
	 * @param pHtmlPageText
	 * @return Html Body Text from within pHtmlPageText
	 */
	public static String extractHtmlBody(String pHtmlPageText) {
		int indexOfBodyStartTag = pHtmlPageText.indexOf("<body>");
		if (indexOfBodyStartTag != -1) {
			pHtmlPageText = pHtmlPageText.substring(indexOfBodyStartTag + 6);
		}
		int indexOfBodyEndTag = pHtmlPageText.indexOf("</body>");
		if (indexOfBodyEndTag != -1) {
			pHtmlPageText = pHtmlPageText.substring(0, indexOfBodyEndTag);
		}
		return pHtmlPageText;
	}
}