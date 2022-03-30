package jmagine

import grails.core.GrailsApplication

class JmagineImageTagLib {
    static defaultEncodeAs = [taglib:'raw']
    GrailsApplication grailsApplication

    def jmagineImage = { attrs, body ->
        def uri
        if(!attrs.class) attrs.class = ''

        if( attrs.type=="background")
            uri = asset.image(src:'default_background.png', class:attrs.class )
        else
            uri = asset.image(src:'default_avatar.png', class:attrs.class )

        if(attrs.makelink && attrs.src) {
            if( attrs.absolute )
                out << grailsApplication.config.grails.assetspath.url+attrs.src
        }
        else if(attrs.src) {
            out << "<img src=\"${( grailsApplication.config.grails.assetspath.url + attrs.src)}\" class=\"${attrs.class}\" />"
        }
        else {
            out << uri
        }
    }
}
