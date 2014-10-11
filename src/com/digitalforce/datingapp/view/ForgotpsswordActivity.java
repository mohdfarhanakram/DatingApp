package com.digitalforce.datingapp.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.utils.Validation;
import com.farru.android.network.ServiceResponse;

import com.farru.android.utill.StringUtils;

public class ForgotpsswordActivity extends BaseActivity implements OnClickListener{

	private EditText medtEmailAddress;
	private Button mbtnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forgot_password);
		
		medtEmailAddress = (EditText) findViewById(R.id.edt_forgot_email_address);
		mbtnSend = (Button) findViewById(R.id.btn_forgot_send);
		
		mbtnSend.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_forgot_send:
			if(checkValidation())
			{
				String postData = getRequestJson();
				Log.e("Post Data", postData);
				postData(DatingUrlConstants.FORGOT_PASS_URL, ApiEvent.FORGOT_PASSWORD_EVENT, postData);
			}
			
			break;

		default:
			break;
		}
	}

	private boolean checkValidation()
	{
		boolean valid = true;
		
		if(!Validation.isEmailAddress(medtEmailAddress, true))
			valid = false;
		return valid;
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
		case ApiEvent.FORGOT_PASSWORD_EVENT:
			String msg = (String)serviceResponse.getResponseObject();
			if(!StringUtils.isNullOrEmpty(msg)){
				showCommonError(msg); 
			}
			break;

		default:
			break;
		}
	}
	
	
	private String getRequestJson(){

		//{"email" : "shahidansari.bit@gmail.com"}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("email", medtEmailAddress.getText().toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}
	
}
