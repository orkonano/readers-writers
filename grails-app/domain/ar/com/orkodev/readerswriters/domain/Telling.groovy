package ar.com.orkodev.readerswriters.domain

class Telling {

    final static DRAFT = 0
    final static PUBLISHED = 1
    final static ERASED = 2

    String title
    String description
    String text
    User author
    NarrativeGenre narrativeGenre
    TellingType tellingType
    Integer state


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
        cache: true
        text type: 'text'
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
}
