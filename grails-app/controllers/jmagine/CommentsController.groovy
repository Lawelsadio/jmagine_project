package jmagine

import grails.plugin.springsecurity.SpringSecurityService
import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {description "Commenet API"})

class CommentsController {
    SpringSecurityService springSecurityService

    def list( Long p_id ) {
        Parcours parcours = Parcours.get( p_id )
        User me = springSecurityService.getCurrentUser()
        if( parcours ) {
            def comments = Comment.withCriteria {
                or {
                    eq( 'parcours', parcours )
                    'in'( 'poi', parcours.pois )
                }
                order( 'dateCreated', 'asc' )
            }
            render( view:'/comments/list', model:[me:me, parcours:parcours, comments:comments] )
        }
    }

    def list_from_poi( Long p_id, Long poi_id ) {
        Parcours parcours = Parcours.get( p_id )
        POI poi = POI.get( poi_id )

        User me = springSecurityService.getCurrentUser()

        if( parcours && poi ) {
            def comments = Comment.withCriteria {
                eq( 'poi', poi )
                order( 'dateCreated', 'asc' )
            }
            render( view:'/comments/list', model:[me:me, poi:poi, parcours:parcours, comments:comments] )
        }
    }
}
