import ar.com.orkodev.readerswriters.metrics.AppMetric

// Place your Spring DSL code here
beans = {


    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan'('base-package': "ar.com.orkodev.readerswriters")

//    authenticationSuccessHandler(SimpleUrlAuthenticationSuccessHandler) {
//        defaultTargetUrl = '/panel/dashboard'
//    }

    appMetric(AppMetric){
        grailsApplication = ref('grailsApplication')
    }

}
