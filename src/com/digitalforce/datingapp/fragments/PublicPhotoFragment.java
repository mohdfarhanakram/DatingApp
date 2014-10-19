/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalforce.datingapp.R;

/**
 * @author FARHAN
 *
 */
public class PublicPhotoFragment extends BaseFragment{
	private View mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.layout_fragment_public_photo, container, false);
		return mView;
	}
}
