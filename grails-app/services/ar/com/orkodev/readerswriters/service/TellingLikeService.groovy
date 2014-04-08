package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingLike
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.cache.Cacheable

import java.lang.reflect.Method

class TellingLikeService{

    static transactional = true

    static springSecurityService, grailsApplication, tellingService,
            grailsCacheManager, customCacheKeyGenerator

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
        cleanCacheInSave(tellingLike)
        tellingLike
    }

    private void cleanCacheInSave(TellingLike tellingLike){
        Method method = this.getClass().getDeclaredMethod("findLikeTellingByUser", User.class, Integer.class, Integer.class)
        def key = customCacheKeyGenerator.generate(this, method, tellingLike.reader, 5, 0)
        grailsCacheManager.getCache('readers-writers').evict(key)

        method = this.getClass().getDeclaredMethod("isLike", Telling.class, User.class)
        key = customCacheKeyGenerator.generate(this, method, tellingLike.telling, tellingLike.reader)
        grailsCacheManager.getCache('readers-writers').evict(key)
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
        def deleted = tellingLikeToDelete.delete() == null
        if (deleted)
            cleanCacheInSave(tellingLikeToDelete)
        deleted
    }

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
        tellingService.findTellingsByIds(query.list(params))
    }
}
