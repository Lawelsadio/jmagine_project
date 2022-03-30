<script id="jmagine-image-upload-plugin-template" type="x-tmpl-mustache">
    <div class="image_uploader">
        <label><g:message code="jmagine.pois.background_picture"/></label>
        <div class="background_preview" id="background_preview_{{uid}}">
            <div class="position_fix">
                {{#default_image_src}}
                <img src="{{ default_image_src }}"/>
                {{/default_image_src}}
            </div>
        </div>

        <div class="form-group">
            <div class="upload_button_fix btn btn-primary">
                <div class="skin_holder">
                    <i class="glyphicon glyphicon-cloud-upload"></i>
                    <g:message code="jmagine.form.upload_image"/>
                </div>
                <div class="input_holder">
                    <input type="file" name="{{file_upload_name_and_id}}" id="{{file_upload_name_and_id}}" />
                </div>
            </div>
            <button type="button" id="pick_from_media_{{uid}}" class="btn btn-default">
                <i class="glyphicon glyphicon-th"></i>
                <g:message code="jmagine.form.pick_image_from_media"/>
            </button>
            <button type="button" id="delete_background_{{uid}}" class="btn btn-default">
                <i class="glyphicon glyphicon-trash"></i>
                <g:message code="jmagine.form.delete_image"/>
            </button>
            {{#has_error}}
                <p class="help-block">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        {{#has_error_invalid}}
                            <g:message code="jmagine.pois.form.errors.image_invalid"/>
                        {{/has_error_invalid}}
                        {{#has_error_missing}}
                            <g:message code="jmagine.pois.form.errors.image_missing"/>
                        {{/has_error_missing}}
                    </div>
                </p>
            {{/has_error}}
            <p class="help-block"><g:message code="jmagine.parcours.form.helpers.background" /></p>
        </div>
    </div>
</script>