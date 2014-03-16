import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

// Place your Spring DSL code here
beans = {
    authenticationSuccessHandler(SimpleUrlAuthenticationSuccessHandler) {
        defaultTargetUrl = '/panel/dashboard'
    }
}
