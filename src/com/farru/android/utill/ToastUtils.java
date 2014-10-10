package com.farru.android.utill;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Class is designed for following purposes- 1. Immediate display of new toast,
 * even if previous is being displayed. 2. Changing position of all toasts
 * through-out application.
 * 
 * It will work as intended, only if all toasts in application are shown using
 * it.
 */
public class ToastUtils {

	/**
	 * This field will be used if any positive value is assigned to it.
	 */
	private static int			TOAST_MARGIN_BOTTOM_DP	= 0;

	/**
	 * This field will be used if any positive value is assigned to it.
	 */
	private static final int	TOAST_MIN_SHOW_MILLIS	= 1000;

	/**
	 * mSingletonToast will be Singleton in application.
	 */
	private static Toast		mSingletonToast;

	/**
	 * These fields will be used to concat messages if required.
	 */
	private static String		mLastShownMesssage;
	private static long			mLastShownAtMillis;

	/**
	 * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
	 * 
	 * @param pContext
	 * @param pMessage
	 */
	public static void showToast(Context pContext, String pMessage) {
		showToast(pContext, pMessage, false);
	}

	/**
	 * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
	 * 
	 * @note If pCanAppendToLastToast is true and last shown toast is not shown
	 *       for TOAST_MIN_SHOW_MILLIS then pMessage will be appended to it.
	 * 
	 * @param pContext
	 * @param pMessage
	 * @param pCanAppendToLastToast
	 */
	public static void showToast(Context pContext, String pMessage, boolean pCanAppendToLastToast) {
		if (!pCanAppendToLastToast) {
			mLastShownAtMillis = 0;
		}
		showToast(pContext, pMessage, Toast.LENGTH_LONG);
	}

	/**
	 * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
	 * 
	 * @param pContext
	 * @param pPrefix
	 * @param pMessage
	 * @param pFallback
	 */
	public static void showToast(Context pContext, String pPrefix, String pMessage, String pFallback) {
		if (StringUtils.isNullOrEmpty(pMessage)) {
			showToast(pContext, pPrefix + pFallback);
		} else {
			showToast(pContext, pPrefix + pMessage);
		}
	}

	/**
	 * Shows the pMessage as an immediate Toast for pDuration.
	 * 
	 * @param pContext
	 * @param pMessage
	 * @param pDuration
	 */
	public static void showToast(Context pContext, String pMessage, int pDuration) {
		/**
		 * If pMessage is null or blank, new toast will not be shown.
		 */
		if (pMessage == null || pMessage.trim().length() == 0) {
			return;
		}

		if (pDuration != Toast.LENGTH_LONG && pDuration != Toast.LENGTH_SHORT) {
			pDuration = Toast.LENGTH_LONG;
		}

		if (mSingletonToast == null) {
			mSingletonToast = Toast.makeText(pContext.getApplicationContext(), pMessage, pDuration);

			if (TOAST_MARGIN_BOTTOM_DP > 0) {
				float density = pContext.getResources().getDisplayMetrics().density;
				int yOffSet = (int) (density * TOAST_MARGIN_BOTTOM_DP);
				mSingletonToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, yOffSet);
			}

		}
		mSingletonToast.setDuration(pDuration);
		if (mLastShownAtMillis == 0) {
			mLastShownMesssage = pMessage;
		} else if (System.currentTimeMillis() - mLastShownAtMillis < TOAST_MIN_SHOW_MILLIS) {
			if (!mLastShownMesssage.contains(pMessage)) {
				mLastShownMesssage = mLastShownMesssage + "\n" + pMessage;
			}
		} else {
			mLastShownMesssage = pMessage;
		}
		mLastShownAtMillis = System.currentTimeMillis();
		mSingletonToast.setText(mLastShownMesssage);
		mSingletonToast.show();

	}

	/**
	 * Removes the currently displaying toast.
	 */
	public static void removeToast() {
		if (mSingletonToast == null) {
			mSingletonToast.cancel();
		}
	}
}
