<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'company.label', default: '회사')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<g:sidebar/>

<div id="create-user" class="content" role="main">
    <h3 class="content-header">회사등록</h3>
    <div class="panel panel-default panel-margin-10">
        <div class="panel-body panel-body-content text-center">
            <p class="lead">회사 인증 진행 중입니다.</p>
            <p>인증 완료후 구인게시물을 작성 하실 수 있습니다. <br/> 정보 검토 후 인증이 완료되면 입력해주신 정보로 연락 드리겠습니다.</p>

            <g:link controller="main" action="index" class="btn btn-primary">확인</g:link>

            %{--혹시, 인증 요청 메일이 수신 되지 않았을 경우 <a href="#">인증 메일 다시 보내기</a>를 눌러 주시기 바랍니다.</p>--}%
            %{--<a class="btn btn-default">이메일 주소 변경</a> <a class="btn btn-primary">인증 메일 다시 보내기</a>--}%
        </div>
    </div>
</div>
</body>
</html>
