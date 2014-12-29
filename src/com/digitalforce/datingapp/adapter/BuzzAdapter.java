package com.digitalforce.datingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.view.UserLocationActivity;
import com.digitalforce.datingapp.widgets.RoundedImageView;
import com.farru.android.utill.StringUtils;

import java.util.ArrayList;

/**
 * Created by FARHAN on 11/9/2014.
 */

public class BuzzAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserInfo> mUserInfos;
    private LayoutInflater inflater;

    public BuzzAdapter(Context context, ArrayList<UserInfo> userInfos)
    {
        this.context = context;
        mUserInfos = userInfos;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(mUserInfos != null && mUserInfos.size() > 0)
            return mUserInfos.size();
        return 0;
    }

    @Override
    public UserInfo getItem(int position) {
        // TODO Auto-generated method stub
        if(mUserInfos != null && mUserInfos.size() > 0)
            return mUserInfos.get(position);
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        UserInfo nearBy = getItem(position);
        if(convertView==null){
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.layout_grid_nearby_details, parent, false);

            viewHolder.infoDistance = (LinearLayout)convertView.findViewById(R.id.info_distance);
            viewHolder.infoLayout = (LinearLayout)convertView.findViewById(R.id.info_layout);
            viewHolder.member = (TextView) convertView.findViewById(R.id.txt_nearby_member_name);
            viewHolder.place = (TextView) convertView.findViewById(R.id.txt_nearby_place);
            viewHolder.image = (RoundedImageView) convertView.findViewById(R.id.img_nearby_member);
            viewHolder.country = (TextView) convertView.findViewById(R.id.txt_country);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.place.setVisibility(View.GONE);

        if(!StringUtils.isNullOrEmpty(nearBy.getFirstName()+" "+nearBy.getLastName())){

            viewHolder.infoLayout.setVisibility(View.VISIBLE);
            viewHolder.member.setText(nearBy.getFirstName()+" "+nearBy.getLastName());

        }else if(!StringUtils.isNullOrEmpty(nearBy.getLastName())){

            viewHolder.infoLayout.setVisibility(View.VISIBLE);
            viewHolder.member.setText(nearBy.getLastName());

        }else{
            viewHolder.infoLayout.setVisibility(View.INVISIBLE);

        }

        if(!StringUtils.isNullOrEmpty(nearBy.getCountry())){
            viewHolder.country.setVisibility(View.VISIBLE);
            viewHolder.country.setText(nearBy.getCountry());
        }else{
            viewHolder.country.setVisibility(View.VISIBLE);
        }

        viewHolder.infoDistance.setTag(nearBy);
        viewHolder.infoDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo nObj = (UserInfo)v.getTag();
                if(StringUtils.isNullOrEmpty(nObj.getCountry()) || nObj.getCountry().equalsIgnoreCase("N/A")){
                    return;
                }
                Intent i =new Intent(context,UserLocationActivity.class);
                i.putExtra(AppConstants.MAP_LATITUDE,nObj.getLatitude());
                i.putExtra(AppConstants.MAP_LONGITUDE,nObj.getLongitude());
                i.putExtra(AppConstants.MAP_USER_NAME,nObj.getFirstName());
                i.putExtra(AppConstants.MAP_USER_ID,nObj.getUserId());
                context.startActivity(i);
            }
        });


        if(!StringUtils.isNullOrEmpty(nearBy.getImage())){
            picassoLoad(nearBy.getImage(), viewHolder.image);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView member;
        public TextView place;
        public TextView country;
        public RoundedImageView image;
        public LinearLayout infoLayout;
        public LinearLayout infoDistance;
    }

    public void picassoLoad(String url, ImageView imageView) {
        PicassoEx.getPicasso(context).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
    }



}
