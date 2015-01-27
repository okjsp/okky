<%@ page import="net.okjsp.Article" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
    <g:sidebar category="${article.category}"/>

        <div id="article-edit" class="content" role="main">

            <g:hasErrors bean="${article}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${article}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>

            <g:hasErrors bean="${article.content}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${article.content}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>

            <div class="panel panel-default clearfix">
                <div class="panel-heading clearfix">
                    <g:avatar avatar="${article.displayAuthor}" size="medium" dateCreated="${article.dateCreated}" class="pull-left" />
                    <div class="content-identity pull-right">
                        <div class="article-id">#${article.id}</div>
                        <div><i class="fa fa-eye"></i> ${article.viewCount}</div>
                    </div>
                </div>
                <div class="panel-body">
                    <g:form url="[resource:article, action:'update']" useToken="true" method="PUT" class="article-form" role="form" onsubmit="return postForm()">
                        <fieldset class="form">
                            <g:render template="form"/>

                            <div class="nav" role="navigation">
                                <fieldset class="buttons">
                                    <g:link action="show" id="${article.id}" class="btn btn-default btn-wide" onclick="return confirm('정말로 취소하시겠습니까?')"><g:message code="default.button.cancel.label" default="Cancel"/></g:link>
                                    <g:submitButton name="update" class="create btn btn-success btn-wide pull-right" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                                </fieldset>
                            </div>

                        </fieldset>
                    </g:form>
                </div>
            </div>

        </div>

	</body>
</html>
