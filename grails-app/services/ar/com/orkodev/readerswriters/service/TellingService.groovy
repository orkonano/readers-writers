package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.Role
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.UserRole
import grails.transaction.Transactional

@Transactional
class TellingService {

    def springSecurityService

    def save(Telling tellingToSave) {
        tellingToSave.author = springSecurityService.getCurrentUser()
        tellingToSave.validate()
        if (tellingToSave.hasErrors()) {
            throw new ValidationException(errors: tellingToSave.errors)
        }
        tellingToSave.save()
    }

    def delete(Telling tellingToSave) {
        tellingToSave.state = Telling.ERASED
        tellingToSave.save()
    }

}
