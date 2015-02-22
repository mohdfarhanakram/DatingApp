/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.ui.widget.CustomAlert;
import de.ankri.views.Switch;

/**
 * @author FARHAN
 *
 */
public class FilterActivity extends BaseActivity implements OnClickListener{


    private String sex_roles[] = {"All","Top","Bottom"," Versatile","Vers Top","Vers Bottom"};

    private String looking_for[] = {"All","Chat","Dates","Friends","Networking","Relationship","Right Now","Trans","Bear","Daddy","Son"};

    private String body_types[] = {"All","Toned","Average","Large","Muscular","Slim","Stocky"};

    private String relation_ship_status[] = {"All","Single","Married"};

    private Switch mSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppBaseTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);

        ((TextView) findViewById(R.id.txt_screen_title)).setText("Filter");

        mSwitch = (Switch)findViewById(R.id.btn_enable_disable);
		
		setData();

		findViewById(R.id.edtv_min_age).setOnClickListener(this);
		findViewById(R.id.edtv_max_age).setOnClickListener(this);
		findViewById(R.id.edtv_ethnicity).setOnClickListener(this);

        findViewById(R.id.edtv_min_weight).setOnClickListener(this);
        findViewById(R.id.edtv_max_weight).setOnClickListener(this);

        findViewById(R.id.edtv_min_height).setOnClickListener(this);
        findViewById(R.id.edtv_max_height).setOnClickListener(this);

        findViewById(R.id.edtv_body_type).setOnClickListener(this);
        findViewById(R.id.edtv_looking_for).setOnClickListener(this);
        findViewById(R.id.edtv_relationship).setOnClickListener(this);
        findViewById(R.id.edtv_sexual_role).setOnClickListener(this);

        findViewById(R.id.btn_save_filter).setOnClickListener(this);

	}

	@Override
	public void onEvent(int eventId, Object eventData) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edtv_min_age:
			showSingleSelectionDialog(R.id.edtv_min_age,getAgeArray(),"Select Min Age");
			break;
		case R.id.edtv_max_age:
			showSingleSelectionDialog(R.id.edtv_max_age,getAgeArray(),"Select Max Age");
			break;
		case R.id.edtv_ethnicity:
			showSingleSelectionDialog(R.id.edtv_ethnicity,getEthnicity(),"Select Ethnicity");
			break;
            case R.id.edtv_min_height:
                showSingleSelectionDialog(R.id.edtv_min_height,getHeightArray(),"Min Height");
                break;
            case R.id.edtv_max_height:
                showSingleSelectionDialog(R.id.edtv_max_height,getHeightArray(),"Max Height");
                break;
            case R.id.edtv_min_weight:
                showSingleSelectionDialog(R.id.edtv_min_weight,getWeightArray(),"Min Weight");
                break;
            case R.id.edtv_max_weight:
                showSingleSelectionDialog(R.id.edtv_max_weight,getWeightArray(),"Max Weight");
                break;
            case R.id.edtv_body_type:
                showSingleSelectionDialog(R.id.edtv_body_type,body_types,"Body Type");
                break;
            case R.id.edtv_sexual_role:
                showSingleSelectionDialog(R.id.edtv_sexual_role,sex_roles,"Sexual Role");
                break;
            case R.id.edtv_relationship:
                showSingleSelectionDialog(R.id.edtv_relationship,relation_ship_status,"Relationship Status");
                break;
            case R.id.edtv_looking_for:
                showSingleSelectionDialog(R.id.edtv_looking_for,looking_for,"Looking For");
                break;
		case R.id.btn_save_filter:
			
			saveFilterData();
		default:
			break;
		}

	}


	private void showSingleSelectionDialog(final int resId,final String[] items,String title){

        if(!mSwitch.isChecked()){
            return;
        }

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View titleView = LayoutInflater.from(this).inflate(R.layout.dialog_title_view,null);
        ((TextView)titleView.findViewById(R.id.dialo_title)).setText(title);
        builder.setCustomTitle(titleView);
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

    private String[] getAgeArray()
    {
        String[] age = new String[82];

        for(int i=0; i<82; i++){
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
        String[] weight = new String[166];

        for(int i=0; i<166; i++){
            weight[i] = (i+99)+" "+"lbs " + "("+getConvertKg((i+99))+" kg)";
        }

        return weight;
    }

    private int getConvertKg(int lbs){
        return (int)(lbs / 2.20462262185);
    }
    /**
     *
     * @return
     */
    private String[] getHeightArray()
    {
        String[] height = new String[48];

        int index = 0;

        for(int i=0; i<4; i++)
        {
            for(int j=0; j<=11;j++)
            {
                height[index] = (i+4)+"'"+j+"\"" +" ("+convertInCM((i+4),j)+" cm)";
                Log.e("KMD", "i=" + i + " j=" + j);
                index++;

               /* height[index] = (i+4)+" ft "+j+" in";
                Log.e("KMD", "i=" + i + " j=" + j);
                index++;*/
            }
        }

        return height;
    }

    private int convertInCM(int ft,int cm){
        int a = (12*ft)+ cm;
        return (int)(a * 2.54);
    }

	private String[] getEthnicity(){

		String[] ethnicity = {"All","Asian","Black","Latino","Mixed","White","Other"};

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

		String minAge = ((EditText)findViewById(R.id.edtv_min_age)).getText().toString();
		String maxAge = ((EditText)findViewById(R.id.edtv_max_age)).getText().toString();
		String ethinicity = ((EditText)findViewById(R.id.edtv_ethnicity)).getText().toString();

        String minHeight = ((EditText)findViewById(R.id.edtv_min_height)).getText().toString();
        String maxHeight = ((EditText)findViewById(R.id.edtv_max_height)).getText().toString();
        String minWeight = ((EditText)findViewById(R.id.edtv_min_weight)).getText().toString();
        String maxWeight = ((EditText)findViewById(R.id.edtv_max_weight)).getText().toString();
        String sexRole = ((EditText)findViewById(R.id.edtv_sexual_role)).getText().toString();
        String relationShip = ((EditText)findViewById(R.id.edtv_relationship)).getText().toString();
        String bodyType = ((EditText)findViewById(R.id.edtv_body_type)).getText().toString();
        String lookingFor = ((EditText)findViewById(R.id.edtv_looking_for)).getText().toString();


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

        DatingAppPreference.putBoolean(DatingAppPreference.FILTER_ENABLE, mSwitch.isChecked(), this);

		
		ToastCustom.makeText(this, "Filter data is saved successfully.", 3000);
		
		finish();
		
		
	}


	private void setData(){

		String minAge = DatingAppPreference.getString(DatingAppPreference.MIN_AGE, "18", this);
		String maxAge = DatingAppPreference.getString(DatingAppPreference.MAX_AGE, "99", this);
		String ethinicity = DatingAppPreference.getString(DatingAppPreference.ETHNICITY, "All", this);

        String minHeight = DatingAppPreference.getString(DatingAppPreference.MIN_HEIGHT, "4'0\""+"(121 cm)", this);
        String maxHeight = DatingAppPreference.getString(DatingAppPreference.MAX_HEIGHT, "7'11\""+"(241 cm)", this);
        String minWeight = DatingAppPreference.getString(DatingAppPreference.MIN_WEIGHT, "99 lbs (44 kg)", this);
        String maxWeight = DatingAppPreference.getString(DatingAppPreference.MAX_WEIGHT, "264 lbs (119 kg)", this);
        String sexRole = DatingAppPreference.getString(DatingAppPreference.SEX_ROLE, "All", this);
        String relationShip = DatingAppPreference.getString(DatingAppPreference.RELATION_SHIP, "", this);
        String bodyType = DatingAppPreference.getString(DatingAppPreference.BODY_TYPE, "", this);
        String lookingFor = DatingAppPreference.getString(DatingAppPreference.LOOKING_FOR, "", this);
        boolean isEnable = DatingAppPreference.getBoolean(DatingAppPreference.FILTER_ENABLE, true, this);

        ((EditText)findViewById(R.id.edtv_min_age)).setText(minAge);
        ((EditText)findViewById(R.id.edtv_max_age)).setText(maxAge);
        ((EditText)findViewById(R.id.edtv_ethnicity)).setText(ethinicity);

        ((EditText)findViewById(R.id.edtv_min_height)).setText(minHeight);
        ((EditText)findViewById(R.id.edtv_max_height)).setText(maxHeight);
        ((EditText)findViewById(R.id.edtv_min_weight)).setText(minWeight);
        ((EditText)findViewById(R.id.edtv_max_weight)).setText(maxWeight);
        ((EditText)findViewById(R.id.edtv_sexual_role)).setText(sexRole);
        ((EditText)findViewById(R.id.edtv_relationship)).setText(relationShip);
        ((EditText)findViewById(R.id.edtv_body_type)).setText(bodyType);
        ((EditText)findViewById(R.id.edtv_looking_for)).setText(lookingFor);
        mSwitch.setChecked(isEnable);

	}



}
