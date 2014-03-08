package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FollowerService)
@Mock([Follower,User])
class FollowerServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }


    void "test leave method"() {
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
        def followerService = new FollowerService()
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(2) { ->currentUser }
        followerService.springSecurityService = springSecurityService.createMock()
        new Follower(following: currentUser,author: author).save(flush: true,failOnError: true)
        when:"Cuando se elimina a un author de los autores seguidos y el mismo existe"
        def erased = followerService.leaveAuthor(author)
        then: "La relación se borra correctamente"
        erased
        Follower.list().isEmpty()

        when: "Se quiere borrar una relación que no existe"
        erased = followerService.leaveAuthor(author)
        then: "La relación no se puede borrar"
        !erased
    }


    void "test save method"() {
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
        def followerService = new FollowerService()
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(3) { ->currentUser }
        followerService.springSecurityService = springSecurityService.createMock()

        when:"El usuario a seguir es el mismo que el usuario author"
        followerService.followAuthor(currentUser)
        then: "Se arroja la excepción SameUserToCurrentException"
        thrown(SameUserToCurrentException)

        when:"El usuario a seguir es diferente al actual"
        def follower = followerService.followAuthor(author)
        then: "Se guarda con éxito y no se arroja ninguna excepcion"
        follower != null
        follower.id != null

        when:"El usuario a seguir ya es seguido por el mismo usuario"
        new Follower(author:author,following: currentUser).save(flush: true,failOnError: true)
        followerService.followAuthor(author)
        then: "Se arroja la excepción ValidationException"
        thrown(ValidationException)
    }

    void "test isFollowed method"() {
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
        def followerService = new FollowerService()
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        def author2 = new User(username: "author2@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true,failOnError: true)
        author2.springSecurityService = springSecurityServiceUser
        author2.save(flush: true,failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(2) { ->currentUser }
        followerService.springSecurityService = springSecurityService.createMock()
        new Follower(following: currentUser,author: author).save(flush: true,failOnError: true)
        when:"Cuando el author es seguido por el current user"
        def isFollowed = followerService.isFollowAuthor(author)
        then: "El resultado es verdadero"
        isFollowed

        when: "Se el author no es seguido por el current user"
        isFollowed = followerService.isFollowAuthor(author2)
        then: "El resultado es falso"
        !isFollowed
    }
}
