package ar.com.orkodev.readerswriters.controller.admin

import ar.com.orkodev.readerswriters.metrics.AppMetric
import ar.com.orkodev.readerswriters.metrics.ControllerMetric
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MetricController)
class MetricControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test index acion"() {
        given:
        AppMetric appMetric = Mock(AppMetric)
        controller.appMetric = appMetric
        when: "Cuando se llama el action index, con todas las condiciones exitosas"
        controller.index()
        then: "La response es exitosa"
        response.status == 200
        view == '/metric/index'
        model
        model.appMetric
        model.appMetric == appMetric
    }

    void "test metric acion"() {
        given:
        def nameController = "home"
        def controllerMetric = new ControllerMetric()
        def appMetric = new AppMetric(controllersMetrics: [(nameController): controllerMetric])
        controller.appMetric = appMetric
        when: "Cuando se llama el action index, con todas las condiciones exitosas"
        controller.metric(nameController)
        then: "La response es exitosa"
        response.status == 200
        view == '/metric/metric'
        model
        model.controllerMetric
        model.controllerMetric == controllerMetric

        when: "Si se pasa un controller name que no existe"
        response.reset()
        controller.metric("otro")
        then: "La response es exitosa, pero el model vacío"
        response.status == 200
        view == '/metric/metric'
        model
        !model.controllerMetric
    }
}
