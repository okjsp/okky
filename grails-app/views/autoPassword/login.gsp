<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
        <!-- meta tag -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <title>okky</title>
        
		<!-- script -->

        <asset:javascript src="jquery" />
        <asset:javascript src="libs/jquery.progressTimer.js" />
        <asset:javascript src="libs/dualauth.error-3.0.js" />
        <asset:javascript src="libs/dualauth-3.0.js" />

        <asset:stylesheet src="reset.css"/>
        <asset:stylesheet src="style.css"/>
        <asset:stylesheet src="APW-style.css.css"/>
        
        
        <!--[if lt IE 9]>
            <script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
        <![endif]-->
        
        
	</head>
    
	<body>
        <div class="layout-container">
            <div class="main ">
                <div id="edit-user" class="content" role="main">
                    <h3 class="content-header">로그인</h3>
                    <div class="col-md-6 main-block-left">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h5 class="panel-header">아이디 로그인</h5>
                            </div>

                            <form action="/j_spring_security_check" class="form-signin form-user panel-body panel-margin" method="POST" id="loginForm" autocomplete="off">


                                <input type="hidden" name="redirectUrl" value="/">

                                <input type="text" name="j_username" autocorrect="off" autocapitalize="off" id="username" class="username form-control input-sm" placeholder="아이디" required="" autofocus="">
                                <input type="password" name="j_password" class="password form-control input-sm" placeholder="비밀번호" required="">
                                
                                <!-- AutoPassword 추가 -->
                                <div class="AutoPassword-bar" id="otp_login">
                                    <div class="pwBar"> 
                                        <div class="aplogo"></div>
										<input type="hidden" id="user_otp" name="user_otp"/> 
                                        <!-- progress bar 실행 -->
                                        <div id="AutoPassword">
                                            <div class="aplogo"></div>
                                        </div>
                                        <div class="otpNum">
                                            <ul>
                                                <li>
                                                    <img id="otpNum0" src="/images/AutoPassword/num_00.png" alt="Number0">
                                                    <span class="ir">0</span>
                                                </li>
                                                <li>
                                                    <img id="otpNum1" src="/images/AutoPassword/num_00.png" alt="Number0">
                                                    <span class="ir">0</span>	
                                                </li>                                                
                                                <li>
                                                    <img id="otpNum2" src="/images/AutoPassword/num_00.png" alt="Number0">
                                                    <span class="ir">0</span>
                                                </li>
                                                <li class="Right">
                                                    <img id="otpNum3" src="/images/AutoPassword/num_00.png" alt="Number0">
                                                    <span class="ir">0</span>
                                                </li>
                                                <li>
                                                    <img id="otpNum4" src="/images/AutoPassword/num_00.png" alt="Number0">
                                                    <span class="ir">0</span>
                                                </li>
                                                <li>
                                                    <img id="otpNum5" src="/images/AutoPassword/num_00.png" alt="Number0">
                                                    <span class="ir">0</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="remember_me" id="remember_me"> 로그인 유지
                                    </label>
                                </div>
                                <!-- AutoPassword 추가 -->
                                <div class="APW-login">
                                    <span>AutoPassword&trade;</span>
                                    <label class="switch">
                                        <input type="checkbox" id="btnAutoPW">
                                        <span class="slider round"></span>
                                    </label>
                                </div>
                                
								<div id="divUserLogin">
	                                <button class="btn btn-primary btn-block" type="submit" id="btnUserLogin">로그인</button>
								</div>
								<div id="divOTPLogin">
									<button class="btn btn-primary btn-block" type="submit" id="btnOTPLogin">로그인</button>
									<div class="btn btn-primary btn-block APW-login-cancel">
										<a href="#;" id="btnOTPCancel">취소</a>
									</div>
								</div>
                                <div class="signup-block">
                                    <a href="/find/user/index">계정 찾기</a> 
                                    <span class="inline-saperator">/</span>
                                    <!-- AutoPassword 추가 -->
                                    <a href="#;">AutoPassword&trade; 재설정</a>
                                    <span class="inline-saperator">/</span> 
                                    <a href="/user/register">회원 가입</a>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-md-6 main-block-right">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h5 class="panel-header">SNS 로그인</h5>
                            </div>
                            <div class="panel-body panel-margin sns-buttons">
                                <a href="/oauth/facebook/authenticate?redirectUrl=" provider="facebook" class="btn btn-facebook btn-block"><i class="fa fa-facebook fa-fw"></i> Facebook 으로 로그인</a>

                                <a href="/oauth/google/authenticate?redirectUrl=" provider="google" class="btn btn-google btn-block"><i class="fa fa-google fa-fw"></i> Google 로 로그인</a>
                                
                                <!-- AutoPassword 추가 -->
                                <a href="#;" class="btn btn-block APW-blue">AutoPassword&trade; 2차 인증 활성화</a>
                                <a href="#;" class="btn btn-block APW-disabled">AutoPassword&trade; 2차 인증 비활성화</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6 main-block-right">
                        <div class="panel panel-default APW-social-login">
                            <div class="panel-heading">
                                <h5 class="panel-header">SNS 로그인 AutoPassword&trade; 2차 인증</h5>
                            </div>
                            <div class="panel-body APW-social-wrapper">
                                <div class="APW-social-status">
                                    <!-- facebook일 때 -->
                                    <div class="APW-facebook">
                                        <div class="APW-social-icon">
                                            <i class="fa fa-facebook-square" aria-hidden="true"></i>
                                        </div>
                                        <div class="social-text">
                                            <p>Facebook으로 로그인하셨습니다.</p>
                                            <p class="APW-blue account"><i class="fa fa-envelope-o" aria-hidden="true"></i>abc@abcd.com</p>
                                        </div>
                                    </div>

                                    <!-- google일 때 -->
                                    <!--<div class="APW-google">
                                        <div class="APW-social-icon">
                                            <i class="fa fa-google" aria-hidden="true"></i>
                                        </div>
                                        <div class="social-text">
                                            <p>Google로 로그인하셨습니다.</p>
                                            <p class="APW-blue account"><i class="fa fa-envelope-o" aria-hidden="true"></i>abc@abcd.com</p>
                                        </div>
                                    </div>-->
                                </div>
                                <div class="APW-2fa-panel">
                                    <p><img src="./assets/images/AutoPassword/ap-textlogo.png" alt="AutoPassword&trade">&nbsp;2차 인증</p>
                                    <div class="AutoPassword-bar">
                                        <div class="pwBar"> 
                                            <div class="aplogo"></div>
                                            <!-- progress bar 실행 -->
                                            <div id="AutoPassword-social">
                                                <div class="aplogo"></div>
                                            </div>
                                            <div class="otpNum">
                                                <ul>
                                                    <li>
                                                        <img id="otpNum0" src="./assets/images/AutoPassword/num_00.png" alt="Number0">
                                                        <span class="ir">0</span>
                                                    </li>
                                                    <li>
                                                        <img id="otpNum1" src="./assets/images/AutoPassword/num_00.png" alt="Number0">
                                                        <span class="ir">0</span>
                                                    </li>
                                                    <li>
                                                        <img id="otpNum2" src="./assets/images/AutoPassword/num_00.png" alt="Number0">
                                                        <span class="ir">0</span>
                                                    </li>
                                                    <li class="Right">
                                                        <img id="otpNum3" src="./assets/images/AutoPassword/num_00.png" alt="Number0">
                                                        <span class="ir">0</span>
                                                    </li>
                                                    <li>
                                                        <img id="otpNum4" src="./assets/images/AutoPassword/num_00.png" alt="Number0">
                                                        <span class="ir">0</span>
                                                    </li>
                                                    <li>
                                                        <img id="otpNum5" src="./assets/images/AutoPassword/num_00.png" alt="Number0">
                                                        <span class="ir">0</span>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="btn btn-primary btn-block APW-login-cancel">
                                        <a href="#;">취소</a>
                                    </div>
                                    <a href="#;" class="APW-reset-button">AutoPassword&trade; 재설정</a>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="col-md-12 APW-setting">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h5 class="panel-header">AutoPassword™ 설정</h5>
                            </div>
                            <div class="panel-body panel-margin">
                                <div class="APW-setting-panel">
                                    <p>비밀번호 관리가 힘들다면 <span class="APW-blue">AutoPassword™</span>를 사용하세요!</p>
                                    <p>사용자 대신 스마트폰에서 일회용 비밀번호를 생성하고 입력해주는 <span class="APW-underline">비밀번호 대체 서비스</span>입니다.</p>
                                </div>
                                <button class="btn btn-primary" type="submit" id="btnAdd">설정하기</button>
                            </div>
                        </div>
                    </div>
                    
                    <!-- AutoPassword 사용중일때 -->
                    <div class="col-md-12 APW-setting">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h5 class="panel-header">AutoPassword™ 설정</h5>
                            </div>
                            <div class="panel-body panel-margin">
                                <div class="APW-setting-panel">
                                    <p><span class="APW-blue">AutoPassword™</span>를 사용중입니다.</p>
                                    <p>스마트폰을 교체하거나 스마트폰에서 AutoPassword™ 애플리케이션을
                                        재설치 했을 경우 사용 해지 후 애플리케이션에서 웹사이트 재등록이 필요합니다.</p>
                                </div>
                                <button class="btn btn-primary APW-disabled" type="submit">해지</button>
                            </div>
                        </div>
                    </div>
                </div>

                <script async="" src="//www.google-analytics.com/analytics.js"></script><script type="text/javascript">
                <!--
                    (function() {
                    document.forms['loginForm'].elements['j_username'].focus();
                })();
                // -->
                </script>

                <div class="right-banner-wrapper">

                </div>
                <!--div id="footer" class="footer" role="contentinfo">
                    <div class="row">
                        <div class="col-sm-7">
                            <div style="float: left;margin-right: 10px;">
                                <img src="//okky.kr/./assets/okky_logo_footer-afc30f99f303854f15a531d9089a1d50.png">
                            </div>
                            <div>
                                <a href="/intro/about">About OKKY</a>
                                | <a href="/user/privacy" data-toggle="modal" data-target="#userPrivacy">개인정보보호</a>
                                | <a href="mailto:admin@okky.kr">Contact</a>
                                | <a href="https://www.facebook.com/okky.sns" target="_blank">Facebook</a>
                                | <a href="https://github.com/okjsp/okky" target="_blank">Github</a>  v0.11.1
                                <br> @ 2017 <a href="http://www.ebrain.kr" target="_blank">eBrain Management</a>
                            </div>
                        </div>
                        <div class="sponsor-banner col-sm-5">
                            <div class="sponsor-banner-head">Sponsored by</div>
                            <div class="sponsor-banner-images">
                                <a href="http://www.inames.co.kr" target="_blank"><img src="//okky.kr/./assets/spb_inames-47b092113795fdf02b8d8b0f36f91c5f.png" alt="아이네임즈"></a>
                            </div>
                        </div>
                    </div>
                </div-->
            </div>
        </div>
	</body>
    
    <!-- AutoPassword Progress Bar -->
    <script>

      $(function ($) {

//        $("#AutoPassword-social").progressTimer({
//          timeLimit: 60,
//          warningThreshold: 10,
//          baseStyle: 'progress-bar-info',
//          warningStyle: 'progress-bar-info',
//          completeStyle: 'progress-bar-info',
//          onFinish: function () {
//            console.log("I'm done");
//          }
//        });
//
//        //기본 설정값
//        $("#AutoPassword").progressTimer({
//          timeLimit: 60,
//          warningThreshold: 10,
//          baseStyle: 'progress-bar-info',
//          warningStyle: 'progress-bar-info',
//          completeStyle: 'progress-bar-info',
//          onFinish: function () {
//            console.log("I'm done");
//          }
//        });

        $("[name=j_password]").show();
        $(".AutoPassword-bar").hide();
        $("[id=divUserLogin]").show();
        $("[id=divOTPLogin]").hide();


        //이벤트 function
        $("[id=btnAutoPW]").click(function (e) {

          if ($(this).is(":checked") == true) {
            $("[name=j_password]").hide();
            $(".AutoPassword-bar").show();
            $("[id=divUserLogin]").hide();
            $("[id=divOTPLogin]").show();
            $(".btn btn-primary btn-block APW-login-cancel").hide();


          } else {
            $("[name=j_password]").show();
            $(".AutoPassword-bar").hide();
            $("[id=divUserLogin]").show();
            $("[id=divOTPLogin]").hide();
          }
        });

        $("[id=btnAdd]").click(function (e) {
          location.href = "./joinStep.jsp";
        });


        // * AutoPassword 관련 script 시작 ##########################################
        var maxWaitingSec = 60;
        $("#otp_login").dualauth(
          false,
          {
            checkID: function (corp_user_id, service_type) {
              var result = {result: false, msg: "Unknown Error", code: "000.1"};
              service_type = "<%=request.getParameter("service_type") == null ? "service_password"  : request.getParameter("service_type")%>";		// 강제로 타입을 결정한다.

              $.ajax({
                type: "POST",
                url: "./action/checkID.jsp",
                data: "corp_user_id=" + corp_user_id + "&service_type=" + service_type + "&session_term=" + maxWaitingSec,
                dataType: "json",
                async: false,
                success: function (data) {
                  result = data;
                },
                error: function (data) {
                  console.log(data);
                }
              })
              return result;
            },
            goNextCheck: function (corp_user_id) {
              alert("인증성공");

              //로그인 처리 로직 추가
              //AutoPassword 인증이 완료되면 Service Site에서 Session 등을 생성한 후 페이지 이동한다.
              location.href = "main.gsp";

            },
            checkUserPassword: function (corp_user_id, user_password) {
              var result = {result: false, msg: "Unknown Error", code: "000.1"};
              ;
              $.ajax({
                type: "POST",
                url: "./action/checkUserPassword.jsp",
                data: "corp_user_id=" + corp_user_id + "&user_password=" + user_password,
                dataType: "json",
                async: false,
                success: function (data) {
                  result = data;
                },
                error: function (data) {
                  console.log(data);
                }
              })

            },
            checkServiceOTP: function (corp_user_id, user_otp) {
              var result = {result: false, msg: "Unknown Error", code: "000.1"};
              ;
              $.ajax({
                type: "POST",
                url: "./action/checkServiceSecureOTP.jsp",
                data: "corp_user_id=" + corp_user_id + "&user_otp=" + user_otp,
                dataType: "json",
                async: false,
                success: function (data) {
                  result = data;
                },
                error: function (data) {
                  console.log(data);
                }
              })
              return result;

            },
            checkUserOTP: function (corp_user_id, user_otp) {
              var result = {result: false, msg: "Unknown Error", code: "000.1"};
              ;
              $.ajax({
                type: "POST",
                url: "./action/checkServiceOTP.jsp",
                data: "corp_user_id=" + corp_user_id + "&user_otp=" + user_otp,
                dataType: "json",
                async: false,
                success: function (data) {
                  result = data;
                },
                error: function (data) {
                  console.log(data);
                }
              })
              return result;

            },
            cancelSession: function () {
              var result = {result: false, msg: "Unknown Error", code: "000.1"};
              ;
              $.ajax({
                type: "POST",
                url: "./action/cancelSession.jsp",
                dataType: "json",
                async: false,
                success: function (data) {
                  result = data;
                },
                error: function (data) {
                  console.log(data);
                }
              })
              return result;
            },
            cancelCD: function (corp_user_id) {
              var result = {result: false, msg: "Unknown Error", code: "000.1"};
              ;
              $.ajax({
                type: "POST",
                url: "./action/cancelCD.jsp",
                data: "corp_user_id=" + corp_user_id,
                dataType: "json",
                async: false,
                success: function (data) {
                  result = data;
                },
                error: function (data) {
                  console.log(data);
                }
              })
              return result;
            },
            countTime: function (resttime) {
              console.log(resttime);
            }
          },			//callback 함수들
          null,
          maxWaitingSec,
          "./action/autoCheck.jsp",
          "./login.jsp"
        ); //id 입력 받고 난 후의 데어터

        // * AutoPassword 관련 script 시작 ##########################################

      })

    </script>
</html>
