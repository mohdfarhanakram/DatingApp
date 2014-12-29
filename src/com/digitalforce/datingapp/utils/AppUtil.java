package com.digitalforce.datingapp.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by FARHAN on 11/9/2014.
 */
public class AppUtil {
    public static String getFormatedDate(String msgTime){
        String formatedDate = msgTime;
        try{

            String str[] = msgTime.split(" ");
            String d = str[0];

            String date[] = d.split("-");

            formatedDate = Utils.getShortMonth(date[1])+" "+date[2]+", "+date[0];

        }catch(Exception e){
          e.printStackTrace();
        }

        return formatedDate;

    }

    public static int getImageOrientation(String imagePath){
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap getActualBitmap(String url,Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(getImageOrientation(url));
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }





}
