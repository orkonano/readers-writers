package ar.com.orkodev.readerswriters.metrics

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Created by orko on 4/19/14.
 */
class ControllerMetric{
    static final String DEFAULT_NAME = "sin_nombre"
    static final int DEFAULT_ACTION_CANT = 10

    ConcurrentMap<String, Metric> actionMetrics
    Metric controllerMetric

    public ControllerMetric(String controllerName){
        actionMetrics = new ConcurrentHashMap((int)Math.ceil((double) DEFAULT_ACTION_CANT / 0.75))
        controllerMetric = new Metric(name: controllerName)
    }

    def addTimeProcesor(String actionName, long time){
        controllerMetric.addTimeProcessor(time)
        actionName = actionName ?: DEFAULT_NAME
        actionName = actionName.toLowerCase()
        Metric actionMetric = new Metric(name: actionName)
        actionMetric = actionMetrics.putIfAbsent(actionName, actionMetric) ?: actionMetric
        actionMetric.addTimeProcessor(time)
    }

    def getAllActionMetric(){
        actionMetrics.values()
    }
}
