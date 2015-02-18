package com.digitalforce.datingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.fragments.ImageFragment;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.view.UserLocationActivity;
import com.digitalforce.datingapp.widgets.RoundedImageView;
import com.farru.android.utill.StringUtils;

import java.util.ArrayList;

/**
 * Created by FARHAN on 2/17/2015.
 */


public class GalleryAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> urls;

    public GalleryAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position=position%urls.size();
        View view = getView(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urls==null?0:urls.size();
        /*if(urls.size()>0)
            return Integer.MAX_VALUE;
        else
            return 0;*/
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public View getView(int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        String url = urls.get(position);
        imageView.setTag(urls);
        picassoLoad(url, imageView);
        return imageView;

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private void picassoLoad(String url, ImageView imageView) {
        PicassoEx.getPicasso(context).load(url).error(R.drawable.bg).placeholder(R.drawable.bg).fit().into(imageView);
    }
}


