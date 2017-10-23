
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


String cdtime = request.getParameter("cdtime");
String sessionid = session.getId();

String url = "http://localhost:11040/otp/mobileAction";
HttpParameter params = new HttpParameter()
	.add("sessionid", sessionid)
	.add("subcustom_code", corp_id); 

String result = new HttpConnection().sendPost(url, params);
JSONObject json = new JSONObject(result);

%>
{"type":"<%=json.getString("type") %>","data":"<%=json.getString("data") %>","errmsg":"<%=json.getString("errmsg") %>", "resttime":<%=(Integer.parseInt(cdtime) - (int)(System.currentTimeMillis()/1000)) %>}


