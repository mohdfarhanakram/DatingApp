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
	public final static String ETHNICITY = "ethnicity";
    public final static String MIN_HEIGHT = "min_height";
    public final static String MAX_HEIGHT = "max_height";
    public final static String MIN_WEIGHT = "min_weight";
    public final static String MAX_WEIGHT = "max_weight";
    public final static String BODY_TYPE = "body_type";
    public final static String SEX_ROLE = "sex_role";
    public final static String RELATION_SHIP = "relation_ship";
    public final static String LOOKING_FOR = "looking_for";

    public static final String PROPERTY_REG_ID = "registration_id";
    /** GCM **/
    public static final String GCM_REGISTRATION_ID = "gcm_registration_id";
    public static final String GCM_VERSION_ID = "gcm_version_id";

    public static final String FILTER_ENABLE = "filter_enable";
}
