/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.digitalforce.datingapp.R;

/**
 * @author FARHAN
 *
 */
public class FilterActivity extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);

		findViewById(R.id.min_age_layout).setOnClickListener(this);
		findViewById(R.id.max_age_layout).setOnClickListener(this);
		findViewById(R.id.ethnicity_layout).setOnClickListener(this);
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

		default:
			break;
		}

	}
	
	
	private void showSingleSelectionDialog(final int resId,final String[] items,String title){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			
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
	
	

}
