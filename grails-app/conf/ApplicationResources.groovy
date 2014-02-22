modules = {
    overrides {
        jquery {
            defaultBundle 'common'
        }
    }

    application {
        defaultBundle 'common'
        resource url:'js/application.js'
        resource url:'js/login_logout.js'
    }
}