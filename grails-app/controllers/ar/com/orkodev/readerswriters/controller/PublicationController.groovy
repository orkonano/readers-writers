package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Telling
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class PublicationController extends BaseController {

    def tellingService
    def springSecurityService

    def save(Long tellingId) {
        Telling tellingInstance = tellingService.findById(new Telling(id: tellingId))
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            tellingService.publish(tellingInstance)
            def result = [success: true, view: [mensaje: "Se publicó con éxito"]]
            render result as JSON
        }else{
            response.status = 403;
        }
    }

    private boolean isTellingFromUserLogin(Telling telling){
        def userLogin = springSecurityService.getCurrentUser()
        return userLogin.id == telling.author.id
    }
}
