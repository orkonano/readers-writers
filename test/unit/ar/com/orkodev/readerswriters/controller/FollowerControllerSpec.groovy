package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.service.FollowerService
import ar.com.orkodev.readerswriters.service.UserService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(FollowerController)
@Mock([Follower,User])
class FollowerControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }



    void "Test the follow action as JSON"() {
        given:
        def followerServiceSucess = mockFor(FollowerService)
        followerServiceSucess.demandExplicit.followAuthor() { User user1 ->return new Follower() }
        def userService = mockFor(UserService)
        userService.demandExplicit.findById(2) {User user1 ->return new User(id: 1)}
        def followerServiceFail1 = mockFor(FollowerService)
        def mensaje = "error"
        followerServiceFail1.demandExplicit.followAuthor() {User user1 ->
            throw new SameUserToCurrentException(mensaje)
        }

        when: "Cuando se llama al action con el usuario definido"
        controller.followerService = followerServiceSucess.createMock()
        controller.userService = userService.createMock();
        controller.follow(1l)
        then: "Se renderiza con success true"
        response.json.success == true

        when: "Cuando se arroja una exception"
        response.reset()
        controller.followerService = followerServiceFail1.createMock()
        controller.follow(2)
        then:"Se renderiza success false y el mensaje en el Json"
        response.json.success == true
        response.json.errors == [mensaje]
    }

    void "Test the leave follow action as JSON"() {
        given:
        def followService = mockFor(FollowerService)
        followService.demandExplicit.leaveAuthor() {User user1 -> return true}
        def userService = mockFor(UserService)
        userService.demandExplicit.findById(2) {User user1 ->return new User(id: 1)}
        def followServiceFail = mockFor(FollowerService)
        followServiceFail.demandExplicit.leaveAuthor() { User user1 -> return false}

        when: "Cuando se llama al action y se puede borrar"
        controller.followerService = followService.createMock()
        controller.userService = userService.createMock()
        controller.leaveFollow(1l)
        then: "Se renderiza con success true"
        response.json.success == true

        when: "Cuando se llama al action y no se puede borrar"
        response.reset()
        controller.followerService = followServiceFail.createMock()
        controller.leaveFollow(1l)
        then: "Se renderiza con success false"
        response.json.success == false
    }
}
