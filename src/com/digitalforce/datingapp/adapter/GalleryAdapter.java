package com.digitalforce.datingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
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

public class GalleryAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private ArrayList<String> urls;


    public GalleryAdapter(FragmentManager fm,Context context, ArrayList<String> urls) {
        super(fm);
        this.context = context;
        this.urls = urls;
    }



    @Override
    public Fragment getItem(int i) {

        String url = urls.get(i);
        ImageFragment fragment = new ImageFragment();
        fragment.setImageUrl(url);
        return fragment;

    }

    @Override
    public int getCount() {
        return urls==null?0:urls.size();
    }
}


