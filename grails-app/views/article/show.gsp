<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="net.okjsp.AvatarPictureType; net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentTextType" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_with_banner">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
        <title>${article.title}</title>
        <meta property="og:type"		content="article">
        <meta property="og:site_name" content="OKKY">
        <meta property="og:url" content="${grailsApplication.config.grails.serverURL}/article/${article.id}">
        <meta property="og:image" content="${resource(dir: 'images', file: 'okky_logo_fb.png')}">
        %{--<meta property="og:image" content="${profileImage(size: 'fb', avatar: article.displayAuthor)}">--}%
        <meta property="og:description" content="${description(text:article.content?.text, length: 200)}">
        <meta property="og:title" content="OKKY | ${article.title}">
        <meta property="dable:item_id" content="${article.id}">
        <meta property="dable:author" content="${article.displayAuthor}">
        <meta property="article:section" content="${article.category.code}">
        <meta property="article:published_time" content="${article.dateCreated.format("yyyy-MM-dd'T'HH:mm:ss'Z'")}">
    </head>
	<body>

        <g:sidebar category="${article.category}"/>

        <div id="article" class="content" role="main">
            <div class="nav" role="navigation">
                <g:link class="create btn btn-success btn-wide pull-right" uri="/articles/${article.category.code}/create"><i class="fa fa-pencil"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>

                <h4><g:message code="${article.category.labelCode}" default="${article.category.defaultLabel}" /></h4>
            </div>

            <div class="panel panel-default clearfix fa-">
                <div class="panel-heading clearfix">
                    <g:avatar avatar="${article.displayAuthor}" size="medium" dateCreated="${article.dateCreated}" changeLog="${changeLogs?.find { it[2] == article.content.id}}" logType="article" class="pull-left" />
                    <div class="content-identity pull-right">
                    <div class="content-identity-count"><i class="fa fa-comment"></i> <g:formatNumber number="${article.noteCount}" /></div>
                        <div class="content-identity-count"><i class="fa fa-eye"></i> <g:formatNumber number="${article.viewCount}" /></div>
                    </div>
                </div>
                <div class="content-container clearfix">
                    <div id="content-body" class="panel-body content-body pull-left">
                        <div class="content-tags">
                            <span class="list-group-item-text article-id">#${article.id}</span>
                            <g:categoryLabel category="${article.category}" />
                            <g:tags tags="${article.tagString}" />
                        </div>
                        <h2 class="panel-title">
                            <g:if test="${!article.enabled}">
                                <span class="fa fa-ban" style="color:red;"></span>
                            </g:if>
                            ${article.title}
                        </h2>
                        <hr/>
                        <article class="content-text" itemprop="articleBody">
                        <g:if test="${article.content}">
                            <g:if test="${article.content?.textType == ContentTextType.MD}">
                                <markdown:renderHtml text="${article.content.text}"/>
                            </g:if>
                            <g:elseif test="${article.content?.textType == ContentTextType.HTML}">
                                <g:filterHtml text="${article.content.text}" />
                            </g:elseif>
                            <g:else>
                                <g:lineToBr text="${article.content?.text}" />
                            </g:else>
                        </g:if>
                        </article>

                    </div>

                    <div id="content-function" class="content-function pull-right text-center">
                        <div class="content-function-group">
                            <g:voteButtons content="${article.content}" votes="${contentVotes}" category="${article.category}" />
                        </div>
                        <div class="content-function-group article-scrap-wrapper">
                            <a href="javascript://" id="article-scrap-btn" data-type="${scrapped ? 'unscrap' : 'scrap'}"><i class="fa fa-bookmark ${scrapped ? 'note-scrapped' : ''}" data-toggle="tooltip" data-placement="left" title="${scrapped ? '스크랩 취소' : '스크랩'}"></i></a>
                            <div id="article-scrap-count" class="content-count"><g:shorten number="${article.scrapCount}" /></div>
                        </div>
                    </div>
                    <div class="content-function-cog share-btn-wrapper">
                        <div class="dropdown">
                            <a href="http://www.facebook.com/sharer/sharer.php?app_id=${grailsApplication.config.oauth.providers.facebook.key}&sdk=joey&u=${encodedURL(withDomain: true)}&display=popup&ref=plugin" class="btn-facebook-share"><i class="fa fa-facebook-square fa-fw" data-toggle="tooltip" data-placement="left" title="페이스북 공유"></i></a>
                            %{--<a href="javascript://" data-toggle="dropdown" id="sns-share-btn"><i class="fa fa-share-alt" data-toggle="tooltip" data-placement="left" title="SNS 공유"></i></a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="sns-share-btn">
                                <li><a href="javascript://"><i class="fa fa-facebook-square fa-fw"></i> Facebook</a></li>
                                <li><a href="javascript://"><i class="fa fa-google-plus-square fa-fw"></i> Google +</a></li>
                            </ul>--}%
                        </div>

                        <g:isAuthorOrAdmin author="${article.author}">
                        <div class="dropdown">
                            <g:form url="[resource:article, action:'delete']" name="article-delete-form" method="DELETE">
                                <div class="dropdown">
                                    <a href="javascript://" data-toggle="dropdown"><i class="fa fa-cog" data-toggle="tooltip" data-placement="left" title="게시물 설정"></i></a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><g:link class="edit" action="edit" resource="${article}"><i class="fa fa-edit fa-fw"></i> <g:message code="default.button.edit.label" default="Edit" /> <sec:ifAllGranted roles="ROLE_ADMIN"><span style="color:red;">(관리자권한)</span></sec:ifAllGranted></g:link></li>
                                        <g:if test="${notes.size() > 0}">
                                            <li><a href="javascript://" onclick="alert('댓글이 있는 글은 삭제하실 수 없습니다.');"><i class="fa fa-trash-o fa-fw"></i> ${message(code: 'default.button.delete.label', default: 'Delete')} <sec:ifAllGranted roles="ROLE_ADMIN"><span style="color:red;">(관리자권한)</span></sec:ifAllGranted></a></li>
                                        </g:if>
                                        <g:else>
                                            <li><a href="javascript://" id="article-delete-btn"><i class="fa fa-trash-o fa-fw"></i> ${message(code: 'default.button.delete.label', default: 'Delete')} <sec:ifAllGranted roles="ROLE_ADMIN"><span style="color:red;">(관리자권한)</span></sec:ifAllGranted></a></li>
                                        </g:else>
                                    </ul>
                                </div>
                            </g:form>
                        </div>
                        </g:isAuthorOrAdmin>
                    </div>
                </div>
            </div>

            <g:banner type="CONTENT" />

            <div class="panel panel-default clearfix">
                <!-- List group -->
                <ul class="list-group">
                <g:set var="noteTitle" value="${article.category.useSelectSolution ? '답변':'댓글'}" />
                    <li id="note-title" class="list-group-item note-title">
                        <h3 class="panel-title">${noteTitle} <span id="note-count">${article.noteCount}</span></h3>
                    </li>
                    <g:each in="${notes}" var="note">
                        <li class="list-group-item note-item clearfix" id="note-${note.id}">
                            <g:form url="[resource:note, action:'update']" method="PUT" data-id="${note.id}" class="note-update-form">
                                <div class="content-body panel-body pull-left">
                                    <g:if test="${article.category.useSelectSolution}">
                                        <g:isAuthor author="${article.author}">
                                            <g:if test="${article.selectedNote?.id == note.id}">
                                                <a href="javascript://" class="note-vote-btn note-select-btn note-selected" data-id="${note.id}" data-type="deselect">
                                                    <i class="fa fa-check"></i>
                                                    <i class="fa fa-times"></i>
                                                </a>
                                            </g:if>
                                            <g:else>
                                                <g:if test="${article.selectedNote}">
                                                    <div href="javascript://" class="note-select-indicator note-unselected">
                                                        <i class="fa fa-comment"></i>
                                                    </div>
                                                </g:if>
                                                <g:else>
                                                    <a href="javascript://" class="note-vote-btn note-select-btn note-deselected" data-id="${note.id}" data-type="select">
                                                        <i class="fa fa-check"></i>
                                                    </a>
                                                </g:else>
                                            </g:else>
                                        </g:isAuthor>
                                        <g:isNotAuthor author="${article.author}">
                                            <g:if test="${article.selectedNote?.id == note.id}">
                                                <div class="note-select-indicator note-selected">
                                                    <i class="fa fa-check"></i>
                                                </div>
                                            </g:if>
                                            <g:else>
                                                <div class="note-select-indicator note-deselected">
                                                    <i class="fa fa-comment"></i>
                                                </div>
                                            </g:else>
                                        </g:isNotAuthor>
                                    </g:if>

                                    <g:avatar avatar="${note.displayAuthor}" size="medium" dateCreated="${note.dateCreated}" changeLog="${changeLogs?.find { it[2] == note.id}}" logType="content"/>
                                    <fieldset class="form">
                                        <article id="note-text-${note.id}" class="list-group-item-text note-text">
                                            <g:if test="${note.textType == ContentTextType.MD}">
                                                <markdown:renderHtml text="${note.text}" />
                                            </g:if>
                                            <g:elseif test="${note.textType == ContentTextType.HTML}">
                                                <g:filterHtml text="${note.text}" />
                                            </g:elseif>
                                            <g:else>
                                                <g:lineToBr text="${note.text}" />
                                            </g:else>
                                        </article>
                                    </fieldset>
                                </div>

                                <div class="content-function pull-right text-center">
                                    <div class="content-function-group">
                                        <g:voteButtons content="${note}" votes="${contentVotes}" category="${article.category}" />
                                    </div>
                                </div>

                                <g:isAuthorOrAdmin author="${note.author}">
                                <div id="content-function-cog-${note.id}" class="content-function-cog">
                                    <div class="dropdown">
                                        <a href="javascript://" data-toggle="dropdown"><i class="fa fa-cog" data-toggle="tooltip" data-placement="left" title="게시물 설정"></i></a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="javascript://" class="note-edit-btn" data-id="${note.id}"><i class="fa fa-edit fa-fw"></i> <g:message code="default.button.edit.label" default="Edit" /></a><sec:ifAllGranted roles="ROLE_ADMIN"><span style="color:red;">(관리자권한)</span></sec:ifAllGranted></li>
                                            <li><a href="javascript://" class="note-delete-btn" data-id="${note.id}"><i class="fa fa-trash-o fa-fw"></i> ${message(code: 'default.button.delete.label', default: 'Delete')}</a><sec:ifAllGranted roles="ROLE_ADMIN"><span style="color:red;">(관리자권한)</span></sec:ifAllGranted></li>
                                        </ul>
                                    </div>
                                    <div class="buttons" style="display: none;">
                                        <p><a href="javascript://" class="btn btn-default btn-wide note-edit-cancel-btn">취소</a></p>
                                        <p><g:submitButton name="create" class="btn btn-success btn-wide" value="${message(code: 'note.button.edit.label', default: '저장')}" /></p>
                                    </div>
                                </div>
                                </g:isAuthorOrAdmin>
                            </g:form>

                            <g:form url="[resource:note, action:'delete']" id="note-delete-form-${note.id}" method="DELETE">
                            </g:form>
                        </li>
                    </g:each>
                    <li class="list-group-item note-form clearfix">
                        <sec:ifLoggedIn>
                            <g:form url="[resource:article, action:'addNote']" method="POST" class="note-create-form">
                                <g:if test="${notes}">
                                    <g:hiddenField name="lastNoteId" value="${notes?.last().id} "/>
                                </g:if>
                                <div class="content-body panel-body pull-left">
                                    <div style="margin-left: 5px;">
                                        <g:if test="${article.category.useSelectSolution}">
                                            <div class="note-select-indicator note-deselected">
                                                <i class="fa fa-edit"></i>
                                            </div>
                                        </g:if>
                                        <g:if test="${article.category.anonymity}">
                                            <g:avatar size="medium" avatar="[nickname: '익명', pictureType:net.okjsp.AvatarPictureType.ANONYMOUSE]"/>
                                        </g:if>
                                        <g:else>
                                            <g:avatar size="medium"/>
                                        </g:else>
                                    </div>
                                    <fieldset class="form">
                                        <g:hiddenField name="note.textType" value="HTML"/>
                                        <g:textArea name="note.text" id="note-create" placeholder="댓글 쓰기" class="form-control" />
                                    </fieldset>
                                </div>
                                <div class="content-function-cog note-submit-buttons clearfix">
                                    <p><a href="javascript://" id="note-create-cancel-btn" class="btn btn-default btn-wide" style="display: none;">취소</a></p>
                                    <g:submitButton name="create" id="btn-create-btn" class="btn btn-success btn-wide" disabled="disabled" value="${message(code: 'note.button.create.label', default: ' 등록')}" />
                                </div>
                            </g:form>
                        </sec:ifLoggedIn>
                        <sec:ifNotLoggedIn>
                           <div class="panel-body">
                                <h5 class="text-center"><g:link uri="/login/auth?redirectUrl=${encodedURL()}" class="link">로그인</g:link>을 하시면 ${noteTitle}을 등록할 수 있습니다.</h5>
                            </div>
                        </sec:ifNotLoggedIn>
                    </li>
                </ul>
            </div>
        </div>

        <g:if test="${article.category.useEvaluate}">
            <g:form url="[resource:article, action:'dissent']" name="note-dissent-form" method="PUT">
            </g:form>
            <g:form url="[resource:article, action:'assent']" name="note-assent-form" method="PUT">
            </g:form>
        </g:if>
        <g:else>
            <g:form url="[resource:article, action:'assent']" name="note-vote-form" method="PUT">
            </g:form>
        </g:else>
        <g:form url="[resource:article, action:'unvote']" name="note-unvote-form" method="PUT">
        </g:form>
        <g:form url="[resource:article, action:'selectNote']" name="note-select-form" method="PUT">
        </g:form>
        <g:form url="[resource:article, action:'deselectNote']" name="note-deselect-form" method="PUT">
        </g:form>
        <g:form url="[resource:article, action:'scrap']" name="article-scrap-form" method="POST">
        </g:form>

        <content tag="script">
            <div id="fb-root"></div>
            <asset:javascript src="apps/article.js" />
            <g:if test="${params.note}">
                <script>
                    $(function() {
                        $("html, body").animate({
                            'scrollTop' : $('#note-${params.note}').offset().top
                        }, 500, function() {
                            $('#note-${params.note}').addClass('note-alert');
                        });
                    });
                </script>
            </g:if>
            <script>
                (function(d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) return;
                js = d.createElement(s); js.id = id;
                js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&appId=1539685662974940&version=v2.0";
                fjs.parentNode.insertBefore(js, fjs);
                }(document, 'script', 'facebook-jssdk'));
            </script>
        </content>
	</body>
</html>
