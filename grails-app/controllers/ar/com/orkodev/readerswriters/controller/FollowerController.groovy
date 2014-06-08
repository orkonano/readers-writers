package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.NotErasedException
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class FollowerController extends BaseController {

    def followerService
    def springSecurityService
    def userService

    def save(Long authorId) {
        User author = bindingById(authorId)
        if (author == null){
            notFound('userInstance.label','User')
            return
        }
        Follower followerSuccess =  followerService.followAuthor(author)
        /*
            Acá debería resolverlo directamente el framework, yo le paso un link de un resources y debería resolverlo,
            pero lamentablemente no funciona igual en las gsp que en el controller
            <g:link resource="author/follower" action="delete" authorId="${userInstance.id}" id="{{followerId}}">
                 Dejar de seguir
            </g:link>
            String urlUnfollow = g.link(resource: "author/follower", authorId: author.id, id: followerSuccess.id)
         */
        String urlUnfollow = "/authors/" + author.id + "/followers/" + followerSuccess.id
        def result = [success: true, view: [urlUnfollow: urlUnfollow]]
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

    def handleNotErasedException(NotErasedException ex){
        def result = [success:true, errors:[ex.message]]
        render result as JSON
        return
    }

    private User bindingById(Long id){
        id != null ? userService.findById(new User(id: id)) : null
    }

    def delete(Long authorId, Long id) {
        User author = bindingById(authorId)
        Follower follower = followerService.findById(new Follower(id: id))
        if (!author){
            notFound('userInstance.label','User')
            return
        }
        if (!follower){
            notFound('followerInstance.label','Follower')
            return
        }
        boolean erased =  followerService.leaveAuthor(follower)
        String urlFollow = "/authors/" + author.id + "/followers"
        def result = [success: erased, view: [urlFollow: urlFollow]]
        render result as JSON
    }
}
