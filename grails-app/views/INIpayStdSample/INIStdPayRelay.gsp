<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
        	function Submit_me(){
        		frm.target="INIpayStd_Return";
        		frm.submit();
     			self.close();
        	}
        </script>
</head>
    <body onload="Submit_me()">
<% 
	try{

		//#############################
		// 인증결과 파라미터 일괄 수신
		//#############################
		request.setCharacterEncoding("UTF-8");
		Map<String,String> paramMap = new Hashtable<String,String>();
		Enumeration elems = request.getParameterNames();
		String temp = "";

		out.println("<form name='frm' method='post' action='/INIStdPayReturn.jsp'>");
		while(elems.hasMoreElements())
		{
			temp = (String) elems.nextElement();
			out.println("<input type='text' name='"+temp+"' value='"+request.getParameter(temp)+"'>");
		}
		out.println("</form>");

	}catch(Exception e){

		System.out.println(e);
	}
%>
