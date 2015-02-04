package com.digitalforce.datingapp.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.model.Chat;
import com.digitalforce.datingapp.parser.JsonParser;
import com.digitalforce.datingapp.view.RudeChatActivity;
import com.farru.android.application.BaseApplication;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by FARHAN on 11/23/2014.
 */

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    String TAG = "Rude GcmIntentService";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                //sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                //sendNotification("Deleted messages on server: " +extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                String json = intent.getStringExtra("data");
                Chat chat = JsonParser.parseNotificationData(json);
                if(AppConstants.isRunningInBg){
                    sendNotification(chat);
                    Log.i(TAG, "Received: " + extras.toString());
                }else{
                    Intent newIntent = new Intent(AppConstants.INTENT_EVENT_NAME);
                    newIntent.putExtra("data",json);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(newIntent);

                }

            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
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

        setTypeOfNotification(contentIntent,chat.getUserId(),chat.getByName(),msg);
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
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(uri);
        mNotificationManager.notify(getNotificationId(userId), mBuilder.build());
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
