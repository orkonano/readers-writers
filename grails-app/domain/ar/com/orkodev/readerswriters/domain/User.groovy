package ar.com.orkodev.readerswriters.domain

class User implements Serializable{

    private static final long serialVersionUID = 8799656478674716638L;

	transient springSecurityService

    public static final String CACHE_NAME = 'USER_'

	String username
	String password
    String firstname
    String lastname
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    Date dateCreated

    static hasOne = [facebookUser:FacebookUser]
    static hasMany = [tellings:Telling]
	static transients = ['springSecurityService']
	static constraints = {
		username blank: false, unique: true, email: true
		password blank: false
        firstname nullable: true
        lastname nullable: true
        facebookUser nullable: true
	}
	static mapping = {
        password column: '`password`'
        facebookUser fetch: 'join', cache: true
        cache true
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

    Set<User> getFollowings(){
        Follower.findAllByAuthor(this,[sort:"dateCreated"]).collect { it.following } as Set
    }

    Set<User> getAuthorFollowed(){
        Follower.findAllByFollowing(this,[sort:"dateCreated"]).collect { it.author } as Set
    }

    Set<Telling> getTellings(){
        def query = TellingLike.where {
            telling == this
        }
        query.list([sort:"dateCreated"]).collect { it.telling } as Set
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof User)) return false
        User user = (User) o
        if (id == null && user.id == null) return false
        if (id != user.id) return false
        return true
    }

    int hashCode() {
        return id.hashCode()
    }
}
