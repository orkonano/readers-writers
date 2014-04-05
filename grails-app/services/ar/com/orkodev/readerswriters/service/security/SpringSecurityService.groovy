package ar.com.orkodev.readerswriters.service.security

import grails.plugin.springsecurity.SpringSecurityUtils

class SpringSecurityService extends grails.plugin.springsecurity.SpringSecurityService{


    Object getCurrentUser() {
        if (!isLoggedIn()) {
            return null
        }

        String className = SpringSecurityUtils.securityConfig.userLookup.userDomainClassName
        String usernamePropName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
        grailsApplication.mainContext.userService.findById(principal.getId())
    }
}
