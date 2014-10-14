package com.digitalforce.datingapp.adapter;

import java.util.ArrayList;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.model.NearBy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		// TODO Auto-generated method stub
		
		convertView = inflater.inflate(R.layout.layout_grid_nearby_details, parent, false);
		
		TextView mmemberName = (TextView) convertView.findViewById(R.id.txt_nearby_member_name);
		TextView mplace = (TextView) convertView.findViewById(R.id.txt_nearby_place);
		ImageView mimgMember = (ImageView) convertView.findViewById(R.id.img_nearby_member);
		
		NearBy nearBy = getItem(position);
		mmemberName.setText(nearBy.getFirstName());
		mmemberName.setTag(nearBy.getUserId());
		mplace.setText(nearBy.getDistance());
		
		return convertView;
	}

}
