package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import grails.plugin.cache.Cacheable

class NarrativeGenreService {

    static transactional = true

    @Cacheable('readers-writers')
    List<NarrativeGenre> getAll() {
        NarrativeGenre.list()
    }
}
