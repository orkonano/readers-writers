package ar.com.orkodev.readerswriters.domain

import ar.com.orkodev.readerswiters.exception.ValidationException
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.*
import ar.com.orkodev.readerswriters.service.UserService
import spock.lang.*

@TestFor(UserController)
@Mock(User)
class UserControllerSpec extends Specification {

    def id

    def populateValidParams(params) {
        assert params != null
        params["username"] = 'orko@@gmail.com'
        params["password"] = '343434'
    }

     void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model != null
    }

    void "Test the save action correctly persists an instance"() {
        given:
        def userServiceError = mockFor(UserService)
        def userServiceSuccess = mockFor(UserService)
        def springSecurityService = mockFor(SpringSecurityService)

        when: "The save action is executed with an invalid instance"
        def user = new User()
        userServiceError.demand.save(user) {  throw new ValidationException() }
        controller.userService = userServiceError.createMock()
        request.contentType = FORM_CONTENT_TYPE

        controller.save(user)

        then: "The create view is rendered again with the correct model"
        model.userInstance != null
        view == '/user/create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        user = new User(params)
        userServiceSuccess.demand.save(user) { user }
        controller.userService = userServiceSuccess.createMock()
        springSecurityService.demand.reauthenticate(user.username,user.password){ user.username}
        controller.springSecurityService = springSecurityService.createMock()
        controller.save(user)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/'
    }

    void "Test the edit action returns the correct model"() {
        given:
        def springSecurityService = mockFor(SpringSecurityService)
        when: "The edit action is executed"
        springSecurityService.demand.getCurrentUser(){return new User(id:1,username: "hh@gmail.com",password: "hola")}
        controller.springSecurityService = springSecurityService.createMock()
        controller.edit()
        then: "The model is correctly edited"
        model != null
    }

    void "Test the update action correctly persists an instance"() {
        given:
        def userServiceError = mockFor(UserService)
        def userServiceSuccess = mockFor(UserService)
        def springSecurityService = mockFor(SpringSecurityService)
        springSecurityService.demand.getCurrentUser(){return new User(id:1,username: "hh@gmail.com",password: "hola")}

        when: "The update action is executed with an invalid instance"
        def user = new User()
        userServiceError.demand.editUser(user) {  throw new ValidationException() }
        controller.userService = userServiceError.createMock()
        controller.springSecurityService = springSecurityService.createMock()
        request.contentType = FORM_CONTENT_TYPE
        controller.update(user)

        then: "The edit view is rendered again with the correct model"
        model.userInstance != null
        view == '/user/edit'

        when: "The update action is executed with a valid instance"
        response.reset()
        params['id'] = 1
        populateValidParams(params)
        user = new User(params)
        userServiceSuccess.demand.editUser(user) { user }
        controller.userService = userServiceSuccess.createMock()
        springSecurityService.demand.getCurrentUser(){return new User(id:1,username: "hh@gmail.com",password: "hola")}
        controller.springSecurityService = springSecurityService.createMock()
        controller.update(user)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/user/edit'
    }
}
