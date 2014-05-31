package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.cache.CacheHelperImpl
import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FollowerService)
@Mock([Follower, User])
class FollowerServiceSpec extends Specification {

    def setup(){
        service.cacheHelper = Mock(CacheHelperImpl)
    }

    def cleanup() {
    }


    void "test leave method"() {
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
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
        new Follower(following: currentUser,author: author).save(flush: true, failOnError: true)

        when:"Cuando se elimina a un author de los autores seguidos y el mismo existe"
        def erased = service.leaveAuthor(author)
        then: "La relación se borra correctamente"
        erased
        Follower.list().isEmpty()

        when: "Se quiere borrar una relación que no existe"
        erased = service.leaveAuthor(author)
        then: "La relación no se puede borrar"
        !erased
    }


    void "test save method"() {
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def author = new User(username: "author@example.com",password: "superpassword")
        author.springSecurityService = springSecurityServiceUser
        author.save(flush: true, failOnError: true)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(3) { ->currentUser }
        service.springSecurityService = springSecurityService.createMock()

        when:"El usuario a seguir es el mismo que el usuario author"
        service.followAuthor(currentUser)
        then: "Se arroja la excepción SameUserToCurrentException"
        thrown(SameUserToCurrentException)

        when:"El usuario a seguir es diferente al actual"
        def follower = service.followAuthor(author)
        then: "Se guarda con éxito y no se arroja ninguna excepcion"
        follower != null
        follower.id != null

        when:"El usuario a seguir ya es seguido por el mismo usuario"
        service.followAuthor(author)
        then: "Se arroja la excepción ValidationException"
        thrown(ValidationException)
    }

    void "test isFollowed method"() {
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
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
        service.springSecurityService = springSecurityService.createMock()
        new Follower(following: currentUser, author: author).save(flush: true,failOnError: true)
        when:"Cuando el author es seguido por el current user"
        def isFollowed = service.isFollowAuthor(author)
        then: "El resultado es verdadero"
        isFollowed

        when: "Se el author no es seguido por el current user"
        isFollowed = service.isFollowAuthor(author2)
        then: "El resultado es falso"
        !isFollowed
    }

    /*
    //Queda comentado, ya que queda arreglar el problema con la projections author.id, que con hibernate
    funciona con property(author.id) pero con la base en memoria no
    void "test getAuthorFollowed method"(){
        given:
        mockForConstraintsTests Follower
        mockForConstraintsTests User
        def springSecurityService = mockFor(SpringSecurityService)
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def currentUser = new User(username: "current@example.com",password:"superpassword")
        currentUser.springSecurityService = springSecurityServiceUser
        currentUser.save(flush: true,failOnError: true)
        springSecurityService.demandExplicit.getCurrentUser(3) { ->currentUser }
        service.springSecurityService = springSecurityService.createMock()
        saveLotOfFollowers(currentUser)
        grailsApplication.mainContext.followerService.userService = new UserService()

        when: "Cuando pido los autores seguidos por el current user, sin parametros"
        def result = service.findAuthorFollowed()
        then: "El resultado es 10"
        result.size == 10
        result.collect{it -> it.id} as List == [19, 17, 15, 13, 11, 9, 7, 5, 3, 1]

        when: "Cuando pido una determinada cantidad de autores"
        result = service.findAuthorFollowed(4)
        then: "El resultado es 4"
        result.size == 4
        result.collect{it -> it.id} as List == [19, 17, 15, 13]

        when: "Cuando pido por un offset y una cantidad determinada"
        result = service.findAuthorFollowed(4,3)
        then: "el resultado es 4 pero desde otra posicion"
        result.size == 4
        result.collect{it -> it.id} as List == [13, 11, 9, 7]
    }

    void saveLotOfFollowers(User currentUser) {
        def springSecurityServiceUser = Mock(SpringSecurityService)
        def userAuthor
        for ( i in (0..20).toArray() ) {
            userAuthor = new User(username: "user"+i+"@gg.com", password: "pass"+i)
            userAuthor.springSecurityService = springSecurityServiceUser
            userAuthor.save(flush: true,failOnError: true)
        }
        for ( i in [1, 3, 5, 7, 9, 11, 13, 15, 17, 19] ) {
            new Follower(author: User.get(i), following: currentUser).save(flush: true,failOnError: true)
        }
    }*/
}
