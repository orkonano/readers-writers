package ar.com.orkodev.readerswriters.cache

import grails.plugin.cache.CustomCacheKeyGenerator
import grails.plugin.cache.GrailsCacheManager
import org.springframework.beans.factory.annotation.Autowired

import java.lang.reflect.Method

/**
 * Created by orko on 4/11/14.
 */
class CacheHelperImpl implements CacheHelper {

    @Autowired
    private GrailsCacheManager grailsCacheManager
    @Autowired
    private CustomCacheKeyGenerator customCacheKeyGenerator

    public void deleteFromCache(String cacheName, Object object, String methodName, Class[] paramMethodType,
                                Object[] paramValue){
        Method method = object.getClass().getDeclaredMethod(methodName, paramMethodType)
        def key = customCacheKeyGenerator.generate(object, method, paramValue)
        grailsCacheManager.getCache(cacheName).evict(key)
    }

}
