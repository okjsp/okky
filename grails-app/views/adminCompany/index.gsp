
<%@ page import="net.okjsp.Company" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-company" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				%{--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%
			</ul>
		</div>
		<div id="list-company" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<div class="search-form">
				<form name="searchForm">
					<div class="input-group">
						<input type="search" class="form-control" name="where" value="${params.where}" placeholder="검색어 (회사명, 사업자등록번호)" />
						<span class="input-group-btn">
							<button type="submit" class="btn btn-default">검색</button>
							<g:if test="${params.where}">
								<g:link action="index" class="btn btn-warning">clear</g:link>
							</g:if>
						</span>
					</div>
				</form>
			</div>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="logo" title="${message(code: 'company.logo.label', default: 'Logo')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'company.name.label', default: 'Name')}" />
					
						<th><g:message code="company.manager.label" default="Manager" /></th>

						<g:sortableColumn property="enabled" title="${message(code: 'company.enabled.label', default: 'Enabled')}" />

						<g:sortableColumn property="locked" title="${message(code: 'company.locked.label', default: 'Locked')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${companyList}" status="i" var="company">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${company.id}"><img src="${grailsApplication.config.grails.fileURL}/logo/${fieldValue(bean: company, field: "logo")}" width="20" height="20" /></g:link></td>

						<td><g:link action="show" id="${company.id}">${fieldValue(bean: company, field: "name")}</g:link></td>

						<td>${fieldValue(bean: company, field: "manager")}</td>

						<td><strong><g:formatBoolean boolean="${company.enabled}" true="인증됨" false="인증안됨" /></strong></td>

						<td><strong><g:formatBoolean boolean="${company.locked}" true="블록됨" false="정상" /></strong></td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="admin-pagination">
				<g:paginate total="${companyCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
