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

import android.widget.EditText;
import android.widget.Toast;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingConstants;
import com.digitalforce.datingapp.location.GPSTracker;
import com.digitalforce.datingapp.location.LocationController;
import com.digitalforce.datingapp.location.LocationListener;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.LocationUtils;
import com.farru.android.utill.StringUtils;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;

public class SplashActivity extends BaseActivity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

    private GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash_screen);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);
	}


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



	private void handleNavigationFromSplash(){
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				handleNavigation();

			}
		}, SPLASH_TIME_OUT);
	}
	
	

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    private void getLocation(){
        GPSTracker gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            DatingAppPreference.putString(DatingAppPreference.USER_DEVICE_LATITUDE, latitude+"", this);
            DatingAppPreference.putString(DatingAppPreference.USER_DEVICE_LONGITUDE, longitude+"", this);
            handleNavigationFromSplash();

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
}
