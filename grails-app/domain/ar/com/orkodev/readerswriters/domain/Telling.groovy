package ar.com.orkodev.readerswriters.domain

class Telling implements Serializable{

    private static final long serialVersionUID = 1

    final static DRAFT = 0
    final static PUBLISHED = 1
    final static ERASED = 2

    Long id
    String title
    String description
    String text
    User author
    NarrativeGenre narrativeGenre
    TellingType tellingType
    Integer state
    Date dateCreated


    static constraints = {
        title blank: false
        description blank: false
        text blank: false
        author nullable: false
        narrativeGenre nullable: false
        tellingType nullable: false
        state nullable:true, validator:{val ->
            if (val && val != DRAFT && val != PUBLISHED && val != ERASED){
                return false
            }else{
                return true
            }
        }
    }

    static mapping = {
        text type: 'text'
        narrativeGenre fetch: 'join'
        tellingType fetch: 'join'
        cache true
    }

    def beforeInsert() {
       setDefaultState()
    }

    private def setDefaultState(){
        if (!state){
            state = DRAFT
        }
    }

    def beforeUpdate() {
        setDefaultState()
    }

    def isPublicable(){
        return this.state == Telling.DRAFT
    }

    def isEliminable(){
        return this.state == Telling.DRAFT
    }

    def isEditable(){
        return this.state == Telling.DRAFT
    }

    def getStringState(){
        switch (this.state){
            case DRAFT: return "Borrador"
            case ERASED: return "Eliminado"
            case PUBLISHED: return "Publicado"
        }
    }

    Set<User> getReaders(){
        def query = TellingLike.where {
            reader == this
        }
        query.list([sort:"dateCreated"]).collect { it.reader } as Set
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Telling telling = (Telling) o

        if (id != telling.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }
}
