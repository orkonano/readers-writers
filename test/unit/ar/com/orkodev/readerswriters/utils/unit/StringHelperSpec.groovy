package ar.com.orkodev.readerswriters.utils.unit

import ar.com.orkodev.readerswriters.utils.StringHelper
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class StringHelperSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test quitarAcentos method"() {
        given: "Inicializo la cadena con acentos"
        def str1 = "hola cómo andás chabón bravo"
        def str1Expected = "hola como andas chabon bravo"
        when: "Sa llama al método estático para quitar acentos"
        def strResult = StringHelper.quitarAcentos(str1)
        then: "El resultado es exitoso"
        strResult == str1Expected
        strResult != str1
    }

    void "test cortarStringPorEspacio method"() {
        given: "Inicializo diferentes string, algunos largos y otros cortos"
        def str1 = """En menos de 24 horas en Santa Fe hubo cuatro sangrientos asesinatos.
            Tres de esos casos registran un mismo modus operandi: la persecución y la ejecución de la víctima a sangre fría.
            Todo sucedió entre el domingo y este mediodía. En el primero de los hechos, un adolescente de 14
            años falleció tras ser alcanzado por dos proyectiles mientras huía de un grupo de jóvenes que
            dispararon sin piedad, por los pasillos del barrio Guadalupe Oeste.
            """
        when: "Sa llama al método estático para cortar la palabra, con el punto de corte en 10"
        def str1Expected = "En menos de"
        def strResult = StringHelper.cortarStringPorEspacio(str1, 10)
        then: "El resultado es la siguiente palabra cortada"
        strResult == str1Expected
        strResult != str1
        strResult.replaceAll(/\s/, '').size() <= 10

        when: "Sa llama al método estático para cortar la palabra con el punto de corte por default"
        str1Expected = """En menos de 24 horas en Santa Fe hubo cuatro sangrientos asesinatos.
            Tres de esos casos registran un mismo modus"""
        strResult = StringHelper.cortarStringPorEspacio(str1)
        then: "El resultado es la siguiente palabra cortada en 100 caracteres"
        strResult == str1Expected
        strResult != str1
        strResult.replaceAll(/\s/, '').size() <= 100
    }

    void "test convertStringToFriendlyUrl method"() {
        given: "Inicializo diferentes string, algunos largos y otros cortos"
        def str1 = """En menos de 24 horas en Santa Fé hubo cuatro sangrientos asesinatos.
            Tres de esos casos registran un mismo modus operandi: la persecución y la ejecución de la víctima a sangre fría.
            Todo sucedió entre el domingo y este mediodía. En el primero de los hechos, un adolescente de 14
            años falleció tras ser alcanzado por dos proyectiles mientras huía de un grupo de jóvenes que
            dispararon sin piedad, por los pasillos del barrio Guadalupe Oeste.
            """
        when: "Sa llama al método estático que transforma al string en friendly url"
        def str1Expected = """en-menos-de-24-horas-en-santa-fe-hubo-cuatro-sangrientos-asesinatos-tres-de-esos-casos-registran-un-mismo-modus"""
        def strResult = StringHelper.convertStringToFriendlyUrl(str1)
        then: "El resultado es la siguiente palabra cortada"
        strResult == str1Expected
        strResult != str1
    }
}
