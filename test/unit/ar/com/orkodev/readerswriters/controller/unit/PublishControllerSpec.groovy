package ar.com.orkodev.readerswriters.controller.unit

import ar.com.orkodev.readerswriters.controller.PublishController
import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.service.TellingService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PublishController)
@Mock([Telling, User])
class PublishControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    User getCurrentUser(){
        mockForConstraintsTests(User)
        User user = new User(username: "jose@gg.com", password: "dd")
        def springSecurityService = Mock(SpringSecurityService)
        user.springSecurityService = springSecurityService
        user.save(flush: true)
    }

    void "Test that the save action"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        User user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(1) { ->return user}
        controller.springSecurityService = springSecurityService.createMock()
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findById(){Telling t -> new Telling(id: 1, author: user)}
        tellingService.demandExplicit.publish(){Telling telling1 ->
            telling1.state = Telling.PUBLISHED
        }
        controller.tellingService = tellingService.createMock()
        when: "A domain is publish"
        response.reset()
        populateValidParams(params)
        Telling telling = new Telling(params)
        controller.save(telling.id)
        then: "The instance is published"
        response.redirectedUrl == '/telling/index'
        flash.success != null
    }

    def populateValidParams(params) {
        assert params != null
        params["id"] = 1
        params["text"] = "hola"
        params["descripcion"] = "hola 2"
        params["author"] = User.get(1)
        params["narrativeGenre"] = new NarrativeGenre(id: 1)
        params["tellingType"] = new TellingType(id: 1)
        params["state"] = Telling.DRAFT
    }
}
