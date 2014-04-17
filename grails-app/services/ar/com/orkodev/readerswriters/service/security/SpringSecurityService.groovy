package ar.com.orkodev.readerswriters.service.security

import ar.com.orkodev.readerswriters.domain.User

class SpringSecurityService extends grails.plugin.springsecurity.SpringSecurityService{

    def userService

    Object getCurrentUser() {
        if (!isLoggedIn()) {
            return null
        }
        userService.findById(new User(id: principal.getId()))
    }
}
