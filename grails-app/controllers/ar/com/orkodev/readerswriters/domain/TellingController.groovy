package ar.com.orkodev.readerswriters.domain

import ar.com.orkodev.readerswiters.exception.NotPublishedException
import ar.com.orkodev.readerswiters.exception.ValidationException
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured("ROLE_US")
class TellingController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService, tellingService


    def index(Integer max) {
        def userLogin = springSecurityService.getCurrentUser()
        params.max = Math.min(max ?: 10, 100)
        def query = Telling.where {
            author == userLogin && state != Telling.ERASED
        }
        respond query.list(params), model: [tellingInstanceCount: query.count()]
    }

    def show(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound()
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            render model:["tellingInstance": tellingInstance],view:"show"
        }else{
            response.status = 403;
        }

    }

    private boolean isTellingFromUserLogin(Telling telling){
        def userLogin = springSecurityService.getCurrentUser()
        return userLogin.id == telling.author.id
    }

    def create() {
        render  model:generarModelViewSaveAndUpdate(new Telling()), view:'create'
    }

    def save(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound()
            return
        }

        try {
            tellingInstance = tellingService.save(tellingInstance)
        }catch (ValidationException ex){
            tellingInstance.errors = ex.errors
            render  model:generarModelViewSaveAndUpdate(tellingInstance), view:'create'
            return
        }

        redirect action: "index"
    }

    def edit(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound()
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            render  model:generarModelViewSaveAndUpdate(tellingInstance), view:'create'
        }else{
            response.status = 403;
        }
    }

    def update(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound()
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            try {
                tellingInstance = tellingService.save(tellingInstance)
            }catch (ValidationException ex){
                tellingInstance.errors = ex.errors
                render  model:generarModelViewSaveAndUpdate(tellingInstance), view:'edit'
                return
            }

            redirect action: "index"
        }else{
            response.status = 403;
        }
    }

    private Map generarModelViewSaveAndUpdate(Telling telling){
        return ["tellingInstance":telling,"narrativesGenre":NarrativeGenre.list(),"tellingsType":TellingType.list()]
    }

    def delete(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound()
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            tellingService.delete(tellingInstance)
            redirect action: "index"
        }else{
            response.status = 403;
        }
    }

    def publish(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound()
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            tellingService.publish(tellingInstance)
            flash.success = "Se publicó con éxito"
            redirect action: "index"
        }else{
            response.status = 403;
        }
    }

    def handleNotPublishedException(NotPublishedException ex){
        flash.error = ex.message
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tellingInstance.label', default: 'Telling'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
