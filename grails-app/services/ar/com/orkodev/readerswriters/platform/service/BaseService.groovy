package ar.com.orkodev.readerswriters.platform.service

import java.lang.reflect.ParameterizedType

abstract class BaseService<T> {

    static transactional = true

    protected Class<T> persistenceClass

    BaseService(){
        persistenceClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    T findById(Long id) {
        persistenceClass.get(id)
    }
}
