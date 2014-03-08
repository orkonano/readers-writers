package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswiters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingLike
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.service.FollowerService
import ar.com.orkodev.readerswriters.service.TellingLikeService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TellingLikeController)
@Mock([TellingLike,Telling])
class TellingLikeControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Test the like action as JSON"() {
        given:
        def tellingLikeServiceSucess = mockFor(TellingLikeService)
        tellingLikeServiceSucess.demandExplicit.like() {Telling telling ->return new TellingLike()}
        def tellingLikeServiceFail1 = mockFor(TellingLikeService)
        def mensaje = "error"
        tellingLikeServiceFail1.demandExplicit.like() {Telling telling ->throw new SameUserToCurrentException(mensaje)}
        def telling = new Telling()

        when: "Cuando se llama al action con el usuario definido"
        controller.tellingLikeService = tellingLikeServiceSucess.createMock()
        controller.like(telling)
        then: "Se renderiza con success true"
        response.json.success == true

        when: "Cuando se arroja una exception"
        response.reset()
        controller.tellingLikeService = tellingLikeServiceFail1.createMock()
        controller.like(telling)
        then:"Se renderiza success false y el mensaje en el Json"
        response.json.success == false
        response.json.errors == [mensaje]
    }

    void "Test the leave follow action as JSON"() {
        def tellingLikeServiceSucess = mockFor(TellingLikeService)
        tellingLikeServiceSucess.demandExplicit.stopLike() {Telling telling ->return true}
        def tellingLikeServiceFail1 = mockFor(TellingLikeService)
        tellingLikeServiceFail1.demandExplicit.stopLike() {Telling telling ->return false}
        def telling = new Telling()

        when: "Cuando se llama al action y se puede borrar"
        controller.tellingLikeService = tellingLikeServiceSucess.createMock()
        controller.stopTolike(telling)
        then: "Se renderiza con success true"
        response.json.success == true

        when: "Cuando se llama al action y no se puede borrar"
        response.reset()
        controller.tellingLikeService = tellingLikeServiceFail1.createMock()
        controller.stopTolike(telling)
        then: "Se renderiza con success false"
        response.json.success == false
    }
}
