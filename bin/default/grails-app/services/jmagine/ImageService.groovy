package jmagine

import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class ImageService {
    def grailsApplication

    FileContainer createImage(params  ) {
        def dimension = getImageDimension(params.picture)
        if( dimension ) {

            //String ext = FilenameUtils.getExtension( params.picture.getOriginalFilename() );
            String ext = 'jpg'
            def tmpFile = File.createTempFile( 'img', '.'+ext, new File(grailsApplication.config.grails.assetspath.path) )
            //println("params.picture")
           // println(params.picture)

            params.picture.transferTo( tmpFile )
            def img = new FileContainer( filename:tmpFile.getName(), ownerId:params.ownerId, ownerType:params.ownerType, type:params.fileType?params.fileType:FileType.IMG )
           // BufferedImage originalImage = ImageIO.read(params.picture);
           // BufferedImage resizedImage = Scalr.resize(originalImage, 200);
           // File resizedFile = new File(tmpFile);
           // ImageIO.write(resizedImage, "jpg", resizedFile);
            //resizeImages(params.picture,tmpFile,200)

            resizeImage(img,
                    dimension,
                    params.sizing_informations?.width?params.sizing_informations.width:100,
                    params.sizing_informations?.height?params.sizing_informations.height:100,
                    params.sizing_informations?.resize )
            if( params.save ) img.save()
            return img
        }
        else {
            return null
        }
    }

   def resizeImages( File file, String targetFilePath, int targetSize) {
        try {
            //File sourceFile = new File(originalFilePath);
            BufferedImage originalImage = ImageIO.read(file);

            BufferedImage resizedImage = Scalr.resize(originalImage, targetSize);

            File resizedFile = new File(targetFilePath);
            ImageIO.write(resizedImage, "jpg", resizedFile);

            originalImage.flush();
            resizedImage.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thumbnail createThumbnail(params ) {
        def dimension = getImageDimension( params.picture )
        if( dimension ) {
            //String ext = FilenameUtils.getExtension(params.picture.getOriginalFilename());
            String ext = 'jpg'
            println("apres l extension" + grailsApplication.config.grails.assetspath.path)
            def tmpFile = File.createTempFile('img', '.' + ext, new File(grailsApplication.config.grails.assetspath.path))
            params.picture.transferTo(tmpFile)
            def img = new Thumbnail(filename: tmpFile.getName(), user: params.user)
            resizeImage(img, dimension, params.sizing_informations?.width ? params.sizing_informations.width : 200, params.sizing_informations?.height ? params.sizing_informations.height : 200, params.sizing_informations?.resize)
            if (params.save) img.save()
            return img
        }
        else {
            return null
        }
    }

    def getImageDimension(MultipartFile picture) {
        if( isValidMimeType( picture ) ) {
            try {
                BufferedImage bimg = ImageIO.read( picture.getInputStream() )
                def dimension = [ width:bimg.getWidth(), height:bimg.getHeight()]
                return dimension
            }
            catch(e) {

            }
        }
        else return null;
    }

    boolean isValidMimeType( MultipartFile picture ) {
        String mime = picture.getContentType()
        if(( mime == 'image/png' )||( mime == 'image/jpeg' )||( mime == 'image/gif' )||( mime == 'image/pjpeg' )||( mime == 'image/x-png' )) return true
        else return false
    }

    def deleteImage(FileContainer image ) {
        def file = new File(grailsApplication.config.grails.assetspath.path + image.filename )
        if( file && image.filename ) {
            file.delete()
        }
    }
/*
    def deleteImage(Thumbnail image ) {
        def file = new File(grailsApplication.config.grails.assetspath.path + image.filename )
        if( file && image.filename ) {
            file.delete()
        }
    }
*/
    def resizeImage( image, image_dimension,
                     int max_width = 182,
                     int max_height = 182,
                     resize = false ) {

        Runtime runtime = Runtime.getRuntime();

        def file = new File( grailsApplication.config.grails.assetspath.path + image.filename )

        if( !image_dimension ) {
            BufferedImage bimg = ImageIO.read( file );
            image_dimension = [ width:bimg.getWidth(), height:bimg.getHeight() ]
        }

        def i_ratio = image_dimension.width / image_dimension.height;
        def c_ratio = max_width / max_height;

        if( resize ) {
            if( i_ratio != c_ratio ) {
                if( i_ratio < c_ratio ) {
                    int offset = Math.abs( Math.floor( ( ( max_width / i_ratio ) - max_height ) / 2 ) );
                    /*def process = runtime.exec( 'magick mogrify -format jpg -quality 75 -resize '+max_width+' -crop '+max_width+'x'+max_height+'+0+'+offset+' '+file.toString());
                    process.waitFor()
                    if( process.exitValue() == 0 ) {

                    }
                    else {
//                    println "ERREUR LORS DU TRAITEMENT DE L'IMAGE"
                    }*/
                }
                else {
                    int offset = Math.abs( Math.floor( ( ( max_height * i_ratio ) - max_width ) / 2 ) );
                    /*def process = runtime.exec( 'magick mogrify -format jpg -quality 75 -resize x'+max_height+' -crop '+max_width+'x'+max_height+'+'+offset+'+0 '+file.toString());
                     process.waitFor()
                    if( process.exitValue() == 0 ) {
                    }
                    else {
//                    println "ERREUR LORS DU TRAITEMENT DE L'IMAGE"
                    }*/
                }
            }
            else {
                /*def process = runtime.exec( 'magick mogrify -format jpg -quality 75 -resize '+max_width+'x'+max_height+' '+file.toString() );
                process.waitFor()
                if( process.exitValue() == 0 ) {

                }
                else {
//                println "ERREUR LORS DU TRAITEMENT DE L'IMAGE"
                }*/
            }
        }
        else {
            if( ( image_dimension.width > 1080 ) || ( image_dimension.height > 1080 ) ) {
                /* def process = runtime.exec( 'magick mogrify -format jpg -quality 75 -resize '+max_width+'x'+max_height+' '+file.toString() );

                 process.waitFor()
                 if( process.exitValue() == 0 ) {

                 }
                 else {
 //                println "ERREUR LORS DU TRAITEMENT DE L'IMAGE"
                 }*/
            }
            else {
                /* def process = runtime.exec( 'magick mogrify -format jpg -quality 75 '+file.toString() );
                 process.waitFor()
                 if( process.exitValue() == 0 ) {

                 }
                 else {
 //                println "ERREUR LORS DU TRAITEMENT DE L'IMAGE"
                 }*/
            }
        }
    }
}
