<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'company.label', default: '회사')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<g:sidebar category="${category}"/>

<div id="article-create" class="content" role="main">

	<div class="content-header">
		<h3>회사 등록</h3>
	</div>

	<div class="panel panel-default clearfix">
		<div class="panel-body">
			<g:form id="article-form" url="[resource:company, uri: '/company/save']" enctype="multipart/form-data" useToken="true" class="article-form" role="form" onsubmit="return postForm()">
				<fieldset class="form">

					<div class="alert alert-info">
						<strong>2017년 7월 5일</strong>부터는 <strong style="text-decoration: underline">회사 정보 등록 및 인증을 받은 회원만</strong> 구인 게시판에 등록이 가능합니다.<br/> (7/5 이전까지는 '건너뛰기'로 회사 정보 등록 없이 작성 가능)<br/>
						별도의 인증 절차가 필요하오니 구인 게시판을 이용하실 기업 회원분들은 사전에 등록 및 인증을 받으시기를 바랍니다.
						<br/><br/>
						구인 게시판을 이용하시는 모든 회원분들께 보다 많은 혜택과 서비스를 제공하기 위함이니 적극적으로 협조해 주시면 고맙겠습니다.<br/><br/>
						<span class="required-indicator">*</span> 항목은 필수 입력 입니다.
					</div>

					<g:if test="${company?.hasErrors() || companyInfo?.hasErrors()}">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<ul>
								<g:eachError bean="${company}" var="error">
									<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
								</g:eachError>
								<g:eachError bean="${companyInfo}" var="error">
									<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
								</g:eachError>
							</ul>
						</div>
					</g:if>

					<g:render template="form"/>

					<div class="nav" role="navigation">
						<fieldset class="buttons">
							%{--<g:link uri="/recruits" class="btn btn-default btn-wide" onclick="return confirm('정말로 취소하시겠습니까?')">취소</g:link>--}%
							<g:link uri="/articles/recruit/create?skipCompanyRegister=Y" class="btn btn-default btn-wide" onclick="return confirm('2017년 7월 5일부터는 필수 등록으로 변경됩니다? 나중에 등록하시겠습니까?')">건너뛰기</g:link>
							<g:submitButton name="create" class="create btn btn-success btn-wide pull-right" value="${message(code: 'default.button.create.label', default: 'Create')}" />
						</fieldset>
					</div>
				</fieldset>
			</g:form>
		</div>
	</div>

</div>

<asset:script type="text/javascript">
	$('#jobType').change(function() {
        var $opt = $(this).find(':selected');

        $('#article-form').attr('action', contextPath+'/recruits/create')
            .submit();
    });
</asset:script>

</body>
</html>
