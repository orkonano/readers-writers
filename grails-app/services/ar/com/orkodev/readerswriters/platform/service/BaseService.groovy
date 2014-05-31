package ar.com.orkodev.readerswriters.platform.service

abstract class BaseService<T> {


    BaseService(){
    }

    /**
     * El objecto debe tener el id cargado, sino no va a traer nada
     * @param id
     * @return
     */
    T findById(T object) {
        object.get(object.id)
    }

    /**
     * Se debe pasar una instancia de un objeto que admita tener un id como atributo
     * @param ids
     * @param object
     * @return
     */
    List<T> findByIds(List<Long> ids, T object){
        ids.collect{it ->
                object.id = it
                findById(object)
        }
    }
}
