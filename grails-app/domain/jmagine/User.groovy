package jmagine

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    transient springSecurityService

    String username
    String password
    String mail
    Thumbnail thumbnail
    //  this need to be updated to false once a real bdd has been uploaded
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    Date	dateCreated
    Date 	lastUpdated

    static transients = ['springSecurityService']
    static mappedBy = [ moderatedParcours: 'moderators']
    static hasMany = [ moderatedParcours: Parcours ]

    static constraints = {
        username 	blank: false, unique: true
        password 	blank: false
        mail     	blank: false, email: true
        thumbnail	nullable: true
    }

    static mapping = {
        table '`User`'
        password 	column: '`password`'
        thumbnail 	cascade: 'all-delete-orphan'
    }


    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }




}
