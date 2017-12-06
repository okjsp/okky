<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title>SNS로 회원가입</title>
</head>

<body>
<g:sidebar/>

    <div id="edit-user" class="content" role="main">
        <h3 class="content-header">SNS로 회원가입</h3>
        <div class="col-md-6">
            <div class="panel panel-default panel-margin-10">
                <div class="panel-heading">
                    <h5 class="panel-header">회원 가입하기</h5>
                </div>
                <g:form action="createAccount" class="form-signup form-user panel-body" method='POST' id='loginForm' autocomplete='off'>
                    <g:hasErrors model="[userInstance: userInstance, avatarInstance: userInstance.avatar, personInstance: userInstance.person]">
                        <div  class="alert alert-danger" role="alert">
                            <ul>
                                <g:eachError bean="${userInstance}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                                <g:eachError bean="${userInstance.avatar}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                                <g:eachError bean="${userInstance.person}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                            </ul>
                        </div>
                    </g:hasErrors>

                    <fieldset>

                        <g:textField name="username" class="form-control input-sm" required="" placeholder="${message(code: "user.username.label", default: '아이디')}" value="${userInstance?.username}" />

                        <g:passwordField name="password" class="form-control input-sm" placeholder="${message(code: "user.password.label", default: '비밀번호')}" required="" value="" />

                        <g:textField name="person.email" class="form-control input-sm" placeholder="${message(code: "person.email.label", default: '이메일')}" required="" value="${userInstance?.person?.email}"/>

                        <g:textField name="person.fullName" class="form-control input-sm" placeholder="${message(code: "person.fullName.label", default: '이름')}" required="" value="${userInstance?.person?.fullName}"/>

                        <g:textField name="avatar.nickname" class="form-control input-sm" placeholder="${message(code: "person.nickname.label", default: '닉네임')}" required="" value="${userInstance?.avatar?.nickname}"/>

                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="person.dmAllow" value="true" checked="checked"> <g:message code="person.dm.allow.label" default="이메일 수신 동의"/>
                            </label>
                        </div>

                        <recaptcha:ifEnabled>
                            <recaptcha:ifFailed>
                                <div  class="alert alert-danger alert-dismissible" role="alert">
                                    <ul>
                                        <li><g:message error="CAPTCHA 인증이 실패했습니다."/></li>
                                    </ul>
                                </div>
                            </recaptcha:ifFailed>
                            <recaptcha:recaptcha theme="white"/>
                        </recaptcha:ifEnabled>

                        <button class="btn btn-primary btn-block" type="submit"><g:message code="user.button.join.label" default="회원가입"/></button>                    </fieldset>
                    </fieldset>
                    <div class="signup-block">
                        <a href="#">회원가입약관</a> <span class="inline-saperator">/</span> <a href="#">개인정보취급방침</a>
                    </div>
                </g:form>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h5 class="panel-header">기존 계정에 연결하기</h5>
                </div>

                <g:hasErrors bean="${createAccountCommand}">
                    <div class="errors">
                        <g:renderErrors bean="${createAccountCommand}" as="list"/>
                    </div>
                </g:hasErrors>

                <g:form action="linkAccount" class="form-signin form-user panel-body panel-margin" method="post" autocomplete="off">
                    <input type="text" name="username" id='username' class="username form-control input-sm" placeholder="${message(code: "springSecurity.login.username.label")}" required>
                    <input type="password" name='password' class="password form-control input-sm" placeholder="${message(code: "springSecurity.login.password.label")}" required>
                    <button class="btn btn-primary btn-block" type="submit"><g:message code="springSecurity.login.button"/></button>
                    <div class="signup-block">
                        <g:link controller="findUser">계정 찾기</g:link>
                    </div>
                </g:form>
            </div>
        </div>

    </div>
</body>
</html>
