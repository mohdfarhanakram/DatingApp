package com.digitalforce.datingapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.model.NearBy;
import com.digitalforce.datingapp.widgets.RoundedImageView;
import com.farru.android.utill.StringUtils;

public class NearByAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<NearBy> mlistNearby;
	private LayoutInflater inflater;

	public NearByAdapter(Context context, ArrayList<NearBy> mlistNearby)
	{
		this.context = context;
		this.mlistNearby = mlistNearby;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mlistNearby != null && mlistNearby.size() > 0)
			return mlistNearby.size();
		return 0;
	}

	@Override
	public NearBy getItem(int position) {
		// TODO Auto-generated method stub
		if(mlistNearby != null && mlistNearby.size() > 0)
			return mlistNearby.get(position);
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
		NearBy nearBy = getItem(position);
		if(convertView==null){
			viewHolder = new ViewHolder();

			convertView = inflater.inflate(R.layout.layout_grid_nearby_details, parent, false);

			viewHolder.member = (TextView) convertView.findViewById(R.id.txt_nearby_member_name);
			viewHolder.place = (TextView) convertView.findViewById(R.id.txt_nearby_place);
			viewHolder.image = (RoundedImageView) convertView.findViewById(R.id.img_nearby_member);

			convertView.setTag(viewHolder);

		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		if(!StringUtils.isNullOrEmpty(nearBy.getFirstName())){
			viewHolder.member.setVisibility(View.VISIBLE);
			viewHolder.member.setText(nearBy.getFirstName());

		}else{
			viewHolder.member.setText("Farhan");
			viewHolder.member.setVisibility(View.VISIBLE);
		}

		if(!StringUtils.isNullOrEmpty(nearBy.getDistance())){
			viewHolder.place.setVisibility(View.VISIBLE);
			viewHolder.place.setText(nearBy.getDistance());
		}else{
			viewHolder.place.setVisibility(View.GONE);
		}
		return convertView;
	}

	public class ViewHolder{
		public TextView member;
		public TextView place;
		public RoundedImageView image;
	}

}
