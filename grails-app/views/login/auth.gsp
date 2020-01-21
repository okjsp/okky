<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />

    <asset:stylesheet src="style.css"/>
    <asset:stylesheet src="APW-style.css"/>
</head>
<body>
<g:sidebar/>

<div id="edit-user" class="content" role="main">
    <h3 class="content-header">로그인</h3>
    <div class="col-md-6 main-block-left">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h5 class="panel-header">아이디 로그인</h5>
            </div>

            <form action='${postUrl}' class="form-signin form-user panel-body panel-margin" method='POST' id='loginForm' autocomplete='off'>
                <g:if test='${flash.message}'>
                    <div  class="alert alert-warning" role="alert">
                        <ul>
                            <li>${flash.message}</li>
                        </ul>
                    </div>
                </g:if>
                <g:if test="${params.redirectUrl}">
                    <input type="hidden" name="redirectUrl" value="${params.redirectUrl}" />
                </g:if>
                <g:if test="${params.strategy}">
                    <input type="hidden" name="strategy" value="${params.strategy}" />
                </g:if>
                <input type="text" name="j_username" autocorrect="off" autocapitalize="off" id="username" class="username form-control input-sm" placeholder="${message(code: "springSecurity.login.username.label")}" required autofocus>
                <input type="password" name='j_password' class="password form-control input-sm" placeholder="${message(code: "springSecurity.login.password.label")}" required>
                <!-- AutoPassword 추가 -->
                <div class="AutoPassword-bar" id="otp_login" style="display: none;">
                    <div class="pwBar">
                        <div class="aplogo"></div>
                        <input type="hidden" id="user_otp" name="user_otp"/>
                        <!-- progress bar 실행 -->
                        <div id="AutoPassword">
                            <div class="aplogo"></div>
                        </div>
                        <div class="otpNum" style="display:none">
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
                        <input type="checkbox" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>> <g:message code="springSecurity.login.remember.me.label"/>
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

                <!--button class="btn btn-primary btn-block" type="submit"><g:message code="springSecurity.login.button"/></button-->

                <div id="divUserLogin">
                    <button class="btn btn-primary btn-block" type="submit" id="btnUserLogin"><g:message code="springSecurity.login.button"/></button>
                </div>
                <div id="divOTPLogin">
                    <button class="btn btn-primary btn-block" type="button" id="btnOTPLogin">AutoPassword™ 로그인</button>
                    <div class="btn btn-primary btn-block APW-login-cancel" id="btnOTPCancel">
                        <a href="#;">취소</a>
                    </div>
                </div>
                <div class="signup-block">
                    <g:link controller="findUser">계정 찾기</g:link>
                    <span class="inline-saperator">/</span>
                    <!-- AutoPassword 추가 -->
                    <g:link controller="autoPassword" action="reset">AutoPassword™ 재설정</g:link>
                    <span class="inline-saperator">/</span>
                    <g:link controller="user" action="register">회원 가입</g:link>
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
                <oauth:connect provider="facebook" id="facebook-connect-link" class="btn btn-facebook btn-block"><i class="fa fa-facebook fa-fw"></i> Facebook 으로 로그인</oauth:connect>
                %{--<oauth:connect provider="twitter" id="twitter-connect-link" class="btn btn-twitter btn-block"><i class="fa fa-twitter fa-fw"></i> Twitter 로 로그인</oauth:connect>--}%
                <oauth:connect provider="google" id="google-connect-link" class="btn btn-google btn-block"><i class="fa fa-google fa-fw"></i> Google 로 로그인</oauth:connect>
            </div>
        </div>
    </div>

</div>

<content tag="script">
    <asset:javascript src="libs/jquery.progressTimer.js" />
    <asset:javascript src="libs/dualauth.error-3.0.js" />
    <asset:javascript src="libs/dualauth-3.0.js" />

    <script type='text/javascript'>
      <!--
      (function() {
        document.forms['loginForm'].elements['j_username'].focus();


//        //기본 설정값
//        $("#AutoPassword").progressTimer({
//          timeLimit: 60,
//          warningThreshold: 10,
//          baseStyle: 'progress-bar-info',
//          warningStyle: 'progress-bar-info',
//          completeStyle: 'progress-bar-info',
//          onFinish: function() {
//            console.log("I'm done");
//          }
//        });
//
//        $("#AutoPassword-social").progressTimer({
//          timeLimit: 60,
//          warningThreshold: 10,
//          baseStyle: 'progress-bar-info',
//          warningStyle: 'progress-bar-info',
//          completeStyle: 'progress-bar-info',
//          onFinish: function() {
//            console.log("I'm done");
//          }
//        });

        $("[name=j_password]").show();
        $(".AutoPassword-bar").hide();
        $("[id=divUserLogin]").show();
        $("[id=divOTPLogin]").hide();
        $("[id=btnOTPLogin]").hide();
        $("[id=btnOTPCancel]").hide();


        //이벤트 function
        $("[id=btnAutoPW]").click(function(e) {

          if ($(this).is(":checked") == true){
            $("[name=j_password]").hide();
            $(".AutoPassword-bar").show();
            $("[id=divUserLogin]").hide();
            $("[id=divOTPLogin]").show();
            $("[id=btnOTPLogin]").show();
            $("[id=btnOTPCancel]").hide();
          }else{
            $("[name=j_password]").show();
            $(".AutoPassword-bar").hide();
            $("[id=divUserLogin]").show();
            $("[id=divOTPLogin]").hide();
            $("[id=btnOTPLogin]").hide();
            $("[id=btnOTPCancel]").hide();
          }
        });

        $("[id=btnAdd]").click(function(e) {
          location.href = "./joinStep.jsp";
        });


        // * AutoPassword 관련 script 시작 ##########################################
        var maxWaitingSec = 60;
        $("#otp_login").dualauth(
          false,
          {
            checkID : function (corp_user_id, service_type) {
              var result = {result : false, msg : "Unknown Error", code : "000.1"};
              service_type = "<%=request.getParameter("service_type") == null ? "service_password"  : request.getParameter("service_type")%>";		// 강제로 타입을 결정한다.

              $.ajax({
                type: "POST",
                url: "${request.contextPath}/autoPassword/checkID",
                data : "corp_user_id=" + corp_user_id + "&service_type=" + service_type + "&session_term=" + maxWaitingSec,
                dataType : "json",
                async : false,
                success : function(data) {
                  result = data;
                },
                error :function(data) {
                  console.log(data);
                }
              });
              return result;
            },
            goNextCheck : function (corp_user_id) {

              location.href = "${request.contextPath}/";

            },
            checkUserPassword : function (corp_user_id, user_password) {
              var result = {result : false, msg : "Unknown Error", code : "000.1"};;
              $.ajax({
                type: "POST",
                url: "${request.contextPath}/autoPassword/checkUserPassword",
                data : "corp_user_id=" + corp_user_id + "&user_password=" + user_password,
                dataType : "json",
                async : false,
                success : function(data) {
                  result = data;
                },
                error :function(data) {
                  console.log(data);
                }
              })

            },
            checkServiceOTP  : function (corp_user_id, user_otp) {
              var result = {result : false, msg : "Unknown Error", code : "000.1"};;
              $.ajax({
                type: "POST",
                url: "${request.contextPath}/autoPassword/checkServiceSecureOTP",
                data : "corp_user_id=" + corp_user_id + "&user_otp=" + user_otp,
                dataType : "json",
                async : false,
                success : function(data) {
                  result = data;
                },
                error :function(data) {
                  console.log(data);
                }
              })
              return result;

            },
            checkUserOTP  : function (corp_user_id, user_otp) {
              var result = {result : false, msg : "Unknown Error", code : "000.1"};;
              $.ajax({
                type: "POST",
                url: "${request.contextPath}/autoPassword/checkServiceOTP",
                data : "corp_user_id=" + corp_user_id + "&user_otp=" + user_otp,
                dataType : "json",
                async : false,
                success : function(data) {
                  result = data;
                },
                error :function(data) {
                  console.log(data);
                }
              })
              return result;

            },
            cancelSession : function () {
              var result = {result : false, msg : "Unknown Error", code : "000.1"};;
              $.ajax({
                type: "POST",
                url: "${request.contextPath}/autoPassword/cancelSession",
                dataType : "json",
                async : false,
                success : function(data) {
                  result = data;
                },
                error :function(data) {
                  console.log(data);
                }
              })
              return result;
            },
            cancelCD : function(corp_user_id) {
              var result = {result : false, msg : "Unknown Error", code : "000.1"};;
              $.ajax({
                type: "POST",
                url: "${request.contextPath}/autoPassword/cancelCD",
                data : "corp_user_id=" + corp_user_id,
                dataType : "json",
                async : false,
                success : function(data) {
                  result = data;
                },
                error :function(data) {
                  console.log(data);
                }
              })
              return result;
            },
            countTime : function(resttime) {
              console.log(resttime);
            }
          },			//callback 함수들
          null,
          maxWaitingSec,
          "${request.contextPath}/autoPassword/autoCheck",
          "./auth"
        ); //id 입력 받고 난 후의 데어터

        // * AutoPassword 관련 script 시작 ##########################################

      })();
      // -->
    </script>
</content>

</body>
</html>
