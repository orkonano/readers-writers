package ar.com.orkodev.readerswriters.controller

import grails.plugin.springsecurity.annotation.Secured
import org.joda.time.DateTime

@Secured('permitAll')
class HomeController {

    private static Integer MAX_LAST_TELLING = 5

    def tellingService

    def index() {
        DateTime dt = new DateTime();
        def hourNow = dt.getHourOfDay()
        def lastTelling = tellingService.findLastTellingPublish(MAX_LAST_TELLING, hourNow)
        render view: 'index', model: [tellingInstanceList: lastTelling,
                                      tellingInstanceCount: MAX_LAST_TELLING,
                                      seoDescription: "Red social de escritores y lectores"]
    }
}
