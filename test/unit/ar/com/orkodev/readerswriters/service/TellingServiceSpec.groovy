package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.NotErasedException
import ar.com.orkodev.readerswiters.exception.NotPublishedException
import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.domain.Telling
import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.domain.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TellingService)
@Mock(Telling)
class TellingServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test save telling"() {
        given:
        mockForConstraintsTests Telling
        def tellingService = new TellingService()
        def springSecurityService = mockFor(SpringSecurityService)
        springSecurityService.demandExplicit.getCurrentUser(1..2) { ->return new User(id:1,username: "jose",password: "dd") }
        tellingService.springSecurityService = springSecurityService.createMock()
        when: "el objeto telling no está bien cargado"
        def telling = new Telling()
        tellingService.save(telling)
        then: "Se arroja la excepcion de validacion"
        thrown(ValidationException)

        when: "el objeto telling  está bien cargado"
        def telling1 = new Telling(title: "ddd",text: "dasdasd",description: "dddd",narrativeGenre: new NarrativeGenre(id:1),tellingType: new TellingType(id: 1))
        tellingService.save(telling1)
        then: "Se arroja la excepcion de validacion"
        notThrown(ValidationException)
    }

    void "test save erased"() {
        given:
        mockForConstraintsTests Telling
        def tellingService = new TellingService()
        when: "el objeto telling va a borrarse"
        def telling1 = new Telling(title: "ddd",text: "dasdasd",description: "dddd",narrativeGenre: new NarrativeGenre(id:1),tellingType: new TellingType(id: 1),id:1,author: new User(id:1),state: Telling.DRAFT)
        telling1 = tellingService.delete(telling1)
        then: "El estado del objeto es borrado"
        telling1.state == Telling.ERASED
        notThrown(NotErasedException)

        when: "El objeto del estado está en estado eliminado y va a ser elminado"
        telling1 = new Telling(title: "ddd",text: "dasdasd",description: "dddd",narrativeGenre: new NarrativeGenre(id:1),tellingType: new TellingType(id: 1),id:1,author: new User(id:1),state: Telling.ERASED)
        telling1 = tellingService.delete(telling1)
        then: "Se arroja una excepcion runtime que no puede ser eliminado"
        thrown(NotErasedException)
    }

    void "test save published"() {
        given:
        mockForConstraintsTests Telling
        def tellingService = new TellingService()
        when: "el objeto telling está en estado borrador y va a ser publicado"
        def telling1 = new Telling(title: "ddd",text: "dasdasd",description: "dddd",narrativeGenre: new NarrativeGenre(id:1),tellingType: new TellingType(id: 1),id:1,author: new User(id:1),state: Telling.DRAFT)
        telling1 = tellingService.publish(telling1)
        then: "El estado del objeto es publicado"
        telling1.state == Telling.PUBLISHED
        notThrown(NotPublishedException)

        when: "El objeto del estado está en estado eliminado y va a ser publicado"
        telling1 = new Telling(title: "ddd",text: "dasdasd",description: "dddd",narrativeGenre: new NarrativeGenre(id:1),tellingType: new TellingType(id: 1),id:1,author: new User(id:1),state: Telling.PUBLISHED)
        telling1 = tellingService.publish(telling1)
        then: "Se arroja una excepcion runtime que no puede ser publicado"
        thrown(NotPublishedException)

        when: "El objeto del estado está en estado publicado y va a ser publicado"
        telling1 = new Telling(title: "ddd",text: "dasdasd",description: "dddd",narrativeGenre: new NarrativeGenre(id:1),tellingType: new TellingType(id: 1),id:1,author: new User(id:1),state: Telling.ERASED)
        telling1 = tellingService.publish(telling1)
        then: "Se arroja una excepcion runtime que no puede ser publicado"
        thrown(NotPublishedException)
    }
}
