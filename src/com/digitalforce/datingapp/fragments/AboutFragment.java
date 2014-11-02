package com.digitalforce.datingapp.fragments;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.model.UserInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 
 * @author KMD
 *
 */

public class AboutFragment  extends Fragment{
	
	private View mView;
	private TextView mLookingFor, mAboutMe, mInterest;
	
	private UserInfo mUserInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.layout_fragment_about, container, false);
		
		mAboutMe = (TextView) mView.findViewById(R.id.txt_fragment_about_about_me);
		mLookingFor = (TextView) mView.findViewById(R.id.txt_fragment_about_looking_for);
		mInterest = (TextView) mView.findViewById(R.id.txt_fragment_about_interest);
		
		return mView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		updateUi(mUserInfo);
	}
	
	public void setUserInfo(UserInfo userInfo)
	{
		
		mUserInfo = userInfo;
	}
	
	
	public void updateUi(UserInfo userInfo){
		
		mUserInfo = userInfo;
		
		if(mUserInfo==null)
			return;
		mLookingFor.setText(mUserInfo.getLookingFor());
		mAboutMe.setText(mUserInfo.getAboutMe());
		mInterest.setText(mUserInfo.getInterest());
	}

}
