package ar.com.orkodev.readerswriters.controller.admin

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADM'])
class HibernateController {

    def sessionFactory

    def index() {
        render view: 'index', model: [statistitcs: sessionFactory.getStatistics()]
    }
}
