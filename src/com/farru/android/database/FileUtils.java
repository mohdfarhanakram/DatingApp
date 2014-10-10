package com.farru.android.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
/**
 * Creates the specified <code>toFile</code> as a byte for byte copy of the
 * <code>fromFile</code>. If <code>toFile</code> already exists, then it
 * will be replaced with a copy of <code>fromFile</code>. The name and path
 * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
 * <br/>
 * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
 * this function.</i>
 * 
 * @param fromFile
 *            - FileInputStream for the file to copy from.
 * @param toFile
 *            - FileInputStream for the file to copy to.
 */
public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
    FileChannel fromChannel = null;
    FileChannel toChannel = null;
    try {
        fromChannel = fromFile.getChannel();
        toChannel = toFile.getChannel();
        fromChannel.transferTo(0, fromChannel.size(), toChannel);
    } finally {
        try {
            if (fromChannel != null) {
                fromChannel.close();
            }
        } finally {
            if (toChannel != null) {
                toChannel.close();
            }
        }
    }
}



public void copyFromDataPackgeToSdCard(Context context) throws IOException {
    try {
        File sdCard = Environment.getExternalStorageDirectory();
        File appDataDir = Environment.getDataDirectory();
        if (sdCard.canWrite()) {
            /*String currentDBPath = "//data//"
                    + context.getPackageName() + "//databases//"
                    + DatabaseHelper.DB_NAME;*/
        	
        	String currentDBPath = DatabaseHelper.DB_PATH+DatabaseHelper.DB_NAME;
        	
            String backupDBPath = DatabaseHelper.DB_NAME;
            File currentDatabase = new File(appDataDir, currentDBPath);
            File backupDatabase = new File(sdCard, backupDBPath);

            if (currentDatabase.exists()) {
                FileChannel src = new FileInputStream(currentDatabase).getChannel();
                FileChannel dst = new FileOutputStream(backupDatabase).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        }
    } catch (Exception e) {
        Log.e("copyFromDataPackgeToSdCard", e.getMessage());
    }
}







public static String[] getStorageDirectories()
{
    String[] dirs = null;
    BufferedReader bufReader = null;
    try {
        bufReader = new BufferedReader(new FileReader("/proc/mounts"));
        ArrayList list = new ArrayList();
        String line;
        while ((line = bufReader.readLine()) != null) {
            if (line.contains("vfat") || line.contains("/mnt")) {
                StringTokenizer tokens = new StringTokenizer(line, " ");
                String s = tokens.nextToken();
                s = tokens.nextToken(); // Take the second token, i.e. mount point

                if (s.equals(Environment.getExternalStorageDirectory().getPath())) {
                    list.add(s);
                }
                else if (line.contains("/dev/block/vold")) {
                    if (!line.contains("/mnt/secure") && !line.contains("/mnt/asec") && !line.contains("/mnt/obb") && !line.contains("/dev/mapper") && !line.contains("tmpfs")) {
                        list.add(s);
                    }
                }
            }
        }

        dirs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            dirs[i] = (String) list.get(i);
        }
    }
    catch (FileNotFoundException e) {}
    catch (IOException e) {}
    finally {
        if (bufReader != null) {
            try {
                bufReader.close();
            }
            catch (IOException e) {}
    }

    return dirs;
}
}






}