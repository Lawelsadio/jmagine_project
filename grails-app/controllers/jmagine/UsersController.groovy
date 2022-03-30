package jmagine

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.web.multipart.MultipartFile
import swagger.grails4.openapi.ApiDoc

import java.security.SecureRandom
@ApiDoc(tag = {description "User API"})

class UsersController {
    SpringSecurityService springSecurityService
    UserService userService
    RightsService rightsService
    UtilsService utilsService

    private SecureRandom random = new SecureRandom();

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    @ApiDoc(operation = {
        summary " Lister les utilisateurs "
        description "Permet de lister tous le utilisateurs"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "400": {
            content "default": {
                description "success response with 201"
                schema Response400
            }
        }, "404": {
            content "default": {
                description "success response with 201"
                schema Response404
            }
        }
    })
    def list( ) {
        User me = springSecurityService.getCurrentUser()
        if( me ) {
            if( me.getAuthorities()[0].authority == 'ROLE_OP' ) {
//                def users = User.withCriteria {
//                    order( 'username', 'asc')
//                }
//                render( view:'/users/list', model:[me:me, users:users])
                def users = User.list(params)
                render( view:'/users/list', model:[me:me, users:users])
            }
            else if( me.getAuthorities()[0].authority == 'ROLE_ADMIN' ) {
//                jmagine.Role adminRole = jmagine.Role.findByAuthority('ROLE_ADMIN')
                Role modRole = Role.findByAuthority('ROLE_MOD')
                def roles = UserRole.findAllByRole(modRole)
                render( view:'/users/list', model:[me:me, users:roles.user])
            }
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    // Vérification nécessaire : on ne peut modifier qu'un utilisateur de rang inférieur au sien
    @ApiDoc(operation = {
        summary "Activer un Utilisateur "
        description "Permet d'acctiver un utilisteur"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "201": {
            content "default": {
                description "success response with 201"
                schema Response201
            }
        } , "204": {
            content "default": {
                description "success response with 201"
                schema Response204
            }
        } , "400": {
            content "default": {
                description "success response with 201"
                schema Response400
            }
        }, "404": {
            content "default": {
                description "success response with 201"
                schema Response404
            }
        }
    })
    def enable( Long user_id ) {
        User me = springSecurityService.getCurrentUser()
        User user = User.get( user_id )

        if( user ) {
            if( rightsService.hasHigherRole( me, user ) ) {
                user.withTransaction { status ->
                    user.withSession {
                        user.enabled = true;
                        user.save(flush:true)
                    }
                }
                redirect( controller: 'users', action:'list')
            }
            else {
                response.sendError(403)
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    // Vérification nécessaire : on ne peut modifier qu'un utilisateur de rang inférieur au sien
    @ApiDoc(operation = {
        summary "Desactiver un Utilisateur "
        description "Permet de desactiver un Utilisateur"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "201": {
            content "default": {
                description "success response with 201"
                schema Response201
            }
        } , "204": {
            content "default": {
                description "success response with 201"
                schema Response204
            }
        } , "400": {
            content "default": {
                description "success response with 201"
                schema Response400
            }
        }, "404": {
            content "default": {
                description "success response with 201"
                schema Response404
            }
        }
    })
    def disable( Long user_id ) {
        User me = springSecurityService.getCurrentUser()
        User user = User.get( user_id )
        if( user ) {
            if( rightsService.hasHigherRole( me, user ) ) {
                user.withTransaction { status ->
                    user.withSession {
                        user.enabled = false;
                        user.save(flush:true)
                    }
                }
                redirect( controller: 'users', action:'list')
            }
            else {
                response.sendError(403)
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def add( ) {
        User me = springSecurityService.getCurrentUser()
        render( view:"/users/add", model:[me:me] )
    }

    def signup(){
        render(view:"/login/signup")
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    // Vérification nécessaire : Seul les OP peuvent spéficier un role particulier
    @ApiDoc(operation = {
        summary "Creer un Utilisateur "
        description "Permet de creer un Utilisateur"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "201": {
            content "default": {
                description "success response with 201"
                schema Response201
            }
        } , "204": {
            content "default": {
                description "success response with 201"
                schema Response204
            }
        } , "400": {
            content "default": {
                description "success response with 201"
                schema Response400
            }
        }, "404": {
            content "default": {
                description "success response with 201"
                schema Response404
            }
        }
    })
    def do_add( ) {
        User me = springSecurityService.getCurrentUser()

        MultipartFile picture
        try {
            picture = request.getFile('profile_picture' )
        }
        catch(e) {}
        Role role
        Boolean valid = true
        flash.errors = [:];
        flash.fields = [:];

        flash.fields.put( 'username', params.username)
        flash.fields.put( 'email', params.email )

        if( !params.username || ( !params.username.size() ) ) {
            valid = false
            flash.errors.put( 'username', 'empty' )
        }
        else {
            User user_check = User.findByUsername(params.username)
            if( user_check ) {
                valid = false
                flash.errors.put( 'username', "unavailable")
            }
        }

        if( !params.email || !params.email.size() ) {
            valid = false
            flash.errors.put( 'email', 'empty' )
        }
//        else {
//            if( utilsService.isEmailValid( params.email.toString() ) ) {
//                User user_check = User.findByMail( params.email )
//                if( user_check ) {
//                    valid = false
//                    flash.errors.put( 'email', 'unavailable' )
//                }
//            }
//            else {
//                valid = false
//                flash.errors.put( 'email', 'invalid' )
//            }
//        }

        if( rightsService.simpleRoleCheck( me, 'ROLE_OP' ) ) {
            role = Role.findByAuthority( params.role )
            if( !role ) {
                valid = false
                flash.errors.put( 'role', 'invalid' )
            }
            else {
                flash.fields.put( 'role', params.role )
            }
        }
        else {
            role = Role.findByAuthority( 'ROLE_MOD' )
        }

        if( !params.password ) params.password = new BigInteger(130, random).toString(32);
        else {
            if( !utilsService.isValidPassword( params.password ) ) {
                valid = false
                 flash.errors.put( 'password', 'invalid' )
            }
        }

        if( valid ) {
            User new_user = userService.createUser( username:params.username, password:params.password, mail: params.email, role:role, thumbnail:picture, imageType:params.image_type )
//            TODO : Réactiver les mails
//            if( new_user ) {
//                sendMail {
//                    async true
//                    to new_user.mail
//                    subject g.message( code: 'jmagine.emails.user_creation.object' )
//                    html g.render( template:"/emails/user_creation_email", model:[user:new_user, raw_password:params.password])
//                }
//            }
            redirect( controller: 'users', action:'list' )
        }
        else {
            redirect( controller: 'users', action:'add' )
        }
    }





    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    // Vérification nécessaire : on ne peut modifier qu'un utilisateur de rang inférieur au sien
    @ApiDoc(operation = {
        summary "Metre a jours un utilisateur "
        description "Permet de metre a jour un utilisateur"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "201": {
            content "default": {
                description "success response with 201"
                schema Response201
            }
        } , "204": {
            content "default": {
                description "success response with 201"
                schema Response204
            }
        } , "400": {
            content "default": {
                description "success response with 201"
                schema Response400
            }
        }, "404": {
            content "default": {
                description "success response with 201"
                schema Response404
            }
        }
    })
    def edit( Long user_id ) {
        User me = springSecurityService.getCurrentUser()
        User user = User.get( user_id )

        if( user ) {
            if( rightsService.hasHigherRole( me, user ) ) {
                render( view:"/users/edit", model:[me:me, user:user] )
            }
            else {
                response.sendError(403)
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    // Vérification nécessaire : on ne peut modifier qu'un utilisateur de rang inférieur au sien
    @ApiDoc(operation = {
        summary "Metre a jours un User "
        description "Permet de metre a jour un User"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "201": {
            content "default": {
                description "success response with 201"
                schema Response201
            }
        } , "204": {
            content "default": {
                description "success response with 201"
                schema Response204
            }
        } , "400": {
            content "default": {
                description "success response with 201"
                schema Response400
            }
        }, "404": {
            content "default": {
                description "success response with 201"
                schema Response404
            }
        }
    })
    def do_edit( Long user_id ) {
        User me = springSecurityService.getCurrentUser()
        User user = User.get( user_id )

        if( user ) {
            if( rightsService.hasHigherRole( me, user ) || ( me == user ) ) {
                MultipartFile picture
                try {
                    picture = request.getFile('profile_picture' )
                }
                catch(e) {}
                Role role
                Boolean valid = true
                flash.errors = [:];
                flash.fields = [:];

                flash.fields.put( 'username', params.username)
                flash.fields.put( 'email', params.email )

                if( !params.username || ( !params.username.size() ) ) {
                    valid = false
                    flash.errors.put( 'username', 'empty' )
                }
                else {
                    User user_check = User.findByUsername(params.username)
                    if( user_check && (user_check!=user)) {
                        valid = false
                        flash.errors.put( 'username', "unavailable")
                    }
                }

//                if( !params.email || !params.email.size() ) {
//                    valid = false
//                    flash.errors.put( 'email', 'empty' )
//                }
//                else {
//                    if( utilsService.isEmailValid( params.email.toString() ) ) {
//                        User user_check = User.findByMail( params.email )
//                        if( user_check && ( user_check != user)) {
//                            valid = false
//                            flash.errors.put( 'email', 'unavailable' )
//                        }
//                    }
//                    else {
//                        valid = false
//                        flash.errors.put( 'email', 'invalid' )
//                    }
//                }

                if( rightsService.simpleRoleCheck( me, 'ROLE_OP' ) ) {
                    role = Role.findByAuthority( params.role )
                    if( !role ) {
                        valid = false
                        flash.errors.put( 'role', 'invalid' )
                    }
                    else {
                        flash.fields.put( 'role', params.role )
                    }
                }
                else {
                    role = Role.findByAuthority( 'ROLE_MOD' )
                }

                if( params.password ) {
                    if( params.password == params.verify_password ) {
                        if( !utilsService.isValidPassword( params.password ) ) {
                            valid = false
                            flash.errors.put( 'password', 'invalid' )
                        }
                    }
                    else {
                        valid = false
                        flash.errors.put( 'password', 'missmatch' )
                    }
                }

                if( valid ) {
                    user = userService.updateUser( user:user, username:params.username, password:params.password, mail: params.email, role:role, thumbnail:picture, imageType:params.image_type)
                    if( params.my_account ) redirect( controller: 'users', action:'my_account' )
                    else redirect( controller: 'users', action:'list' )
                }
                else {
                    if( params.my_account ) redirect( controller: 'users', action:'edit_me' )
                    else redirect( controller: 'users', action:'edit', params:[user_id:user.id] )
                }
            }
            else {
                response.sendError(403)
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def my_account() {
        User me = springSecurityService.getCurrentUser()
        render( view:"/users/my_account", model:[me:me] )
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def edit_me( ) {
        User me = springSecurityService.getCurrentUser()
        render( view:"/users/edit_me", model:[me:me, user:me] )
    }
}
