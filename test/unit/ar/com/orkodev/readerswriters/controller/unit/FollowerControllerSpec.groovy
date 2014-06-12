package ar.com.orkodev.readerswriters.controller.unit

import ar.com.orkodev.readerswriters.controller.FollowerController
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
        Follower followerAuthor = new Follower(id: 1)
        followerServiceSucess.demandExplicit.followAuthor() { User user1 ->
            return followerAuthor
        }
        def userService = mockFor(UserService)
        User userMock = new User(id: 1)
        userService.demandExplicit.findById(2) { User user1 -> return userMock }
        def followerServiceFail1 = mockFor(FollowerService)
        def mensaje = "error"
        followerServiceFail1.demandExplicit.followAuthor() {User user1 ->
            throw new SameUserToCurrentException(mensaje)
        }

        when: "Cuando se llama al action con el usuario definido"
        controller.followerService = followerServiceSucess.createMock()
        controller.userService = userService.createMock();
        controller.save(1l)
        then: "Se renderiza con success true"
        response.json.success == true
        response.json.view.urlUnfollow == "/authors/"+userMock.id+"/followers/"+followerAuthor.id

        when: "Cuando se arroja una exception"
        response.reset()
        controller.followerService = followerServiceFail1.createMock()
        controller.save(2)
        then:"Se renderiza success false y el mensaje en el Json"
        response.json.success == true
        response.json.errors == [mensaje]
    }

    void "Test the leave follow action as JSON"() {
        given:
        def followService = mockFor(FollowerService)
        followService.demandExplicit.findById() { Follower follower2 -> new Follower(id: 1) }
        followService.demandExplicit.leaveAuthor() { Follower follower1 -> return true }
        controller.followerService = followService.createMock()
        def userService = mockFor(UserService)
        userService.demandExplicit.findById(3) { User user1 ->return new User(id: 1) }
        controller.userService = userService.createMock()

        def followServiceFail = mockFor(FollowerService)
        followServiceFail.demandExplicit.findById() { Follower follower2 -> new Follower(id: 1) }
        followServiceFail.demandExplicit.leaveAuthor() { Follower follower1 -> return false }

        def followServiceException = mockFor(FollowerService)
        def mensaje = "error"
        followServiceException.demandExplicit.findById() { Follower follower2 -> new Follower(id: 1) }
        followServiceException.demandExplicit.leaveAuthor() { Follower follower2 ->
            throw new SameUserToCurrentException(mensaje)
        }

        when: "Cuando se llama al action y se puede borrar"
        controller.delete(1, 1)
        then: "Se renderiza con success true"
        response.json.success
        response.json.view.urlFollow == "/authors/" + 1 + "/followers"

        when: "Cuando se llama al action y no se puede borrar"
        response.reset()
        controller.followerService = followServiceFail.createMock()
        controller.delete(1, 1)
        then: "Se renderiza con success false"
        response.json.success == false

        when: "Cuando se llama al action y no se puede borrar"
        response.reset()
        controller.followerService = followServiceException.createMock()
        controller.delete(1, 1)
        then: "Se renderiza con success false"
        response.json.success == true
        response.json.errors == [mensaje]
    }
}
