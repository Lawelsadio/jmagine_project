(function ($) {

    image_types = {
        default:"default",
        empty:"empty",
        librairy:"librairy",
        upload:"upload"
    };

    $.widget( 'jmagine.imageUploader', {

        options:{
            has_error:null,
            has_error_invalid:false,
            has_error_missing:false,
            template:"jmagine-image-upload-plugin-template",
            allow_browse_gallery:true,
            allow_delete_image:true,
            allow_upload:true,
            file_upload_name_and_id:'background_pic',
            uid:0,
            size:{width:365,height:243},
            parcours_id:null,
            default_image_src:null,
            onChange:function(){}
        },

        image_data: {
            type:image_types.default,
            imageID:null
        },

        elements: {
            upload_button:null,
            librairy_button:null,
            delete_button:null,
            preview_holder:null,
            image_element:null,
            input_element:null
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
            if(!template) console.error( 'Template not found!');
            else {
                if( this.options.has_error == 'missing' ) this.options.has_error_missing = true
                else if( this.options.has_error == 'invalid' ) this.options.has_error_invalid = true

                Mustache.parse(template);
                var rendered = Mustache.render(template,this.options);
                this.element.html( rendered );

                this.elements.upload_button = this.element.find( '.upload_button_fix' );
                this.elements.librairy_button = this.element.find( '#pick_from_media_'+this.options.uid );
                this.elements.delete_button = this.element.find( '#delete_background_'+this.options.uid );
                this.elements.preview_holder = this.element.find( '#background_preview_'+this.options.uid );
                this.elements.input_element = this.element.find( '#'+this.options.file_upload_name_and_id );
                this.elements.image_element = this.elements.preview_holder.find('img');
                if( !this.elements.image_element.length ) this.elements.image_element = null;

                if(!this.options.allow_browse_gallery) this.elements.librairy_button.css('display', 'none' );
                else {
                    this.elements.librairy_button.on('click', $.proxy( this._handle_show_librairy, this ) );
                }
                if(!this.options.allow_delete_image) this.elements.delete_button.css('display', 'none' );
                else {
                    this.elements.delete_button.on('click', $.proxy( this._handle_remove_image, this ) );
                }
                if(!this.options.allow_upload ) this.elements.upload_button.css('display', 'none' );
                else {
                    this.elements.input_element.on('change', $.proxy( this._handle_file_input_change, this ) );
                }
            }
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
            // TODO : test présence Modal Bootstrap!

            return plugins_missing;
        },
        _handle_local_file_load:function(event) {
            if(!this.elements.image_element) {
                this.elements.image_element = $('<img />');
                this.elements.preview_holder.find('.position_fix').append( this.elements.image_element );
            }

            this.elements.image_element[0].src = event.currentTarget.result;
            var holder_width = this.options.size.width;
            var holder_height = this.options.size.height;

            var h = this.elements.image_element[0].naturalHeight;
            var w = this.elements.image_element[0].naturalWidth;
            var ratio_holder = this.options.size.width/this.options.size.height;
            var ratio_image = w / h;

            if( ratio_holder > ratio_image ) {
                this.elements.image_element.width( holder_width );
                this.elements.image_element.height( Math.round( holder_width / ratio_image ) );
                this.elements.image_element.css({'top':Math.round( holder_height - ((holder_width / ratio_image)))/2, left:'0'});
            }
            else {
                this.elements.image_element.width( Math.round( holder_height * ratio_image ) );
                this.elements.image_element.height( holder_height );
                this.elements.image_element.css({'left':Math.round(holder_width - ((holder_height * ratio_image )))/2, top:'0'});
            }
        },
        _handle_file_input_change:function(event) {
            if( this.elements.input_element[0].files[0] ) {
                var reader = new FileReader();
                reader.addEventListener('load', $.proxy( this._handle_local_file_load, this ));
                reader.readAsDataURL( this.elements.input_element[0].files[0] );

                this.image_data.type = image_types.upload;
                this.image_data.imageID = null;
                this._dispatchChangeEvent();
            }
            else {
                if( this.image_data.type == image_types.upload ) {
                    if( this.options.default_image_src ) {
                        if(!this.elements.image_element) {
                            this.elements.image_element = $('<img />');
                            this.elements.preview_holder.find('.position_fix').append( this.elements.image_element );
                        }
                        this.elements.image_element[0].src = this.options.default_image_src;
                    }
                    else {
                        if(this.elements.image_element) {
                            this.elements.image_element.remove();
                            this.elements.image_element = null;
                        }
                    }
                    this.image_data.type = image_types.default;
                    this.image_data.imageID = null;
                }
                this._dispatchChangeEvent();
            }
        },
        _handle_remove_image:function(event) {
            if( this.elements.image_element ) {
                this.elements.image_element.remove();
                this.elements.image_element = null;
            }

            this.image_data.type = image_types.empty;
            this.image_data.imageID = null;
            this._dispatchChangeEvent();
        },
        _handle_librairy_pick:function(event) {
            this.image_data.type = image_types.librairy;
            this.image_data.imageID = $(event.currentTarget).data('media-id');
            if(!this.elements.image_element) {
                this.elements.image_element = $('<img />');
                this.elements.preview_holder.find('.position_fix').append( this.elements.image_element );
            }
            console.log( this.elements.image_element );
            this.elements.image_element[0].src = $(event.currentTarget).data('media-url');
            $('#modal').modal('hide');
            this._dispatchChangeEvent();
        },
        _handle_show_librairy:function(event) {
            $.ajax( base_url + '/parcours/'+this.options.parcours_id+'/medias').done( $.proxy( function(data){
                var j_data = $(data);
                j_data.find('.media_item').on('click', $.proxy( this._handle_librairy_pick, this ));
                $('#modal').addClass('media_modal');
                $("#modal .modal-body").empty();
                $("#modal .modal-body").append(j_data);
                $('#modal').modal({});
            }, this ));
        },
        _dispatchChangeEvent:function() {
            if( this.options.onChange ) this.options.onChange.call( this, this.image_data );
        }
    });

}(jQuery));