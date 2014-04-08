package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.TellingType
import grails.plugin.cache.Cacheable

class TellingTypeService {

    static transactional = true

    @Cacheable('readers-writers')
    List<TellingType> getAll() {
        def query = TellingType.where {}.property('id')
        loadByIds(query.list())
    }

    List<TellingType> loadByIds(List<Long> ids){
        List<TellingType> tellingTypes = new ArrayList<>(ids.size())
        ids.each {it -> tellingTypes.add(findById(it))}
        tellingTypes
    }

    TellingType findById(Long id){
        TellingType.get(id)
    }
}
