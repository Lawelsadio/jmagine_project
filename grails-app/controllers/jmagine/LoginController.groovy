package jmagine

import grails.plugin.springsecurity.SpringSecurityService
import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {description "Login API"})

class LoginController {

    SpringSecurityService springSecurityService

    def auth() {
        User me = springSecurityService.getCurrentUser()
        if( me ) {
            redirect( controller:'parcours', action:'list')
        }
        else {
            render( view:'/login/auth', model:[error:params.login_error])
        }
    }
}
