package ar.com.orkodev.readerswriters.domain

class User {

	transient springSecurityService
    transient String newPassword

	String username
	String password
    String firstname
    String lastname
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    static hasOne = [facebookUser:FacebookUser]

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true, email: true
		password blank: false
        firstname nullable: true
        lastname nullable: true
        facebookUser nullable: true
        newPassword nullable:true, validator: { val, obj ->
            if (!val){
                if (obj.id && !obj.facebookUser){
                    return false
                }else{
                    return true
                }
            }

        }
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
        if (newPassword){
            password = newPassword
        }
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

}
