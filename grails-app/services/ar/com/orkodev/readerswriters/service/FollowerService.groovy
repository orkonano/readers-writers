package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswiters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswiters.exception.ValidationException
import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User

class FollowerService {

    static transactional = true

    static springSecurityService

    def followAuthor(User author) {
        User currentUser = springSecurityService.getCurrentUser()
        if (author.id == currentUser.id)
            throw new SameUserToCurrentException("No se puede seguir si el autor y el seguidor son el mismo usuario")
        def follower = new Follower(author: author, following: currentUser)
        if (!follower.validate())
            throw new ValidationException(errors: follower.errors)
        follower.save()
    }

    def leaveAuthor(User authorLeave){
        User currentUser = springSecurityService.getCurrentUser()
        def query = Follower.where {
            author.id == authorLeave.id && following.id == currentUser.id
        }
        def currentFollowers = query.find()
        def erased = false
        if (currentFollowers)
            erased = (currentFollowers.delete() == null)
        return erased
    }

    def isFollowAuthor(User authorToFind) {
        User currentUser = springSecurityService.getCurrentUser()
        def query = Follower.where {
            author.id == authorToFind.id && following.id == currentUser.id
        }
        def currentFollowers = query.find()
        currentFollowers != null
    }
}
