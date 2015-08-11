
<%@ page import="net.okjsp.User" %>
<%@ page import="net.okjsp.Activity" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentType" %>
<%@ page import="net.okjsp.ActivityType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
        <g:sidebar/>
        <div id="user" class="content clearfix" role="main">
            <div class="panel panel-default">
                <div class="panel-body">
                    <g:avatar size="big" avatar="${avatar}" pictureOnly="true" class="col-sm-3 text-center" />
                    <div class="user-info col-sm-9">
                        <div class="clearfix">
                            <h2 class="pull-left">${avatar.nickname}</h2>
                            <button class="btn btn-success pull-right btn-wide disabled"><i class="fa fa-plus"></i> 팔로우</button>
                        </div>
                        <div class="user-points">
                            <div class="user-point">
                                <div class="user-point-label"><i class="fa fa-flash"></i> 활동점수</div>
                                <div class="user-point-num"><a href="#">${avatar.activityPoint}</a></div>
                            </div>
                            <div class="user-point">
                                <div class="user-point-label"><i class="fa fa-user"></i> 팔로잉</div>
                                <div class="user-point-num"><a href="#">${counts.followingCount}</a></div>
                            </div>
                            <div class="user-point">
                                <div class="user-point-label"><i class="fa fa-users"></i> 팔로워</div>
                                <div class="user-point-num"><a href="#">${counts.followerCount}</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            %{--<div class="col-sm-2 user-info-nav">
                <ul class="nav">
                    <li class="active"><g:link uri="/user/info/2">최근 활동</g:link> </li>
                    <li><g:link uri="/user/info/2/articles"><g:if test="${counts.postedCount > 0}"><span class="badge">${counts.postedCount}</span></g:if> 게시물</g:link></li>
                    <li><g:link uri="/user/info/2/solved"><g:if test="${counts.solvedCount > 0}"><span class="badge">${counts.solvedCount}</span></g:if> 질문 해결</g:link></li>
                    <li><g:link uri="/user/info/2/scrapped"><g:if test="${counts.scrappedCount > 0}"><span class="badge">${counts.scrappedCount}</span></g:if> 스크랩</g:link></li>
                </ul>
            </div>--}%
            <div class="col-sm-10 main-block-left">
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

                            <div class="list-title-wrapper clearfix">
                                <div class="list-tag clearfix">
                                    <span class="list-group-item-text article-id">#${article.id}</span>
                                    <g:categoryLabel category="${article.category}" />
                                    <g:tags tags="${article.tagString}" />
                                </div>

                                <h5 class="list-group-item-heading ${category?.useEvaluate ? 'list-group-item-evaluate' : ''}">
                                    <g:link controller="article" action="show" id="${article.id}">${fieldValue(bean: article, field: "title")}</g:link>
                                </h5>
                            </div>

                            <div class="list-summary-wrapper clearfix">
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
                                    <div class="list-group-item-summary clearfix">
                                        <ul>
                                            <li class="${article.noteCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-comment "></i> <g:shorten number="${article.noteCount}" /></li>
                                            <li class="${article.voteCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-thumbs-up"></i> <g:shorten number="${article.voteCount}" /></li>
                                            <li class="${article.viewCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-eye"></i> <g:shorten number="${article.viewCount}" /></li>
                                        </ul>
                                    </div>
                                </g:else>
                            </div>

                            <div class="list-group-item-author clearfix">
                                <g:avatar avatar="${article.displayAuthor}" size="list" dateCreated="${article.dateCreated}" />
                            </div>
                        </li>
                    </g:each>
                </ul>
                <div class="text-center">
                    <g:if test="${activitiesCount > 0}">
                        <g:paginate controller="user" action="index" id="${avatar.id}" class="pagination-sm" total="${activitiesCount ?: 0}" />
                    </g:if>
                </div>
            </div>
            <div class="col-sm-2 user-info-nav">
                <ul class="nav">
                    <li><g:link uri="/user/info/${avatar.id}">최근 활동</g:link> </li>
                    <li class="active"><g:link uri="/user/articles/${avatar.id}">게시물 <g:if test="${counts.postedCount > 0}"><span class="badge">${counts.postedCount}</span></g:if></g:link></li>
                    <li><g:link uri="/user/scraps/${avatar.id}">스크랩 <g:if test="${counts.scrappedCount > 0}"><span class="badge">${counts.scrappedCount}</span></g:if></g:link></li>
                </ul>
            </div>
        </div>
	</body>
</html>
