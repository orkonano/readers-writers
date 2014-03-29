package ar.com.orkodev.readerswriters.exception

/**
 * Created by orko on 05/03/14.
 */
class SameUserToCurrentException extends RuntimeException{

    public SameUserToCurrentException(String message){
        super(message)
    }

}
