
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

    '/content/**':              ['ROLE_USER'],

    '/user/**':                 ['permitAll'],
    '/user/*/**':               ['permitAll'],
    
    '/user/info/*':             ['permitAll'],

    '/user/edit':               ['ROLE_USER'],
    '/user/update':             ['ROLE_USER'],

    '/find/user/**':            ['permitAll'],

    '/notification':            ['ROLE_USER'],
    '/notification.*':          ['ROLE_USER'],
    '/notification/*/**':       ['ROLE_USER'],

    '/oauth/**':                ['permitAll'],

    '/seq/*':                  ['permitAll'],

    '/bbs':                     ['permitAll'],

    '/intro/**':                ['permitAll'],
    
    '/flush':                   ['ROLE_USER'],

    '/file/image':              ['ROLE_USER'],

    '/upload/*/**':             ['permitAll'],

    '/banner/stats/**':         ['permitAll'],

    '/_admin/**':               ['ROLE_ADMIN']
]