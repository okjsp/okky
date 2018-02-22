
<%@ page import="net.okjsp.Company" %>
<%@ page import="net.okjsp.JobType" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<g:sidebar/>
<div id="user" class="content clearfix" role="main_with_banner">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="avatar avatar-big clearfix col-sm-3 text-center">
				<a href="${request.contextPath}/company/info/${company.id}" class="avatar-photo avatar-company">
					<g:if test="${company?.logo}">
						<img src="${grailsApplication.config.grails.fileURL}/logo/${company.logo}"></a>
					</g:if>
					<g:else>
						<img src="${assetPath(src: 'company-default.png')}">
					</g:else>
				</a>
			</div>
			<div class="user-info col-sm-9">
				<div class="clearfix">
					<h2 class="pull-left">${company.name}</h2>
				</div>
				<hr/>
				<div class="clearfix">
					<h4>회사 소개</h4>
					<g:filterHtml text="${companyInfo.description}" />
				</div>
				<g:if test="${companyInfo.welfare}">
				<hr/>
				<div class="clearfix">
					<h4>복지/복리후생</h4>
					<g:filterHtml text="${companyInfo.welfare}" />
				</div>
				</g:if>
			</div>
		</div>
	</div>
	<g:if test="${recruitCount > 0}">
		<hr/>
		<h3>구인 이력</h3>
		<ul class="list-group">
		<g:each in="${recruits}" var="recruit">
			<li class="list-group-item clearfix">

				<div class="list-title-wrapper clearfix">
					<div class="list-tag clearfix">
						<span class="list-group-item-text article-id">#${recruit.article.id}</span>
						<span class="label ${recruit.jobType == JobType.valueOf('FULLTIME') ? 'label-primary' : 'label-success'}"><g:message
								code="recruit.jobType.${recruit.jobType}"/></span>
						<span>${recruit.city} ${recruit.district}</span>
					</div>

					<h5 class="list-group-item-heading ${recruit.article.category?.useEvaluate ? 'list-group-item-evaluate' : ''}">
						<g:link controller="recruit" action="show" id="${recruit.article.id}">
							<g:if test="${!recruit.article.enabled}">
								<span class="fa fa-ban" style="color:red;"></span>
							</g:if>
							${fieldValue(bean: recruit.article, field: "title")}

						</g:link>
					</h5>
				</div>

				<div class="list-summary-wrapper clearfix">
					<div class="list-group-item-summary clearfix">
						<ul>
							<li class="${recruit.article.noteCount == 0 ? 'item-icon-disabled' : ''}"><i
									class="item-icon fa fa-comment "></i> <g:shorten
									number="${recruit.article.noteCount}"/></li>
							<li class="${recruit.article.voteCount == 0 ? 'item-icon-disabled' : ''}"><i
									class="item-icon fa fa-thumbs-up"></i> <g:shorten
									number="${recruit.article.voteCount}"/></li>
							<li class="${recruit.article.viewCount == 0 ? 'item-icon-disabled' : ''}"><i
									class="item-icon fa fa-eye"></i> <g:shorten number="${recruit.article.viewCount}"/>
							</li>
						</ul>
					</div>
				</div>

				<div class="list-group-item-author clearfix">
					<div class="avatar avatar-list clearfix"><a href="${request.contextPath}/company/info/${recruit?.company?.id}" class="avatar-photo avatar-company">
						<g:if test="${recruit?.company?.logo}">
							<img src="${grailsApplication.config.grails.fileURL}/logo/${recruit?.company?.logo}">
						</g:if>
						<g:else>
							<img src="${assetPath(src: 'company-default.png')}">
						</g:else>
					</a>

						<div class="avatar-info"><a class="nickname" href="${request.contextPath}/company/info/${recruit?.company?.id}" title="${recruit?.company?.name}">${recruit?.company?.name}</a>

							<div class="date-created"><span class="timeago"
															title="${recruit.article.dateCreated}">${recruit.article.dateCreated.format('yyyy-MM-dd HH:mm:ss')}</span></div></div>
					</div>
				</div>
			</li>
		</g:each>
		</ul>
	</g:if>
	<div class="text-center">
		<g:if test="${recruitCount > 0}">
			<g:paginate uri="${request.contextPath}/company/info/${company.id}" class="pagination-sm" total="${recruitCount ?: 0}" />
		</g:if>
	</div>
</div>
</body>
</html>
