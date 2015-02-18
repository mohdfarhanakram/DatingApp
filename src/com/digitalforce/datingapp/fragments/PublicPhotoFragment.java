/**
 * 
 */
package com.digitalforce.datingapp.fragments;

import java.util.ArrayList;

import com.digitalforce.datingapp.dialog.PhotoGalleryDialog;
import com.digitalforce.datingapp.listener.RemovePhotoListener;
import com.sromku.simple.fb.listeners.OnPhotosListener;
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
public class PublicPhotoFragment extends BaseFragment implements RemovePhotoListener{
	private GridView mGridView;
	private View mView;
	private MyPictureAdapter myPictureAdapter;
	
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
				case ApiEvent.DELETE_PUBLIC_PHOTO_EVENT:
					if(!StringUtils.isNullOrEmpty(serviceResponse.getBaseModel().getSuccessMsg()))
					   ((BaseActivity)getActivity()).showCommonError(serviceResponse.getBaseModel().getSuccessMsg());
					if(mPictureList.size()>0){
						int index = (Integer)serviceResponse.getRequestData();
						mPictureList.remove(index);
						myPictureAdapter.setPhotoList(mPictureList);
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
			myPictureAdapter = new MyPictureAdapter(getActivity(), mPictureList,encodeImage,this);
			mGridView.setAdapter(myPictureAdapter);

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

	public void removePhoto(boolean isRemove, boolean isPublic){
        if(mPictureList.size()>0 && myPictureAdapter!=null){
			myPictureAdapter.setRemovePhoto(isRemove);
		}
	}
	
	
	private void selectImageOperation(String imgUrl) {

		(new PhotoGalleryDialog(getActivity(),mPictureList,mPictureList.indexOf(imgUrl))).show();

		/*Intent intent = new Intent(getActivity(),PhotoDetailActivity.class);
		intent.putExtra(AppConstants.IMAGE_URL, imgUrl);
		intent.putExtra(AppConstants.IS_COMING_FROM_PUBLIC_PHOTO, true);
		startActivity(intent);
		*/
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

	@Override
	public void onRemovePhoto(int index) {
		String url = mPictureList.get(index);
		String imageName = "";
		try {
			String[] splitUrl = url.split("/");
			imageName = splitUrl[splitUrl.length - 1];
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("N2HIM", "Image Name : " + imageName);
		postData(DatingUrlConstants.DELETE_USER_PHOTO, ApiEvent.DELETE_PUBLIC_PHOTO_EVENT, getRemoveRequestJson(imageName),index);
	}


	private String getRemoveRequestJson(String imageName){

		//{"userid":"27", "imagename":"142051589727.jpg", "privacy":"public"}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", getActivity()));
			jsonObject.putOpt("privacy", "public");
			jsonObject.putOpt("imagename", imageName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Request", jsonObject.toString());
		return jsonObject.toString();
	}
}
 
