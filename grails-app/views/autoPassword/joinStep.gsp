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

	<input type="hidden" name="corp_user_id" id="corp_user_id" />
	<h3 class="content-header">AutoPassword&trade; 설정</h3>
	<div class="col-md-12 APW-setting-tab">
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="APW-tabmenu">
					<ul>
						<li><button class="active">1단계</button></li>
						<li><button>2단계</button></li>
						<li><button>3단계</button></li>
					</ul>
				</div>
			</div>
			<div class="panel-body">
				<div class="tab-contents">
					<div class="APW-tab-con APW-tab01">
						<div class="APW-tab-inner">
							<img src="${request.contextPath}/images/AutoPassword/APW-tab-con1.png" alt="AutoPassword 설치 화면" />
							<p>인증에 사용할 스마트폰에 AutoPassword™ 애플리케이션을 설치합니다.</p>
							<button class="btn btn-primary btn-block">다음</button>
						</div>
					</div>
					<div class="APW-tab-con APW-tab02" style="display:none;">
						<div class="APW-tab-inner">
							<img src="${request.contextPath}/images/AutoPassword/APW-tab-con2.png" alt="AutoPassword 설치 화면" />
							<p>설치된 AutoPassword™ 애플리케이션을 실행 후 메뉴에서 ‘웹사이트 추가’를 선택합니다.<br>* 최초 실행 시 자동 표시됩니다.</p>
							<p>다음 정보를 이용하여 웹사이트를 추가합니다.</p>
							<div class="request-info">
								<ul>
									<div id="step2_1">
									<li>- 웹사이트 주소 : <span class="bold">okky.kr</span></li>
									<li>- 본인 확인 코드: <a class="APW-blue confirm-start" href="javascript: userCode();">본인확인 코드 생성</a></li>
									<!-- 본인확인코드 생성 시 -->
									<li class="confirm-code" style="display:none;">- 본인 확인 코드: <span class="APW-blue" id="userCode">00000</span><span class="notice">(<span id="periodMs">180</span>초 후에 재생성 해야됩니다.)</span></li>
									</div>
									<div id="step2_2">
									<!-- 추가 완료일 때 -->
									<li class="confirm-complete bold">AutoPassword™ 애플리케이션에 웹사이트가 정상적으로 추가되었습니다.</li>
									</div>
								</ul>
							</div>
							<div id="step2_btn1" style="display:none;">
							<button class="btn btn-primary btn-block confirm-sucess">다음</button>
							</div>
							<!-- disable 버튼 -->
							<div id="step2_btn2">
							<button class="btn btn-primary APW-disabled btn-block">다음</button>
							<span class="sm-text notice">*애플리케이션에 웹사이트가 정상적으로 추가되면 활성화 됩니다.</span>
							</div>
						</div>
					</div>
					<div class="APW-tab-con APW-tab03" style="display:none;">
						<div class="APW-tab-inner">
							<img src="${request.contextPath}/images/AutoPassword/APW-tab-con3.png" alt="AutoPassword 구동 화면" />
							<p>AutoPassword™ 설정이 완료되었습니다.<br>이제 AutoPassword™로 아이디만 입력하면 간편하고 안전하게 로그인하실 수 있습니다.</p>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

</div>


<content tag="script">
	<asset:javascript src="libs/jquery.progressTimer.js" />
	<asset:javascript src="libs/dualauth.error-3.0.js" />
	<asset:javascript src="libs/dualauth-3.0.js" />

	<script>

      /* AutoPassword 설정 */
      var step = $(".APW-setting-tab .panel-heading .APW-tabmenu ul li");
      var content = $(".APW-setting-tab .tab-contents>div");
      var refreshIntervalId = "";

      $(function($) {

        content.hide();
        content.eq(0).show();

        content.eq(0).children().find("button").click(function(){
          content.eq(0).hide();
          content.eq(1).show();
          step.eq(0).children("button").removeClass("active");
          step.eq(1).children("button").addClass("active");
        });

        content.eq(1).children().find("button.confirm-sucess").click(function(){
          content.eq(1).hide();
          content.eq(2).show();
          step.eq(1).children("button").removeClass("active");
          step.eq(2).children("button").addClass("active");
        });

        /* 본인확인코드 설정 클릭 시 */
        $(".APW-setting-tab .tab-contents .request-info li a").click(function(){
          $(this).parent().hide();
          $(".APW-setting-tab .tab-contents .request-info li.confirm-code").show();
        });

        $("[id=step2_2]").hide();

      })

      function userCode(){
        var result = {result : false, msg : "Unknown Error", code : "000.1"};
        $.ajax({
          type: "POST",
          url: "${request.contextPath}/autoPassword/userOID?uid=${uid}",
          dataType : "json",
          async : false,
          success : function(data) {
            result = data;
            var user_serviceKey = result.user_serviceKey;
            var corp_user_id = result.corp_user_id;
            var period_ms = result.period_ms;
            period_ms = period_ms / 1000

            $("#periodMs").text(period_ms);
            $("#corp_user_id").val(corp_user_id);
            $("#userCode").text(user_serviceKey);

            pool();
            refreshIntervalId = setInterval(f_period_ms, 1000);

          },
          error :function(data) {
            console.log(data);
          }
        })
      }

      function f_period_ms(){

        var period_ms = $("#periodMs").text();
        period_ms = period_ms - 1;

        $("#periodMs").html(period_ms);

        if (period_ms == "0"){
          $(".APW-setting-tab .tab-contents .request-info li.confirm-code").hide();
          $(".APW-setting-tab .tab-contents .confirm-start").parent().show();

          clearInterval(refreshIntervalId);
        }
      }

      function pool(){
        var option = {
          p_corp_user_id : $("#corp_user_id").val(),  //사용자 OID
          p_resttime : 0,  //남은시단 ms
          p_per_check_s : 3,   // polling check
          p_showRest : function(resttime) {
            //$("#resttime").text(resttime);
          },
          p_showTimeout : function(resttime) {
            //alert("timeout.");
          },
          p_showAdded : function(resttime) {
            $("[id=step2_1]").hide();
            $("[id=step2_2]").show();
            $("[id=step2_btn1]").show();
            $("[id=step2_btn2]").hide();
          }
        };
        DualAuth.addingPool(option);
      }

	</script>

</content>
</body>
</html>

