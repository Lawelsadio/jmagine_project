package jmagine

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import grails.util.Holders
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile

class ParcoursController {
    ParcoursService parcoursService
    SpringSecurityService springSecurityService
    PoiService poiService
    ImageService imageService
    RightsService rightsService // pour tchequer les roles

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : Ne lister que les parcours modérés si l'user est ROLE_MOD
    def list() {
        User me = springSecurityService.getCurrentUser()
        if (rightsService.simpleRoleCheck(me, 'ROLE_ADMIN')) {
            def parcours = Parcours.withCriteria {
                order('dateCreated', 'desc')
            }
            render(view: '/parcours/list', model: [me: me, parcours_list: parcours])
        } else {
            render(view: '/parcours/list', model: [me: me, parcours_list: me.moderatedParcours])
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : On ne peut éditer que les parcours dont on est le modérateur, a moins d'être ROLE_ADMIN ou ROLE_OP
    def edit(Long p_id) {
        def parcours = Parcours.get(p_id)
        if (parcours) {
            User me = springSecurityService.getCurrentUser()
            if (rightsService.simpleRoleCheck(me, 'ROLE_ADMIN')) {
                render(view: '/parcours/edit', model: [me: me, parcours: parcours])
            } else if (parcours in me.moderatedParcours) {
                render(view: '/parcours/edit', model: [me: me, parcours: parcours])
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def add() {
        User me = springSecurityService.getCurrentUser()
        render(view: '/parcours/add', model: [me: me])
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP
    def do_add() {
        MultipartFile file
        try {
            file = request.getFile("background_pic")
            String path = request.getSession().getServletContext().getRealPath("").concat("/uploadFile");

            def test = file.inputStream.
            println("path")
            println(path)
          //  println("path2")
           // println(path2)


            def test2 = file.
            println("test2")
            println(test2)

        }
        catch (e) {
            e.printStackTrace()
        }
        User me = springSecurityService.getCurrentUser()

        if (params.title && params.title != '') {
            Parcours parcours = parcoursService
                    .createParcours(title: params.title, description: params.description, imageType: params.image_type, background_picture: file, author: me)
            redirect(controller: 'parcours', action: 'edit', params: [p_id: parcours.id])
        } else {
            flash.errors = [title: true];
            flash.fields = [
                    description: params.description
            ]
            redirect(controller: 'parcours', action: 'add')
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : On ne peut éditer que les parcours dont on est le modérateur, a moins d'être ROLE_ADMIN ou ROLE_OP
    def do_info_edit(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        if (parcours) {
            User me = springSecurityService.getCurrentUser()
            if (rightsService.simpleRoleCheck(me, 'ROLE_ADMIN') || (parcours in me.moderatedParcours)) {
                MultipartFile file
                try {
                    file = request.getFile("background_pic")
                }
                catch (e) {
                    e.printStackTrace()
                }

                if (params.title && params.title != '') {
                    parcoursService.updateParcours(parcours: parcours, title: params.title, description: params.description, background_picture: file, imageType: params.image_type, imageId: params.image_id)
                    redirect(controller: 'parcours', action: 'edit', params: [p_id: parcours.id])
                } else {
                    flash.errors = [title: true];
                    flash.fields = [
                            description: params.description
                    ]
                    redirect(controller: 'parcours', action: 'edit', params: [p_id: parcours.id])
                }
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : On ne peut supprimer un parcours que si on est ROLE_ADMIN ou ROLE_OP
    def delete(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        User me = springSecurityService.getCurrentUser()

        if (parcours) {
            if (parcoursService.deleteParcours(parcours, me)) {
                redirect(controller: "parcours", action: "list")
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : On ne peut activer/desactiver un parcours que si on est ROLE_ADMIN ou ROLE_OP
    def enable(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        User me = springSecurityService.getCurrentUser()

        if (parcours) {
            if (rightsService.simpleRoleCheck(me, 'ROLE_ADMIN')) {
                parcoursService.toogleParcoursStatus(parcours, true)
                redirect(controller: 'parcours', action: 'list')
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    // Vérification nécessaire : On ne peut activer/desactiver un parcours que si on est ROLE_ADMIN ou ROLE_OP
    def disable(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        User me = springSecurityService.getCurrentUser()

        if (parcours) {
            if (rightsService.simpleRoleCheck(me, 'ROLE_ADMIN')) {
                parcoursService.toogleParcoursStatus(parcours, false)
                redirect(controller: 'parcours', action: 'list')
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }

    // API

    // Ouvert a tous
    def api_get(Long p_id) {
        Parcours parcours = Parcours.get(p_id)

        if (parcours) {
            if (parcours.isValidated) {
                withFormat {
                    xml {
                        XML.use('api_basic') {
                            render parcours as XML
                        }
                    }
                }
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }
    // Ouvert a tous
    def api_get_full(Long p_id) {
        Parcours parcours = Parcours.get(p_id)
        def ret = [:]
        ret.id = parcours.id
        ret.title = parcours.title
        ret.pois = []
        ret.sections = []
        parcours.components.each {
            ContentComponent content ->
                def retComponent = [:]
                retComponent.backgroundPic = grailsApplication.config.grails.assetspath.url + content.backgroundPic.filename
                retComponent.title = content.title
                retComponent.shortDesc = content.shortDesc
                retComponent.content = content.content
                ret.sections.add(retComponent)
        }
        parcours.pois.each {
            POI poi ->
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
                ret.pois.add(poiRet)
        }
        render ret as JSON
    }

    // Ouvert a tous
    def api_get_all() {
        def parcoursList = Parcours.list()

        def retParcours = []

        parcoursList.each {
            def ret = [:]
            ret.id = it.id
            ret.title = it.title
            ret.description = it.description
            //ret.backgroundPic = grailsApplication.config.grails.serverURL + grailsApplication.config.grails.assetspath.url + it.backgroundPic.filename
            //LocalHost du mobile
//            ret.backgroundPic = grailsApplication.config.grails.assetspath.url + it.backgroundPic.filename
            ret.backgroundPic = grailsApplication.config.grails.assetspath.url + it.backgroundPic.filename

            retParcours.add(ret)
        }

        render retParcours as JSON
        /*withFormat {
            xml {
                XML.use('api_basic') {
                    render parcoursList as XML
                }
            }
        }*/
    }

    // Ouvert a tous
    def api_get_from_title(Long p_id) {
        Parcours parcours = Parcours.get(p_id)

        if (parcours) {
            if (parcours.isValidated) {
                withFormat {
                    xml {
                        XML.use('api_basic') {
                            render parcours as XML
                        }
                    }
                }
            } else {
                response.sendError(403)
            }
        } else {
            response.sendError(404)
        }
    }
}
