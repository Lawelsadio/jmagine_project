<%--
  Created by IntelliJ IDEA.
  User: Lionel
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

    %{--<div class="modal fade qrcode-modal" role="dialog" id="qrcode_modal" aria-labelledby="gridSystemModalLabel" aria-hidden="true">--}%
        %{--<div class="modal-dialog">--}%
            %{--<div class="modal-content">--}%
                %{--<div class="modal-body">--}%
                    %{--<img src=""/>--}%
                %{--</div>--}%
            %{--</div>--}%
        %{--</div>--}%
    %{--</div>--}%

    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main poi_list">
        <h1 class="page-header"><g:message code="jmagine.parcours.edit"/></h1>

        <div class="row">
            <g:render template="/parcours_menu"/>
            <div class="content">
                <div class="top_actions">
                    <g:link class="btn btn-primary" controller="pois"
                            action="add" params="${[p_id:parcours.id]}"><g:message code="jmagine.pois.button.new"/></g:link>
                </div>

                <div id="map-canvas"></div>
                <ul id="sortable" class="poi_list">
                <g:each in="${pois}" var="poi" status="poi_index">
                    <g:if test="${poi}">
                    <li class="poi ui-state-default" data-poi-id="${poi.id}" data-poi-number="${(poi_index+1)}" data-poi-lat="${poi.lat}" data-poi-lng="${poi.lng}">
                        <div class="background_image">
                            <img src="${ grailsApplication.config.grails.assetspath.url + poi.backgroundPic?.filename}" />
                        </div>
                        <div class="marker">
                            <div class="image"><asset:image src="reserve/map_marker_sh.png"/></div>
                            <div class="number">${(poi_index+1)}</div>
                        </div>
                        <div class="handle">
                            <div class="inner">
                                <div class="image"><asset:image src="reserve/poi_handle.png"/></div>
                            </div>
                        </div>
                        <div class="qrcode_holder">
                            <g:link controller="qrcode" action="text" params="${[text:'/parcours/'+parcours.id+'/pois/'+poi.id]}" class="qrcode_modal_link" target="_blank">
                                <qrcode:image height="100" width="100" text="${poi.id}-jmagine-poi-${poi.id}"/>
                            </g:link>
                        </div>
                        <div class="body">
                            <h3>${ poi.title }</h3>
                            <span class="tag_name">jmagine-poi-${poi.id}</span>
                            <div>${ poi.address }</div>
                            <div class="actions">
                                <g:link controller="pois" action="edit" params="${[p_id:parcours.id,poi_id:poi.id]}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <g:message code="jmagine.pois.button.edit"/></g:link>
                                <button type="button" class="btn btn-default btn-xs focus-button" data-poi-id="${poi.id}"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> <g:message code="jmagine.pois.button.focus"/></button>
                                <g:link controller="pois" onclick="return confirm('Êtes vous sûr ?')" action="delete" params="${[p_id:parcours.id,poi_id:poi.id]}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <g:message code="jmagine.pois.button.delete"/></g:link>
                            </div>
                        </div>
                    </li>
                    </g:if>
                </g:each>
                </ul>
            </div>
        </div>
    </div>
    <script>
        var map_instance;
        var parcoursID = ${parcours.id}
        var jmagineMap = {
            bounds:null,
            pois:[],
            min_lat:null,
            min_lng:null,
            max_lat:null,
            max_lng:null,

            init:function(){
                var pois = $('#sortable li');
                for( var i = 0; i < pois.length; i++ ){
                    var poi = $(pois[i]);
                    var coords = new google.maps.LatLng( poi.data('poi-lat' ), poi.data('poi-lng'));

                    jmagineMap.pois.push({
                        marker:null,
                        coords:coords,
                        id:poi.data('poi-id' ),
                        element:pois[i]
                    })
                }
                for( var  i = 0; i < jmagineMap.pois.length; i++ ) {
                    if( ( jmagineMap.max_lat == null ) || ( jmagineMap.pois[i].coords.lat() > jmagineMap.max_lat ) ) {
                        jmagineMap.max_lat = jmagineMap.pois[i].coords.lat();
                    }
                    if( ( jmagineMap.min_lat == null ) || ( jmagineMap.pois[i].coords.lat() < jmagineMap.min_lat ) ) {
                        jmagineMap.min_lat = jmagineMap.pois[i].coords.lat();
                    }
                    if( ( jmagineMap.max_lng == null ) || ( jmagineMap.pois[i].coords.lng() > jmagineMap.max_lng ) ) {
                        jmagineMap.max_lng = jmagineMap.pois[i].coords.lng();
                    }
                    if( ( jmagineMap.min_lng == null ) || ( jmagineMap.pois[i].coords.lng() < jmagineMap.min_lng ) ) {
                        jmagineMap.min_lng = jmagineMap.pois[i].coords.lng();
                    }
                }
                jmagineMap.bounds = new google.maps.LatLngBounds( new google.maps.LatLng( jmagineMap.min_lat, jmagineMap.min_lng ), new google.maps.LatLng( jmagineMap.max_lat, jmagineMap.max_lng ) );
            }
        };

        function CustomLabel(opt_options) {
            this.setValues(opt_options);
            this.marker = $( '<div class="custom-marker"><div class="holder"><div class="image"><asset:image src="reserve/map_marker.png"/></div><div class="number">'+this.number+'</div></div></div>' ).get(0);
            $(this.marker).css( 'zIndex', 100+this.zIndex );
        }
        try {
            CustomLabel.prototype = new google.maps.OverlayView;
            CustomLabel.prototype.onAdd = function() {
                var pane = this.getPanes().overlayLayer;
                pane.appendChild( this.marker );
            };
            CustomLabel.prototype.onRemove = function() {
                $( this.marker ).remove();
            };
            CustomLabel.prototype.draw = function() {
                var projection = this.getProjection();
                var position = projection.fromLatLngToDivPixel( this.position );
                var div = this.div_;
                position.y -= 32;
                position.x -= 10;
                this.marker.style.left = position.x + 'px';
                this.marker.style.top = position.y + 'px';
                this.marker.style.display = 'block';
            };
        }
        catch(e) {}

        function getBoundsZoomLevel(bounds, mapDim) {
            if( !bounds ) return map_instance.getZoom();

            var WORLD_DIM = { height: 256, width: 256 };
            var ZOOM_MAX = 21;

            function latRad(lat) {
                var sin = Math.sin(lat * Math.PI / 180);
                var radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
                return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
            }

            function zoom(mapPx, worldPx, fraction) {
                return Math.floor(Math.log(mapPx / worldPx / fraction) / Math.LN2);
            }

            var ne = bounds.getNorthEast();
            var sw = bounds.getSouthWest();

            var latFraction = (latRad(ne.lat()) - latRad(sw.lat())) / Math.PI;

            var lngDiff = ne.lng() - sw.lng();
            var lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;

            var latZoom = zoom(mapDim.height, WORLD_DIM.height, latFraction);
            var lngZoom = zoom(mapDim.width, WORLD_DIM.width, lngFraction);

            return Math.min(latZoom, lngZoom, ZOOM_MAX);
        }

        $(function() {

            //handle:'.class' pour limiter le drag a un sous element

            $( "#sortable" ).sortable({
                placeholder: "ui-state-highlight",
//                axis:"y",
                handle:'.handle',

                update: function( event, ui ) {
                    $("#sortable li").each( function(i,e){
                        $(e).find('.number').html(i+1);
                        if( ui.item[0] == e ) {
                            // Do ajax call!
//                            console.log( 'Changed '+ui.item.data('poi-id') +' to index '+i )
                            $.ajax( '/parcours/'+parcoursID+'/pois/'+ui.item.data('poi-id')+'/move_to/'+i).done(function(data){

                            });
                        }
                        $( $(e).data('marker').marker ).find('.number').html(i+1);
                    });
                }
            });
//            $( "#sortable" ).disableSelection();

            function initialize() {
                jmagineMap.init();
                var needed_zoom = getBoundsZoomLevel( jmagineMap.bounds, {width:320, height:320} );
                needed_zoom = Math.min( 18, needed_zoom )

                var mapOptions = {
                    center: jmagineMap.bounds.getCenter(),
                    zoom: needed_zoom
                };
                map_instance = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

                for( var  i = 0; i < jmagineMap.pois.length; i++ ) {
                    var marker = new CustomLabel({
                        position: jmagineMap.pois[i].coords,
                        number:(i+1),
                        map: map_instance
                    });
                    jmagineMap.pois[i].marker = marker;
                    $(jmagineMap.pois[i].element).data('marker', marker );
                }

                $('.focus-button').on('click', function(e){
                    for( var i = 0; i < jmagineMap.pois.length; i++ ) {
                        if( jmagineMap.pois[i].id == $(e.target).data('poi-id') ) {
                            map_instance.panTo( jmagineMap.pois[i].coords )
                            map_instance.setZoom( 16 )
                        }
                    }
                });

                $(".qrcode_modal_link").on('click', function(e){
                    $('#modal').addClass('qrcode-modal');
                    $("#modal .modal-body").empty();
                    $("#modal .modal-body").append($('<img />'));
                    $('#modal').modal({});
                    $('#modal img')[0].src = e.currentTarget.href;
                    return false;
                });
           }

            google.maps.event.addDomListener(window, 'load', initialize);
        });

    </script>

</body>
</html>