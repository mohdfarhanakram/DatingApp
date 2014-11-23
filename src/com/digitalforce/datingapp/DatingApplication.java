package com.digitalforce.datingapp;

import com.digitalforce.datingapp.constants.AppConstants;
import com.farru.android.application.BaseApplication;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

public class DatingApplication extends BaseApplication{


	
	@Override
	public void onCreate() {
		super.onCreate();
		
		 /*Permission[] perm = new Permission[3];
	        perm[0] = Permission.BASIC_INFO;
	        perm[1] = Permission.USER_BIRTHDAY;
	        perm[2] = Permission.EMAIL;
	        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
	                .setAppId(AppConstants.FB_APP_ID)
	                .setNamespace(AppConstants.APP_NAME_SPACE)
	                .setPermissions(perm)
	                .setAskForAllPermissionsAtOnce(false)
	                .build();

	        SimpleFacebook.setConfiguration(configuration);*/
	}

}
