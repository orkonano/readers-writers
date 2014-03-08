package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswiters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.Telling
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class TellingLikeController extends BaseController{

    def tellingLikeService,springSecurityService

    def like(Telling tellingToLike) {
        if (tellingToLike == null){
            notFound('tellingInstance.label','Telling')
            return
        }
        tellingLikeService.like(tellingToLike)
        def result = [success:true]
        render result as JSON
    }

    def stopTolike(Telling tellingToLike) {
        if (tellingToLike == null){
            notFound('tellingInstance.label','Telling')
            return
        }
        def erased = tellingLikeService.stopLike(tellingToLike)
        def result = [success:erased]
        render result as JSON
    }

    def handleSameUserToCurrentException(SameUserToCurrentException ex){
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
}
