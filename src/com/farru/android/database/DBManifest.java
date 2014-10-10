package com.farru.android.database;


public interface DBManifest {

	/**
	 * Database name and version of the database.
	 */
	String		DATABASE_NAME				= "groupon_db";
	int			DATABASE_VERSION			= 1;

	/**
	 * Array of Table create queries...
	 */
	String[]	DB_SQL_CREATE_TABLE_QUERIES	= new String[] {
			 "Login_TABLE",
			 "Emp_Table"};
}
