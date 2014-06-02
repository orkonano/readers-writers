package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.service.NarrativeGenreService
import ar.com.orkodev.readerswriters.service.TellingLikeService
import ar.com.orkodev.readerswriters.service.TellingService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(TellingController)
@Mock([Telling, NarrativeGenre, TellingType, User])
class TellingControllerSpec extends Specification {

    def setup(){
        def narrativeGenreService = mockFor(NarrativeGenreService)
        narrativeGenreService.demandExplicit.getAll(3){ -> [new NarrativeGenre()]}
        def tellingTypeService = mockFor(NarrativeGenreService)
        tellingTypeService.demandExplicit.getAll(3){ -> [new TellingType()]}
        controller.narrativeGenreService = narrativeGenreService.createMock()
        controller.tellingTypeService = tellingTypeService.createMock()

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

    def getCurrentUser(){
        mockForConstraintsTests(User)
        def user = new User(username: "jose@gg.com", password: "dd")
        def springSecurityService = Mock(SpringSecurityService)
        user.springSecurityService = springSecurityService
        user.save(flush: true)
    }

    void "Test the index action returns the correct model"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.listAllAuthorUserTelling() {User user1, int i -> return ([new Telling(), 1])}
        def user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(1..2) { ->return user}
        controller.springSecurityService = springSecurityService.createMock()
        controller.tellingService = tellingService.createMock()
        when: "The index action is executed"
        controller.index()
        then: "The model is correct"
        model.tellingList
        model.tellingInstanceCount == 1
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()
        then: "The model is correctly created"
        model.tellingInstance
        model.narrativesGenre
        model.tellingsType
    }

    void "Test the save action correctly persists an instance"() {
        given:
        def holder = SynchronizerTokensHolder.store(session)
        def token = holder.generateToken('/telling/save')
        params[SynchronizerTokensHolder.TOKEN_URI] = '/telling/save'
        params[SynchronizerTokensHolder.TOKEN_KEY] = token
        when: "The save action is executed with an invalid instance"
        def tellingService  = mockFor(TellingService)
        tellingService.demandExplicit.save(){Telling telling ->
            telling.validate()
            throw new ValidationException(errors: telling.errors)
         }
        controller.tellingService = tellingService.createMock()
        request.contentType = FORM_CONTENT_TYPE
        def telling = new Telling()
        controller.save(telling)
        then: "The create view is rendered again with the correct model"
        model.tellingInstance != null
        view == '/telling/create'

        when: "The save action is executed with a valid instance"
        response.reset()
        holder = SynchronizerTokensHolder.store(session)
        token = holder.generateToken('/telling/save')
        params[SynchronizerTokensHolder.TOKEN_URI] = '/telling/save'
        params[SynchronizerTokensHolder.TOKEN_KEY] = token
        populateValidParams(params)
        telling = new Telling(params)
        tellingService  = mockFor(TellingService)
        tellingService.demandExplicit.save(){Telling telling1 -> return telling1.save()}
        controller.tellingService = tellingService.createMock()
        controller.save(telling)
        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/telling/index'
    }

    void "Test that the show action returns the correct model"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(2) { -> user }
        controller.springSecurityService = springSecurityService.createMock()
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findById(){Telling t -> new Telling(id: 1, author: user)}
        controller.tellingService = tellingService.createMock()
        when: "A domain instance is passed to the show action"
        response.reset()
        populateValidParams(params)
        def telling = new Telling(params)
        controller.show(telling.id)

        then: "A model is populated containing the domain instance"
        response.status == 200
        model
    }


    void "Test that the edit action returns the correct model"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(1) { ->return user }
        controller.springSecurityService = springSecurityService.createMock()
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findById(){Telling t -> new Telling(id: 1, author: user)}
        controller.tellingService = tellingService.createMock()

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def telling = new Telling(params)
        controller.edit(telling.id)

        then: "A model is populated containing the domain instance"
        model.tellingInstance != null
        model.narrativesGenre != null
        model.tellingsType != null
    }

    void "Test the update action performs an update on a valid domain instance"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(2) { ->return user}
        controller.springSecurityService = springSecurityService.createMock()
        //        when: "The show action is executed with a null domain"
//        controller.update(null)
//
//        then: "A 404 error is returned"
//        response.status == (NOT_FOUND.value() || 302)

        when: "An invalid domain instance is passed to the update action"
        def tellingService  = mockFor(TellingService)
        tellingService.demandExplicit.save(){Telling telling ->
            telling.validate()
            throw new ValidationException(errors: telling.errors)
        }
        controller.tellingService = tellingService.createMock()
        response.reset()
        def telling = new Telling()
        telling.author = user
        controller.update(telling)

        then: "The edit view is rendered again with the invalid instance"
        view == '/telling/edit'
        model.tellingInstance == telling

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        tellingService  = mockFor(TellingService)
        tellingService.demandExplicit.save(){Telling telling1 -> return telling1.save()}
        controller.tellingService = tellingService.createMock()
        controller.update(telling)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/telling/index"
    }


    void "Test that the publish action"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def user = getCurrentUser()
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
        def telling = new Telling(params)
        controller.publish(telling.id)
        then: "The instance is published"
        response.redirectedUrl == '/telling/index'
        flash.success != null
    }

    void "Test that the list action"() {
        given:
        mockForConstraintsTests NarrativeGenre
        mockForConstraintsTests TellingType

        def tellingService  = mockFor(TellingService)
        def telling = new Telling(id: 1, title: "3")
        tellingService.demandExplicit.listPublished(){Telling telling1,Integer max, Integer offset ->
            return [[telling], 1]
        }
        controller.tellingService = tellingService.createMock()
        when:"Es la primera vez que se llama y los paramestros son nulos"
        controller.list(null)
        then:"se redirecciona al listado, cargando los combos para buscar"
        view == "/telling/list"
        model.tellingInstanceList == []
        model.tellingInstanceCount == 0
        model.narrativesGenre
        model.tellingsType

        when:"Es la primera vez que se llama y los paramestros tiene init cargado"
        response.reset()
        params['init'] = true
        controller.list(new Telling())
        then:"se redirecciona al listado, cargando los combos para buscar"
        view == "/telling/list"
        model.tellingInstanceList == []
        model.tellingInstanceCount == 0
        model.narrativesGenre
        model.tellingsType

        when: "se recibe un telling con datos"
        response.reset()
        params.remove("init")
        controller.list(new Telling())
        then:"se redirecciona al listado con datos"
        view == "/telling/list"
        model.tellingInstanceList == [telling]
        model.tellingInstanceCount == 1
        model.narrativesGenre
        model.tellingsType
    }

    void "Test that the read action returns the correct model"() {
        given:
        def tellingLikeService = mockFor(TellingLikeService)
        tellingLikeService.demandExplicit.isLike(2){Telling telling -> return true}
        controller.tellingLikeService = tellingLikeService.createMock()
        def springSecurityService = mockFor(SpringSecurityService)
        springSecurityService.demandExplicit.isLoggedIn() { -> return true }
        controller.springSecurityService = springSecurityService.createMock()
        def tellingService = mockFor(TellingService)
        tellingService.demandExplicit.findById(2){Telling t -> new Telling(id: 1, description: "Hola que tal")}
        controller.tellingService = tellingService.createMock()
        when: "A domain instance is passed to the read action y está logeado"
        params['id'] = 1
        def telling = new Telling(params)
        controller.read(telling.id, "Hola que tal")
        then: "A model is populated containing the domain instance"
        response.status == 200
        model
        model.isLike
        view == '/telling/read_logged'

        when: "se recibe un telling con datos"
        response.reset()
        def springSecurityService2 = mockFor(SpringSecurityService)
        springSecurityService2.demandExplicit.isLoggedIn() { -> return false }
        controller.springSecurityService = springSecurityService2.createMock()
        params['id'] = 1
        telling = new Telling(params)
        controller.read(telling.id, "Hola que tal")
        then: "A model is populated containing the domain instance"
        response.status == 200
        model
        !model.isLike
        view == '/telling/read_logout'
    }


}
