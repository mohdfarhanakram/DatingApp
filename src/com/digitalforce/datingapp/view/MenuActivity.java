package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.utils.ToastCustom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MenuActivity extends Activity implements OnClickListener{

	private ImageView mimgProfile, mimgMembers, mimgChats, mimgMatch, mimgPictures, mimgExpand,
	mimgFavourites, mimgSetting, mimgNewBuzz, mimgLogout;
	private RelativeLayout mlayoutContainer;

	private View mainContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu);

		mlayoutContainer = (RelativeLayout) findViewById(R.id.container);
		mimgProfile = (ImageView) findViewById(R.id.img_menu_profile);
		mimgMembers = (ImageView) findViewById(R.id.img_menu_members);
		mimgChats = (ImageView) findViewById(R.id.img_menu_chats);
		mimgMatch = (ImageView) findViewById(R.id.img_menu_match);
		mimgPictures = (ImageView) findViewById(R.id.img_menu_pictures);
		mimgFavourites = (ImageView) findViewById(R.id.img_menu_favourites);
		mimgSetting = (ImageView) findViewById(R.id.img_menu_settings);
		mimgNewBuzz = (ImageView) findViewById(R.id.img_menu_new_buzz);
		mimgLogout = (ImageView) findViewById(R.id.img_menu_logout);
		mainContainer =  findViewById(R.id.container);


		mimgProfile.setOnClickListener(this);
		mimgMembers.setOnClickListener(this);
		mimgChats.setOnClickListener(this);
		mimgMatch.setOnClickListener(this);
		mimgPictures.setOnClickListener(this);
		mimgFavourites.setOnClickListener(this);
		mimgSetting.setOnClickListener(this);
		mimgNewBuzz.setOnClickListener(this);
		mimgLogout.setOnClickListener(this);
		mainContainer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_menu_profile:
			Intent intentProfile = new Intent(this, UpdateProfileActivity.class);
			intentProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentProfile);
			finish();
			break;
		case R.id.img_menu_members:
			//ToastCustom.underDevelopment(this);
			Intent intentMembers = new Intent(this, MembersActivity.class);
			intentMembers.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentMembers);
			finish();
			break;
		case R.id.img_menu_chats:
			//ToastCustom.underDevelopment(this);
			Intent intentChat = new Intent(this, ChatActivity.class);
			intentChat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentChat);
			finish();
			break;
		case R.id.img_menu_match:
			//ToastCustom.underDevelopment(this);
			Intent intentMatch = new Intent(this, MatchActivity.class);
			intentMatch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentMatch);
			finish();

			break;
		case R.id.img_menu_pictures:
			Intent intentMyPicture = new Intent(this, MyPictureActivity.class);
			intentMyPicture.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentMyPicture);
			finish();
			break;
		case R.id.img_menu_favourites:
			//ToastCustom.underDevelopment(this);
			Intent intentFovirite = new Intent(this, FavouriteActivity.class);
			intentFovirite.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentFovirite);
			finish();
			break;
		case R.id.img_menu_settings:
			//ToastCustom.underDevelopment(this);
			Intent intentSetting = new Intent(this, BuzzActivity.class);
			intentSetting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentSetting);
			finish();
			break;
		case R.id.img_menu_new_buzz:
			ToastCustom.underDevelopment(this);
			Intent intentBuzz = new Intent(this, BuzzActivity.class);
			intentBuzz.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentBuzz);
			finish();
			break;
		case R.id.img_menu_logout:
			setResult(AppConstants.RESULT_CODE_FOR_LOGOUT);
			finish();
			break;
		case R.id.container:
			finish();
			break;
		default:
			break;
		}

	}
}
