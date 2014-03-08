package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswiters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.service.FollowerService
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
        followerServiceSucess.demandExplicit.followAuthor() {User user1 ->return new Follower()}
        def followerServiceFail1 = mockFor(FollowerService)
        def mensaje = "error"
        followerServiceFail1.demandExplicit.followAuthor() {User user1 ->throw new SameUserToCurrentException(mensaje)}
        def user = new User()

        when: "Cuando se llama al action con el usuario definido"
        controller.followerService = followerServiceSucess.createMock()
        controller.follow(user)
        then: "Se renderiza con success true"
        response.json.success == true

        when: "Cuando se arroja una exception"
        response.reset()
        controller.followerService = followerServiceFail1.createMock()
        controller.follow(user)
        then:"Se renderiza success false y el mensaje en el Json"
        response.json.success == false
        response.json.errors == [mensaje]
    }

    void "Test the leave follow action as JSON"() {
        given:
        def followService = mockFor(FollowerService)
        followService.demandExplicit.leaveAuthor() {User user1 ->return true}
        def followServiceFail = mockFor(FollowerService)
        followServiceFail.demandExplicit.leaveAuthor() { User user1->return false}
        def user = new  User()

        when: "Cuando se llama al action y se puede borrar"
        controller.followerService = followService.createMock()
        controller.leaveFollow(user)
        then: "Se renderiza con success true"
        response.json.success == true

        when: "Cuando se llama al action y no se puede borrar"
        response.reset()

        controller.followerService = followServiceFail.createMock()
        controller.leaveFollow(user)
        then: "Se renderiza con success false"
        response.json.success == false
    }
}
