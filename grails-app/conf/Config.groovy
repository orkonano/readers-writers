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

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.app.context = '/'

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

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.excludes = ['/WEB-INF/**']

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
grails.scaffolding.templates.domainSuffix = 'Instance'

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
//grails.cache.enabled = true
//grails.hibernate.cache.queries = false

grails{
    cache {
        enabled = true
        ehcache {
            ehcacheXmlLocation = 'classpath:ehcache.xml' // conf/ehcache.xml
            reloadable = false
        }
    }
}

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.resources.debug = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    }

    environments {
        development {
            appenders {
              file name:'ehcache', file:'/home/orko/dev/apps/readers-writers/logs/ehcache.log'
            }
        }
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
           'org.hibernate'
    environments {
        development {
            debug   ehcache:  'net.sf.ehcache'
        }
    }
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'ar.com.orkodev.readerswriters.domain.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'ar.com.orkodev.readerswriters.domain.UserRole'
grails.plugin.springsecurity.authority.className = 'ar.com.orkodev.readerswriters.domain.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
            '/':                                        ['permitAll'],
            '/j_spring_security_facebook_redirect':     ['permitAll'],
            '/index':                                   ['permitAll'],
            '/authors**':['permitAll'],
            '/index.gsp':                               ['permitAll'],
            '/**/js/**':                                ['permitAll'],
            '/**/css/**':                               ['permitAll'],
            '/**/images/**':                            ['permitAll'],
            '/**/favicon.ico':                          ['permitAll']
    ]
grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.roleHierarchy = '''
        ROLE_ADM > ROLE_US
'''
grails.plugin.springsecurity.rememberMe.cookieName = 'reader-writers_remember_me'
grails.plugin.springsecurity.useSessionFixationPrevention = true
//--FACEBOOK--
grails.plugin.springsecurity.facebook.taglib.button.text = ''
grails.plugin.springsecurity.facebook.domain.classname = 'ar.com.orkodev.readerswriters.domain.FacebookUser'
grails.plugin.springsecurity.facebook.filter.redirect.successHandler = 'authenticationFacebookSuccessHandler'
environments {
    development {
        grails.plugin.springsecurity.facebook.appId='368555996619672'
        grails.plugin.springsecurity.facebook.secret='8bbb1090188393a6351481bfcda374f3'
    }
    production{
        grails.plugin.springsecurity.facebook.appId='1411873405731514'
        grails.plugin.springsecurity.facebook.secret='2504f4860cfd2ae4036f17526c1a154d'
    }
}

//--BOOTSTRAP
grails.plugins.twitterbootstrap.fixtaglib = true

//--Metric
metric.lowerBound = 1
seo{
    title{
        appDisplay = 'Readers & Writers Red Social'
    }
}
