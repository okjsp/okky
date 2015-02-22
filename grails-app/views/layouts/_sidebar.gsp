<%@ page import="net.okjsp.Category" %>
<g:set var="isSub" value="${!!category}"/>
<g:set var="parentCategory" value="${category?.parent ?: category}"/>
<div class="sidebar ${isSub ? 'sidebar-category' : ''}">

    <a href="javascript://" class="sidebar-header">
        <i class="fa fa-bars sidebar-header-icon"></i>
    </a>
    
    <g:if test="${isSub}">
        <h1><div class="logo"><g:link uri="/"><asset:image src="okjsp_logo.png" alt="OKKY" title="OKKY" /></g:link></div></h1>
        <ul class="nav nav-sidebar nav-sidebar-search-wrapper">
            <li class="nav-sidebar-search"><a class="link"><i class="fa fa-search"></i></a></li>
        </ul>
    </g:if>
    <g:else>
        <h1><div class="logo"><g:link uri="/"><asset:image src="okjsp_logo.png" alt="OKKY" title="OKKY" /></g:link></div></h1>

        <form name="searchMain" class="nav-sidebar-form" action="http://www.google.com/search" onsubmit="searchMain.q.value='site:okky.kr '+searchMain.query.value;">
            <div class="input-group">
                <input type="text" name="query" class="form-control input-sm" placeholder="Search" />
                <input type="hidden" name="q" />
                <input type="hidden" name="ie" value="utf-8"/>
                <span class="input-group-btn">
                    <button class="btn btn-default btn-sm" type="submit"><i class="fa fa-search"></i></button>
                </span>
            </div>
        </form>
    </g:else>

    <div class="nav-user nav-sidebar">
        <sec:ifLoggedIn>
            <g:if test="${isSub}">
                <g:avatar size="medium" pictureOnly="true" />
            </g:if>
            <g:else>
                <g:avatar size="medium" />
            </g:else>
            <div class="nav-user-action">
                <div class="nav-user-func">
                    <a href="javascript://" id="user-func" data-toggle="popover" data-trigger="focus" tabindex="0">
                        <i id="user-func-icon" class="fa fa-cog"></i>
                    </a>
                </div>
                <div class="nav-user-func">
                    <a href="javascript://" id="user-notification" data-toggle="popover" data-trigger="focus" tabindex="0">
                        <i id="user-notification-icon" class="fa fa-bell" <g:if test="${notificationCount > 0}">style="display:none;"</g:if>></i>
                        <span id="user-notification-count" class="badge notification" <g:if test="${notificationCount == 0}">style="display:none;"</g:if>>${notificationCount}</span>
                    </a>
                </div>
            </div>
            <g:form controller="logout" method="post" style="display:none;"><g:submitButton name="logoutButton" /></g:form>

            <script id="setting-template" type="text/template">
            <div class="popover popover-notification" role="tooltip"><div class="arrow"></div>
                <h3 class="popover-title"></h3>
                <div class="popover-footer clearfix" id="user-func-popover">
                    <label href="" for="logoutButton" class="popover-btn"><i class="fa fa-sign-out"></i> 로그아웃</label>
                    <g:link uri="/user/edit" class="popover-btn"><i class="fa fa-user"></i> 정보수정</g:link>
                </div>
            </div>
            </script>

            <script id="notification-template" type="text/template">
            <div class="popover popover-notification" role="tooltip"><div class="arrow"></div>
                <h3 class="popover-title"></h3>
                <div class="popover-content" id="notification-popover"></div>
            </div>
            </script>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <g:set var="redirectUrl" value="${request.forwardURI.substring(request.contextPath.length())}" />
            <ul class="nav nav-sidebar">
                <li <g:if test="${isSub}">data-toggle="tooltip" data-container="body" title="로그인"</g:if>><g:link uri="/login/auth?redirectUrl=${encodedURL()}" class="link"><i class="fa fa-sign-in"></i> <span class="nav-sidebar-label">로그인</span></g:link></li>
                <li <g:if test="${isSub}">data-toggle="tooltip" data-container="body" title="회원가입"</g:if>><g:link uri="/user/register" class="link"><i class="fa fa-user"></i> <span class="nav-sidebar-label">회원가입</span></g:link></li>
            </ul>
        </sec:ifNotLoggedIn>
    </div>

    <ul class="nav nav-sidebar nav-main">
        <g:each in="${Category.getTopCategories()}" var="category">
            <li <g:if test="${category.code == parentCategory?.code}">class="active"</g:if> <g:if test="${isSub}">data-toggle="tooltip" data-placement="right" data-container="body" title="<g:message code="${category.labelCode}" default="${category.defaultLabel}" />"</g:if>><g:link uri="/articles/${category.code}" class="link"><i class="nav-icon ${category.iconCssNames}"></i> <span class="nav-sidebar-label nav-sidebar-category-label"><g:message code="${category.labelCode}" default="${category.defaultLabel}" /></span></g:link></li>
        </g:each>
    </ul>

    <ul class="nav nav-sidebar nav-bottom">
        <li <g:if test="${isSub}">data-toggle="tooltip" data-placement="right" data-container="body" title="Github Issues"</g:if>><g:link url="https://github.com/okjsp/okky/issues" class="link" target="_blank"><i class="fa fa-github"></i> <span class="nav-sidebar-label nav-sidebar-category-label">Github Issues</span></g:link></li>
    </ul>
</div>
<g:if test="${isSub}">
<g:set var="subCategories" value="${category.children ?: category.parent?.children}"/>
<div class="sidebar-category-nav">
<h3 class="sub-title"><g:message code="${parentCategory.labelCode}" default="${parentCategory.defaultLabel}" /></h3>
<ul class="nav">
    <li><g:link uri="/articles/${parentCategory.code}" class="link"><span class="nav-sidebar-label nav-sidebar-category-label">All</span> <span class="nav-indicator ${category.code == parentCategory.code ? 'nav-selected': ''}"><span class="nav-selected-dot"></span></span></g:link></li>
    <g:each in="${subCategories}" var="subCategory">
        <li><g:link uri="/articles/${subCategory.code}" class="link"><span class="nav-sidebar-label nav-sidebar-category-label"><g:message code="${subCategory.labelCode}" default="${subCategory.defaultLabel}" /></span> <span class="nav-indicator ${subCategory.code == category.code ? 'nav-selected': ''}"><span class="nav-selected-dot"></span></span></g:link></li>
    </g:each>
</ul>
</div>
</g:if>