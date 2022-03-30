package jmagine

import grails.converters.JSON

class APIGenericResponseController {

    def error() {
        withFormat {
            json {
                JSON.use('api_basic') {
                    if( session['rest_errors']  ) {
//                        r.code = RestCodes[params.reason]
//                        r.fields = session['rest_errors']
//                        render errors:params.errors, status:status, text:r as JSON
                    }
                    else {
//                        render status:status, text:RestCodes[params.reason] as JSON
                    }
                }
            }
        }
    }
}
