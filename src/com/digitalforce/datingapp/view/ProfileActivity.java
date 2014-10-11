package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.Fragments.AboutFragment;
import com.digitalforce.datingapp.Fragments.PhotosFragment;
import com.digitalforce.datingapp.utils.ToastCustom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity implements OnClickListener{

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
		case R.id.img_action_menu:
			//ToastCustom.underDevelopment(this);
			Intent intentMenu = new Intent(this, MenuActivity.class);
			startActivity(intentMenu);
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
}
