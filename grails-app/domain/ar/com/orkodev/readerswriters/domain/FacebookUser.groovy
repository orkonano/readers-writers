package ar.com.orkodev.readerswriters.domain

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
