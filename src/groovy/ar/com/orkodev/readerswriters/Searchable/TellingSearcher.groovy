
package ar.com.orkodev.readerswriters.Searchable

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.domain.User

/**
 * Created by orko on 4/1/14.
 */
class TellingSearcher {

    NarrativeGenre narrativeGenre
    TellingType tellingType
    User author

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        TellingSearcher that = (TellingSearcher) o
        if (author != that.author) return false
        if (narrativeGenre != that.narrativeGenre) return false
        if (tellingType != that.tellingType) return false
        return true
    }

    int hashCode() {
        int result
        result = (narrativeGenre != null ? narrativeGenre.hashCode() : 0)
        result = 31 * result + (tellingType != null ? tellingType.hashCode() : 0)
        result = 31 * result + (author != null ? author.hashCode() : 0)
        return result
    }
}
