package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateProfileActivity extends Activity{

	private ImageView mimgback, mimgMenu;
	private TextView mtxtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_update_profile);
		
		mimgback = (ImageView) findViewById(R.id.img_action_back);
		mimgMenu = (ImageView) findViewById(R.id.img_action_menu);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		
		mimgback.setVisibility(View.INVISIBLE);
		mimgMenu.setVisibility(View.INVISIBLE);
		mtxtTitle.setText(getResources().getString(R.string.profile));
	}
}
