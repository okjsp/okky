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
    <h3 class="content-header">계정 찾기</h3>
    <g:form action="send" method="post">
    <div class="col-sm-8 col-sm-offset-2">
        <div class="panel panel-default panel-margin-10">
            <div class="panel-body panel-body-content text-center">
                <p class="lead">이메일 주소를 입력해 주세요.</p>
                <p>회원 가입시 입력하신 이메일 주소를 입력하시면,<br/> 해당 이메일로 아이디 및 비밀번호 변경 링크를 보내드립니다.</p>
                <g:if test='${flash.message}'>
                    <div  class="alert alert-danger" role="alert">
                        <ul>
                            <li>${flash.message}</li>
                        </ul>
                    </div>
                </g:if>
                <div class="form-group">
                    <input type="email" name="email" class="form-control form-control-inline text-center" placeholder="이메일 주소" value="${email}" />
                </div>
                <button type="submit" class="btn btn-primary">계정 찾기</button> <g:link uri="/" class="btn btn-default">취소</g:link>
            </div>
        </div>
    </div>
    </g:form>
</div>
</body>
</html>
