package jmagine
import grails.rest.*

/**
 * a jmagine.Parcours is an ordered list of POIs
 * The "isValidated" status helps with the building of new "jmagine.Parcours", used to hide unfinished to users
 * "fileList" contains all the files used for this "jmagine.Parcours"
 /////
* Un “ Parcours  jmagine" est une liste ordonnée de POIS(points).
        * Le statut "isValidated" aide à la construction de nouveaux “ Le Parcours jmagine", est utilisé pour cacher aux utilisateurs les éléments non terminés.
        * fileList" contient tous les fichiers utilisés pour ce "jmagine.Parcours".
        */
//@Resource(uri='/Parcours')

class Parcours {

    String              title
    FileContainer backgroundPic
    Boolean             isValidated = Boolean.FALSE
    Boolean             isNFCEnabled = Boolean.FALSE
    Boolean             isQREnabled = Boolean.FALSE
    Boolean             isSNSEnabled = Boolean.FALSE
    Boolean             isGeolocEnabled = Boolean.FALSE
    User author
    String              description
    List                pois
    List                components
    List                fileList
    Date                dateCreated
    Date                lastUpdated

    static hasMany = [pois      :POI, comments:Comment,
                      moderators:User,
                      fileList  :FileContainer, components:ContentComponent ]
    static mappedBy = [ fileList: "parcours", backgroundPic:'none', moderators: "moderatedParcours"]
    static belongsTo = [User]


    static constraints =
    {
        title           blank: false
        backgroundPic   nullable: true
        isValidated     nullable: false
        author          nullable: false
        pois            nullable: true
        comments        nullable: true
        description     nullable: true
        moderators      nullable: false // by default, the author is a moderator
        fileList        nullable: true
        isNFCEnabled    nullable: false
        isQREnabled     nullable: false
        isSNSEnabled    nullable: false
        isGeolocEnabled nullable: false
    }

    static mapping = {
        description type: 'text'
        pois cascade: 'all-delete-orphan'
        fileList cascade: 'all-delete-orphan'
        components cascade: 'all-delete-orphan'
    }
}
