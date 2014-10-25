/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.MyPictureAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.view.BaseActivity;
import com.farru.android.network.ServiceResponse;

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
public class PrivatePhotoFragment extends BaseFragment{

	private GridView mGridView;
	private View mView;

	ArrayList<String> mPictureList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.layout_fragment_private_photo, container, false);

		mGridView = (GridView) mView.findViewById(R.id.grid_view_picture);
		postData(DatingUrlConstants.SHOW_MY_PICTURE_URL, ApiEvent.SHOW_PRIVATE_PICTURE_EVENT, getRequestJson());

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
				// TODO Auto-generated method stub
				/* Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra(AppConstants.SHOW_PROFILE_USER_ID, mlistNearBy.get(position).getUserId());
                startActivity(i);*/
			}
		});

		return mView;
	}


	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		mView.findViewById(R.id.loading_view).setVisibility(View.GONE);
		if(serviceResponse.getErrorCode()==ServiceResponse.SUCCESS){

			switch (serviceResponse.getEventType()) {
			case ApiEvent.SHOW_PRIVATE_PICTURE_EVENT:

				mPictureList = (ArrayList<String>)serviceResponse.getResponseObject();

				if(mPictureList.size()>0){

					mView.findViewById(R.id.grid_view_picture).setVisibility(View.VISIBLE);
					mView.findViewById(R.id.empty_view).setVisibility(View.GONE);
					mGridView.setAdapter(new MyPictureAdapter(getActivity(), mPictureList));

				}else{

					mView.findViewById(R.id.grid_view_picture).setVisibility(View.GONE);
					mView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
				}

				break;

			case ApiEvent.UPLOAD_PRIVATE_PICTURE_EVENT:

				((BaseActivity)getActivity()).showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
				break;

			default:
				break;
			}


		}else{

		}

	}


	private String getRequestJson(){

		//{"userid" : "2","privacy":"private"}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", getActivity()));
			jsonObject.putOpt("privacy", "private");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}

}
