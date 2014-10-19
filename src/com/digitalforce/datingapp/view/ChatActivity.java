package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.ChatAdapter;
import com.farru.android.network.ServiceResponse;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends BaseActivity{
	
	private ListView mlist;
	private ImageView mimgMenuOption;
	private TextView mtxtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chat);
		
		mimgMenuOption = (ImageView) findViewById(R.id.img_action_menu);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mlist = (ListView) findViewById(R.id.list_match);
		
		mtxtTitle.setText(getResources().getString(R.string.chat));
		
		ChatAdapter matchAdapter = new ChatAdapter(this);
		mlist.setAdapter(matchAdapter);
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);
		
	}

}
