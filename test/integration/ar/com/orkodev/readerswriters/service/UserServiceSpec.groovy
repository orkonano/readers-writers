package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.User

/**
 *
 */
class UserServiceSpec extends GroovyTestCase {
    static transactional = false

    def userService

    def setup() {
    }

    def cleanup() {
    }

    void testSaveUser() {
        def userToSave = new User(username: "",password: "")
        try{
            userService.saveUser(userToSave)
        }catch (ValidationException ex){
            assertNotNull(ex.errors)
            assertNotNull(ex.errors['username'])
            assertNotNull(ex.errors['password'])
        }
        userToSave = new User(username: "orko@orko.com",password: "3434")
        userToSave = userService.saveUser(userToSave)
        assertNotNull(userToSave.id)
    }
}
