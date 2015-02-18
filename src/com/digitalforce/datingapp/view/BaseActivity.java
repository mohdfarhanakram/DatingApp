/**
 * 
 */
package com.digitalforce.datingapp.view;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

//import com.splunk.mint.Mint;
import android.os.AsyncTask;
import android.os.Environment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Config;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.digitalforce.datingapp.BuildConfig;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.parser.BaseParser;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.application.BaseApplication;
import com.farru.android.network.ServiceResponse;
import com.farru.android.network.VolleyGenericRequest;
import com.farru.android.network.VolleyHelper;
import com.farru.android.parser.IParser;
import com.farru.android.ui.IScreen;
import com.farru.android.utill.LocationUtils;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;
import com.farru.android.volley.AuthFailureError;
import com.farru.android.volley.NetworkError;
import com.farru.android.volley.NoConnectionError;
import com.farru.android.volley.ParseError;
import com.farru.android.volley.Response;
import com.farru.android.volley.ServerError;
import com.farru.android.volley.TimeoutError;
import com.farru.android.volley.VolleyError;

/**
 * @author FARHAN
 *
 */
/**
 * This class is used as base-class for application-base-activity.
 */
public abstract class BaseActivity extends FragmentActivity implements IScreen,Response.Listener, Response.ErrorListener {

	private String	LOG_TAG	= getClass().getSimpleName();
    private GoogleCloudMessaging gcm;
    private String regId;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            restoreInstanceState(savedInstanceState);
        }
		super.onCreate(savedInstanceState);
        //Mint.initAndStartSession(this, AppConstants.BUGSENSE_API_KEY);   //Bug Sense session
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "onCreate()");
		}

		getHashKey();

        if(checkPlayServices()){

            gcm = GoogleCloudMessaging.getInstance(this);
            regId = getRegistrationId(this);

            if(StringUtils.isNullOrEmpty(regId)){
                registerInBackground();
            }
        }
	}

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(BaseActivity.this);
                    }
                    regId = gcm.register(AppConstants.SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;

                    DatingAppPreference.putString(DatingAppPreference.GCM_REGISTRATION_ID,regId,BaseActivity.this);
                    DatingAppPreference.putInt(DatingAppPreference.GCM_VERSION_ID, getAppVersion(BaseActivity.this), BaseActivity.this);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                Log.e(LOG_TAG,"GCM Id+ "+regId);
                return null;
            }

            @Override
            protected void onPostExecute(Void msg) {

            }
        }.execute(null, null, null);

    }


    private String getRegistrationId(Context context) {

        String gcmRegId = DatingAppPreference.getString(DatingAppPreference.GCM_REGISTRATION_ID,"",context);
        if(StringUtils.isNullOrEmpty(gcmRegId)){
            return "";
        }

        int registeredVersion = DatingAppPreference.getInt(DatingAppPreference.GCM_VERSION_ID, Integer.MIN_VALUE, context);

        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return gcmRegId;

    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /*
     * Save instance state
     */
    protected void saveInstanceState(Bundle outState){

    }

    /*
     * Restore instance state.
     */
    protected void restoreInstanceState(Bundle savedInstanceState){

    }


    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		/*View backBtn = findViewById(R.id.img_action_back);
		if(backBtn!=null){
			backBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();

				}
			});
		}*/
		View menuBtn = findViewById(R.id.img_action_menu);
		if(menuBtn!=null){
			menuBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intentMenu = new Intent(BaseActivity.this, MenuActivity.class);
					startActivityForResult(intentMenu, AppConstants.REQUEST_CODE_FOR_MENU_SCREEN);

				}
			});

		}
	}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }*/



    @Override
	protected void onResume() {
		super.onResume();
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "onResume()");
		}
		
		Application application = this.getApplication();
		if (application instanceof BaseApplication) {
			BaseApplication baseApplication = (BaseApplication) application;
			if (baseApplication.isAppInBackground()) {
				onAppResumeFromBackground();
			}
			baseApplication.onActivityResumed();
		}


	}



	/**
	 * This callback will be called after onResume if application is being
	 * resumed from background. <br/>
	 * 
	 * Subclasses can override this method to get this callback.
	 */
	protected void onAppResumeFromBackground() {
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "onAppResumeFromBackground()");
		}
	}

	/**
	 * This method should be called to force app assume itself not in
	 * background.
	 */
	public final void setAppNotInBackground() {
		Application application = this.getApplication();
		if (application instanceof BaseApplication) {
			BaseApplication baseApplication = (BaseApplication) application;
			baseApplication.setAppInBackground(false);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "onPause()");
		}

		Application application = this.getApplication();
		if (application instanceof BaseApplication) {
			BaseApplication baseApplication = (BaseApplication) application;
			baseApplication.onActivityPaused();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "onNewIntent()");
		}
	}

	/**
	 * @param serviceResponse
	 */
	@Override
	public final void handleUiUpdate(final ServiceResponse serviceResponse) {
		if (isFinishing()) {
			return;
		}
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				updateUiDelegate(serviceResponse);
			}
		});
	}

	/**
	 * Users of this method should call this method only from UI thread.
	 */
	public final void updateUiDelegate(ServiceResponse serviceResponse) {
		if (BuildConfig.DEBUG) {
			updateUi(serviceResponse);
		} else {
			try {
				updateUi(serviceResponse);
			} catch (Exception e) {
				Log.e(LOG_TAG, "updateUi()", e);
			}
		}
	}

	/**
	 * Subclass should over-ride this method to update the UI with response,
	 * this base class promises to call this method from UI thread.
	 * 
	 * @param serviceResponse
	 */
	public void updateUi(ServiceResponse serviceResponse){
		
		if(serviceResponse!=null){
			
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				if(serviceResponse.getEventType()==ApiEvent.LOGOUT_EVENT){
					showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					logout();
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

	// ////////////////////////////// show and hide ProgressDialog

	private ProgressDialog	mProgressDialog;

	/**
	 * Shows a simple native progress dialog<br/>
	 * Subclass can override below two methods for custom dialogs- <br/>
	 * 1. showProgressDialog <br/>
	 * 2. removeProgressDialog
	 * 
	 * @param bodyText
	 */
	public void showProgressDialog(String bodyText) {
		if (isFinishing()) {
			return;
		}
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(BaseActivity.this);
			mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setOnKeyListener(new Dialog.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH) {
						return true; //
					}
					return false;
				}
			});
		}

		mProgressDialog.setMessage(bodyText);

		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	/**
	 * Removes the simple native progress dialog shown via showProgressDialog <br/>
	 * Subclass can override below two methods for custom dialogs- <br/>
	 * 1. showProgressDialog <br/>
	 * 2. removeProgressDialog
	 */
	public void removeProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	// ////////////////////////////// show and hide key-board

	/**
	 * shows the soft key pad
	 */
	public void showSoftKeypad() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				try {
					imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
				} catch (Exception e) {
					Log.e(LOG_TAG, "showSoftKeypad()", e);
				}
			}
		}, 100);
	}

	/**
	 * hides the soft key pad
	 */
	public void hideSoftKeypad() {
		hideSoftKeypad(getWindow().getCurrentFocus());
	}

	/**
	 * hides the soft key pad
	 */
	public void hideSoftKeypad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e(LOG_TAG, "hideSoftKeypad()", e);
        }
    }

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		View v = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);

		if (v instanceof EditText) {
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];

			if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
				hideSoftKeypad();
			}
		}
		return ret;
	}

	/**
	 * @param pAlertId
	 * @param pAlertMessage
	 * @param pAlertTag
	 */
	public final void showCustomAlert(int pAlertId, String pAlertMessage, String pAlertTag) {
		/*CustomAlert customAlert = new CustomAlert();
		customAlert.setAlertId(pAlertId);
		//customAlert.setTitle(getString(R.string.app_name));
		customAlert.setMessage(pAlertMessage);
		customAlert.setCancelable(false);
		customAlert.show(this, pAlertTag);*/
	}


	/**
	 * Utility function for displaying progress dialog
	 *
	 * @param to be shown
	 */

	public void showProgressDialog() {

		showProgressDialog("Loading...");
	}


	/**
	 * Helper method for making a http post request
	 *
	 * @param url         request url
	 * @param eventType   request event type
	 * @param map         post body params as map
	 * @param postData    string/json post body
	 * @param contentType content type for distinguishing json/plain text request
	 * @param parser      parser object tobe used for response parsing
	 */
	private void postData(String url, int eventType, HashMap<String, String> map, String postData, int contentType, IParser parser,Object requestObject) {

		if(Config.DEBUG)
			Log.d("request",url+"");
		try {
			VolleyGenericRequest req = null;
			if (map != null) {
				req = new VolleyGenericRequest(VolleyGenericRequest.ContentType.FORM_ENCODED_DATA, url, map, this, this, this);
			} else
				req = new VolleyGenericRequest(contentType, url, postData, this, this, this);
			req.setEventType(eventType);

			req.setRequestData(requestObject);

			req.setParser(parser == null ? new BaseParser() : parser);

			VolleyHelper.getInstance(this).addRequestInQueue(req);
			Log.d("URL:  ", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method for making a http post request
	 *
	 * @param url         request url
	 * @param eventType   request event type
	 * @param postData    string/json post body
	 * @param contentType content type for distinguishing json/plain text request
	 * @param parser      parser object tobe used for response parsing
	 */
	public void postData(String url, int eventType, String postData, int contentType, IParser parser) {
		postData(url, eventType, null, postData, contentType, parser,null);

	}

	/**
	 * Helper method for making a http post request
	 *
	 * @param url       request url
	 * @param eventType request event type
	 * @param map       post body params as map
	 * @param parser    parser object to be used for response parsing
	 */
	public void postData(String url, int eventType, HashMap<String, String> map, IParser parser) {
		postData(url, eventType, map, null, VolleyGenericRequest.ContentType.FORM_ENCODED_DATA, parser,null);

	}

	public void postData(String url, int eventType, String postData) {
		postData(url, eventType,postData, true);

	}

	public void postData(String url, int eventType, String postData,boolean isLoaderRequired) {
		if(isLoaderRequired)
			showProgressDialog();
		postData(url, eventType, null, postData, VolleyGenericRequest.ContentType.JSON, null,null);

	}
	
	public void postData(String url, int eventType,String data, IParser parser) {
		postData(url, eventType, null, data, VolleyGenericRequest.ContentType.JSON, parser,null);

	}


	public void postData(String url, int eventType, String postData,boolean isLoaderRequired,Object requestObj) {
		if(isLoaderRequired)
			showProgressDialog();
		postData(url, eventType, null, postData, VolleyGenericRequest.ContentType.JSON, null,requestObj);

	}



	/**
	 * Helper method to make Http get data from server.
	 *
	 * @param url       request url
	 * @param eventType request event type
	 * @param parser    parser object to be used for response parsing
	 * @param requestObject Object used to uniquely identify the response
	 */

	public boolean fetchData(String url, final int eventType, IParser parser, Object requestObject,Response.Listener responseListener,Response.ErrorListener errorListener,Context ctx,boolean isLoaderRequired) {
		boolean returnVal = false;
		if (Utils.isInternetAvailable(this)) {
			if(isLoaderRequired)
				showProgressDialog();

			final IParser parser1 = parser == null ? new BaseParser() : parser;

			String cachedResponse = getJSONForRequest(eventType);

			//TODO apiToken in refine catalog api
			if (StringUtils.isNullOrEmpty(cachedResponse)) {
				if(Config.DEBUG)
					Log.d("request",url+"");

				VolleyGenericRequest req = new VolleyGenericRequest(url, responseListener, errorListener, ctx);
				req.setRequestData(requestObject);

				req.setEventType(eventType);
				req.setParser(parser1);
				//TODO  req.setRequestTimeOut(Constants.API_TIMEOUT);
				VolleyHelper.getInstance(this).addRequestInQueue(req);
			} else {

				final String tempResponse = cachedResponse;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//       Log.d("BaseActivity fetchData", "found response in cache for eventType " + eventType);
						onResponse(parser1.parseData(eventType, tempResponse));
						//TODO execute it on Non UI Thread
					}
				});
			}
		} else {
			removeProgressDialog();

			showCommonError("Network Error Occured");
			returnVal = true;
		}
		return returnVal;
	}

	/**
	 * Helper method to make Http get data from server.
	 *
	 * @param url       request url
	 * @param eventType request event type
	 * @param parser    parser object to be used for response parsing
	 */

	public boolean fetchData(String url, final int eventType, IParser parser) {
		return  fetchData(url, eventType,parser, null);
	}
	public boolean fetchData(String url, final int eventType, IParser parser, Object requestObject) {
		return fetchData(url,eventType,parser,requestObject,this,this,this,true);
	}

	public boolean fetchData(String url, final int eventType, IParser parser, Object requestObject,boolean isLoaderRequired) {
		return fetchData(url,eventType,parser,requestObject,this,this,this,isLoaderRequired);
	}


	/**
	 * Helper function to obtain cached json data based on event type
	 *
	 * @param eventType
	 * @return
	 */
	public String getJSONForRequest(int eventType) {
		String request = null;
		switch (eventType) {
		// Assign key ToDo
		/*case ApiType.API_INIT:
                request = Constants.RESPONSE_INIT_API;
                break;*/

		default:

		}

		/*if(eventType==ApiType.API_GET_REVIEW_ORDER_DATA || eventType==ApiType.API_GET_REVIEW_ORDER_DATA){
            return Constants.REVIEW_JSON_STRING;
        }*/

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (preferences.contains(request)) {
			return preferences.getString(request, "");
		}

		return "";
	}

	/**
	 * Utility function for showing common error dialog.
	 *
	 * @param message
	 */

	public void showCommonError(String message) {
		if (TextUtils.isEmpty(message)) {
			message = "Something went wrong";
		}
		ToastCustom.makeText(this, message, 3000);
	}

	@Override
	public void onResponse(Object response) {
		removeProgressDialog();
		updateUi((ServiceResponse)response);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		removeProgressDialog();
		ServiceResponse responseObj = new ServiceResponse();

		responseObj.setErrorCode(ServiceResponse.MESSAGE_ERROR);

		/*int  statusCode = error.networkResponse.statusCode;
		NetworkResponse response = error.networkResponse;

		Log.d("testerror",""+statusCode+" "+response.data);*/
		// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
		// For AuthFailure, you can re login with user credentials.
		// For ClientError, 400 & 401, Errors happening on client side when sending api request.
		// In this case you can check how client is forming the api and debug accordingly.
		// For ServerError 5xx, you can do retry or handle accordingly.
		if( error instanceof NetworkError) {
			responseObj.setErrorMessages("Network Connection is not available.");
		} else if( error instanceof ServerError) {
			responseObj.setErrorMessages("Server Error");

		} else if( error instanceof AuthFailureError) {
			responseObj.setErrorMessages("Auth Error");
		} else if( error instanceof ParseError) {
			responseObj.setErrorMessages("Parsing Error");
		} else if( error instanceof NoConnectionError) {

			responseObj.setErrorMessages("No connection could be established.");

		} else if( error instanceof TimeoutError) {
			responseObj.setErrorMessages("Network Connection Time out");
		}

		updateUi(responseObj);
	}




	private void getHashKey(){
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.digitalforce.datingapp", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	public boolean isUserLogin(){
		String uid = DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this);
		return !StringUtils.isNullOrEmpty(uid);
	}


	public boolean isTcAccept(){
		return  DatingAppPreference.getBoolean(DatingAppPreference.USER_TC_ACCEPT, false, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent inent) {

		super.onActivityResult(requestCode, resultCode, inent);
		if(requestCode==AppConstants.REQUEST_CODE_FOR_MENU_SCREEN){

			switch (resultCode) {
			case AppConstants.RESULT_CODE_FOR_LOGOUT:
				requestForLogout();
				break;

			default:
				break;
			}

		}
	}
	
	
	private void requestForLogout(){
		showProgressDialog();
		postData(DatingUrlConstants.LOGOUT_URL, ApiEvent.LOGOUT_EVENT, getLogoutRequestJson());
	}
	
	
	private String getLogoutRequestJson(){

		//{"userid" : "12345"}
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}
	
	private void logout(){
		DatingAppPreference.putString(DatingAppPreference.USER_ID, "", this);
        DatingAppPreference.putString(DatingAppPreference.USER_NAME, "", this);
        DatingAppPreference.putString(DatingAppPreference.USER_PROFILE_URL, "", this);
        DatingAppPreference.putString(DatingAppPreference.GCM_REGISTRATION_ID,"",BaseActivity.this);
		Intent i = new Intent(this,LoginActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	        i.addFlags(0x8000);
		startActivity(i);
	}
	
	
	 public void picassoLoad(String url, ImageView imageView) {
	        PicassoEx.getPicasso(this).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
	 }
	 
	 public void picassoLoad(String imgUrl, ImageView imageView,int width,int height) {
		 PicassoEx.getPicasso(this).load(imgUrl).error(R.drawable.farhan).placeholder(R.drawable.farhan).resize(width,height).into(imageView);
	 }



    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,AppConstants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("RudeBoy", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }




    public File createFile(int event) {
        // Create an image file name
        try{
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File albumF = getAlbumDir();
            File f = null;
            switch(event){
                case 1: // image
                    String imageFileName = "IMG" + timeStamp + "_";
                    return File.createTempFile(imageFileName, ".jpg", albumF);

                case 3:  //audio
                    String audioFileName = "AUDIO"+ timeStamp + "_";
                    return File.createTempFile(audioFileName, ".3gpp", albumF);

                case 4:  //video
                    String videoFileName = "VIDEO"+ timeStamp + "_";
                    return File.createTempFile(videoFileName, ".mp4", albumF);
                default:

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    protected File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = getAlbumStorageDir("n2him");
            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }
        return storageDir;
    }


    protected File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                ),
                albumName
        );
    }






}
