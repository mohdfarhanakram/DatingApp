/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.fragments.PrivatePhotoFragment;
import com.digitalforce.datingapp.fragments.PublicPhotoFragment;
import com.farru.android.network.ServiceResponse;

/**
 * @author FARHAN
 *
 */
public class MyPictureActivity extends BaseActivity implements OnClickListener{

	private TextView mtxtTitle, mtxtPublicPic, mtxtPrivatePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_picture);

		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mtxtPublicPic = (TextView) findViewById(R.id.txt_public_photo);
		mtxtPrivatePic = (TextView) findViewById(R.id.txt_private_photo);

		mtxtPublicPic.setOnClickListener(this);
		mtxtPrivatePic.setOnClickListener(this);

		selectFragment(new PublicPhotoFragment());
		mtxtTitle.setText(getResources().getString(R.string.mypicyure));
	}


	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		super.updateUi(serviceResponse);
	}
	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_public_photo:
			selectFragment(new PublicPhotoFragment());
			mtxtPublicPic.setBackgroundResource(R.drawable.left_corner_round);
			mtxtPrivatePic.setBackgroundResource(Color.TRANSPARENT);
			mtxtPrivatePic.setTextColor(Color.WHITE);
			mtxtPublicPic.setTextColor(Color.BLACK);
			break;
		case R.id.txt_private_photo:
			selectFragment(new PrivatePhotoFragment());
			mtxtPrivatePic.setBackgroundResource(R.drawable.right_corner_round);
			mtxtPublicPic.setBackgroundResource(Color.TRANSPARENT);
			mtxtPrivatePic.setTextColor(Color.BLACK);
			mtxtPublicPic.setTextColor(Color.WHITE);
			break;
		default:
			break;
		}

	}

	private void selectFragment(Fragment fragment)
	{
		FragmentManager fragmentmaneger = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentmaneger.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_picture, fragment);
		fragmentTransaction.commit();

	}

}
