<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="net.okjsp.AvatarPictureType; net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentTextType" %>
<%@ page import="net.okjsp.Article" %>
<%@ page import="net.okjsp.Category" %>
<g:set var="category" value="${Category.get('event')}"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_with_banner">
    <title>행사 안내</title>
</head>
<body>

<g:sidebar category="${category}" />

<div id="article" class="content" role="main">
    <div class="nav" role="navigation">
        <a href="/articles/event/create" class="create btn btn-success btn-wide pull-right"><i class="fa fa-pencil"></i> 새 글 쓰기</a>

        <h4>OKKY 행사</h4>
    </div>

    <div class="panel panel-default clearfix fa-">
        <div class="panel-heading clearfix">
            <div class="avatar avatar-medium clearfix pull-left"><a href="/user/info/45597" class="avatar-photo"><img src="//www.gravatar.com/avatar/b66da5ef6099211f5db8f5f7a3b4c36b?d=identicon&amp;s=40"></a> <div class="avatar-info"><a class="nickname" href="/user/info/45597" title="OKKY">OKKY</a> <div class="activity"><span class="fa fa-flash"></span> 960</div><div class="date-created"><span class="timeago" title="2018-09-04 12:37:42.0">2018-09-04 12:37:42</span> 작성</div> </div></div>
            <div class="content-identity pull-right">
                <div class="content-identity-count"><i class="fa fa-comment"></i> 21</div>
                <div class="content-identity-count"><i class="fa fa-eye"></i> 3297</div>
            </div>
        </div>
        <div class="content-container clearfix">
            <div class="panel-body">
                <div class="content-tags">
                    <span class="list-group-item-text article-id">#499051</span>
                    <a href="/articles/event" class="list-group-item-text item-tag label label-info"><i class="fa fa-comments"></i> IT 행사</a>
                    <a href="/articles/tagged/OKKYCON" class="list-group-item-text item-tag label label-gray">OKKYCON</a> <a href="/articles/tagged/Conference" class="list-group-item-text item-tag label label-gray">Conference</a> <a href="/articles/tagged/TDD" class="list-group-item-text item-tag label label-gray">TDD</a>
                </div>
                <h2 class="panel-title">

                    [OKKYCON: 2018] The Real TDD 컨퍼런스 개최합니다!
                </h2>
                <hr>
                <div ></div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
