package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.plugin.cache.Cacheable

class NarrativeGenreService extends BaseService<NarrativeGenre> {

    static transactional = true

    @Cacheable('readers-writers')
    List<NarrativeGenre> getAll() {
        def query = NarrativeGenre.where {}.property('id')
        findByIds(query.list(), new NarrativeGenre())
    }

}
