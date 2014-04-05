package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.Follower
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.exception.SameUserToCurrentException
import ar.com.orkodev.readerswriters.exception.ValidationException
import grails.plugin.cache.Cacheable

import java.lang.reflect.Method

class FollowerService {

    static transactional = true

    def springSecurityService
    def grailsApplication
    def userService
    def grailsCacheManager
    def customCacheKeyGenerator

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
        Method method = this.getClass().getDeclaredMethod("findAuthorFollowedByUser", User.class,
                Integer.class, Integer.class)
        def key = customCacheKeyGenerator.generate(this, method, follower.following, 5, 0)
        grailsCacheManager.getCache('readers-writers').evict(key)
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
        if (erased)
            cleanCacheInSave(currentFollowers)
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
        query = query.property('author.id')
        userService.loadUserByIds(query.list(params))
    }
}
