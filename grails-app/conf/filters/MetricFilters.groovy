package filters

import ar.com.orkodev.readerswriters.metrics.AppMetric
import ar.com.orkodev.readerswriters.metrics.ControllerMetric
import ar.com.orkodev.readerswriters.metrics.Metric

class MetricFilters {

    def appMetric
    private static final String START_TIME_ATTRIBUTE = 'Controller__START_TIME__'

    def filters = {
        metricAction(controller: '*', action: '*') {
            before = {
                long start = System.currentTimeMillis()
                request[START_TIME_ATTRIBUTE] = start
            }
            after = { Map model ->
                long start = request[START_TIME_ATTRIBUTE]
                long end = System.currentTimeMillis()
                def timeProcess = end - start
                appMetric.addTimeProcesor(controllerName, actionName, timeProcess)
                def controller = controllerName ?: AppMetric.DEFAULT_NAME
                def action = actionName ?: ControllerMetric.DEFAULT_NAME
                Metric actualMetric = appMetric.controllersMetrics.get(controller).actionMetrics.get(action)
                if (model == null){
                    model = new HashMap()
                }
                model.put('metric', actualMetric)
                model.put('metricTime', timeProcess)
            }
            afterView = { Exception e ->

            }
        }
    }
}
