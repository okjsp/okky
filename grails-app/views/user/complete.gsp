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
    <div class="panel panel-default panel-margin-10">
        <div class="panel-body panel-body-content text-center">
            <p class="lead"><strong>${email}</strong> 로 인증 요청 메일을 보냈습니다.<br/>
                해당 이메일을 확인 하시고, 인증 확인 링크를 눌러 주시기 바랍니다.</p>
            <p>이메일 인증이 완료 되지 않을 경우 사이트 이용에 제한이 있습니다.</p>
            <p><strong>※ 서비스에 따라 스팸으로 분류 되있을 수도 있습니다. 스팸함도 꼭 확인해 주시기 바랍니다.</strong></p>

            <g:link controller="login" action="auth" class="btn btn-primary">로그인</g:link>

            %{--혹시, 인증 요청 메일이 수신 되지 않았을 경우 <a href="#">인증 메일 다시 보내기</a>를 눌러 주시기 바랍니다.</p>--}%
            %{--<a class="btn btn-default">이메일 주소 변경</a> <a class="btn btn-primary">인증 메일 다시 보내기</a>--}%
        </div>
    </div>
</div>
</body>
</html>
