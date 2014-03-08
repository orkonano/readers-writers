modules = {
    overrides {
        jquery {
            defaultBundle 'common'
        }
    }

    follow{
        defaultBundle 'common'
        dependsOn 'application','mustache'
        resource url:'js/follow.js'
    }

    mustache{
        dependsOn 'jquery'
        defaultBundle 'common'
        resource url:'js/jquery.mustache.js'
        resource url:'js/mustache.js'
    }

    application {
        defaultBundle 'common'
        dependsOn 'jquery'
        resource url:'js/application.js'
        resource url:'js/login_logout.js'
    }
}