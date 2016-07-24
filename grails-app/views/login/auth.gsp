<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
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
                <input type="text" name="username" id='username' class="username form-control input-sm" placeholder="${message(code: "springSecurity.login.username.label")}" required autofocus>
                <input type="password" name='password' class="password form-control input-sm" placeholder="${message(code: "springSecurity.login.password.label")}" required>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>> <g:message code="springSecurity.login.remember.me.label"/>
                    </label>
                </div>
                <button class="btn btn-primary btn-block" type="submit"><g:message code="springSecurity.login.button"/></button>
                <div class="signup-block">
                    <g:link controller="findUser">계정 찾기</g:link> <span class="inline-saperator">/</span> <g:link controller="user" action="register">회원 가입</g:link>
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

<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>
