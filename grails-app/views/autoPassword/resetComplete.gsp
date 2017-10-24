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
    <h3 class="content-header">AutoPassword™ 재설정</h3>
    <div class="panel panel-default panel-margin-10">
        <div class="panel-body panel-body-content text-center">
            <p class="lead">회원님의 이메일로 요청하신 계정정보를 보냈습니다.</p>
            <p>해당 이메일을 확인 하시고, AutoPassword™ 재설정이 필요하신 경우 해당 이메일을 통해 변경 가능합니다..</p>
            <p><strong>※ 서비스에 따라 스팸으로 분류 되있을 수도 있습니다. 스팸함도 꼭 확인해 주시기 바랍니다.</strong></p>

            <g:link controller="login" action="auth" class="btn btn-primary">로그인</g:link>

        </div>
    </div>
</div>
</body>
</html>
