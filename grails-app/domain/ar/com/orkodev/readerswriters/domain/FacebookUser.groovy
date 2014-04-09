package ar.com.orkodev.readerswriters.domain

class FacebookUser implements Serializable {

    long uid
    String accessToken
    Date accessTokenExpires
    User user

    static constraints = {
        uid unique: true
    }

    static mapping = {
        user fetch: 'join', cache: true
        cache: true
    }
}
