package jmagine
//import grails.rest.*


/**
 * Serves all purposes for file handling, can be files for the frontend presentation or files obtained from comments
 * Each file can be submitted with a comment to help tracking
 * jmagine.FileType must be defined for each file
 * Files are linked to the "jmagine.Parcours" to help segmentation between different projects
 * The user should be tracked in order to prevent abusive use of the comment service
 *
 * * Il peut s'agir de fichiers pour la présentation frontale ou de fichiers obtenus à partir de commentaires.
 * Chaque fichier peut être soumis avec un commentaire pour aider au suivi.
 * jmagine.FileType doit être défini pour chaque fichier.
 * Les fichiers sont liés au "jmagine.Parcours" pour faciliter la segmentation entre les différents projets.
 * L'utilisateur doit être suivi afin d'éviter une utilisation abusive du service de commentaires.
 */
//@Resource(uri='/FileContainer')
class FileContainer {

    FileType type
    String      filename
    String      comment
    Long        ownerId // Can be intern jmagine.User / jmagine.MobileUser depending on the ownerType
    OwnerType ownerType
    Date        dateCreated


    static belongsTo = [ parcours: Parcours ]

    static constraints =
    {
        type                nullable: false
        filename            blank: false
        comment             blank: true, nullable: true
        ownerId             nullable: false
        ownerType           nullable: false
    }
}
