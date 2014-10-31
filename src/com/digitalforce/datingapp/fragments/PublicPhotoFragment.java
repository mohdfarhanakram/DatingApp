/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.MyPictureAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.view.BaseActivity;
import com.digitalforce.datingapp.view.MyPictureActivity;
import com.digitalforce.datingapp.view.PhotoDetailActivity;
import com.farru.android.network.ServiceResponse;
import com.farru.android.utill.StringUtils;

/**
 * @author FARHAN
 *
 */
public class PublicPhotoFragment extends BaseFragment{
	private GridView mGridView;
	private View mView;
	
	ArrayList<String> mPictureList = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.layout_fragment_public_photo, container, false);
		
		mGridView = (GridView) mView.findViewById(R.id.grid_view_picture);
		postData(DatingUrlConstants.SHOW_MY_PICTURE_URL, ApiEvent.SHOW_PUBLIC_PICTURE_EVENT, getRequestJson());

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
				selectImageOperation(mPictureList.get(position));
			}
        });
		
		return mView;
	}
	
	
	@Override
	public void updateUi(ServiceResponse serviceResponse) {
		mView.findViewById(R.id.loading_view).setVisibility(View.GONE);
		if(serviceResponse.getErrorCode()==ServiceResponse.SUCCESS){

			switch (serviceResponse.getEventType()) {
			case ApiEvent.SHOW_PUBLIC_PICTURE_EVENT:

				mPictureList = (ArrayList<String>)serviceResponse.getResponseObject();
				drawGridView(null);

				break;
				
			case ApiEvent.UPLOAD_PUBLIC_PICTURE_EVENT:
				((BaseActivity)getActivity()).showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
				
				String imgUrl = (String)serviceResponse.getResponseObject();
				
				if(!StringUtils.isNullOrEmpty(imgUrl)){
					mPictureList.add(0,imgUrl);
					drawGridView(((MyPictureActivity)getActivity()).getImageEncodedData());
				}
				
				break;
				
			default:
				break;
			}


		}else{

		}

	}
	
	
	private void drawGridView(String encodeImage){
		

		if(mPictureList.size()>0){

			mView.findViewById(R.id.grid_view_picture).setVisibility(View.VISIBLE);
			mView.findViewById(R.id.empty_view).setVisibility(View.GONE);
			mGridView.setAdapter(new MyPictureAdapter(getActivity(), mPictureList,encodeImage));

		}else{

			mView.findViewById(R.id.grid_view_picture).setVisibility(View.GONE);
			mView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
		}
	}

	
	private String getRequestJson(){

		//{"userid" : "2","privacy":"public"}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", getActivity()));
			jsonObject.putOpt("privacy", "public");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}
	
	
	private void selectImageOperation(String imgUrl) {

		Intent intent = new Intent(getActivity(),PhotoDetailActivity.class);
		intent.putExtra(AppConstants.IMAGE_URL, imgUrl);
		intent.putExtra(AppConstants.IS_COMING_FROM_PUBLIC_PHOTO, true);
		startActivity(intent);
		
        /*final CharSequence[] items = { "Make Profile Photo", "Make Private",
                "Delete" };
 
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("My Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item==0) {
                   
                } else if (item==1) {
                    
                } else if (item==2) {
                    
                }
                ToastCustom.underDevelopment(getActivity());
                dialog.dismiss();
            }
        });
        builder.show();*/
    }

}
 
