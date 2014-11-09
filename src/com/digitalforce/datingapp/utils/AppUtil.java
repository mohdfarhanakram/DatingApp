package com.digitalforce.datingapp.utils;

import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;

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
}
