package ar.com.orkodev.readerswriters.domain.unit

import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock([User,Follower])
class FollowerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contraint"() {
        given:
        mockForConstraintsTests(Follower)
        def springSecurityService = Mock(SpringSecurityService)
        def user1 = new User(username: "dd@gg.com",password: "dd")
        user1.springSecurityService = springSecurityService
        user1.save(flush: true,failOnError: true)
        def user2 = new User(username: "dd2@gg.com",password: "dd")
        user2.springSecurityService = springSecurityService
        user2.save(flush: true,failOnError: true)
        def user3 = new User(username: "dd3@gg.com",password: "dd")
        user3.springSecurityService = springSecurityService
        user3.save(flush: true,failOnError: true)

        when:"Cuando no se cargan ningún dato"
        def follower = new Follower()
        then: "La validacion falla"
        !follower.validate()
        follower.hasErrors()
        follower.errors["following"] == 'nullable'
        follower.errors["author"] == 'nullable'

        when:"Cuando se cargan los datos correctamente"
        def follower2 = new Follower(author: user1, following: user2)
        then:"la validacion funciona"
        follower2.validate()
        !follower2.hasErrors()
        follower2.save(flush: true,failOnError: true)

        when: "cuando se intenta insertar a los mismos autores y seguidores"
        def follower3 = new Follower(author: user1, following: user2)
        then:"la validacion falla"
        !follower3.validate()
        follower3.hasErrors()
        follower3.errors["author"] == 'unique'


        when:"Cuando se inserta mismo author pero diferente seguidor"
        def follower4 = new Follower(author: user1, following: user3)
        then:"la validacion funciona"
        follower4.validate()
        !follower4.hasErrors()
        follower4.save(flush: true,failOnError: true)

        then: "Las asociaciones quedan de la siguiente forma"
        user1.getFollowings().size() == 2
        user1.getAuthorFollowed().isEmpty()
        user2.getAuthorFollowed().size() == 1
        user2.getFollowings().isEmpty()
        user3.getAuthorFollowed().size() == 1
        user3.getFollowings().isEmpty()
    }
}
