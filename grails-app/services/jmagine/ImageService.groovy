package jmagine

import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D;

import org.imgscalr.Scalr;

import java.io.File;
import java.io.IOException;

@Transactional
class ImageService {
    def grailsApplication
    int h = 514;
    int w = 770;

    FileContainer createImage(params  ) {

        // getWidthAndHeight(params.picture)




        def dimension = getImageDimension(params.picture)
        if( dimension ) {

            println("params.picture")
            println(params.picture)
            InputStream inputStream = params.picture.getInputStream();
            println("inputStream")
            println(inputStream)
            BufferedImage imagetest = ImageIO.read(inputStream);
            println("imagetest")
            println(imagetest)
            BufferedImage imgg = new BufferedImage(w, h, imagetest.getType());

            Graphics2D g = imgg.createGraphics();
            g.drawImage(imagetest, 0, 0, w, h, null);
            g.dispose();
            /*

            BufferedImage image = Scalr.resize(
                    imagetest,
                    Scalr.Method.BALANCED,
                    Scalr.Mode.FIT_TO_WIDTH,
                    maxWidth,
                    maxHeight,
                    Scalr.OP_ANTIALIAS);

             */

            // File resizedFile = new File("/Applications/MAMP/htdocs/jmagine-backend-mamac-no-s3/grails-app/assets/images/logo-60.jpg");


            //def dim = getImageDimension(imgg)
            // println("dim")
            //println(dim)

            //String ext = FilenameUtils.getExtension( params.picture.getOriginalFilename() );
            String ext = 'jpg'
            def tmpFile = File.createTempFile( 'img', '.'+ext, new File(grailsApplication.config.grails.assetspath.path) )

            println("tmpFile avant transfert to")
            println(tmpFile)

            ImageIO.write(imgg, "jpg", tmpFile);

            //originalImage.flush();
            // imgg.flush();
            println("imgg")
            println(imgg)
            //params.picture.transferTo( tmpFile )
            println("tmpFile apres")
            println(tmpFile)
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

    def getWidthAndHeight(MultipartFile file){
        String ext = 'jpg'
        def tmpFile = File.createTempFile( 'img', '.'+ext, new File(grailsApplication.config.grails.assetspath.path) )
        //println("params.picture")
        // println(params.picture)

        file.transferTo( tmpFile )
        println("tmpFile")
        println(tmpFile)
        try {
            InputStream inputStream = file.getInputStream();
            println("inputStream")
            println(inputStream)
            BufferedImage imagetest = ImageIO.read(inputStream);
            println("imagetest")
            println(imagetest)
            BufferedImage image = Scalr.resize(imagetest, targetSize);
            println("image")
            println(image)
            // File resizedFile = new File(tmpFile);
            // ImageIO.write(image, "jpg", resizedFile);
            println("image2")
            println(image)
            //println("resizedFile")
            //println(resizedFile)

            if (null == image){
                return "ERROR";
            }
            int width = image.getWidth();
            int height = image.getHeight();
            println("width")
            println(width)
            println("height")
            println(height)
            return "?width="+width+"&height="+height;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    def deleteImage(Thumbnail image ) {
        def file = new File(grailsApplication.config.grails.assetspath.path + image.filename )
        if( file && image.filename ) {
            file.delete()
        }
    }

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
