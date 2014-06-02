package ar.com.orkodev.readerswriters.cache

/**
 * Created by orko on 5/31/14.
 */
public interface CacheHelper {

    public void deleteFromCache(String cacheName, Object object, String methodName, Class[] paramMethodType,
                                Object[] paramValue)

}