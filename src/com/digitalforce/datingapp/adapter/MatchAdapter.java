package com.digitalforce.datingapp.adapter;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.widgets.RoundedImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MatchAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	
	public MatchAdapter(Context context)
	{
		this.context = context;
		inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 15;
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
		convertView = inflater.inflate(R.layout.layout_match_list_detail, parent, false);
		
		RoundedImageView mimgMatcher = (RoundedImageView) convertView.findViewById(R.id.img_match);
		TextView mtxtMatcherName = (TextView) convertView.findViewById(R.id.txt_match_name);
		TextView mtxtMatcherViews = (TextView) convertView.findViewById(R.id.txt_match_views);
		TextView mtxtMatcherTime = (TextView) convertView.findViewById(R.id.txt_matchchat_time);
		
		mimgMatcher.setImageResource(R.drawable.farhan);
		return convertView;
	}

}
