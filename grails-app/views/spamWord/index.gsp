<%@ page import="net.net.okjsp.SpamWord" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'spamWord.label', default: 'SpamWord')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-spamWord" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-spamWord" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div>
        <ul class="spam-word-list clearfix">
        <g:each in="${spamWordList}" status="i" var="spamWord">

                <li><g:link action="show" id="${spamWord.id}">${fieldValue(bean: spamWord, field: "text")}</g:link></li>

        </g:each>
        </ul>
    </div>

    <div class="admin-pagination">
        <g:paginate total="${spamWordCount ?: 0}" class="pagination-sm"/>
    </div>
</div>
</body>
</html>
