package jmagine
/**
 * This class handle the comment service
 * Can handle text comment through "content" or files through "linkedFile"
 */
class Comment {

    String              content
    FileContainer linkedFile
    ModStatus modStatus = ModStatus.UNMODERATED
    User moderatedBy
    Long                submittedByUserId // Can be intern jmagine.User / jmagine.MobileUser depending on the ownerType
    OwnerType submittedByUserType
    Date                dateCreated
    Date                lastUpdated

    static belongsTo = [parcours: Parcours, poi:POI ]

    static constraints =
    {
        content                 blank: true
        linkedFile              nullable: true
        modStatus               nullable: false
        moderatedBy             nullable: true
        submittedByUserId       nullable: false
        submittedByUserType     nullable: false
        poi                     nullable: true
        parcours                nullable: true
    }
}
