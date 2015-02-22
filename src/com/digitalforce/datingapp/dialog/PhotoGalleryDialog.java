package com.digitalforce.datingapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.GalleryAdapter;
import com.digitalforce.datingapp.listener.BlockReportListener;
import com.digitalforce.datingapp.view.BaseActivity;

import java.util.ArrayList;

/**
 * Created by FARHAN on 2/17/2015.
 */
public class PhotoGalleryDialog extends Dialog implements android.view.View.OnClickListener{

    private ArrayList<String> photoUrls;
    private int index;
    private Context mContext;

    private int previous;
    private int next;

    public PhotoGalleryDialog(Context context,ArrayList<String> photoUrls,int index) {
        super(context);
        this.photoUrls = photoUrls;
        this.index = index;
        mContext = context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_gallery_layout);

        ((ViewPager)findViewById(R.id.gallery_view_pager)).setAdapter(new GalleryAdapter(mContext, photoUrls));

        ((ViewPager)findViewById(R.id.gallery_view_pager)).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                index = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        setGalleyItem();
        findViewById(R.id.next_btn).setOnClickListener(this);
        findViewById(R.id.prev_btn).setOnClickListener(this);

        if(photoUrls.size()==1){
            findViewById(R.id.next_btn).setVisibility(View.GONE);
            findViewById(R.id.prev_btn).setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.next_btn:
               if(index==photoUrls.size()-1){
                   return;
               }

               index = index+1;
               setGalleyItem();

               break;
           case R.id.prev_btn:

               if(index == 0)
                   return;
               index = index - 1;
               setGalleyItem();
               break;
       }

    }

    private void setGalleyItem(){
        ((ViewPager)findViewById(R.id.gallery_view_pager)).setCurrentItem(index);
    }

}