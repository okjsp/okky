
<%@ page import="net.okjsp.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-user" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<div class="search-form">
				<form name="searchForm">
					<div class="input-group">
						<input type="search" class="form-control" name="query" value="${params.query}" placeholder="검색어 (아이디, 닉네임, 이름, 이메일주소)" />
						<span class="input-group-btn">
							<button type="submit" class="btn btn-default">검색</button>
							<g:if test="${params.query}">
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

						<g:sortableColumn property="username" action="index" title="${message(code: 'user.username.label', default: 'Username')}" />

						<th><g:message code="user.person.label" default="이름" /></th>

						<th><g:message code="user.avatar.label" default="닉네임" /></th>

						<g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled')}" />

						<g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}" />

						<g:sortableColumn property="dateCreated" title="${message(code: 'user.dateCreated.label', default: 'Date Created')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${userList}" status="i" var="user">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${user.id}">${fieldValue(bean: user, field: "username")}</g:link></td>

						<td>${fieldValue(bean: user, field: "person")}</td>

						<td>${fieldValue(bean: user, field: "avatar")}</td>

						<td><g:formatBoolean boolean="${user.enabled}" /></td>

						<td><g:formatBoolean boolean="${user.accountLocked}" /></td>

						<td>${fieldValue(bean: user, field: "dateCreated")}</td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="admin-pagination">
				<g:paginate total="${userCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
