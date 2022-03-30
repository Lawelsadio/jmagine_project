package jmagine

class Thumbnail {
    String      filename
    Date        dateCreated

    static belongsTo = [ user:User ]

    static constraints = {
        filename            blank: false
    }
}
