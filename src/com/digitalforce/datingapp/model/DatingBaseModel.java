/**
 * 
 */
package com.digitalforce.datingapp.model;

import com.farru.android.model.BaseModel;

/**
 * @author FARHAN
 *
 */
public class DatingBaseModel extends BaseModel{
	
	private int messageCode;
	private String errorMsg;
	
	public int getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
