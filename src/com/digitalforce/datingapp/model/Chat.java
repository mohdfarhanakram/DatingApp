package com.digitalforce.datingapp.model;

import android.graphics.Bitmap;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.utill.StringUtils;

/**
 * Created by FARHAN on 11/8/2014.
 */
public class Chat {
    private String userId;
    private String text;
    private String chatMediaUrl;
    private String byName;
    private String byPhoto;
    private String type;
    private String time;

    private int emotionId = -1;

    private String baseEncoded;

    private String localMediaUrl;

    public String getChatMediaUrl() {
        return chatMediaUrl;
    }

    public void setChatMediaUrl(String chatMediaUrl) {
        this.chatMediaUrl = chatMediaUrl;
    }

    public String getBaseEncoded() {
        return baseEncoded;
    }

    public void setBaseEncoded(String baseEncoded) {
        this.baseEncoded = baseEncoded;
    }

    public String getLocalMediaUrl() {
        return localMediaUrl;
    }

    public void setLocalMediaUrl(String localMediaUrl) {
        this.localMediaUrl = localMediaUrl;
    }

    public int getEmotionId() {
        return emotionId;
    }

    public void setEmotionId(int emotionId) {
        this.emotionId = emotionId;
    }

    public Chat(){}

    public Chat(Chat chat){
        userId = chat.getUserId();
        text = chat.getText();
        chatMediaUrl = chat.getChatMediaUrl();
        byName = chat.getByName();
        byPhoto = chat.getByPhoto();
        type = chat.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (time != null ? !time.equals(chat.time) : chat.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return time != null ? time.hashCode() : 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getByPhoto() {
        return byPhoto;
    }

    public void setByPhoto(String byPhoto) {
        this.byPhoto = byPhoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public  boolean isHeader(){
        return !StringUtils.isNullOrEmpty(time);
    }

    public int getChatType(){
        if(type.equalsIgnoreCase("text")){
            if(getText().contains(AppConstants.EMOTION_TAG) && isContainsId(getText())){
                return 2;
            }
            return 0;
        }else if(type.equalsIgnoreCase("image")){
            return 1;
        }else if(type.equalsIgnoreCase("audio")){
            return 3;
        }else if(type.equalsIgnoreCase("video")){
            return 4;
        }else{
            return 0;
        }
    }


    private boolean isContainsId(String msg){
        try{
            String[] msgArr = msg.split(AppConstants.EMOTION_TAG);
            String emotionNo = msgArr[msgArr.length-1];

           emotionId = Integer.parseInt(emotionNo.trim());
           return true;
       }catch(Exception e){
            emotionId = -1;
       }

        return false;
    }


    private String getMsgEmotions(String msg){

        int emotionId = -1;
        String finalMsg = "";
        try{

            String emotionMsg = "";
            String[] msgArr = msg.split(";");
            String userName = msgArr[0];
            String emotMsg = msgArr[1];

            emotionId = Integer.parseInt(emotMsg.replaceAll(AppConstants.EMOTION_TAG,"").trim());

            if(emotionId!=-1){
                finalMsg = userName+" "+AppConstants.EMOTION_MSG;
            }

        }catch(Exception e){
            emotionId = -1;
            e.printStackTrace();
        }

        return finalMsg;
    }



}
