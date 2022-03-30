package jmagine

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.web.multipart.commons.CommonsMultipartFile
import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {description "Section API"})

class SectionsController {
    ParcoursService parcoursService
    SpringSecurityService springSecurityService
    RightsService rightsService

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : Ne lister que les sections que si ils appartiennent a un parcours modéré par l'utilisateur ( ou si l'utilisateur est ROLE_ADMIN ou ROLE_OP )
    def list(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        def sectionsList = parcours.components
        User me = springSecurityService.getCurrentUser()

        if( parcours ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) )
                render(view: '/sections/list', model: [ parcours:parcours, sections_list: sectionsList, me:me ])
            else response.sendError(403)
        }
        else {
            response.sendError(404)
        }
    }

    //Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def edit(Long p_id, Long s_id) {
        Parcours parcours = Parcours.get(p_id)
        ContentComponent section = ContentComponent.get(s_id)
        User me = springSecurityService.getCurrentUser()

        if( parcours && section && ( section in parcours.components ) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) )
                render(view: '/sections/edit', model: [ parcours:parcours, section: section, me:me ])
            else
                response.sendError(403)
        }
        else {
            response.sendError(404)
        }

    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def add(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        User me = springSecurityService.getCurrentUser()

        if( parcours ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                render(view: '/sections/add', model: [ parcours:parcours, me:me ])
            }
            else {
                response.sendError(403)
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def delete(Long p_id, Long s_id) {
        Parcours parcours = Parcours.get(p_id)
        ContentComponent section = ContentComponent.get(s_id)
        User me = springSecurityService.getCurrentUser()

        if( parcours && section && ( section in parcours.components ) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                parcours.removeFromComponents( section )
                render(view: '/sections/list', model: [ parcours:parcours, sections_list: parcours.components, me:me ])
            }
            else {
                response.sendError(404)
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def do_add(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        User me = springSecurityService.getCurrentUser()

        if( parcours ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                def file
                try {
                    file = request.getFile("background_pic")
                }
                catch (e) {

                }

                if( params.title && params.title != '' && ( !file.empty || ( params.image_type == "librairy" )) )
                {
                    ContentComponent sectionInstance = parcoursService.addComponent( title: params.title, shortDesc: params.shortDesc, background_picture: file, imageType: params.image_type, imageId:params.image_id, content: params.content, parcours: parcours )
                    redirect( controller: 'sections', action: 'edit', params: [ p_id: parcours.id, s_id: sectionInstance.id ] )
                }
                else
                {
                    if (!params.title || params.title == '')
                        flash.errors =[ title: true ]
                    else if (file.empty)
                        flash.errors = [ background: true ]
                    redirect( controller: 'sections', action: 'add', params: [ p_id: parcours.id ] )
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

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def do_info_edit(Long p_id, Long s_id) {
        Parcours parcours = Parcours.get(p_id)
        ContentComponent section = ContentComponent.get(s_id)
        User me = springSecurityService.getCurrentUser()

        if( parcours && section && (section in parcours.components) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                CommonsMultipartFile file
                try {
                    file = request.getFile("background_pic")
                }
                catch (e) {

                }

                if (params.title && params.title != '' && params.content && params.content != '' )
                {
                    parcoursService.editComponent( title: params.title, background_picture: file, content: params.content, shortDesc: params.shortDesc, imageType: params.image_type, section: section, parcours: parcours, imageId: params.image_id)
                    redirect(controller: 'sections', action: 'list', params: [p_id: p_id, s_id: s_id])
                }
                else
                {
                    if (!params.title || params.title == '')
                        flash.errors = [ title: true ]
                    else if (!params.content || params.content == '' )
                        flash.errors = [ content: true ]
                    redirect(controller: 'sections', action: 'edit', params: [p_id: p_id, s_id: s_id])
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

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def move( Long p_id, Long s_id, int index ) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get( p_id )
        ContentComponent component = ContentComponent.get( s_id )

        if( parcours && component && ( parcours.components.contains(component) ) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                ContentComponent.withTransaction{
                    parcours.components.remove(component)
                    parcours.components.add(index,component)
                    parcours.save()
                }
                render( [done:true] as JSON )
            }
            else {
                response.sendError(403)
            }
        }
        else {
            response.sendError(404)
        }
    }
}
