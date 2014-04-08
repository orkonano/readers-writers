package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import grails.plugin.cache.Cacheable

class NarrativeGenreService {

    static transactional = true

    @Cacheable('readers-writers')
    List<NarrativeGenre> getAll() {
        def query = NarrativeGenre.where {}.property('id')
        loadByIds(query.list())
    }

    List<NarrativeGenre> loadByIds(List<Long> ids){
        List<NarrativeGenre> narrativesGenre = new ArrayList<>(ids.size())
        ids.each {it -> narrativesGenre.add(findById(it))}
        narrativesGenre
    }

    NarrativeGenre findById(Long id){
        NarrativeGenre.get(id)
    }
}
