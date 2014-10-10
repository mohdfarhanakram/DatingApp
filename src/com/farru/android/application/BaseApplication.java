package com.farru.android.application;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.digitalforce.datingapp.BuildConfig;
import com.farru.android.database.BaseDbHelper;

/**
 * This class holds some application-global instances.
 */
public class BaseApplication extends Application {

	private static final String	LOG_TAG							= "BaseApplication";

	private BaseDbHelper		mBaseDbHelper;
	private SQLiteDatabase		mWritableDatabase;

	private Timer				mActivityTransitionTimer;
	private TimerTask			mActivityTransitionTimerTask;
	public boolean				mAppInBackground;
	private final long			MAX_ACTIVITY_TRANSITION_TIME_MS	= 2000;

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "onCreate()");
		}
	}

	/**
	 * @return the mIsAppInBackground
	 */
	public boolean isAppInBackground() {
		return mAppInBackground;
	}

	/**
	 * @param isAppInBackground
	 *            value to set for mIsAppInBackground
	 */
	public void setAppInBackground(boolean isAppInBackground) {
		mAppInBackground = isAppInBackground;
		onAppStateSwitched(isAppInBackground);
	}

	/**
	 * @note subclass can override this method for this callback
	 * 
	 * @param isAppInBackground
	 */
	protected void onAppStateSwitched(boolean isAppInBackground) {
		// nothing to do here in base application class 
	}

	/**
	 * should be called from onResume of each activity of application
	 */
	public void onActivityResumed() {
		if (this.mActivityTransitionTimerTask != null) {
			this.mActivityTransitionTimerTask.cancel();
		}

		if (this.mActivityTransitionTimer != null) {
			this.mActivityTransitionTimer.cancel();
		}
		setAppInBackground(false);
	}

	/**
	 * should be called from onPause of each activity of app
	 */
	public void onActivityPaused() {
		this.mActivityTransitionTimer = new Timer();
		this.mActivityTransitionTimerTask = new TimerTask() {
			public void run() {
				setAppInBackground(true);
				if (BuildConfig.DEBUG) {
					Log.i(LOG_TAG, "None of our activity is in foreground.");
				}
			}
		};

		this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask, MAX_ACTIVITY_TRANSITION_TIME_MS);
	}

	/**
	 * Get the BaseDbHelper instance.
	 * 
	 * @return mBaseDbHelper
	 */
	public BaseDbHelper getBaseDbHelper() {
		if (mBaseDbHelper == null || mWritableDatabase == null) {
			mBaseDbHelper = new BaseDbHelper(this);
			mWritableDatabase = mBaseDbHelper.getWritableDatabase();
		}
		return mBaseDbHelper;
	}

	/**
	 * Get the database instance.
	 * 
	 * @return mWritableDatabase
	 */
	public SQLiteDatabase getWritableDbInstance() {
		if (mBaseDbHelper == null || mWritableDatabase == null) {
			mBaseDbHelper = new BaseDbHelper(this);
			mWritableDatabase = mBaseDbHelper.getWritableDatabase();
		}
		return mWritableDatabase;
	}
}
