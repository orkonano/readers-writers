package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

class FollowerService {


    def springSecurityService
    def grailsApplication
    def userService
    def cacheHelper

    @Transactional
    def followAuthor(User author) {
        User currentUser = springSecurityService.getCurrentUser()
        if (author.id == currentUser.id)
            throw new SameUserToCurrentException("No se puede seguir si el autor y el seguidor son el mismo usuario")
        def follower = new Follower(author: author, following: currentUser)
        if (!follower.validate())
            throw new ValidationException(errors: follower.errors)
        follower.save()
        cleanCacheInSave(follower)
        follower
    }

    private void cleanCacheInSave(Follower follower){
        cacheHelper.deleteFromCache('readers-writers', this, "findAuthorFollowedByUser", [User.class, Integer.class,
                Integer.class] as Class[], [follower.following, 5, 0] as Object[])
        cacheHelper.deleteFromCache('readers-writers', this, "isFollowerAuthor", [User.class, User.class] as Class[],
                [follower.author, follower.following] as Object[])
    }

    @Transactional
    def leaveAuthor(User authorLeave){
        User currentUser = springSecurityService.getCurrentUser()
        def query = Follower.where {
            author.id == authorLeave.id && following.id == currentUser.id
        }
        def currentFollowers = query.find()
        def erased = false
        if (currentFollowers)
            erased = (currentFollowers.delete() == null)
        if (erased)
            cleanCacheInSave(currentFollowers)
        return erased
    }

    @Transactional(readOnly = true)
    def isFollowAuthor(User authorToFind) {
        User currentUser = springSecurityService.getCurrentUser()
        grailsApplication.mainContext.followerService.isFollowerAuthor(authorToFind, currentUser)
    }

    @Cacheable('readers-writers')
    Boolean isFollowerAuthor(User authorToFind, User followingUser){
        def query = Follower.where {
            author.id == authorToFind.id && following.id == followingUser.id
        }
        query = query.property('id')
        query.find() != null
    }

    @Transactional(readOnly = true)
    List<User> findAuthorFollowed(Integer count = null, Integer offset = 0) {
        User currentUser = springSecurityService.getCurrentUser()
        grailsApplication.mainContext.followerService.findAuthorFollowedByUser(currentUser, count, offset)
    }

    @Cacheable('readers-writers')
    List<User> findAuthorFollowedByUser(User user, Integer count = null, Integer offset = 0){
        def query = Follower.where {
            following.id == user.id
        }
        def params = [sort: 'dateCreated', order: "desc", offset: offset]
        if (count != null){
            params.max = count
        }
        query = query.property('id')
        userService.findByIds(query.list(params), new User())
    }
}
