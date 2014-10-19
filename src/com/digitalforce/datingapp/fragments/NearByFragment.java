package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.NearByAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.NearBy;
import com.digitalforce.datingapp.view.ProfileActivity;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.view.BaseActivity;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class NearByFragment extends BaseFragment{

	private View mView;
	private GridView mgridlistNearBy;
	private ArrayList<UserInfo> mlistNearBy = new ArrayList<UserInfo>();
	private NearByAdapter mnearByAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.layout_fragment_nearby, container, false);
		mgridlistNearBy = (GridView) mView.findViewById(R.id.grid_view_nearby);
		postData(DatingUrlConstants.USER_NEAR_BY_URL, ApiEvent.USER_NEAR_BY_EVENT, getRequestJson());

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
	public void updateUi(ServiceResponse serviceResponse) {
		mView.findViewById(R.id.loading_view).setVisibility(View.GONE);
		if(serviceResponse.getErrorCode()==ServiceResponse.SUCCESS){

			switch (serviceResponse.getEventType()) {
			case ApiEvent.USER_NEAR_BY_EVENT:

				mlistNearBy = (ArrayList<UserInfo>)serviceResponse.getResponseObject();

				if(mlistNearBy.size()>0){

					mView.findViewById(R.id.grid_view_nearby).setVisibility(View.VISIBLE);
					mView.findViewById(R.id.empty_view).setVisibility(View.GONE);

					mnearByAdapter = new NearByAdapter(getActivity(), mlistNearBy);
					mgridlistNearBy.setAdapter(mnearByAdapter);

				}else{

					mView.findViewById(R.id.grid_view_nearby).setVisibility(View.GONE);
					mView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
				}

				break;

			default:
				break;
			}


		}else{

		}

	}

	private String getRequestJson(){

		//{"userid":"5", "lat":"28.5470898", "long":"77.3051591", "distance":"10"}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", getActivity()));
			jsonObject.putOpt("lat", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", getActivity()));
			jsonObject.putOpt("long", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", getActivity()));
			jsonObject.putOpt("distance", AppConstants.NEAR_BY_DISTANCE+"");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}

}
