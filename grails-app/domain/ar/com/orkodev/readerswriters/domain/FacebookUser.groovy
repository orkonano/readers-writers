package ar.com.orkodev.readerswriters.domain

import ar.com.orkodev.readerswriters.domain.User

class FacebookUser {

    long uid
    String accessToken
    Date accessTokenExpires

    static belongsTo = [user: User]

    static constraints = {
        uid unique: true
    }
}
