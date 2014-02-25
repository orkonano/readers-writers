package org.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.Role
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.domain.UserRole
import grails.transaction.Transactional

@Transactional
class UserService {

    def saveUser(User userToSave) {
        userToSave.validate()
        if (userToSave.hasErrors()) {
            throw new ValidationException(errors: userToSave.errors)
        }
        userToSave.save()
        def rolUser = Role.findByAuthority(Role.ROLE_US)
        UserRole.create(userToSave,rolUser)
        return userToSave
    }

    User editUser(User userToSave) {
        userToSave.validate()
        if (userToSave.hasErrors()) {
            throw new ValidationException(errors: userToSave.errors)
        }
        userToSave.save()
    }
}
