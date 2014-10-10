/**
 * 
 */
package com.farru.android.ui;

public interface Events {
	/**
	 * int constants for framework dialogs/events
	 */
	int	DATE_PICKER_DIALOG				= 1;
	int	PHOTO_OPTIONS_DIALOG			= 2;
	int	EVENT_PHOTO_REMOVED				= 3;
	int	REQUEST_FACEBOOK_USER			= 4;
	int	APP_RATER_DIALOG				= 5;
	int	CUSTOM_ALERT_POSITIVE_BUTTON	= 6;

	/**
	 * int constants for project dialog/events
	 */
	int	USER_REGISTER_DIALOG			= 101;
	int	RESEND_VERIF_CODE_DIALOG		= 102;
	int	LOCATION_IS_OFF_DIALOG			= 103;
	int	GPS_IS_OFF_DIALOG				= 104;
	int	CITY_CHANGE_CONFIRM_DIALOG		= 105;
	int	REDEEM_COUPON_DIALOG			= 106;
	int	APPLY_COUPON_CODE_DIALOG		= 107;
	int	REMOVE_COUPON_CODE				= 108;
	int	CLEAR_CART						= 109;
	int	CONFIRM_REMOVE_STORED_CARD		= 110;
	int	CONFIRM_REMOVE_PROFILE_PIC		= 111;
	int	ALERT_MESSAGE_FROM_CART_APIS	= 112;
	int	ALERT_SERVER_MSG_TO_REVIEW_CART	= 113;
}