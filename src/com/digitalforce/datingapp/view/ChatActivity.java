package com.digitalforce.datingapp.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.ChatAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.network.ServiceResponse;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity{
	
	private ListView mlist;
	private ImageView mimgMenuOption;
	private TextView mtxtTitle;

    private ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chat);
		
		mimgMenuOption = (ImageView) findViewById(R.id.img_action_menu);
		mtxtTitle = (TextView) findViewById(R.id.txt_screen_title);
		mlist = (ListView) findViewById(R.id.list_match);
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentChat = new Intent(ChatActivity.this, RudeChatActivity.class);
                intentChat.putExtra(AppConstants.CHAT_USER_ID,userList.get(position).getUserId());
                intentChat.putExtra(AppConstants.CHAT_USER_NAME,userList.get(position).getFirstName()+" "+userList.get(position).getLastName());
                intentChat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentChat);
            }
        });

        mtxtTitle.setText(getResources().getString(R.string.chat));


        hitRequestForOnlineUser();
    }

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}


    private String getRequestJson(){
        //{"userId" : "2"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("userId", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.e("Request", jsonObject.toString());
        return jsonObject.toString();
    }

    private void hitRequestForOnlineUser(){
        postData(DatingUrlConstants.ON_LINE_USER_URL, ApiEvent.ON_LINE_USER,getRequestJson());
    }

    @Override
    public void updateUi(ServiceResponse serviceResponse) {
        super.updateUi(serviceResponse);
        removeProgressDialog();
        if(serviceResponse!=null){
            switch (serviceResponse.getErrorCode()) {
                case ServiceResponse.SUCCESS:
                    switch (serviceResponse.getEventType()) {
                        case ApiEvent.ON_LINE_USER:
                            userList = (ArrayList<UserInfo>)serviceResponse.getResponseObject();
                            ChatAdapter matchAdapter = new ChatAdapter(this,userList);
                            mlist.setAdapter(matchAdapter);
                            break;
                        default:
                            break;
                    }
                    break;
                case ServiceResponse.MESSAGE_ERROR:
                    showCommonError(serviceResponse.getErrorMessages());
                    break;
                default:
                    showCommonError(null);
                    break;
            }
        }else{
            showCommonError(null);
        }
    }

}
