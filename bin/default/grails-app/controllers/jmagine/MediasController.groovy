package jmagine

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.web.multipart.MultipartFile

class MediasController {

    ImageService imageService
    SpringSecurityService springSecurityService

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def list( Long p_id ) {
        Parcours parcours = Parcours.get( p_id );
        User me = springSecurityService.getCurrentUser()

        if( parcours ) {
            if(request.xhr) {
                def fileList = FileContainer.withCriteria {
                    eq( 'parcours', parcours )
                    eq( 'type', FileType.IMG )
                    order('dateCreated', 'asc' )
                }
                render( view:'/medias/list_modal', model:[me:me, parcours:parcours, medias:fileList ])
            }
        }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def upload( Long p_id ) {
        Parcours parcours = Parcours.get( p_id )
        response.setContentType("application/json")
        if( parcours ){
            MultipartFile upload_file = request.getFile("upload")


            if( upload_file && !upload_file.empty ) {
                FileContainer new_fileContainer =
                        imageService.createImage(
                                fileType:FileType.IMG_CKEDITOR,
                                picture:upload_file,
                                ownerType:OwnerType.BACKEND,
                                ownerId:parcours.author.id,
                                sizing_informations: [width:1080, height:1080, resize:true] );
                println("upload_file")
                println(upload_file)
                parcours.addToFileList( new_fileContainer ).save()

                /*
                {
                    success: true, // Must be false if upload fails
                    file: 'https://example.com/myimage.jpg'
                }
                 */

                /*
                    Json to return in success case according Trumbowyg documentation
                 */

                render new TrumbowygResponse(true,
                        grailsApplication.config.grails.assetspath.url + new_fileContainer.filename ) as JSON




            }
            else {
/*
                String error = "File missing"
                render '<html>' +
                        '<body>' +
                        '<script type="text/javascript">window.parent.CKEDITOR.tools.callFunction('+params.CKEditorFuncNum+', null,"'+error+'");' +
                        '</script>' +
                        '</body>' +
                        '</html>';
*/

                render new TrumbowygResponse(false, '') as JSON

            }
       }
    }

    // Protégé par Config.groovy: ROLE_ADMIN/ROLE_OP/ROLE_MOD
    def get_json_img_list( Long p_id ) {
        def parcoursInstance = Parcours.get(p_id)
        def base_dir = grailsApplication.config.grails.assetspath.url
        if (parcoursInstance)
        {
            def file_list = []
            parcoursInstance.fileList.each
            {
                FileContainer fileContainer ->
                if ( fileContainer && fileContainer.type && ( fileContainer.type == FileType.IMG || fileContainer.type == FileType.IMG_CKEDITOR ) )
                {
                    def item = [:]
                    item["image"] = base_dir+fileContainer.filename
                    item["thumb"] = base_dir+fileContainer.filename
                    item["folder"] = "lol"
                    file_list.add(item)
                }
            }
            render file_list as JSON
        }
    }
}
