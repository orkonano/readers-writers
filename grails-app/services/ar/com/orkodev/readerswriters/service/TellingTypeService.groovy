package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.plugin.cache.Cacheable

class TellingTypeService extends BaseService<TellingType>{

    static transactional = true

    @Cacheable('readers-writers')
    List<TellingType> getAll() {
        def query = TellingType.where {}.property('id')
        findByIds(query.list(), new TellingType())
    }

}
