package com.digitalforce.datingapp.utils;




import com.digitalforce.datingapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastCustom {
	
	public static void makeText(Activity activity, String text, int time)
	{
		@SuppressWarnings("static-access")
		LayoutInflater inflater=(LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
		
		View convertView = inflater.inflate(R.layout.layout_toast, null);

		TextView txt_toast_msg = (TextView)convertView.findViewById(R.id.txt_toast_msg);

		txt_toast_msg.setText(text);
		
		   Toast toast = new Toast(activity);
		   toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 120);
		   toast.setDuration(time);
		   toast.setView(convertView);
		   toast.show();
	}
	public static void underDevelopment(Context activity)
	{
		makeText(activity, activity.getResources().getString(R.string.under_development), Toast.LENGTH_SHORT);
	}

	
	public static void makeText(Context activity, String text, int time)
	{
		@SuppressWarnings("static-access")
		LayoutInflater inflater=(LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
		
		View convertView = inflater.inflate(R.layout.layout_toast, null);

		TextView txt_toast_msg = (TextView)convertView.findViewById(R.id.txt_toast_msg);

		txt_toast_msg.setText(text);
		
		   Toast toast = new Toast(activity);
		   toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 120);
		   toast.setDuration(time);
		   toast.setView(convertView);
		   toast.show();
	}

}
