package jmagine

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.web.multipart.MultipartFile
import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {description "poi API"})

class PoisController {

    RightsService rightsService
    SpringSecurityService springSecurityService
    PoiService poiService
    ImageService imageService

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : Ne lister que les POIs que si ils appartiennent a un parcours modéré par l'utilisateur ( ou si l'utilisateur est ROLE_ADMIN ou ROLE_OP )
    @ApiDoc(operation = {
        summary "Afficher les detailles complets d'un POI "
        description "Permet de detailler un parcours"
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
    def list(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        User me = springSecurityService.getCurrentUser()
        if( parcours ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                render( view:'/pois/list', model:[ me:me, parcours:parcours, pois:parcours.pois ] )
            }
            else {
                println("403")
                response.sendError(403)
            }
        }
        else {
            println("404")
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : Ne permettre l'ajout de jmagine.POI que si il appartient a un parcours modéré par l'utilisateur ( ou si l'utilisateur est ROLE_ADMIN ou ROLE_OP )
    def add( Long p_id ) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get( p_id )
        if( parcours ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                POI previous_poi
                if( parcours.pois.size() ) previous_poi = parcours.pois[parcours.pois.size()-1]
                render( view:'/pois/add', model:[ me:me, parcours:parcours, previous_poi:previous_poi ] )
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
    // Vérification nécessaire : Ne permettre l'ajout de jmagine.POI que si il appartient a un parcours modéré par l'utilisateur ( ou si l'utilisateur est ROLE_ADMIN ou ROLE_OP )
    @ApiDoc(operation = {
        summary "Creer un POI "
        description "Permet de creer un POI"
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
    def do_add( Long p_id ) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get( p_id )
        if( parcours ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                boolean isValid = true
                MultipartFile backgroundPic = request.getFile("background_pic")

                flash.errors = [:]
                flash.fields = [:]

                if( !params.title || params.title == '') {
                    isValid = false;
                    flash.errors.put( 'title', 'missing' )
                }
                else flash.fields.put( 'title', params.title )

                if( !params.content || params.content == '') {
                    isValid = false
                    flash.errors.put( 'content', 'missing' )
                }
                else flash.fields.put( 'content', params.content )

                if( !params.tagQr && !params.tagNfc && !params.tagGeo && !params.tagSns ) {
                    isValid = false
                    flash.errors.put( 'poi_tags', 'missing' )
                }
                else {
                    if( params.tagQr ) flash.fields.put( 'tagQr', params.tagQr )
                    if( params.tagNfc ) flash.fields.put( 'tagNfc', params.tagNfc )
                    if( params.tagGeo ) flash.fields.put( 'tagGeo', params.tagGeo )
                    if( params.tagSns ) flash.fields.put( 'tagSns', params.tagSns )
                }

                if( params.image_type == 'librairy' ) {
                    if( !params.image_id ) {
                        isValid = false
                        flash.errors.put( 'image', 'invalid' )
                    }
                    else {
                        FileContainer bg = FileContainer.get(params.image_id)
                        if( bg && (bg.parcours == parcours )) {
                            flash.fields.put('image_type', params.image_type )
                            flash.fields.put('image_id', params.image_id )
                        }
                        else {
                            isValid = false
                            flash.errors.put( 'image', 'invalid' )
                        }
                    }
                }
                else if( params.image_type == 'upload' && backgroundPic && !backgroundPic.empty ) {
                    if( !imageService.isValidMimeType( backgroundPic ) ) {
                        isValid = false
                        flash.errors.put( 'image', 'invalid' )
                    }
                }
                else {
                    isValid = false
                    flash.errors.put( 'image', 'missing' )
                }

                if( !params.lng || !params.lat || !params.address ) {
                    isValid = false
                    flash.errors.put( 'map', 'missing' )
                }
                else {
                    flash.fields.put( 'lat', params.lat )
                    flash.fields.put( 'lng', params.lng )
                    flash.fields.put( 'address', params.address )

                }

                if( isValid ) {
                    POI new_poi = poiService.createPOI(
                            parcours:parcours, author:me, title:params.title, content:params.content,
                            address:params.address, lat:params.lat, lng:params.lng, background_picture:backgroundPic,
                            imageType: params.image_type, imageId: params.image_id,
                            tagNfc: params.tagNfc, tagQr: params.tagQr, tagSns: params.tagSns, tagGeo: params.tagGeo )
                    redirect( controller: 'pois', action:'list', params:[p_id:parcours.id] )
                }
                else {
                    redirect( controller: 'pois', action:'add', params:[p_id:parcours.id] )
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
    // Vérification nécessaire :  On ne peut éditer que les parcours dont on est le modérateur, a moins d'être ROLE_ADMIN ou ROLE_OP

    def edit( Long p_id, Long poi_id ) {
        Parcours parcours = Parcours.get( p_id )
        POI poi = POI.get( poi_id )
        User me = springSecurityService.getCurrentUser()

        if( parcours && poi && ( poi.parcours == parcours ) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                render( view:'/pois/edit', model:[ me:me, parcours:parcours, poi:poi ] )
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
    // Vérification nécessaire : On ne peut éditer que les parcours dont on est le modérateur, a moins d'être ROLE_ADMIN ou ROLE_OP
    @ApiDoc(operation = {
        summary "Metre a jours un POI "
        description "Permet de metre a jour un POI"
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
    def do_edit( Long p_id, Long poi_id ) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get( p_id )
        POI poi = POI.get( poi_id )

        if( parcours && poi && ( poi.parcours == parcours ) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                boolean isValid = true
                MultipartFile backgroundPic = request.getFile("background_pic")

                flash.errors = [:]
                flash.fields = [:]

                if( !params.title || params.title == '') {
                    isValid = false;
                    flash.errors.put( 'title', 'missing' )
                }
                else flash.fields.put( 'title', params.title )

                if( !params.content || params.content == '') {
                    isValid = false
                    flash.errors.put( 'content', 'missing' )
                }
                else flash.fields.put( 'content', params.content )

                if( !params.tagQr && !params.tagNfc && !params.tagGeo && !params.tagSns ) {
                    isValid = false
                    flash.errors.put( 'poi_tags', 'missing' )
                }
                else {
                    if( params.tagQr ) flash.fields.put( 'tagQr', params.tagQr )
                    if( params.tagNfc ) flash.fields.put( 'tagNfc', params.tagNfc )
                    if( params.tagGeo ) flash.fields.put( 'tagGeo', params.tagGeo )
                    if( params.tagSns ) flash.fields.put( 'tagSns', params.tagSns )
                }

                if( params.image_type == 'librairy' ) {
                    if( !params.image_id ) {
                        isValid = false
                        flash.errors.put( 'image', 'invalid' )
                    }
                    else {
                        FileContainer bg = FileContainer.get(params.image_id)
                        if( bg && (bg.parcours == parcours )) {
                            flash.fields.put('image_type', params.image_type )
                            flash.fields.put('image_id', params.image_id )
                        }
                        else {
                            isValid = false
                            flash.errors.put( 'image', 'invalid' )
                        }
                    }
                }
                else if( params.image_type == 'upload' && backgroundPic && !backgroundPic.empty ) {
                    if (!imageService.isValidMimeType(backgroundPic)) {
                        isValid = false
                        flash.errors.put('image', 'invalid')
                    }
                }
                else if( params.image_type=="default" ) {

                }
                else {
                    isValid = false
                    flash.errors.put( 'image', 'missing' )
                }

                if( !params.lng || !params.lat || !params.address ) {
                    isValid = false
                    flash.errors.put( 'map', 'missing' )
                }
                else {
                    flash.fields.put( 'lat', params.lat )
                    flash.fields.put( 'lng', params.lng )
                    flash.fields.put( 'address', params.address )

                }

                if( isValid ) {
                    poiService.updatePOI(
                            parcours: parcours, poi:poi, title: params.title, content: params.content,
                            lat:params.lat, lng:params.lng, address:params.address, background_picture: backgroundPic,
                            imageType: params.image_type, imageId: params.image_id,
                            tagNfc: params.tagNfc, tagQr: params.tagQr, tagSns: params.tagSns, tagGeo: params.tagGeo)
                    redirect( controller: 'pois', action:'list', params:[p_id:parcours.id] )
                }
                else {
                    redirect(controller: 'pois', action: 'edit', params: [p_id: parcours.id, poi_id:poi.id ])
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
    // Vérification nécessaire : On ne peut supprimer des POIs dans un parcours seulement si on est modérateur, a moins d'être ROLE_ADMIN ou ROLE_OP
    @ApiDoc(operation = {
        summary "Supprimer un POI "
        description "Permet de supprimer un POI"
        responses "200": {
            content "default": {
                description "success response"
                schema Response200
            }
        }, "204": {
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
    def delete( Long p_id, Long poi_id ) {
        Parcours parcours = Parcours.get( p_id )

        POI poi = POI.get( poi_id )

        def delete_image_name = poi.backgroundPic.filename
        def delete_image_path = new File(grailsApplication.config.grails.assetspath.path + delete_image_name )
        println("POI delete_image_name")
        println(delete_image_name)
        println("POI delete_image_path")
        println(delete_image_path)
        User me = springSecurityService.getCurrentUser()

        if( parcours && poi &&( poi.parcours == parcours )) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                delete_image_path.delete()
                poi.delete(flush:true)

                if( parcours.isValidated && ( parcours.pois.size() == 0 ) ) {
                    parcours.isValidated = false
                    parcours.save(flush:true)
                }
                redirect( controller: 'pois', action:'list', params:[p_id:parcours.id] )
            }
            else {
                redirect( controller: 'pois', action:'list' )
            }
        }
        else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : On ne peut éditer que les parcours dont on est le modérateur, a moins d'être ROLE_ADMIN ou ROLE_OP
    def move( Long p_id, Long poi_id, int index ) {
        User me = springSecurityService.getCurrentUser()
        Parcours parcours = Parcours.get( p_id )
        POI poi = POI.get( poi_id )

        if( parcours && poi && ( poi.parcours == parcours ) ) {
            if( rightsService.simpleRoleCheck( me, 'ROLE_ADMIN') || ( parcours in me.moderatedParcours ) ) {
                Parcours.withTransaction {
                    parcours.pois.remove(poi)
                    parcours.pois.add(index,poi)
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

    // API

    // Ouvert a tous
    @ApiDoc(operation = {
        summary "Recuperer  POI "
        description "Permet de recuperer un POI en fonction de son id et de l id du parcour "
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
    def api_get( Long p_id, Long poi_id ) {
        Parcours parcours = Parcours.get( p_id )
        POI poi = POI.get( poi_id )

        if( poi && parcours && ( poi.parcours == parcours )) {
            if( parcours.isValidated ) {
               /* withFormat {
                    xml {
                        XML.use('api_basic') {
                            render poi as XML
                        }
                    }
                }*/

                def poiRet = [:]
                poiRet.id = poi.id
                poiRet.title = poi.title
                poiRet.lat = poi.lat
                poiRet.lng = poi.lng
                poiRet.address = poi.address
                poiRet.isGeolocEnabled = poi.isGeolocEnabled
                poiRet.isSNSEnabled = poi.isSNSEnabled
                poiRet.isQREnabled = poi.isQREnabled
                poiRet.isNFCEnabled = poi.isNFCEnabled
                poiRet.address = poi.address
//                poiRet.backgroundPic = grailsApplication.config.grails.serverURL + grailsApplication.config.grails.assetspath.url + poi.backgroundPic.filename
                poiRet.backgroundPic = grailsApplication.config.grails.assetspath.url + poi.backgroundPic.filename
                render poiRet as JSON

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
