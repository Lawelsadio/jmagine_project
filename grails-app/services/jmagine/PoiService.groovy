package jmagine

import grails.gorm.transactions.Transactional

@Transactional
class PoiService {
    def springSecurityService
    ImageService imageService
    PoiService poiService

    POI createPOI(params ) {
        POI new_poi = new POI(
                title:params.title,
                lat:Double.parseDouble( params.lat ),
                lng:Double.parseDouble( params.lng ),
                address: params.address,
                parcours:params.parcours,
                content:params.content )

        new_poi.isNFCEnabled  = params.tagNfc ? Boolean.TRUE : Boolean.FALSE
        new_poi.isQREnabled  = params.tagQr ? Boolean.TRUE : Boolean.FALSE
        new_poi.isSNSEnabled  = params.tagSns ? Boolean.TRUE : Boolean.FALSE
        new_poi.isGeolocEnabled  = params.tagGeo ? Boolean.TRUE : Boolean.FALSE
        if( params.imageType == 'empty' ) {

        }
        else if( ( params.imageType == 'upload' ) &&
                        params.background_picture &&
                        !params.background_picture.empty ) {

            FileContainer background = imageService.createImage( save:false,
                    ownerType:OwnerType.BACKEND,
                    ownerId:params.author.id,
                    picture:params.background_picture,
                    sizing_informations: [width:770, height:513] )

            new_poi.parcours.addToFileList( background )
            new_poi.backgroundPic = background
            background.save(flush: true,failOnError: true)
            new_poi.parcours.save()
        }
        else if( params.imageType == 'librairy' && params.imageId ) {
            FileContainer background = FileContainer.get( params.imageId )
            new_poi.backgroundPic = background
        }
        return new_poi.save(failOnError: true)
    }

    POI updatePOI(params ) {
        POI poi = params.poi
        poi.title = params.title;
        poi.content = params.content;
        poi.address = params.address;
        poi.lat = Double.parseDouble( params.lat );
        poi.lng = Double.parseDouble( params.lng );
        poi.isNFCEnabled  = params.tagNfc ? Boolean.TRUE : Boolean.FALSE
        poi.isQREnabled  = params.tagQr ? Boolean.TRUE : Boolean.FALSE
        poi.isSNSEnabled  = params.tagSns ? Boolean.TRUE : Boolean.FALSE
        poi.isGeolocEnabled  = params.tagGeo ? Boolean.TRUE : Boolean.FALSE

        if( ( params.imageType == 'librairy' ) && ( params.imageId != null ) ) {
            FileContainer media = FileContainer.get( params.imageId )
            if( media ) poi.backgroundPic = media
        }
        else if( ( params.imageType == 'upload' ) && params.background_picture && !params.background_picture.empty ) {
            poi.backgroundPic = imageService.createImage( ownerType:OwnerType.BACKEND, ownerId:poi.parcours.author.id, picture:params.background_picture, sizing_informations: [width:770, height:513] )
            poi.parcours.addToFileList( poi.backgroundPic )
            poi.parcours.save()
        }
        return poi.save()
    }

    void movePOI( params ) {

    }
}
