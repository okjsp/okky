
<%@ page import="net.okjsp.Article" %>
<%@ page import="net.okjsp.Category" %>
<g:set var="category" value="${Category.get('event')}"/>
<html>
<head>
    <meta name="layout" content="main_with_banner">
    <g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
    <title>행사 목록</title>
</head>

<body>
    <g:sidebar category="${category}" />
    <div id="list-article" class="content scaffold-list" role="main">
        <h4>OKKY 행사</h4>

        <div class="nav" role="navigation">
            <g:form name="category-filter-form" method="get" uri="/articles/${category.code}">
                <div class="category-filter-wrapper">
                    <div class="category-filter-query pull-right">
                        <div class="input-group input-group-sm">
                            <input type="search" name="query" id="search-field" class="form-control" placeholder="검색어" value="${params.query}" />
                            <span class="input-group-btn">
                                <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                <g:if test="${params.query}">
                                    <g:link uri="/articles/${category.code}" class="btn btn-warning"><i class="fa fa-times-circle"></i> clear</g:link>
                                </g:if>
                            </span>
                        </div>
                    </div>
                    <ul class="list-sort pull-left">
                        <li><g:link uri="/articles/${category.code}" params="[sort:'id', order:'desc']" data-sort="id" data-order="desc" class="category-sort-link ${params.sort == 'id' ? 'active':''}">최신순</g:link></li>
                        <li><g:link uri="/articles/${category.code}" params="[sort:'voteCount', order:'desc']" data-sort="voteCount" data-order="desc" class="category-sort-link ${params.sort == 'voteCount' ? 'active':''}">추천순</g:link></li>
                        <li><g:link uri="/articles/${category.code}" params="[sort:'noteCount', order:'desc']" data-sort="noteCount" data-order="desc" class="category-sort-link ${params.sort == 'noteCount' ? 'active':''}">댓글순</g:link></li>
                        <li><g:link uri="/articles/${category.code}" params="[sort:'scrapCount', order:'desc']" data-sort="scrapCount" data-order="desc" class="category-sort-link ${params.sort == 'scrapCount' ? 'active':''}">스크랩순</g:link></li>
                        <li><g:link uri="/articles/${category.code}" params="[sort:'viewCount', order:'desc']" data-sort="viewCount" data-order="desc" class="category-sort-link ${params.sort == 'viewCount' ? 'active':''}">조회순</g:link></li>
                    </ul>
                    <input type="hidden" name="sort" id="category-sort-input" value="${params.sort}"/>
                    <input type="hidden" name="order" id="category-order-input" value="${params.order}"/>
                </div>
            </g:form>
        </div>

        <div class="row">
            <div class="col col-lg-4">
                <div class="panel panel-default">
                    <div><a href=""><img src="${resource(dir: 'images/temp', file: 's-1.jpg')}" style="width:100%;" /></a></div>
                    <div style="margin:10px">
                        <div style="font-weight: bold;">스마트미디어 스타트업 성공 전략</div>
                        <div style="margin-top:10px;">
                            <span class="label label-warning">유료</span>
                            <span class="label label-default">세미나/컨퍼런스</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col col-lg-4">
                <div class="panel panel-default">
                    <div><img src="${resource(dir: 'images/temp', file: 's-2.jpg')}" style="width:100%;" /></div>
                    <div style="margin:10px">
                        <div style="font-weight: bold;">스마트미디어 스타트업 성공 전략</div>
                        <div style="margin-top:10px;">
                            <span class="label label-warning">유료</span>
                            <span class="label label-default">세미나/컨퍼런스</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col col-lg-4">
                <div class="panel panel-default">
                    <div><img src="${resource(dir: 'images/temp', file: 's-3.jpg')}" style="width:100%;" /></div>
                    <div style="margin:10px">
                        <div style="font-weight: bold;">스마트미디어 스타트업 성공 전략</div>
                        <div style="margin-top:10px;">
                            <span class="label label-warning">유료</span>
                            <span class="label label-default">세미나/컨퍼런스</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>