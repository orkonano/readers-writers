package ar.com.orkodev.readerswriters.controller.unit

import ar.com.orkodev.readerswriters.controller.TellingLikeController
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingLike
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.service.TellingLikeService
import ar.com.orkodev.readerswriters.service.TellingService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TellingLikeController)
@Mock([TellingLike, Telling])
class TellingLikeControllerSpec extends Specification {

    def setup() {
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findById(2){Telling t -> new Telling(id: 1, description: "Hola que tal")}
        controller.tellingService = tellingService.createMock()
    }

    def cleanup() {
    }

    void "Test the like action as JSON"() {
        given:
        def tellingLikeServiceSucess = mockFor(TellingLikeService)
        TellingLike tellingLike = new TellingLike(id: 1)
        tellingLikeServiceSucess.demandExplicit.like() {Telling telling -> return tellingLike}
        def tellingLikeServiceFail1 = mockFor(TellingLikeService)
        def mensaje = "error"
        tellingLikeServiceFail1.demandExplicit.like() {Telling telling ->
            throw new SameUserToCurrentException(mensaje)
        }
        when: "Cuando se llama al action con el usuario definido"
        controller.tellingLikeService = tellingLikeServiceSucess.createMock()
        controller.save(1l)
        then: "Se renderiza con success true"
        response.json.success == true
        response.json.view
        response.json.view.urlunlike == '/tellings/1/likes/'+tellingLike.id
        when: "Cuando se arroja una exception"
        response.reset()
        controller.tellingLikeService = tellingLikeServiceFail1.createMock()
        controller.save(1l)
        then:"Se renderiza success false y el mensaje en el Json"
        response.json.success == true
        response.json.errors == [ mensaje ]
    }

    void "Test the leave follow action as JSON"() {
        TellingLike tellingLike = new TellingLike(id: 1)
        def tellingLikeServiceSucess = mockFor(TellingLikeService)
        tellingLikeServiceSucess.demandExplicit.findById() {TellingLike like -> return tellingLike}
        tellingLikeServiceSucess.demandExplicit.stopLike() {TellingLike telling -> return true}
        def tellingLikeServiceFail1 = mockFor(TellingLikeService)
        tellingLikeServiceFail1.demandExplicit.findById() {TellingLike like -> return tellingLike}
        tellingLikeServiceFail1.demandExplicit.stopLike() {TellingLike telling -> return false}


        when: "Cuando se llama al action y se puede borrar"
        controller.tellingLikeService = tellingLikeServiceSucess.createMock()
        controller.delete(1, 1)
        then: "Se renderiza con success true"
        response.json.success == true
        response.json.view
        response.json.view.urlLike == '/tellings/1/likes'

        when: "Cuando se llama al action y no se puede borrar"
        response.reset()
        controller.tellingLikeService = tellingLikeServiceFail1.createMock()
        controller.delete(1, 1)
        then: "Se renderiza con success false"
        response.json.success == false
    }
}
