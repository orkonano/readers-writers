package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingLike
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.cache.Cacheable

class TellingLikeService {

    static transactional = true

    static springSecurityService, grailsApplication

    def like(Telling tellingToLike) {
        def currentUser = springSecurityService.getCurrentUser()
        if (currentUser.id == tellingToLike.author.id) {
            throw new SameUserToCurrentException("Al autor de la obra no le puede gustar su propia obra")
        }
        def tellingLike = new TellingLike(reader: currentUser,telling: tellingToLike)
        if (!tellingLike.validate()) {
            throw new ValidationException(errors: tellingLike.errors)
        }
        tellingLike.save()
    }

    def stopLike(Telling tellingToStopLike) {
        def currentUser = springSecurityService.getCurrentUser()
        def query = TellingLike.where {
            reader == currentUser && telling == tellingToStopLike
        }
        def tellingLikeToDelete = query.find()
        if (!tellingLikeToDelete){
            return false
        }
        tellingLikeToDelete.delete() == null
    }

    def isLike(Telling telling){
        def currentUser = springSecurityService.getCurrentUser()
        def query = TellingLike.where {
            reader == currentUser && telling == telling
        }
        query.find()!=null
    }

    def findLikeTelling(Integer countLast = null, Integer offset = 0){
        def currentUser = springSecurityService.getCurrentUser()
        grailsApplication.mainContext.tellingLikeService.findLikeTellingByUser(currentUser, countLast, offset)
    }

    @Cacheable('readers-writers')
    List<Telling> findLikeTellingByUser(User user, Integer countLast = null, Integer offset = 0){
        def query = TellingLike.where {
            reader.id == user.id
        }
        def params = [sort: 'dateCreated', order: "desc", offset: offset]
        if (countLast != null){
            params.max = countLast
        }
        query.list(params).collect{it -> it.telling} as List
    }
}
