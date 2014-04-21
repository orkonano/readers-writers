package ar.com.orkodev.readerswriters.metrics

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Created by orko on 4/19/14.
 */
class ControllerMetric extends Metric{
    static final String DEFAULT_NAME = "sin_nombre"
    static final int DEFAULT_ACTION_CANT = 10

    ConcurrentMap<String, Metric> actionMetrics

    public ControllerMetric(){
        actionMetrics = new ConcurrentHashMap((int)Math.ceil((double) DEFAULT_ACTION_CANT / 0.75))
    }

    def addTimeProcesor(String actionName, long time){
        super.addTimeProcessor(time)
        actionName = procesarActionName(actionName)
        Metric actionMetric = new Metric(name: actionName)
        actionMetric = actionMetrics.putIfAbsent(actionName, actionMetric) ?: actionMetric
        actionMetric.addTimeProcessor(time)
    }

    def getAllActionMetric(){
        actionMetrics.values()
    }

    public static String procesarActionName(actionName){
        (actionName ?: DEFAULT_NAME).toLowerCase()
    }

    def addRenderTimeProcessor(String actionName, long time) {
        super.addRenderTimeProcessor(time)
        actionName = procesarActionName(actionName)
        Metric actionMetric = new Metric(name: actionName)
        actionMetric = actionMetrics.putIfAbsent(actionName, actionMetric) ?: actionMetric
        actionMetric.addRenderTimeProcessor(time)
    }

}
