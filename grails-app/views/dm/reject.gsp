<%@ page import="net.okjsp.Banner" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="${message(code: 'banner.label', default: 'Banner')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-banner" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
			</ul>
		</div>
		<div id="edit-banner" class="content scaffold-edit" role="main">
			<h1>수신거부 명단 업데이트</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${banner}">
			<ul class="errors" role="alert">
				<g:eachError bean="${banner}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[controller:'dm', action:'updateReject']" method="PUT" enctype="multipart/form-data">
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: banner, field: 'image', 'error')} required">
						<label for="image">
							<g:message code="dm.rejectCount.label" default="수신거부자" />
						</label>

						<span>${rejectCount} 명</span>
					</div>
					<div class="fieldcontain ${hasErrors(bean: banner, field: 'image', 'error')} required">
						<label for="rejectFile">
							<g:message code="dm.rejectFile.label" default="수신거부 명단 (CSV)" />
							<span class="required-indicator">*</span>
						</label>

						<input type="file" name="rejectFile" required="" id="rejectFile" />

						<g:if test="${banner?.image}">
							<a href="${banner?.image}" target="_blank">${banner?.image}</a>
						</g:if>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="updateReject" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
