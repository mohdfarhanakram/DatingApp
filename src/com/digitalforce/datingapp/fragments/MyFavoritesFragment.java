/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import com.digitalforce.datingapp.adapter.MyFavoritesAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.view.ProfileActivity;
import com.farru.android.network.ServiceResponse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author FARHAN
 *
 */
public class MyFavoritesFragment extends BaseFragment{

	private View mView;
	private GridView mgridlistfAV;
	private ArrayList<UserInfo> mlistFav = new ArrayList<UserInfo>();
	private MyFavoritesAdapter mMyFavoritesAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.layout_fragment_favourite, container, false);
		mgridlistfAV = (GridView) mView.findViewById(R.id.grid_view_fav);
		
		postData(DatingUrlConstants.SHOW_MY_FAVOURITE_URL, ApiEvent.SHOW_MY_FAVOURITE, getRequestJson());

		mgridlistfAV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra(AppConstants.SHOW_PROFILE_USER_ID, mlistFav.get(position).getUserId());
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
			case ApiEvent.SHOW_MY_FAVOURITE:

				mlistFav = (ArrayList<UserInfo>)serviceResponse.getResponseObject();

				if(mlistFav.size()>0){

					mView.findViewById(R.id.grid_view_fav).setVisibility(View.VISIBLE);
					mView.findViewById(R.id.empty_view).setVisibility(View.GONE);

                    mMyFavoritesAdapter = new MyFavoritesAdapter(getActivity(), mlistFav);
					mgridlistfAV.setAdapter(mMyFavoritesAdapter);

				}else{

					mView.findViewById(R.id.grid_view_fav).setVisibility(View.GONE);
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

		//{"userid":"5"}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", getActivity()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}

}
