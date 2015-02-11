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
    <h3 class="content-header">비밀번호 변경</h3>
    <g:form action="updatePassword" method="post">
    <div class="col-sm-8 col-sm-offset-2">
        <div class="panel panel-default panel-margin-10">
            <div class="panel-body panel-body-content text-center">
                <p class="lead">변경하실 비밀번호를 입력해 주세요.</p>
                <g:if test='${flash.message}'>
                    <div  class="alert alert-danger" role="alert">
                        <ul>
                            <li class="text-left">${flash.message}</li>
                        </ul>
                    </div>
                </g:if>
                <input type="hidden" name="key" value="${key}" />
                <div class="form-group">
                    <input type="password" name="password" class="form-control form-control-inline text-center" placeholder="비밀번호" />
                </div>
                <div class="form-group">
                    <input type="password" name="passwordConfirm" class="form-control form-control-inline text-center" placeholder="비밀번호 확인" />
                </div>
                <button type="submit" class="btn btn-primary">비밀번호 변경</button> <g:link uri="/" class="btn btn-default">취소</g:link>
            </div>
        </div>
    </div>
    </g:form>
</div>
</body>
</html>
