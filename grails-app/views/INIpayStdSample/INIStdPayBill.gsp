<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.inicis.std.util.SignatureUtil"%>
<%@page import="java.util.*"%>
<%

	/*
		//*** 위변조 방지체크를 signature 생성 ***

			oid, price, timestamp 3개의 키와 값을

			key=value 형식으로 하여 '&'로 연결한 하여 SHA-256 Hash로 생성 된값

			ex) oid=INIpayTest_1432813606995&price=819000&timestamp=2012-02-01 09:19:04.004
				

			 * key기준 알파벳 정렬

			 * timestamp는 반드시 signature생성에 사용한 timestamp 값을 timestamp input에 그대로 사용하여야함
	*/

	//############################################
	// 1.전문 필드 값 설정(***가맹점 개발수정***)
	//############################################

	// 여기에 설정된 값은 Form 필드에 동일한 값으로 설정
	String mid					= "INIBillTst";		// 가맹점 ID(가맹점 수정후 고정)					
	
	//인증
	String signKey			    = "SU5JTElURV9UUklQTEVERVNfS0VZU1RS";	// 가맹점에 제공된 웹 표준 사인키(가맹점 수정후 고정)
	String timestamp			= SignatureUtil.getTimestamp();			// util에 의해서 자동생성

	String oid					= mid+"_"+SignatureUtil.getTimestamp();	// 가맹점 주문번호(가맹점에서 직접 설정)
	String price					= "1000";													// 상품가격(특수기호 제외, 가맹점에서 직접 설정)

	//###############################################
	// 2. 가맹점 확인을 위한 signKey를 해시값으로 변경 (SHA-256방식 사용)
	//###############################################
	String mKey = SignatureUtil.hash(signKey, "SHA-256");
	
	//###############################################
	// 2.signature 생성
	//###############################################
	Map<String, String> signParam = new HashMap<String, String>();

	signParam.put("oid",		oid); 							// 필수
	signParam.put("price", price);							// 필수
	signParam.put("timestamp",	timestamp);		// 필수

	// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
	String signature = SignatureUtil.makeSignature(signParam);
	
	
	/* 기타 */
	String siteDomain = "https://okky.kr/INIpayStdSample"; //가맹점 도메인 입력

	// 페이지 URL에서 고정된 부분을 적는다. 
	// Ex) returnURL이 http://127.0.0.1:8080INIpayStdSample/INIStdPayReturn.jsp 라면
	// http://127.0.0.1:8080/INIpayStdSample 까지만 기입한다.
	
%>

<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="intro">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body { background-color: #efefef;}
		body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
		table, img {border:none}
	</style>

	<!-- 이니시스 표준결제 js -->
	<!-- 가맹점 URL이 http일 경우 http처리, 실제 오픈시 가맹점 MID로 stdpay로 처리 -->	
	<script language="javascript" type="text/javascript" src="https://stgstdpay.inicis.com/stdjs/INIStdPay.js" charset="UTF-8"></script>
<script type="text/javascript">

function cardShow(){
	document.getElementById("acceptmethod").value = "BILLAUTH(card):FULLVERIFY";
}

function hppShow(){
	document.getElementById("acceptmethod").value = "BILLAUTH(HPP):HPP(1)";
}

</script>

</head>
<body bgcolor="#FFFFFF" text="#242424" leftmargin="0" topmargin="15" marginwidth="0" marginheight="0" bottommargin="0" rightmargin="0">
	
	<div style="padding:10px;background-color:#f3f3f3;width:100%;font-size:13px;color: #ffffff;background-color: #000000;text-align: center">
		이니시스 표준결제 신용카드 빌링키 발급 샘플
	</div>
	
	<table width="650" border="0" cellspacing="0" cellpadding="0" style="padding:10px;" align="center">
		<tr>
			<td bgcolor="6095BC" align="center" style="padding:10px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="padding:20px">

					<tr>
						<td>
							이 페이지는 INIpay Standard 결제요청을 위한 예시입니다.<br/>
							<br/>
							결제처리를 위한 action등의 모든 동작은 Import 된 스크립트에 의해 자동처리됩니다.<br/>

							<br/>
							Form에 설정된 모든 필드의 name은 대소문자 구분하며,<br/>
							이 Sample은 결제를 위해서 설정된 Form은 테스트 / 이해돕기를 위해서 모두 type="text"로 설정되어 있습니다.<br/>
							운영에 적용시에는 일부 가맹점에서 필요에 의해 사용자가 변경하는 경우를 제외하고<br/>
							모두 type="hidden"으로 변경하여 사용하시기 바랍니다.<br/>

							<br/>
							<font color="#336699"><strong>함께 제공되는 매뉴얼을 참조하여 작성 개발하시기 바랍니다.</strong></font>
							<br/><br/>
						</td>
					</tr>
					<tr>
						<td>
							<input type="radio" id="card" name="payTypeChk" value="card" onclick="cardShow()" checked="checked">카드빌링
							<input type="radio" id="hpp"  name="payTypeChk" value="hpp"  onclick="hppShow()" >휴대폰빌링
						</td>
					</tr>
					<tr>
						<td >
							<button onclick="INIStdPay.pay('SendPayForm_id')" style="padding:10px">빌링결제요청</button>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td style="text-align:left;">
										<form id="SendPayForm_id" name="" method="POST" >
										
											<br/><b>***** 필 수 *****</b>
											<div style="border:2px #dddddd double;padding:10px;background-color:#f3f3f3;">

												<br/><b>version</b> :
												<br/><input  style="width:100%;" name="version" value="1.0" >

												<br/><b>mid</b> :
												<br/><input  style="width:100%;" name="mid" value="<%=mid%>" >
													
												<br/><b>goodname</b> :
												<br/><input  style="width:100%;" name="goodname" value="테스트" >

												<br/><b>oid</b> :
												<br/><input  style="width:100%;" name="oid" value="<%=oid%>" >

												<br/><b>price</b> :
												<br/><input  style="width:100%;" name="price" value="<%=price%>" >

												<br/><b>currency</b> :
												<br/>[WON|USD]
												<br/><input  style="width:100%;" name="currency" value="WON" >

												<br/><b>buyername</b> :
												<br/><input  style="width:100%;" name="buyername" value="홍길동" >

												<br/><b>buyertel</b> :
												<br/><input  style="width:100%;" name="buyertel" value="010-1234-5678" >

												<br/><b>buyeremail</b> :
												<br/><input  style="width:100%;" name="buyeremail" value="test@inicis.com" >

											
												<!-- <br/><b>timestamp</b> : -->
												<input type="hidden" style="width:100%;" name="timestamp" value="<%=timestamp %>" >

												<!-- <br/><b>signature</b> : -->
												<input type="hidden" style="width:100%;" name="signature" value="<%=signature%>" >

												<br/><b>returnUrl</b> :
												<br/><input  style="width:100%;" name="returnUrl" value="<%=siteDomain%>/INIStdPayReturn.jsp" >
												<!--
                            payViewType이 popup일 경우 crossDomain이슈로 우회처리 권장하지 않음. 
                            <input  style="width:100%;" name="returnUrl" value="<%=siteDomain%>/INIStdPayRelay.jsp" >
												-->
												<input type="hidden"  name="mKey" value="<%=mKey%>" >

											</div>

											<br/><br/>
											<b>***** 기본 옵션 *****</b>
											<div style="border:2px #dddddd double;padding:10px;background-color:#f3f3f3;">												
                        						<input type="hidden" style="width:100%;" name="gopaymethod" value="">
												<br/>
												<b>offerPeriod</b> : 제공기간
												<br/>ex)20151001-20151231, [Y2:년단위결제, M2:월단위결제, yyyyMMdd-yyyyMMdd : 시작일-종료일]
												<br/><input  style="width:100%;" name="offerPeriod" value="20161001-20161231" >
												<br/><br/>
												
													<br/><b>acceptmethod : 기타 옵션 정보 및 설명은 연동정의보 참조 구분자 ":"
											
												<br/><input style="width:100%;" name="acceptmethod" value="BILLAUTH(card)" > 
												<b>결제일 알림 메세지</b> : 결제일 알림 메세지
												<br/><input  style="width:100%;" id="billPrint_msg" name="billPrint_msg" value="고객님의 매월 결제일은 24일 입니다." >
												<br/>
											</div>
											<br/><br/>
											<b>***** 표시 옵션 *****</b>
											<div style="border:2px #dddddd double;padding:10px;background-color:#f3f3f3;">
												<br/><b>languageView</b> : 초기 표시 언어
												<br/>[ko|en] (default:ko)
												<br/><input style="width:100%;" name="languageView" value="" >

												<br/><b>charset</b> : 리턴 인코딩
												<br/>[UTF-8|EUC-KR] (default:UTF-8)
												<br/><input style="width:100%;" name="charset" value="" >

												<br/><b>payViewType</b> : 결제창 표시방법
												<br/>[overlay] (default:overlay)
												<br/><input style="width:100%;" name="payViewType" value="" >

												<br/><b>closeUrl</b> : payViewType='overlay','popup'시 취소버튼 클릭시 창닥기 처리 URL(가맹점에 맞게 설정)
												<br/>close.jsp 샘플사용(생략가능, 미설정시 사용자에 의해 취소 버튼 클릭시 인증결과 페이지로 취소 결과를 보냅니다.)
												<br/><input style="width:100%;" name="closeUrl" value="<%=siteDomain%>/close.jsp" >

												<br/><b>popupUrl</b> : payViewType='popup'시 팝업을 띄울수 있도록 처리해주는 URL(가맹점에 맞게 설정)
												<br/>popup.jsp 샘플사용(생략가능,payViewType='popup'으로 사용시에는 반드시 설정)
												<br/><input style="width:100%;" name="popupUrl" value="<%=siteDomain%>/popup.jsp" >

											</div>
											
											
											<br/><br/>
											<b>***** 추가 옵션 *****</b>
											<div style="border:2px #dddddd double;padding:10px;background-color:#f3f3f3;">
												<br/><b>merchantData</b> : 가맹점 관리데이터(2000byte)
												<br/>인증결과 리턴시 함께 전달됨
												<br/><input  style="width:100%;" name="merchantData" value="" >
											</div>																													
										</form>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>