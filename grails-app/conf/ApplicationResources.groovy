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

    publish{
        defaultBundle 'common'
        dependsOn 'application','mustache','switchAction'
        resource url:'js/publish.js'
    }

    dashboard{
        defaultBundle 'common'
        dependsOn 'application','mustache'
        resource url:'js/dashboard.js'
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

    signin{
        defaultBundle 'common'
        dependsOn 'application'
        resource url:'css/signin.css'
    }

    application {
        defaultBundle 'common'
        dependsOn 'jquery','bootstrap'
        resource url:'js/application.js'
        resource url:'js/login_logout.js'
    }
}