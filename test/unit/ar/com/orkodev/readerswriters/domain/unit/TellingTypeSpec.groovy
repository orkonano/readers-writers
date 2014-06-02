package ar.com.orkodev.readerswriters.domain.unit

import ar.com.orkodev.readerswriters.domain.TellingType
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(TellingType)
class TellingTypeSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contraint"() {
        given:
        mockForConstraintsTests TellingType
        when: "Si no se carga los campos obligatorios"
        def tellingType = new TellingType()
        then: "la validacion falla"
        !tellingType.validate()
        tellingType.hasErrors()
        tellingType.errors['name']

        when: "Si se cargan los campos obligatorios"
        tellingType = new TellingType(name: "type1")
        then: "la validacion pasa con exito"
        tellingType.validate()
        !tellingType.hasErrors()
        !tellingType.errors['name']
        tellingType.save(flush:true, failOnError: true)

        when: "Si se carga un telling type duplicado"
        tellingType = new TellingType(name: "type1")
        then: "la validacion falla"
        !tellingType.validate()
        tellingType.hasErrors()
        tellingType.errors['name']

        when: "Si se carga un telling type no duplicado"
        tellingType = new TellingType(name: "type2")
        then: "la validacion fpasa con éxito"
        tellingType.validate()
        !tellingType.hasErrors()
        !tellingType.errors['name']
        tellingType.save(flush:true, failOnError: true)

    }
}
