package com.farru.android.utill;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;

import com.digitalforce.datingapp.BuildConfig;
import com.digitalforce.datingapp.R;


public class ShareUtils {

	// Constants
	private static final String	LOG_TAG					= "ShareUtils";
	private static final String	ACTION_SMS_SENT			= "sms_utils_action_sms_sent";
	private static final String	ACTION_SMS_DELIVERED	= "sms_utils_action_sms_delivered";

	/**
	 * @param context
	 * @param text
	 */
	public static void sharePlainText(Context context, String chooserTitle, String subject, String text) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		if (StringUtils.isNullOrEmpty(subject)) {
			context.startActivity(shareIntent);
		} else {
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		}
		if (StringUtils.isNullOrEmpty(chooserTitle)) {
			context.startActivity(shareIntent);
		} else {
			context.startActivity(Intent.createChooser(shareIntent, chooserTitle));
		}
	}

	/**
	 * @param context
	 * @param email
	 * @param subject
	 * @param text
	 */
	public static void email(Context context, String email, String subject, String text) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { email });
		i.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		i.putExtra(android.content.Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(i, "Send email"));
	}

	/**
	 * @param pContext
	 * @param pText
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void copyToClipboard(Context pContext, String pText) {
		ClipboardManager clipboardManager = (ClipboardManager) pContext.getSystemService(Context.CLIPBOARD_SERVICE);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			clipboardManager.setText(pText);
		} else {
			clipboardManager.setPrimaryClip(ClipData.newPlainText(pContext.getString(R.string.app_name), pText));
		}
	}

	/**
	 * @param pContext
	 * @param pReceiverNo
	 * @param pMessage
	 */
	public static void sendSms(Context pContext, String pReceiverNo, String pMessage, final SmsEventListener pSmsEventListener) {

		// SMS sent receiver
		pContext.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (BuildConfig.DEBUG) {
					Log.d(LOG_TAG, "SMS sent intent received.");
				}
				if (pSmsEventListener != null) {
					pSmsEventListener.onSmsEvent(SmsEvent.SMS_SENT, intent);
				}
			}
		}, new IntentFilter(ACTION_SMS_SENT));

		// SMS delivered receiver
		pContext.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (BuildConfig.DEBUG) {
					Log.d(LOG_TAG, "SMS delivered intent received.");
				}
				if (pSmsEventListener != null) {
					pSmsEventListener.onSmsEvent(SmsEvent.SMS_DELIVERED, intent);
				}
			}
		}, new IntentFilter(ACTION_SMS_DELIVERED));

		// SMS sent pending intent
		PendingIntent sentIntent = PendingIntent.getBroadcast(pContext, 0, new Intent(ACTION_SMS_SENT), 0);

		// SMS delivered pending intent
		PendingIntent deliveredIntent = PendingIntent.getBroadcast(pContext, 0, new Intent(ACTION_SMS_DELIVERED), 0);

		// Send the SMS message
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(pReceiverNo, null, pMessage, sentIntent, deliveredIntent);
	}

	public static enum SmsEvent {
		SMS_SENT, SMS_DELIVERED
	}

	public static interface SmsEventListener {
		void onSmsEvent(SmsEvent pSmsEvent, Intent pEventData);
	}
}
