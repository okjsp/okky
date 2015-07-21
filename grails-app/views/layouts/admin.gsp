<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="admin.css"/>
		<asset:javascript src="application.js"/>
		<g:layoutHead/>
	</head>
	<body>
		<div id="grailsLogo" role="banner"><h1 style="vertical-align: middle;"> <g:link uri="/"><asset:image src="okjsp_logo.png" alt="OKKY" title="OKKY" style="vertical-align: middle;"/></g:link>| Administation Tools <g:if env="utest">(TEST SERVER)</g:if> </h1></div>
        <div class="nav" role="navigation">
            <ul>
				<li class="controller"><g:link controller="statistic">가입현황</g:link></li>
                <li class="controller"><g:link controller="banner">베너</g:link></li>
                <li class="controller"><g:link controller="spamWord">스팸문구</g:link></li>
                <li class="controller"><g:link controller="export">DM발송대상</g:link></li>
            </ul>
        </div>
		<g:layoutBody/>
		<div class="footer" role="contentinfo">v<g:meta name="app.version"/></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	</body>
</html>
