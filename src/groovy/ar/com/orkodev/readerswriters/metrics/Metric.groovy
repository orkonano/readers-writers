package ar.com.orkodev.readerswriters.metrics

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by orko on 4/19/14.
 */
class Metric {

    String name
    AtomicLong totalAccess
    AtomicLong timeProcessor
    AtomicLong renderTimeProcessor
    AtomicInteger lastTimeProcessor
    AtomicInteger lastRenderTimeProcessor
    AtomicInteger totalException


    public Metric(){
        timeProcessor = new AtomicLong(0)
        totalAccess = new AtomicLong(0)
        renderTimeProcessor = new AtomicLong(0)
        lastTimeProcessor = new AtomicInteger(0)
        lastRenderTimeProcessor = new AtomicInteger(0)
        totalException = new AtomicInteger(0)
    }

    /**
     * Suma el tiempo de procesamiento y además suma un total de acceso
     * @param time
     */
    def addTimeProcessor(long time){
        timeProcessor.addAndGet(time)
        lastTimeProcessor.set((int)time)
    }

    def incrementAccess(){
        totalAccess.addAndGet(1)
    }

    def addRenderTimeProcessor(long time){
        renderTimeProcessor.addAndGet(time)
        lastRenderTimeProcessor.set((int)time)
    }

    def addException(){
        totalException.incrementAndGet()
    }

    def getAvg(){
        totalAccess.get() != 0 ? timeProcessor.get() / totalAccess.get() : 0
    }

    def getTotalTimeProcessor(){
        timeProcessor.get() + renderTimeProcessor.get()
    }

    def getLastTotalTimeProcessor(){
        lastTimeProcessor.get() + lastRenderTimeProcessor.get()
    }

    def getTotalAvg(){
        totalAccess.get() != 0 ? totalTimeProcessor / totalAccess.get() : 0
    }

    def getRenderAvg(){
        totalAccess.get() != 0 ? renderTimeProcessor.get() / totalAccess.get() : 0
    }

    def getExceptionPercentage(){
        totalAccess.get() != 0 ? (totalException.get() / totalAccess.get())*100 : 0
    }
}
