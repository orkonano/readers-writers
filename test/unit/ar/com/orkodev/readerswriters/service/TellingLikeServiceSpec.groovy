package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.domain.*
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TellingLikeService)
@Mock([TellingLike,User,Telling])
class TellingLikeServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test stop like method"() {
        given:
        mockForConstraintsTests Telling
        mockForConstraintsTests User
        mockForConstraintsTests TellingLike
        def tellingLikeService = new TellingLikeService()
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(2) { ->currentUser }
        tellingLikeService.springSecurityService = springSecurityService.createMock()
        def telling = new Telling(title: "t1",author: author,description: "d2",text: "text1",narrativeGenre: new NarrativeGenre(),tellingType: new TellingType()).save(flush: true,failOnError: true)
        new TellingLike(reader:currentUser,telling: telling ).save(flush: true,failOnError: true)

        when:"Cuando un usuario ya no le gusta más una obra"
        def erased = tellingLikeService.stopLike(telling)
        then: "La relación se borra correctamente"
        erased
        TellingLike.list().isEmpty()

        when: "Se quiere borrar una relación que no existe"
        erased = tellingLikeService.stopLike(telling)
        then: "La relación no se puede borrar"
        !erased
    }


    void "test like telling method"() {
        given:
        mockForConstraintsTests Telling
        mockForConstraintsTests User
        def tellingLikeService = new TellingLikeService()
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(3) { ->currentUser }
        tellingLikeService.springSecurityService = springSecurityService.createMock()
        def telling = new Telling(title: "t1",author: currentUser,description: "d2",text: "text1",narrativeGenre: new NarrativeGenre(),tellingType: new TellingType()).save(flush: true,failOnError: true)
        def telling2 = new Telling(title: "t1",author: author,description: "d2",text: "text1",narrativeGenre: new NarrativeGenre(),tellingType: new TellingType()).save(flush: true,failOnError: true)

        when:"El telling que gusta es del mismo author que el usuario"
        tellingLikeService.like(telling)
        then: "Se arroja la excepción SameUserToCurrentException"
        thrown(SameUserToCurrentException)

        when:"El author del telling a seguir es diferente al current"
        def tellingLike = tellingLikeService.like(telling2)
        then: "Se guarda con éxito y no se arroja ninguna excepcion"
        tellingLike != null
        tellingLike.id != null

//        when:"El telling a seguir ya es seguido por el mismo usuario"
//        tellingLikeService.like(telling2)
//        then: "Se arroja la excepción ValidationException"
//        thrown(ValidationException)
    }


    void "test isLike method"() {
        given:
        mockForConstraintsTests TellingLike
        mockForConstraintsTests User
        mockForConstraintsTests Telling
        def tellingLikeService = new TellingLikeService()
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        def telling = new Telling(title: "t1",author: author,description: "d2",text: "text1",narrativeGenre: new NarrativeGenre(),tellingType: new TellingType()).save(flush: true,failOnError: true)
        def telling2 = new Telling(title: "t1",author: author,description: "d2",text: "text1",narrativeGenre: new NarrativeGenre(),tellingType: new TellingType()).save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(2) { ->currentUser }
        tellingLikeService.springSecurityService = springSecurityService.createMock()
        new TellingLike(reader: currentUser,telling: telling).save(flush: true,failOnError: true)
        when:"Cuando al current user le gusta el telling"
        def isLiked = tellingLikeService.isLike(telling)
        then: "El resultado es verdadero"
        isLiked

        when: "Se al current user no le gusta el tellign"
        isLiked = tellingLikeService.isLike(telling2)
        then: "La relación no se puede borrar"
        !isLiked
    }
}
