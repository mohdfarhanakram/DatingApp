package com.digitalforce.datingapp.view;

import org.json.JSONException;
import org.json.JSONObject;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.R.string;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.utils.Validation;
import com.farru.android.network.ServiceResponse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateProfileActivity extends BaseActivity implements OnClickListener{

	private ImageView mimgback, mimgMenu;
	private TextView mtxtTitle, mtxtTap;
	private EditText medtFname,medtLname,medtDob,medtGender,medtCountry,medtMobile,
						medtAboutMe, medtAge,  medtHeight, medtWeight, medtLokingFor,
						medtHivStatus,medtInterest,medtSexRole; 
	private Button mbtnUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_update_profile);
		
		mimgback = (ImageView) findViewById(R.id.img_action_back);
		mimgMenu = (ImageView) findViewById(R.id.img_action_menu);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		medtFname = (EditText) findViewById(R.id.edt_update_profile_fname);
		medtLname = (EditText) findViewById(R.id.edt_update_profile_lname);
		medtDob = (EditText) findViewById(R.id.edt_update_profile_dob);
		medtGender = (EditText) findViewById(R.id.edt_update_profile_gender);
		medtCountry = (EditText) findViewById(R.id.edt_update_profile_country);
		medtMobile = (EditText) findViewById(R.id.edt_update_profile_mobile);
		medtAboutMe = (EditText) findViewById(R.id.edt_update_profile_about_me);
		medtAge = (EditText) findViewById(R.id.edt_update_profile_age);
		medtHeight = (EditText) findViewById(R.id.edt_update_profile_height);
		medtWeight = (EditText) findViewById(R.id.edt_update_profile_weight);
		medtLokingFor = (EditText) findViewById(R.id.edt_update_profile_loking_for);
		medtHivStatus = (EditText) findViewById(R.id.edt_update_profile_hiv_status);
		medtInterest = (EditText) findViewById(R.id.edt_update_profile_interest);
		medtSexRole = (EditText) findViewById(R.id.edt_update_profile_sex_role);
		mbtnUpdate = (Button) findViewById(R.id.btn_update_profile_update);
		mtxtTap = (TextView) findViewById(R.id.txt_update_profile_photo);
		
		mimgback.setVisibility(View.INVISIBLE);
		mimgMenu.setVisibility(View.INVISIBLE);
		mtxtTitle.setText(getResources().getString(R.string.profile));
		mbtnUpdate.setOnClickListener(this);
		mtxtTap.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_update_profile_update:
			if(checkValidation())
			{
				String postData = getUpadteProfileRequestJson();
				Log.e("Post Data", postData);
				postData(DatingUrlConstants.UPDATE_PROFILE_URL, ApiEvent.UPDATE_PROFILE_EVENT, postData);
			}
			
			break;
		case R.id.txt_update_profile_photo:
			ToastCustom.underDevelopment(this);
			break;
			
		default:
			break;
		}
	}
	/**
	 * Validation 
	 */
	private boolean checkValidation()
	{
		boolean valid = true;
		if(!Validation.hasText(medtFname))
			valid = false;
		if(!Validation.hasText(medtLname))
			valid = false;
		if(!Validation.hasText(medtDob))
			valid = false;
		if(!Validation.hasText(medtGender))
			valid = false;
		if(!Validation.hasText(medtCountry))
			valid = false;
		if(!Validation.hasText(medtMobile))
			valid = false;
		if(!Validation.hasText(medtAboutMe))
			valid = false;
		if(!Validation.hasText(medtAge))
			valid = false;
		if(!Validation.hasText(medtHeight))
			valid = false;
		if(!Validation.hasText(medtWeight))
			valid = false;
		if(!Validation.hasText(medtLokingFor))
			valid = false;
		if(!Validation.hasText(medtHivStatus))
			valid = false;
		if(!Validation.hasText(medtInterest))
			valid = false;
		if(!Validation.hasText(medtSexRole))
			valid = false;
		
		return valid;
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);

		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				switch (serviceResponse.getEventType()) {
				case ApiEvent.UPDATE_PROFILE_EVENT:
					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					finish();
					break;
					
				default:
					break;
				}
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
	/*{
		  "userid" : "1",
		  "fname" : "shahid",
		  "lname" : "ansari",
		  "dob" : "1987-08-12",
		  "gender" : "M",
		  "country" : "India",
		  "mobile" : "9999860594","about_me":"i am cool","age":"24","height":"xyz",
		  "weight":"xyz","hiv_status":"xyz","looking_for":"xyz","interest":"zyx","sex_role":"xyz"}*/
	private String getUpadteProfileRequestJson()
	{
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.putOpt("fname", medtFname.getText().toString());
			jsonObject.putOpt("lname", medtLname.getText().toString());
			jsonObject.putOpt("dob", medtDob.getText().toString());
			jsonObject.putOpt("gender", medtGender.getText().toString());
			jsonObject.putOpt("country", medtCountry.getText().toString());
			jsonObject.putOpt("mobile", medtMobile.getText().toString());
			jsonObject.putOpt("about_me", medtAboutMe.getText().toString());
			jsonObject.putOpt("age", medtAge.getText().toString());
			jsonObject.putOpt("height", medtHeight.getText().toString());
			jsonObject.putOpt("weight", medtWeight.getText().toString());
			jsonObject.putOpt("hiv_status", medtHivStatus.getText().toString());
			jsonObject.putOpt("looking_for", medtLokingFor.getText().toString());
			jsonObject.putOpt("interest", medtInterest.getText().toString());
			jsonObject.putOpt("sex_role", medtSexRole.getText().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
		
	}
}
