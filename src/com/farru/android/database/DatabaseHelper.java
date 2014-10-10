package com.farru.android.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	//The Android's default system path of your application database.

	/*public static String DB_PATH = "/mnt/sdcard/";
	public static String DB_NAME = "zonesoft.db";*/
	public static String DB_PATH = "/data/data/com.flipchase.android/databases/";
//	public static String DB_UPLOAD_NAME = "upload.db";
	public static String DB_NAME = "FlipchaseDb";
	private static int DB_VERSION = 1;
	private final Context myContext;
	private SQLiteDatabase myDataBase;
	
	
	/**
	  * Constructor
	  * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	  * @param context
	  */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}	
	 
	/**
	  * Creates a empty database on the system and rewrites it with your own database.
	  * */
	public void createDataBase() throws IOException{
		
		boolean dbExist = checkDataBase();
		Log.i("inside:"+"createDataBase()","dbExist"+dbExist);
		
		if(dbExist){
			//do nothing - database already exist
		}else{
			this.getReadableDatabase();
			
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
	
	
	
	
	
	public void createDataBaseForUploading() throws IOException{
		
		boolean dbExist = checkDataBase();
		Log.i("inside:"+"createDataBase()","dbExist"+dbExist);
		
		if(dbExist){
			//do nothing - database already exist
		}else{
			this.getReadableDatabase();
			
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
	 
	/**
	  * Check if the database already exist to avoid re-copying the file each time you open the application.
	  * @return true if it exists, false if it doesn't
	  */
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		
		try{
			
			/*File externalStorage = Environment.getExternalStorageDirectory();
			externalStorage.getAbsolutePath();
			File sdcard = Environment.getExternalStorageDirectory();
			String myPath = sdcard.getAbsolutePath() + File.separator+ "ZoneSoft" + File.separator + DB_NAME;*/
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			//database does't exist yet.
		}
		
		if(checkDB != null){
			checkDB.close();
		}
		
		return checkDB != null ? true : false;
	}
	 
	/**
	  * Copies your database from your local assets-folder to the just created empty database in the
	  * system folder, from where it can be accessed and handled.
	  * This is done by transfering bytestream.
	  * */
	private void copyDataBase() throws IOException{
		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		
		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}
		
		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	
	/*public void copyDBToSDCard() {
	    try {
	        InputStream myInput = new FileInputStream("/data/data/com.DxS.androidSunTec.visioapp/databases/"+DB_NAME);
	        
	        Log.i("sd card path: ", ""+Environment.getExternalStorageDirectory().getPath().toString());

	        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+DB_NAME);
	        if (!file.exists()){
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                Log.i("FO","File creation failed for " + file);
	            }
	        }

	        OutputStream myOutput = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/"+DB_NAME);

	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = myInput.read(buffer))>0){
	            myOutput.write(buffer, 0, length);
	        }

	        //Close the streams
	        myOutput.flush();
	        myOutput.close();
	        myInput.close();
	        Log.i("FO","copied");

	    } catch (Exception e) {
	        Log.i("FO","exception="+e);
	    }


	}*/
	
	
	/**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 * */
	public boolean importDatabase(String dbPath) throws IOException {

	    // Close the SQLiteOpenHelper so it will commit the created empty
	    // database to internal storage.
	 //   close();
	    File newDb = new File(dbPath);
	//    File oldDb = new File(DB_PATH+DB_UPLOAD_PATH+DB_NAME);
	    File oldDb = new File(DB_PATH+DB_NAME);
	    if (newDb.exists()) {
	    	Log.i("newDb.exists(): ", "creating");
	        FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
	        // Access the copied database so SQLiteHelper will cache it and mark
	        // it as created.
	        getWritableDatabase().close();
	        return true;
	    }
	    return false;
	}
	
	public void openDataBase() throws SQLException{ 
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE/*.OPEN_READONLY*/);
	}
	
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		super.close();
		if(myDataBase!=null)
			myDataBase.close();
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.i("executing...", "Query");
	//	db.execSQL(SCRIPT_CREATE_GN_LOCATION_TABLE);
	}
	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	 
	}	 
}