<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<g:sidebar/>

    <div id="create-user" class="content" role="main">
        <h3 class="content-header">회원가입</h3>
        <div class="col-md-6">
            <div class="panel panel-default panel-margin-10">
                <div class="panel-heading">
                    <h5 class="panel-header">이메일로 가입하기</h5>
                </div>
                <g:form url="[resource:user, action:'save']" class="form-signup form-user panel-body" method='POST' id='loginForm' autocomplete='off'>
                    <g:hasErrors model="[user:user, userAvatar:user.avatar, userPerson:user.person]">
                        <div  class="alert alert-danger" role="alert">
                            <ul>
                                <g:eachError bean="${user}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                                <g:eachError bean="${user.avatar}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                                <g:eachError bean="${user.person}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                            </ul>
                        </div>
                    </g:hasErrors>

                    <fieldset>

                        <g:textField name="username" class="form-control input-sm" required="" placeholder="${message(code: "user.username.label", default: '아이디')}" value="${user?.username}" />

                        <g:passwordField name="password" class="form-control input-sm" placeholder="${message(code: "user.password.label", default: '비밀번호')}" required="" value="" />

                        <g:textField name="person.email" class="form-control input-sm" placeholder="${message(code: "person.email.label", default: '이메일')}" required="" value="${user?.person?.email}"/>

                        <g:textField name="person.fullName" class="form-control input-sm" placeholder="${message(code: "person.fullName.label", default: '이름')}" required="" value="${user?.person?.fullName}"/>

                        <g:textField name="avatar.nickname" class="form-control input-sm" placeholder="${message(code: "person.nickname.label", default: '닉네임')}" required="" value="${user?.avatar?.nickname}"/>

                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="person.dmAllowed" value="true" checked="checked"> <g:message code="person.dm.allow.label" default="이메일 수신 동의"/>
                            </label>
                        </div>
                    </fieldset>

                    <div class="recaptcha-wrapper">
                        <recaptcha:ifEnabled>
                            <recaptcha:ifFailed>
                                <div  class="alert alert-danger alert-dismissible" role="alert">
                                    <ul>
                                        <li><g:message error="CAPTCHA 인증이 실패했습니다."/></li>
                                    </ul>
                                </div>
                            </recaptcha:ifFailed>
                            %{--<script src="https://www.google.com/recaptcha/api.js" async defer></script>--}%
                            %{--<div class="g-recaptcha" data-sitekey="6Lcvw_gSAAAAAH3zOofJBJOFLpmjx7Vq3hxnYIRw"></div>--}%
                            <recaptcha:recaptcha/>
                        </recaptcha:ifEnabled>
                    </div>

                    <button class="btn btn-primary btn-block" type="submit"><g:message code="user.button.join.label" default="회원가입"/></button>

                    <div class="signup-block">
                        <a href="#">회원가입약관</a> <span class="inline-saperator">/</span> <a href="#">개인정보취급방침</a>
                    </div>
                </g:form>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h5 class="panel-header">SNS로 가입하기</h5>
                </div>
                <div class="panel-body panel-margin sns-buttons">
                    <oauth:connect provider="facebook" id="facebook-connect-link" class="btn btn-facebook btn-block"><i class="fa fa-facebook fa-fw"></i> Facebook 으로 가입하기</oauth:connect>
                    %{--<oauth:connect provider="twitter" id="twitter-connect-link" class="btn btn-twitter btn-block"><i class="fa fa-twitter fa-fw"></i> Twitter 로 가입하기</oauth:connect>--}%
                    <oauth:connect provider="google" id="google-connect-link" class="btn btn-google btn-block"><i class="fa fa-google fa-fw"></i> Google 로 가입하기</oauth:connect>
                </div>
            </div>
        </div>

    </div>
	</body>
</html>
