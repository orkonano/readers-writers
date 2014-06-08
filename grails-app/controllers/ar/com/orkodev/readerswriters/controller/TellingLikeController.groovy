package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingLike
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class TellingLikeController extends BaseController{

    def tellingLikeService
    def springSecurityService
    def tellingService

    def save(Long id) {
        Telling tellingToLike = bindingById(id)
        if (tellingToLike == null){
            notFound('tellingInstance.label','Telling')
            return
        }
        TellingLike tellingLike = tellingLikeService.like(tellingToLike)
        /*
           Acá debería resolverlo directamente el framework, yo le paso un link de un resources y debería resolverlo,
           pero lamentablemente no funciona igual en las gsp que en el controller
           <g:link resource="author/follower" action="delete" authorId="${userInstance.id}" id="{{followerId}}">
                Dejar de seguir
           </g:link>
           String urlUnfollow = g.link(resource: "author/follower", authorId: author.id, id: followerSuccess.id)
        */
        String urlunlike = "/tellings/" + tellingToLike.id + "/tellinglikes/" + tellingLike.id
        def result = [ success:true, view: [urlunlike: urlunlike] ]
        render result as JSON
    }

    def stopTolike(Long id) {
        Telling tellingToLike = bindingById(id)
        if (tellingToLike == null){
            notFound('tellingInstance.label','Telling')
            return
        }
        def erased = tellingLikeService.stopLike(tellingToLike)
        def result = [success: erased]
        render result as JSON
    }

    private Telling bindingById(Long id){
        id != null ? tellingService.findById(new Telling(id: id)) : null
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
}
