
<%@ page import="net.okjsp.JobType" %>

<g:set var="evaluateClass" value="no-note"/>

<g:if test="${article.selectedNote}">
    <g:set var="evaluateClass" value="success"/>
</g:if>
<g:elseif test="${article.noteCount > 0}">
    <g:set var="evaluateClass" value="has-note"/>
</g:elseif>

<li class="list-group-item ${category?.useEvaluate ? 'list-group-item-question' : ''} list-group-${evaluateClass} clearfix">

    <div class="list-title-wrapper clearfix">
        <div class="list-tag clearfix">
            <span class="list-group-item-text article-id">#${article.id}</span>
            <g:categoryLabel category="${article.category}" />
            <g:link uri="/articles/recruit" params="['filter.jobType': article.recruit?.jobType]"><span class="label ${article.recruit?.jobType == JobType.valueOf('FULLTIME') ? 'label-success' : 'label-primary'}"><g:message
                code="recruit.jobType.${article.recruit?.jobType}"/></span></g:link>
            <span>${article.recruit?.city} ${article.recruit?.district}</span>
        </div>

        <h5 class="list-group-item-heading ${category?.useEvaluate ? 'list-group-item-evaluate' : ''}">
            <g:link controller="recruit" action="show" id="${article.id}">
                <g:if test="${!article.enabled}">
                    <span class="fa fa-ban" style="color:red;"></span>
                </g:if>
                ${fieldValue(bean: article, field: "title")}

            </g:link>
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
                        <span><g:shorten number="${article.voteCount}"/>
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
                        <g:shorten number="${article.noteCount}"/>
                    </div>
                </div>
            </div>
        </g:if>
        <g:else>
            <div class="list-group-item-summary clearfix">
                <ul>
                    <li class="${article.noteCount == 0 ? 'item-icon-disabled' : ''}"><i
                            class="item-icon fa fa-comment "></i> <g:shorten
                            number="${article.noteCount}"/></li>
                    <li class="${article.voteCount == 0 ? 'item-icon-disabled' : ''}"><i
                            class="item-icon fa fa-thumbs-up"></i> <g:shorten
                            number="${article.voteCount}"/></li>
                    <li class="${article.viewCount == 0 ? 'item-icon-disabled' : ''}"><i
                            class="item-icon fa fa-eye"></i> <g:shorten number="${article.viewCount}"/>
                    </li>
                </ul>
            </div>
        </g:else>
    </div>

    <div class="list-group-item-author clearfix">
        <div class="avatar avatar-list clearfix"><a href="${request.contextPath}/company/info/${article.recruit?.company?.id}" class="avatar-photo avatar-company">
            <g:if test="${article.recruit?.company?.logo}">
                <img src="${grailsApplication.config.grails.fileURL}/logo/${article.recruit?.company?.logo}">
            </g:if>
            <g:else>
                <img src="${assetPath(src: 'company-default.png')}">
            </g:else>
        </a>

            <div class="avatar-info"><a class="nickname" href="${request.contextPath}/company/info/${article.recruit?.company?.id}" title="${article.recruit?.company?.name}">${article.recruit?.company?.name}</a>

                <div class="date-created"><span class="timeago"
                                                title="${article.dateCreated}">${article.dateCreated.format('yyyy-MM-dd HH:mm:ss')}</span></div></div>
        </div>
    </div>
</li>
