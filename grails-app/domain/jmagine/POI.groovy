package jmagine
/**
 * A jmagine.POI is a step in a "jmagine.Parcours", contains many informations about the spot itself, as well as many "components"
 * for mobile display handling
 * We should never store an "address", but correlate the lat / lng with reverse geocoding
 * Future improvement : allow the "linking" between many POIs, a same place could be part of many "jmagine.Parcours" and we
 * should offer the users the possibility to switch from one "jmagine.Parcours" to another at these meeting points
 *
 * * Un jmagine.POI est une étape dans un "jmagine.Parcours", il contient de nombreuses informations sur le lieu lui-même, ainsi que de nombreux "composants".
 * pour la manipulation de l'affichage mobile
 * Nous ne devrions jamais stocker une "adresse", mais corréler le lat / lng avec un géocodage inverse.
 * Amélioration future : permettre la "liaison" entre plusieurs POIs, un même lieu pourrait faire partie de plusieurs "jmagine.Parcours" et nous devrions offrir aux utilisateurs la possibilité d'utiliser les mêmes POIs.
 * Nous devrions offrir aux utilisateurs la possibilité de passer d'un "jmagine.Parcours" à un autre à ces points de rencontre.
 */
class POI
{
    String              title
    FileContainer       backgroundPic
    Double              lat
    Double              lng

    Date                dateCreated
    Date                lastUpdated
    String              address
    String              content

    Boolean             isNFCEnabled = Boolean.FALSE
    Boolean             isQREnabled = Boolean.FALSE
    Boolean             isSNSEnabled = Boolean.FALSE
    Boolean             isGeolocEnabled = Boolean.FALSE

    static hasMany = [ comments: Comment ]

    static belongsTo = [ parcours: Parcours ]

    static constraints =
    {
        title           blank: false
        backgroundPic   nullable: false
        lat             nullable: false
        lng             nullable: false
        content         nullable: true, blank: true
        isNFCEnabled    nullable: false
        isQREnabled     nullable: false
        isSNSEnabled    nullable: false
        isGeolocEnabled nullable: false
    }

    static mapping = {
        address type: 'text'
        content type: 'text'
    }
}
