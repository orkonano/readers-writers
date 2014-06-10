package ar.com.orkodev.readerswriters.domain

class TellingLike {

    Telling telling
    User reader
    Date dateCreated

    static constraints = {
        telling nullable: false, unique: 'reader'
        reader nullable: false
        id bindable: true
    }

    static mapping = {
        cache: true
    }

}
