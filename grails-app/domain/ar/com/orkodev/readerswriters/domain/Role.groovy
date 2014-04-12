package ar.com.orkodev.readerswriters.domain

class Role {

    final static ROLE_ADM = "ROLE_ADM"
    final static ROLE_US = "ROLE_US"

	String authority

	static mapping = {
		cache usage: 'read-only'
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
