

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'net.okjsp.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'net.okjsp.UserRole'
grails.plugin.springsecurity.authority.className = 'net.okjsp.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/dbconsole/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.logout.redirectToReferer = true
grails.plugin.springsecurity.successHandler.targetUrlParameter = 'redirectUrl'

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

// Added by the Spring Security OAuth plugin:
grails.plugin.springsecurity.oauth.domainClass = 'net.okjsp.OAuthID'

def baseURL = grails.serverURL ?: "http://localhost:8080/okky"

environments {
	development {
		oauth {
			debug = false
			providers {
				facebook {
					key = '892426937476416'
					secret = '3a607e0138aa2251156702c5234f31d7'
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

grails.mail.disabled = false
grails.mail.host = ""
grails.mail.port = 465
grails.mail.username = ""
grails.mail.password = ""
grails.mail.poolSize = 50
grails.mail.default.from="OKKY <no-reply@okky.kr>"
grails.mail.key = "key"
