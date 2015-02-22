package com.digitalforce.datingapp.view;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.*;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.RudeChatAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.dialog.AudioRecordingDialog;
import com.digitalforce.datingapp.listener.AudioRecordingCompleteListener;
import com.digitalforce.datingapp.model.Chat;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.parser.JsonParser;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.AppUtil;
import com.digitalforce.datingapp.widgets.FlowLayout;
import com.edmodo.cropper.CropImageView;
import com.farru.android.network.ServiceResponse;
import com.farru.android.persistance.AppSharedPreference;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by FARHAN on 11/8/2014.
 */
public class RudeChatActivity extends BaseActivity implements View.OnClickListener,AudioRecordingCompleteListener {

    private String mUserId;

    private ListView mChatListView;

    private ArrayList<Chat> chatArrayList = new ArrayList<Chat>();
    private RudeChatAdapter mRudeChatAdapter;

    private NotificationManager  mNotificationManager;

    protected static final String JPEG_FILE_PREFIX = "IMG_";
    protected static final String JPEG_FILE_SUFFIX = ".jpg";

    String mPhotoMediaPath;
    String mVideoMediaPath;
    String mAudioMediaPath;
    String mBaseEncodedString;

    private boolean isUserOnline;
    private boolean isComingFromNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rude_chat_layout);

        mUserId = getIntent().getStringExtra(AppConstants.CHAT_USER_ID);

        isUserOnline = getIntent().getBooleanExtra(AppConstants.IS_USER_ONLINE,false);
        isComingFromNotification = getIntent().getBooleanExtra(AppConstants.IS_COMING_FROM_NOTIFICATION,false);

        ((TextView) findViewById(R.id.txt_screen_title)).setText("Chat");
        ((TextView) findViewById(R.id.txt_profile_name)).setText(getIntent().getStringExtra(AppConstants.CHAT_USER_NAME));

       if(!StringUtils.isNullOrEmpty(getIntent().getStringExtra(AppConstants.CHAT_USER_LOCATION)))
           ((TextView) findViewById(R.id.txt_profile_location)).setText(getIntent().getStringExtra(AppConstants.CHAT_USER_LOCATION));
        else
            ((TextView) findViewById(R.id.txt_profile_location)).setText("N/A");

        if(!StringUtils.isNullOrEmpty(getIntent().getStringExtra(AppConstants.CHAT_USER_AWAY)))
           ((TextView) findViewById(R.id.txt_profile_distance)).setText("Approx. "+getIntent().getStringExtra(AppConstants.CHAT_USER_AWAY)+" Away");
        else
            ((TextView) findViewById(R.id.txt_profile_distance)).setText("N/A");

        if(!StringUtils.isNullOrEmpty(getIntent().getStringExtra(AppConstants.CHAT_USER_IMAGE))){
            picassoLoad(getIntent().getStringExtra(AppConstants.CHAT_USER_IMAGE),(ImageView)findViewById(R.id.img_profile));
        }

        findViewById(R.id.img_action_menu).setVisibility(View.VISIBLE);

        mChatListView = (ListView)findViewById(R.id.chat_list_view);
        mChatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mChatListView.setStackFromBottom(true);

        findViewById(R.id.send_btn).setOnClickListener(this);
        findViewById(R.id.smiley_btn).setOnClickListener(this);
        findViewById(R.id.camera_btn).setOnClickListener(this);
        findViewById(R.id.mic_btn).setOnClickListener(this);
        findViewById(R.id.video_btn).setOnClickListener(this);
        findViewById(R.id.img_profile).setOnClickListener(this);

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

                UserInfo userInfo = (UserInfo)serviceResponse.getResponseObject();
                updateData(userInfo);
                chatArrayList = userInfo.getChats();
                updateData(userInfo);
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

    private void sendMessage(Chat chat,int event){

        Calendar calendar = Calendar.getInstance();
        String year  = calendar.get(Calendar.YEAR)+"";
        String month = (calendar.get(Calendar.MONTH)+ 1)+"";
        String day = calendar.get(Calendar.DAY_OF_MONTH)+"";

        String date = Utils.getShortMonth(month)+" "+day+", "+year;

        chat.setUserId(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", this));
        chat.setByName(DatingAppPreference.getString(DatingAppPreference.USER_NAME, "", this));

        chat.setByPhoto(DatingAppPreference.getString(DatingAppPreference.USER_PROFILE_URL, "", this));// By photo
        chat.setTime(date);

        setAdapterData(chat);

        if(event == 0){
            postData(DatingUrlConstants.SEND_MSG_URL, ApiEvent.SEND_MSG_EVENT,sendMessageJsonRequest(chat.getText(),event),false);
        }else{
            decodeMedia(chat, event);
        }


    }

    private void setAdapterData(Chat chat){

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
                    break;*/
                case 3:
                    jsonObject.putOpt("audio", msg);
                    jsonObject.putOpt("type", "audio");
                    break;
                case 4:
                    jsonObject.putOpt("video", msg);
                    jsonObject.putOpt("type", "video");
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

        /*if(!isComingFromNotification && !isUserOnline){
            showCommonError(getIntent().getStringExtra(AppConstants.CHAT_USER_NAME) + " is offline, you can not chat with him.");
            return;
        }*/

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
                break;
            case R.id.mic_btn:
                recordAudio();
                break;
            case R.id.camera_btn:
                selectImage();
                break;
            case R.id.video_btn:
                recordVideo();
                break;
            case R.id.img_profile:
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra(AppConstants.SHOW_PROFILE_USER_ID, mUserId);
                startActivity(i);
                break;
            default:
        }
    }


    private void sendChatMessage(){
        String msg = ((EditText)findViewById(R.id.msg_edit)).getText().toString();
        if(!StringUtils.isNullOrEmpty(msg)){
            Chat chat = new Chat();
            chat.setText(msg);
            chat.setType("text");
            sendMessage(chat, 0);
            ((EditText)findViewById(R.id.msg_edit)).setText("");
        }
    }


    private void sendEmotions(int id){
        Chat chat = new Chat();
        String UserName = DatingAppPreference.getString(DatingAppPreference.USER_NAME, "", this);
        chat.setText(UserName+";"+AppConstants.EMOTION_TAG+id+AppConstants.EMOTION_TAG); //add emotion key to identify emotion message
        chat.setType("text");
        sendMessage(chat, 0);
    }

    private void sendImage(){
        Chat chat = new Chat();
        chat.setChatMediaUrl(mPhotoMediaPath);
        chat.setType("IMAGE");
        sendMessage(chat,1);
    }


    private void sendAudio(){
        Chat chat = new Chat();
        chat.setChatMediaUrl(mAudioMediaPath);
        chat.setType("audio");
        sendMessage(chat,3);
    }

    private void sendVideo(){
        Chat chat = new Chat();
        chat.setChatMediaUrl(mVideoMediaPath);
        chat.setType("video");
        sendMessage(chat,4);
    }



    @Override
    protected void onResume() {
        super.onResume();
        AppConstants.isRunningInBg = false;
        if(mRudeChatAdapter==null){
            refreshChatHistory(true);
        }else{
            //refreshChatHistory(false);
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

    private void sendNotification(Chat chat) {
        if(chat==null)
            return;
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intentChat = new Intent(this, RudeChatActivity.class);
        intentChat.putExtra(AppConstants.CHAT_USER_ID,chat.getUserId());
        intentChat.putExtra(AppConstants.CHAT_USER_NAME,chat.getByName());
        intentChat.putExtra(AppConstants.IS_COMING_FROM_NOTIFICATION,true);
        intentChat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,intentChat, 0);

        String msg = null;

        switch (chat.getChatType()){
            case 0:  //text
            case 2:  //text
                msg = chat.getText();
                break;
            case 1:  //Image
                msg = "Image";
                break;
            case 3:  //audio
                msg = "Audio";
                break;
            case 4:  //video
                msg = "Video";
                break;
            default:
        }

        setTypeOfNotification(contentIntent, chat.getUserId(), chat.getByName(), msg);
    }

    private void setTypeOfNotification(PendingIntent contentIntent,String userId,String userName,String chatText){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(userName)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(chatText))
                        .setContentText(chatText);

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(getNotificationId(userId), mBuilder.build());
    }


    private int getNotificationId(String userId){
        int id = 1;
        try{
            id =  Integer.parseInt(userId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }



    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = null;
                    try {
                        f = createFile(1);
                        mPhotoMediaPath = f.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    } catch (Exception e) {
                        e.printStackTrace();
                        f = null;
                        Toast.makeText(RudeChatActivity.this, "Error while starting Camera.", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                    startActivityForResult(takePictureIntent, AppConstants.REQUEST_CODE_FOR_CAMERA);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            AppConstants.REQUEST_CODE_FOR_GALLERY);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void recordAudio(){
        (new AudioRecordingDialog(this,this)).show();
        /*Intent i = new Intent(this,AudioRecorderActivity.class);
        startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_AUDIO);*/
    }
    private void recordVideo(){
        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File f = createFile(4);
        mVideoMediaPath = f.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri());
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT , 102400);  // 100kb
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT , 60);

        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_VIDEO);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.REQUEST_CODE_FOR_CAMERA) {

                sendImage();

            } else if (requestCode == AppConstants.REQUEST_CODE_FOR_GALLERY) {
                Uri selectedImageUri = data.getData();
                mPhotoMediaPath = Utils.getPath(selectedImageUri, this);
                sendImage();

            }else if(requestCode == AppConstants.REQUEST_CODE_FOR_VIDEO){

                if(resultCode == RESULT_OK)
                     sendVideo();

            }else if(requestCode == AppConstants.REQUEST_CODE_FOR_AUDIO){
                if(resultCode == RESULT_OK){
                    mAudioMediaPath = data.getStringExtra(AppConstants.RECORDED_AUDIO_URL);
                    sendAudio();
                }

            }
        }
    }


   private String createEncodeImage(String path){
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
       if(bitmap!=null){
           Bitmap aBitmap = AppUtil.getActualBitmap(path,bitmap);
           if(aBitmap!=null){
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               // Must compress the Image to reduce image size to make upload easy
               aBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
               byte[] byte_arr = stream.toByteArray();
               // Encode Image to String
               return Base64.encodeToString(byte_arr, 0);
           }

       }
       return "";

    }


    private void decodeMedia(final Chat chat,final int event){
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {

                if(event==1){
                    mBaseEncodedString = createEncodeImage(chat.getChatMediaUrl());
                }else if(event==3){
                    mBaseEncodedString = createEncodeAudioString(chat.getChatMediaUrl());
                }else if(event==4){
                    mBaseEncodedString = createEncodeVideoString(chat.getChatMediaUrl());
                }

                return "";
            }

            @Override
            protected void onPostExecute(String msg) {

                postData(DatingUrlConstants.SEND_MSG_URL, ApiEvent.SEND_MSG_EVENT,sendMessageJsonRequest(mBaseEncodedString,event),false);

            }
        }.execute(null, null, null);
    }


    private String createEncodeVideoString(String url){
        try{

            FileInputStream fis = new FileInputStream(url);
            ByteArrayOutputStream objByteArrayOS = new ByteArrayOutputStream();
            byte[] byteBufferString = new byte[1024];

            for (int readNum; (readNum = fis.read(byteBufferString)) != -1;)
            {
                objByteArrayOS.write(byteBufferString, 0, readNum);

            }

            byte[] byteBinaryData = Base64.encode((objByteArrayOS.toByteArray()), Base64.DEFAULT);

            return new String(byteBinaryData);

        }catch(Exception e){

        }
        return "";
    }

    private String createEncodeAudioString(String path){
        try{

            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream objByteArrayOS = new ByteArrayOutputStream();
            byte[] byteBufferString = new byte[1024];

            for (int readNum; (readNum = fis.read(byteBufferString)) != -1;)
            {
                objByteArrayOS.write(byteBufferString, 0, readNum);

            }

            byte[] byteBinaryData = Base64.encode((objByteArrayOS.toByteArray()), Base64.DEFAULT);

            return  new String(byteBinaryData);


        }catch(Exception e){
            e.printStackTrace();
        }

        return "";
    }

    private void updateData(UserInfo userInfo){
        if(!StringUtils.isNullOrEmpty(userInfo.getCountry()))
            ((TextView) findViewById(R.id.txt_profile_location)).setText(getIntent().getStringExtra(AppConstants.CHAT_USER_LOCATION));
        else
            ((TextView) findViewById(R.id.txt_profile_location)).setText("N/A");

        if(!StringUtils.isNullOrEmpty(userInfo.getCity()))
            ((TextView) findViewById(R.id.txt_profile_distance)).setText("Approx. "+getIntent().getStringExtra(AppConstants.CHAT_USER_AWAY)+" Away");
        else
            ((TextView) findViewById(R.id.txt_profile_distance)).setText("N/A");

        if(!StringUtils.isNullOrEmpty(userInfo.getImage())){
            picassoLoad(getIntent().getStringExtra(AppConstants.CHAT_USER_IMAGE),(ImageView)findViewById(R.id.img_profile));
        }

    }

    @Override
    public void onRecordingCompleted(String mediaPath) {
        mAudioMediaPath = mediaPath;
        sendAudio();
    }
}
