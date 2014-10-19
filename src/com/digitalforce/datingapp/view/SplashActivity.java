package com.digitalforce.datingapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.DatingConstants;
import com.digitalforce.datingapp.dialog.TermConditon;
import com.farru.android.network.ServiceResponse;

public class SplashActivity extends BaseActivity{

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash_screen);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				
				//new TermConditon(SplashActivity.this).show();
				
				// close this activity
				//finish();
				 handleNavigation();
				
			}
		}, SPLASH_TIME_OUT);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateUi(ServiceResponse serviceResponse) {
		// TODO Auto-generated method stub
		
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
}
