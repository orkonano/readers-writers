package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.cache.CacheHelper
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.NotErasedException
import ar.com.orkodev.readerswriters.exception.NotPublishedException
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.gorm.DetachedCriteria
import grails.plugin.cache.Cacheable
import org.springframework.beans.factory.annotation.Autowired

class TellingService extends BaseService<Telling>{

    static transactional = true

    def springSecurityService
    def grailsApplication
    @Autowired
    private CacheHelper cacheHelper

    def save(Telling tellingToSave) {
        tellingToSave.author = springSecurityService.getCurrentUser()
        tellingToSave.validate()
        if (tellingToSave.hasErrors()) {
            throw new ValidationException(errors: tellingToSave.errors)
        }
        tellingToSave.save()
        cleanCacheInSave(tellingToSave)
    }

    private void cleanCacheInSave(Telling telling){
        //borro los listados del usuario donde se mantiene como cache
        cacheHelper.deleteFromCache('readers-writers', this, "listAllAuthorUserTelling", [User.class, Integer.class]
                                    as Class[], [telling.author, 10] as Object[])
        cacheHelper.deleteFromCache('readers-writers', this, "findTellingByAuthor", [User.class, Integer.class,
                                    Integer.class] as Class[], [telling.author, 5, 0] as Object[])
    }

    def delete(Telling tellingToErase) {
        if (!tellingToErase.isEliminable()){
            throw new NotErasedException("Debe estar en estado borrador para eliminarse")
        }else{
            tellingToErase.state = Telling.ERASED
            tellingToErase.save()
            cleanCacheInSave(tellingToErase)
        }
    }

    def publish(Telling tellingToPublish){
        if (!tellingToPublish.isPublicable()){
            throw new NotPublishedException("Debe estar en estado borrador para publicarse")
        }else{
            tellingToPublish.state = Telling.PUBLISHED
            tellingToPublish.save()
            cleanCacheInSave(tellingToPublish)
        }
    }

    def listPublished(Telling tellingSearch, Integer max = 15, Integer offset = 0){
        def currentUser = springSecurityService.getCurrentUser();
        def query = Telling.where {
            state == Telling.PUBLISHED && author != currentUser
        }
        if ( tellingSearch.narrativeGenre?.id != null ){
            query = query.where {
                narrativeGenre.id == tellingSearch.narrativeGenre.id
            }
        }
        if ( tellingSearch.tellingType?.id != null ){
            query = query.where {
                tellingType.id == tellingSearch.tellingType.id
            }
        }
        if ( tellingSearch.author?.id != null ){
            query = query.where {
                author == tellingSearch.author
            }
        }
        def count = query.count()
        List<Telling> tellingResult = loadTelling(query, [max: max?:15, offset: offset?:0]);
        [tellingResult, count]
    }

    List<Telling> findCurrentUserTelling(Integer count = null, Integer offset = 0) {
        def currentUser = springSecurityService.getCurrentUser()
        grailsApplication.mainContext.tellingService.findTellingByAuthor(currentUser, count, offset)
    }

    @Cacheable('readers-writers')
    List<Telling> findTellingByAuthor(User authorParams, Integer count = null, Integer offset = 0){
        def query = Telling.where {
            author.id == authorParams.id && state != Telling.ERASED
        }
        def params = [sort: 'dateCreated', order: "desc", offset: offset]
//        if (count != null){
//            params.max = count
//        }
        loadTelling(query, params)
    }

    List<Telling> loadTelling(DetachedCriteria query, params){
        query = query.property('id')
        findByIds(query.list(params), new Telling());
    }

    def listAllAuthorUserTelling(User userLogin, Integer limit){
        def query = Telling.where {
            author == userLogin && state != Telling.ERASED
        }
        def count = query.count()
        List<Telling> tellingResult = loadTelling(query, [max: limit]);
        [tellingResult, count]
    }
}
