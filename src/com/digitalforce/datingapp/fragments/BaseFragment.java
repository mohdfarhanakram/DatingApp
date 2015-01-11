/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.view.BaseActivity;
import com.farru.android.network.ServiceResponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * @author FARHAN
 *
 */
public abstract class BaseFragment extends Fragment {
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void postData(String url, int eventType, String postData) {
		((BaseActivity)getActivity()).postData(url, eventType, postData,false);

	}

	public void postData(String url, int eventType, String postData,Object requestObj) {
		((BaseActivity)getActivity()).postData(url, eventType, postData,true,requestObj);

	}
	
	
	public void updateUi(ServiceResponse serviceResponse){
		
	}

	public void removePhoto(boolean isRemove, boolean isPublic){

	}
}
