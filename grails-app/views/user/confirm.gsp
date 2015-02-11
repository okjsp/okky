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
    <h3 class="content-header">이메일 인증</h3>
    <div class="panel panel-default panel-margin-10">
        <div class="panel-body panel-body-content text-center">
            <p class="lead">이메일 인증이 완료되었습니다</p>
            <p>이제 해당 계정 및 이메일 사용이 가능합니다. 로그인 후 이용해 주시기 바랍니다.</p>
            <g:link controller="login" action="auth" class="btn btn-primary">로그인</g:link>
        </div>
    </div>
</div>
</body>
</html>
