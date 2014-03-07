package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswiters.exception.SameUserFollowException
import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.User
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class FollowerController extends BaseController {

    def followerService,springSecurityService

    def follow(User author) {
        if (author == null){
            notFound('userInstance.label','User')
            return
        }
        followerService.followAuthor(author)

        def result = [success:true]
        render result as JSON
    }

    def handleSameUserFollowException(SameUserFollowException ex){
        def result = [success:false,errors:[ex.message]]
        render result as JSON
        return
    }


    def handleValidationException(ValidationException ex){
        def error = []
        ex.errors.each{it -> it.getAllErrors().each {it1 -> error.add(it1)}}
        def result = [success:false,errors:error]
        render result as JSON
        return
    }

    def leaveFollow(User author) {
        if (author == null){
            notFound('userInstance.label','User')
            return
        }
        def erased =  followerService.leaveAuthor(author)

        def result = [success:erased?true:false]
        render result as JSON
    }
}
