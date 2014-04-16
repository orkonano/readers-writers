package ar.com.orkodev.readerswriters.domain

class FacebookUser implements Serializable {

    private static final long serialVersionUID = 1L;

    long uid
    String accessToken
    Date accessTokenExpires

    static constraints = {
        uid unique: true
    }

    static mapping = {
        user fetch: 'join'
        cache usage: 'nonstrict-read-write'
    }

    static belongsTo = [user: User]
}
