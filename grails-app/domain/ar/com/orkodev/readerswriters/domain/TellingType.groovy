package ar.com.orkodev.readerswriters.domain

class TellingType implements Serializable{

    String name

    static constraints = {
        name blank: false, unique: true
        id bindable: true
    }

    static mapping = {
        cache usage: 'read-only'
    }

    public String toString(){
        return name?.toLowerCase()?.capitalize()
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        TellingType that = (TellingType) o
        if (id != that.id) return false
        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }
}
