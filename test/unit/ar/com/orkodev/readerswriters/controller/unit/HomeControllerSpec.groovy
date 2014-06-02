package ar.com.orkodev.readerswriters.controller.unit

import ar.com.orkodev.readerswriters.controller.HomeController
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.service.TellingService
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HomeController)
class HomeControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test index action"() {
        given:
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findLastTellingPublish(){ int max, int hour
        -> return [new Telling()]}
        controller.tellingService = tellingService.createMock()
        when: "Se llama al action index"
        controller.index()
        then: "La respuesta es la siguiente"
        response.status == 200
        view == '/home/index'
        model
        model.tellingInstanceList
        model.tellingInstanceList.size() == 1
        model.tellingInstanceCount == 5
        model.seoDescription == "Red social de escritores y lectores"
    }
}
