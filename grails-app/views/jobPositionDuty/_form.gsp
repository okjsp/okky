<%@ page import="net.okjsp.JobPositionDuty" %>



<div class="fieldcontain ${hasErrors(bean: jobPositionDuty, field: 'group', 'error')} required">
	<label for="group">
		<g:message code="jobPositionDuty.group.label" default="Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="group" name="group.id" from="${net.okjsp.JobPositionGroup.list()}" optionKey="id" required="" value="${jobPositionDuty?.group?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: jobPositionDuty, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="jobPositionDuty.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${jobPositionDuty?.name}"/>

</div>

