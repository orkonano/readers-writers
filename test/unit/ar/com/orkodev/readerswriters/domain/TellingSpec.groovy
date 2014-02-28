package ar.com.orkodev.readerswriters.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Telling)
class TellingSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contraint"() {
        given:
        mockForConstraintsTests Telling
        when: "Si no se carga los campos obligatorios"
        def telling = new Telling()
        then: "la validacion falla"
        !telling.validate()
        telling.hasErrors()
        telling.errors['title']
        telling.errors['description']
        telling.errors['text']
        telling.errors['author']
        telling.errors['narrativeGenre']
        telling.errors['tellingType']
        !telling.errors['state']

        when: "Si se cargan todos los campos obligatorios, pero cargo un estado no permitido"
        telling = new Telling(title: "a",description: "d",text: "das",author: new User(),narrativeGenre: new NarrativeGenre(),tellingType: new TellingType(),state: 4)
        then: "la validacion falla"
        !telling.validate()
        telling.hasErrors()
        !telling.errors['title']
        !telling.errors['description']
        !telling.errors['text']
        !telling.errors['author']
        !telling.errors['narrativeGenre']
        !telling.errors['tellingType']
        telling.errors['state']

        when: "Si se cargan todos los campos obligatorios"
        telling = new Telling(title: "a",description: "d",text: "das",author: new User(),narrativeGenre: new NarrativeGenre(),tellingType: new TellingType())
        then: "la validacion falla"
        telling.validate()
        !telling.hasErrors()
        !telling.errors['title']
        !telling.errors['description']
        !telling.errors['text']
        !telling.errors['author']
        !telling.errors['narrativeGenre']
        !telling.errors['tellingType']
        !telling.errors['state']
        telling.save(flush: true,failOnError: true)
        telling.state == Telling.DRAFT
    }


}
