
<%@ page import="net.net.okjsp.Banner" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="${message(code: 'banner.label', default: 'Banner')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-banner" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-banner" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

                        <g:sortableColumn property="name" title="${message(code: 'banner.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="target" title="${message(code: 'banner.target.label', default: 'Target')}" />
					
						<g:sortableColumn property="clickCount" title="${message(code: 'banner.clickCount.label', default: 'Click Count')}" />

                        <g:sortableColumn property="dateCreated" title="${message(code: 'banner.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'banner.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bannerList}" status="i" var="banner">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${banner.id}">${fieldValue(bean: banner, field: "name")}</g:link></td>

                        <td>${fieldValue(bean: banner, field: "target")}</td>
					
						<td>${fieldValue(bean: banner, field: "clickCount")}</td>
					
						<td><g:formatDate date="${banner.dateCreated}" /></td>
					
						<td><g:formatDate date="${banner.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>

			<div class="admin-pagination">
				<g:paginate total="${bannerCount ?: 0}" class="pagination-sm" />
			</div>
		</div>
	</body>
</html>
