package com.farru.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper class to execute create table queries when installed.
 * 
 */
public class BaseDbHelper extends SQLiteOpenHelper implements DBManifest {

	private static final String	LOG_TAG	= "BaseDbHelper";

	/**
	 * @param pContext
	 */
	public BaseDbHelper(Context pContext) {
		super(pContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(LOG_TAG, "Tables creation start.");
		int size = DB_SQL_CREATE_TABLE_QUERIES.length;
		for (int i = 0; i < size; i++) {
			db.execSQL(DB_SQL_CREATE_TABLE_QUERIES[i]);
		}
		Log.i(LOG_TAG, "Tables creation end.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(LOG_TAG, "DB upgrade.");
	}
}