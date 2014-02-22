package ar.com.orkodev.readerswriters.domain

import ar.com.orkodev.readerswiters.exception.ValidationException
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


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

/*    def edit(User userInstance) {
        respond userInstance
    }*/

/*    def update(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'edit'
            return
        }

        userInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect userInstance
            }
            '*'{ respond userInstance, [status: OK] }
        }
    }*/

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
