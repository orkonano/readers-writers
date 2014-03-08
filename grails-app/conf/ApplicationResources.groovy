modules = {
    overrides {
        jquery {
            defaultBundle 'common'
        }
    }

    follow{
        defaultBundle 'common'
        dependsOn 'application','mustache','switchAction'
        resource url:'js/follow.js'
    }

    like{
        defaultBundle 'common'
        dependsOn 'application','mustache','switchAction'
        resource url:'js/like.js'
    }

    mustache{
        dependsOn 'jquery'
        defaultBundle 'common'
        resource url:'js/jquery.mustache.js'
        resource url:'js/mustache.js'
    }

    switchAction{
        defaultBundle 'common'
        resource url:'js/switchAction.js'
    }

    application {
        defaultBundle 'common'
        dependsOn 'jquery'
        resource url:'js/application.js'
        resource url:'js/login_logout.js'
    }
}