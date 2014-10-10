/**
 * 
 */
package com.digitalforce.datingapp.parser;

import org.json.JSONObject;

import com.digitalforce.datingapp.model.UserInfo;

/**
 * @author FARHAN
 *
 */
public class JsonParser {
	
	public static UserInfo parseLoginJson(JSONObject jsonObject){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(jsonObject.optString("userid"));
		return userInfo;
	}
	
	
	public static UserInfo parseSignUpJson(JSONObject jsonObject){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(jsonObject.optString("userid"));
		return userInfo;
	}

}
