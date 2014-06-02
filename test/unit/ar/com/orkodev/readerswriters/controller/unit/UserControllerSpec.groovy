package ar.com.orkodev.readerswriters.controller.unit

import ar.com.orkodev.readerswriters.controller.UserController
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.ValidationException
import ar.com.orkodev.readerswriters.service.FollowerService
import ar.com.orkodev.readerswriters.service.TellingService
import ar.com.orkodev.readerswriters.service.UserService
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder
import spock.lang.Specification

@TestFor(UserController)
@Mock(User)
class UserControllerSpec extends Specification {

    def id

    def populateValidParams(params) {
        assert params != null
        params["username"] = 'orko@@gmail.com'
        params["password"] = '343434'
    }

    def populateShowParams(params) {
        assert params != null
        params["username"] = 'orko@@gmail.com'
        params["password"] = '343434'
        params["id"] = 1
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
        def holder = SynchronizerTokensHolder.store(session)
        def token = holder.generateToken('/user/save')
        params[SynchronizerTokensHolder.TOKEN_URI] = '/user/save'
        params[SynchronizerTokensHolder.TOKEN_KEY] = token

        when: "The save action is executed with an invalid instance"
        def user = new User()
        userServiceError.demand.saveUser(user) {  throw new ValidationException() }
        controller.userService = userServiceError.createMock()
        request.contentType = FORM_CONTENT_TYPE
        controller.save(user)
        then: "The create view is rendered again with the correct model"
        model.userInstance != null
        view == '/user/create'

        when: "The save action is executed with a valid instance"
        response.reset()
        holder = SynchronizerTokensHolder.store(session)
        token = holder.generateToken('/user/save')
        params[SynchronizerTokensHolder.TOKEN_URI] = '/user/save'
        params[SynchronizerTokensHolder.TOKEN_KEY] = token

        populateValidParams(params)
        user = new User(params)
        userServiceSuccess.demand.saveUser(user) { user }
        controller.userService = userServiceSuccess.createMock()
        springSecurityService.demand.reauthenticate(user.username,user.password){ user.username}
        controller.springSecurityService = springSecurityService.createMock()
        controller.save(user)
        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/panel/dashboard'
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
        springSecurityService.demand.getCurrentUser(){ ->
            return new User(id:1,username: "hh@gmail.com",password: "hola")
        }

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
        userServiceSuccess.demand.editUser() {User user1 -> user }
        controller.userService = userServiceSuccess.createMock()
        springSecurityService.demand.getCurrentUser(){ ->
            return new User(id:1,username: "hh@gmail.com",password: "hola")
        }
        controller.springSecurityService = springSecurityService.createMock()
        controller.update(user)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/user/edit'
    }

    void "Test that the showAuthor action returns the correct model"() {
        given:
        def followService = mockFor(FollowerService)
        followService.demandExplicit.isFollowAuthor(){User user -> return true}
        def followServiceFail = mockFor(FollowerService)
        followServiceFail.demandExplicit.isFollowAuthor(){User user -> return false}
        def telling = new Telling(id: 1, title: "3")
        def tellingService  = mockFor(TellingService)
        tellingService.demandExplicit.listTellingPublishByAuthor(2){Telling telling1,Integer max, Integer offset ->
            return [[telling], 1]
        }
        def user = new User(params)
        controller.tellingService = tellingService.createMock()
        def springSecurityService = mockFor(SpringSecurityService)
        springSecurityService.demandExplicit.isLoggedIn(2) { -> return true }
        controller.springSecurityService = springSecurityService.createMock()
        def userService = mockFor(UserService)
        userService.demandExplicit.findById(2){User u -> new User(id: 1)}
        controller.userService = userService.createMock()
        when: "A domain instance is passed to the show action and is followed"
        response.reset()
        populateShowParams(params)
        controller.followerService = followService.createMock()
        controller.show(1l)
        then: "A model is populated containing the domain instance"
        response.status == 200
        model
        model.isFollowed
        model.tellingInstanceList == [telling]
        model.tellingInstanceCount == 1

        when: "A domain instance is passed to the show action and is not followed"
        response.reset()
        populateShowParams(params)
        controller.followerService = followServiceFail.createMock()
        controller.show(1l)
        then: "A model is populated containing the domain instance"
        response.status == 200
        model
        !model.isFollowed
        model.tellingInstanceList == [telling]
        model.tellingInstanceCount == 1
    }
}
