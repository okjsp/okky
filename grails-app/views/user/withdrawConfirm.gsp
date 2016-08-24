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
    <h3 class="content-header">회원 탈퇴</h3>
    <div class="panel panel-default panel-margin-10">
        <div class="panel-body panel-body-content text-center">
            <p class="lead">회원 탈퇴시 아래의 조취가 취해 집니다.</p>
            <ul class="text-left">
                <li>회원 탈퇴 일로부터 계정과 닉네임을 포함한 계정 정보(아이디/이메일/닉네임)는 <strong>'개인 정보 보호 정책'에
                따라 60일간 보관(잠김) 되며,</strong> 60일이 경과된 후에는 모든 개인 정보는 완전히 삭제되며 더 이상 복구할 수 없게 됩니다.</li>
                <li><strong>작성된 게시물은 삭제되지 않으며, 익명처리 후 OKKY로 소유권이 귀속됩니다.</strong></li>
                <li><strong>게시물 삭제가 필요한 경우에는 관리자(<a href="mailto:admin@okky.kr">admin@okky.kr</a>)로 문의해 주시기 바랍니다.</strong></li>
            </ul>
            <g:link controller="user" action="edit" class="btn btn-default">아니오</g:link>
            <g:link controller="user" action="withdraw" class="btn btn-danger">예, 탈퇴하겠습니다.</g:link>
        </div>
    </div>
</div>
</body>
</html>
