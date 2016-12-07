<%@ page import="net.okjsp.Company" %>



<div class="fieldcontain ${hasErrors(bean: company, field: 'logo', 'error')} ">
	<label for="logo">
		<g:message code="company.logo.label" default="Logo" />
		
	</label>
	<g:textField name="logo" value="${company?.logo}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: company, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="company.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${company?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: company, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="company.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${company?.enabled}" />

</div>

<div class="fieldcontain ${hasErrors(bean: company, field: 'manager', 'error')} required">
	<label for="manager">
		<g:message code="company.manager.label" default="Manager" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="manager" name="manager.id" from="${net.okjsp.Person.list()}" optionKey="id" required="" value="${company?.manager?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: company, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="company.members.label" default="Members" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${company?.members?}" var="m">
    <li><g:link controller="person" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="person" action="create" params="['company.id': company?.id]">${message(code: 'default.add.label', args: [message(code: 'person.label', default: 'Person')])}</g:link>
</li>
</ul>


</div>

