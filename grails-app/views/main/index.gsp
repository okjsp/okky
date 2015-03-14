<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>All That Developer</title>
        <meta name="google-site-verification" content="DkGncyJVqYFVekHithdbYnKgklkyKVwruPZ18WUDjr0" />
	</head>
	<body>
    <g:sidebar/>

    <g:banner type="MAIN" />
    
    <div id="index" class="content scaffold-list clearfix" role="main">
        <div class="col-sm-12 main-block-all">
            <h4 class="main-header"><i class="fa fa-flag"></i> Editor's Choice</h4>
            <div class="panel panel-default">

                <!-- Table -->

                <ul class="list-group">
                    <g:each in="${choiceArticles}" var="article">

                        <g:set var="evaluateClass" value="no-note" />

                        <g:if test="${article.selectedNote}">
                            <g:set var="evaluateClass" value="success" />
                        </g:if>
                        <g:elseif test="${article.noteCount > 0}">
                            <g:set var="evaluateClass" value="has-note" />
                        </g:elseif>

                        <li class="list-group-item ${category?.useEvaluate ? 'list-group-item-question':''} list-group-${evaluateClass} clearfix">

                            <div class="list-summary-wrapper clearfix">
                                <div class="pull-left clearfix">
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
                                            <li><i class="item-icon fa fa-bookmark"></i> <g:shorten number="${article.scrapCount}" /></li>
                                            <li><i class="item-icon fa fa-eye"></i> <g:shorten number="${article.viewCount}" /></li>
                                        </ul>
                                    </div>
                                </g:else>
                            </div>
                            <div class="list-title-wrapper">
                                <h5 class="list-group-item-heading ${category?.useEvaluate ? 'list-group-item-evaluate' : ''} pull-left"><g:link controller="article" action="show" id="${article.id}">${fieldValue(bean: article, field: "title")}</g:link></h5>
                                <div class="list-group-item-author pull-right clearfix">
                                    <g:avatar avatar="${article.displayAuthor}" size="small" dateCreated="${article.dateCreated}" />
                                </div>
                            </div>
                        </li>
                    </g:each>
                </ul>
            </div>
        </div>

        <div class="col-sm-6 main-block-left">
            <g:render template="article_block" model="[articleBlock:articleBlocks[0]]" />
            <g:render template="article_block" model="[articleBlock:articleBlocks[2]]" />
        </div>
        <div class="col-sm-6 main-block-right">
            <g:render template="article_block" model="[articleBlock:articleBlocks[1]]" />
            <g:render template="article_block" model="[articleBlock:articleBlocks[3]]" />
        </div>
    </div>
	</body>
</html>
