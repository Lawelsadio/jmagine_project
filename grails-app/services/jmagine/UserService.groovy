package jmagine

import grails.gorm.transactions.Transactional

@Transactional
class UserService {
    def springSecurityService
    ImageService imageService

    User createUser(params ) {
        User new_user = new User( username:params.username, password:params.password, mail:params.mail )
        if( ( params.imageType == 'upload' ) && ( params.thumbnail && !params.thumbnail.empty ) ) {
            Thumbnail thumbnail = imageService.createThumbnail( picture:params.thumbnail )
            new_user.thumbnail = thumbnail
        }
        new_user.save(failOnError: true)
        UserRole.create( new_user, params.role )
        return new_user
    }

    User updateUser(params ) {
        User user = params.user
        user.mail = params.mail
        user.username = params.username
        if( params.password && params.password.size() ) user.password = params.password

        if( ( params.imageType == 'upload' ) && ( params.thumbnail && !params.thumbnail.empty ) ) {
            if( user.thumbnail ) {
                imageService.deleteImage( user.thumbnail )
                Thumbnail reference = user.thumbnail
                user.thumbnail = null
                reference.delete()
            }
            Thumbnail thumbnail = imageService.createThumbnail( picture:params.thumbnail )
            user.thumbnail = thumbnail
        }
        else if( params.imageType == 'empty' ) {
            if( user.thumbnail ) {
                imageService.deleteImage( user.thumbnail )
                Thumbnail reference = user.thumbnail
                user.thumbnail = null
                reference.delete()
            }
        }

        user.save(flush:true,failOnError:true)
        if( user.getAuthorities()[0] != params.role ) {
            UserRole.remove( user, user.getAuthorities()[0] )
            UserRole.create( user, params.role )
        }
        return user
    }

    User getOrCreateMobileUser(params ) {
        MobileUser mobileUser = MobileUser.get( params.id )
        if( !mobileUser ) {
            mobileUser = new MobileUser( mobileId:params.id ).save()
        }
        return mobileUser
    }

}
