package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.exception.NotPublishedException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.springsecurity.annotation.Secured


class TellingController extends BaseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService
    def tellingService
    def tellingLikeService
    def narrativeGenreService
    def tellingTypeService


    @Secured("ROLE_US")
    def index(Integer max) {
        def userLogin = springSecurityService.getCurrentUser()
        params.max = Math.min(max ?: 10, 100)
        def (tellingList, countResult) = tellingService.listAllAuthorUserTelling(userLogin, params.max );
        respond tellingList, model: [tellingInstanceCount: countResult]
    }

    @Secured("ROLE_US")
    def list(Telling tellingSearch){
        def narrativeGenres = narrativeGenreService.getAll()
        def tellingTypes = tellingTypeService.getAll()
        if (tellingSearch == null || params.init){
           render view:"list",model: [tellingInstanceList:[], tellingInstanceCount: 0,
                   narrativesGenre: narrativeGenres, tellingsType: tellingTypes ]
        }else{
            def max = params.max?:15
            def offset = params.ofsset?:0
            def (tellingList, countResult) = tellingService.listPublished(tellingSearch,max,offset)
            render view: "list", model: [tellingInstanceList: tellingList,
                                         tellingInstanceCount: countResult,
                                         narrativesGenre: narrativeGenres,
                                         tellingsType: tellingTypes
                                        ]
        }
    }

    @Secured("ROLE_US")
    def show(Long id) {
        Telling tellingInstance = bindingById(id)
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

    private Telling bindingById(Long id){
        id != null ? tellingService.findById(new Telling(id: id)) : null
    }


    @Secured('permitAll')
    def read(Long id, String title) {
        boolean islogged = springSecurityService.isLoggedIn()
        Telling tellingInstance = bindingById(id)
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
            return
        }

        def isLike = islogged ? tellingLikeService.isLike(tellingInstance) : false
        render model:["tellingInstance": tellingInstance, isLike:isLike], view:"read"
    }

    private boolean isTellingFromUserLogin(Telling telling){
        def userLogin = springSecurityService.getCurrentUser()
        return userLogin.id == telling.author.id
    }

    @Secured("ROLE_US")
    def create() {
        render  model:generarModelViewSaveAndUpdate(new Telling()), view:'create'
    }

    @Secured("ROLE_US")
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

    @Secured("ROLE_US")
    def edit(Long id) {
        Telling tellingInstance = bindingById(id)
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

    @Secured("ROLE_US")
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
        return [tellingInstance: telling,
                narrativesGenre: narrativeGenreService.getAll(),
                tellingsType: tellingTypeService.getAll()
                ]
    }

    @Secured("ROLE_US")
    def delete(Long id) {
        Telling tellingInstance = bindingById(id)
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

    @Secured("ROLE_US")
    def publish(Long id) {
        Telling tellingInstance = bindingById(id)
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
