package filters

import ar.com.orkodev.readerswriters.metrics.AppMetric
import ar.com.orkodev.readerswriters.metrics.ControllerMetric
import ar.com.orkodev.readerswriters.metrics.Metric

class AppFilters {

    def appMetric
    def springSecurityService
    private static final String START_TIME_ATTRIBUTE = 'Controller__START_TIME__'
    private static final String AFTER_TIME_ATTRIBUTE = 'Controller__AFTER_TIME__'

    def filters = {

        currentUser(controller: '*', action: '*'){
            after = { Map model ->
                if (!request.xhr) {
                    if (model == null){
                        model = new HashMap()
                    }
                    def currentUser = springSecurityService.getCurrentUser()
                    if (currentUser != null){
                        model.put('currentUser', currentUser)
                    }
                }
            }
        }

        metricAction(controller: '*', controllerExclude: 'metric', action: '*') {
            before = {
                long start = System.currentTimeMillis()
                request[START_TIME_ATTRIBUTE] = start
            }
            after = { Map model ->
                long start = request[START_TIME_ATTRIBUTE]
                long end = System.currentTimeMillis()
                def timeProcess = end - start
                request[AFTER_TIME_ATTRIBUTE] = end
                appMetric.addTimeProcesor(controllerName, actionName, timeProcess)
                def controller = AppMetric.procesarControllerName(controllerName)
                def action = ControllerMetric.procesarActionName(actionName)
                Metric actualMetric = appMetric.controllersMetrics.get(controller).actionMetrics.get(action)
                if (model == null){
                    model = new HashMap()
                }
                model.put('metric', actualMetric)
                model.put('metricTime', timeProcess)
            }
            afterView = { Exception e ->
                long start = request[AFTER_TIME_ATTRIBUTE]
                long end = System.currentTimeMillis()
                def timeProcess = end - start
                appMetric.addRenderTimeProcesor(controllerName, actionName, timeProcess)
                if (e != null) {
                    appMetric.addException(controllerName, actionName)
                }
            }
        }
    }
}
