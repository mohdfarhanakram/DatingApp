package com.digitalforce.datingapp.view;

import org.json.JSONException;
import org.json.JSONObject;


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

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.utils.Validation;
import com.farru.android.network.ServiceResponse;
import com.farru.android.ui.activity.BaseActivity;
import com.farru.android.utill.Utils;


public class LoginActivity extends BaseActivity implements OnClickListener{

	private EditText medtCoolName, medtPassword;
	private TextView mtxtNewUser, mtxtForgotPassword;
	private ImageView mimgFb, mimgTw;
	private Button mbtnLogin;
	private boolean mSignInClicked;
	private boolean mIntentInProgress;


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

		
		mtxtNewUser.setOnClickListener(this);
		mtxtForgotPassword.setOnClickListener(this);
		mbtnLogin.setOnClickListener(this);
		mimgTw.setOnClickListener(this);
		mimgFb.setOnClickListener(this);

		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_login_lets_go:
			if(checkValidation())
			{
				String postData = getRequestJson();
				Log.e("Post Data", postData);
				postData(DatingUrlConstants.LOGIN_URL, ApiEvent.LOGIN_EVENT, postData);
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
		//mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		/*if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}*/
	}
	

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	protected void updateUi(ServiceResponse serviceResponse) {
		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				onSuccess(serviceResponse);
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
	
	private void onSuccess(ServiceResponse serviceResponse){
		switch (serviceResponse.getEventType()) {
		case ApiEvent.LOGIN_EVENT:
			UserInfo userInfo = (UserInfo)serviceResponse.getResponseObject();
			if(userInfo!=null){
				showCommonError(userInfo.getUserId()); // its only for testing
			}
			break;

		default:
			break;
		}
	}
	
	
	private String getRequestJson(){

		//{"email":"shaan@gmail.com", "password":"12345", "lat":"743872432", "long":"3749382", "device":"89748937432784937498hjjk38343"}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("email", medtCoolName.getText().toString());
			jsonObject.putOpt("password", medtPassword.getText().toString());
			jsonObject.putOpt("lat", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", this));
			jsonObject.putOpt("long", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", this));
			jsonObject.putOpt("device", Utils.getDeviceEMI(this));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.e("Login Request", jsonObject.toString());
		return jsonObject.toString();
	}
	
}
