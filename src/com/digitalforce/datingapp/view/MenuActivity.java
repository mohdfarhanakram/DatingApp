package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MenuActivity extends Activity implements OnClickListener{

	private ImageView mimgProfile, mimgMembers, mimgChats, mimgMatch, mimgPictures, mimgExpand,
	mimgFavourites, mimgSetting, mimgNewBuzz, mimgLogout;
	private RelativeLayout mlayoutContainer;

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
		mimgExpand = (ImageView) findViewById(R.id.img_menu_expand);
		

		mimgProfile.setOnClickListener(this);
		mimgMembers.setOnClickListener(this);
		mimgChats.setOnClickListener(this);
		mimgMatch.setOnClickListener(this);
		mimgPictures.setOnClickListener(this);
		mimgFavourites.setOnClickListener(this);
		mimgSetting.setOnClickListener(this);
		mimgNewBuzz.setOnClickListener(this);
		mimgLogout.setOnClickListener(this);
		mimgExpand.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_menu_profile:

			break;
		case R.id.img_menu_members:

			break;
		case R.id.img_menu_chats:

			break;
		case R.id.img_menu_match:

			break;
		case R.id.img_menu_pictures:

			break;
		case R.id.img_menu_favourites:

			break;
		case R.id.img_menu_settings:

			break;
		case R.id.img_menu_new_buzz:

			break;
		case R.id.img_menu_logout:

			break;
		case R.id.img_menu_expand:
				finish();
			break;

		default:
			break;
		}

	}
}
