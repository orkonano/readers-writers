package ar.com.orkodev.readerswriters.controller

import ar.com.orkodev.readerswriters.domain.Telling
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class PublishController extends BaseController {

    def tellingService
    def springSecurityService

    def save(Long id) {
        Telling tellingInstance = tellingService.findById(new Telling(id: id))
        if (tellingInstance == null) {
            notFound('tellingInstance.label','Telling')
            return
        }
        if (isTellingFromUserLogin(tellingInstance)){
            tellingService.publish(tellingInstance)
            flash.success = "Se publicó con éxito"
            redirect controller: 'telling', action: "index"
        }else{
            response.status = 403;
        }
    }

    private boolean isTellingFromUserLogin(Telling telling){
        def userLogin = springSecurityService.getCurrentUser()
        return userLogin.id == telling.author.id
    }
}
