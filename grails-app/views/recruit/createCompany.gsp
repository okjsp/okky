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
			<g:form id="article-form" url="[resource:article, uri: '/recruits/company/save']" enctype="multipart/form-data" useToken="true" class="article-form" role="form" onsubmit="return postForm()">
				<fieldset class="form">
					<g:render template="formCompany"/>

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
