
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
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-company" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="logo" title="${message(code: 'company.logo.label', default: 'Logo')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'company.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="enabled" title="${message(code: 'company.enabled.label', default: 'Enabled')}" />
					
						<th><g:message code="company.manager.label" default="Manager" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${companyList}" status="i" var="company">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${company.id}">${fieldValue(bean: company, field: "logo")}</g:link></td>
					
						<td>${fieldValue(bean: company, field: "name")}</td>
					
						<td><g:formatBoolean boolean="${company.enabled}" /></td>
					
						<td>${fieldValue(bean: company, field: "manager")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${companyCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
