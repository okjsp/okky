<%@page import="javax.crypto.Mac"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="com.sun.crypto.provider.HmacSHA1"%>
<%@page import="com.estorm.framework.util.HttpConnection"%>
<%@page import="com.estorm.framework.util.HttpParameter"%>
<%@page import="org.json.JSONObject"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="../common.jsp" %>
    
<%!
public static String byteArrayToHex(byte[] ba) { if (ba == null || ba.length == 0) { return null; } StringBuffer sb = new StringBuffer(ba.length * 2); String hexNumber; for (int x = 0; x < ba.length; x++) { hexNumber = "0" + Integer.toHexString(0xff & ba[x]); sb.append(hexNumber.substring(hexNumber.length() - 2)); } return sb.toString(); }
public String getHash(String data, String enckey) { 
	String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	try {
		SecretKeySpec signingKey = new SecretKeySpec(enckey.getBytes(), HMAC_SHA1_ALGORITHM);
	    Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);	mac.init(signingKey);
	    return byteArrayToHex(mac.doFinal(data.getBytes()));
	} catch (Exception e) {
		return null;
	}
}

%>
<%
response.setHeader("Cache-Control","no-store");   
response.setHeader("Pragma","no-cache");   
response.setDateHeader("Expires",0);   
if (request.getProtocol().equals("HTTP/1.1")) 
        response.setHeader("Cache-Control", "no-cache"); 


String user_id = request.getParameter("corp_user_id");		//로그인 페이지의 AutoPassword 사용자 아이디 입력 필도
String user_otp = request.getParameter("user_otp");


/****************************** 기능구현  시작**********************************/
//사용자 아이디가 존재하는지 확인 
//사용자 OID 정보 확인(기존 사용자 Table에 있는 정보)
String ServiceUrl = "http://demo.autopassword.com";				//Service Site의 Domain으로 변경
String service_url = ServiceUrl + "/autopw/userInfo.do";			//Service Site의 처리 URL으로 변경
HttpParameter service_params = new HttpParameter()
		.add("user_id", user_id);

String service_result = new HttpConnection().sendPost(service_url, service_params);
JSONObject service_json = new JSONObject(service_result);

boolean isID = true;										//정보 변경(존재할 경우 true, 존재하지 않을 경우 false)
String corp_user_id = "";								//정보 변경(사용자의 OID 정보)
if (service_json.getBoolean("result")) {
	isID = true;
	corp_user_id = service_json.get("userOID").toString();
}else{
	isID = false;
}

/****************************** 기능구현 종료**********************************/


if (!isID) {
	%>{"result":false, "code": "001.4", "msg":"미등록", "data":""}<%
	return;
}


String url = "http://localhost:11040/otp/rest/adapter/auth/checkSecureOTP";
HttpParameter params = new HttpParameter()
.add("corp_id", corp_id)
.add("token", managerToken)
.add("corp_user_id", corp_user_id)
.add("random", session.getId())
//.add("user_id", corp_user_id)
.add("session_yn", "Y")
.add("otp_token", user_otp)
.add("policy", "0")
.add("policy_contry", "KO");
String result = new HttpConnection().sendPost(url, params);

JSONObject json = new JSONObject(result);
if (json.getBoolean("result")) {
	JSONObject data =  json.getJSONObject("data");
	String hash = data.getString("hash");
	String realresult = data.getString("data");
		
	json = new JSONObject(realresult);
	
	if (json.getBoolean("result")) {
		if (session.getId().equals(json.getJSONObject("data").getString("random"))) {
			String hash_cv = getHash(realresult, enckey);
			if (hash_cv != null && hash_cv.equals(hash)) {
				session.setAttribute("logined", user_id);		
			} else {
				json.put("result", false);
				json.put("code", "000.2");
				json.put("msg", "hashError");
			}
		} else {
			json.put("result", false);
			json.put("code", "000.2");
			json.put("msg", "Forgery");
		}
	} 
}
%>
{"result":<%=json.getBoolean("result") %>, "code": "<%=json.getString("code") %>", "msg":"<%=json.getString("msg") %>", "data":""}


