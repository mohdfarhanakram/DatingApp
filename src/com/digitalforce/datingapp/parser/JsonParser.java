/**
 * 
 */
package com.digitalforce.datingapp.parser;

import java.util.ArrayList;

import com.digitalforce.datingapp.model.Chat;
import com.digitalforce.datingapp.model.Insight;
import com.digitalforce.datingapp.utils.AppUtil;
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
        userInfo.setFirstName(jsonObject.optString("firstname"));   // it was earlier firstName
        userInfo.setLastName(jsonObject.optString("lastname"));
        userInfo.setImage(jsonObject.optString("image"));
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
                nearBy.setInterestStatus(nearJObj.optBoolean("interest_status"));
                nearBy.setCity(nearJObj.optString("city"));
                nearBy.setChatMessageTime(nearJObj.optString("chat_message_time"));
				nearBy.setChatMessage(nearJObj.optString("chat_message"));
				nearBy.setBodyType(nearJObj.optString("body_type"));
				nearBy.setEthnicity(nearJObj.optString("ethnicity"));

				JSONArray photoJArray = nearJObj.optJSONArray("public_photos");
				if(photoJArray!=null){
					for(int index=0; index < photoJArray.length(); index++){
						String url = photoJArray.optString(index);
						nearBy.getPhotos().add(url);
					}
				}
				nearByUserList.add(nearBy);
			}
		}

        JSONObject jsonInsight = jsonObject.optJSONObject("insight");
        if(jsonInsight!=null && nearByUserList.size()==1){
            Insight insight = new Insight();
            insight.setAgebetween18and19(jsonInsight.optString("agebetween18and19"));
            insight.setAgebetween20and24(jsonInsight.optString("agebetween20and24"));
            insight.setAgebetween25and29(jsonInsight.optString("agebetween25and29"));
            insight.setHeight(jsonInsight.optString("height"));
            insight.setReplyRate(jsonInsight.optString("reply_rate"));
            insight.setWeight(jsonInsight.optString("weight"));

            nearByUserList.get(0).setInsight(insight);

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

	public static String parseAddPrivatePublicPhotoJson(JSONObject jsonObject){

		try{

			return jsonObject.optString("pic");


		}catch(Exception e){

		}


		return "";

	}

	public static UserInfo parseUserChat(JSONObject jsonObject){
		UserInfo userInfo = new UserInfo();
		try{
			JSONObject  userJObj = jsonObject.optJSONObject("userprofile");
			userInfo.setImage(userJObj.optString("userImage"));
			userInfo.setDistance(userJObj.optString("distance"));
			userInfo.setCountry(userJObj.optString("country"));
			userInfo.setCity(userJObj.optString("city"));
			userInfo.setUserId(userJObj.optString("userId"));
			userInfo.setChats(parseUserChatHistoryData(jsonObject));
		}catch(Exception e){

		}
		return userInfo;
	}


    public static ArrayList<Chat> parseUserChatHistoryData(JSONObject jsonObject){

        ArrayList<Chat> chats = new ArrayList<Chat>();
        try {
            JSONArray jsonArray = jsonObject.optJSONArray("Data");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jObj = jsonArray.optJSONObject(i);
                if(jObj!=null){
                    Chat chat = new Chat();
                    chat.setUserId(jObj.optString("userId"));
                    chat.setText(jObj.optString("text"));
                    String type = jObj.optString("type");
                    chat.setType(type);

                    if(type.equalsIgnoreCase("IMAGE"))
                        chat.setChatMediaUrl(jObj.optString("chat_image"));
                    else if(type.equalsIgnoreCase("audio"))
                        chat.setChatMediaUrl(jObj.optString("chat_audio"));
                    else if(type.equalsIgnoreCase("video"))
                        chat.setChatMediaUrl(jObj.optString("chat_video"));


                    chat.setByName(jObj.optString("by_name"));
                    chat.setByPhoto(jObj.optString("by_photo"));
                    chat.setTime(AppUtil.getFormatedDate(jObj.optString("time")));
                    chats.add(chat);
                    /*if(chats.indexOf(chat)== -1){
                        chats.add(chat);
                        chats.add(new Chat(chat));

                    }else{
                        chat.setTime("");
                        chats.add(chat);
                    }*/

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return chats;
    }


    public static Chat parseNotificationData(String json){
        Chat chat = null;
        try{
           chat = new Chat();
            JSONObject  jObj = new JSONObject(json);
            chat.setUserId(jObj.optString("sender"));
            chat.setText(jObj.optString("msg"));
            String type = jObj.optString("type");
            chat.setType(type);

            if(type.equalsIgnoreCase("IMAGE"))
                chat.setChatMediaUrl(jObj.optString("image"));
            else if(type.equalsIgnoreCase("audio"))
                chat.setChatMediaUrl(jObj.optString("audio"));
            else if(type.equalsIgnoreCase("video"))
                chat.setChatMediaUrl(jObj.optString("video"));

            chat.setByName(jObj.optString("from_name"));
            chat.setTime(AppUtil.getFormatedDate(jObj.optString("date")));
        }catch(Exception e){

        }

        return chat;
    }


}
