package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.NotErasedException
import ar.com.orkodev.readerswiters.exception.NotPublishedException
import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.Telling

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

    def list(Telling tellingSearch, Integer max,Integer offset){
        def query = Telling.where {
            state == Telling.PUBLISHED
        }
        if (tellingSearch.narrativeGenre!= null && tellingSearch.narrativeGenre.id != null){
            query = query.where {
                narrativeGenre.id == tellingSearch.narrativeGenre.id
            }
        }
        if (tellingSearch.tellingType!= null && tellingSearch.tellingType.id != null){
            query = query.where {
                tellingType.id == tellingSearch.tellingType.id
            }
        }
        def count = query.count()
        def resultList = query.list(offset: offset?:0,max: max?:15)
        return ["result":resultList,"countResult":count]
    }

}
