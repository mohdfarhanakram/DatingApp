/**
 * 
 */
package com.digitalforce.datingapp.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.digitalforce.datingapp.model.NearBy;
import com.digitalforce.datingapp.model.UserInfo;

/**
 * @author FARHAN
 *
 */
public class JsonParser {

	/*
	 * Parse Login response
	 */
	public static UserInfo parseLoginJson(JSONObject jsonObject){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(jsonObject.optString("userid"));
		return userInfo;
	}

    /*
     *  Parse Sign up data
     */
	public static UserInfo parseSignUpJson(JSONObject jsonObject){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(jsonObject.optString("userid"));
		return userInfo;
	}
	
	/*
	 * Parse Forgot password response
	 */
	public static String parseForgotPasswordJson(JSONObject jsonObject){

		return jsonObject.optString("success");

	}

	/*
	 *  Parse Near By user data
	 */
	public static ArrayList<UserInfo> parseNearByUserJson(JSONObject jsonObject){
		ArrayList<UserInfo> nearByUserList = new ArrayList<UserInfo>();
		
		JSONArray jsonArray = jsonObject.optJSONArray("Data");
		if(jsonArray!=null){
			for(int i=0; i<jsonArray.length(); i++){
				JSONObject nearJObj = jsonArray.optJSONObject(i);
				
				UserInfo nearBy = new UserInfo();
				nearBy.setUserId(nearJObj.optString("userid"));
				nearBy.setFirstName(nearJObj.optString("firstname"));   // it was earlier firstName
				nearBy.setDistance(nearJObj.optString("distance"));
				nearBy.setLastName(nearJObj.optString("lastname"));
				nearBy.setEmail(nearJObj.optString("email"));
				nearBy.setDob(nearJObj.optString("dob"));
				nearBy.setGender(nearJObj.optString("gender"));
				nearBy.setMobile(nearJObj.optString("mobile"));
				nearBy.setCountry(nearJObj.optString("country"));
				nearBy.setLatitude(nearJObj.optString("lat"));
				nearBy.setLongitude(nearJObj.optString("long"));
				nearBy.setStatus(nearJObj.optString("status"));
				nearBy.setDistance(nearJObj.optString("distance"));
				nearBy.setImage(nearJObj.optString("image"));
				nearBy.setVideo(nearJObj.optString("video"));
				nearBy.setAudio(nearJObj.optString("audio"));
				nearBy.setHeight(nearJObj.optString("height"));
				nearBy.setWeight(nearJObj.optString("weight"));
				nearBy.setHivStatus(nearJObj.optString("hiv_status"));
				nearBy.setLookingFor(nearJObj.optString("looking_for"));
				nearBy.setInterest(nearJObj.optString("interest"));
				nearBy.setSexRole(nearJObj.optString("sex_role"));
				nearBy.setAboutMe(nearJObj.optString("about_me"));  
				nearBy.setAge(nearJObj.optString("age"));
				nearBy.setFavourite(nearJObj.optBoolean("fav_status"));
				nearByUserList.add(nearBy);
			}
		}

		return nearByUserList;

	}
	/**
	 * parse update profile data
	 */
	public static String parseUploadImageJson(JSONObject jsonObject)
	{
		
		return jsonObject.optString("userImage");
		
	}
	
	public static ArrayList<String> parseMyPicture(JSONObject jsonObject){
		
		ArrayList<String> pictureList = new ArrayList<String>();
		try{
			JSONArray jsonArray = jsonObject.optJSONArray("Data");
			if(jsonArray!=null){
				for(int i=0; i<jsonArray.length(); i++){
					String url = jsonArray.optString(i, "");
					pictureList.add(url);
				}
			}
			
			
		}catch(Exception e){
			
		}
		
		
		return pictureList;
		
	}
	

}
