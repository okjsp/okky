<%@ page import="net.okjsp.SpamWord" %>



<div class="fieldcontain ${hasErrors(bean: spamWord, field: 'text', 'error')} required">
    <label for="text">
        <g:message code="spamWord.text.label" default="Text"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="text" required="" value="${spamWord?.text}"/>

</div>

