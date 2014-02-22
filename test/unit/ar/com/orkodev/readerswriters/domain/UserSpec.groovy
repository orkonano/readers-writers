package ar.com.orkodev.readerswriters.domain

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock(User)
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contraint"() {
        given:
        mockForConstraintsTests User
        when: "Los atributos username y password son nulos"
        def user = new User()
        then: "la validación falla porque"
        !user.validate()
        user.hasErrors()
        user.errors['username'] == 'nullable'
        user.errors['password'] == 'nullable'
        !user.errors['firstname']
        !user.errors['lastname']

        when: "Los atributos estan vacios (username y password)"
        user = new User(username: "", password: "")
        then: "La validacion falla"
        !user.validate()
        user.hasErrors()
        user.errors['username'] == 'nullable'
        user.errors['password'] == 'nullable'
        !user.errors['firstname']
        !user.errors['lastname']

        when: "Cuando el user name no es un email"
        user = new User(username: "orkero", password: "asdsd")
        then: "La validacion falla"
        !user.validate()
        user.hasErrors()
        user.errors['username'] == 'email'
        !user.errors['password']
        !user.errors['firstname']
        !user.errors['lastname']

        when: "Cuando el username es un email y el password esta cargado"
        user = new User(username: "orkero@fff.com", password: "asdsd")
        then: "Validacion success"
        user.validate()
        !user.hasErrors()
    }

    void "test unique username"() {
        given:
        mockForConstraintsTests User
        when: "cuando registro un usuario que no está repetido"
        def springSecurityService = Mock(SpringSecurityService)
        def user1 = new User(username: "jose@jose.com",password:  "4344")
        user1.springSecurityService = springSecurityService
        then: "se guarda ok"
        user1.save(flush: true)
        when: "Registro dos usuarios con mismo username"
        def userRepetido = new User(username: "jose@jose.com",password:  "4344")
        userRepetido.springSecurityService = springSecurityService
        then: "la validación falla"
        !userRepetido.save(flush: true)
        !userRepetido.validate()
        userRepetido.hasErrors()
        userRepetido.errors['username'] == 'unique'
        !userRepetido.errors['password']
        !userRepetido.errors['firstname']
        !userRepetido.errors['lastname']

    }
}
