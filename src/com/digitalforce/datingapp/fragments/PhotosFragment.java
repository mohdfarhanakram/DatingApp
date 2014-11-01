package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.MyPictureAdapter;
import com.digitalforce.datingapp.adapter.ProfilePhotoAdapter;
import com.farru.android.utill.Utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.GridView;

public class PhotosFragment extends BaseFragment{
	
	private ArrayList<String> mPhotoList;
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.layout_fragment_photos, container, false);
		return mView;
	}
	
	public void setPhoto(ArrayList<String> photoList){
		mPhotoList = photoList;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(mPhotoList.size() > 0){
			mView.findViewById(R.id.no_photo).setVisibility(View.GONE);
			mView.findViewById(R.id.grid_view_picture).setVisibility(View.VISIBLE);
			
			GridView gridView = (GridView)mView.findViewById(R.id.grid_view_picture);
			gridView.setAdapter(new ProfilePhotoAdapter(getActivity(), mPhotoList));
			
			Utils.setGridViewHeightBasedOnChildren(gridView, 4, Utils.dpToPx(2));
		}else{
			mView.findViewById(R.id.no_photo).setVisibility(View.VISIBLE);
			mView.findViewById(R.id.grid_view_picture).setVisibility(View.GONE);
		}
		

	}
}
