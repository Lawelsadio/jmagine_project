package jmagine

import grails.gorm.transactions.Transactional

@Transactional
class ParcoursService {
    def springSecurityService
    ImageService imageService
    def rightsService
    def grailsApplication



    Parcours createParcours(params) {
        FileContainer background
        Parcours new_parcours = new Parcours(
                title: params.title,
                description: params.description,
                author: params.author,
                isValidated: true
        ).addToModerators(params.author).save(failOnError: true)

        if (params.imageType == 'upload' && params.background_picture && !params.background_picture.empty) {

            background = imageService.createImage(ownerType: OwnerType.BACKEND, ownerId: params.author.id, picture: params.background_picture, sizing_informations: [width: 128, height: 128])
println("params.background_picture")
            println(params.background_picture)


            new_parcours.addToFileList(background)
            new_parcours.backgroundPic = background

        }

        return new_parcours.save(flush: true)
    }

    Parcours updateParcours(params) {
        Parcours parcours = params.parcours
        parcours.title = params.title;
        parcours.description = params.description;
        if (params.imageType == 'empty') parcours.backgroundPic = null;
        else if ((params.imageType == 'librairy') && (params.imageId != null)) {
            FileContainer media = FileContainer.get(params.imageId)
            if (media) parcours.backgroundPic = media
        } else if ((params.imageType == 'upload') && params.background_picture && !params.background_picture.empty) {
            parcours.backgroundPic = imageService.createImage(ownerType: OwnerType.BACKEND, ownerId: parcours.author.id, picture: params.background_picture, sizing_informations: [width: 130, height: 130])
            parcours.addToFileList(parcours.backgroundPic)
        }
        if (!parcours.backgroundPic || !parcours.pois.size()) parcours.isValidated = false;
        return parcours.save(flush: true)
    }

    boolean toogleParcoursStatus(Parcours parcours, boolean state) {
        if (state && parcours.backgroundPic && parcours.pois.size()) parcours.isValidated = true;
        else parcours.isValidated = false;
        parcours.save()
        return parcours.isValidated
    }

    ContentComponent addComponent(params) {
        Parcours parcours = params.parcours;
        FileContainer background

        if ((params.imageType == 'librairy') && (params.imageId != null)) {
            background = FileContainer.get(params.imageId)
        } else if ((params.imageType == 'upload') && params.background_picture && !params.background_picture.empty) {
            background = imageService.createImage(ownerType: OwnerType.BACKEND, ownerId: parcours.author.id, picture: params.background_picture, sizing_informations: [width: 182, height: 182])
            parcours.addToFileList(background)
            parcours.save(flush: true)
        }

        ContentComponent component = new ContentComponent(title: params.title,
                content: params.content, backgroundPic: background, shortDesc: params.shortDesc)
        parcours.addToComponents(component)
        component.save()
        parcours.save()
        return component
    }

    Boolean editComponent(params) {
        def sectionInstance = params.section
        sectionInstance.title = params.title
        sectionInstance.content = params.content
        sectionInstance.shortDesc = params.shortDesc
        def parcoursInstance = params.parcours
        if ((params.imageType == 'librairy') && (params.imageId != null)) {
            FileContainer media = FileContainer.get(params.imageId)
            if (media) sectionInstance.backgroundPic = media
        } else if ((params.imageType == 'upload') && params.background_picture && !params.background_picture.empty) {
            sectionInstance.backgroundPic = imageService.createImage(ownerType: OwnerType.BACKEND, ownerId: parcoursInstance.author.id, picture: params.background_picture, sizing_informations: [width: 185, height: 185])
            parcoursInstance.addToFileList(sectionInstance.backgroundPic)
        }
        parcoursInstance.save()
        sectionInstance.save()
        return true
    }

    Boolean deleteParcours(Parcours parcours, User me) {
        if (rightsService.simpleRoleCheck(me, 'ROLE_ADMIN')) {
            parcours.pois.collect().each { POI poi ->
                parcours.removeFromPois(poi)
                parcours.save()
            }
            parcours.components.collect().each { ContentComponent component ->
                parcours.removeFromComponents(component)
                parcours.save()
            }
            parcours.backgroundPic = null;
            parcours.save()

            parcours.fileList.collect().each { FileContainer fileContainer ->
                def file = new File(grailsApplication.config.grails.assetspath.path + fileContainer.filename )
                println(file)
                if( file && fileContainer.filename ) {
                    println("le fichier existe")
                    file.delete()
                    println("le fichier doit avoir ete effac??")
                }else {
                    println("le fichier n existe pas")
                }
              //  imageService.deleteImage(fileContainer)
                parcours.removeFromFileList(fileContainer)
            }

            parcours.moderators.collect().each { User user ->
                user.removeFromModeratedParcours(parcours)
            }

            parcours.save()
            parcours.delete(flush: true)
            return Boolean.TRUE
        }
        return Boolean.FALSE
    }
}
