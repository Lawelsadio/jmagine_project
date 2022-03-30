<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 01/04/2015
  Time: 17:41
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="backend"/>
    <title></title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Trumbowyg/2.23.0/trumbowyg.min.js" integrity="sha512-sffB9/tXFFTwradcJHhojkhmrCj0hWeaz8M05Aaap5/vlYBfLx5Y7woKi6y0NrqVNgben6OIANTGGlojPTQGEw==" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Trumbowyg/2.23.0/ui/trumbowyg.min.css" integrity="sha512-iw/TO6rC/bRmSOiXlanoUCVdNrnJBCOufp2s3vhTPyP1Z0CtTSBNbEd5wIo8VJanpONGJSyPOZ5ZRjZ/ojmc7g==" crossorigin="anonymous" />
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h1 class="page-header"><g:message code="jmagine.sections.new"/></h1>

    <div class="row">
        <g:render template="/parcours_menu"/>
        <div class="content">
            <g:form controller="sections" action="do_add" params="[p_id:parcours.id]" enctype="multipart/form-data">
                <div class="col-xs-6 col-sm-6">
                    <g:if test="${!flash.errors?.title}">
                        <div class="form-group">
                            <label for="title"><g:message code="jmagine.sections.sections_title"/></label>
                            <input class="form-control" name="title" id="title" placeholder="<g:message code="jmagine.sections.sections_title"/>">
                        </div>
                    </g:if>
                    <g:else>
                        <div class="form-group has-error has-feedback">
                            <label for="title"><g:message code="jmagine.sections.sections_title"/></label>
                        <input aria-describedby="title_error" class="form-control" name="title" id="title" placeholder="<g:message code="jmagine.sections.sections_title"/>">
                        <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                        <span id="title_error" class="sr-only">(error)</span>
                        <p class="help-block">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <g:message code="jmagine.sections.form.errors.title"/>
                        </div>
                        </p>
                    </div>
                    </g:else>
                    <div class="form-group">
                        <label for="shortDesc"><g:message code="jmagine.sections.sections_short_desc"/></label>
                        <textarea class="form-control" name="shortDesc" id="shortDesc" placeholder="<g:message code="jmagine.sections.sections_short_desc"/>"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editor"><g:message code="jmagine.sections.content"/></label>
                        <textarea name="content" id="editor"></textarea>
                    </div>
                    <div class="poi_image_uploader"></div>
                    <input type="hidden" name="image_type" id="image_type" value="empty"/>
                    <input type="hidden" name="image_id" id="image_id" value=""/>
                    <button type="submit" class="btn btn-default"><g:message code="jmagine.form.submit"/></button>
                </div>
            </g:form>
            <div class="col-xs-4 col-sm-4 col-sm-offset-2 preview">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <g:message code="jmagine.sections.preview.title"/>
                        </h3>
                    </div>
                    <div class="panel-body">
                        <g:message code="jmagine.sections.preview.na"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<g:render template="/mustache_templates/image_uploader"/>
<script>
    var parcours_id = ${parcours.id};
    var base_url = $('head base').attr('href');

    $( function(){
        function updateInputs( data ) {
            $('#image_type').val( data.type );
            $('#image_id').val( data.imageID );
        };

        $('.poi_image_uploader').imageUploader({
            allow_browse_gallery:true,
            allow_delete_image:false,
            allow_upload:true,
            uid:1,
            parcours_id:${parcours.id},
            onChange:updateInputs
        });

        $('#editor').trumbowyg({
            imageWidthModalEdit: true,
            btnsDef: {
                // Create a new dropdown
                image: {
                    dropdown: ['insertImage', 'upload'],
                    ico: 'insertImage'
                }
            },
            // Redefine the button pane
            btns: [
                ['viewHTML'],
                ['formatting'],
                ['strong', 'em', 'del'],
                ['superscript', 'subscript'],
                ['link'],
                ['image'], // Our fresh created dropdown
                ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull'],
                ['unorderedList', 'orderedList'],
                ['horizontalRule'],
                ['removeformat'],
                ['fullscreen']
            ],
            plugins: {
                // Add imagur parameters to upload plugin for demo purposes
                upload: {
                    serverPath: '/parcours/'+parcours_id+'/medias/upload',
                    fileFieldName: 'upload',
                    success : function (data, trumbowyg, $modal, values){
                        if(data.file) {

                            /*var $modal = $('#myModal');

                            $('#previewImage').attr("src", data.file)
                            let height = $('#height').val()
                            let width = $('#width').val()

                            console.log(height,  width)


                            $modal.modal('show');
                            $modal.on('click', '#saveModal', function(e) {
                                $modal.modal('hide');
                                    var html = trumbowyg.html();
                                    html += "<img height="+height+" width="+width+" src=\'"+data.file+"\'/>";
                                    trumbowyg.html(html);
                            });*/

                            var html = trumbowyg.html();
                            html += "<img src=\'"+data.file+"\'/>";
                            trumbowyg.html(html);

                            trumbowyg.closeModal();

                        }
                        else {
                            trumbowyg.addErrorOnModalField(
                                $('input[type=file]', $modal),
                                trumbowyg.lang.uploadError || data.message
                            );
                        }
                    }
                }
            }
        });
    });
</script>
<asset:javascript src="trumbowyg.upload.js"/>
<asset:javascript src="trumbowyg_pasteeembed.js"/>
</body>
</html>