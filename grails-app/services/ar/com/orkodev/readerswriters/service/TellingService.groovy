package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.exception.NotErasedException
import ar.com.orkodev.readerswriters.exception.NotPublishedException
import ar.com.orkodev.readerswriters.exception.ValidationException

class TellingService {

    static transactional = true

    def springSecurityService

    def save(Telling tellingToSave) {
        tellingToSave.author = springSecurityService.getCurrentUser()
        tellingToSave.validate()
        if (tellingToSave.hasErrors()) {
            throw new ValidationException(errors: tellingToSave.errors)
        }
        tellingToSave.save()
    }

    def delete(Telling tellingToErase) {
        if (!tellingToErase.isEliminable()){
            throw new NotErasedException("Debe estar en estado borrador para eliminarse")
        }else{
            tellingToErase.state = Telling.ERASED
            tellingToErase.save()
        }
    }

    def publish(Telling tellingToPublish){
        if (!tellingToPublish.isPublicable()){
            throw new NotPublishedException("Debe estar en estado borrador para publicarse")
        }else{
            tellingToPublish.state = Telling.PUBLISHED
            tellingToPublish.save()
        }
    }

    def listPublished(Telling tellingSearch, Integer max,Integer offset){
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
        def resultList = query.list(offset: offset?:0,max: max?:15)
        return [resultList, count]
    }

    def findCurrentUserTelling(Integer count = null, Integer offset = 0) {
        def currentUser = springSecurityService.getCurrentUser()
        def query = Telling.where {
            author.id == currentUser.id
        }
        def params = [sort: 'dateCreated', order: "desc", offset: offset]
        if (count != null){
            params.max = count
        }
        query.list(params)
    }
}
