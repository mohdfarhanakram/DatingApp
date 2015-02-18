package com.digitalforce.datingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.utils.PicassoEx;

/**
 * Created by FARHAN on 2/18/2015.
 */

public class ImageFragment extends Fragment {

    private View mView;
    private String url;
    private ImageView mMediaImageView;

    public ImageFragment(){

    }

    public void setImageUrl(String url){
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_media,container,false);
        mMediaImageView = (ImageView)mView.findViewById(R.id.media_img_view);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        picassoLoad(url, mMediaImageView);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void picassoLoad(String url, ImageView imageView) {
        PicassoEx.getPicasso(getActivity()).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
    }

}

