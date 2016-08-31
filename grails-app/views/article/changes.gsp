<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="net.okjsp.ContentType; net.okjsp.ChangeLogType; net.okjsp.AvatarPictureType; net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ChangeLog" %>
<%@ page import="net.okjsp.ContentTextType" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
    <title>Change Logs</title>
    <meta property="og:type"		content="article">
    <meta property="og:site_name" content="OKKY">
    <meta property="og:url" content="${grailsApplication.config.grails.serverURL}/changes/${params.id}">
    <meta property="og:image" content="${resource(dir: 'images', file: 'okky_logo_fb.png')}">
    %{--<meta property="og:image" content="${profileImage(size: 'fb', avatar: article.displayAuthor)}">--}%
    <meta property="og:description" content="Change Logs #${params.id}">
    <meta property="og:title" content="OKKY | Change Logs">
</head>
<body>

<g:sidebar />

<div id="article" class="content" role="main">
    <div class="nav" role="navigation">
        <h4>Change Logs #${params.id}</h4>
    </div>

    <h3>현재 버전</h3>
    <div class="panel panel-default clearfix">
        <div class="panel-body">
            <g:if test="${content.type == ContentType.ARTICLE}">
                <g:tags tags="${article.tagString}" />
                <h2 class="panel-title">${article.title}</h2>
                <hr />
            </g:if>
            <article class="content-log-text">
                <g:if test="${content.text}">
                    <g:if test="${content?.textType == ContentTextType.MD}">
                        <markdown:renderHtml text="${content.text}"/>
                    </g:if>
                    <g:elseif test="${content?.textType == ContentTextType.HTML}">
                        <g:filterHtml text="${content.text}" />
                    </g:elseif>
                    <g:else>
                        <g:lineToBr text="${content?.text}" />
                    </g:else>
                </g:if>
            </article>
            <div class="text-center expend-content" style="display: none;"><a href="javascript://" class="expend-content-btn">- 펼처 보기 -</a></div>
        </div>
    </div>
    <hr/>

    <h3>수정 이력</h3>
    <g:each in="${changeLogs}" var="changeLog">
        <div class="panel panel-default clearfix">
            <div class="panel-heading clearfix">
                <g:if test="${changeLog.type == ChangeLogType.TITLE}">
                    <span class="change-date">${changeLog.dateCreated}</span>에 아래 제목에서 변경 됨
                </g:if>
                <g:if test="${changeLog.type == ChangeLogType.CONTENT}">
                    <span class="change-date"><g:formatDate date="${changeLog.dateCreated}" format="yyyy-MM-dd HH:mm:ss"/></span> 에 아래 내용에 변경 됨
                </g:if>
                <g:if test="${changeLog.type == ChangeLogType.TAGS}">
                    <span class="change-date">${changeLog.dateCreated}</span>에 아래 태그에서 변경 됨
                </g:if>
                <span class="pull-right">#${changeLog.revision}</span>
            </div>
            <div class="panel-body">
                <g:if test="${changeLog.type == ChangeLogType.TITLE}">
                <h2 class="panel-title">${changeLog.text}</h2>
                </g:if>
                <g:if test="${changeLog.type == ChangeLogType.TAGS}">
                    <g:tags tags="${changeLog.text}" />
                </g:if>
                <g:if test="${changeLog.type == ChangeLogType.CONTENT}">
                    <article class="content-log-text">
                        <g:if test="${changeLog.text}">
                            <g:if test="${changeLog.content?.textType == ContentTextType.MD}">
                                <markdown:renderHtml text="${changeLog.text}"/>
                            </g:if>
                            <g:elseif test="${changeLog.content?.textType == ContentTextType.HTML}">
                                <g:filterHtml text="${changeLog.text}" />
                            </g:elseif>
                            <g:else>
                                <g:lineToBr text="${changeLog?.text}" />
                            </g:else>
                        </g:if>
                    </article>
                    <div class="text-center expend-content" style="display: none;"><a href="javascript://" class="expend-content-btn">- 펼처 보기 -</a></div>
                </g:if>
            </div>
        </div>
    </g:each>

    <content tag="script">
        <script>
            $(function() {
                $('.content-log-text').each(function() {
                   if($(this)[0].scrollHeight > 180) {
                       $(this).next().show();
                   }
                });

                $('.expend-content').click(function() {
                    var $content = $(this).prev();
                    console.log($content[0])
                    if($content.is('.expend')) {
                        $content.removeClass('expend');
                        $(this).find('.expend-content-btn').text('- 펼처 보기 -');
                    } else {
                        $content.addClass('expend');
                        $(this).find('.expend-content-btn').text('- 접 기 -');
                    }
                });
            });
        </script>
    </content>
</div>
</body>
</html>
