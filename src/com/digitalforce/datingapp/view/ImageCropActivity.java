/**
 * 
 */
package com.digitalforce.datingapp.view;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.utils.AppUtil;
import com.edmodo.cropper.CropImageView;
import com.edmodo.cropper.cropwindow.CropOverlayView;

/**
 * @author FARHAN
 *
 */
public class ImageCropActivity extends BaseActivity implements OnClickListener{
	
	private String mBaseEncodedString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_crop_activity);

		String path = getIntent().getStringExtra(AppConstants.IMAGE_PATH);

		BitmapFactory.Options options = null;
		options = new BitmapFactory.Options();
		options.inSampleSize = 3;
		Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        Bitmap aBitmap = AppUtil.getActualBitmap(path,bitmap);
        ((CropImageView) findViewById(R.id.crop_image_view)).setImageBitmap(aBitmap);




		/*ImageView imageview = (ImageView)findViewById(R.id.selected_image);
		final Rect result = new Rect(0, 0,500,500);
		CropOverlayView cropImageView = (CropOverlayView)findViewById(R.id.cropOverlayView);
		cropImageView.setVisibility(View.VISIBLE);
		cropImageView.setBitmapRect(result);*/
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		findViewById(R.id.img_action_back).setOnClickListener(this);
		findViewById(R.id.img_action_menu).setOnClickListener(this);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_action_back:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.img_action_menu:
			cropAndEncodeImage();
			
			break;

		default:
			break;
		}

	}
	
	
	public void cropAndEncodeImage() {
        new AsyncTask<Void, Void, String>() {
 
            protected void onPreExecute() {
            	showProgressDialog("Cropping..");
            };
 
            @Override
            protected String doInBackground(Void... params) {

                Bitmap bitmap = ((CropImageView)findViewById(R.id.crop_image_view)).getCroppedImage();
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
            	removeProgressDialog();
            	
            	Intent i = new Intent();
            	i.putExtra(AppConstants.ENCODED_IMAGE_STRING, mBaseEncodedString);
    			setResult(RESULT_OK, i);
    			finish();
		       
            }
        }.execute(null, null, null);
    }
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}
}
