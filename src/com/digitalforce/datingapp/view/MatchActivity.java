package com.digitalforce.datingapp.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.MatchAdapter;
import com.digitalforce.datingapp.model.NearBy;
import com.farru.android.network.ServiceResponse;

public class MatchActivity extends BaseActivity{

	private TextView mtxtTitle;
	private GridView mgridVeiw;
	private MatchAdapter adapter;
	private ArrayList<NearBy> mlistNearby;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_match);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		
		mtxtTitle.setText(getResources().getString(R.string.match));
		
		mlistNearby = new ArrayList<NearBy>();
		for(int i = 0; i<=22; i++)
		{
			NearBy nearBy = new NearBy();
			nearBy.setFirstName("KMD");
			nearBy.setDistance("Noida");
			mlistNearby.add(nearBy);
		}
		
		
		mgridVeiw = (GridView) findViewById(R.id.grid_view_match);
		adapter = new MatchAdapter(this, mlistNearby);
		mgridVeiw.setAdapter(adapter);
		
		mgridVeiw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
                Intent i = new Intent(MatchActivity.this, ProfileActivity.class);
                startActivity(i);
			}
        });
		
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
