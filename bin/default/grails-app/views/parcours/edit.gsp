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
</head>
<body>

    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header"><g:message code="jmagine.parcours.edit"/></h1>

        <div class="row">
            <g:render template="/parcours_menu"/>
            <div class="content">
                <g:form controller="parcours" name="parcours_edit_submit" action="do_info_edit" params="${[p_id:parcours.id]}" enctype="multipart/form-data">
                    <div class="col-xs-6 col-sm-6">
                        <g:if test="${!flash.errors?.title}">
                            <div class="form-group">
                                <label for="title"><g:message code="jmagine.parcours.parcours_title"/></label>
                                <input class="form-control" name="title" value="${flash.fields?.title?flash.fields.title:parcours.title}" id="title" placeholder="<g:message code="jmagine.parcours.parcours_title"/>">
                            </div>
                        </g:if>
                        <g:else>
                            <div class="form-group has-error has-feedback">
                                <label for="title"><g:message code="jmagine.parcours.parcours_title"/></label>
                            <input aria-describedby="title_error" class="form-control" name="title" id="title" placeholder="<g:message code="jmagine.parcours.parcours_title"/>">
                            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                            <span id="title_error" class="sr-only">(error)</span>
                            <p class="help-block">
                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <g:message code="jmagine.parcours.form.errors.title"/>
                            </div>
                            </p>
                        </div>
                        </g:else>
                        <div class="form-group">
                            <label for="description"><g:message code="jmagine.parcours.parcours_description"/></label>
                            <textarea class="form-control" name="description"  id="description" placeholder="<g:message code="jmagine.parcours.parcours_description"/>">${flash.fields?.description?flash.fields.description:parcours.description}</textarea>
                        </div>
                        <div class="poi_image_uploader"></div>
                        <input type="hidden" name="image_type" id="image_type" value="default"/>
                        <input type="hidden" name="image_id" id="image_id" value=""/>
                        <button type="submit" class="btn btn-default"><g:message code="jmagine.form.submit"/></button>
                    </div>
                </g:form>
                <div class="col-xs-4 col-sm-4 col-sm-offset-2 preview">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <g:message code="jmagine.parcours.preview.title"/>
                                <div class="parcours_state">
                                    <g:message code="jmagine.parcours.disabled" />
                                </div>
                            </h3>
                        </div>
                        <div class="panel-body">
                            <g:message code="jmagine.parcours.preview.na"/>
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
                allow_delete_image:true,
                allow_upload:true,
                uid:1,
                parcours_id:${parcours.id},
                default_image_src:${ parcours.backgroundPic?.filename?raw('"'+g.jmagineImage( src:parcours.backgroundPic?.filename, makelink:true, absolute:true )+'"'):'null'},
                onChange:updateInputs
            });
        });
    </script>
</body>
</html>