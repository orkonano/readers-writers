package ar.com.orkodev.readerswriters.metrics

import java.util.concurrent.atomic.AtomicLong

/**
 * Created by orko on 4/19/14.
 */
class Metric {

    String name
    AtomicLong totalTimeProcessor
    AtomicLong totalAccess


    public Metric(){
        totalTimeProcessor = new AtomicLong(0)
        totalAccess = new AtomicLong(0)
    }

    /**
     * Suma el tiempo de procesamiento y además suma un total de acceso
     * @param time
     */
    def addTimeProcessor(long time){
        totalTimeProcessor.addAndGet(time)
        totalAccess.addAndGet(1)
    }

    def getAvg(){
        totalAccess.get() != 0 ? totalTimeProcessor.get() / totalAccess.get() : 0
    }
}
