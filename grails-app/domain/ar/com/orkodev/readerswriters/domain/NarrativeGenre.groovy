package ar.com.orkodev.readerswriters.domain

class NarrativeGenre {

    String name
    NarrativeGenre parent

    static constraints = {
        name blank: false, unique: true
        parent nullable: true
    }

    static hasMany = [subNarrativeGenres: NarrativeGenre]

    static mapping = {
        subNarrativeGenres joinTable: false, column: "parent_id"
    }

    public String toString(){
        return name.toLowerCase().capitalize()
    }
}
