<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
</head>
<body>
<g:sidebar/>
<div id="create-user" class="content" role="main">
    <h3 class="content-header">회원 정보 수정</h3>
    <div class="col-md-6">
        <div class="panel panel-default panel-margin-10">
            <div class="panel-heading">
                <g:avatar avatar="${user.avatar}" size="medium" />
            </div>
            <g:form url="[resource:user, action:'update']" class="form-signup form-user panel-body" method='PUT' id='loginForm' autocomplete='off'>
                <g:hasErrors>
                    <div  class="alert alert-danger" role="alert">
                        <ul>
                            <g:eachError bean="${user}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                            <g:eachError bean="${user.avatar}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                            <g:eachError bean="${user.person}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </div>
                </g:hasErrors>

                <fieldset>

                    <g:textField name="person.email" class="form-control input-sm" placeholder="${message(code: "person.email.label", default: '이메일')}" required="" value="${user?.person?.email}"/>

                    <g:textField name="person.fullName" class="form-control input-sm" placeholder="${message(code: "person.fullName.label", default: '이름')}" required="" value="${user?.person?.fullName}"/>

                    <g:textField name="avatar.nickname" class="form-control input-sm" placeholder="${message(code: "person.nickname.label", default: '닉네임')}" required="" value="${user?.avatar?.nickname}"/>

                    <div class="checkbox">
                        <label>
                            <g:checkBox name="person.dmAllowed" value="${user?.person?.dmAllowed}"  /> <g:message code="person.dm.allow.label" default="이메일 수신 동의"/>
                        </label>
                    </div>
                </fieldset>

                <button class="btn btn-primary btn-block" type="submit"><g:message code="user.button.edit.label" default="정보 수정"/></button>
            </g:form>
        </div>
    </div>
    <div class="col-md-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h5 class="panel-header">SNS 연결</h5>
            </div>
            <div class="panel-body panel-margin sns-buttons">
                <g:if test="${user.oAuthIDs.find{ it.provider == 'facebook' }}">
                    <div id="facebook-connect-link" class="btn btn-disconnect btn-block"><i class="fa fa-chain-broken fa-fw"></i> Facebook 연결 끊기</div>
                </g:if>
                <g:else>
                    <oauth:connect provider="facebook" id="facebook-connect-link" class="btn btn-facebook btn-block"><i class="fa fa-facebook fa-fw"></i> Facebook 연결하기</oauth:connect>
                </g:else>
                %{--<g:if test="${user.oAuthIDs.find{ it.provider == 'twitter' }}">
                    <div id="facebook-connect-link" class="btn btn-disconnect btn-block"><i class="fa fa-chain-broken fa-fw"></i> Twitter 연결 끊기</div>
                </g:if>
                <g:else>
                    <oauth:connect provider="twitter" id="twitter-connect-link" class="btn btn-twitter btn-block"><i class="fa fa-twitter fa-fw"></i> Twitter 연결하기</oauth:connect>
                </g:else>--}%
                <g:if test="${user.oAuthIDs.find{ it.provider == 'google' }}">
                    <div id="facebook-connect-link" class="btn btn-disconnect btn-block"><i class="fa fa-chain-broken fa-fw"></i> Google 연결 끊기</div>
                </g:if>
                <g:else>
                    <oauth:connect provider="google" id="google-connect-link" class="btn btn-google btn-block"><i class="fa fa-google fa-fw"></i> Google 연결하기</oauth:connect>
                </g:else>
            </div>
        </div>
    </div>

</div>
</body>
</html>
