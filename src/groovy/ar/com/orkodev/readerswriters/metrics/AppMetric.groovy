package ar.com.orkodev.readerswriters.metrics
/**
 * Created by orko on 4/19/14.
 */
class AppMetric extends Metric{

    static final String DEFAULT_NAME = "sin_nombre"

    Map<String, ControllerMetric> controllersMetrics
    Metric appMetric
    def grailsApplication


    def synchronized inicializarMapas(){
        if (appMetric == null){
            //contabilizo uno mas para cuando no se registra accón ni controlador
            int totalControllers = grailsApplication.controllerClasses.size() + 1
            this.controllersMetrics = new HashMap<String, ControllerMetric>((int) Math.ceil(totalControllers / 0.75))
            for (controllerArtefact in grailsApplication.controllerClasses){
                String nameController = controllerArtefact.getName().toLowerCase()
                this.controllersMetrics.put(nameController, new ControllerMetric(name: nameController))
            }
            this.controllersMetrics.put(DEFAULT_NAME, new ControllerMetric(name: DEFAULT_NAME))
            appMetric = new Metric()
        }
    }

    def addTimeProcesor(String controllerName, String actionName, long time){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addTimeProcessor(time)
        controllerName = procesarControllerName(controllerName)
        ControllerMetric controllerMetric = controllersMetrics.get(controllerName)
        controllerMetric.addTimeProcesor(actionName, time)
    }

    def getAllControllersMetrics(){
        controllersMetrics.values()
    }

    public static String procesarControllerName(controllerName){
        (controllerName ?:DEFAULT_NAME).toLowerCase()
    }

    def addRenderTimeProcesor(String controllerName, String actionName, long time) {
        appMetric.addRenderTimeProcessor(time)
        controllerName = procesarControllerName(controllerName)
        ControllerMetric controllerMetric = controllersMetrics.get(controllerName)
        controllerMetric.addRenderTimeProcessor(actionName, time)
    }
}
