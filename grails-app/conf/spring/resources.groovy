import ar.com.orkodev.readerswriters.cache.CacheHelperImpl
import ar.com.orkodev.readerswriters.metrics.AppMetric
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

// Place your Spring DSL code here
beans = {


    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan'('base-package': "ar.com.orkodev.readerswriters")

    authenticationFacebookSuccessHandler(SimpleUrlAuthenticationSuccessHandler) {
        defaultTargetUrl = '/panel/dashboard'
    }

    cacheHelper(CacheHelperImpl)

    appMetric(AppMetric){
        grailsApplication = ref('grailsApplication')
    }

}
