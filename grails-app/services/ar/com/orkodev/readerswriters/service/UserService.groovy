package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Role
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.domain.UserRole
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

class UserService extends BaseService<User>{


    @Transactional
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

    @Transactional
    User editUser(User userToSave) {
        userToSave.validate()
        if (userToSave.hasErrors()) {
            throw new ValidationException(errors: userToSave.errors)
        }
        userToSave.save()
    }


    @Cacheable('readers-writers')
    @Transactional(readOnly = true)
    Set<Role> getRoles(User user){
        user.getAuthorities()
    }
}
