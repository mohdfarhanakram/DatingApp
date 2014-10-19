/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import com.digitalforce.datingapp.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author FARHAN
 *
 */
public class PrivatePhotoFragment extends BaseFragment{
	
	private View mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.layout_fragment_private_photo, container, false);
		return mView;
	}

}
