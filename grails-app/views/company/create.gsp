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
						아직 회사정보가 등록되지 않았습니다. <br/><br/>
						<b>회사등록은 기재하신 연락처를 통해 관리자의 인증이 진행되며, 인증 완료 후 구인게시판 이용이 가능합니다.</b> <br/>
						모든 정보를 정확히 입력해 주시기 바랍니다.
					</div>

					<g:if test="${company.hasErrors() || companyInfo.hasErrors()}">
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
							<g:link uri="/recruits" class="btn btn-default btn-wide" onclick="return confirm('정말로 취소하시겠습니까?')"><g:message code="default.button.cancel.label" default="Cancel"/></g:link>
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
