package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

class NarrativeGenreService extends BaseService<NarrativeGenre> {


    @Cacheable('readers-writers')
    @Transactional(readOnly = true)
    List<NarrativeGenre> getAll() {
        def query = NarrativeGenre.where {}.property('id')
        findByIds(query.list(), new NarrativeGenre())
    }

}
