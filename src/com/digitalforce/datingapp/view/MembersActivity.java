package com.digitalforce.datingapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.fragments.BaseFragment;
import com.digitalforce.datingapp.fragments.ExploreFragment;
import com.digitalforce.datingapp.fragments.NearByFragment;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.network.ServiceResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MembersActivity extends BaseActivity implements OnClickListener{

	private ImageView mimgMenuOption;
	private TextView mtxtTitle, mtxtNearBy, mtxtExplore;
	private EditText medtSearch;
	
	private GoogleMap googleMap;
	
	private String EXPLORER_TAG = "explorer";
	private String NEAR_BY_TAG = "near_by_tag";

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

		mimgMenuOption.setOnClickListener(this);
		mtxtNearBy.setOnClickListener(this);
		mtxtExplore.setOnClickListener(this);

		selectFragment(new NearByFragment());
		mtxtTitle.setText(getResources().getString(R.string.member));
		
		medtSearch.setVisibility(View.GONE);

        medtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
			
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		//initilizeMap();
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
			}else if(fragmentmaneger.getFragments().get(i) instanceof ExploreFragment){
                ExploreFragment fragment = (ExploreFragment)fragmentmaneger.getFragments().get(i);
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
	
	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {

			
			FragmentManager fragmentmaneger = getSupportFragmentManager();
			if(fragmentmaneger!=null && fragmentmaneger.getFragments()!=null){
				for(int i=0; i<fragmentmaneger.getFragments().size(); i++){
					if(fragmentmaneger.getFragments().get(i) instanceof SupportMapFragment){
						SupportMapFragment fragment = (SupportMapFragment)fragmentmaneger.getFragments().get(i);
						googleMap = fragment.getMap();
					}
					
				}
			}
		  
			
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
			
			if(googleMap!=null){
				setUpMap();
			}
		
	}
	
	private void setUpMap() {
		// Hide the zoom controls as the button panel will cover it.
		googleMap.getUiSettings().setZoomControlsEnabled(false); 

		float latitude = Float.parseFloat(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", this));
		float longitude = Float.parseFloat(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", this));

		final LatLng USER_CURRENT_LOCATION = new LatLng(latitude,longitude);

		Marker hamburg = googleMap.addMarker(new MarkerOptions().position(USER_CURRENT_LOCATION)
				.title("Dating App"));

		// Move the camera instantly to hamburg with a zoom of 15.
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_CURRENT_LOCATION, 15));

		// Zoom in, animating the camera.
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

	private boolean checkReady() {
		if (googleMap == null) {
			Toast.makeText(this, "Map is not ready", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}


    private void performSearch(){

    }
	
	/*@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}*/
	
	

}
