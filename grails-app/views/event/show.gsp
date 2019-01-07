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

        <h4>OKKY 행사 <span style="font-size:12px;color:#999;margin-left:20px;">행사 결제의 경우 행사 일정 <strong>3개월</strong> 전부터 접수 및 등록 가능</span></h4>
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
            <div id="content-body" class="panel-body content-body pull-left">
                <div class="content-tags">
                    <span class="list-group-item-text article-id">#499051</span>
                    <a href="/articles/event" class="list-group-item-text item-tag label label-info"><i class="fa fa-comments"></i> IT 행사</a>
                    <a href="/articles/tagged/OKKYCON" class="list-group-item-text item-tag label label-gray">OKKYCON</a> <a href="/articles/tagged/Conference" class="list-group-item-text item-tag label label-gray">Conference</a> <a href="/articles/tagged/TDD" class="list-group-item-text item-tag label label-gray">TDD</a>
                </div>
                <h2 class="panel-title">

                    [OKKYCON: 2018] The Real TDD 컨퍼런스 개최합니다!
                </h2>
                <hr>
                <article class="content-text">


                    <p>OKKY 회원 여러분, 안녕하세요!</p><p>지난 2017년 OKKYCON 1회에 이어</p><p>오는 <span style="font-weight:bold">10월 18일</span>, <span style="font-weight:bold">OKKYCON 2회 &lt;The Real TDD&gt;</span>를 개최합니다. :D</p>

                    <div class="text-center" style="margin-top:40px;">
                        <g:link uri="/event/1/request" class="btn btn-lg btn-primary btn-wide" style="padding-left:40px;padding-right:40px;">신청하기</g:link>
                    </div>
                    <p><br></p>
                    <p>지난 6월 폭발적인 반응으로 오픈한 지 수시간 만에 마감된</p><p><a href="https://okky.kr/article/476113" rel="nofollow">&lt;TDD 잘알못을 위한 돌직구 세미나&gt;</a>&nbsp;를 기억하시나요?</p><p><br></p><p>이번에는 기존 세 연사님 뿐만 아니라,</p><p>쿠팡 · 삼성SDS · 오마이호텔 등에서 실무에 TDD를 적용하고 계신 연사 세 분을 추가로 더 모셔서</p><p>더욱 탄탄하고 유익한 내용으로 준비했습니다!</p><p><br></p><p><span style="font-weight:bold">TDD에 관심을 가지고 시도/도전</span>해보았지만 방향을 잃거나 실패하신 분들께</p><p> TDD를 실제 비즈니스에 적용하여 <span style="font-weight:bold">개발 부담은 낮추고 코드 품질은 높일 수 있는</span> 노하우와 인사이트를</p><p><span style="font-weight:bold">강의 / Live Coding / 열띤 토론</span> 등으로 전달해드릴 예정입니다.</p><p><br></p><p>자세한 연사 및 세션 소개는 아래 OKKYCON 홈페이지를 통해 확인하실 수 있습니다.</p><p><br></p><p><br></p><p><img src="//file.okky.kr/images/1536032209667.PNG" style="width:100%"></p><h2 style="text-align:center"><a href="http://www.okkycon.com" style="font-weight:bold" rel="nofollow">www.okkycon.com</a> <a href="http://www.okkycon.com" target="_blank" title="새창으로 열기"><i class="fa fa-external-link"></i></a></h2><p><br></p><p><br></p><p>감사합니다.</p><p>개발자를 행복하게 해주는 커뮤니티, OKKY 운영진 드림</p>

                    <div class="text-center" style="margin-top:60px;">
                        <g:link uri="/event/1/request" class="btn btn-lg btn-primary btn-wide" style="padding-left:40px;padding-right:40px;">신청하기</g:link>
                    </div>
                </article>

            </div>

            <div id="content-function" class="content-function pull-right text-center">
                <div class="content-function-group">
                    <div class="note-evaluate-wrapper"><a href="javascript://" class="note-vote-btn" role="button" data-type="assent" data-eval="true" data-id="1510440"><i id="note-evaluate-assent-1510440" class="fa fa-angle-up note-evaluate-assent-assent" data-placement="left" data-toggle="tooltip" title="" data-original-title="추천"></i></a><div id="content-vote-count-1510440" class="content-eval-count">6</div><a href="javascript://" class="note-vote-btn" role="button" data-type="dissent" data-eval="true" data-id="1510440"><i id="note-evaluate-dissent-1510440" class="fa fa-angle-down note-evaluate-dissent-dissent" data-placement="left" data-toggle="tooltip" title="" data-original-title="반대"></i></a></div>
                </div>
                <div class="content-function-group article-scrap-wrapper">
                    <a href="javascript://" id="article-scrap-btn" data-type="scrap"><i class="fa fa-bookmark " data-toggle="tooltip" data-placement="left" title="" data-original-title="스크랩"></i></a>
                    <div id="article-scrap-count" class="content-count">2</div>
                </div>
            </div>
            <div class="content-function-cog share-btn-wrapper">
                <div class="dropdown">
                    <a href="http://www.facebook.com/sharer/sharer.php?app_id=1451111438499030&amp;sdk=joey&amp;u=https%3A%2F%2Fokky.kr%2Farticle%2F499051&amp;display=popup&amp;ref=plugin" class="btn-facebook-share"><i class="fa fa-facebook-square fa-fw" data-toggle="tooltip" data-placement="left" title="" data-original-title="페이스북 공유"></i></a>

                </div>


            </div>
        </div>
    </div>

    <div class="panel panel-default clearfix">
        <!-- List group -->
        <ul class="list-group">

            <li id="note-title" class="list-group-item note-title">
                <h3 class="panel-title">댓글 <span id="note-count">0</span></h3>
            </li>


            <li class="list-group-item note-form clearfix">


                <div class="panel-body">
                    <h5 class="text-center"><a href="/login/auth?redirectUrl=%2Farticle%2F499051" class="link">로그인</a>을 하시면 댓글을 등록할 수 있습니다.</h5>
                </div>

            </li>
        </ul>
    </div>
</div>
</body>
</html>
