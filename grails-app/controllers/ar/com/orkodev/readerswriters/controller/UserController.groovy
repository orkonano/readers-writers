package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.springsecurity.annotation.Secured

class UserController extends BaseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def userService, springSecurityService, followerService, tellingService

    /*def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userInstanceCount: User.count()]
    }*/


    @Secured('permitAll')
    def create() {
        respond new User(params)
    }

    @Secured('permitAll')
    def save(User userInstance) {
        withForm {
            if (userInstance == null) {
                notFound('userInstance.label','User')
                return
            }
            def password = userInstance.password
            try {
                userInstance = userService.saveUser(userInstance)
            }catch (ValidationException ex){
                userInstance.errors = ex.errors
                render  model:["userInstance":userInstance], view:'create'
                return
            }
            springSecurityService.reauthenticate(userInstance.username,password)
            redirect controller: 'panel',action: 'dashboard'
        }
    }

    @Secured("ROLE_US")
    def edit() {
        def userLogin = springSecurityService.getCurrentUser()
        render model:["userInstance":userLogin],view:"edit"
    }

    @Secured("ROLE_US")
    def update(User userInstance) {
        if (userInstance == null) {
            notFound('userInstance.label','User')
            return
        }
        def userLogin = springSecurityService.getCurrentUser()
        if (userLogin.id != userInstance.id){
            userInstance.errors
            render model:["userInstance":userInstance,"otherError":"No se está actualizando al mismo usuario loggeado"], view:'edit'
            return
        }

        try {
            userInstance = userService.editUser(userInstance)
        }catch (ValidationException ex){
            userInstance.errors = ex.errors
            render  model:["userInstance":userInstance], view:'edit'
            return
        }

        redirect action: "edit"
    }

    @Secured("ROLE_US")
    def showAuthor(User userInstance) {
        def isFollowed = followerService.isFollowAuthor(userInstance)
        def tellingSearch = new Telling(author: userInstance)
        def (tellingList, countResult) = tellingService.listPublished(tellingSearch, 15, 0)
        render model:[
                      userInstance: userInstance,
                      isFollowed:isFollowed,
                      tellingInstanceList: tellingList,
                      tellingInstanceCount: countResult,
                     ],
                view:"showAuthor"
    }

}
