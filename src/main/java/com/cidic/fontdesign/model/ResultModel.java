package com.cidic.fontdesign.model;

public class ResultModel {
	private int resultCode;
	private String message;
	private Object object;
	private String uptoken;
	
	public int getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}

	public String getUptoken() {
		return uptoken;
	}

	public void setUptoken(String uptoken) {
		this.uptoken = uptoken;
	}	
}
