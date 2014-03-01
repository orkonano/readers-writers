package ar.com.orkodev.readerswriters.service

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
        tellingToErase.state = Telling.ERASED
        tellingToErase.save()
    }

    def publish(Telling tellingToPublish){
        if (tellingToPublish.state != Telling.DRAFT){
            throw new ValidationException()
        }else{
            tellingToPublish.state = Telling.PUBLISHED
            tellingToPublish.save()
        }
    }

}
