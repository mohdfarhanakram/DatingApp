package com.digitalforce.datingapp.view;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.MatchAdapter;
import com.digitalforce.datingapp.adapter.NearByAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.NearBy;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.network.ServiceResponse;

public class MatchActivity extends BaseActivity{

	private TextView mtxtTitle;
	private GridView mgridVeiw;
	private MatchAdapter adapter;
	private ArrayList<UserInfo> mUserInfos = new ArrayList<UserInfo>();
	
	private GridView mGridView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_match);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mtxtTitle.setText(getResources().getString(R.string.match));
		
		mgridVeiw = (GridView) findViewById(R.id.grid_view_match);
		
		mgridVeiw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
                Intent i = new Intent(MatchActivity.this, ProfileActivity.class);
                i.putExtra(AppConstants.SHOW_PROFILE_USER_ID,mUserInfos.get(position).getUserId());
                startActivity(i);
			}
        });
		
		postData(DatingUrlConstants.MATCH_FINDER_URL, ApiEvent.MATCH_FINDER_EVENT, getRequestJson());
		
	}
	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		findViewById(R.id.loading_view).setVisibility(View.GONE);
		
		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				onSuccess(serviceResponse);
				break;
			case ServiceResponse.MESSAGE_ERROR:
				showCommonError(serviceResponse.getErrorMessages());
				break;
			default:
				showCommonError(null);
				break;
			}
		}else{
			showCommonError(null);
		}
		
	}
	
	private String getRequestJson(){

		//{"userId" : "1","min_age":"20","max_age":"60","sex_role":"top","latitude":"28","longitude":"77"}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userId", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.putOpt("latitude", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", this));
			jsonObject.putOpt("longitude", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", this));
			jsonObject.putOpt("min_age", DatingAppPreference.getString(DatingAppPreference.MIN_AGE, "20", this));
			jsonObject.putOpt("max_age", DatingAppPreference.getString(DatingAppPreference.MAX_AGE, "60", this));
			jsonObject.putOpt("sex_role", DatingAppPreference.getString(DatingAppPreference.MAX_AGE, "top", this));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}
	
	private void onSuccess(ServiceResponse serviceResponse){
		
		switch (serviceResponse.getEventType()) {
		case ApiEvent.MATCH_FINDER_EVENT:

			mUserInfos = (ArrayList<UserInfo>)serviceResponse.getResponseObject();

			if(mUserInfos.size()>0){

				findViewById(R.id.grid_view_match).setVisibility(View.VISIBLE);
				findViewById(R.id.empty_view).setVisibility(View.GONE);

				mgridVeiw.setAdapter(new NearByAdapter(this, mUserInfos));

			}else{

				findViewById(R.id.grid_view_match).setVisibility(View.GONE);
				findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
			}

			break;

		default:
			break;
		}
		
	}

}
