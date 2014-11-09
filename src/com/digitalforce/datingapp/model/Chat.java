package com.digitalforce.datingapp.model;

import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.farru.android.utill.StringUtils;

/**
 * Created by FARHAN on 11/8/2014.
 */
public class Chat {
    private String userId;
    private String text;
    private String chatImage;
    private String byName;
    private String byPhoto;
    private String type;
    private String time;

    public Chat(){}

    public Chat(Chat chat){
        userId = chat.getUserId();
        text = chat.getText();
        chatImage = chat.getChatImage();
        byName = chat.getChatImage();
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

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
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
            return 0;
        }else{
            return 1;
        }
    }


}
