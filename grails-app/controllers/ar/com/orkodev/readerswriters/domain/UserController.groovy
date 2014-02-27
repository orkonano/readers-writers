package ar.com.orkodev.readerswriters.domain

import ar.com.orkodev.readerswiters.exception.ValidationException
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*

class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def userService
    def springSecurityService

    /*def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userInstanceCount: User.count()]
    }*/

 /*   def show(User userInstance) {
        respond userInstance
    }*/
    @Secured('permitAll')
    def create() {
        respond new User(params)
    }

    @Secured('permitAll')
    def save(User userInstance) {
        if (userInstance == null) {
            notFound()
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
        redirect url:"/"
    }

    @Secured("ROLE_US")
    def edit() {
        def userLogin = springSecurityService.getCurrentUser()
        render model:["userInstance":userLogin],view:"edit"
    }

    @Secured("ROLE_US")
    def update(User userInstance) {
        if (userInstance == null) {
            notFound()
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

    /*@Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }*/

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userInstance.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
