package ar.com.orkodev.readerswriters.service.security

class SpringSecurityService extends grails.plugin.springsecurity.SpringSecurityService{

    def userService

    Object getCurrentUser() {
        if (!isLoggedIn()) {
            return null
        }
        userService.findById(principal.getId())
    }
}
