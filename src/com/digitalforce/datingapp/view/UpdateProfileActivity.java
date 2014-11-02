package com.digitalforce.datingapp.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import com.digitalforce.datingapp.dialog.DialogUpdateProfile;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.utils.Validation;
import com.digitalforce.datingapp.widgets.RoundedImageView;
import com.edmodo.cropper.CropImageView;
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

	private String mVideoEncodedString;
	private String mVideoFilePath;

	private String mAudioEncodedString;
	private String mAudioFilePath;
	private String mGender[] = {"Male","Female"};
	private String mHivStatus[] = {"Positive","Negative"};
	private String country_list[] = {"Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua &amp; Barbuda","Argentina","Armenia","Aruba",
			"Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia",
			"Bosnia &amp; Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Cape Verde",
			"Cayman Islands","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cruise Ship","Cuba","Cyprus","Czech Republic",
			"Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Estonia","Ethiopia","Falkland Islands","Faroe Islands",
			"Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam",
			"Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland",
			"Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyz Republic","Laos","Latvia","Lebanon","Lesotho","Liberia",
			"Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius",
			"Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia",
			"New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland",
			"Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre &amp; Miquelon","Samoa","San Marino","Satellite","Saudi Arabia","Senegal",
			"Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain","Sri Lanka","St Kitts &amp; Nevis","St Lucia",
			"St Vincent","St. Lucia","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este",
			"Togo","Tonga","Trinidad &amp; Tobago","Tunisia","Turkey","Turkmenistan","Turks &amp; Caicos","Uganda","Ukraine","United Arab Emirates","United Kingdom",
			"Uruguay","Uzbekistan","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"};
	
	//private String[] mAge;

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
		//medtDob = (EditText) findViewById(R.id.edt_update_profile_dob);
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
		medtFname.setOnClickListener(this);
		medtLname.setOnClickListener(this);
		medtCountry.setOnClickListener(this);
		medtMobile.setOnClickListener(this);
		medtAboutMe.setOnClickListener(this);
		medtLokingFor.setOnClickListener(this);
		medtInterest.setOnClickListener(this);
		medtSexRole.setOnClickListener(this);
		medtAge.setOnClickListener(this);
		medtGender.setOnClickListener(this);
		medtWeight.setOnClickListener(this);
		medtHeight.setOnClickListener(this);
		medtHivStatus.setOnClickListener(this);

		mProfileImage.setFocusable(true);

		findViewById(R.id.btn_update_profile_audio).setOnClickListener(this);
		findViewById(R.id.btn_update_profile_video).setOnClickListener(this);

		fetchUserProfileData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_update_profile_update:
			//if(checkValidation())
			//{
				String postData = getUpadteProfileRequestJson();
				Log.e("Post Data", postData);
				postData(DatingUrlConstants.UPDATE_PROFILE_URL, ApiEvent.UPDATE_PROFILE_EVENT, postData);
			//}

			break;
		case R.id.txt_update_profile_photo:

			selectImage();
			break;

		case R.id.btn_update_profile_audio:
			Intent i = new Intent(this,AudioRecorderActivity.class);
			startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_AUDIO);
			break;
		case R.id.btn_update_profile_video:
			videoRecording();
			break;
		case R.id.edt_update_profile_fname:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtFname, getResources().getString(R.string.fname), medtFname.getText().toString()).show();
			break;
		case R.id.edt_update_profile_lname:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtLname, getResources().getString(R.string.lname), medtLname.getText().toString()).show();
			break;
		/*case R.id.edt_update_profile_dob:
			break;*/
		case R.id.edt_update_profile_gender:
			
			showSingleSelectionDialog(medtGender, mGender, getResources().getString(R.string.gender));
			break;
		case R.id.edt_update_profile_country:
			//new DialogUpdateProfile(UpdateProfileActivity.this, medtCountry, getResources().getString(R.string.country), medtCountry.getText().toString()).show();
			showSingleSelectionDialog(medtCountry, country_list, getResources().getString(R.string.country));
			break;
		case R.id.edt_update_profile_mobile:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtMobile, getResources().getString(R.string.mobile), medtMobile.getText().toString()).show();
			break;
		case R.id.edt_update_profile_about_me:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtAboutMe, getResources().getString(R.string.about_me), medtAboutMe.getText().toString()).show();
			break;
		case R.id.edt_update_profile_age:
			showSingleSelectionDialog(medtAge, getAgeArray(), getResources().getString(R.string.age));
			break;
		case R.id.edt_update_profile_weight:
			showSingleSelectionDialog(medtWeight, getWeightArray(), getResources().getString(R.string.weight));
			break;
		case R.id.edt_update_profile_height:
			showSingleSelectionDialog(medtHeight, getHeightArray(), getResources().getString(R.string.height));
			break;
		case R.id.edt_update_profile_hiv_status:
			showSingleSelectionDialog(medtHivStatus, mHivStatus, getResources().getString(R.string.hiv_status));
			break;
		case R.id.edt_update_profile_loking_for:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtLokingFor, getResources().getString(R.string.loking_for), medtLokingFor.getText().toString()).show();
			break;
		case R.id.edt_update_profile_interest:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtInterest, getResources().getString(R.string.interest), medtInterest.getText().toString()).show();
			break;
		case R.id.edt_update_profile_sex_role:
			new DialogUpdateProfile(UpdateProfileActivity.this, medtSexRole, getResources().getString(R.string.sex_role), medtSexRole.getText().toString()).show();
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
				case ApiEvent.UPLOAD_PROFILE_VIDEO_EVENT:
				case ApiEvent.UPLOAD_PROFILE_AUDIO_EVENT:
					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
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
			//jsonObject.putOpt("dob", medtDob.getText().toString());
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
					File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
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


	private void videoRecording(){
		//create new Intent
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		File f = new File(android.os.Environment.getExternalStorageDirectory(), "farhantemp.mp4");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		//intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri());
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT , 102400);  // 100kb
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT , 60);

		startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_VIDEO);
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

			}else if(requestCode == AppConstants.REQUEST_CODE_FOR_VIDEO){
				File f = new File(Environment.getExternalStorageDirectory().toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("farhantemp.mp4")) {
						f = temp;
						break;
					}
				}

				mVideoFilePath = f.getAbsolutePath();
				Toast.makeText(this, f.getAbsolutePath() , Toast.LENGTH_LONG).show();
				saveAndEncodeVideo();
			}else if(requestCode == AppConstants.REQUEST_CODE_FOR_AUDIO){
				File f = new File(Environment.getExternalStorageDirectory().toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("farhantemp.3gpp")) {
						f = temp;
						break;
					}
				}

				mAudioFilePath = f.getAbsolutePath();

				saveAndEncodeAudio();

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
		//if(!StringUtils.isNullOrEmpty(userInfo.getDob())) medtDob.setText(userInfo.getDob());
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


	private void saveAndEncodeVideo() {
		new AsyncTask<Void, Void, String>() {

			protected void onPreExecute() {
				showProgressDialog("Video is being uploaded..");
			};

			@Override
			protected String doInBackground(Void... params) {

				try{
					FileInputStream fis = new FileInputStream(mVideoFilePath);
					ByteArrayOutputStream objByteArrayOS = new ByteArrayOutputStream();
					byte[] byteBufferString = new byte[1024];

					for (int readNum; (readNum = fis.read(byteBufferString)) != -1;) 
					{
						objByteArrayOS.write(byteBufferString, 0, readNum);

					}

					byte[] byteBinaryData = Base64.encode((objByteArrayOS.toByteArray()), Base64.DEFAULT);

					mVideoEncodedString = new String(byteBinaryData);

					Log.e("video ", mVideoEncodedString);

				}catch(Exception e){
					e.printStackTrace();
				}

				return "";
			}

			@Override
			protected void onPostExecute(String msg) {

				uploadVideoOnServer();

			}
		}.execute(null, null, null);
	}


	private void uploadVideoOnServer(){

		postData(DatingUrlConstants.UPLOAD_PROFILE_VIDEO_URL, ApiEvent.UPLOAD_PROFILE_VIDEO_EVENT, getVideoJsonRequest(),null);
	}

	private String getVideoJsonRequest(){
		//{"userid" : "1","video":"base64encodeddata"}

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.putOpt("video",mVideoEncodedString);
			jsonObject.putOpt("device_type","android");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}


	public void saveAndEncodeAudio() {
		new AsyncTask<Void, Void, String>() {

			protected void onPreExecute() {
				showProgressDialog("Audio is being uploaded..");
			};

			@Override
			protected String doInBackground(Void... params) {

				try{

					FileInputStream fis = new FileInputStream(mAudioFilePath);
					ByteArrayOutputStream objByteArrayOS = new ByteArrayOutputStream();
					byte[] byteBufferString = new byte[1024];

					for (int readNum; (readNum = fis.read(byteBufferString)) != -1;) 
					{
						objByteArrayOS.write(byteBufferString, 0, readNum);

					}

					byte[] byteBinaryData = Base64.encode((objByteArrayOS.toByteArray()), Base64.DEFAULT);

					mAudioEncodedString = new String(byteBinaryData);

					Log.e("Audio ", mAudioEncodedString);

				}catch(Exception e){
					e.printStackTrace();
				}

				return "";
			}

			@Override
			protected void onPostExecute(String msg) {

				uploadAudioOnServer();

			}
		}.execute(null, null, null);
	}


	private void uploadAudioOnServer(){

		postData(DatingUrlConstants.UPLOAD_PROFILE_AUDIO_URL, ApiEvent.UPLOAD_PROFILE_AUDIO_EVENT, getAudioJsonRequest(),null);
	}
	
	private String getAudioJsonRequest(){
		//{"userid" : "1","video":"base64encodeddata"}

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
			jsonObject.putOpt("audio",mAudioEncodedString);
			jsonObject.putOpt("device_type","android");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}
	/**
	 * show dialog for single selection
	 * @param resId
	 * @param options
	 * @param title
	 */
	
	private void showSingleSelectionDialog(final EditText resId,final String[] options,String title){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		int selectedIndex = getSelectedIndex(resId.getText().toString(), options);
		builder.setSingleChoiceItems(options, selectedIndex, new DialogInterface.OnClickListener() {
		//builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				//TextView textView = (TextView)findViewById(resId);
				if(resId!=null){
					resId.setText(options[item]);
				}
				dialog.dismiss();
			}
		});
		builder.show();
	}
	/**
	 * 
	 * @return
	 */
	private String[] getAgeArray()
	{
			String[] age = new String[83];
		
		for(int i=0; i<83; i++){
			age[i] = (i+18)+"";
		}
		
		return age;
	}
	/**
	 * 
	 * @return
	 */
	private String[] getWeightArray()
	{
			String[] weight = new String[2202];
		
		for(int i=0; i<2202; i++){
			weight[i] = (i+99)+" "+"lbs";
		}
		
		return weight;
	}
	/**
	 * 
	 * @return
	 */
	private String[] getHeightArray()
	{
		String[] height = new String[84];
			
	    int index = 0;
		
		for(int i=0; i<7; i++)
		{
			for(int j=0; j<=11;j++)
			{
				
				height[index] = (i+3)+" ft "+j+" in";
				Log.e("KMD", "i="+i+" j="+j);
				index++;
			}
		}
		
		return height;
	}
	
	
	private int getSelectedIndex(String selected,String[] options){
		
		int selectedIndex = -1;
		for(int i=0; i<options.length; i++){
			
			if(options[i].equals(selected)){
				selectedIndex = i;
				break;
			}
			
		}
		
		return selectedIndex;
		
	}
	
		

}
