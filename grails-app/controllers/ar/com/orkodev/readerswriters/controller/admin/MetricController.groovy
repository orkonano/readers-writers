package ar.com.orkodev.readerswriters.controller.admin

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADM'])
class MetricController {

    def appMetric

    def index() {
        render view: 'index', model: [appMetric: appMetric]
    }

    def metric(String controllerName){
        render view: 'metric', model: [controllerMetric: appMetric.controllersMetrics.get(controllerName)]
    }
}
