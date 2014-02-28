package ar.com.orkodev.readerswriters.domain

class User {

	transient springSecurityService

	String username
	String password
    String firstname
    String lastname
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
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
        cache: true
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

}
