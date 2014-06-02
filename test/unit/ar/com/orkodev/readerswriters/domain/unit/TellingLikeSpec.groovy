package ar.com.orkodev.readerswriters.domain.unit

import ar.com.orkodev.readerswriters.domain.*
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock([User,Telling,TellingLike])
class TellingLikeSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contraint"() {
        given:
        mockForConstraintsTests(TellingLike)
        def springSecurityService = Mock(SpringSecurityService)
        def user1 = new User(username: "dd@gg.com",password: "dd")
        user1.springSecurityService = springSecurityService
        user1.save(flush: true,failOnError: true)
        def user2 = new User(username: "dd2@gg.com",password: "dd")
        user2.springSecurityService = springSecurityService
        user2.save(flush: true,failOnError: true)
        def telling = new Telling(title: "t1", author: user2, description: "d2", text: "text1",
                narrativeGenre: new NarrativeGenre(), tellingType: new TellingType())
                .save(flush: true,failOnError: true)
        when:"Cuando no se cargan ningún dato"
        def tellingLike = new TellingLike()
        then: "La validacion falla"
        !tellingLike.validate()
        tellingLike.hasErrors()
        tellingLike.errors["reader"] == 'nullable'
        tellingLike.errors["telling"] == 'nullable'

        when:"Cuando se cargan los datos correctamente"
        def tellingLike2 = new TellingLike(reader: user1,telling: telling)
        then:"la validacion funciona"
        tellingLike2.validate()
        !tellingLike2.hasErrors()
        tellingLike2.save(flush: true,failOnError: true)
        TellingLike.list().size() == 1

        when: "cuando se intenta insertar a a la misma historia el mismo usuario que ya le gusta"
        def tellingLike3 = new TellingLike(reader: user1,telling: telling)
        then:"la validacion falla"
        !tellingLike3.validate()
        tellingLike3.hasErrors()
        tellingLike3.errors["telling"] == 'unique'


        when:"Cuando se inserta misma telling pero diferente usuario"
        def tellingLike4 = new TellingLike(reader: user2,telling: telling)
        then:"la validacion funciona"
        tellingLike4.validate()
        !tellingLike4.hasErrors()
        tellingLike4.save(flush: true,failOnError: true)
        TellingLike.list().size() == 2

//        then: "Las asociaciones quedan de la siguiente forma"
//        user1.getTellings().size() == 1
//        telling.getReaders().size() == 2
//        user2.getTellings().size() == 1
    }
}
