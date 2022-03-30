(function ($) {

    var geocoder = null;

    $.widget( 'jmagine.mapPicker', {
        options:{
            uid:0,
            position:{
                lat:null,
                lng:null
            },
            center:{
                lat:null,
                lng:null
            },
            address:null,
            template:'jmagine-map-plugin-template',
            onChange:function(){}
        },
        map_instance:null,
        coords_timeout:null,
        geocoding_timeout:null,
        last_geocoding:null,
        geocoding:false,
        ignore_geocoding_results:false,
        autocomplete_instance:null,
        marker_instance:null,
        map_data: {
            latLng:null,
            address:null
        },
        elements: {
            lat:null,
            lng:null,
            address:null,
            geocoding_info:null
        },

        _create:function() {
            if( !this._check_dependencies() ) {
                this._initialize();
            }
            else {
                console.error('Could not initialize jmagine.mapPicker plugin!');
            }
        },
        _initialize:function() {
            var template = $('#'+this.options.template).html();
            Mustache.parse(template);
            var rendered = Mustache.render(template,this.options);
            this.element.html( rendered );

            // Find elements
            this.elements.lat = this.element.find('#lat_'+ this.options.uid );
            this.elements.lng = this.element.find('#lng_'+ this.options.uid );
            this.elements.address = this.element.find('#address_'+ this.options.uid );
            this.elements.geocoding_info = this.element.find('.geocoding-info' );

            this.elements.lat.val( this.options.position.lat );
            this.elements.lng.val( this.options.position.lng );
            this.elements.address.val( this.options.address );

            var map_center = null;
            var zoom_level = 16;

            if( ( this.options.position.lat != null ) && ( this.options.position.lng != null ) ) {
                map_center = this.map_data.latLng = new google.maps.LatLng( this.options.position.lat, this.options.position.lng );
            }
            else if( ( this.options.center.lat != null ) && ( this.options.center.lng != null ) ) {
                map_center =  new google.maps.LatLng( this.options.center.lat, this.options.center.lng );
            }
            else {
                map_center = new google.maps.LatLng(46.227638,2.213749000000007);
                zoom_level = 5;
            }

            this.elements.lat.on('input', $.proxy( this._handle_coords_change, this ) );
            this.elements.lng.on('input', $.proxy( this._handle_coords_change, this ) );

            var mapOptions = {
                center:map_center,
                zoom: zoom_level,
                disableDefaultUI:true,
                styles:[
                    {
                        "featureType": "poi",
                        "elementType": "labels",
                        "stylers": [
                            { "visibility": "off" }
                        ]
                    }
                ]
            };
            this.map_instance = new google.maps.Map( this.element.find( '.google_map_canvas')[0], mapOptions );
            google.maps.event.addListener( this.map_instance, 'click', $.proxy( this._handle_map_click, this ) );

            this.autocomplete_instance = new google.maps.places.Autocomplete( this.elements.address[0] );
            this.autocomplete_instance.bindTo('bounds', this.map_instance );
            google.maps.event.addListener( this.autocomplete_instance, 'place_changed', $.proxy( this._handle_address_change, this ) );
            this._handle_new_marker_position();
        },

        // Public methods
        getLat:function(){
            return this.map_data.latLng.lat()
        },
        getLng:function(){
            return this.map_data.latLng.lng()
        },
        getAddress:function(){
            return this.map_data.address
        },

        // Private methods
        _check_dependencies:function() {
            var plugins_missing = false;
            try {
                Mustache
            }
            catch(e) {
                plugins_missing = true;
                console.error('Mustache.js missing!');
            }

            try {
                geocoder = new google.maps.Geocoder();
            }
            catch(e) {
                plugins_missing = true;
                console.error('GoogleMaps API missing!');
            }
            return plugins_missing;
        },
        _call_geocoding:function() {
            this.elements.geocoding_info.addClass('working');
            this.ignore_geocoding_results = false;

            var now = new Date();

            if( this.geocoding ) {
                if( this.geocoding_timeout ) {
                    clearTimeout( this.geocoding_timeout );
                    this.geocoding_timeout = null;
                }
                this.geocoding_timeout = setTimeout($.proxy( this._call_geocoding,this), 1100 );
                this.ignore_geocoding_results = true;
            }
            else {
                if( ( !this.last_geocoding ) || ( (now-this.last_geocoding)>1000 ) ) {
                    this.geocoding = true;

                    geocoder.geocode({'latLng': this.map_data.latLng }, $.proxy( function(results, status) {
                        this.elements.geocoding_info.removeClass('working');
                        if (status == google.maps.GeocoderStatus.OK) {
                            if(results[0]&&!this.ignore_geocoding_results) {
                                this.elements.address.val( results[0].formatted_address );
                                this.map_data.address = results[0].formatted_address;
                            } else {
//                            alert('No results found');
                            }
                        }
                        else if( google.maps.GeocoderStatus.ZERO_RESULTS ){
                            this.elements.address.val('');
                            this.map_data.address = '';
                        }
                        else {

                        }
                        this.geocoding = false;
                        this.last_geocoding = new Date()
                        this._dispatchChangeEvent();
                    }, this ));
                }
                else {
                    if( this.geocoding_timeout ) {
                        clearTimeout( this.geocoding_timeout );
                        this.geocoding_timeout = null;
                    }
                    this.geocoding_timeout = setTimeout( $.proxy(this._call_geocoding, this), 1100 - ( now - this.last_geocoding ) );
                }
            }
        },
        _clear_geocoding_timeout:function( ) {
            // do css stuff
            if( this.geocoding_timeout ) {
                clearTimeout( this.geocoding_timeout );
                this.geocoding_timeout = null;
            }
        },
        _handle_coords_change_timeout:function() {
            this._handle_new_marker_position();
            this._center_map();
            this._call_geocoding();
        },
        _handle_coords_change:function(event) {
            if( this.coords_timeout ) {
                clearTimeout( this.coords_timeout );
                this.coords_timeout = null;
            }
            this._clear_geocoding_timeout();
            // test values
            this.map_data.latLng = new google.maps.LatLng( this.elements.lat.val(), this.elements.lng.val() );
            this.coords_timeout = setTimeout( $.proxy( this._handle_coords_change_timeout, this ), 400 );
        },
        _set_latLng:function() {
            this.elements.lat.val( this.map_data.latLng.lat() );
            this.elements.lng.val( this.map_data.latLng.lng() );
        },
        _create_marker_position:function() {
            this.marker_instance = new google.maps.Marker({
                position: this.map_data.latLng,
                map: this.map_instance,
                draggable:true
            });

            google.maps.event.addListener( this.marker_instance, 'dragend', $.proxy( function( d ) {
                this.map_data.latLng = d.latLng;
                this._set_latLng();
                this._call_geocoding();
            }, this ));
        },
        _handle_address_change:function(event) {
            var place = this.autocomplete_instance.getPlace();
            if( place ) {
                if (!place.geometry) return;
                if (place.geometry.viewport) {
                    this.map_instance.fitBounds( place.geometry.viewport );
                } else {
                    this.map_instance.setCenter( place.geometry.location );
                    this.map_instance.setZoom( 17 );
                }
                this.map_data.address = this.elements.address.val();
                this.map_data.latLng = place.geometry.location;
                this._handle_new_marker_position();
                this._set_latLng();
                this._dispatchChangeEvent();
            }
        },
        _handle_new_marker_position:function(event) {
            if(!this.marker_instance) this._create_marker_position();
            this.marker_instance.setPosition( this.map_data.latLng );
        },
        _handle_map_click:function(event) {
            this.map_data.latLng = event.latLng;
            this._handle_new_marker_position();
            this._set_latLng();
            this._call_geocoding();
            this._dispatchChangeEvent();
        },
        _center_map:function(){
            this.map_instance.setCenter( this.map_data.latLng );
        },
        _dispatchChangeEvent:function() {
            if( this.options.onChange ) this.options.onChange.call( this, this.map_data );
        }
    });

}(jQuery));