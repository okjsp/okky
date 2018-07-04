<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="main">
		<g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
	</head>
	<body>
		<g:sidebar/>
		<div class='body'>
			<g:if env="development">
				<g:renderException exception="${exception}" />
			</g:if>
			<g:else>
				<div class='errors'>An error has occurred</div>
				<div><g:link href="/">[Go to Main]</g:link></div>
			</g:else>
		</div>
	</body>
</html>
