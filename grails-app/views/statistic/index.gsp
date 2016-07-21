<%@ page import="net.net.okjsp.SpamWord" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
</head>

<body>
<a href="#list-spamWord" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>


<div id="list-spamWord" class="content scaffold-list" role="main">
    <h1>가입현황 <strong style="float:right;">(전체 가입자 수 : ${totalCount} 명)</strong></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <th>날짜</th>
            <th>가입자 수</th>
        </tr>
        </thead>
        <tbody>
            <g:each in="${userCounts}" var="count" status="i">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${count[1]}</td>
                    <td>${count[0]}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
