package com.digitalforce.datingapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.RudeChatAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.Chat;
import com.digitalforce.datingapp.parser.JsonParser;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.AppUtil;
import com.farru.android.network.ServiceResponse;
import com.farru.android.persistance.AppSharedPreference;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by FARHAN on 11/8/2014.
 */
public class RudeChatActivity extends BaseActivity implements View.OnClickListener{

    private String mUserId;

    private ListView mChatListView;

    private ArrayList<Chat> chatArrayList = new ArrayList<Chat>();
    private RudeChatAdapter mRudeChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rude_chat_layout);

        mUserId = getIntent().getStringExtra(AppConstants.CHAT_USER_ID);

        ((TextView) findViewById(R.id.txt_screen_title)).setText(getIntent().getStringExtra(AppConstants.CHAT_USER_NAME));
        findViewById(R.id.img_action_menu).setVisibility(View.INVISIBLE);

        mChatListView = (ListView)findViewById(R.id.chat_list_view);
        findViewById(R.id.send_btn).setOnClickListener(this);

        refreshChatHistory();

    }

    @Override
    public void updateUi(ServiceResponse serviceResponse) {
        super.updateUi(serviceResponse);
        removeProgressDialog();
        if(serviceResponse!=null){
            switch (serviceResponse.getErrorCode()) {
                case ServiceResponse.SUCCESS:
                    onSuccess(serviceResponse);
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

    private void onSuccess(ServiceResponse serviceResponse){
        switch (serviceResponse.getEventType()){
            case ApiEvent.CHAT_HISTORY_EVENT:
                chatArrayList = (ArrayList<Chat>)serviceResponse.getResponseObject();
                mRudeChatAdapter = new RudeChatAdapter(this,chatArrayList);
                mChatListView.setAdapter(mRudeChatAdapter);
                break;
            case ApiEvent.SEND_MSG_EVENT:
                break;
            default:
        }
    }

    @Override
    public void onEvent(int eventId, Object eventData) {

    }

    private void refreshChatHistory(){
        postData(DatingUrlConstants.SHOW_CHAT_HISTORY_URL, ApiEvent.CHAT_HISTORY_EVENT,chatHistoryJsonRequest());
    }

    private void sendMessage(String msg,int event){

        Calendar calendar = Calendar.getInstance();
        String year  = calendar.get(Calendar.YEAR)+"";
        String month = (calendar.get(Calendar.MONTH)+ 1)+"";
        String day = calendar.get(Calendar.DAY_OF_MONTH)+"";

        String date = Utils.getShortMonth(month)+" "+day+", "+year;

        Chat chat = new Chat();
        chat.setUserId(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
        chat.setByName(DatingAppPreference.getString(DatingAppPreference.USER_NAME, "", this));
        if(event==0){
            chat.setType("text");
            chat.setText(msg);
        }else{
            chat.setType("image");
            chat.setChatImage(""); //chat url
        }

        chat.setByPhoto(DatingAppPreference.getString(DatingAppPreference.USER_PROFILE_URL, "", this));// By photo
        chat.setTime(date);

        if(chatArrayList.indexOf(chat)== -1){
            chatArrayList.add(chat);
            chatArrayList.add(new Chat(chat));

        }else{
            chat.setTime("");
            chatArrayList.add(chat);
        }
        mRudeChatAdapter = (RudeChatAdapter)mChatListView.getAdapter();
        if(mRudeChatAdapter==null){
            mRudeChatAdapter = new RudeChatAdapter(this,chatArrayList);
            mChatListView.setAdapter(mRudeChatAdapter);
        }else{
          mRudeChatAdapter.setChatList(chatArrayList);
        }

        postData(DatingUrlConstants.SEND_MSG_URL, ApiEvent.SEND_MSG_EVENT,sendMessageJsonRequest(msg,event),false);
    }

    private String sendMessageJsonRequest(String msg,int type){
        //{"sender":"1","receiver":"2","text":"hello","type":"text"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("sender", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
            jsonObject.putOpt("receiver", mUserId);
            switch (type){
                case 0:
                    jsonObject.putOpt("text", msg);
                    jsonObject.putOpt("type", "text");
                    break;
                case 1:
                    jsonObject.putOpt("image", msg);
                    jsonObject.putOpt("type", "IMAGE");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Request", jsonObject.toString());
        return jsonObject.toString();
    }

    private String chatHistoryJsonRequest(){
        //{"login_user_id":"1","other_user_id":"2"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("login_user_id", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
            jsonObject.putOpt("other_user_id", mUserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Request", jsonObject.toString());
        return jsonObject.toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_btn:
                sendChatMessage();
                break;
            default:
        }
    }


    private void sendChatMessage(){
        String msg = ((EditText)findViewById(R.id.msg_edit)).getText().toString();
        if(!StringUtils.isNullOrEmpty(msg)){
            sendMessage(msg,0);
            ((EditText)findViewById(R.id.msg_edit)).setText("");
        }
    }


}
