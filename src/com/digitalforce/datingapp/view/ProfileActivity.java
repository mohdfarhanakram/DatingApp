package com.digitalforce.datingapp.view;

import org.json.JSONException;
import org.json.JSONObject;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.fragments.AboutFragment;
import com.digitalforce.datingapp.fragments.PhotosFragment;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.network.ServiceResponse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends BaseActivity implements OnClickListener{

	private TextView mtxtAbout, mtxtPhotos, mtxtInsight, mtxtage, mtxtWeight, mtxtheight, mtxtName, mtxtlocation,
	mtxtSexRole, mtxtHivStatus, mtxtProfileTitle;
	private ImageView mimgBack, mimgMenu, mimgPrevious, mimgNext, mimgChat, mimgFavourite, mimgProfile, mimgOnlineStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile);

		mtxtProfileTitle = (TextView) findViewById(R.id.txt_screen_title);
		mtxtAbout = (TextView) findViewById(R.id.txt_profile_about);
		mtxtPhotos = (TextView) findViewById(R.id.txt_profile_photos);
		mtxtInsight = (TextView) findViewById(R.id.txt_profile_insight);
		mtxtage = (TextView) findViewById(R.id.txt_profile_age);
		mtxtWeight = (TextView) findViewById(R.id.txt_profile_weight);
		mtxtheight = (TextView) findViewById(R.id.txt_profile_height);
		mtxtSexRole = (TextView) findViewById(R.id.txt_profile_sex_role);
		mtxtHivStatus = (TextView) findViewById(R.id.txt_profile_hiv_status);
		mtxtName = (TextView) findViewById(R.id.txt_profile_name);
		mtxtlocation = (TextView) findViewById(R.id.txt_profile_location);

		mimgBack = (ImageView) findViewById(R.id.img_action_back);
		mimgMenu = (ImageView) findViewById(R.id.img_action_menu);
		mimgProfile = (ImageView) findViewById(R.id.img_profile);
		mimgChat = (ImageView) findViewById(R.id.img_profile_chat);
		mimgFavourite = (ImageView) findViewById(R.id.img_profile_favorite);
		mimgPrevious = (ImageView) findViewById(R.id.img_profile_pre);
		mimgNext = (ImageView) findViewById(R.id.img_profile_next);
		mimgOnlineStatus = (ImageView) findViewById(R.id.img_profile_online_status);

		mtxtProfileTitle.setText(getResources().getString(R.string.profile));
		selectFragment(new AboutFragment());

		mtxtAbout.setOnClickListener(this);
		mtxtPhotos.setOnClickListener(this);
		mtxtInsight.setOnClickListener(this);
		mimgMenu.setOnClickListener(this);
		mimgChat.setOnClickListener(this);
		mimgFavourite.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_profile_about:
			selectFragment(new AboutFragment());
			mtxtAbout.setBackgroundResource(R.color.about_bg_color);
			mtxtPhotos.setBackgroundResource(Color.TRANSPARENT);
			break;
		case R.id.txt_profile_photos:
			selectFragment(new PhotosFragment());
			mtxtPhotos.setBackgroundResource(R.color.about_bg_color);
			mtxtAbout.setBackgroundResource(Color.TRANSPARENT);
			break;
		case R.id.txt_profile_insight:
			ToastCustom.underDevelopment(this);
			break;

		case R.id.img_profile_chat:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.img_profile_favorite:
			requestForAddToFav();
			break;
		default:
			break;
		}

	}
	/**
	 * call fragment
	 * @param fragment
	 */
	private void selectFragment(Fragment fragment)
	{
		FragmentManager fragmentmaneger = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentmaneger.beginTransaction();
		fragmentTransaction.add(R.id.fragment_profile, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);
		
		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				switch (serviceResponse.getEventType()) {
				case ApiEvent.ADD_TO_FAVOURITE:
					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					break;

				default:
					break;
				}
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


	private void requestForAddToFav(){
		String postData =  getFavRequestJson();
		Log.e("Post Data", postData);
		postData(DatingUrlConstants.ADD_TO_FAVOURITE_URL, ApiEvent.ADD_TO_FAVOURITE, postData);

	}


	private String getFavRequestJson(){
		//{"userid":"5", "fav_user_id":"4"}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.put("fav_user_id", 5);  //
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}
}
