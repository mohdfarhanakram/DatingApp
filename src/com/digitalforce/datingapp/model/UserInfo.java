/**
 * 
 */
package com.digitalforce.datingapp.model;

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
    private String latitude;
    private String longitude;
	
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
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
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

}
