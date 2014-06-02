package ar.com.orkodev.readerswriters.controller.unit

import ar.com.orkodev.readerswriters.controller.PanelController
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.service.FollowerService
import ar.com.orkodev.readerswriters.service.TellingLikeService
import ar.com.orkodev.readerswriters.service.TellingService
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PanelController)
class PanelControllerSpec extends Specification {



    def setup() {
    }

    def cleanup() {
    }

    void "test dashboard action"() {
        when:" dashboard action is called"
        controller.dashboard()
        then: "se renderiza la view dashboard"
        view == '/panel/dashboard'
    }

    void "test likeTelling action"(){
        given:
        def tellingLikeService = mockFor(TellingLikeService)
        tellingLikeService.demandExplicit.findLikeTelling(){Integer number ->
            [new Telling(id: 1, title: "hola" ,author: new User(id: 1, username: "dd")),
             new Telling(id: 2, title: "hola" ,author: new User(id: 1, username: "dd")),
             new Telling(id: 3, title: "hola" ,author: new User(id: 1, username: "dd")),
             new Telling(id: 4, title: "hola" ,author: new User(id: 1, username: "dd")),
             new Telling(id: 5, title: "hola" ,author: new User(id: 1, username: "dd"))]
        }
        controller.tellingLikeService = tellingLikeService.createMock()

        when:"Cuando se llama a la action likeTelling"
        controller.likeTelling()
        then:"Se obtiene una response del tipo JSON"
        response.json.success == true
        response.json.model != null
        response.json.model.elements != null
    }

    void "test authorsFollowed action"(){
        given:
        def followerService = mockFor(FollowerService)
        followerService.demandExplicit.findAuthorFollowed(){Integer number ->
            [new User(id: 1, username: "hola@gg.com" ,password: "pass"),
             new User(id: 2, username: "hola2@gg.com" ,password: "pass"),
             new User(id: 3, username: "hola3@gg.com" ,password: "pass"),
             new User(id: 4, username: "hola4@gg.com" ,password: "pass"),
             new User(id: 5, username: "hola5@gg.com" ,password: "pass")]
        }
        controller.followerService = followerService.createMock()

        when:"Cuando se llama a la action authorsFollowed"
        controller.authorsFollowed()
        then:"Se obtiene una response del tipo JSON"
        response.json.success == true
        response.json.model != null
        response.json.model.elements != null
    }

    void "test ownTelling action"(){
        given:
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findCurrentUserTelling(){Integer number ->
            [new Telling(id: 1, title: "hola"),
             new Telling(id: 2, title: "hola"),
             new Telling(id: 3, title: "hola"),
             new Telling(id: 4, title: "hola"),
             new Telling(id: 5, title: "hola")
            ]
        }
        controller.tellingService = tellingService.createMock()

        when:"Cuando se llama a la action ownTelling"
        controller.ownTelling()
        then:"Se obtiene una response del tipo JSON"
        response.json.success == true
        response.json.model != null
        response.json.model.elements != null
    }
}
