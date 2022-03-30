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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Trumbowyg/2.23.0/trumbowyg.min.js" integrity="sha512-sffB9/tXFFTwradcJHhojkhmrCj0hWeaz8M05Aaap5/vlYBfLx5Y7woKi6y0NrqVNgben6OIANTGGlojPTQGEw==" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Trumbowyg/2.23.0/ui/trumbowyg.min.css" integrity="sha512-iw/TO6rC/bRmSOiXlanoUCVdNrnJBCOufp2s3vhTPyP1Z0CtTSBNbEd5wIo8VJanpONGJSyPOZ5ZRjZ/ojmc7g==" crossorigin="anonymous" />
    <title></title>
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main new_poi">
    <h1 class="page-header"><g:message code="jmagine.pois.new"/></h1>
    <div class="row">
        <g:render template="/parcours_menu"/>
        <div class="content">
            <g:form controller="pois" action="do_add" name="poi_add_submit" params="${[p_id:parcours.id]}" enctype="multipart/form-data">
                <div class="col-xs-6 col-sm-6">
                    <g:if test="${!flash.errors?.title}">
                        <div class="form-group">
                            <label for="title"><g:message code="jmagine.pois.pois_title"/></label>
                            <input class="form-control" value="${flash.fields?.title}" name="title" id="title" placeholder="<g:message code="jmagine.pois.pois_title"/>">
                        </div>
                    </g:if>
                    <g:else>
                        <div class="form-group has-error has-feedback">
                            <label for="title"><g:message code="jmagine.pois.pois_title"/></label>
                        <input aria-describedby="title_error" class="form-control" name="title" id="title" placeholder="<g:message code="jmagine.pois.pois_title"/>">
                        <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                        <span id="title_error" class="sr-only">(error)</span>
                        <p class="help-block">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <g:message code="jmagine.pois.form.errors.title"/>
                        </div>
                        </p>
                    </div>
                    </g:else>

                    <g:if test="${!flash.errors?.content}">
                        <div class="form-group">
                            %{--<label for="content"><g:message code="jmagine.pois.poi_content"/></label>
                            <ckeditor:config filebrowserImageUploadUrl =
                                             "${ g.createLink( absolute: true,
                                                     controller: "medias",
                                                     action:"upload",
                                                     params:[p_id:parcours.id]) }"
                                             filebrowserUploadUrl = "${ g.createLink( absolute: true,
                                                     controller: "medias",
                                                     action:"upload", params:[p_id:parcours.id]) }"/>--}%

                            <textarea name="content" id="editor" >
                                ${flash.fields?.content}
                            </textarea>
                            %{--<ckeditor:editor
                                    name="content"
                                    height="400px"
                                    width="100%"
                                    toolbar="custom">${flash.fields?.content}</ckeditor:editor>--}%

                        </div>
                    </g:if>
                    <g:else>
                        <div class="form-group has-error">
                            <label for="content"><g:message code="jmagine.pois.poi_content"/></label>
                    %{--
                                                    <ckeditor:config filebrowserImageUploadUrl = "${ g.createLink( absolute: true, controller: "medias", action:"upload", params:[p_id:parcours.id]) }" filebrowserUploadUrl = "${ g.createLink( absolute: true, controller: "medias", action:"upload", params:[p_id:parcours.id]) }"/>
                                                    <ckeditor:editor name="content" height="400px" width="100%" toolbar="custom"></ckeditor:editor>
                    --}%
                        <textarea name="content" id="editor" ></textarea>
                        <p class="help-block">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <g:message code="jmagine.pois.form.errors.description"/>
                        </div>
                        </p>
                    </div>
                    </g:else>

                    <g:if test="${!flash.errors?.poi_tags}">
                        <div class="form-group checkboxes">
                            <label><g:message code="jmagine.pois.poi_tags"/></label><br/>
                            <table>
                                <tr><td><label class="min" for="tag-nfc"><g:message code="jmagine.pois.poi_tags.nfc"/></label></td>
                                    <td><input <g:if test="${flash.fields?.tagNfc}">checked</g:if> type="checkbox" name="tagNfc" id="tag-nfc"></td><td></td></tr>
                                <tr><td><label class="min" for="tag-qr"><g:message code="jmagine.pois.poi_tags.qr"/></label></td>
                                    <td><input <g:if test="${flash.fields?.tagQr}">checked</g:if> type="checkbox" name="tagQr" id="tag-qr"></td><td></td></tr>
                                <tr><td><label class="min" for="tag-geo"><g:message code="jmagine.pois.poi_tags.geo"/></label></td>
                                    <td><input <g:if test="${flash.fields?.tagGeo}">checked</g:if> type="checkbox" name="tagGeo" id="tag-geo"></td><td></td></tr>
                                <tr><td><label class="min" for="tag-sns"><g:message code="jmagine.pois.poi_tags.sns"/></label></td>
                                    <td><input <g:if test="${flash.fields?.tagSns}">checked</g:if> type="checkbox" name="tagSns" id="tag-sns"></td><td class="info">*</td></tr>
                            </table>
                            <div class="info">${message(code: 'jmagine.help.sns')}</div>
                        </div>
                    </g:if>
                    <g:else>
                        <div class="form-group checkboxes has-error">
                            <label><g:message code="jmagine.pois.poi_tags"/></label><br/>
                        <table>
                            <tr><td><label class="min" for="tag-nfc"><g:message code="jmagine.pois.poi_tags.nfc"/></label></td>
                                <td><input type="checkbox" class="form-control" name="tagNfc" id="tag-nfc"></td><td></td></tr>
                            <tr><td><label class="min" for="tag-qr"><g:message code="jmagine.pois.poi_tags.qr"/></label></td>
                                <td><input type="checkbox" name="tagQr" id="tag-qr"></td><td></td></tr>
                            <tr><td><label class="min" for="tag-geo"><g:message code="jmagine.pois.poi_tags.geo"/></label></td>
                                <td><input type="checkbox" name="tagGeo" id="tag-geo"></td><td></td></tr>
                            <tr><td><label class="min" for="tag-sns"><g:message code="jmagine.pois.poi_tags.sns"/></label></td>
                                <td><input type="checkbox" name="tagSns" id="tag-sns"></td><td class="info">*</td></tr>
                        </table>
                        <p class="help-block">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <g:message code="jmagine.pois.form.errors.poi_tags"/>
                        </div>
                        </p>
                        <div class="info">${message(code: 'jmagine.help.sns')}</div>
                        </div>
                    </g:else>
                    <div class="poi_image_uploader <g:if test="${flash.errors?.image}">error</g:if>"></div>
                    <div class="poi_map_holder <g:if test="${flash.errors?.map}">error</g:if>"></div>

                    <div class="clearfix"></div>

                    <input type="hidden" name="image_type" id="image_type" value="empty"/>
                    <input type="hidden" name="image_id" id="image_id" value=""/>

                    <input type="hidden" name="lat" id="lat" value="${ flash.fields?.lat }"/>
                    <input type="hidden" name="lng" id="lng" value="${ flash.fields?.lng }"/>
                    <input type="hidden" name="address" id="address" value="${ flash.fields?.address }"/>

                    <button type="submit" class="btn btn-default"><g:message code="jmagine.form.submit"/></button>
                </div>
            </g:form>
        </div>
    </div>
</div>

<g:render template="/mustache_templates/map_picker"/>
<g:render template="/mustache_templates/image_uploader"/>
<script>
    var parcours_id = ${parcours.id};
    var base_url = $('head base').attr('href');

    $( function(){

        function updateInputs( data ) {
            $('#image_type').val( data.type );
            $('#image_id').val( data.imageID );
        };

        function updateMapInputs( data ) {
            $('#lat').val( data.latLng.lat() );
            $('#lng').val( data.latLng.lng() );
            $('#address').val( data.address );
        };

        $('.poi_image_uploader').imageUploader({
            has_error:${flash.errors?.image?raw('"'+flash.errors.image+'"'):'null'},
            allow_browse_gallery:true,
            allow_delete_image:false,
            allow_upload:true,
            uid:1,
            parcours_id:${parcours.id},
            onChange:updateInputs
        });

        $('.poi_map_holder').mapPicker({
            has_error:${flash.errors?.map?raw('"'+flash.errors.map+'"'):'null'},
            position:{ lat:${ flash.fields?.lat!=null?flash.fields?.lat:'null' }, lng:${ flash.fields?.lng!=null?flash.fields.lng:'null' } },
            center:{ lat: ${previous_poi?.lat!=null?previous_poi?.lat:'null'}, lng: ${previous_poi?.lng!=null?previous_poi?.lng:'null'} },
            uid:1,
            address:"${ flash.fields?.address }",
            onChange:updateMapInputs
        });

        //Initialisation du contenu ckeditor>
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



        $.fn.bootstrapSwitch.defaults.onText = "${g.message(code: 'jmagine.checkbox.on')}";
        $.fn.bootstrapSwitch.defaults.offText = "${g.message(code: 'jmagine.checkbox.off')}";
        $.fn.bootstrapSwitch.defaults.state = false;
        $("[name='tagNfc']").bootstrapSwitch();
        $("[name='tagQr']").bootstrapSwitch();
        $("[name='tagGeo']").bootstrapSwitch();
        $("[name='tagSns']").bootstrapSwitch();
    });


</script>
<asset:javascript src="trumbowyg.upload.js"/>
<asset:javascript src="trumbowyg_pasteeembed.js"/>

</body>
</html>