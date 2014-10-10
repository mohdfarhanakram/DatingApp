/**
 * 
 */
package com.farru.android.model;

/**
 * @author FARHAN
 *
 */
public class BaseModel implements IBaseModel{
	
	private int eventType;
	
	private Object responseObject;
	
	private String jsonResponse;

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	
	

}
