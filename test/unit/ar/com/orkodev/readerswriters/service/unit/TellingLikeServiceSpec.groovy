package ar.com.orkodev.readerswriters.service.unit

import ar.com.orkodev.readerswriters.cache.CacheHelperImpl
import ar.com.orkodev.readerswriters.domain.*
import ar.com.orkodev.readerswriters.exception.NotErasedException
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.service.TellingLikeService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TellingLikeService)
@Mock([TellingLike, User, Telling])
class TellingLikeServiceSpec extends Specification {

    def setup() {
        service.cacheHelper = Mock(CacheHelperImpl)
    }

    def cleanup() {
    }

    void "test stop like method"() {
        given:
        mockForConstraintsTests Telling
        mockForConstraintsTests User
        mockForConstraintsTests TellingLike
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(2) { ->currentUser }
        service.springSecurityService = springSecurityService.createMock()
        def telling = new Telling(title: "t1",author: author,description: "d2",text: "text1",narrativeGenre: new NarrativeGenre(),tellingType: new TellingType()).save(flush: true,failOnError: true)
        TellingLike tellingLike = new TellingLike(reader:currentUser,telling: telling ).save(flush: true,failOnError: true)
        TellingLike tellingLikeFail = new TellingLike(reader:author, telling: telling )

        when:"Cuando un usuario ya no le gusta más una obra"
        def erased = service.stopLike(tellingLike)
        then: "La relación se borra correctamente"
        erased
        TellingLike.list().isEmpty()

        when: "Se quiere borrar una relación que no existe"
        service.stopLike(tellingLikeFail)
        then: "La relación no se puede borrar"
        thrown(NotErasedException)
    }


    void "test like telling method"() {
        given:
        mockForConstraintsTests Telling
        mockForConstraintsTests User
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com", password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true, failOnError: true)
        def currentUser = new User(username: "current@example.com", password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(3) { ->currentUser }
        service.springSecurityService = springSecurityService.createMock()
        def telling = new Telling(title: "t1",author: currentUser, description: "d2", text: "text1",
                narrativeGenre: new NarrativeGenre(), tellingType: new TellingType()).save(flush: true,
                failOnError: true)
        def telling2 = new Telling(title: "t1", author: author, description: "d2", text: "text1",
                narrativeGenre: new NarrativeGenre(), tellingType: new TellingType()).save(flush: true,
                failOnError: true)

        when:"El telling que gusta es del mismo author que el usuario"
        service.like(telling)
        then: "Se arroja la excepción SameUserToCurrentException"
        thrown(SameUserToCurrentException)

        when:"El author del telling a seguir es diferente al current"
        def tellingLike = service.like(telling2)
        then: "Se guarda con éxito y no se arroja ninguna excepcion"
        tellingLike != null
        tellingLike.id != null

        when:"El telling a seguir ya es seguido por el mismo usuario"
        service.like(telling2)
        then: "Se arroja la excepción ValidationException"
        thrown(ValidationException)
    }
}
