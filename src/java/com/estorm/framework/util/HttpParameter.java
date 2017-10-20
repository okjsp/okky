package com.estorm.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpParameter {
	StringBuffer sb = new StringBuffer();
	public HttpParameter() {
		
	}
	public HttpParameter(String name, String value) {
		try {
			sb.append(name+"="+ URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public HttpParameter add(String name, String value) {
		if(sb.length() != 0) {
			sb.append("&");
		} 
		try {
			sb.append(name+"="+ URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}
	public String getParams(){
		return sb.toString();
	}
}
