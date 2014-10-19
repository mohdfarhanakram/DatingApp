/**
 * 
 */
package com.digitalforce.datingapp.model;

import com.google.gson.annotations.SerializedName;

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
	@SerializedName("hiv_status")
	private String hivStatus;
	@SerializedName("looking_for")
	private String lookingFor;
	private String interest;
	@SerializedName("sex_role")
	private String sexRole;
	
	
	

	public String getAboutMe() {
		return aboutMe;
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
