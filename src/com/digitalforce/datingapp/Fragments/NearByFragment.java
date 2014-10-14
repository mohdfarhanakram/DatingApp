package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.NearByAdapter;
import com.digitalforce.datingapp.model.NearBy;
import com.digitalforce.datingapp.view.ProfileActivity;
import com.farru.android.network.ServiceResponse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class NearByFragment extends BaseFragment{

	private View mView;
	private GridView mgridlistNearBy;
	private ArrayList<NearBy> mlistNearBy;
	private NearByAdapter mnearByAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.layout_fragment_nearby, container, false);
		
		mgridlistNearBy = (GridView) mView.findViewById(R.id.grid_view_nearby);
		
		mlistNearBy = new ArrayList<NearBy>();
		
		for(int i = 0; i<15; i++)
		{
			NearBy nearBy = new NearBy();
			nearBy.setFirstName("Farhan");
			nearBy.setUserId("1");
			nearBy.setDistance("Lucknow");
			mlistNearBy.add(nearBy);
		}
		mnearByAdapter = new NearByAdapter(getActivity(), mlistNearBy);
		mgridlistNearBy.setAdapter(mnearByAdapter);
		
		mgridlistNearBy.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
			}
        });
		
		return mView;
	}

	@Override
	protected void updateUi(ServiceResponse serviceResponse) {
		// TODO Auto-generated method stub
		
	}
}
