
<%@ page import="net.okjsp.Article" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="${category.labelCode}" default="${category.defaultLabel}" /></title>
	</head>
	<body>
        <g:sidebar category="${category}"/>
		<div id="list-article" class="content scaffold-list" role="main">
            <div class="nav" role="navigation">
                <g:link class="create btn btn-success btn-wide pull-right" uri="/articles/${params.code}/create"><i class="fa fa-pencil"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>

                <ul class="list-sort pull-left">
                    <li><g:link uri="/articles/${category.code}" params="[sort:'id', order:'desc']"  class="${params.sort == 'id' ? 'active':''}">최신순</g:link></li>
                    <li><g:link uri="/articles/${category.code}" params="[sort:'voteCount', order:'desc']"  class="${params.sort == 'voteCount' ? 'active':''}">추천순</g:link></li>
                    <li><g:link uri="/articles/${category.code}" params="[sort:'noteCount', order:'desc']"  class="${params.sort == 'noteCount' ? 'active':''}">댓글순</g:link></li>
                    <li><g:link uri="/articles/${category.code}" params="[sort:'scrapCount', order:'desc']"  class="${params.sort == 'scrapCount' ? 'active':''}">스크랩순</g:link></li>
                    <li><g:link uri="/articles/${category.code}" params="[sort:'viewCount', order:'desc']"  class="${params.sort == 'viewCount' ? 'active':''}">조회순</g:link></li>
                </ul>

            </div>
            <div class="panel panel-default">

                <!-- Table -->

                <ul class="list-group">

                    <g:if test="${articlesCount == 0}">
                        <li class="list-group-item clearfix">
                            <div class="panel-body text-center">
                                글이 없습니다.
                            </div>
                        </li>
                    </g:if>

                    <g:each in="${articleList}" status="i" var="article">

                        <g:set var="evaluateClass" value="no-note" />

                        <g:if test="${article.selectedNote}">
                            <g:set var="evaluateClass" value="success" />
                        </g:if>
                        <g:elseif test="${article.noteCount > 0}">
                            <g:set var="evaluateClass" value="has-note" />
                        </g:elseif>

                        <li class="list-group-item ${category?.useEvaluate ? 'list-group-item-question':''} list-group-${evaluateClass} clearfix">

                            <div class="list-summary-wrapper clearfix">
                                <div class="list-tag pull-left clearfix">
                                    <span class="list-group-item-text article-id">#${article.id}</span>
                                    <g:categoryLabel category="${article.category}" />
                                    <g:tags tags="${article.tagString}" />
                                </div>

                                <g:if test="${category?.useEvaluate}">
                                    <div class="item-evaluate-wrapper pull-right clearfix">
                                        <div class="item-evaluate">
                                            <div class="item-evaluate-icon">
                                                <i class="item-icon fa fa-thumbs-o-up"></i>
                                            </div>
                                            <div class="item-evaluate-count">
                                                <span><g:shorten number="${article.voteCount}" />
                                            </div>
                                        </div>
                                        <div class="item-evaluate item-evaluate-${evaluateClass}">
                                            <div class="item-evaluate-icon">
                                                <g:if test="${evaluateClass == 'no-note'}">
                                                    <i class="item-icon fa fa-question-circle"></i>
                                                </g:if>
                                                <g:elseif test="${evaluateClass == 'has-note'}">
                                                    <i class="item-icon fa fa-exclamation-circle"></i>
                                                </g:elseif>
                                                <g:elseif test="${evaluateClass == 'success'}">
                                                    <i class="item-icon fa fa-check-circle"></i>
                                                </g:elseif>
                                            </div>
                                            <div class="item-evaluate-count">
                                                <g:shorten number="${article.noteCount}" />
                                            </div>
                                        </div>
                                    </div>
                                </g:if>
                                <g:else>
                                    <div class="list-group-item-summary pull-right text-right clearfix">
                                        <ul>
                                            <li><i class="item-icon fa fa-comment"></i> <g:shorten number="${article.noteCount}" /></li>
                                            <li><i class="item-icon fa fa-thumbs-up"></i> <g:shorten number="${article.voteCount}" /></li>
                                            <li><i class="item-icon fa fa-eye"></i> <g:shorten number="${article.viewCount}" /></li>
                                        </ul>
                                    </div>
                                </g:else>
                            </div>
                            <div class="list-title-wrapper clearfix">

                                <h5 class="list-group-item-heading ${category?.useEvaluate ? 'list-group-item-evaluate' : ''}">
                                    <g:link controller="article" action="show" id="${article.id}">${fieldValue(bean: article, field: "title")}</g:link>
                                    <div class="list-group-item-author pull-right clearfix">
                                        <g:avatar avatar="${article.displayAuthor}" size="small" dateCreated="${article.dateCreated}" />
                                    </div>
                                </h5>

                            </div>
                        </li>
                    </g:each>
                </ul>
            </div>
            <div class="text-center">
                <g:if test="${articlesCount > 0}">
                    <g:paginate uri="/articles/${category.code}" class="pagination-sm" total="${articlesCount ?: 0}" />
                </g:if>
            </div>
		</div>
	</body>
</html>
