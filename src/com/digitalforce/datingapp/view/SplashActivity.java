package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.view.dialog.TermConditon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{

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
				
				new TermConditon(SplashActivity.this).show();
				// close this activity
				//finish();
			}
		}, SPLASH_TIME_OUT);
	}
}
