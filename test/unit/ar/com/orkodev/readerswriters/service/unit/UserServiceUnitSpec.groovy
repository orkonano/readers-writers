package ar.com.orkodev.readerswriters.service.unit

import ar.com.orkodev.readerswriters.domain.Role
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.domain.UserRole
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.service.UserService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
@Mock([User, UserRole, Role])
class UserServiceUnitSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test save user"() {
        given:
        mockForConstraintsTests User
        when: "Cuando el objeto user está vacío"
        def user = new User()
        service.saveUser(user)
        then: "Se arroja la excepcion de validacion"
        thrown(ValidationException)

        when: "el objeto user está bien cargado"
        user = new User(username: "orko@orko.com", password: "3434")
        def springSecurityServiceUser = Mock(SpringSecurityService)
        user.springSecurityService = springSecurityServiceUser
        service.saveUser(user)
        then: "No se arroja la excepcion de validacion"
        notThrown(ValidationException)
    }

    void "test edit user"() {
        given:
        mockForConstraintsTests User
        when: "Cuando el objeto user está vacío"
        def user = new User()
        service.editUser(user)
        then: "Se arroja la excepcion de validacion"
        thrown(ValidationException)

        when: "el objeto user está bien cargado"
        user = new User(username: "orko@orko.com", password: "3434")
        def springSecurityServiceUser = Mock(SpringSecurityService)
        user.springSecurityService = springSecurityServiceUser
        service.saveUser(user)
        then: "No se arroja la excepcion de validacion"
        notThrown(ValidationException)
    }
}
