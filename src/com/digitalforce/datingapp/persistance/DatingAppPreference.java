/**
 * 
 */
package com.digitalforce.datingapp.persistance;

import com.farru.android.persistance.AppSharedPreference;

/**
 * @author FARHAN
 *
 */
public class DatingAppPreference extends AppSharedPreference{
	
	public final static String USER_DEVICE_LATITUDE  = "user_device_lat";
	public final static String USER_DEVICE_LONGITUDE = "user_device_long";
	
	public final static String USER_ID = "user_id";
    public final static String USER_NAME = "user_name";
    public final static String USER_PROFILE_URL  = "user_profile_url";
	public final static String USER_TC_ACCEPT  = "user_tc_accept";
	
	public final static String MIN_AGE  = "min_age";
	public final static String MAX_AGE  = "max_age";
	public final static String SEX_ROLE = "sex_role";
}
