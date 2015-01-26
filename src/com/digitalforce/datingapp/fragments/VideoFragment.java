package com.digitalforce.datingapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.view.BaseActivity;
import com.digitalforce.datingapp.view.PlayVideoActivity;
import com.farru.android.utill.StringUtils;

public class VideoFragment extends Fragment{

	private View mView;
	private ProgressDialog pDialog;

    private UserInfo mUserInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.layout_fragment_video, container, false);


        mView.findViewById(R.id.btn_play_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!StringUtils.isNullOrEmpty(mUserInfo.getVideo())){
                    Intent i = new Intent(getActivity(),PlayVideoActivity.class);
                    i.putExtra(AppConstants.USER_VIDEO_URL,mUserInfo.getVideo());
                    startActivity(i);
                }else{
                    ((BaseActivity)(getActivity())).showCommonError("No profile video.");
                }

            }
        });
		return mView;
	}



    public void userInfoInstance(UserInfo userInfo){
        mUserInfo = userInfo;
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
        /*if(mUserInfo!=null && !StringUtils.isNullOrEmpty(mUserInfo.getVideo())){
            mView.findViewById(R.id.btn_play_video).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.video_txt_view).setVisibility(View.GONE);
        }else{
            mView.findViewById(R.id.btn_play_video).setVisibility(View.GONE);
            mView.findViewById(R.id.video_txt_view).setVisibility(View.VISIBLE);

        }*/
    }

}
