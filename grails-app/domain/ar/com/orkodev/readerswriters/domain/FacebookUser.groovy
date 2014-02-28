package ar.com.orkodev.readerswriters.domain

import ar.com.orkodev.readerswriters.domain.User

class FacebookUser {

    long uid
    String accessToken
    Date accessTokenExpires
    User user

    static constraints = {
        uid unique: true
    }

    static mapping = {
        cache: true
    }
}
