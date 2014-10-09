package com.digitalforce.datingapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.utils.Validation;

public class ForgotpsswordActivity extends Activity implements OnClickListener{

	private EditText medtEmailAddress;
	private Button mbtnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forgot_password);
		
		medtEmailAddress = (EditText) findViewById(R.id.edt_forgot_email_address);
		mbtnSend = (Button) findViewById(R.id.btn_forgot_send);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_forgot_send:
			if(checkValidation())
			{
				
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
}
