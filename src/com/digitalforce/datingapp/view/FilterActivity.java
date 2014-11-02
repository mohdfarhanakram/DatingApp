/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.ui.widget.CustomAlert;

/**
 * @author FARHAN
 *
 */
public class FilterActivity extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);
		
		setData();

		findViewById(R.id.min_age_layout).setOnClickListener(this);
		findViewById(R.id.max_age_layout).setOnClickListener(this);
		findViewById(R.id.ethnicity_layout).setOnClickListener(this);
		findViewById(R.id.btn_save_filter).setOnClickListener(this);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.min_age_layout:
			showSingleSelectionDialog(R.id.txtv_min_age,getAgeArray(),"Select Min Age");
			break;
		case R.id.max_age_layout:
			showSingleSelectionDialog(R.id.txtv_max_age,getAgeArray(),"Select Max Age");
			break;
		case R.id.ethnicity_layout:
			showSingleSelectionDialog(R.id.txtv_ethnicity,getEthnicity(),"Select Ethnicity");
			break;
		case R.id.btn_save_filter:
			
			saveFilterData();
		default:
			break;
		}

	}


	private void showSingleSelectionDialog(final int resId,final String[] items,String title){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		String selectedString = "";
		TextView t = (TextView)findViewById(resId);
		if(t!=null){
			selectedString = t.getText().toString();
		}
		int selectedIndex = getSelectedIndex(selectedString, items);
		builder.setSingleChoiceItems(items, selectedIndex, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				TextView t = (TextView)findViewById(resId);
				if(t!=null){
					t.setText(items[which]);
					dialog.dismiss();
				}
			}
		});
		//builder.setPositiveButton("OK",this);
		//builder.setNegativeButton("Cancel", this);
		builder.create().show();
	}

	private String[] getAgeArray(){
		String[] age = new String[83];

		for(int i=0; i<83; i++){
			age[i] = (i+18)+"";
		}

		return age;
	}


	private String[] getEthnicity(){

		String[] ethnicity = {"Asian","Black","Latino","Mixed","White","Other"};

		return ethnicity;
	}

	private int getSelectedIndex(String selectedString,String[] options){

		int selectedIndex = -1;

		for(int i=0; i<options.length; i++){
			if(options[i].equals(selectedString)){
				selectedIndex = i;
			}
		}

		return selectedIndex;
	}


	private void saveFilterData(){

		String minAge = ((TextView)findViewById(R.id.txtv_min_age)).getText().toString();
		String maxAge = ((TextView)findViewById(R.id.txtv_max_age)).getText().toString();
		String ethinicity = ((TextView)findViewById(R.id.txtv_ethnicity)).getText().toString();

		DatingAppPreference.putString(DatingAppPreference.MIN_AGE, minAge, this);
		DatingAppPreference.putString(DatingAppPreference.MAX_AGE, maxAge, this);
		DatingAppPreference.putString(DatingAppPreference.SEX_ROLE, ethinicity, this);
		
		ToastCustom.makeText(this, "Filter data is saved successfully.", 3000);
		
		finish();
		
		
	}


	private void setData(){

		String minAge = DatingAppPreference.getString(DatingAppPreference.MIN_AGE, "18", this);
		String maxAge = DatingAppPreference.getString(DatingAppPreference.MAX_AGE, "100", this);
		String ethinicity = DatingAppPreference.getString(DatingAppPreference.SEX_ROLE, "top", this);
		
		((TextView)findViewById(R.id.txtv_min_age)).setText(minAge);
		((TextView)findViewById(R.id.txtv_max_age)).setText(maxAge);
		((TextView)findViewById(R.id.txtv_ethnicity)).setText(ethinicity);

	}



}
