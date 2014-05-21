package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class FollowerController extends BaseController {

    def followerService
    def springSecurityService
    def userService

    def follow(Long id) {
        User author = bindingById(id)
        if (author == null){
            notFound('userInstance.label','User')
            return
        }
        followerService.followAuthor(author)

        def result = [success:true]
        render result as JSON
    }

    def handleSameUserToCurrentException(SameUserToCurrentException ex){
        def result = [success:true, errors:[ex.message]]
        render result as JSON
        return
    }


    def handleValidationException(ValidationException ex){
        def error = []
        ex.errors.each{it -> it.getAllErrors().each {it1 -> error.add(it1)}}
        def result = [success:true, errors:error]
        render result as JSON
        return
    }

    private User bindingById(Long id){
        id != null ? userService.findById(new User(id: id)) : null
    }

    def leaveFollow(Long id) {
        User author = bindingById(id)
        if (author == null){
            notFound('userInstance.label','User')
            return
        }
        def erased =  followerService.leaveAuthor(author)

        def result = [success:erased?true:false]
        render result as JSON
    }
}
