<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
    <g:sidebar category="${category}"/>

        <div id="article-create" class="content" role="main">

            <div class="content-header">
                <h3>새 글 쓰기</h3>
            </div>

            <g:hasErrors bean="${article}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <ul>
                        <g:eachError bean="${article}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>

            <g:hasErrors bean="${article.content}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <ul>
                        <g:eachError bean="${article.content}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>

            <div class="panel panel-default clearfix">
                <div class="panel-heading clearfix">
                    <g:if test="${category.anonymity}">
                        <g:avatar avatar="${article.displayAuthor}" size="medium" class="pull-left" />
                    </g:if>
                    <g:else>
                        <g:avatar avatar="${article.displayAuthor}" size="medium" class="pull-left" />
                    </g:else>
                </div>
                <div class="panel-body">
                    <g:form id="article-form" url="[resource:article, uri: '/articles/'+params.code+'/save']" useToken="true" class="article-form" role="form" onsubmit="return postForm()">
                        <fieldset class="form">
                            <g:render template="form"/>

                            <div class="nav" role="navigation">
                                <fieldset class="buttons">
                                    <g:link uri="/articles/${params.code}" class="btn btn-default btn-wide" onclick="return confirm('정말로 취소하시겠습니까?')"><g:message code="default.button.cancel.label" default="Cancel"/></g:link>
                                    <g:submitButton name="create" class="create btn btn-success btn-wide pull-right" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                                </fieldset>
                            </div>
                        </fieldset>
                    </g:form>
                </div>
            </div>

        </div>

        <asset:script type="text/javascript">
            $('#category').change(function() {
                var $opt = $(this).find(':selected');
                if($opt.val() == 'recruit') {
                  location.href=contextPath+'/recruits/create';
                }
            });
        </asset:script>

	</body>
</html>
