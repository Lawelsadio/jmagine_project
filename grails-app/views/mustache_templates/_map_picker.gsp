<script id="jmagine-map-plugin-template" type="x-tmpl-mustache">
    <div class="map_picker">
        <label><g:message code="jmagine.pois.position"/></label>

        <div class="google_map_canvas"></div>
        <div class="coords_holder">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="lat_{{uid}}"><g:message code="jmagine.generic.latitude"/></label>
                        <input type="text" class="form-control" id="lat_{{uid}}" placeholder="<g:message code="jmagine.generic.latitude"/>">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="lng_{{uid}}"><g:message code="jmagine.generic.longitude"/></label>
                        <input type="text" class="form-control" id="lng_{{uid}}" placeholder="<g:message code="jmagine.generic.longitude"/>">
                    </div>
                </div>
            </div>
        </div>
        <div class="autocomplete_holder">
            <div class="form-group">
                <label for="address_{{uid}}"><g:message code="jmagine.generic.address"/></label>
                <div class="input-group">
                    <input type="text" id="address_{{uid}}" class="form-control address"/>
                    <span class="input-group-addon geocoding-info">
                        <i class="glyphicon glyphicon-ok done"/>
                        <i class="glyphicon glyphicon-hourglass working"/>
                    </span>
                </div>
            </div>
        </div>
        {{#has_error}}
            <p class="help-block">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <g:message code="jmagine.pois.form.errors.map"/>
                </div>
            </p>
        {{/has_error}}
    </div>
</script>