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

}
