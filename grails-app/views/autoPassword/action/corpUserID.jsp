<%@page import="com.estorm.framework.util.HttpConnection"%>
<%@page import="com.estorm.framework.util.HttpParameter"%>
<%@page import="org.json.JSONObject"%>
<%@ page contentType="text/json; charset=UTF-8"
		 pageEncoding="UTF-8"%>
    
    <%
    try{
	    
    	//본 페이지는 AutoPassword 사용 등록시 진행되는 AutoPassword 앱에서 사이트 추가시 AutoPassword Server 에서 인증 완료된 후 본인확인 번호(user_pw)를 전달해 준다.
    	//전달된 본인확인 번호로 Service Site의 Temp Table에 저장된 본인확인 번호로 발급된  OID를 찾아 AutoPassword Server에 다시 전달해야 최종 완료된다.
    	//OID 전달후 해당 OID를 사용자 정보 Table에 꼭 매칭하여 저장해야 한다.
    	// ** 인증이 완료되면 Temp Table에서 해당 정보를 삭제해도 됨.
    	
	    String user_pw = request.getParameter("user_pw");		//AutoPassword Server에서 전달되는 본인확인 번호
	   
	  
		/****************************** 기능구현  시작**********************************/
	  	//사용자 본인확인 번호 확인 
		//user_pw 정보(본인확인 번호)를 이용하여 OID 정보 추출 (Temp Table에 있는 정보)
		String ServiceUrl = "http://demo.autopassword.com";								//Service Site의 Domain으로 변경
 		String service_url = ServiceUrl + "/autopw/auatopasswordTemp.do";		//Service Site의 처리 URL으로 변경
		HttpParameter service_params = new HttpParameter()
				.add("userNO", user_pw);

		String service_result = new HttpConnection().sendPost(service_url, service_params);
		JSONObject service_json = new JSONObject(service_result);

		boolean isID = true;					//정보 변경(존재할 경우 true, 존재하지 않을 경우 false)
	  	String corp_user_id = "";				//정보 변경(Temp Table에서 본인확인 번호로 사용자 OID 정보 확인)
		String user_id = "";
		if (service_json.getBoolean("result")) {
			corp_user_id		= service_json.get("userOID").toString();
			user_id              = service_json.get("userID").toString();
		} else{
			isID = false;
		}

	  	
	  	/****************************** 기능구현 종료**********************************/

	    if (isID) {
	    	
	    	/****************************** 기능구현  시작**********************************/
	    	//사용자 정보 Table에 사용자 OID 저장
			service_url = ServiceUrl + "/autopw/userInfoUpdate.do";		//Service Site의 처리 URL으로 변경
			service_params = new HttpParameter()
					.add("userID", user_id)
					.add("userOID", corp_user_id);

			service_result = new HttpConnection().sendPost(service_url, service_params);


	    	/****************************** 기능구현 종료**********************************/
	    	
	%>
	    	{"result":true, "msg":"OK", "code":"000.0", "data":{"corp_user_id":"<%=corp_user_id%>"}}
	<%	
	    } else {
	%>
	   		{"result":false, "msg":"NO USER", "code":"001.4"}
	<%   		
	   	}
    } catch (Exception e) {
    	e.printStackTrace();
    }
    %>
