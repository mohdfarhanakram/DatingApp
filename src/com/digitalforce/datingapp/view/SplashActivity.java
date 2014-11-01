package com.digitalforce.datingapp.view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingConstants;
import com.digitalforce.datingapp.location.LocationController;
import com.digitalforce.datingapp.location.LocationListener;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.LocationUtils;
import com.farru.android.utill.StringUtils;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;

public class SplashActivity extends BaseActivity implements LocationListener{

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash_screen);

		

	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		executeLocationController();
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);

	}
	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == DatingConstants.TCACTION && resultCode == DatingConstants.TCACTION)
		{
			finish();
		}
	}
	 */

	private void handleNavigation(){

		if(isTcAccept() && !isUserLogin()){
			Intent i = new Intent(this,LoginActivity.class);
			startActivity(i);
			finish();
		}else if(isTcAccept() && isUserLogin()){
			Intent intent = new Intent(this, MembersActivity.class);
			startActivity(intent);
			finish();
		}else{
			//new TermConditon(SplashActivity.this).show();

			Intent intent = new Intent(SplashActivity.this, TermConditionActivity.class);
			startActivityForResult(intent, DatingConstants.TCACTION);
			finish();
		}

	}

	private void promptGPSDisable(){
		
		String lat   = DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "", this);
		String longi = DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "", this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Location service is off!!");  // GPS not found
		
		if(!StringUtils.isNullOrEmpty(lat) && !StringUtils.isNullOrEmpty(longi)){
			builder.setMessage("Location service is off so unable to update your current location"); // Want to enable?
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int i) {
					handleNavigation();
				}
			});

		}else{
			builder.setMessage("Location service is disable. Do you want to enable your location services?"); // Want to enable?
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int i) {
					startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), AppConstants.REQUEST_CODE_FOR_LOCATION);
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int i) {
					finish();
				}
			});
		}

		builder.create().show();
		return;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppConstants.REQUEST_CODE_FOR_LOCATION) {
			executeLocationController();
		}

	}

	@Override
	public void onLocationFound(int locationCode, Location location) {
		switch (locationCode) {
		case LOCATION_FOUND:
			DatingAppPreference.putString(DatingAppPreference.USER_DEVICE_LATITUDE, location.getLatitude()+"", this);
			DatingAppPreference.putString(DatingAppPreference.USER_DEVICE_LONGITUDE, location.getLongitude()+"", this);	
			handleNavigationFromSplash();
			break;
		case LOCATION_PROVIDER_OFF:
			promptGPSDisable();
			break;
		default:
			retryToFetchLocation();
			break;
		}

	}
	
	private void handleNavigationFromSplash(){
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				handleNavigation();

			}
		}, SPLASH_TIME_OUT);
	}
	
	
	private void retryToFetchLocation(){
		
		String lat   = DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "", this);
		String longi = DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "", this);
		
		
		
		if(!StringUtils.isNullOrEmpty(lat) && StringUtils.isNullOrEmpty(longi)){
			handleNavigationFromSplash();
		}else{
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Location Failed!!");  // GPS not found
			builder.setMessage("Something went wrong to fetch your location. Please try again?"); // Want to enable?
			builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int i) {
					executeLocationController();
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int i) {
					finish();
				}
			});
			builder.create().show();
			return;
			
		}
		
	}
	
	private void executeLocationController(){
		(new LocationController(this, this)).execute();
	}



}
