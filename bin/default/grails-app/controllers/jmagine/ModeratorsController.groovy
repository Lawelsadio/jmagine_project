package jmagine


import grails.plugin.springsecurity.SpringSecurityService

class ModeratorsController {
    RightsService rightsService
    SpringSecurityService springSecurityService

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def list(Long p_id) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get(p_id)
        if( parcours ) {
            if( me.getAuthorities()[0].authority == 'ROLE_OP' )
            {
                def user_list_toadd = User.list()
                user_list_toadd.collect().each
                {
                    User user ->
                        if (user.getAuthorities()[0].authority == 'ROLE_OP' || user.getAuthorities()[0].authority == 'ROLE_ADMIN')
                            user_list_toadd.remove(user)
                }
                render( view:'/moderators/list', model:[me:me, users:parcours.moderators, parcours:parcours,  users_toadd: user_list_toadd])
            }
            else if( me.getAuthorities()[0].authority == 'ROLE_ADMIN' ) {
                def user_list = parcours.moderators.collect()
                parcours.moderators.each {
                    User userInstance ->
                    if (!userInstance.getAuthorities()[0].authority == 'ROLE_ADMIN' && !userInstance.getAuthorities()[0].authority == 'ROLE_MOD')
                        user_list.remove(userInstance)
                }
                render( view:'/moderators/list', model:[me:me, users:user_list, parcours:parcours])
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def add(Long p_id) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get(p_id)
        if( parcours )
        {
            def user_list = User.list()
            user_list.collect().each
            {
                User user ->
                    if (user.getAuthorities()[0].authority == 'ROLE_OP' || user.getAuthorities()[0].authority == 'ROLE_ADMIN')
                        user_list.remove(user)
            }
            render( view:'/moderators/add', model:[me:me, users:user_list, parcours:parcours])
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def do_add(Long p_id, Long user_id) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get(p_id)
        User user = User.get(user_id)
        boolean validReturn = false

        if( user && parcours ) {
            switch (me.getAuthorities()[0].authority)
            {
                case 'ROLE_OP':
                case 'ROLE_ADMIN':
                    if( !parcours.moderators.contains(user) && user.getAuthorities()[0].authority == 'ROLE_MOD' ) {
                        parcours.addToModerators(user)
                        validReturn = true
                    }
                    break;
            }
            if (validReturn) {
                parcours.withTransaction { status ->
                    parcours.withSession {
                        parcours.save(flush:true, failOnError: true)
                        render(status: 200, text: message(code: "jmagine.return.msg.mod.added", args: [user.username]))
                    }
                }
            }
            else
                render( status: 401, text: message(code: "jmagine.return.msg.mod.rejected"))
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def do_remove(Long p_id, Long user_id) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get(p_id)
        User user = User.get(user_id)
        boolean validReturn = false

        if( parcours && user ) {
            switch (me.getAuthorities()[0].authority)
            {
                case 'ROLE_OP':
                case 'ROLE_ADMIN':
                    if ( parcours.moderators.contains(user) && user.getAuthorities()[0].authority == 'ROLE_MOD' ) {
                        parcours.removeFromModerators(user)
                        validReturn = true
                    }
                    break;
                case 'ROLE_MOD':
                    if ( parcours.moderators.contains(me) && parcours.moderators.contains(user) && user.getAuthorities()[0].authority == 'ROLE_MOD') {
                        parcours.removeFromModerators(user)
                        validReturn = true
                    }
                    break;
            }
            if (validReturn) {
                parcours.withTransaction { status ->
                    parcours.withSession {
                        parcours.save(flush:true, failOnError: true)
                        render(status: 200, text: message(code: "jmagine.return.msg.mod.removed", args: [user.username]))
                    }
                }
            }
            else
                render( status: 401, text: message(code: "jmagine.return.msg.mod.rejected"))
        }
        else {
            response.sendError(404)
        }
    }
}
