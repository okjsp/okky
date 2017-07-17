
grails.plugin.springsecurity.interceptUrlMap = [
    '/':                        ['permitAll'],
    '/index':                   ['permitAll'],
    '/index.*':                 ['permitAll'],
    '/**/assets/**':            ['permitAll'],
    '/**/favicon.ico':          ['permitAll'],
    '/login/**':                ['permitAll'],
    '/logout/**':               ['permitAll'],
    '/dbconsole/**':            ['permitAll'],
    '/dbdoc/**':                ['permitAll'],
    '/*.html':                  ['permitAll'],
    '/css/*':                   ['permitAll'],
    '/js/*':                    ['permitAll'],
    '/iamges/*':                ['permitAll'],
    '/robots.txt':              ['permitAll'],

    '/articles/*':              ['permitAll'],
    '/articles/*/create':       ['ROLE_USER'],
    '/articles/*/save':         ['ROLE_USER'],
    '/articles/tagged/*':       ['permitAll'],

    '/article/*':               ['permitAll'],
    '/article/*/**':            ['ROLE_USER'],

    '/recruits':                 ['permitAll'],
    '/recruits/create':          ['ROLE_USER'],
    '/recruits/save':            ['ROLE_USER'],
    '/recruits/tagged/*':        ['permitAll'],
    '/recruits/company':         ['ROLE_USER'],
    '/recruits/company/save':     ['ROLE_USER'],
    '/recruits/company/registered':  ['ROLE_USER'],
    '/recruits/company/wait':  ['ROLE_USER'],
    '/recruits/addNote':  ['ROLE_USER'],

    '/recruit/*':               ['permitAll'],
    '/recruit/*/**':            ['ROLE_USER'],

    '/content/**':              ['ROLE_USER'],

    '/changes/**':              ['permitAll'],

    '/user/**':                 ['permitAll'],
    '/user/*/**':               ['permitAll'],
    
    '/user/info/*':             ['permitAll'],

    '/user/edit':               ['ROLE_USER'],
    '/user/update':             ['ROLE_USER'],
    '/user/withdraw':           ['ROLE_USER'],
    '/user/withdrawConfirm':    ['ROLE_USER'],
    '/user/passwordChange':     ['ROLE_USER'],
    '/user/updatePasswordChange':    ['ROLE_USER'],

    '/find/user/**':            ['permitAll'],

    '/notification':            ['ROLE_USER'],
    '/notification.*':          ['ROLE_USER'],
    '/notification/*/**':       ['ROLE_USER'],

    '/company/info/*':           ['permitAll'],

    "/company/create":          ['ROLE_USER'],
    "/company/save":            ['ROLE_USER'],
    "/company/edit":            ['ROLE_USER'],
    "/company/update":          ['ROLE_USER'],
    "/company/registered":      ['ROLE_USER'],
    "/company/updated":         ['ROLE_USER'],
    "/company/wait":            ['ROLE_USER'],

    '/oauth/**':                ['permitAll'],

    '/seq/*':                   ['permitAll'],

    '/bbs':                     ['permitAll'],

    '/intro/**':                ['permitAll'],
    
    '/flush':                   ['ROLE_USER'],

    '/file/image':              ['ROLE_USER'],

    '/upload/*/**':             ['permitAll'],

    '/banner/stats/**':         ['permitAll'],

    '/ads/**':                  ['permitAll'],

    '/_admin/**':               ['ROLE_ADMIN']
]