
<%@page import="java.net.URLEncoder"%>
<%@page import="com.estorm.framework.util.OTPUtiles"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.estorm.framework.util.HttpConnection"%>
<%@page import="com.estorm.framework.util.HttpParameter"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<%
response.setHeader("Cache-Control","no-store");   
response.setHeader("Pragma","no-cache");   
response.setDateHeader("Expires",0);   
if (request.getProtocol().equals("HTTP/1.1")) 
        response.setHeader("Cache-Control", "no-cache"); 


String user_serviceKey = "123456"; // formatter.format(new java.util.Date());			//정보 변경

JSONObject json = null;	
String token 				= managerToken;
String corp_user_id 	= "";
long period_ms 			= 60000;
long period_time			= 1;
String url = "http://localhost:11040/otp/rest/autopassword/getTmpCorpUserID";

HttpParameter params = new HttpParameter()
	.add("corp_id", corp_id)
	.add("token", token);
	
String result = new HttpConnection().sendPost(url, params);
json = new JSONObject(result); 
if (json.getBoolean("result")) {
	JSONObject datajson = json.getJSONObject("data");
	
	corp_user_id = datajson.get("corp_user_id").toString();						//AutoPassword 서버에서 발급한 사용자 OID
	period_ms = Integer.parseInt(datajson.get("period_ms").toString());		//발급한 사용자 OID의 사용 가능 시간(분)  - 사용 가능 시간은 AutoPassword 앱의 등록 시간을 뜻함.
	period_time = (period_ms / 1000) / 60;

	session.setAttribute("corp_user_id", corp_user_id);

	//AutoPassword Server에서 사용자 OID를 받아오면
	//사용자 Temp Table에 저장한다.





%>
{"result":true, "code": "Z10.1", "msg":"Check Your ID", "user_serviceKey":"<%=user_serviceKey%>", "corp_user_id":"<%=corp_user_id%>", "period_ms":"<%=period_ms%>"}
<%
}else{
%>
{"result":false, "code": "Z10.1", "msg":"Check Your ID", "data":""}
<%
}		
%>

