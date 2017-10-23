<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="test">
	<head>
		
        <!-- meta tag -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <title>AutoPassword™</title>
        
		<!-- script -->
		<script type="text/javascript" src="./js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="./js/jquery.progressTimer.js"></script>
		<script type="text/javascript" src="./js/dualauth.error-3.0.js"></script>
		<script type="text/javascript" src="./js/dualauth-3.0.js"></script>
		
		
		<!-- <script src="./js/jquery-1.10.2.min.js"></script> -->
		<!-- <script src="./js/bootstrap.js"></script> -->
        <script src="./js/datepicker.js"></script>
        <script src="./js/swiper.js"></script>
        <!-- <script src="./js/jquery.progressTimer.js"></script> -->
        <script src="./js/jquery.popupwindow.js"></script>
        
        
        
        <!-- css -->
        <link rel="stylesheet" href="./css/bootstrap.css">
        <link rel="stylesheet" href="./css/bootstrap-theme.css">
        <link rel="stylesheet" href="./css/datepicker.css">
        <link rel="stylesheet" href="./css/swiper.css">
        <link rel="stylesheet" href="./css/style.css">
        <link rel="stylesheet" href="./css/reset.css">

        <script type="text/javascript">
            var agt = navigator.userAgent.toLowerCase();
            if (agt.indexOf("msie") != -1 && navigator.userAgent.indexOf('MSIE 8.0') > -1) {
                document.write('<link rel="stylesheet" href="./css/style_ie8.css" />');
            } else {
                document.write('<link rel="stylesheet" href="./css/style.css" />');
            }
        </script>

        
	</head>

<%
String user_id = request.getParameter("user_id");
session.setAttribute("user_id", user_id);


/****************************** 기능구현  시작**********************************/
//사용자가 AutoPassword를 사용하는지 확인
boolean isID = true;										//정보 변경(사용할 경우 true, 사용하지 않을 경우 false)
String corp_user_id = "00742f2471304d96b7ff42a3523baca036";						//정보 변경(사용자의 OID 정보)

//사용자 정보 확인
String ServiceUrl = "http://demo.autopassword.com";		//Service Site의 Domain으로 변경
//String service_url = ServiceUrl + "/autopw/userInfo.do";		//Service Site의 처리 URL으로 변경
//HttpParameter service_params = new HttpParameter()
//		.add("user_id", user_id);

//String service_result = new HttpConnection().sendPost(service_url, service_params);
//JSONObject service_json = new JSONObject(service_result);
//if (service_json.getBoolean("result")) {
//		corp_user_id		= service_json.get("userOID").toString();
//		user_id              = service_json.get("userID").toString();
//} else{
//		isID = false;
//}

boolean delBtnYN = true;
if (isID) {
	delBtnYN = true;

	//AutoPassword Server에 정보가 있는지 다시 한번 확인


} else{
	delBtnYN = false;
}
/****************************** 기능구현 종료**********************************/




%>    
	<body>
		<form id="fm" method="post">
			<input type="hidden" name="user_id" id="user_id" value="<%=user_id %>" />
			<div class="pop_wrapper">
				<div class="popup">
					<div class="pop_body">
						<p class="pop_tit mb10 text-center">Service Site Main</p>
						<br /><br />
						<div class="conbox">
							<div class="otp_login" id="user_login">
									
								<div class="confirm_btn">
									<a href="javascript: autoPasswordReg();">AutoPassword 신청</a>
									<%if (delBtnYN){%>
									<br />
									<a href="javascript: autoPasswordDel();">AutoPassword 해지</a>
									<%}%>
								</div>		
							</div>
						</div>
					</div><!--// pop_body -->
				</div>             
			</div>
		</form>
	</doby>

<script>

	function autoPasswordReg(){
		//popUrl = "/autopassword/contents/join_1.jsp?user_id="+$("#user_id").val();	
		//popOption = "width=490, height=450, resizable=no, scrollbars=no, status=no;";
		//window.open(popUrl,"",popOption);

		$("#fm").attr(	{
			target:"_self",
			action:"./step.jsp?user_id="+$("#user_id").val()
		}).submit();
	}

	function autoPasswordDel(){
		$("#fm").attr(	{
			target:"_self",
			action:"./action/delUserAutopassword.jsp"
		}).submit();
	}

</script>

</html>
