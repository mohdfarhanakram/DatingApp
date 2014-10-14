/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import com.farru.android.network.ServiceResponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author FARHAN
 *
 */
public abstract class BaseFragment extends Fragment{
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	protected abstract void updateUi(ServiceResponse serviceResponse);
}
