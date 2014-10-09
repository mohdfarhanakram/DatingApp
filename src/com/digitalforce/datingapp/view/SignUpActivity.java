package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.utils.Validation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity implements OnClickListener{

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
}
