/**
 * 
 */
package com.digitalforce.datingapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.farru.android.utill.StringUtils;

/**
 * @author FARHAN
 *
 */


public class ProfilePhotoAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<String> mPictureList;
	private LayoutInflater mInflater;

	public ProfilePhotoAdapter(Context context,ArrayList<String> pictureList){
		mContext = context;
		mPictureList = pictureList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPictureList==null?0:mPictureList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String url = mPictureList.get(position);
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView =  mInflater.inflate(R.layout.row_profile_photo, parent, false);
			viewHolder.myPicImageView = (ImageView)convertView.findViewById(R.id.my_picture_img_view);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		
		if(!StringUtils.isNullOrEmpty(url)) picassoLoad(url, viewHolder.myPicImageView);
		return convertView;
	}

	public class ViewHolder{
		public ImageView myPicImageView;
	}

	public void picassoLoad(String url, ImageView imageView) {
		PicassoEx.getPicasso(mContext).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
	}

}
