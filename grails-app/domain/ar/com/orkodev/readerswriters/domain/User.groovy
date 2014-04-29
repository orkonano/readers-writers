package ar.com.orkodev.readerswriters.domain

class User implements Serializable{

    private static final long serialVersionUID = 8799656478674716638L;

	transient springSecurityService

	String username
	String password
    String firstname
    String lastname
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    Date dateCreated
    FacebookUser facebook

    static hasMany = [tellings:Telling]
	static transients = ['springSecurityService']
	static constraints = {
		username blank: false, unique: true, email: true
		password blank: false
        firstname nullable: true
        lastname nullable: true
        facebook nullable: true, unique: true
        id bindable: true
	}
	static mapping = {
        password column: '`password`'
        facebook lazy: false, cache: true
        cache usage: 'nonstrict-read-write'
	}

	Set<Role> getAuthorities() {
        def query = UserRole.where {
            user == this
        }
		query.list().collect { it.role } as Set
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

    def getNombreAMostrar(){
        (this.firstname && this.lastname) ? (this.firstname + " " + this.lastname) : this.username
    }
}
