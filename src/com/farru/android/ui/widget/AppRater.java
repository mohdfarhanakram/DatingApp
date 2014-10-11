package com.farru.android.ui.widget;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;

import com.digitalforce.datingapp.BuildConfig;
import com.digitalforce.datingapp.R;
import com.farru.android.ui.Events;
import com.farru.android.ui.IScreen;
import com.farru.android.utill.ConnectivityUtils;
import com.farru.android.utill.ToastUtils;

/**
 * @author m.farhan
 */
@SuppressLint("NewApi")
public class AppRater {

	private static final String	LOG_TAG								= "AppRater";

	private static final int	MIN_SESSIONS_TO_SHOW_APP_RATER		= 6;
	private static final int	MIN_SESSIONS_TO_CLEAR_OLD_SESSIONS	= 100;

	// Keys to store required values in SharedPreferences
	private static final String	_APP_RATER_PREFS					= "_app_rater_prefs";
	private static final String	PREF_IS_RATE_US_CLICKED				= "pref_is_rate_us_clicked";
	private static final String	PREF_SESSION_COUNT					= "pref_session_count";

	/**
	 * singleton
	 */
	private static AppRater		mSingletonInstance;

	/**
	 * This method can be called only after calling initialize()
	 * 
	 * @return mSingletonInstance
	 */
	public static AppRater getInstance() {
		if (mSingletonInstance == null) {
			throw new RuntimeException("AppRater is not initialized.");
		}
		return mSingletonInstance;
	}

	/**
	 * @param pContext
	 */
	public static void initialize(Context pContext) {
		if (mSingletonInstance == null) {
			mSingletonInstance = new AppRater(pContext);
		}
	}

	/**
	 * App Name and Google Play URLs are to be initialized with context
	 */
	private final String			mAppName;
	private final String			mPlayAppUrl;
	private final String			mPlayBrowserUrl;
	private final SharedPreferences	mPreferences;

	private AlertDialog				mAppRaterDialog;
	private boolean					mIsEnabled;

	/**
	 * @param pContext
	 */
	private AppRater(Context pContext) {
		pContext = pContext.getApplicationContext();

		// appPackageName as in manifest
		String appPackageName = pContext.getPackageName(); // package
		// Google Play URLs
		mPlayAppUrl = "market://details?id=" + appPackageName;
		mPlayBrowserUrl = "https://play.google.com/store/apps/details?id" + appPackageName;

		mPreferences = pContext.getSharedPreferences(appPackageName + _APP_RATER_PREFS, Context.MODE_PRIVATE);

		mAppName = pContext.getString(R.string.app_name);
	}

	/**
	 * @param mIsEnabled
	 *            the mIsEnabled to set
	 */
	public void setEnabled(boolean pIsEnabled) {
		this.mIsEnabled = pIsEnabled;
	}

	/**
	 * @param pActivity
	 *            or null if dialog is not to be shown
	 */
	public void startSession(Activity pActivity) {
		int newCount = 1 + mPreferences.getInt(PREF_SESSION_COUNT, 0);

		if (newCount > MIN_SESSIONS_TO_CLEAR_OLD_SESSIONS) {
			setRateUsClicked(false);
			setSessionCount(0);
		} else {
			setSessionCount(newCount);
		}

		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "Session Count: " + newCount);
		}
		if (pActivity != null) {
			showDialog(pActivity, true);
		}
	}

	/**
	 * @param pActivity
	 * @param pBasedOnSessionCounts
	 */
	public void showDialog(Activity pActivity, boolean pAsPerPrefs) {
		if (!mIsEnabled) {
			return;
		}
		if (pAsPerPrefs && !canShowDialog()) {
			return;
		}
		if (mAppRaterDialog != null && mAppRaterDialog.isShowing()) {
			mAppRaterDialog.dismiss();
			mAppRaterDialog = null;
		}
		if (!pActivity.isFinishing()) {
			mAppRaterDialog = createDialog(pActivity);
			mAppRaterDialog.show();
		}
	}

	/**
	 * @return
	 */
	private boolean canShowDialog() {
		if (mPreferences.getBoolean(PREF_IS_RATE_US_CLICKED, false)) {
			if (BuildConfig.DEBUG) {
				Log.i(LOG_TAG, "canShowDialog(). Rate Us has already been clicked.");
			}
			return false;
		}
		int sessionCount = mPreferences.getInt(PREF_SESSION_COUNT, 0);
		if (sessionCount < MIN_SESSIONS_TO_SHOW_APP_RATER) {
			if (BuildConfig.DEBUG) {
				Log.i(LOG_TAG, "showDialog() Session Count: " + sessionCount);
			}
			return false;
		}
		return true;
	}

	/**
	 * @param pActivity
	 * @return AlertDialog
	 */
	private AlertDialog createDialog(final Activity pActivity) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(pActivity);
		builder.setTitle("Rate " + mAppName);
		builder.setMessage("If you enjoyed using the App, please rate us on Google Play");
		builder.setPositiveButton("Rate Us", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				boolean isPlayOpened = openGooglePlay(pActivity);
				if (pActivity instanceof IScreen) {
					((IScreen) pActivity).onEvent(Events.APP_RATER_DIALOG, isPlayOpened);
				}
			}
		});

		builder.setNegativeButton("Remind me later", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				setSessionCount(0);
			}
		});

		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				setSessionCount(0);
			}
		});

		// Create the AlertDialog object and return it
		return builder.create();
	}

	/**
	 * @param
	 */
	public boolean openGooglePlay(Activity pActivity) {
		if (!ConnectivityUtils.isNetworkEnabled(pActivity)) {
			ToastUtils.showToast(pActivity, "No network");
			return false;
		}
		boolean success = false;
		Intent actionViewIntent = new Intent(Intent.ACTION_VIEW);
		try {
			Uri uri1 = Uri.parse(mPlayAppUrl);
			actionViewIntent.setData(uri1);
			pActivity.startActivity(actionViewIntent);
			success = true;
		} catch (Exception e) {
			ToastUtils.showToast(pActivity, "Unable to open Play application.");
		}

		if (!success) {
			Uri uri2 = Uri.parse(mPlayBrowserUrl);
			actionViewIntent.setData(uri2);
			try {
				pActivity.startActivity(actionViewIntent);
				success = true;
			} catch (Exception e) {
				ToastUtils.showToast(pActivity, "Unable to open Google Play on your browser.");
			}
		}
		if (success) {
			setRateUsClicked(true);
		}
		return success;
	}

	/**
	 * @param pSessionCount
	 */
	@SuppressLint("NewApi")
	private void setSessionCount(int pSessionCount) {
		Editor _editor = mPreferences.edit();
		_editor.putInt(PREF_SESSION_COUNT, pSessionCount);
		_editor.apply();
	}

	/**
	 * PREF_SESSION_COUNT will be set to (REQ_SESSIONS-1) to show dialog in next
	 * session.
	 */
	public void saturateSessionCount() {
		setSessionCount(MIN_SESSIONS_TO_SHOW_APP_RATER - 1);
	}

	/**
	 * @param pIsRateUsClicked
	 */
	private void setRateUsClicked(boolean pIsRateUsClicked) {
		Editor _editor = mPreferences.edit();
		_editor.putBoolean(PREF_IS_RATE_US_CLICKED, pIsRateUsClicked);
		_editor.apply();
	}
}
