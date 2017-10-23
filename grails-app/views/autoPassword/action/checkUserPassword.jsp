<%@page import="com.estorm.framework.util.HttpConnection"%>
<%@page import="com.estorm.framework.util.HttpParameter"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%

String corp_user_id = request.getParameter("user_id");
String user_password = request.getParameter("user_password");

/****************************** 구현부분  시작**********************************/
//사용자 아이디/비밀번호 존재하는지 확인
	boolean isOK = true; 			//정보 변경(존재할 경우 true, 존재하지 않을 경우 false)
	boolean isAutoPW = false; 		//정보 변경(AutoPassword를 사용하는 경우 true, 사용하지 않는 경우 false)


//사용자 정보 확인(기존 사용자 Table에 있는 정보)

/****************************** 구현부분   종료**********************************/

if (isOK) {
	
	if (isAutoPW) {
		%>
		<script>
			alert("AutoPassword를 사용하는 사용자입니다.");
			location.href="../join.jsp";
		</script>
		<%
	}else{
		session.setAttribute("DualAuthData.join.corp_user_id", corp_user_id);	
	
		String url = "http://localhost:11040/otp/rest/adapter/user/getUser";
		HttpParameter params = new HttpParameter()
			.add("corp_id", corp_id)	
			.add("corp_user_id", corp_user_id)
			.add("token", managerToken);
		
		String result = new HttpConnection().sendPost(url, params);			
		JSONObject json = new JSONObject(result);
		if (json.getBoolean("result")) {
			response.sendRedirect("../join_3.jsp");
			
		} else if ("630.3".equals(json.getString("code"))) {
			response.sendRedirect("../join_1.jsp?user_id="+corp_user_id+"");
		} else {
			%>
			<script>
			alert("에러입니다. 관리자에게 문의 하십시요.(에러코드 : <%=json.getString("code") %>)");
			location.href = "./join.jsp";
			</script>
			<%
		}
	}
} else {
	%>
	<script>
		alert("ID/Password가 틀렸습니다.");
		location.href="../join.jsp";
	</script>
	<%
}
%>
