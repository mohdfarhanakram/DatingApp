package com.digitalforce.datingapp.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.Validation;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.Utils;

import java.util.ArrayList;

public class SignUpActivity extends BaseActivity implements OnClickListener{

	private EditText medtPassword, medtConfirmPassword, medtCoolName;
	private Button mbtnSignup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_signup);
		
		mbtnSignup = (Button) findViewById(R.id.btn_signup);
		medtConfirmPassword = (EditText) findViewById(R.id.edt_signup_confirm_password);
		medtCoolName = (EditText) findViewById(R.id.edt_signup_cool_name);
		medtPassword = (EditText) findViewById(R.id.edt_signup_password);
		
		mbtnSignup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_signup:
			if(checkvalidation())
			{
				String postData = getRequestJson();
				Log.e("Post Data", postData);
				postData(DatingUrlConstants.SIGN_UP_URL, ApiEvent.SIGN_UP_EVENT, postData);
			}
			break;

		default:
			break;
		}
	}
	
	private boolean checkvalidation()
	{
		boolean valid = true;
		
		if(!Validation.isPasswordMatch(medtPassword, medtConfirmPassword))
			valid=false;
		
		return valid;
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUi(ServiceResponse serviceResponse) {
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
		case ApiEvent.SIGN_UP_EVENT:
			UserInfo userInfo = (UserInfo)serviceResponse.getResponseObject();
			if(userInfo!=null){
				showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
                navigateToHomeScreen(userInfo);
			}
			break;

		default:
			break;
		}
	}


	private String getRequestJson(){

		//{"email":"shaan@gmail.com", "password":"12345", "confirmpass":"12345", "lat":"743872432", "long":"3749382", "device":"89748937432784937498hjjk38343"}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("email", medtCoolName.getText().toString());
			jsonObject.putOpt("password", medtPassword.getText().toString());
			jsonObject.putOpt("confirmpass", medtConfirmPassword.getText().toString());
			jsonObject.putOpt("latitude", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", this));
			jsonObject.putOpt("longitude", DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", this));
			jsonObject.putOpt("device", Utils.getDeviceEMI(this));
			jsonObject.putOpt("device_type", "android");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.e("Sign Up Request", jsonObject.toString());
		return jsonObject.toString();
	}


    private void navigateToHomeScreen(UserInfo userInfo){

        DatingAppPreference.putString(DatingAppPreference.USER_ID, userInfo.getUserId(), this);
        DatingAppPreference.putString(DatingAppPreference.USER_NAME, (userInfo.getFirstName()+" "+userInfo.getLastName()).trim(), this);
        DatingAppPreference.putString(DatingAppPreference.USER_PROFILE_URL, userInfo.getImage(), this);

        Intent intent = new Intent(this, MembersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        //setResult(RESULT_OK);
        finish();

    }
}
