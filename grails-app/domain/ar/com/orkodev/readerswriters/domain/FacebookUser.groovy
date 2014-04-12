package ar.com.orkodev.readerswriters.domain

class FacebookUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
