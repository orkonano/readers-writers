package org.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.FacebookUser
import ar.com.orkodev.readerswriters.domain.User
import com.the6hours.grails.springsecurity.facebook.FacebookAuthToken
import grails.transaction.Transactional
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.social.facebook.api.impl.FacebookTemplate

@Transactional
class FacebookAuthService {

    def userService

    User createAppUser(FacebookUser fUser, FacebookAuthToken token){
        Facebook facebook = new FacebookTemplate(token.accessToken.accessToken)
        FacebookProfile fbProfile = facebook.userOperations().userProfile
        def user = new User(username: fbProfile.email,password: "facebookPass")
        user = userService.saveUser(user)
    }
}
