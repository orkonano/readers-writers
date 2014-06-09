package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingLike
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.NotErasedException
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

class TellingLikeService extends BaseService<TellingLike>{


    def springSecurityService
    def grailsApplication
    def tellingService
    def cacheHelper

    @Transactional
    TellingLike like(Telling tellingToLike) {
        User currentUser = springSecurityService.getCurrentUser()
        if (currentUser.id == tellingToLike.author.id) {
            throw new SameUserToCurrentException("Al autor de la obra no le puede gustar su propia obra")
        }
        TellingLike tellingLike = new TellingLike(reader: currentUser,telling: tellingToLike)
        if (!tellingLike.validate()) {
            throw new ValidationException(errors: tellingLike.errors)
        }
        tellingLike.save()
        cleanCacheInSave(tellingLike)
        return tellingLike
    }

    private void cleanCacheInSave(TellingLike tellingLike){
        cacheHelper.deleteFromCache('readers-writers', this,  "findLikeTellingByUser", [User.class, Integer.class,
                Integer.class] as Class[], [tellingLike.reader, 5, 0] as Object[])
        cacheHelper.deleteFromCache('readers-writers', this, "isLike", [Telling.class, User.class] as Class[],
                [tellingLike.telling, tellingLike.reader] as Object[])
    }

    @Transactional
    boolean stopLike(TellingLike tellingToStopLike) {
        def currentUser = springSecurityService.getCurrentUser()
        if (tellingToStopLike.reader.id != currentUser.id){
            throw new NotErasedException("No se puede eliminar al like, ya que no son el mismo reader")
        }
        def deleted = tellingToStopLike.delete() == null
        if (deleted)
            cleanCacheInSave(tellingToStopLike)
        return deleted
    }

    @Transactional(readOnly = true)
    def isLike(Telling telling){
        def currentUser = springSecurityService.getCurrentUser()
        grailsApplication.mainContext.tellingLikeService.isLike(telling, currentUser)
    }

    @Cacheable('readers-writers')
    Boolean isLike(Telling telling, User readerUser){
        def query = TellingLike.where {
            reader == readerUser && telling == telling
        }
        query = query.property('id')
        query.find()!=null
    }

    @Transactional(readOnly = true)
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
        query = query.property('telling.id')
        tellingService.findByIds(query.list(params), new Telling())
    }
}
