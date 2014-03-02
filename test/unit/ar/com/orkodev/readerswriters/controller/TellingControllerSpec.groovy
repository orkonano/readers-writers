package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.*
import ar.com.orkodev.readerswriters.service.TellingService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(TellingController)
@Mock([Telling,NarrativeGenre,TellingType,User])
class TellingControllerSpec extends Specification {

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
        def user = new User(username: "jose@gg.com",password: "dd")
        def springSecurityService = Mock(SpringSecurityService)
        user.springSecurityService = springSecurityService
        user.save(flush: true)
    }

    void "Test the index action returns the correct model"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(1..2) { ->return user}
        controller.springSecurityService = springSecurityService.createMock()
        when: "The index action is executed"
        controller.index()
        then: "The model is correct"
        !model.tellingInstanceList
        model.tellingInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.tellingInstance != null
        model.narrativesGenre != null
        model.tellingsType != null
    }

    void "Test the save action correctly persists an instance"() {

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
        springSecurityService.demandExplicit.getCurrentUser(2) { ->user }
        controller.springSecurityService = springSecurityService.createMock()
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        response.reset()
        populateValidParams(params)
        def telling = new Telling(params)
        controller.show(telling)

        then: "A model is populated containing the domain instance"
        response.status == 200
        model!=null
    }


    void "Test that the edit action returns the correct model"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        def user = getCurrentUser()
        springSecurityService.demandExplicit.getCurrentUser(1) { ->return user }
        controller.springSecurityService = springSecurityService.createMock()
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def telling = new Telling(params)
        controller.edit(telling)

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
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        controller.update(null)

        then: "A 404 error is returned"
        response.status == 404

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
        when: "The publish action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        controller.publish(null)

        then: "A 404 is returned"
        response.status == 404

        when: "A domain is publish"
        response.reset()
        populateValidParams(params)
        def telling = new Telling(params)
        def tellingService  = mockFor(TellingService)
        tellingService.demandExplicit.publish(){Telling telling1 ->
            telling1.state = Telling.PUBLISHED
        }
        controller.tellingService = tellingService.createMock()
        controller.publish(telling)
        then: "The instance is published"
        response.redirectedUrl == '/telling/index'
        flash.success != null
    }

//    void "Test that the delete action deletes an instance if it exists"() {
//        given:
//        def springSecurityService = mockFor(SpringSecurityService)
//        def user = getCurrentUser()
//        springSecurityService.demandExplicit.getCurrentUser(1) { ->return user}
//        controller.springSecurityService = springSecurityService.createMock()
//        when: "The delete action is called for a null instance"
//        request.contentType = FORM_CONTENT_TYPE
//        controller.delete(null)
//
//        then: "A 404 is returned"
//        response.status = 404
//
//        when: "A domain instance is created"
//        response.reset()
//        populateValidParams(params)
//        def telling = new Telling(params)
//        def tellingService  = mockFor(TellingService)
//        tellingService.demandExplicit.delete(){Telling telling1 ->
//            telling.state = Telling.ERASED
//            return telling
//        }
//        controller.tellingService = tellingService.createMock()
//        controller.delete(telling)
//
//        then: "The instance is deleted"
//        response.redirectedUrl == '/telling/index'
//    }

}
