package com.digitalforce.datingapp.adapter;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.widgets.RoundedImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.farru.android.utill.StringUtils;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;

    private ArrayList<UserInfo> mUserInfos;
	
	public ChatAdapter(Context context,ArrayList<UserInfo> userInfos)
	{
		this.context = context;
        mUserInfos = userInfos;
		inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mUserInfos==null?0:mUserInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

        UserInfo userInfo = mUserInfos.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_chat_layout, parent, false);
            viewHolder.imageView = (RoundedImageView) convertView.findViewById(R.id.img_match);
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_match_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(!StringUtils.isNullOrEmpty((userInfo.getFirstName()+ " "+userInfo.getLastName()).trim())){

            viewHolder.name.setVisibility(View.VISIBLE);
            viewHolder.name.setText((userInfo.getFirstName()+ " "+userInfo.getLastName()).trim());
        }else{
            viewHolder.name.setVisibility(View.INVISIBLE);
        }

        if(!StringUtils.isNullOrEmpty(userInfo.getImage()))
             picassoLoad(userInfo.getImage(),viewHolder.imageView);

		/*
		
		RoundedImageView mimgMatcher = (RoundedImageView) convertView.findViewById(R.id.img_match);
		TextView mtxtMatcherName = (TextView) convertView.findViewById(R.id.txt_match_name);
		TextView mtxtMatcherViews = (TextView) convertView.findViewById(R.id.txt_match_views);
		TextView mtxtMatcherTime = (TextView) convertView.findViewById(R.id.txt_matchchat_time);*/
		
		//mimgMatcher.setImageResource(R.drawable.farhan);
		return convertView;
	}
	
	 public void picassoLoad(String url, ImageView imageView) {
	        PicassoEx.getPicasso(context).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
	 }

    public class ViewHolder{
        public TextView name;
        public ImageView imageView;
    }

}
