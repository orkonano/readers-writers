package ar.com.orkodev.readerswriters.controller

import static org.springframework.http.HttpStatus.NOT_FOUND

abstract class  BaseController {

    protected void notFound(String codeMessageArgument, String defaultArgument) {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: ('default.not.found.message'), args: [message(code: codeMessageArgument, default: defaultArgument), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
