
<%@ page import="net.okjsp.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-user" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list user">
			
				<g:if test="${user?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="user.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${user}" field="username"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.person}">
				<li class="fieldcontain">
					<span id="person-fullName-label" class="property-label"><g:message code="person.fullName.label" default="Person" /></span>
					
						<span class="property-value" aria-labelledby="person-fullName-label">${user?.person?.fullName}</span>
					
				</li>
					<li class="fieldcontain">
						<span id="person-email-label" class="property-label"><g:message code="person.email.label" default="Email" /></span>

						<span class="property-value" aria-labelledby="person-email-label">${user?.person?.email}</span>

					</li>
				</g:if>
			
				<g:if test="${user?.avatar}">
				<li class="fieldcontain">
					<span id="avatar-label" class="property-label"><g:message code="avatar.nickname.label" default="Avatar" /></span>
					
						<span class="property-value" aria-labelledby="avatar-label">${user?.avatar?.nickname}</span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.enabled}">
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="user.enabled.label" default="Enabled" /></span>
					
						<span class="property-value" aria-labelledby="enabled-label"><g:formatBoolean boolean="${user?.enabled}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.accountExpired}">
				<li class="fieldcontain">
					<span id="accountExpired-label" class="property-label"><g:message code="user.accountExpired.label" default="Account Expired" /></span>
					
						<span class="property-value" aria-labelledby="accountExpired-label"><g:formatBoolean boolean="${user?.accountExpired}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.accountLocked}">
				<li class="fieldcontain">
					<span id="accountLocked-label" class="property-label"><g:message code="user.accountLocked.label" default="Account Locked" /></span>
					
						<span class="property-value" aria-labelledby="accountLocked-label"><g:formatBoolean boolean="${user?.accountLocked}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.passwordExpired}">
				<li class="fieldcontain">
					<span id="passwordExpired-label" class="property-label"><g:message code="user.passwordExpired.label" default="Password Expired" /></span>
					
						<span class="property-value" aria-labelledby="passwordExpired-label"><g:formatBoolean boolean="${user?.passwordExpired}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.lastPasswordChanged}">
				<li class="fieldcontain">
					<span id="lastPasswordChanged-label" class="property-label"><g:message code="user.lastPasswordChanged.label" default="Last Password Changed" /></span>
					
						<span class="property-value" aria-labelledby="lastPasswordChanged-label"><g:formatDate date="${user?.lastPasswordChanged}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.createIp}">
				<li class="fieldcontain">
					<span id="createIp-label" class="property-label"><g:message code="user.createIp.label" default="Create Ip" /></span>
					
						<span class="property-value" aria-labelledby="createIp-label"><g:fieldValue bean="${user}" field="createIp"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.lastUpdateIp}">
				<li class="fieldcontain">
					<span id="lastUpdateIp-label" class="property-label"><g:message code="user.lastUpdateIp.label" default="Last Update Ip" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdateIp-label"><g:fieldValue bean="${user}" field="lastUpdateIp"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.dateWithdraw}">
				<li class="fieldcontain">
					<span id="dateWithdraw-label" class="property-label"><g:message code="user.dateWithdraw.label" default="Date Withdraw" /></span>
					
						<span class="property-value" aria-labelledby="dateWithdraw-label"><g:formatDate date="${user?.dateWithdraw}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.withdraw}">
				<li class="fieldcontain">
					<span id="withdraw-label" class="property-label"><g:message code="user.withdraw.label" default="Withdraw" /></span>
					
						<span class="property-value" aria-labelledby="withdraw-label"><g:formatBoolean boolean="${user?.withdraw}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="user.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${user?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.dateJoined}">
				<li class="fieldcontain">
					<span id="dateJoined-label" class="property-label"><g:message code="user.dateJoined.label" default="Date Joined" /></span>
					
						<span class="property-value" aria-labelledby="dateJoined-label"><g:fieldValue bean="${user}" field="dateJoined"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="user.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${user?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.oAuthIDs}">
				<li class="fieldcontain">
					<span id="oAuthIDs-label" class="property-label"><g:message code="user.oAuthIDs.label" default="O Auth ID s" /></span>
					
						<g:each in="${user.oAuthIDs}" var="o">
						<span class="property-value" aria-labelledby="oAuthIDs-label"><g:link controller="OAuthID" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>

				<g:if test="${loggedIns}">
					<li class="fieldcontain">
						<span id="loggedIns-label" class="property-label"><g:message code="user.loggedIns.label" default="Logged Ins" /></span>

						<g:each in="${loggedIns}" var="l">
							<span class="property-value" aria-labelledby="loggedIns-label">${l?.encodeAsHTML()}</span>
						</g:each>

					</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:user, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					%{--<g:link class="edit" action="edit" resource="${user}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
