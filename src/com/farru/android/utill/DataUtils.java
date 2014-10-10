package com.farru.android.utill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This class is intended to have methods, which uses java IO APIs.
 * If any method uses other APIs, that should be added to any other more suitable Utility class.
 * Even android.util.Log is not used in this class.
 */
public class DataUtils
{
	/**
	 * @param pInputStream
	 * @return
	 */
	public static byte[] convertStreamToBytes(InputStream pInputStream) {
		int read = 0;
        byte[] data = new byte[1024];	/** data will be read in chunks */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while( (read=pInputStream.read(data)) != -1 ) {
            	baos.write(data,0,read); 
            }
            return baos.toByteArray();
        } catch(Exception e) { e.printStackTrace(); }
        return null;
	}
	
	/**
	 * @param pFilePath
	 * @return
	 */
	public static FileInputStream getFileInputStream( String pFilePath ) {
		try { return new FileInputStream(new File(pFilePath)); }
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	/**
	 * @param pFilePath
	 * @return
	 */
	public static byte[] getFileData( String pFilePath ) {
		try { return convertStreamToBytes(getFileInputStream(pFilePath)); }
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
}
