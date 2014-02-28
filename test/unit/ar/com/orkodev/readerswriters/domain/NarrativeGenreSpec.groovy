package ar.com.orkodev.readerswriters.domain

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(NarrativeGenre)
class NarrativeGenreSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contraint"() {
        given:
        mockForConstraintsTests NarrativeGenre
        when: "Si no se carga los campos obligatorios"
        def narrativeGenre = new NarrativeGenre()
        then: "la validacion falla"
        !narrativeGenre.validate()
        narrativeGenre.hasErrors()
        narrativeGenre.errors['name']

        when: "Si se cargan los campos obligatorios"
        narrativeGenre = new NarrativeGenre(name: "genre1")
        then: "la validacion pasa con exito"
        narrativeGenre.validate()
        !narrativeGenre.hasErrors()
        !narrativeGenre.errors['name']
        narrativeGenre.save(flush:true, failOnError: true)

        when: "Si se carga un narrative genre duplicado"
        narrativeGenre = new NarrativeGenre(name: "genre1")
        then: "la validacion falla"
        !narrativeGenre.validate()
        narrativeGenre.hasErrors()
        narrativeGenre.errors['name']

        when: "Si se carga un narrative genre no duplicado"
        narrativeGenre = new NarrativeGenre(name: "genre2")
        then: "la validacion fpasa con éxito"
        narrativeGenre.validate()
        !narrativeGenre.hasErrors()
        !narrativeGenre.errors['name']
        narrativeGenre.save(flush:true, failOnError: true)

    }
}
