<%@ page import="net.okjsp.User" %>



<div class="fieldcontain ${hasErrors(bean: user, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" maxlength="15" pattern="${user.constraints.username.matches}" required="" value="${user?.username}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="password" pattern="${user.constraints.password.matches}" required="" value="${user?.password}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="user.person.label" default="Person" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="person" name="person.id" from="${net.okjsp.Person.list()}" optionKey="id" required="" value="${user?.person?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'avatar', 'error')} required">
	<label for="avatar">
		<g:message code="user.avatar.label" default="Avatar" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="avatar" name="avatar.id" from="${net.okjsp.Avatar.list()}" optionKey="id" required="" value="${user?.avatar?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="user.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${user?.enabled}" />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'accountExpired', 'error')} ">
	<label for="accountExpired">
		<g:message code="user.accountExpired.label" default="Account Expired" />
		
	</label>
	<g:checkBox name="accountExpired" value="${user?.accountExpired}" />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'accountLocked', 'error')} ">
	<label for="accountLocked">
		<g:message code="user.accountLocked.label" default="Account Locked" />
		
	</label>
	<g:checkBox name="accountLocked" value="${user?.accountLocked}" />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'passwordExpired', 'error')} ">
	<label for="passwordExpired">
		<g:message code="user.passwordExpired.label" default="Password Expired" />
		
	</label>
	<g:checkBox name="passwordExpired" value="${user?.passwordExpired}" />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'lastPasswordChanged', 'error')} required">
	<label for="lastPasswordChanged">
		<g:message code="user.lastPasswordChanged.label" default="Last Password Changed" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="lastPasswordChanged" precision="day"  value="${user?.lastPasswordChanged}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'loggedIns', 'error')} ">
	<label for="loggedIns">
		<g:message code="user.loggedIns.label" default="Logged Ins" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${user?.loggedIns?}" var="l">
    <li><g:link controller="loggedIn" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="loggedIn" action="create" params="['user.id': user?.id]">${message(code: 'default.add.label', args: [message(code: 'loggedIn.label', default: 'LoggedIn')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'createIp', 'error')} ">
	<label for="createIp">
		<g:message code="user.createIp.label" default="Create Ip" />
		
	</label>
	<g:textField name="createIp" value="${user?.createIp}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'lastUpdateIp', 'error')} ">
	<label for="lastUpdateIp">
		<g:message code="user.lastUpdateIp.label" default="Last Update Ip" />
		
	</label>
	<g:textField name="lastUpdateIp" value="${user?.lastUpdateIp}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'dateWithdraw', 'error')} ">
	<label for="dateWithdraw">
		<g:message code="user.dateWithdraw.label" default="Date Withdraw" />
		
	</label>
	<g:datePicker name="dateWithdraw" precision="day"  value="${user?.dateWithdraw}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'withdraw', 'error')} ">
	<label for="withdraw">
		<g:message code="user.withdraw.label" default="Withdraw" />
		
	</label>
	<g:checkBox name="withdraw" value="${user?.withdraw}" />

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'dateJoined', 'error')} required">
	<label for="dateJoined">
		<g:message code="user.dateJoined.label" default="Date Joined" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="dateJoined" required="" value="${user?.dateJoined}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: user, field: 'oAuthIDs', 'error')} ">
	<label for="oAuthIDs">
		<g:message code="user.oAuthIDs.label" default="O Auth ID s" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${user?.oAuthIDs?}" var="o">
    <li><g:link controller="OAuthID" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="OAuthID" action="create" params="['user.id': user?.id]">${message(code: 'default.add.label', args: [message(code: 'OAuthID.label', default: 'OAuthID')])}</g:link>
</li>
</ul>


</div>

