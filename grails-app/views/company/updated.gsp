<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'company.label', default: '회사')}" />
    <title><g:message code="default.create.label" args  ="[entityName]" /></title>
</head>
<body>
<g:sidebar/>

<div id="create-user" class="content" role="main">
    <h3 class="content-header">회사정보수정</h3>
    <div class="panel panel-default panel-margin-10">
        <div class="panel-body panel-body-content text-center">
            <p class="lead">회사정보 수정이 완료되었습니다.<br/> 정보 검토 후 입력해주신 정보로 연락 드리겠습니다. (영업일 기준 3일 내)
            </p>
            <p><strong>※ 이메일의 경우 서비스에 따라 스팸으로 분류 되있을 수도 있습니다. 스팸함도 꼭 확인해 주시기 바랍니다.</strong></p>

            <g:link uri="/user/edit" class="btn btn-primary">확인</g:link>

            %{--혹시, 인증 요청 메일이 수신 되지 않았을 경우 <a href="#">인증 메일 다시 보내기</a>를 눌러 주시기 바랍니다.</p>--}%
            %{--<a class="btn btn-default">이메일 주소 변경</a> <a class="btn btn-primary">인증 메일 다시 보내기</a>--}%
        </div>
    </div>
</div>
</body>
</html>

