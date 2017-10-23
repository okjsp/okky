
<%@page import="java.net.URLEncoder"%>
<%@page import="com.estorm.framework.util.OTPUtiles"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.estorm.framework.util.HttpConnection"%>
<%@page import="com.estorm.framework.util.HttpParameter"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<%
response.setHeader("Cache-Control","no-store");   
response.setHeader("Pragma","no-cache");   
response.setDateHeader("Expires",0);   
if (request.getProtocol().equals("HTTP/1.1")) 
        response.setHeader("Cache-Control", "no-cache"); 


String user_id = request.getParameter("corp_user_id");		//로그인 페이지의 AutoPassword 사용자 아이디 입력 필드


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

//용자 정보 Table에 사용자 OID 삭제(사용자 Table에 있는 정보)
service_url = ServiceUrl + "/autopw/userInfoUpdate.do";			//Service Site의 처리 URL으로 변경
service_params = new HttpParameter()
		.add("userID", user_id)
		.add("userOID", corp_user_id);

String service_result = new HttpConnection().sendPost(service_url, service_params);
//JSONObject service_json = new JSONObject(service_result);


/****************************** 기능구현 종료**********************************/


if (isID) {
	JSONObject json = null;

	String url = "http://localhost:11040/otp/rest/autopassword/delCorpUserInfo";
	HttpParameter params = new HttpParameter()
		.add("corp_user_id", corp_user_id)
		.add("corp_id", corp_id)
		.add("token", managerToken)
		.add("site_user_id", user_id);

	String result = new HttpConnection().sendPost(url, params);			
	json = new JSONObject(result);
	if (json.getBoolean("result")) {
	%>
		<script>
			alert("해지 완료");
			location.href = "../sample_login.jsp";
		</script>
	<%
	} else{
	%>
		<script>
			alert("해지 시 오류가 발생하였습니다.");
		</script>
	<%
	}
}
%>