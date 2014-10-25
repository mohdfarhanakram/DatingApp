package com.digitalforce.datingapp.utils;

import android.content.Context;

import com.farru.android.picasso.Picasso;

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

}
