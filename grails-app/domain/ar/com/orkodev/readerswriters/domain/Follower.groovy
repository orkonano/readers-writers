package ar.com.orkodev.readerswriters.domain

class Follower {

    User following
    User author
    Date dateCreated

    static constraints = {
        author nullable: false, unique: 'following'
        following nullable: false
    }

    static mapping = {
        cache: true
    }

}
