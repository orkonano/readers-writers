package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.exception.NotPublishedException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class TellingController extends BaseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService, tellingService, tellingLikeService, narrativeGenreService,
        tellingTypeService


    def index(Integer max) {
        def userLogin = springSecurityService.getCurrentUser()
        params.max = Math.min(max ?: 10, 100)
        def (tellingList, countResult) = tellingService.listAllAuthorUserTelling(userLogin, params.max );
        respond tellingList, model: [tellingInstanceCount: countResult]
    }

    def list(Telling tellingSearch){
        if (tellingSearch == null || params.init){
           render view:"list",model: [tellingInstanceList:[],tellingInstanceCount: 0,narrativesGenre:NarrativeGenre.list(),tellingsType:TellingType.list()]
        }else{
            def max = params.max?:15
            def offset = params.ofsset?:0
            def (tellingList, countResult) = tellingService.listPublished(tellingSearch,max,offset)
            render view: "list", model: [tellingInstanceList: tellingList,
                                         tellingInstanceCount: countResult,
                                         narrativesGenre: narrativeGenreService.getAll(),
                                         tellingsType: tellingTypeService.getAll()
                                        ]
        }
    }

    def show(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            render model:["tellingInstance": tellingInstance],view:"show"
        }else{
            response.status = 403;
        }
    }

    def read(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
            return
        }
        def isLike = tellingLikeService.isLike(tellingInstance)
        render model:["tellingInstance": tellingInstance,isLike:isLike],view:"read"
    }

    private boolean isTellingFromUserLogin(Telling telling){
        def userLogin = springSecurityService.getCurrentUser()
        return userLogin.id == telling.author.id
    }

    def create() {
        render  model:generarModelViewSaveAndUpdate(new Telling()), view:'create'
    }

    def save(Telling tellingInstance) {
        withForm {
            if (tellingInstance == null) {
                notFound('tellingInstance.label','Telling')
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
    }

    def edit(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
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
            notFound('tellingInstance.label','Telling')
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
        return ["tellingInstance":telling,"narrativesGenre":narrativeGenreService.getAll(),"tellingsType":tellingTypeService.getAll()]
    }

    def delete(Telling tellingInstance) {
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
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
            notFound('tellingInstance.label','Telling')
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

}
