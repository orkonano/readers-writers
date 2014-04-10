package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.FacebookUser
import ar.com.orkodev.readerswriters.domain.User
import com.the6hours.grails.springsecurity.facebook.FacebookAuthToken
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.social.facebook.api.impl.FacebookTemplate

import java.lang.reflect.Method

@Transactional
class FacebookAuthService {

    def userService
    def grailsCacheManager
    def customCacheKeyGenerator
    def grailsApplication

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
        Method method = this.getClass().getDeclaredMethod("findUser", Long.class)
        def key = customCacheKeyGenerator.generate(this, method, user.uid)
        grailsCacheManager.getCache('readers-writers').evict(key)
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
