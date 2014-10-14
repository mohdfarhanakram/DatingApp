package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.MatchAdapter;
import com.farru.android.network.ServiceResponse;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MatchActivity extends BaseActivity{
	
	private ListView mlist;
	private ImageView mimgMenuOption;
	private TextView mtxtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_match_chat);
		
		mimgMenuOption = (ImageView) findViewById(R.id.img_action_menu);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mlist = (ListView) findViewById(R.id.list_match);
		
		mtxtTitle.setText(getResources().getString(R.string.chat));
		
		MatchAdapter matchAdapter = new MatchAdapter(this);
		mlist.setAdapter(matchAdapter);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateUi(ServiceResponse serviceResponse) {
		// TODO Auto-generated method stub
		
	}

}
