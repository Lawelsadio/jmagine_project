var jmagine = {

}

var background_type = {
    EMPTY:'empty',
    DEFAULT:'default',
    LIBRAIRY:'librairy',
    UPLOAD:'upload'
}

jmagine.parcoursService = {

    background_element:null,
    background_type:null,
    background_media_id:null,
    original_background_url:null,
    file_input_element:null,

    image_type_input_element:null,
    image_id_input_element:null,

    init:function( options ) {
        jmagine.parcoursService.background_element = options.background_preview_element;
        jmagine.parcoursService.file_input_element = options.file_input_element;

        if( jmagine.parcoursService.background_element.find('img')[0] ) {
            jmagine.parcoursService.original_background_url = jmagine.parcoursService.background_element.find('img')[0].src;
            jmagine.parcoursService.background_type = background_type.DEFAULT;
        }
        else {
            background_type.EMPTY;
        }
    },
    setImageFromLibrairy:function( media_id, media_url ) {
        var img_element = jmagine.parcoursService.background_element.find('img');
        if( img_element[0] ) {
            img_element[0].src = media_url;
            img_element.css({left:0, top:0, width:'100%', height:'100%'});
        }
        else {
            jmagine.parcoursService.background_element.find('.position_fix').append( $('<img src="'+media_url+'">'));
        }

        jmagine.parcoursService.background_type = background_type.LIBRAIRY;
        jmagine.parcoursService.background_media_id = media_id;
    },
    setImageFromUpload:function(e){
        var reader = new FileReader();
        reader.addEventListener('load', function() {
            var prv = jmagine.parcoursService.background_element.find('img')[0];
            if(!prv) {
                prv = $('<img />');
                jmagine.parcoursService.background_element.find('.position_fix').append(prv);
                prv = prv[0];
            }

            prv.src = this.result;
            var holder_width = 365;
            var holder_height = 244;

            var h = prv.naturalHeight;
            var w = prv.naturalWidth;
            var ratio_holder = 770/514;
            var ratio_image = w / h;

            if( ratio_holder > ratio_image ) {
                $(prv).width( holder_width );
                $(prv).height( Math.round( holder_width / ratio_image ) );
                $(prv).css({'top':Math.round( holder_height - ((holder_width / ratio_image)))/2, left:'0'});
            }
            else {
                $(prv).width( Math.round( holder_height * ratio_image ) );
                $(prv).height( holder_height );
                $(prv).css({'left':Math.round(holder_width - ((holder_height * ratio_image )))/2, top:'0'});
            }
        }, false);

        if( jmagine.parcoursService.file_input_element[0].files[0] ) {
            jmagine.parcoursService.background_type = background_type.UPLOAD;
            reader.readAsDataURL( jmagine.parcoursService.file_input_element[0].files[0] );
        }
        else {
            if( jmagine.parcoursService.original_background_url ) {
                var prv = jmagine.parcoursService.background_element.find('img')[0];
                if( prv ) prv.src = jmagine.parcoursService.original_background_url;
            }
            else jmagine.parcoursService.background_element.find('img').remove();
        }
    },
    deleteImage:function() {
        jmagine.parcoursService.background_element.find('img').remove();
        jmagine.parcoursService.background_type = background_type.EMPTY;
    }
}

jmagine.sectionsService = {

    background_element:null,
    background_type:null,
    background_media_id:null,
    original_background_url:null,
    file_input_element:null,

    image_type_input_element:null,
    image_id_input_element:null,

    init:function( options ) {
        jmagine.sectionsService.background_element = options.background_preview_element;
        jmagine.sectionsService.file_input_element = options.file_input_element;

        if( jmagine.sectionsService.background_element.find('img')[0] ) {
            jmagine.sectionsService.original_background_url = jmagine.sectionsService.background_element.find('img')[0].src;
            jmagine.sectionsService.background_type = background_type.DEFAULT;
        }
        else {
            background_type.EMPTY;
        }
    },
    setImageFromLibrairy:function( media_id, media_url ) {
        var img_element = jmagine.sectionsService.background_element.find('img');
        if( img_element[0] ) {
            img_element[0].src = media_url;
            img_element.css({left:0, top:0, width:'100%', height:'100%'});
        }
        else {
            jmagine.sectionsService.background_element.find('.position_fix').append( $('<img src="'+media_url+'">'));
        }

        jmagine.sectionsService.background_type = background_type.LIBRAIRY;
        jmagine.sectionsService.background_media_id = media_id;
    },
    setImageFromUpload:function(e){
        var reader = new FileReader();
        reader.addEventListener('load', function() {
            var prv = jmagine.sectionsService.background_element.find('img')[0];
            if(!prv) {
                prv = $('<img />');
                jmagine.sectionsService.background_element.find('.position_fix').append(prv);
                prv = prv[0];
            }

            prv.src = this.result;
            var holder_width = 365;
            var holder_height = 244;

            var h = prv.naturalHeight;
            var w = prv.naturalWidth;
            var ratio_holder = 770/514;
            var ratio_image = w / h;

            if( ratio_holder > ratio_image ) {
                $(prv).width( holder_width );
                $(prv).height( Math.round( holder_width / ratio_image ) );
                $(prv).css({'top':Math.round( holder_height - ((holder_width / ratio_image)))/2, left:'0'});
            }
            else {
                $(prv).width( Math.round( holder_height * ratio_image ) );
                $(prv).height( holder_height );
                $(prv).css({'left':Math.round(holder_width - ((holder_height * ratio_image )))/2, top:'0'});
            }
        }, false);

        if( jmagine.sectionsService.file_input_element[0].files[0] ) {
            jmagine.sectionsService.background_type = background_type.UPLOAD;
            reader.readAsDataURL( jmagine.sectionsService.file_input_element[0].files[0] );
        }
        else {
            if( jmagine.sectionsService.original_background_url ) {
                var prv = jmagine.sectionsService.background_element.find('img')[0];
                if( prv ) prv.src = jmagine.sectionsService.original_background_url;
            }
            else jmagine.sectionsService.background_element.find('img').remove();
        }
    },
    deleteImage:function() {
        jmagine.sectionsService.background_element.find('img').remove();
        jmagine.sectionsService.background_type = background_type.EMPTY;
    }
}

jmagine.poiService = {
    background_element:null,
    background_type:null,
    background_media_id:null,
    original_background_url:null,
    file_input_element:null,

    image_type_input_element:null,
    image_id_input_element:null,

    init:function( options ) {
        jmagine.poiService.background_element = options.background_preview_element;
        jmagine.poiService.file_input_element = options.file_input_element;

        if( jmagine.poiService.background_element.find('img')[0] ) {
            jmagine.poiService.original_background_url = jmagine.poiService.background_element.find('img')[0].src;
            jmagine.poiService.background_type = background_type.DEFAULT;
        }
        else {
            background_type.EMPTY;
        }
    },
    setImageFromLibrairy:function( media_id, media_url ) {
        var img_element = jmagine.poiService.background_element.find('img');
        if( img_element[0] ) {
            img_element[0].src = media_url;
            img_element.css({left:0, top:0, width:'100%', height:'100%'});
        }
        else {
            jmagine.poiService.background_element.find('.position_fix').append( $('<img src="'+media_url+'">'));
        }

        jmagine.poiService.background_type = background_type.LIBRAIRY;
        jmagine.poiService.background_media_id = media_id;
    },
    setImageFromUpload:function(e){
        var reader = new FileReader();
        reader.addEventListener('load', function() {
            var prv = jmagine.poiService.background_element.find('img')[0];
            if(!prv) {
                prv = $('<img />');
                jmagine.poiService.background_element.find('.position_fix').append(prv);
                prv = prv[0];
            }

            prv.src = this.result;
            var holder_width = 365;
            var holder_height = 244;

            var h = prv.naturalHeight;
            var w = prv.naturalWidth;
            var ratio_holder = 770/514;
            var ratio_image = w / h;

            if( ratio_holder > ratio_image ) {
                $(prv).width( holder_width );
                $(prv).height( Math.round( holder_width / ratio_image ) );
                $(prv).css({'top':Math.round( holder_height - ((holder_width / ratio_image)))/2, left:'0'});
            }
            else {
                $(prv).width( Math.round( holder_height * ratio_image ) );
                $(prv).height( holder_height );
                $(prv).css({'left':Math.round(holder_width - ((holder_height * ratio_image )))/2, top:'0'});
            }
        }, false);

        if( jmagine.poiService.file_input_element[0].files[0] ) {
            jmagine.poiService.background_type = background_type.UPLOAD;
            reader.readAsDataURL( jmagine.poiService.file_input_element[0].files[0] );
        }
        else {
            if( jmagine.poiService.original_background_url ) {
                var prv = jmagine.poiService.background_element.find('img')[0];
                if( prv ) prv.src = jmagine.poiService.original_background_url;
            }
            else jmagine.poiService.background_element.find('img').remove();
        }
    },
    deleteImage:function() {
        jmagine.poiService.background_element.find('img').remove();
        jmagine.poiService.background_type = background_type.EMPTY;
    }
};

