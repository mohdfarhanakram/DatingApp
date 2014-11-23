/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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


    private String sex_roles[] = {"Top Versatile","Top (Vers Top)","Bottom(Vers Bottom)"};

    private String looking_for[] = {"Sex","Relationship","Long Term Relationship","Dating","Fun","Flirt","Friends","Network","Sugar Daddy"};

    private String body_types[] = {"Slim","Average","Athletic","Heavy"};

    private String relation_ship_status[] = {"Single","Married"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);
		
		setData();

		findViewById(R.id.min_age_layout).setOnClickListener(this);
		findViewById(R.id.max_age_layout).setOnClickListener(this);
		findViewById(R.id.ethnicity_layout).setOnClickListener(this);

        findViewById(R.id.min_weight_layout).setOnClickListener(this);
        findViewById(R.id.max_weight_layout).setOnClickListener(this);

        findViewById(R.id.min_height_layout).setOnClickListener(this);
        findViewById(R.id.max_height_layout).setOnClickListener(this);

        findViewById(R.id.body_type_layout).setOnClickListener(this);
        findViewById(R.id.looking_for_layout).setOnClickListener(this);
        findViewById(R.id.relationship_status_layout).setOnClickListener(this);
        findViewById(R.id.sexual_role_layout).setOnClickListener(this);

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
            case R.id.min_height_layout:
                showSingleSelectionDialog(R.id.txtv_min_height,getHeightArray(),"Min Height");
                break;
            case R.id.max_height_layout:
                showSingleSelectionDialog(R.id.txtv_max_height,getHeightArray(),"Max Height");
                break;
            case R.id.min_weight_layout:
                showSingleSelectionDialog(R.id.txtv_min_weight,getWeightArray(),"Min Weight");
                break;
            case R.id.max_weight_layout:
                showSingleSelectionDialog(R.id.txtv_max_weight,getWeightArray(),"Max Weight");
                break;
            case R.id.body_type_layout:
                showSingleSelectionDialog(R.id.txtv_body_type,body_types,"Body Type");
                break;
            case R.id.sexual_role_layout:
                showSingleSelectionDialog(R.id.txtv_sexual_role,sex_roles,"Sexual Role");
                break;
            case R.id.relationship_status_layout:
                showSingleSelectionDialog(R.id.txtv_relationship,relation_ship_status,"Relationship Status");
                break;
            case R.id.looking_for_layout:
                showSingleSelectionDialog(R.id.txtv_looking_for,looking_for,"Looking For");
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

    /**
     *
     * @return
     */
    private String[] getWeightArray()
    {
        String[] weight = new String[2202];

        for(int i=0; i<2202; i++){
            weight[i] = (i+99)+" "+"lbs";
        }

        return weight;
    }
    /**
     *
     * @return
     */
    private String[] getHeightArray()
    {
        String[] height = new String[84];

        int index = 0;

        for(int i=0; i<7; i++)
        {
            for(int j=0; j<=11;j++)
            {

                height[index] = (i+3)+" ft "+j+" in";
                Log.e("KMD", "i=" + i + " j=" + j);
                index++;
            }
        }

        return height;
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

        String minHeight = ((TextView)findViewById(R.id.txtv_min_height)).getText().toString();
        String maxHeight = ((TextView)findViewById(R.id.txtv_max_height)).getText().toString();
        String minWeight = ((TextView)findViewById(R.id.txtv_min_weight)).getText().toString();
        String maxWeight = ((TextView)findViewById(R.id.txtv_max_weight)).getText().toString();
        String sexRole = ((TextView)findViewById(R.id.txtv_sexual_role)).getText().toString();
        String relationShip = ((TextView)findViewById(R.id.txtv_relationship)).getText().toString();
        String bodyType = ((TextView)findViewById(R.id.txtv_body_type)).getText().toString();
        String lookingFor = ((TextView)findViewById(R.id.txtv_looking_for)).getText().toString();


		DatingAppPreference.putString(DatingAppPreference.MIN_AGE, minAge, this);
		DatingAppPreference.putString(DatingAppPreference.MAX_AGE, maxAge, this);
		DatingAppPreference.putString(DatingAppPreference.ETHNICITY, ethinicity, this);

        DatingAppPreference.putString(DatingAppPreference.MIN_HEIGHT, minHeight, this);
        DatingAppPreference.putString(DatingAppPreference.MAX_HEIGHT, maxHeight, this);
        DatingAppPreference.putString(DatingAppPreference.MIN_WEIGHT, minWeight, this);
        DatingAppPreference.putString(DatingAppPreference.MAX_WEIGHT, maxWeight, this);
        DatingAppPreference.putString(DatingAppPreference.BODY_TYPE, bodyType, this);
        DatingAppPreference.putString(DatingAppPreference.SEX_ROLE, sexRole, this);
        DatingAppPreference.putString(DatingAppPreference.RELATION_SHIP, relationShip, this);
        DatingAppPreference.putString(DatingAppPreference.LOOKING_FOR, lookingFor, this);

		
		ToastCustom.makeText(this, "Filter data is saved successfully.", 3000);
		
		finish();
		
		
	}


	private void setData(){

		String minAge = DatingAppPreference.getString(DatingAppPreference.MIN_AGE, "18", this);
		String maxAge = DatingAppPreference.getString(DatingAppPreference.MAX_AGE, "100", this);
		String ethinicity = DatingAppPreference.getString(DatingAppPreference.ETHNICITY, "Asian", this);

        String minHeight = DatingAppPreference.getString(DatingAppPreference.MIN_HEIGHT, "3 ft 0 in", this);
        String maxHeight = DatingAppPreference.getString(DatingAppPreference.MAX_HEIGHT, "9 ft 11 in", this);
        String minWeight = DatingAppPreference.getString(DatingAppPreference.MIN_WEIGHT, "99 lbs", this);
        String maxWeight = DatingAppPreference.getString(DatingAppPreference.MAX_WEIGHT, "2300 lbs", this);
        String sexRole = DatingAppPreference.getString(DatingAppPreference.SEX_ROLE, "Top Versatile", this);
        String relationShip = DatingAppPreference.getString(DatingAppPreference.RELATION_SHIP, "", this);
        String bodyType = DatingAppPreference.getString(DatingAppPreference.BODY_TYPE, "", this);
        String lookingFor = DatingAppPreference.getString(DatingAppPreference.LOOKING_FOR, "", this);

        ((TextView)findViewById(R.id.txtv_min_age)).setText(minAge);
        ((TextView)findViewById(R.id.txtv_max_age)).setText(maxAge);
        ((TextView)findViewById(R.id.txtv_ethnicity)).setText(ethinicity);

        ((TextView)findViewById(R.id.txtv_min_height)).setText(minHeight);
        ((TextView)findViewById(R.id.txtv_max_height)).setText(maxHeight);
        ((TextView)findViewById(R.id.txtv_min_weight)).setText(minWeight);
        ((TextView)findViewById(R.id.txtv_max_weight)).setText(maxWeight);
        ((TextView)findViewById(R.id.txtv_sexual_role)).setText(sexRole);
        ((TextView)findViewById(R.id.txtv_relationship)).setText(relationShip);
        ((TextView)findViewById(R.id.txtv_body_type)).setText(bodyType);
        ((TextView)findViewById(R.id.txtv_looking_for)).setText(lookingFor);
		

	}



}
