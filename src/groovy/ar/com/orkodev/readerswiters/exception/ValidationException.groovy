package ar.com.orkodev.readerswiters.exception

import org.springframework.validation.Errors

/**
 * Created by orko on 22/02/14.
 */
class ValidationException extends RuntimeException{
    Errors errors
}
