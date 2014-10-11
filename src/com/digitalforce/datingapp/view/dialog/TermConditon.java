package com.digitalforce.datingapp.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.view.LoginActivity;
import com.farru.android.ui.activity.BaseActivity;

public class TermConditon extends Dialog implements android.view.View.OnClickListener{

	private Button mbtnOk, mbtnCancel;
	private Context context;
	
	public TermConditon(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setCanceledOnTouchOutside(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_term_condition);
		
		mbtnCancel = (Button) findViewById(R.id.btn_dialog_tc_cancel);
		mbtnOk = (Button) findViewById(R.id.btn_dialog_tc_ok);
		
		mbtnCancel.setOnClickListener(this);
		mbtnOk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_dialog_tc_cancel:
			dismiss();
			((BaseActivity) context).finish();
			break;
		case R.id.btn_dialog_tc_ok:
			Intent i = new Intent(context, LoginActivity.class);
			context.startActivity(i);
			dismiss();
			((BaseActivity) context).finish();
			break;

		default:
			break;
		}
	}
}
