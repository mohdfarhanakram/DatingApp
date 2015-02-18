/**
 * 
 */
package com.digitalforce.datingapp.model;

import com.farru.android.utill.StringUtils;

import java.util.ArrayList;


/**
 * @author FARHAN
 *
 */
public class UserInfo extends NearBy{

	private String userId;
	private String aboutMe;
	private String video;
	private String audio;
	private String age;
	private String height;
	private String weight;
	private String hivStatus;
	private String lookingFor;
	private String interest;
	private String sexRole;
	private boolean favourite;
    private Insight insight;
    private boolean interestStatus;
    private String bodyType;
	private String ethnicity;
    private String city;

    private String chatMessageTime;
    private String chatMessage;

	private ArrayList<Chat> chats = new ArrayList<Chat>();

	public ArrayList<Chat> getChats() {
		return chats;
	}

	public void setChats(ArrayList<Chat> chats) {
		this.chats = chats;
	}

	public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatMessageTime() {
        return chatMessageTime;
    }

    public void setChatMessageTime(String chatMessageTime) {
        this.chatMessageTime = chatMessageTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isInterestStatus() {
        return interestStatus;
    }

    public void setInterestStatus(boolean interestStatus) {
        this.interestStatus = interestStatus;
    }

    public Insight getInsight() {
        return insight;
    }

    public void setInsight(Insight insight) {
        this.insight = insight;
    }

    private ArrayList<String> photos = new ArrayList<String>();
	
	
	

	public ArrayList<String> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<String> photos) {
		this.photos = photos;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHeight() {
        if(StringUtils.isNullOrEmpty(height.trim()))
            return "0'00";
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
        if(StringUtils.isNullOrEmpty(weight.trim()))
            return "0 lbs";

		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHivStatus() {
		return hivStatus;
	}

	public void setHivStatus(String hivStatus) {
		this.hivStatus = hivStatus;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getSexRole() {
		return sexRole;
	}

	public void setSexRole(String sexRole) {
		this.sexRole = sexRole;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
}
