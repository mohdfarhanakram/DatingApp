package com.digitalforce.datingapp.view;

import java.util.ArrayList;

import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.fragments.AboutFragment;
import com.digitalforce.datingapp.fragments.AudioFragment;
import com.digitalforce.datingapp.fragments.PhotosFragment;
import com.digitalforce.datingapp.fragments.VideoFragment;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.StringUtils;

public class ProfileActivity extends BaseActivity implements OnClickListener{

	private TextView mtxtAbout, mtxtPhotos, mtxtInsight, mtxtage, mtxtWeight, mtxtheight, mtxtName, mtxtlocation,
	mtxtSexRole, mtxtHivStatus, mtxtProfileTitle, mtxtVedio, mtxtAudio,mDistance;
	private ImageView mimgBack, mimgMenu, mimgPrevious, mimgNext, mimgChat, mimgProfile, mimgOnlineStatus;
	private ImageView mimgFavourite;
	
	private String calledUserProfileId;
	
	private UserInfo mUserInfo;
	private AboutFragment aboutFragment;

    private boolean isUserOnline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile);

		mtxtProfileTitle = (TextView) findViewById(R.id.txt_screen_title);
		mtxtAbout = (TextView) findViewById(R.id.txt_profile_about);
		mtxtPhotos = (TextView) findViewById(R.id.txt_profile_photos);
		mtxtInsight = (TextView) findViewById(R.id.txt_profile_insight);
		mtxtVedio = (TextView) findViewById(R.id.txt_profile_vedio);
		mtxtAudio = (TextView) findViewById(R.id.txt_profile_audio);
		mtxtage = (TextView) findViewById(R.id.txt_profile_age);
		mtxtWeight = (TextView) findViewById(R.id.txt_profile_weight);
		mtxtheight = (TextView) findViewById(R.id.txt_profile_height);
		mtxtSexRole = (TextView) findViewById(R.id.txt_profile_sex_role);
		mtxtHivStatus = (TextView) findViewById(R.id.txt_profile_hiv_status);
		mtxtName = (TextView) findViewById(R.id.txt_profile_name);
		mtxtlocation = (TextView) findViewById(R.id.txt_profile_location);
        mDistance = (TextView)findViewById(R.id.txt_profile_distance);

		mimgBack = (ImageView) findViewById(R.id.img_action_back);
		mimgMenu = (ImageView) findViewById(R.id.img_action_menu);
		mimgProfile = (ImageView) findViewById(R.id.img_profile);
		mimgChat = (ImageView) findViewById(R.id.img_profile_chat);
		mimgFavourite = (ImageView) findViewById(R.id.img_profile_favorite);
		mimgPrevious = (ImageView) findViewById(R.id.img_profile_pre);
		mimgNext = (ImageView) findViewById(R.id.img_profile_next);
		mimgOnlineStatus = (ImageView) findViewById(R.id.img_profile_online_status);

		mtxtProfileTitle.setText(getResources().getString(R.string.profile));
		aboutFragment = new AboutFragment();
		selectFragment(aboutFragment);
	

		mtxtAbout.setOnClickListener(this);
		mtxtPhotos.setOnClickListener(this);
		mtxtVedio.setOnClickListener(this);
		mtxtAudio.setOnClickListener(this);
		mtxtInsight.setOnClickListener(this);
		mimgFavourite.setOnClickListener(this);
		mimgMenu.setOnClickListener(this);
		mimgChat.setOnClickListener(this);
		mimgFavourite.setOnClickListener(this);
		//to request for get profile data
		
		calledUserProfileId = getIntent().getStringExtra(AppConstants.SHOW_PROFILE_USER_ID);
		
		if(calledUserProfileId.equals(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this))){
			mimgFavourite.setEnabled(false);
			mimgFavourite.setClickable(false);
            findViewById(R.id.heart_button).setEnabled(false);

		}else{
			mimgFavourite.setEnabled(true);
			mimgFavourite.setClickable(true);
            findViewById(R.id.heart_button).setEnabled(true);
		}


        if(calledUserProfileId.equals(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this))){
            findViewById(R.id.intrest_layout).setVisibility(View.GONE);
        }else{
            findViewById(R.id.intrest_layout).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.txt_interested).setOnClickListener(this);
        findViewById(R.id.txt_not_interested).setOnClickListener(this);
		
		String postData = getShowProfileRequestJson();
		Log.e("Post Data", postData);
		postData(DatingUrlConstants.SHOW_PROFILE_URL, ApiEvent.SHOW_PROFILE_EVENT, postData);


		findViewById(R.id.heart_button).setOnClickListener(this);
        findViewById(R.id.point_pin).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_profile_vedio:
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.userInfoInstance(mUserInfo);
			selectFragment(videoFragment);
			mtxtVedio.setBackgroundResource(R.drawable.outline_profile_selected);
			mtxtPhotos.setBackgroundResource(Color.TRANSPARENT);
			mtxtAudio.setBackgroundResource(Color.TRANSPARENT);
			mtxtAbout.setBackgroundResource(Color.TRANSPARENT);
			mtxtInsight.setBackgroundResource(Color.TRANSPARENT);
			break;
		case R.id.txt_profile_audio:
            AudioFragment audioFragment = new AudioFragment();
            audioFragment.userInfoInstance(mUserInfo);
			selectFragment(audioFragment);
			mtxtAudio.setBackgroundResource(R.drawable.outline_profile_selected);
			mtxtPhotos.setBackgroundResource(Color.TRANSPARENT);
			mtxtVedio.setBackgroundResource(Color.TRANSPARENT);
			mtxtAbout.setBackgroundResource(Color.TRANSPARENT);
			mtxtInsight.setBackgroundResource(Color.TRANSPARENT);
			break;
		case R.id.txt_profile_about:
			AboutFragment aboutFragment = 	new AboutFragment();
			aboutFragment.setUserInfo(mUserInfo);
			selectFragment(aboutFragment);
			mtxtAbout.setBackgroundResource(R.drawable.outline_profile_selected);
			mtxtPhotos.setBackgroundResource(Color.TRANSPARENT);
			mtxtVedio.setBackgroundResource(Color.TRANSPARENT);
			mtxtAudio.setBackgroundResource(Color.TRANSPARENT);
			mtxtInsight.setBackgroundResource(Color.TRANSPARENT);
			
			break;
		case R.id.txt_profile_photos:
			PhotosFragment photoFragment = new PhotosFragment();
			photoFragment.setPhoto(mUserInfo.getPhotos());
			selectFragment(photoFragment);
			mtxtPhotos.setBackgroundResource(R.drawable.outline_profile_selected);
			mtxtAbout.setBackgroundResource(Color.TRANSPARENT);
			mtxtInsight.setBackgroundResource(Color.TRANSPARENT);
			mtxtVedio.setBackgroundResource(Color.TRANSPARENT);
			mtxtAudio.setBackgroundResource(Color.TRANSPARENT);
			break;
		case R.id.txt_profile_insight:
			//ToastCustom.underDevelopment(this);
            Intent i = new Intent(this,InsightActivity.class);
            i.putExtra(AppConstants.INSIGHT_USER_NAME,mUserInfo.getFirstName());
            i.putExtra(AppConstants.USER_INSIGHT_INFO,mUserInfo.getInsight());
            startActivity(i);

			/*mtxtInsight.setBackgroundResource(R.drawable.outline_profile_selected);
			mtxtAbout.setBackgroundResource(Color.TRANSPARENT);
			mtxtPhotos.setBackgroundResource(Color.TRANSPARENT);
			mtxtVedio.setBackgroundResource(Color.TRANSPARENT);
			mtxtAudio.setBackgroundResource(Color.TRANSPARENT);*/
			break;

		case R.id.img_profile_chat:
            if(calledUserProfileId.equals(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this))){
                Intent intentChat = new Intent(this, ChatActivity.class);
                intentChat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentChat);
            }else{
                Intent intentChat = new Intent(this, RudeChatActivity.class);
                intentChat.putExtra(AppConstants.CHAT_USER_ID,mUserInfo.getUserId());
                intentChat.putExtra(AppConstants.CHAT_USER_NAME,mUserInfo.getFirstName()+" "+mUserInfo.getLastName());
                intentChat.putExtra(AppConstants.IS_USER_ONLINE,isUserOnline);
				intentChat.putExtra(AppConstants.CHAT_USER_LOCATION, mUserInfo.getCountry());
				intentChat.putExtra(AppConstants.CHAT_USER_AWAY,mUserInfo.getDistance());
				intentChat.putExtra(AppConstants.CHAT_USER_IMAGE,mUserInfo.getImage());
                intentChat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentChat);
            }

			break;
		case R.id.img_profile_favorite:
			if(mUserInfo!=null && mUserInfo.isFavourite()){
				requestForRemoveToFav();
			}else if(mUserInfo!=null && !mUserInfo.isFavourite()){
				requestForAddToFav();
			}
			
			break;
            case R.id.txt_interested:
                if(!(Boolean)v.getTag()){
                    markInterested(true);
                }
                break;
            case R.id.txt_not_interested:
                if(!(Boolean)v.getTag()){
                    markInterested(false);
                }
                break;
            case R.id.heart_button:
                requestForLike();
                break;
            case R.id.point_pin:
                openUserLocation();
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
		fragmentTransaction.replace(R.id.fragment_profile, fragment);
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
					updateFavImageUi(true);
					break;
				case ApiEvent.REMOVE_TO_FAVOURITE:
					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					updateFavImageUi(false);
					break;
				case ApiEvent.SHOW_PROFILE_EVENT:
					onSuccess(serviceResponse);
					break;
                case ApiEvent.MARK_INTERESTED_EVENT:
                    showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
                    updateInterestButton(true);
                    break;
                case ApiEvent.MARK_NOT_INTERESTED_EVENT:
                    showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
                    updateInterestButton(false);
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
	
	private void requestForRemoveToFav(){
		String postData =  getFavRequestJson();
		Log.e("Post Data", postData);
		postData(DatingUrlConstants.REMOVE_TO_FAVOURITE_URL, ApiEvent.REMOVE_TO_FAVOURITE, postData);
	}


	private String getFavRequestJson(){
		//{"userid":"5", "fav_user_id":"4"}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.put("fav_user_id", calledUserProfileId);  //
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}

	private String getShowProfileRequestJson(){
        //{"userid":"12345","login_userid":"32"}
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.putOpt("userid", calledUserProfileId);
            jsonObject.putOpt("login_userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("Request", jsonObject.toString());
        return jsonObject.toString();

    }
	
	@SuppressWarnings("unchecked")
	private void onSuccess(ServiceResponse serviceResponse)
	{
		switch (serviceResponse.getEventType()) {
		
		case ApiEvent.SHOW_PROFILE_EVENT:
			ArrayList<UserInfo> userInfo;
			userInfo = (ArrayList<UserInfo>) serviceResponse.getResponseObject();
			for(int i=0; i<userInfo.size(); i++)
			{
				findViewById(R.id.main_layout).setVisibility(View.VISIBLE);
				mUserInfo = userInfo.get(i);
				mtxtName.setText(userInfo.get(i).getFirstName()+" "+userInfo.get(i).getLastName());
                if(!StringUtils.isNullOrEmpty(userInfo.get(i).getCountry()))
				    mtxtlocation.setText(userInfo.get(i).getCountry());
                mDistance.setText("Approx. "+userInfo.get(i).getDistance()+" Away");
				mtxtage.setText(userInfo.get(i).getAge());
				mtxtWeight.setText(userInfo.get(i).getWeight().contains("lbs")?userInfo.get(i).getWeight():userInfo.get(i).getWeight()+" lbs");
				mtxtheight.setText(formattedHeight(userInfo.get(i).getHeight()));
				mtxtSexRole.setText(userInfo.get(i).getSexRole());
				mtxtHivStatus.setText(userInfo.get(i).getHivStatus());
				if(userInfo.get(i).getStatus().equals("Online"))
				{
                    isUserOnline = true;
					mimgOnlineStatus.setImageResource(R.drawable.online_staus);
				}else{
                    isUserOnline = false;
					mimgOnlineStatus.setImageResource(R.drawable.offline);
				}
				
				updateFavImageUi(mUserInfo.isFavourite());

                updateInterestButton(mUserInfo.isInterestStatus());
				
				if(!StringUtils.isNullOrEmpty(userInfo.get(i).getImage()))
				    picassoLoad(userInfo.get(i).getImage(), mimgProfile);
				
				aboutFragment.updateUi(mUserInfo);

			}
			
			break;
			default:
				break;
		}
	}
	
	private void updateFavImageUi(boolean isFavAdded){
		mUserInfo.setFavourite(isFavAdded);
		if(isFavAdded){
			((ImageView) findViewById(R.id.img_profile_favorite)).setImageResource(R.drawable.favourite);
		}else{
			((ImageView) findViewById(R.id.img_profile_favorite)).setImageResource(R.drawable.unfavourite);
		}
		
	}


    private void updateInterestButton(boolean isInterested){

        if(isInterested){

            findViewById(R.id.txt_interested).setBackgroundResource(R.drawable.left_corner_round);
            findViewById(R.id.txt_not_interested).setBackgroundResource(Color.TRANSPARENT);
            ((TextView)findViewById(R.id.txt_not_interested)).setTextColor(Color.WHITE);
            ((TextView)findViewById(R.id.txt_interested)).setTextColor(Color.BLACK);

            findViewById(R.id.txt_interested).setTag(true);
            findViewById(R.id.txt_not_interested).setTag(false);

        }else{
            findViewById(R.id.txt_not_interested).setBackgroundResource(R.drawable.right_corner_round);
            findViewById(R.id.txt_interested).setBackgroundResource(Color.TRANSPARENT);
            ((TextView)findViewById(R.id.txt_not_interested)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.txt_interested)).setTextColor(Color.WHITE);

            findViewById(R.id.txt_interested).setTag(false);
            findViewById(R.id.txt_not_interested).setTag(true);
        }

    }

   private void markInterested(boolean markInterested){
       int event;
       String url;

       if(markInterested){
           event = ApiEvent.MARK_INTERESTED_EVENT;
           url = DatingUrlConstants.MARK_INTERESTED_URL;
       }else{
           event = ApiEvent.MARK_NOT_INTERESTED_EVENT;
           url = DatingUrlConstants.MARK_NOT_INTERESTED_URL;
       }

       postData(url,event,getInterestedJsonResponse());

   }


   private String getInterestedJsonResponse(){
       //{"userid":"1","interest_user_id":"7"}
       JSONObject jsonObject = new JSONObject();
       try {
           jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
           jsonObject.put("interest_user_id", calledUserProfileId);  //
       } catch (JSONException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }

       Log.e("Request", jsonObject.toString());
       return jsonObject.toString();
   }

    private void requestForLike(){
        String postData =  getLikeJsonRequest();
        Log.e("Post Data", postData);
        postData(DatingUrlConstants.LIKE_URL, ApiEvent.LIKE_EVENT, postData);
    }

  private String getLikeJsonRequest(){
      //{"userid":"47","user_id_to_like":"1"}
      JSONObject jsonObject = new JSONObject();
      try {
          jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
          jsonObject.put("user_id_to_like", calledUserProfileId);  //
      } catch (JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }

      Log.e("Request", jsonObject.toString());
      return jsonObject.toString();
  }


  private void openUserLocation(){
      if(((TextView)findViewById(R.id.txt_profile_location)).getText().toString().equalsIgnoreCase("N/A")){
          return;
      }
      Intent i =new Intent(this,UserLocationActivity.class);
      i.putExtra(AppConstants.MAP_LATITUDE,mUserInfo.getLatitude());
      i.putExtra(AppConstants.MAP_LONGITUDE,mUserInfo.getLongitude());
      i.putExtra(AppConstants.MAP_USER_NAME,mUserInfo.getFirstName());
	  i.putExtra(AppConstants.MAP_USER_ID,mUserInfo.getUserId());
      startActivity(i);
}

	private int getConvertKg(int lbs){
		return (int)(lbs / 2.20462262185);
	}

	private String formattedHeight(String cm){
		String cmm = cm;
		try{
			int inch = (int)(Float.parseFloat(cm)/2.54);
			int ft = inch/12;
			int in = inch%12;

			cmm = ((int)ft)+"'"+in+"\"";

		}catch(Exception e){
			e.printStackTrace();
		}
		return cmm;
	}

}
