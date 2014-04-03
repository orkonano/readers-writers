package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.TellingType
import grails.plugin.cache.Cacheable

class TellingTypeService {

    static transactional = true

    @Cacheable('readers-writers')
    List<TellingType> getAll() {
        TellingType.list()
    }
}
