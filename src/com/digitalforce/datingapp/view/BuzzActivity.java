package com.digitalforce.datingapp.view;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.fragments.BaseFragment;
import com.digitalforce.datingapp.fragments.NearByFragment;
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

public class BuzzActivity extends BaseActivity implements OnClickListener{

	private ImageView mimgMenuOption;
	private TextView mtxtTitle, mtxtNearBy, mtxtExplore;
	private EditText medtSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_member);
		
		mimgMenuOption = (ImageView) findViewById(R.id.img_action_menu);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mtxtNearBy = (TextView) findViewById(R.id.txt_member_nearby);
		mtxtExplore = (TextView) findViewById(R.id.txt_member_explore);
		medtSearch = (EditText) findViewById(R.id.edt_member_search);

		//manage text a/c to screen
		mtxtNearBy.setText(getResources().getString(R.string.new_members));
		mtxtExplore.setText(getResources().getString(R.string.whos_viewed_u));
		mtxtTitle.setText(getResources().getString(R.string.buzz));
		medtSearch.setVisibility(View.GONE);
		mtxtNearBy.setBackgroundResource(R.drawable.left_corner_round);
		mtxtExplore.setBackgroundResource(Color.TRANSPARENT);
		mtxtExplore.setTextColor(Color.WHITE);
		mtxtNearBy.setTextColor(Color.BLACK);
		selectFragment(new NearByFragment());
		
		
		mimgMenuOption.setOnClickListener(this);
		mtxtNearBy.setOnClickListener(this);
		mtxtExplore.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
			selectFragment(new NearByFragment());
			mtxtExplore.setBackgroundResource(R.drawable.right_corner_round);
			mtxtNearBy.setBackgroundResource(Color.TRANSPARENT);
			medtSearch.setVisibility(View.GONE);
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
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);
		
		
		if(serviceResponse!=null){
			switch (serviceResponse.getErrorCode()) {
			case ServiceResponse.SUCCESS:
				break;
			case ServiceResponse.MESSAGE_ERROR:
				showCommonError(serviceResponse.getErrorMessages());
				break;
			default:
				showCommonError(null);
				break;
			}
		}else{
			showCommonError(null);
		}
		
		
		FragmentManager fragmentmaneger = getSupportFragmentManager();
		for(int i=0; i<fragmentmaneger.getFragments().size(); i++){
			if(fragmentmaneger.getFragments().get(i) instanceof BaseFragment){
				BaseFragment fragment = (BaseFragment)fragmentmaneger.getFragments().get(i);
				fragment.updateUi(serviceResponse);
			}
			
		}

		
		
		
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
