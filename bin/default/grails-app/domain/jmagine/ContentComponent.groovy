package jmagine

class ContentComponent {

    String              title
    FileContainer backgroundPic
    String              content
    String              shortDesc

    Date                dateCreated
    Date                lastUpdated

    static belongsTo = [Parcours]

    static constraints =
    {
        title           nullable: false
        backgroundPic   nullable: false
        content         blank: true, nullable: true
        shortDesc       blank: true, nullable: true
    }

    static mapping =
    {
        content type: 'text'
    }
}
