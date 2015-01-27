
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
        <meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>OKKY - <g:layoutTitle default="All That Developer"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'icon_57x57.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'icon_114x114.png')}">
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
  		<asset:stylesheet src="application.css"/>
        <!--[if lt IE 9]>
            <asset:javascript src="html5.js" />
        <![endif]-->

        <g:layoutHead/>
	</head>
	<body>
        <div class="layout-container">
            <div class="main ${isIndex ? 'index' : ''}">
                <g:layoutBody/>
                <div class="right-banner-wrapper">
                    <div class="google-ad">

                        <script type="text/javascript"><!--
                        google_ad_client = "ca-pub-8103607814406874";
                        /* okjspad_160x600 */
                        google_ad_slot = "6573675943";
                        google_ad_width = 160;
                        google_ad_height = 600;
                        //-->
                        </script>
                        <script type="text/javascript"
                                src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
                        </script>
                    </div>
                </div>
                <div id="footer" class="footer" role="contentinfo">
                    <g:include view="/layouts/_footer.gsp" />
                </div>
            </div>
        </div>
        <script>
            var contextPath = "${request.contextPath}", encodedURL = "${encodedURL()}";
        </script>
        <asset:javascript src="application.js" />
        <sec:ifLoggedIn>
            <asset:javascript src="apps/notification.js" />
        </sec:ifLoggedIn>
        <g:pageProperty name="page.script"/>
        <asset:deferredScripts />
        <script>
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-6707625-5', 'auto');
            ga('send', 'pageview');

        </script>
	</body>
</html>
