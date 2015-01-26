/**
 * 
 */
package com.digitalforce.datingapp.view;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.fragments.BaseFragment;
import com.digitalforce.datingapp.fragments.PrivatePhotoFragment;
import com.digitalforce.datingapp.fragments.PublicPhotoFragment;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.Utils;

/**
 * @author FARHAN
 *
 */
public class MyPictureActivity extends BaseActivity implements OnClickListener{

	private TextView mtxtTitle, mtxtPublicPic, mtxtPrivatePic;

	private String mImagePath;
	private String mBaseEncodedString;
	private boolean mIsRemoveClicked = false;

	private boolean isPublicSelected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_picture);

		mtxtTitle = (TextView) findViewById(R.id.screen_title);
		mtxtPublicPic = (TextView) findViewById(R.id.txt_public_photo);
		mtxtPrivatePic = (TextView) findViewById(R.id.txt_private_photo);

		mtxtPublicPic.setOnClickListener(this);
		mtxtPrivatePic.setOnClickListener(this);
		findViewById(R.id.remove_image).setOnClickListener(this);

        drawFragment(isPublicSelected);

		mtxtTitle.setText(getResources().getString(R.string.mypicyure));

	}

    @Override
    protected void saveInstanceState(Bundle outState) {

        outState.putBoolean("isPublicSelected",isPublicSelected);
		outState.putBoolean("isRemoveClicked",mIsRemoveClicked);

    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {

        isPublicSelected = savedInstanceState.getBoolean("isPublicSelected", true);
		mIsRemoveClicked = savedInstanceState.getBoolean("isRemoveClicked", false);

    }

    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		(findViewById(R.id.img_action_add)).setVisibility(View.VISIBLE);
		((ImageView)findViewById(R.id.img_action_add)).setImageResource(R.drawable.add_picture_icon);
		((ImageView)findViewById(R.id.img_action_add)).setOnClickListener(this);
	}


	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);
		
		removeProgressDialog();

		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
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


		FragmentManager fragmentmaneger = getSupportFragmentManager();
		for(int i=0; i<fragmentmaneger.getFragments().size(); i++){
			if(fragmentmaneger.getFragments().get(i) instanceof BaseFragment){
				BaseFragment fragment = (BaseFragment)fragmentmaneger.getFragments().get(i);
				fragment.updateUi(serviceResponse);
			}

		}

	}
	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_public_photo:
            drawFragment(true);
			break;
		case R.id.txt_private_photo:
            drawFragment(false);
			break;
		case R.id.img_action_add:
			selectImage();
			break;
		case R.id.remove_image:
			removeImage(!mIsRemoveClicked,isPublicSelected);
			break;
		default:
			break;
		}

	}

	private void removeImage(boolean isRemove,boolean isPublic){

		FragmentManager fragmentmaneger = getSupportFragmentManager();
		for(int i=0; i<fragmentmaneger.getFragments().size(); i++){
			if(fragmentmaneger.getFragments().get(i) instanceof BaseFragment){
				BaseFragment fragment = (BaseFragment)fragmentmaneger.getFragments().get(i);
				mIsRemoveClicked = isRemove;
				fragment.removePhoto(isRemove,isPublic);
			}

		}

	}


    private void drawFragment(boolean fragmentType){
        isPublicSelected = fragmentType;
        if(fragmentType){
            selectFragment(new PublicPhotoFragment());
            mtxtPublicPic.setBackgroundResource(R.drawable.left_corner_round);
            mtxtPrivatePic.setBackgroundResource(Color.TRANSPARENT);
            mtxtPrivatePic.setTextColor(Color.WHITE);
            mtxtPublicPic.setTextColor(Color.BLACK);
        }else{
            selectFragment(new PrivatePhotoFragment());
            mtxtPrivatePic.setBackgroundResource(R.drawable.right_corner_round);
            mtxtPublicPic.setBackgroundResource(Color.TRANSPARENT);
            mtxtPrivatePic.setTextColor(Color.BLACK);
            mtxtPublicPic.setTextColor(Color.WHITE);
        }

    }

	private void selectFragment(Fragment fragment)
	{
		FragmentManager fragmentmaneger = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentmaneger.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_picture, fragment);
		fragmentTransaction.commit();

	}


	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Gallery",
		"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(isPublicSelected)
			builder.setTitle("Add Public Photo");
		else
			builder.setTitle("Add Private Photo");

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

					mImagePath = f.getAbsolutePath();
                    navigateToCropImage();
					//encodeImagetoStringAndUploadImage();


				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == AppConstants.REQUEST_CODE_FOR_GALLERY) {
				Uri selectedImageUri = data.getData();

				mImagePath = Utils.getPath(selectedImageUri, this);
                navigateToCropImage(); 
				//encodeImagetoStringAndUploadImage();
			}else if(requestCode == AppConstants.REQUEST_CODE_FOR_CROP){
            	
            	mBaseEncodedString = data.getStringExtra(AppConstants.ENCODED_IMAGE_STRING);
            	showProgressDialog("Picture is being uploaded..");
            	
            	int event;
				if(isPublicSelected)
					event = ApiEvent.UPLOAD_PUBLIC_PICTURE_EVENT;
				else
					event = ApiEvent.UPLOAD_PRIVATE_PICTURE_EVENT;

				postData(DatingUrlConstants.UPLOAD_MY_PICTURE_URL, event, getUploadPicRequestJson(mBaseEncodedString),null);            	
            }
		}
	}





	private String getUploadPicRequestJson(String bas64Image){
		//{"userid" : "2","image":"base64ncoded","privacy":"public"} or {"userid" : "2","image":"base64ncoded","privacy":"private"}
		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));

			if(isPublicSelected)
				jsonObject.putOpt("privacy","public");
			else
				jsonObject.putOpt("privacy","private");

			jsonObject.putOpt("image",bas64Image);

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


	/*public void encodeImagetoStringAndUploadImage() {
		new AsyncTask<Void, Void, String>() {

			protected void onPreExecute() {
				showProgressDialog("Image is being updated..");
			};

			@Override
			protected String doInBackground(Void... params) {
				BitmapFactory.Options options = null;
				options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				Bitmap bitmap = BitmapFactory.decodeFile(mImagePath,options);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// Must compress the Image to reduce image size to make upload easy
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); 
				byte[] byte_arr = stream.toByteArray();
				// Encode Image to String
				mBaseEncodedString = Base64.encodeToString(byte_arr, 0);
				return "";
			}

			@Override
			protected void onPostExecute(String msg) {
				int event;
				if(isPublicSelected)
					event = ApiEvent.UPLOAD_PUBLIC_PICTURE_EVENT;
				else
					event = ApiEvent.UPLOAD_PRIVATE_PICTURE_EVENT;

				postData(DatingUrlConstants.UPLOAD_MY_PICTURE_URL, event, getUploadPicRequestJson(mBaseEncodedString),null);
			}
		}.execute(null, null, null);
	}
*/

	private void navigateToCropImage(){

		Intent i = new Intent(this,ImageCropActivity.class);
		i.putExtra(AppConstants.IMAGE_PATH, mImagePath);
		startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_CROP);
	}
	
	
	public String getImageEncodedData(){
		return mBaseEncodedString;
	}

}
