package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.utils.ToastCustom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends BaseActivity implements OnClickListener{

	private LinearLayout mMoreSetting, mJustAnotherSetting, mOtherSetting, mProfile, mfilters, mPremiumMembership;
	private TextView mtxtTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);

		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mMoreSetting = (LinearLayout) findViewById(R.id.layout_setting_more_something);
		mJustAnotherSetting = (LinearLayout) findViewById(R.id.layout_setting_just_another);
		mOtherSetting = (LinearLayout) findViewById(R.id.layout_setting_other);
		mProfile = (LinearLayout) findViewById(R.id.layout_setting_profile);
		mfilters = (LinearLayout) findViewById(R.id.layout_setting_filters);
		mPremiumMembership = (LinearLayout) findViewById(R.id.layout_setting_premium_membership);

		mtxtTitle.setText(getResources().getString(R.string.setting));
		mMoreSetting.setOnClickListener(this);
		mJustAnotherSetting.setOnClickListener(this);
		mOtherSetting.setOnClickListener(this);
		mProfile.setOnClickListener(this);
		mfilters.setOnClickListener(this);
		mPremiumMembership.setOnClickListener(this);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_setting_more_something:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.layout_setting_just_another:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.layout_setting_other:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.layout_setting_profile:
			//ToastCustom.underDevelopment(this);
			Intent intentProfile = new Intent(this, UpdateProfileActivity.class);
			startActivity(intentProfile);
			break;
		case R.id.layout_setting_filters:
			Intent intentFilter = new Intent(this, FilterActivity.class);
			startActivity(intentFilter);
			break;
		case R.id.layout_setting_premium_membership:
			ToastCustom.underDevelopment(this);
			break;

		default:
			break;
		}
	}

}
