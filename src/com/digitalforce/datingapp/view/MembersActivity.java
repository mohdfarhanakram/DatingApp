package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.Fragments.ExploreFragment;
import com.digitalforce.datingapp.Fragments.NearByFragment;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.network.ServiceResponse;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MembersActivity extends BaseActivity implements OnClickListener{

	private ImageView mimgMenuOption;
	private TextView mimgTitle, mtxtNearBy, mtxtExplore;
	private EditText medtSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_member);

		mimgMenuOption = (ImageView) findViewById(R.id.img_action_menu);
		mimgTitle = (TextView) findViewById(R.id.txt_screen_title);
		mtxtNearBy = (TextView) findViewById(R.id.txt_member_nearby);
		mtxtExplore = (TextView) findViewById(R.id.txt_member_explore);
		medtSearch = (EditText) findViewById(R.id.edt_member_search);

		mimgMenuOption.setOnClickListener(this);
		mtxtNearBy.setOnClickListener(this);
		mtxtExplore.setOnClickListener(this);

		selectFragment(new ExploreFragment());
		mimgTitle.setText(getResources().getString(R.string.member));
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_action_menu:
			ToastCustom.underDevelopment(this);
			break;
		case R.id.txt_member_nearby:
			selectFragment(new NearByFragment());
			mtxtNearBy.setBackgroundResource(R.drawable.left_corner_round);
			mtxtExplore.setBackgroundResource(Color.TRANSPARENT);
			medtSearch.setVisibility(View.GONE);
			mtxtExplore.setTextColor(Color.WHITE);
			mtxtNearBy.setTextColor(Color.BLACK);
			
			break;
		case R.id.txt_member_explore:
			selectFragment(new ExploreFragment());
			mtxtExplore.setBackgroundResource(R.drawable.right_corner_round);
			mtxtNearBy.setBackgroundResource(Color.TRANSPARENT);
			medtSearch.setVisibility(View.VISIBLE);
			mtxtExplore.setTextColor(Color.BLACK);
			mtxtNearBy.setTextColor(Color.WHITE);
			break;

		default:
			break;
		}
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateUi(ServiceResponse serviceResponse) {
		// TODO Auto-generated method stub

	}
	/**
	 * call fragment
	 * @param fragment
	 */
	private void selectFragment(Fragment fragment)
	{
		FragmentManager fragmentmaneger = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentmaneger.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_member, fragment);
		fragmentTransaction.commit();
	}

}
