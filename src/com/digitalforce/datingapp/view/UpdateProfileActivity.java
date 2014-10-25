package com.digitalforce.datingapp.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.utils.Validation;
import com.digitalforce.datingapp.widgets.RoundedImageView;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;



public class UpdateProfileActivity extends BaseActivity implements OnClickListener{

	private ImageView mimgback, mimgMenu;
	private TextView mtxtTitle, mtxtTap;
	private EditText medtFname,medtLname,medtDob,medtGender,medtCountry,medtMobile,
						medtAboutMe, medtAge,  medtHeight, medtWeight, medtLokingFor,
						medtHivStatus,medtInterest,medtSexRole; 
	private Button mbtnUpdate;
	
	private RoundedImageView mProfileImage;
	
	private Bitmap mProfileBitmap;
	
	private String mImagPath;
	private String mBaseEncodedString;
	
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
		
		mProfileImage = (RoundedImageView)findViewById(R.id.img_nearby_member);
		
		mimgback.setVisibility(View.INVISIBLE);
		mimgMenu.setVisibility(View.INVISIBLE);
		mtxtTitle.setText(getResources().getString(R.string.profile));
		mbtnUpdate.setOnClickListener(this);
		mtxtTap.setOnClickListener(this);
		
		mProfileImage.setFocusable(true);
		
		fetchUserProfileData();
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
			
			selectImage();
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
		
		removeProgressDialog();
		
		

		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				switch (serviceResponse.getEventType()) {
				case ApiEvent.UPDATE_PROFILE_EVENT:
					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					finish();
					break;
				case ApiEvent.UPLOAD_PROFILE_PIC_EVENT:	

					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					
					String picUrl = (String)serviceResponse.getResponseObject();
					picassoLoad(picUrl, mProfileImage);
					break;
				case ApiEvent.SHOW_PROFILE_EVENT:
					ArrayList<UserInfo> userInfoList = (ArrayList<UserInfo>) serviceResponse.getResponseObject();
					if(userInfoList.size()>0){
						setUserProfileData(userInfoList.get(0));
					}
					
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
		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
		
	}
	
	
	private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };
 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Changed Profile Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent,AppConstants.REQUEST_CODE_FOR_CAMERA );
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            AppConstants.REQUEST_CODE_FOR_GALLERY);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.REQUEST_CODE_FOR_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    //final Bitmap bm;
                	
                	
                	
                	mImagPath = f.getAbsolutePath();
                	
                	navigateToCropImage();
                	
                	//encodeImagetoStringAndUploadImage();
                	
                  
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == AppConstants.REQUEST_CODE_FOR_GALLERY) {
                Uri selectedImageUri = data.getData();
 
                mImagPath = Utils.getPath(selectedImageUri, this);
                
                navigateToCropImage();
                
                //encodeImagetoStringAndUploadImage();
            }else if(requestCode == AppConstants.REQUEST_CODE_FOR_CROP){
            	
            	mBaseEncodedString = data.getStringExtra(AppConstants.ENCODED_IMAGE_STRING);
            	showProgressDialog("Picture is being uploaded..");
            	postData(DatingUrlConstants.UPLOAD_PROFILE_PIC_URL, ApiEvent.UPLOAD_PROFILE_PIC_EVENT, getUploadPicRequestJson(mBaseEncodedString),null);
            	
            }
        }
    }
	
	
	
	
	
	private String getUploadPicRequestJson(String bas64Image){
		//{"userid" : "1","userImage":"BASE64_ENCODEDDATA"}
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.putOpt("userImage",bas64Image);
			jsonObject.putOpt("device_type","android");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		
	}
	
	private void fetchUserProfileData(){
		String postData = getShowProfileRequestJson();
		Log.e("Post Data", postData);
		postData(DatingUrlConstants.SHOW_PROFILE_URL, ApiEvent.SHOW_PROFILE_EVENT, postData);
	}
	
	
	private String getShowProfileRequestJson(){
		//{"userid":"12345","login_userid":"32"}
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.putOpt("login_userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();

	}
	
	

	private void setUserProfileData(UserInfo userInfo)
	{
		if(!StringUtils.isNullOrEmpty(userInfo.getFirstName())) medtFname.setText(userInfo.getFirstName());
		if(!StringUtils.isNullOrEmpty(userInfo.getLastName())) medtLname.setText(userInfo.getLastName());
		if(!StringUtils.isNullOrEmpty(userInfo.getDob())) medtDob.setText(userInfo.getDob());
		if(!StringUtils.isNullOrEmpty(userInfo.getGender())) medtGender.setText(userInfo.getGender());
		if(!StringUtils.isNullOrEmpty(userInfo.getCountry())) medtCountry.setText(userInfo.getCountry());
		if(!StringUtils.isNullOrEmpty(userInfo.getMobile())) medtMobile.setText(userInfo.getMobile());
		if(!StringUtils.isNullOrEmpty(userInfo.getAboutMe())) medtAboutMe.setText(userInfo.getAboutMe());
		if(!StringUtils.isNullOrEmpty(userInfo.getAge())) medtAge.setText(userInfo.getAge());
		if(!StringUtils.isNullOrEmpty(userInfo.getHeight())) medtHeight.setText(userInfo.getHeight());
		if(!StringUtils.isNullOrEmpty(userInfo.getWeight())) medtWeight.setText(userInfo.getWeight());
		if(!StringUtils.isNullOrEmpty(userInfo.getLookingFor())) medtLokingFor.setText(userInfo.getLookingFor());
		if(!StringUtils.isNullOrEmpty(userInfo.getHivStatus())) medtHivStatus.setText(userInfo.getHivStatus());
		if(!StringUtils.isNullOrEmpty(userInfo.getInterest())) medtInterest.setText(userInfo.getInterest());
		if(!StringUtils.isNullOrEmpty(userInfo.getSexRole())) medtSexRole.setText(userInfo.getSexRole());
		
		
		if(!StringUtils.isNullOrEmpty(userInfo.getImage()))
		   picassoLoad(userInfo.getImage(), mProfileImage);
		
		
	}
	
	
	private void navigateToCropImage(){
		
		Intent i = new Intent(this,ImageCropActivity.class);
		i.putExtra(AppConstants.IMAGE_PATH, mImagPath);
		startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_CROP);
	}
	
	
	
}
