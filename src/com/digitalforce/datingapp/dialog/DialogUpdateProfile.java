package com.digitalforce.datingapp.dialog;

import com.digitalforce.datingapp.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogUpdateProfile extends Dialog implements android.view.View.OnClickListener{

	private Button mbtnOk, mbtnCancel;
	private Context context;
	private TextView mtxtTitle;
	private EditText medtData,edt;
	private String titleData, setdata;
	
	public DialogUpdateProfile(Context context,EditText edt,String titleData,String setdata) {
		super(context);
		this.context=context;
		this.edt = edt;
		this.titleData = titleData;
		this.setdata = setdata;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setCanceledOnTouchOutside(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_dialog_update_profile);
		
		mtxtTitle = (TextView) findViewById(R.id.txt_dialog_update_profile_title);
		medtData = (EditText) findViewById(R.id.edt_dailog_update_profile_);
		mbtnOk = (Button) findViewById(R.id.btn_dialog_update_profile_ok);
		mbtnCancel = (Button) findViewById(R.id.btn_dialog_update_profile_cancel);
		mtxtTitle.setText(titleData);
		medtData.setText(setdata);
		
		mbtnCancel.setOnClickListener(this);
		mbtnOk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_dialog_update_profile_ok:
			edt.setText(medtData.getText().toString());
			dismiss();
			break;
		case R.id.btn_dialog_update_profile_cancel:
			dismiss();
			break;

		default:
			break;
		}
	}

}
