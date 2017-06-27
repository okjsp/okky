<%@ page import="net.okjsp.JobPositionGroup" %>



<div class="fieldcontain ${hasErrors(bean: jobPositionGroup, field: 'duties', 'error')} ">
	<label for="duties">
		<g:message code="jobPositionGroup.duties.label" default="Duties" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${jobPositionGroup?.duties?}" var="d">
    <li><g:link controller="jobPositionDuty" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="jobPositionDuty" action="create" params="['jobPositionGroup.id': jobPositionGroup?.id]">${message(code: 'default.add.label', args: [message(code: 'jobPositionDuty.label', default: 'JobPositionDuty')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: jobPositionGroup, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="jobPositionGroup.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${jobPositionGroup?.name}"/>

</div>

