package com.digitalforce.datingapp.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.digitalforce.datingapp.widgets.FlowLayout;
import com.farru.android.network.ServiceResponse;
import com.farru.android.persistance.AppSharedPreference;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

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

        ((TextView) findViewById(R.id.txt_screen_title)).setText("Chat");
        ((TextView) findViewById(R.id.txt_profile_name)).setText(getIntent().getStringExtra(AppConstants.CHAT_USER_NAME));
        findViewById(R.id.img_action_menu).setVisibility(View.VISIBLE);

        mChatListView = (ListView)findViewById(R.id.chat_list_view);
        mChatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mChatListView.setStackFromBottom(true);

        findViewById(R.id.send_btn).setOnClickListener(this);
        findViewById(R.id.smiley_btn).setOnClickListener(this);
        findViewById(R.id.camera_btn).setOnClickListener(this);
        findViewById(R.id.mic_btn).setOnClickListener(this);


        ((EditText)findViewById(R.id.msg_edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    findViewById(R.id.media_layout).setVisibility(View.GONE);
                    findViewById(R.id.send_btn).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.media_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.send_btn).setVisibility(View.GONE);
                }
            }
        });
        setEmotionView();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String json = intent.getStringExtra("data");
            Chat chat = JsonParser.parseNotificationData(json);
            if(chat!=null){

                if(chat.getUserId().equalsIgnoreCase(mUserId)){

                    Calendar calendar = Calendar.getInstance();
                    String year  = calendar.get(Calendar.YEAR)+"";
                    String month = (calendar.get(Calendar.MONTH)+ 1)+"";
                    String day = calendar.get(Calendar.DAY_OF_MONTH)+"";

                    String date = Utils.getShortMonth(month)+" "+day+", "+year;
                    chat.setTime(date);
                    setAdapterData(chat);

                }else{
                    sendNotification(chat);
                }
            }
        }
    };



    @Override
    public void updateUi(ServiceResponse serviceResponse) {
        super.updateUi(serviceResponse);
        findViewById(R.id.main_layout).setVisibility(View.VISIBLE);
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

    private void refreshChatHistory(boolean showLoader){
        postData(DatingUrlConstants.SHOW_CHAT_HISTORY_URL, ApiEvent.CHAT_HISTORY_EVENT,chatHistoryJsonRequest(),showLoader);
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

        setAdapterData(chat);

        postData(DatingUrlConstants.SEND_MSG_URL, ApiEvent.SEND_MSG_EVENT,sendMessageJsonRequest(msg,event),false);
    }

    private void setAdapterData(Chat chat){
       /* if(chatArrayList.indexOf(chat)== -1){
            chatArrayList.add(chat);
            chatArrayList.add(new Chat(chat));

        }else{
            chat.setTime("");
            chatArrayList.add(chat);
        }*/
        chatArrayList.add(chat);
        mRudeChatAdapter = (RudeChatAdapter)mChatListView.getAdapter();
        if(mRudeChatAdapter==null){
            mRudeChatAdapter = new RudeChatAdapter(this,chatArrayList);
            mChatListView.setAdapter(mRudeChatAdapter);
        }else{
            mRudeChatAdapter.setChatList(chatArrayList);
        }
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
               /* case 2:
                    jsonObject.putOpt("text", msg);
                    jsonObject.putOpt("type", "emotion");
                    break;
                case 3:
                    jsonObject.putOpt("image", msg);
                    jsonObject.putOpt("type", "audio");
                    break;
                case 4:
                    jsonObject.putOpt("image", msg);
                    jsonObject.putOpt("type", "video");
                    break;*/
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
            case R.id.smiley_btn:
                if(findViewById(R.id.emotion_layout).getVisibility()==View.GONE){
                    findViewById(R.id.emotion_layout).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.emotion_layout).setVisibility(View.GONE);
                }
                sendChatMessage();
                break;
            case R.id.mic_btn:
                sendChatMessage();
                break;
            case R.id.camera_btn:
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




    @Override
    protected void onResume() {
        super.onResume();
        AppConstants.isRunningInBg = false;
        if(mRudeChatAdapter==null){
            refreshChatHistory(true);
        }else{
            refreshChatHistory(false);
        }


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter(AppConstants.INTENT_EVENT_NAME));
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConstants.isRunningInBg = true;
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }



    private void setEmotionView(){
        FlowLayout layout = (FlowLayout)findViewById(R.id.emotion_layout);
        layout.removeAllViews();

        TypedArray emotions = getResources().obtainTypedArray(R.array.emotions_imgs);
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i=0; i<emotions.length(); i++){
            View view = inflater.inflate(R.layout.row_emotion_layout,null);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEmotions((Integer)v.getTag());
                    findViewById(R.id.emotion_layout).setVisibility(View.GONE);
                }
            });
            ((ImageView)view.findViewById(R.id.img_emotion)).setImageResource(emotions.getResourceId(i, -1));
            layout.addView(view);
        }


    }


    private void sendEmotions(int id){
        sendMessage("emotion"+id+"emotion",0);  //add emotion key to identify emotion message
    }

    private void sendNotification(Chat chat) {
        if(chat==null)
            return;
        NotificationManager  mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intentChat = new Intent(this, RudeChatActivity.class);
        intentChat.putExtra(AppConstants.CHAT_USER_ID,chat.getUserId());
        intentChat.putExtra(AppConstants.CHAT_USER_NAME,chat.getByName());
        intentChat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,intentChat, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(chat.getByName())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(chat.getText()))
                        .setContentText(chat.getText());

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(getNotificationId(chat.getUserId()), mBuilder.build());
    }


    private int getNotificationId(String userId){
        int id = 1;
        try{
            id =  Integer.parseInt(userId);
        }catch(Exception e){

        }
        return id;
    }


}
