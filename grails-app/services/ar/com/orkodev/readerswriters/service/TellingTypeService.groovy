package ar.com.orkodev.readerswriters.service

import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.platform.service.BaseService
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

class TellingTypeService extends BaseService<TellingType>{


    @Cacheable('readers-writers')
    @Transactional(readOnly = true)
    List<TellingType> getAll() {
        def query = TellingType.where {}.property('id')
        findByIds(query.list(), new TellingType())
    }

}
