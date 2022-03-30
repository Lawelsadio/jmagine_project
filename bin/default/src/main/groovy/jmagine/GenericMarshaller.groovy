package jmagine

/**
 * Created with IntelliJ IDEA.
 * jmagine.User: Lionel
 * Date: 18/08/14
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */

import grails.converters.XML
import grails.util.Holders
import jmagine.ContentComponent
import jmagine.POI
import jmagine.Parcours
import org.grails.web.converters.marshaller.NameAwareMarshaller
import org.grails.web.converters.marshaller.ObjectMarshaller
import org.hibernate.collection.internal.PersistentList

class GenericMarshaller implements ObjectMarshaller<XML>, NameAwareMarshaller {

    @Override
    boolean supports(Object object) {
        if( object instanceof Date )
            return false
        else if( object instanceof ArrayList )
            return false
        else if( object instanceof PersistentList )
            return false
        else return true
    }

    @Override
    String getElementName(Object object) {
        if( object instanceof ContentComponent )
            return "component"
        else if( object instanceof Parcours )
            return 'parcours'
        else if( object instanceof POI )
            return 'poi'
        else return object.class.name
    }

    @Override
    void marshalObject(Object object, XML converter) {
        if( object instanceof Parcours ) {
            converter.build {
                id( object.id )
                title( object.title )
                backgroundPic( object.backgroundPic? Holders.config.grails.assetspath.url + object.backgroundPic.filename:'' )
                components( object.components )
                if( object.pois.size() ) {
                    first_poi( object.pois[0].id )
                }
                else first_poi( '' )
            }
        }

        else if( object instanceof ContentComponent ) {
            converter.build {
                title( object.title )
                backgroundPic( object.backgroundPic?(Holders.config.grails.assetspath.url +object.backgroundPic.filename):'')
                content( "<![CDATA["+object.content+"]]>" )
            }
        }

        else if( object instanceof POI ) {
            converter.build {
                title( object.title )
                backgroundPic( object.backgroundPic?Holders.config.grails.assetspath.url + object.backgroundPic.filename:'' )
                lat( object.lat )
                lng( object.lng )
                address( object.address )
                if( object.content ) content( "<![CDATA["+object.content+"]]>" )
                else content('')
                def index = object.parcours.pois.indexOf( object )
                isNFCEnabled(object.isNFCEnabled)
                isQREnabled(object.isQREnabled)
                isSNSEnabled(object.isSNSEnabled)
                isGeolocEnabled(object.isGeolocEnabled)

                if( (index != -1) && ( (index+1) < object.parcours.pois.size() ) )
                    next_poi( object.parcours.pois[index+1].id )
                else
                    next_poi( object.parcours.pois[0].id )
            }
        }
    }
}