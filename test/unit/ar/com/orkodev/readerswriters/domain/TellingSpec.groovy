package ar.com.orkodev.readerswriters.domain

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

    void "test if de telling is publicable"() {
        given:
        mockForConstraintsTests Telling
        when: "el estado del telling es draft"
        def telling = new Telling(state: Telling.DRAFT)
        then: "es publicable"
        telling.isPublicable()

        when: "el estado no es draft"
        telling.state = Telling.PUBLISHED
        def telling1 = new Telling(state: Telling.ERASED)
        then: "No es publicable"
        !telling.isPublicable()
        !telling1.isPublicable()

    }

    void "test if de telling is eliminable"() {
        given:
        mockForConstraintsTests Telling
        when: "el estado del telling es draft"
        def telling = new Telling(state: Telling.DRAFT)
        then: "es eliminable"
        telling.isEliminable()

        when: "el estado no es draft"
        telling.state = Telling.PUBLISHED
        def telling1 = new Telling(state: Telling.ERASED)
        then: "No es eliminable"
        !telling.isEliminable()
        !telling1.isEliminable()

    }

    void "test if de telling is editable"() {
        given:
        mockForConstraintsTests Telling
        when: "el estado del telling es draft"
        def telling = new Telling(state: Telling.DRAFT)
        then: "es editable"
        telling.isEditable()

        when: "el estado no es draft"
        telling.state = Telling.PUBLISHED
        def telling1 = new Telling(state: Telling.ERASED)
        then: "No es editable"
        !telling.isEditable()
        !telling.isEditable()

    }

    void "test if de telling is string stat"() {
        when: "el estado del telling es draft"
        def telling = new Telling(state: Telling.DRAFT)
        then:"El estado string es BORRADOR"
        telling.getStringState() == 'Borrador'

        when: "el estado del telling es erased"
        telling = new Telling(state: Telling.ERASED)
        then:"El estado string es Eliminado"
        telling.getStringState() == 'Eliminado'

        when: "el estado del telling es publish"
        telling = new Telling(state: Telling.PUBLISHED)
        then:"El estado string es Publicado"
        telling.getStringState() == 'Publicado'

    }

}
