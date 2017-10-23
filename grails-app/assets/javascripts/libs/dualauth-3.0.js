var DualAuth = (function(app,$){
		var second_data = {};
		var callback = {};
		var maxWaitingSec = 60;
		var isSecond = false;
		
		var pollingObj = null;
		var pollingCnt = 0;
		var autoCheckURL = "./action/autoCheck.jsp";
		var initURL = "/";
		app.init = function(p_isSecond, p_callback, p_second_data, $obj, p_maxWaitingSec, p_autoCheckURL, p_initURL) {
			second_data = p_second_data;

			isSecond = p_isSecond;
			callback = p_callback;
			maxWaitingSec = p_maxWaitingSec;
			autoCheckURL = p_autoCheckURL;
			initURL = p_initURL;						

	        //$(".loginBody .box_2").hide();// 서비스 pw progress 영역
	        //$(".btn btn-primary btn-block APW-login-cancel").hide();
	        
			$("[id=btnOTPLogin]").click(function(e) {
				if ($.trim($("#username").val()) == "") {
					alert("ID를 입력하십시요.");
					return;
				}

				var result = callback.checkID($("#username").val(), "service_password"); 
				
				if (result.result) {
					second_data = result.data;
					isSecond = true;
					changeLoginType(second_data);
				} else {
					alert(DualAuthErr.getMsg(result.code));
					 callback.cancelCD();
	                 callback.cancelSession();
					 location.href = initURL;
				}
			})
			
			if (isSecond) {
				$("#username").attr("readonly", "readonly");
				$("#username").val(p_second_data.corp_user_id);
				$("#btnOTPLogin").trigger("click");
				$( "#username, #user_otp" ).keypress(function( event ) {
     			  if ( event.which == 13 ) {
     				  //$(".confirm_btn a.login_btn").trigger("click");
     				 $("#btnOTPLogin").trigger("click");     				 
     			  }
     			});	
			} else {
				$( "#username" ).keypress(function( event ) {
     			  if ( event.which == 13 ) {
     				  $("#btnOTPLogin").trigger("click");
     			  }
     			});	
			}
		};	
		
		var changeLoginType = function(second_data) {
			 
			$("#username").attr("readonly", "readonly");
			$("#btnOTPLogin").hide();
			$("#btnOTPCancel").show();
			$(".otpNum").show();

			if(second_data.login_type == "0"){
				//$(".loginBody .box_3 input").hide();
				//$(".loginBody .box_2").show();
				if (second_data.service_type == "service_password") {
					makepollingObj();
				}
				
				// * OTP 정보 출력
				for (var i = 0; i < second_data.rotp.length; i++) {
					$(".otpNum ul li:eq("+i+") img").attr("src", contextPath+"/images/AutoPassword/num_0" + second_data.rotp.substring(i, i+1) + ".png");
				}
                
				$("#btnOTPCancel").unbind("click").click(function(e){
					callback.cancelCD($("#username").val());
					callback.cancelSession();
					location.href = initURL
				});
                
				try{
					DualAuth.progressCollectorClear();
				}catch(e){}

				// * Progress Bar
				$("#AutoPassword").progressTimer({
					startDate : new Date() - 1000 * (maxWaitingSec - second_data.resttime),
					//startDate : new Date(),
					timeLimit : maxWaitingSec,
					warningThreshold : 10,
					baseStyle : 'progress-bar-info',
					warningStyle : 'progress-bar-info',
					completeStyle : 'progress-bar-info',
					onFinish : function() {
						/*alert(DualAuthErr.getMsg("Z10.1"));
						callback.cancelCD();
           	callback.cancelSession();
	         	location.href = initURL*/
					}
				});

			}
		
		};		
		
		function makepollingObj() {
			pollingObj = setTimeout(function() {				
				pollingCnt++;
				if (maxWaitingSec < pollingCnt) {
					procAction("ERROR", DualAuthErr.getMsg("Z10.1"));
					return;
				}
				
				$.ajax({
					url : autoCheckURL,
					dataType : "json",
					type : "post",
					cache : false,
					data : "cdtime="+ second_data.totime,
					timeout: 3000,
					success : function(data) {
						if (pollingObj == null) return;
						if (data.type == "WAIT") {							
							callback.countTime(data.resttime);
							pollingCnt = maxWaitingSec - data.resttime;
							makepollingObj();
						} else {
							if (data.type == "OTP" || data.type == "DATASIGNEDOTP" || data.type == "SERVICEOTP") {
								procAction(data.type, "성공", data.data);
								
							} else if (data.type == "STOP") {
								procAction("ERROR", DualAuthErr.getMsg("Z10.2"));
								
							} else if (data.type == "CONTINUE") {
								makepollingObj()								
							} else {
								if (pollingObj != null) {
									option.procAction("ERROR", DualAuthErr.getMsg("Z10.3"));
								}
							}
						}	
					},
					error : function (e) {
						callback.cancelCD($("#username").val());
						callback.cancelSession();
						procAction("ERROR", DualAuthErr.getMsg("000.1") + ":" + e.statusText);						
					}
				});
			}, 1000);
		};
		
		function procAction(type, msg, data) {			
			if (type == "OTP") {
				$("#user_otp").val(data);
				var result = callback.checkServiceOTP($("#username").val(), $("#user_otp").val());
				if (result.result) {
					callback.goNextCheck($("#username").val());
				} else {
					alert(DualAuthErr.getMsg(result.code));
					callback.cancelCD($("#username").val());
					callback.cancelSession();
					location.href = initURL
				}
			} else if (type == "ERROR") { //취소 및 에러이벤트  
				alert(msg);
				callback.cancelCD($("#username").val());
             	callback.cancelSession();
             	location.href = initURL
			}
		} 

		//polling 추가 
		app.addingPool = function(option) { 
			
			//corp_user_id, p_resttime, p_per_check_s, p_showRest, p_showTimeout, p_showAdded
			
			var limited_time = new Date().getTime() + (option.p_resttime ? option.p_resttime : 180000); //<%=ua.tmp_id_rest_time%>;
			var corp_user_id = option.p_corp_user_id;//"<%=session.getAttribute("loinged")%>";
			var per_check_s = option.p_per_check_s ? option.p_per_check_s : 1;
			
			
			var showRest = option.showRest ? option.showRest : function(resttime) {
				
			}
			var showTimeout = option.p_showTimeout ? option.p_showTimeout : function(resttime) {
				 alert("timeout");
			}
			var showAdded = option.p_showAdded ? option.p_showAdded : function(resttime) {
				alert("등록완료");
			}
			
			var pollingObj = null;
			var cnt = 0;
			
			var polling = function(corp_user_id) {
				pollingObj = setTimeout(function(){
					showRest(Math.floor((limited_time -  new Date().getTime())/1000))
					cnt++;
					if ((cnt % per_check_s) == 0) {
						console.log(limited_time + "//" + new Date().getTime() + (limited_time > new Date().getTime()))
						if (limited_time > new Date().getTime()) {
							$.ajax({
								 type: "POST",
								 url: contextPath+"/autoPassword/checkAdded",
								 //url: "./action/checkAdded.jsp",
								 data : "corp_user_id=" + corp_user_id ,
								 dataType : "json",
								 async : false,
								 success : function(data) {
									 if (data.result) {
										 console.log(data);
										 if (data.data.is_added) {
											 showAdded();
										 } else {
											 polling(corp_user_id);
										 } 
									 } else {
										 alert(data.msg);
									 }
								 }, 
								 error :function(data) {
									 alert("통신중에 에러 발생!!");
								 }
							})		
						} else {
							 showTimeout();
						}
					} else if (limited_time > new Date().getTime()) {
						polling(corp_user_id);
					} else {
						showTimeout();
					}
					
					
				}, 1000)
			} 	
			polling(corp_user_id);
		}

		return app;
	})(DualAuth||{},jQuery);
	
	$.fn.extend({
		dualauth : function(issecond, callback, second_data, maxWaitingSec ,url, p_initURL) {
			$($(this)).addClass("dualauth");
			DualAuth.init(issecond, callback, second_data, $(this), maxWaitingSec, url, p_initURL);
		}
	});
