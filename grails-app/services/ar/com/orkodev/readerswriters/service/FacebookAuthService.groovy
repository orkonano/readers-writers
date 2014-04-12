package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.cache.CacheHelper
import ar.com.orkodev.readerswriters.domain.FacebookUser
import ar.com.orkodev.readerswriters.domain.User
import com.the6hours.grails.springsecurity.facebook.FacebookAuthToken
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.social.facebook.api.impl.FacebookTemplate

@Transactional
class FacebookAuthService {

    def userService
    def grailsApplication
    @Autowired
    private CacheHelper cacheHelper

    User createAppUser(FacebookUser fUser, FacebookAuthToken token){
        Facebook facebook = new FacebookTemplate(token.accessToken.accessToken)
        FacebookProfile fbProfile = facebook.userOperations().userProfile
        def user = new User(username: fbProfile.email,password: "facebookPass")
        userService.saveUser(user)
    }

    @Cacheable('readers-writers')
    FacebookUser findUser(Long uidFind){
        def query = FacebookUser.where {
            uid == uidFind
        }
        query.get()
    }

    void afterCreate(FacebookUser user, FacebookAuthToken token){
        cacheHelper.deleteFromCache('readers-writers', this, "findUser", [Long.class] as Class[], [user.uid] as Object[])
    }

    User getAppUser(FacebookUser facebookUser){
        facebookUser.user
    }


    Collection<GrantedAuthority> getRoles(User user){
        grailsApplication.mainContext.userService.getRoles(user).collect(){
            new SimpleGrantedAuthority(it.authority)
        }
    }
}
