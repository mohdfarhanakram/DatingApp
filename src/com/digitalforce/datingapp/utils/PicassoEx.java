package com.digitalforce.datingapp.utils;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.farru.android.picasso.Picasso;
import com.farru.android.utill.Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 201101044 on 4/10/14.
 */
public class PicassoEx {
    private static Picasso mPicasso;

    public static Picasso getPicasso(Context context) {
        if (null == mPicasso) {
         //   Picasso.Builder builder = new Picasso.Builder(context);
          //  builder.executor(PicassoThreadExecutor.getThreadPoolExecutor());
          //  mPicasso = builder.build();
            mPicasso=Picasso.with(context);

        }
        return mPicasso;
    }

    /*public static Bitmap getBitmap(String url,Context context)
    {
        FileCache fileCache=new FileCache(context);
        MemoryCache memoryCache=new MemoryCache();
        File f=fileCache.getFile(url);
        //from SD cache
        //CHECK : if trying to decode file which not exist in cache return null
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        // Download image file from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            // Constructs a new FileOutputStream that writes to file
            // if file not exist then it will create file
            OutputStream os = new FileOutputStream(f);
            // See Utils class CopyStream method
            // It will each pixel from input stream and
            // write pixels to output stream (file)
            Utils.(is, os);
            os.close();
            conn.disconnect();
            //Now file created and going to resize file with defined height
            // Decodes image and scales it to reduce memory consumption
            b = decodeFile(f);
            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //Decodes image and scales it to reduce memory consumption
    private static Bitmap decodeFile(File f){

        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            //Find the correct scale value. It should be the power of 2.
            // Set width/height of recreated image
            final int REQUIRED_SIZE=85;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with current scale values
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
