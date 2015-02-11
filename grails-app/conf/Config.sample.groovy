// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.config.locations = [InterceptUrlMapConfig]

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom:          'application/atom+xml',
                      css:           'text/css',
                      csv:           'text/csv',
                      form:          'application/x-www-form-urlencoded',
                      html:          ['text/html','application/xhtml+xml'],
                      js:            'text/javascript',
                      json:          ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss:           'application/rss+xml',
                      text:          'text/plain',
                      hal:           ['application/hal+json','application/hal+xml'],
                      xml:           ['text/xml', 'application/xml']
]

environments {
    production {
        grails.app.context = "/"
    }
}

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = ''

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://localhost:8080/okky"
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://okky.kr"
        grails.app.context = "/"
        grails.assets.url = "http://okky.kr/assets/"
    }
}

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
        'org.codehaus.groovy.grails.web.pages',          // GSP
        'org.codehaus.groovy.grails.web.sitemesh',       // layouts
        'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
        'org.codehaus.groovy.grails.web.mapping',        // URL mapping
        'org.codehaus.groovy.grails.commons',            // core / classloading
        'org.codehaus.groovy.grails.plugins',            // plugins
        'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
        'org.springframework',
        'org.hibernate',
        'net.sf.ehcache.hibernate'
}



grails.plugins.twitterbootstrap.fixtaglib = true
grails.plugins.twitterbootstrap.defaultBundle = 'bundle_bootstrap'

grails.assets.less.compile = 'less4j'
grails.assets.plugin."twitter-bootstrap".excludes = ["**/*.less"]
grails.assets.plugin."twitter-bootstrap".includes = ["bootstrap.less"]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'net.okjsp.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'net.okjsp.UserRole'
grails.plugin.springsecurity.authority.className = 'net.okjsp.Role'
grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.logout.redirectToReferer = true
grails.plugin.springsecurity.successHandler.targetUrlParameter = 'redirectUrl'
//grails.plugins.springsecurity.providerNames = ['preAuthProvider','rememberMeAuthenticationProvider', 'daoAuthenticationProvider','anonymousAuthenticationProvider']


grails.plugin.springsecurity.oauth.active = true
grails.plugin.springsecurity.oauth.userLookup.oAuthIdsPropertyName = 'oAuthIDs'
grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri = '/oauth/askToLinkOrCreateAccount'
grails.plugin.springsecurity.oauth.registration.roleNames = ['ROLE_USER']

environments {
    development {
        grails.plugin.springsecurity.debug.useFilter = true
    }
}

grails.plugin.springsecurity.rememberMe.parameter = "remember_me"
grails.plugin.springsecurity.rememberMe.cookieName = "os"
grails.plugin.springsecurity.rememberMe.key = "rememberme"

grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

// Added by the Recaptcha plugin:
recaptcha.active = true
recaptcha.enabled = true
recaptcha.publicKey = ""
recaptcha.privateKey = ""
recaptcha.includeNoScript = true
recaptcha.forceLanguageInURL = false
recaptcha.useSecureAPI = true

// Added by the Spring Security OAuth plugin:
grails.plugin.springsecurity.oauth.domainClass = 'net.okjsp.OAuthID'

def baseURL = grails.serverURL ?: "http://localhost:8080/okky"

environments {
    development {
        oauth {
            debug = false
            providers {
                facebook {
                    key = '1539685662974940'
                    secret = '0c5a89652494fd7d11c217b4a4a3fb36'
                    successUri = '/oauth/facebook/success'
                    failureUri = '/oauth/facebook/failure'
                    callback = "${baseURL}/oauth/facebook/callback"
                    scope = 'email,publish_stream,offline_access'
                }
                google {
                    api = org.grails.plugin.springsecurity.oauth.GoogleApi20
                    key = '937987783004-1a2ae8nl90502og2sucupolh5g82el7e.apps.googleusercontent.com'
                    secret = 'Q1MtT5Mf-4RYkqm8Swbb0VOE'
                    successUri = '/oauth/google/success'
                    failureUri = '/oauth/google/failure'
                    callback = "${baseURL}/oauth/google/callback"
                    scope = 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email'
                }
            }
        }
    }
    production {
        oauth {
            debug = true
            providers {
                facebook {
                    key = ''
                    secret = ''
                    successUri = '/oauth/facebook/success'
                    failureUri = '/oauth/facebook/failure'
                    callback = "${baseURL}/oauth/facebook/callback"
                    scope = 'email,publish_stream,offline_access'
                }
                google {
                    api = org.grails.plugin.springsecurity.oauth.GoogleApi20
                    key = ''
                    secret = ''
                    successUri = '/oauth/google/success'
                    failureUri = '/oauth/google/failure'
                    callback = "${baseURL}/oauth/google/callback"
                    scope = 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email'
                }
            }
        }
    }
}

grails.mail.disabled = true
grails.mail.host = "localhost"
grails.mail.username = "youracount"
grails.mail.password = "yourpassword"
grails.mail.poolSize = 50
grails.mail.default.from="OKKY <no-reply@okky.kr>"
grails.mail.key = "key"

quartz {
    autoStartup = true
    jdbcStore = true
}

tomcat.deploy.username=""
tomcat.deploy.password=''
tomcat.deploy.url=""