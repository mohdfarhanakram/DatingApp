package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.utils.Validation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
ConnectionCallbacks, OnConnectionFailedListener{

	private EditText medtCoolName, medtPassword;
	private TextView mtxtNewUser, mtxtForgotPassword;
	private ImageView mimgFb, mimgTw, mimgGooglePlus;
	private Button mbtnLogin;
	private boolean mSignInClicked;
	private boolean mIntentInProgress;
	private ConnectionResult mConnectionResult;
	// Google client to interact with Google API
		private GoogleApiClient mGoogleApiClient;
		private static final int RC_SIGN_IN = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_login);
		medtCoolName = (EditText) findViewById(R.id.edt_login_cool_name);
		medtPassword = (EditText) findViewById(R.id.edt_login_password);
		mtxtNewUser = (TextView) findViewById(R.id.txt_login_forgot_password);
		mtxtForgotPassword = (TextView) findViewById(R.id.txt_login_new_user);
		mbtnLogin = (Button) findViewById(R.id.btn_login_lets_go);
		mimgFb = (ImageView) findViewById(R.id.img_login_facebook);
		mimgTw = (ImageView) findViewById(R.id.img_login_twitter);
		mimgGooglePlus = (ImageView) findViewById(R.id.img_login_google_plus);
		
		mtxtNewUser.setOnClickListener(this);
		mtxtForgotPassword.setOnClickListener(this);
		mbtnLogin.setOnClickListener(this);
		mimgTw.setOnClickListener(this);
		mimgFb.setOnClickListener(this);
		mimgGooglePlus.setOnClickListener(this);
		
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API, null)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_login_lets_go:
			if(checkValidation())
			{
				ToastCustom.underDevelopment(this);
			}

			break;
		case R.id.txt_login_forgot_password:
			//ToastCustom.underDevelopment(this);
			Intent intentForgot = new Intent(this, ForgotpsswordActivity.class);
			startActivity(intentForgot);
			break;
		case R.id.txt_login_new_user:
			//ToastCustom.underDevelopment(this);
			Intent intentSign = new Intent(this, SignUpActivity.class);
			startActivity(intentSign);
			break;
		case R.id.img_login_facebook:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.img_login_twitter:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.img_login_google_plus:
			//ToastCustom.underDevelopment(this);
			signInWithGplus();
			break;

		default:
			break;
		}
	}
	
	private boolean checkValidation()
	{
		boolean valid = true;
		if(!Validation.hasText(medtCoolName))
			valid = false;
		if(!Validation.hasText(medtPassword))
			valid = false;
		
		return valid;
	}
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}
	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}
	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		// Get user's information
		getProfileInformation();

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.e("GPLUSCREDENTIAL", "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);


			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}
}
