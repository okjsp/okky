
<%@ page import="net.okjsp.Company" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-company" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				%{--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%
			</ul>
		</div>
		<div id="show-company" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list company">
			
				<g:if test="${company?.logo}">
				<li class="fieldcontain">
					<span id="logo-label" class="property-label"><g:message code="company.logo.label" default="Logo" /></span>
					
						<span class="property-value" aria-labelledby="logo-label"><img src="${grailsApplication.config.grails.fileURL}/logo/${fieldValue(bean: company, field: "logo")}" width="40" height="40" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${company?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="company.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${company}" field="name"/></span>
					
				</li>
				</g:if>

				<g:if test="${company?.registerNumber}">
					<li class="fieldcontain">
						<span id="registerNumber-label" class="property-label"><g:message code="company.registerNumber.label" default="사업자등록번호" /></span>

						<span class="property-value" aria-labelledby="registerNumber-label"><g:fieldValue bean="${company}" field="registerNumber"/></span>

					</li>
				</g:if>
			
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="company.enabled.label" default="Enabled" /></span>
					
						<span class="property-value" aria-labelledby="enabled-label"><strong><g:formatBoolean boolean="${company.enabled}" true="인증됨" false="인증안됨" /></strong></span>
					
				</li>

				<g:if test="${company?.locked}">
				<li class="fieldcontain">
					<span id="locked-label" class="property-label"><g:message code="company.locked.label" default="Locked" /></span>

					<span class="property-value" aria-labelledby="locked-label"><g:formatBoolean boolean="${company?.locked}" /></span>

				</li>
				</g:if>

				<g:if test="${company?.manager}">
				<li class="fieldcontain">
					<span id="manager-label" class="property-label"><g:message code="company.manager.label" default="Manager" /></span>
					
						<span class="property-value" aria-labelledby="manager-label"><g:link uri="/_admin/user/show/${user?.id}">${company?.manager?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<div id="show-company" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="['Company Info']" /></h1>
			<ol class="property-list company">

				<li class="fieldcontain">
					<span id="tel-label" class="property-label"><g:message code="companyInfo.tel.label" default="연락처" /></span>

					<span class="property-value" aria-labelledby="tel-label"><g:fieldValue bean="${companyInfo}" field="tel"/></span>

				</li>

				<li class="fieldcontain">
					<span id="employeeNumber-label" class="property-label"><g:message code="companyInfo.employeeNumber.label" default="직원 수" /></span>

					<span class="property-value" aria-labelledby="employeeNumber-label">
						<g:message code="companyInfo.employeeNumber.value_${companyInfo.employeeNumber}" />
					</span>

				</li>

				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="companyInfo.email.label" default="이메일" /></span>

					<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${companyInfo}" field="email" /></span>

				</li>

				<li class="fieldcontain">
					<span id="homepageUrl-label" class="property-label"><g:message code="companyInfo.homepageUrl.label" default="홈페이지" /></span>

					<span class="property-value" aria-labelledby="homepageUrl-label"><g:fieldValue bean="${companyInfo}" field="homepageUrl" /></span>

				</li>

				<li class="fieldcontain">
					<span id="introFile-label" class="property-label"><g:message code="companyInfo.introFile.label" default="회사소개서" /></span>

					<span class="property-value" aria-labelledby="introFile-label">
						<g:if test="${companyInfo.introFile != null}">
							<a href="${grailsApplication.config.grails.fileURL}/intro/${companyInfo.introFile.name}">${companyInfo.introFile.orgName}</a>
						</g:if>
						<g:else>
							없음
						</g:else>
					</span>

				</li>


				<li class="fieldcontain">
					<span id="introduce-label" class="property-label"><g:message code="companyInfo.introduce.label" default="회사소개" /></span>

					<span class="property-value" aria-labelledby="introduce-label">
						<g:filterHtml text="${companyInfo.description}" />
					</span>

				</li>



			</ol>
			<g:form url="[uri: '/_admin/company/delete/'+company.id]" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit"  uri="/_admin/company/edit/${company.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" controller="adminCompany" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					|
					<g:if test="${company.enabled}">
						<g:link class="delete" uri="/_admin/company/disable/${company.id}" ><g:message code="company.button.disable.label" default="Disable" /></g:link>
					</g:if>
					<g:else>
						<g:link class="save" uri="/_admin/company/enable/${company.id}" ><g:message code="company.button.enable.label" default="Enable" /></g:link>
					</g:else>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
