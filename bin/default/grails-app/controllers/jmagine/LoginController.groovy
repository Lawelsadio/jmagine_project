package jmagine

import grails.plugin.springsecurity.SpringSecurityService

class LoginController {

    SpringSecurityService springSecurityService

    def auth() {
        User me = springSecurityService.getCurrentUser()
        if( me ) {
            println "----- tonga ato"
            redirect( controller:'parcours', action:'list')
        }
        else {
            render( view:'/login/auth', model:[error:params.login_error])
        }
    }
}
