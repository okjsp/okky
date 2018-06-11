

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
            <g:link controller="article" action="show" id="${article.id}">
                <g:if test="${!article.enabled}">
                    <span class="fa fa-ban" style="color:red;"></span>
                </g:if>
                ${fieldValue(bean: article, field: "title")}
            </g:link>
        </h5>
    </div>

    <div class="list-summary-wrapper clearfix">
        <g:if test="${category?.useSelectSolution}">
            <div class="item-evaluate-wrapper pull-right clearfix">
                <div class="item-evaluate">
                    <div class="item-evaluate-icon">
                        <i class="item-icon fa fa-thumbs-o-${article.voteCount >= 0 ? 'up' : 'down'}"></i>
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
                    <li class="${article.voteCount == 0 ? 'item-icon-disabled' : ''}">
                        <i class="item-icon fa fa-thumbs-${article.voteCount >= 0 ? 'up' : 'down'}"></i> <g:shorten number="${article.voteCount}" />
                    </li>
                    <li class="${article.viewCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-eye"></i> <g:shorten number="${article.viewCount}" /></li>
                </ul>
            </div>
        </g:else>
    </div>

    <div class="list-group-item-author clearfix">
        <g:avatar avatar="${article.displayAuthor}" size="list" dateCreated="${article.dateCreated}" />
    </div>
</li>