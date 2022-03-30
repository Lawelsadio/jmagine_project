<script id="jmagine-image-upload-plugin-template" type="x-tmpl-mustache">
    <label><g:message code="jmagine.users.profile_picture"/></label>
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
        <p class="help-block"><g:message code="jmagine.users.form.helpers.avatar" /></p>
    </div>
</script>